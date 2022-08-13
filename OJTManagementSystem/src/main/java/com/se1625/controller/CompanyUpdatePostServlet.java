/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se1625.controller;

import com.se1625.tblaccount.TblAccountDAO;
import com.se1625.tblaccount.TblAccountDTO;
import com.se1625.tblcompany.TblCompanyDTO;
import com.se1625.tblcompany_post.CompanyPostDetailError;
import com.se1625.tblcompany_post.TblCompany_PostDAO;
import com.se1625.tblcompany_post.TblCompany_PostDTO;
import com.se1625.tblmajor.TblMajorDAO;
import com.se1625.tblmajor.TblMajorDTO;
import com.se1625.utils.MyApplicationConstants;
import com.se1625.utils.MyApplicationHelper;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

/**
 *
 * @author ThanhTy
 */
public class CompanyUpdatePostServlet extends HttpServlet {

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

        //lay postID
        int postID = Integer.parseInt(request.getParameter("postID"));

        //get parameter Post
        String title_Post = request.getParameter("titlePost");
        String vacancy = request.getParameter("vacancy");
        int majorID = Integer.parseInt(request.getParameter("majorID"));
        int quantityIterns = Integer.parseInt(request.getParameter("quantityIterns"));
        String expirationDate = request.getParameter("expirationDate");
        String workLocation = request.getParameter("workLocation");
        String job_Description = request.getParameter("job_Description");
        String job_Requirement = request.getParameter("job_Requirement");
        String remuneration = request.getParameter("remuneration");
        boolean school_confirm = Boolean.parseBoolean(request.getParameter("school_confirm"));
        int statusPost = Integer.parseInt(request.getParameter("statusPost"));

        //get session
        HttpSession session = request.getSession(false);

        CompanyPostDetailError errors = new CompanyPostDetailError();
        try {
            if (session != null) {
                TblCompanyDTO companyDTO = (TblCompanyDTO) session.getAttribute("COMPANY_ROLE_INFO");
                if (companyDTO != null) {
                    url = properties.getProperty(MyApplicationConstants.CompanyFeatures.COMPANY_POST_EDIT_PAGE);

                    //flag update
                    boolean checkUpdate = false;

                    //get Date Now
                    LocalDate today = LocalDate.now();
                    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    String nowDate = format.format(today);
                    //Check date update
//                    if (expirationDate.compareTo(nowDate) < 0) {
//                        errors.setExpirationDateError("Post expiration illegal");
//                        checkUpdate = true;
//                    }

                    //check Tittle Post lenght
                    if (title_Post.trim().length() < 10) {
                        errors.setTitlePostLenghtError("Title post must at least 10 character");
                        checkUpdate = true;
                    }
                    // check vacancy length
                    if(vacancy.trim().length() == 0){
                        errors.setVacancyLengthError("Vacancy is empty");
                        checkUpdate = true;
                    }
                                       
                    //check Quanity Interns
                    if (quantityIterns < 0) {
                        errors.setQuantitytInternsNotEngough("Quantity must positive number");
                        checkUpdate = true;
                    }

                    //check Description
                    if (job_Description.trim().length() < 20) {
                        errors.setDescriptionLenghtError("Description must at least 20 character");
                        checkUpdate = true;
                    }

                    //check Requirement
                    if (job_Requirement.trim().length() < 20) {
                        errors.setRequirementLenghtError("Requirement must at least 20 character");
                        checkUpdate = true;
                    }

                    //check Remuneration
                    if (remuneration.trim().length() < 20) {
                        errors.setRemunerationLenghtError("Requirement must at least 20 character");
                        checkUpdate = true;
                    }

                    //lay detail post
                    TblCompany_PostDAO companyPostDAO = new TblCompany_PostDAO();
                    TblCompany_PostDTO companyPostDTO = companyPostDAO.getCompanyPost(postID);

                    //set Company
                    companyPostDTO.setCompany(companyDTO);
                    request.setAttribute("COMPANY_POST_DETAIL", companyPostDTO);

                    //get list major
                    TblMajorDAO majorDAO = new TblMajorDAO();
                    majorDAO.getNameMajor();
                    List<TblMajorDTO> listNameMajor = majorDAO.getListNameMajor();
                    request.setAttribute("LIST_NAME_MAJOR", listNameMajor);

                    //DATE 
                    Date expDate = Date.valueOf(expirationDate);
                    if (checkUpdate) {
                        request.setAttribute("ERROR_UPDATE", errors);
                        RequestDispatcher rd = request.getRequestDispatcher(url);
                        rd.forward(request, response);
                    } else {
                        boolean resultUpdatePost = companyPostDAO.updateCompanyPostAsCompany(postID, title_Post, majorID, quantityIterns,
                                expDate, workLocation, job_Description, job_Requirement, remuneration, vacancy, school_confirm, statusPost);
                        if (resultUpdatePost) {
                            TblAccountDAO accountDAO = new TblAccountDAO();
                                TblAccountDTO systemAccount = accountDAO.GetAccountByRole(4);
                                TblAccountDTO universityAccount = accountDAO.getAccountSchool();
                                String link = "http://localhost:8080/OJTManagementSystem/AdminShowPostManagementController"; 
                                String subject = "Announcement of an updated company post";
                                String message = "Dear " + universityAccount.getName() + ",\n"
                                        + "\n"
                                        + "The OJT system wants to announce you know that " + companyPostDTO.getCompany().getAccount().getName()
                                        + " company has just changed the post information so you need to confirm this company's post"
                                        + " that the title is " + title_Post + ". "
                                        + "Please click on the link " + link
                                        + " so as not to miss any information.\n"
                                        + "\n"
                                        + "Regards,\n"
                                        + "The support OJT team";
                                MyApplicationHelper.sendEmail(universityAccount, systemAccount, message, subject);
                            url = properties.getProperty(MyApplicationConstants.CompanyFeatures.COMPANY_SHOW_POST_CONTROLLER);
                            response.sendRedirect(url);
                        } else {
                            RequestDispatcher rd = request.getRequestDispatcher(url);
                            rd.forward(request, response);
                        }
                    }

                } //if conpany exist
                else {
                    response.sendRedirect(url);
                } //if company not exist
            }//if session exist
            else {
                response.sendRedirect(url);
            }//if session not exist
        } catch (IllegalArgumentException ex) {
            log("IllegalArgumentException at UpdateStudentProfileServlet " + ex.getMessage());
            errors.setExpirationDateEmptyError("Please, enter post's expiration");
            request.setAttribute("ERROR_UPDATE", errors);
            url = properties.getProperty(MyApplicationConstants.CompanyFeatures.COMPANY_POST_EDIT_PAGE);
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
        } catch (NamingException ex) {
            log("NamingException at CompanyUpdatePostServlet " + ex.getMessage());
        } catch (SQLException ex) {
            log("SQLException at CompanyUpdatePostServlet " + ex.getMessage());
        } catch (AddressException ex) {
            log("AddressException at CreateNewCompanyPostServlet " + ex.getMessage());
        } catch (MessagingException ex) {
            log("MessagingException at CreateNewCompanyPostServlet " + ex.getMessage());
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
