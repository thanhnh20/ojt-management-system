/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se1625.controller;

import com.se1625.tblaccount.TblAccountDAO;
import com.se1625.tblstudent.TblStudentDAO;
import com.se1625.tblstudent.TblStudentDTO;
import com.se1625.tblstudent.TblStudentError;
import com.se1625.utils.MyApplicationConstants;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;

/**
 *
 * @author ASUS
 */
public class UpdateStudentProfileServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        ServletContext context = this.getServletContext();
        Properties properties = (Properties) context.getAttribute("SITE_MAPS");
        String url = MyApplicationConstants.UpdateStudentProfileFeature.LOGIN_PAGE;
        TblStudentError error = new TblStudentError();
        //get session
        HttpSession session = request.getSession(false);
        try {
            if (session != null) {
                TblStudentDTO student = (TblStudentDTO) session.getAttribute("STUDENT_ROLE");
                //check session account
                if (student != null) {
                    //get Date Now
                    LocalDate today = LocalDate.now();
                    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    String nowDate = format.format(today);

                    boolean checkError = false;
                    List<FileItem> items = (List<FileItem>) request.getAttribute("LIST_PARAMETERS");
                    Iterator<FileItem> iter = items.iterator();
                    HashMap<String, String> params = new HashMap<>();
                    String name = "";
                    String value = "";
                    String fileName = "";
                    String avatarName = "";
                    long fileLength = 0;
                    String filePath = "";
                    while (iter.hasNext()) {
                        FileItem item = iter.next();
                        if (item.isFormField()) {
                            name = item.getFieldName();
                            value = item.getString("UTF-8");
                            params.put(name, value);
                        } else {
                            fileName = item.getName();
                            if (fileName == null || fileName.equals("")) {
                                break;
                            } else {
                                Path path = Paths.get(fileName);
                                String realPath = request.getServletContext().getRealPath("/avatars");
                                avatarName = student.getStudentCode() + "_" + path.getFileName().toString();
                                File uploadFile = new File(realPath + "/" + avatarName);
                                filePath = uploadFile.toString();

                                if (Files.exists(Paths.get(realPath)) == false) {
                                    Files.createDirectories(Paths.get(realPath));
                                } else {
                                    if (Files.exists(Paths.get(uploadFile.toURI())) == false) {
                                        item.write(uploadFile);
                                    }
                                }
                                fileLength = Files.size(Paths.get(filePath));
                            }
                        }
                    }
                    fileLength = (fileLength / (1024));
                    long sizeMax = 800;

                    String studentCode = params.get("studentCode");
                    String date = params.get("dateUpdate");
                    String stringGender = params.get("genderUpdate");
                    String address = params.get("addressUpdate");
                    String stringPhone = params.get("phoneUpdate");
                    String email = params.get("email");
                    String postID = params.get("postID");
                    //Check date update
                    if (date.compareTo(nowDate) > 0) {
                        error.setErrorDateInvalid("Your birthDay is illegal");
                        checkError = true;
                    }
                    Period p = Period.between(LocalDate.parse(date), today);
                    if (p.getYears() < 16) {
                        error.setErrorDateInvalid("Your age must over 16 year old");
                        checkError = true;
                    }

                    //check address input format
                    if (address.trim().length() == 0) {
                        error.setErrorAddressLength("Address is required 6-100 characters");
                        checkError = true;
                    }

                    String patternNumberPhone = "^(03|05|07|08|09)([0-9]{8,8})$";
                    //check phone number update
                    if (stringPhone.trim().length() < 10 || stringPhone.trim().length() > 11) {
                        error.setErrorPhoneNumberLength("Number phone is required 10 characters");
                        checkError = true;
                    } else {
                        if (stringPhone.matches(patternNumberPhone) == false) {
                            checkError = true;
                            error.setErrorPhoneNumberFormat("Number phone is invalid format");
                        }
                    }
                    if (avatarName.trim().length() > 0) {
                        if (fileLength > sizeMax) {
                            checkError = true;
                            error.setErrorFileLength("File's size must not exceed 800KB");
                        } else {
                            if (avatarName.endsWith(".png") == false
                                    && avatarName.endsWith(".jpg") == false
                                    && avatarName.endsWith(".jpeg") == false
                                    && avatarName.endsWith(".svg") == false) {
                                checkError = true;
                                error.setErrorFileType("File type must be .png, .jpg, .jpeg, .svg.");
                            }
                        }
                    }

                    if (checkError) {
                        request.setAttribute("ERROR_UPDATE_STUDENTPROFILE", error);
                        if (avatarName.trim().isEmpty() == false) {
                            Files.deleteIfExists(Paths.get(filePath));
                        }
                        url = properties.getProperty(MyApplicationConstants.UpdateStudentProfileFeature.SHOW_STUDENT_PROFILE_SERVLET);
                        RequestDispatcher rd = request.getRequestDispatcher(url);
                        rd.forward(request, response);
                    } else {
                        boolean gender = false;
                        if ("Male".endsWith(stringGender)) {
                            gender = true;
                        }
                        TblStudentDAO dao = new TblStudentDAO();
                        Date birthday = Date.valueOf(date);
                        //update
                        boolean resultIpdateStudent = dao.updateStudent(studentCode, birthday, address, gender, stringPhone);
                        TblAccountDAO accountDAO = new TblAccountDAO();
                        if (fileName.equals("") == false) {
                            String oldAvatar = student.getAccount().getAvatar();
                            boolean resultUpdateAccount = accountDAO.updateAccount(email, avatarName);
                            
                            if (resultIpdateStudent == true && resultUpdateAccount == true) {
                                if (oldAvatar != null || "".equals(oldAvatar) == false) {
                                    String oldAvatarPath = request.getServletContext().
                                            getRealPath("/avatars") + "/" + oldAvatar;
                                    Files.deleteIfExists(Paths.get(oldAvatarPath));
                                }
                            }
                        }
                        TblStudentDTO newInforStudent = dao.getStudentInformation(student.getStudentCode());
                        session.setAttribute("STUDENT_ROLE", newInforStudent);

                        if (!postID.isEmpty() && resultIpdateStudent) {
                            request.setAttribute("POST_ID", postID);
                            url = properties.getProperty(MyApplicationConstants.UpdateStudentProfileFeature.SHOW_APPLY_CV_CONTROLLER);
                            RequestDispatcher rd = request.getRequestDispatcher(url);
                            rd.forward(request, response);
                        } else {
                            url = MyApplicationConstants.UpdateStudentProfileFeature.STUDENT_DASHBOARD_CONTROLLER;
                            response.sendRedirect(url);
                        }

                    }
                } //if student is created
                else {
                    response.sendRedirect(url);
                }
            } //if session exist
            else {
                response.sendRedirect(url);
            }
        } catch (IllegalArgumentException ex) {
            log("IllegalArgumentException at UpdateStudentProfileServlet " + ex.getMessage());
            error.setErrorDateEmpty("Please, enter your birthday");
            request.setAttribute("ERROR_UPDATE_STUDENTPROFILE", error);
            url = properties.getProperty(MyApplicationConstants.UpdateStudentProfileFeature.SHOW_STUDENT_PROFILE_SERVLET);
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
        } catch (FileUploadException ex) {
            log("FileUploadException at UpdateStudentProfileServlet " + ex.getMessage());
        } catch (NamingException ex) {
            log("NamingException at UpdateStudentProfileServlet " + ex.getMessage());
        } catch (SQLException ex) {
            log("SQLException at UpdateStudentProfileServlet " + ex.getMessage());
        } catch (Exception ex) {
            log("Exception at UpdateStudentProfileServlet " + ex.getMessage());
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
