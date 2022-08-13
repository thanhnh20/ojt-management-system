/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se1625.controller;

import com.se1625.tblaccount.TblAccountDAO;
import com.se1625.tblaccount.TblAccountDTO;
import com.se1625.tblapplication.ApplyCVStudentError;
import com.se1625.tblapplication.TblApplicationDAO;
import com.se1625.tblapplication.TblApplicationDTO;
import com.se1625.tblcompany_post.TblCompany_PostDAO;
import com.se1625.tblcompany_post.TblCompany_PostDTO;
import com.se1625.tblsemester.TblSemesterDAO;
import com.se1625.tblsemester.TblSemesterDTO;
import com.se1625.tblstudent.TblStudentDAO;
import com.se1625.tblstudent.TblStudentDTO;
import com.se1625.utils.MyApplicationConstants;
import com.se1625.utils.MyApplicationHelper;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author Thai Quoc Toan <toantqse151272@fpt.edu.vn>
 */
public class ApplyCVStudentServlet extends HttpServlet {

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
        //check session và lấy attribute
        HttpSession session = request.getSession(false);
        String url = MyApplicationConstants.ApplyCVStudentFeature.LOGIN_PAGE;
        try {
            if (session != null) {
                TblStudentDTO student = (TblStudentDTO) session.getAttribute("STUDENT_ROLE");
                if (student != null) {
                    url = properties.getProperty(MyApplicationConstants.ApplyCVStudentFeature.HOME_AFTER_CLICK1_PAGE);
                    // get parameters
                    List<FileItem> items = (List<FileItem>) request.getAttribute("LIST_PARAMETERS");

                    Iterator<FileItem> iter = items.iterator();
                    HashMap<String, String> params = new HashMap<>();
                    String name = "";
                    String value = "";
                    String fileName = "";
                    String cvName = "";
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
                                String realPath = request.getServletContext().getRealPath("/CVs");
                                cvName = params.get("postID") + "-" + student.getStudentCode() + "_" + path.getFileName().toString();
                                File uploadFile = new File(realPath + "/" + cvName);
                                filePath = uploadFile.toString();
                                if (Files.exists(Paths.get(realPath)) == false) {
                                    Files.createDirectories(Paths.get(realPath));
                                }
                                if (Files.exists(Paths.get((uploadFile.toURI()))) == false) {
                                    item.write(uploadFile);
                                }
                                fileLength = Files.size(Paths.get(filePath));
                            }
                        }
                    }
                    String expectedJob = params.get("txtExpectedJob");
                    String technology = params.get("txtTechnology");
                    String experience = params.get("txtExperience");
                    String foreignLanguage = params.get("txtForeignLanguage");
                    String otherSkills = params.get("txtOtherSkills");
                    int postID = Integer.parseInt(params.get("postID"));

                    ApplyCVStudentError errors = new ApplyCVStudentError();
                    boolean found = false;
                        if (expectedJob.trim().length() < 6 || expectedJob.trim().length() > 50) {
                            found = true;
                            errors.setExpectedJobLengthError("Expected Job is required 6-50 characters");
                        }

                        if (technology.trim().length() < 6 || technology.trim().length() > 50) {
                            found = true;
                            errors.setTechnologyLengthError("Main Skill is required 6-50 characters");
                        }

                        if (foreignLanguage.trim().length() < 6 || foreignLanguage.trim().length() > 50) {
                            found = true;
                            errors.setForeignLanguageLengthError("Foreign Language is required 6-50 characters");
                        }

                        if (otherSkills.trim().length() < 6 || otherSkills.trim().length() > 50) {
                            found = true;
                            errors.setOtherSkillsLengthError("Other skills is required 6-50 characters");
                        }

                        if (cvName.trim().equals("")) {
                            found = true;
                            errors.setFileUploadError("File is not empty");
                        } else {
                            if (cvName.endsWith("pdf") == false) {
                                found = true;
                                errors.setFileUploadTypeError("File is required pdf file");
                            }
                            fileLength = (fileLength / (1024 * 1024));
                            long sizeMax = 1;
                            if (fileLength > sizeMax) {
                                found = true;
                                errors.setFileUploadLengthError("File's length is required less than 1MB");
                            }
                        }

                        TblStudentDAO studentDAO = new TblStudentDAO();
                        TblStudentDTO studentInformation = studentDAO.
                                getStudentInformation(student.getStudentCode());
                        request.setAttribute("STUDENT_INFORMATION", studentInformation);

                        TblApplicationDTO application = new TblApplicationDTO();
                        application.setAttachmentPath(cvName);
                        application.setExpected_job(expectedJob);
                        application.setExperience(experience);
                        application.setForeign_Language(foreignLanguage);
                        application.setOtherSkills(otherSkills);
                        application.setTechnology(technology);
                        application.setStudent(student);

                        TblCompany_PostDAO companyPostDAO = new TblCompany_PostDAO();
                        TblCompany_PostDTO companyPost = companyPostDAO.getCompanyPost(postID);
                        application.setCompanyPost(companyPost);

                        request.setAttribute("APPLICATION_INFORMATION", application);
                        TblSemesterDAO semesterDAO = new TblSemesterDAO();
                        TblSemesterDTO currentSemester = semesterDAO.getCurrentSemester();
                        application.setSemester(currentSemester);

                        request.setAttribute("APPLICATION_INFORMATION", application);

                        String errorQuantity = (String) request.getAttribute("ERROR_RUN_OUT_QUANTITY_INTERNS");
                        if (errorQuantity != null) {
                            found = true;
                        }
                        if (found) {
                            request.setAttribute("POST_COMPANY_INFOR", companyPost);
                            request.setAttribute("ERRORS", errors);
                            RequestDispatcher rd = request.getRequestDispatcher(url);
                            rd.forward(request, response);
                            if (cvName.trim().isEmpty() == false) {
                                Files.deleteIfExists(Paths.get(filePath));
                            }
                        } else {
                            TblApplicationDAO applicationDAO = new TblApplicationDAO();
                            boolean result = applicationDAO.addApplication(application);
                            if (result) {
                                String subject = "The new application internship";
                                TblAccountDAO accountDAO = new TblAccountDAO();
                                TblAccountDTO systemAccount = accountDAO.GetAccountByRole(4);
                                String link = "http://localhost:8080/OJTManagementSystem/CompanyShowInternshipApplicationController";
                                String message = "Dear " + companyPost.getCompany().getAccount().getName() + " company,\n"
                                        + "\n"
                                        + "The OJT system wants to announce that the job is " + companyPost.getTitle_Post()
                                        + " that was applied by the student is " + student.getAccount().getName() + ", student Code is "
                                        + student.getStudentCode() + ". Please click on the link " + link
                                        + " so as not to miss any information.\n"
                                        + "\n"
                                        + "Regards,"
                                        + "The support OJT team";
                                MyApplicationHelper.sendEmail(companyPost.getCompany().getAccount(), systemAccount, message, subject);
                                url = MyApplicationConstants.ApplyCVStudentFeature.STUDENT_APPLIED_JOB_PAGE;
                                response.sendRedirect(url);
                            }
                        }
                }else{
                    response.sendRedirect(url);
                }
            }else{
                response.sendRedirect(url);
            }
        } catch (SQLException ex) {
            log("SQLException at ApplyCVStudentServlet " + ex.getMessage());
        } catch (NamingException ex) {
            log("NamingException at ApplyCVStudentServlet " + ex.getMessage());
        } catch (AddressException ex) {
            log("AddressException at CreateNewCompanyPostServlet " + ex.getMessage());
        } catch (MessagingException ex) {
            log("MessagingException at CreateNewCompanyPostServlet " + ex.getMessage());
        } catch (Exception ex) {
            log("Exception at ApplyCVStudentServlet " + ex.getMessage());
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
