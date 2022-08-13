/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se1625.controller;

import com.se1625.tblaccount.TblAccountDAO;
import com.se1625.tblaccount.TblAccountDTO;
import com.se1625.tblcompany.RegisterCompanyError;
import com.se1625.tblcompany.TblCompanyDAO;
import com.se1625.tblcompany.TblCompanyDTO;
import com.se1625.utils.MyApplicationConstants;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
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
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author ThanhTy
 */
public class CompanyUpdateProfileServlet extends HttpServlet {

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

        RegisterCompanyError error = new RegisterCompanyError();

        //get session
        HttpSession session = request.getSession(false);
        try {
            if (session != null) {
                TblAccountDTO account = (TblAccountDTO) session.getAttribute("COMPANY_ROLE");
                TblCompanyDAO companyDAO = new TblCompanyDAO();
//                TblCompanyDTO companyDTO = companyDAO.getCompanyByEmail(account.getEmail())
                //get company info 
                TblCompanyDTO companyDTO = (TblCompanyDTO) session.getAttribute("COMPANY_ROLE_INFO");
                
                if (companyDTO != null) {
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
                                avatarName = companyDTO.getCompanyID() + "_" + path.getFileName().toString();
                                File uploadFile = new File(realPath + "/" + avatarName);
                                filePath = uploadFile.toString();

                                if (Files.exists(Paths.get(realPath)) == false) {
                                    Files.createDirectories(Paths.get(realPath));
                                } else {
                                    if (Files.exists(Paths.get(uploadFile.toURI())) == false)
                                    item.write(uploadFile);
                                }
                                fileLength = Files.size(Paths.get(filePath));
                            }
                        }
                    }
                    fileLength = (fileLength / (1024));
                    long sizeMax = 800;

                    String address = params.get("addressUpdate");
                    String city = params.get("cityUpdate");
                    String stringPhone = params.get("phoneUpdate");
                    String description = params.get("descriptUpdate");
                    String email = params.get("email");

                    //check address input format
                    if (address.trim().length() == 0) {
                        error.setCompanyAddressLengthError("Address is required 6-100 characters");
                        checkError = true;
                    }

                    //check phone number update
                    if (stringPhone.trim().length() < 10 || stringPhone.trim().length() >= 11) {
                        error.setCompanyPhoneLengthError("Number phone is required 10 characters");
                        checkError = true;
                    }
                    //check description update
                    if (description.trim().length() > 2000 || description.trim().length() < 50) {
                        //quang loi
                        checkError = true;
                        error.setCompanyDescriptionLegthError("Company description is required 50-2000 characters");
                    }
                    //check city update
                    if (city.equals("")) {
                        checkError = true;
                        error.setCompanyCityError("City is required!");
                    }
                    if (avatarName.trim().length() > 0) {
                        if (fileLength > sizeMax) {
                            checkError = true;
                            error.setCompanyLogoLengthError("File's size must not exceed 800KB");
                        } else {
                            if (avatarName.endsWith(".png") == false
                                    && avatarName.endsWith(".jpg") == false
                                    && avatarName.endsWith(".jpeg") == false
                                    && avatarName.endsWith(".svg") == false) {
                                checkError = true;
                                error.setCompanyLogoTypeError("File type must be .png, .jpg, .jpeg, .svg.");
                            }
                        }
                    }
                    if (checkError) {
                        request.setAttribute("ERROR_UPDATE_COMPANYPROFILE", error);
                        if (avatarName.trim().isEmpty() == false) {
                            Files.deleteIfExists(Paths.get(filePath));
                        }
                        url = properties.getProperty(MyApplicationConstants.CompanyFeatures.COMPANY_PROFILE_CONTROLLER);
                        RequestDispatcher rd = request.getRequestDispatcher(url);
                        rd.forward(request, response);
                    } else {
                        //Update
                        boolean resultUpdateCompany = companyDAO.updateCompanyProfile(companyDTO.getCompanyID(), address, stringPhone, description, city);
                        TblAccountDAO accountDAO = new TblAccountDAO();
                        if (fileName.equals("") == false) {
                            String oldAvatar = account.getAvatar();
                            boolean resultUpdateAccount = accountDAO.updateAccount(email, avatarName);
                            account.setAvatar(avatarName);
                            session.setAttribute("COMPANY_ROLE", account);
                            if (resultUpdateCompany == true && resultUpdateAccount == true) {
                                if (oldAvatar != null || "".equals(oldAvatar) == false) {
                                    String oldAvatarPath = request.getServletContext().
                                            getRealPath("/avatars") + "/" + oldAvatar;
                                    Files.deleteIfExists(Paths.get(oldAvatarPath));
                                }
                            }
                        }
                        TblCompanyDTO newInforCompany = companyDAO.getCompanyByEmail(account.getEmail());
                        session.setAttribute("COMPANY_ROLE_INFO", newInforCompany);
                        url = MyApplicationConstants.CompanyFeatures.COMPANY_PROFILE_CONTROLLER;
                        response.sendRedirect(url);
                    }

                }//if conmpany exist
                else {
                    response.sendRedirect(url);
                } //if company not exist
            } //if session exist
            else {
                response.sendRedirect(url);
            }// if session not exist

        } catch (FileUploadException ex) {
            log("FileUploadException at CompanyUpdateProfileServlet " + ex.getMessage());
        } catch (NamingException ex) {
            log("NamingException at CompanyUpdateProfileServlet " + ex.getMessage());
        } catch (SQLException ex) {
            log("SQLException at CompanyUpdateProfileServlet " + ex.getMessage());
        } catch (Exception ex) {
            log("Exception at CompanyUpdateProfileServlet " + ex.getMessage());
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
