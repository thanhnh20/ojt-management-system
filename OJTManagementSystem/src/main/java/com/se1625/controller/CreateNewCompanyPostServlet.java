/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se1625.controller;

import com.se1625.tblaccount.TblAccountDAO;
import com.se1625.tblaccount.TblAccountDTO;
import com.se1625.tblcompany.TblCompanyDAO;
import com.se1625.tblcompany.TblCompanyDTO;
import com.se1625.tblcompany_post.CreateNewCompanyPostError;
import com.se1625.tblcompany_post.TblCompany_PostDAO;
import com.se1625.tblmajor.TblMajorDAO;
import com.se1625.tblmajor.TblMajorDTO;
import com.se1625.tblsemester.TblSemesterDAO;
import com.se1625.tblsemester.TblSemesterDTO;
import com.se1625.tblstudent.TblStudentDTO;
import com.se1625.utils.MyApplicationConstants;
import com.se1625.utils.MyApplicationHelper;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
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
 * @author Thai Quoc Toan <toantqse151272@fpt.edu.vn>
 */
public class CreateNewCompanyPostServlet extends HttpServlet {

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
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);

        ServletContext context = this.getServletContext();
        Properties properties = (Properties) context.getAttribute("SITE_MAPS");
        String url = MyApplicationConstants.CreateNewCompanyPostFeature.LOGIN_PAGE;

        String tiltePost = request.getParameter("txtTitlePost");
        String vacancy = request.getParameter("txtVacancy");
        String txtMajorID = request.getParameter("txtMajor");
        int majorID = 0;
        int quantity = 0;
        Date expirationDate = null;
        Date currentDate = null;
        String txtQuantity = request.getParameter("txtQuantity");
        String txtExpirationDate = request.getParameter("txtExpirationDate");
        String txtWorkLocation = request.getParameter("txtWorkLocation");
        String txtJobDescription = request.getParameter("txtJobDescription");
        String txtRequirement = request.getParameter("txtRequirement");
        String txtRemuneration = request.getParameter("txtRemuneration");

        boolean foundError = false;
        CreateNewCompanyPostError errors = new CreateNewCompanyPostError();
        try {
            if (session != null) {
                TblAccountDTO companyAccount = (TblAccountDTO) session.getAttribute("COMPANY_ROLE");
                if (companyAccount != null) {
                    TblCompanyDAO companyDAO = new TblCompanyDAO();
                    TblCompanyDTO company = companyDAO.getCompanyInformation(companyAccount.getEmail());
                    request.setAttribute("COMPANY_ISSIGNED", company.isIs_Signed());
                    
                        TblMajorDAO majorDAO = new TblMajorDAO();
                        majorDAO.getNameMajor();
                        List<TblMajorDTO> listMajor = majorDAO.getListNameMajor();
                        request.setAttribute("LIST_MAJOR_NAME", listMajor);

                        if (tiltePost.trim().length() <= 5 || tiltePost.trim().length() > 100) {
                            foundError = true;
                            errors.setTitlePostEmptyError("Title post is required 6 to 100 characters!");
                        }
                        if (vacancy.trim().length() <= 5 || vacancy.trim().length() > 100) {
                            foundError = true;
                            errors.setVacancyEmptyError("Vacancy is required 6 to 100 characters!");
                        }
                        if (txtMajorID.equals("")) {
                            foundError = true;
                            errors.setMajorChooseError("Major cannot be left blank!");
                        } else {
                            majorID = Integer.parseInt(txtMajorID);
                        }

                        if (txtQuantity.trim().length() <= 0) {
                            foundError = true;
                            errors.setQuantityEmptyError("Quantity cannot be left blank!");
                        } else {
                            quantity = Integer.parseInt(txtQuantity);
                        }

                        if (txtExpirationDate.trim().isEmpty()) {
                            foundError = true;
                            errors.setExpirationdateIllegal("The Expiration date is invalid!");
                        } else {
                            LocalDate today = LocalDate.now();
                            DateTimeFormatter dayFormat
                                    = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                            expirationDate = Date.valueOf(txtExpirationDate);
                            currentDate = Date.valueOf(today.format(dayFormat));
                            if (expirationDate.before(currentDate)) {
                                foundError = true;
                                errors.setExpirationdateIllegal("The Expiration date is invalid!");
                            }
                        }

                        if (txtWorkLocation.trim().isEmpty()) {
                            foundError = true;
                            errors.setWorkLocationEmptyError("The work location cannot be left blank!");
                        }

                        if (txtJobDescription.trim().length() < 6 || txtJobDescription.trim().length() > 1500) {
                            foundError = true;
                            errors.setJobDescriptionEmptyError("The job description is required 6 to 1500 characters!");
                        }

                        if (txtRequirement.trim().length() < 6 || txtRequirement.trim().length() > 1500) {
                            foundError = true;
                            errors.setJobRequirementsEmptyError("The job requirement is required 6 to 1500 characters!");
                        }

                        if (txtRemuneration.trim().length() < 6 || txtRequirement.trim().length() > 1500) {
                            foundError = true;
                            errors.setRemunerationEmptyError("The job requirement is required 6 to 1500 characters!");
                        }
                        if (company.isIs_Signed() == false){
                            foundError = true;
                            errors.setCompanyNotSignedError("Company isn's signed before, so can't not create a new post!");
                        }
                        
                        if (foundError) {
                            request.setAttribute("ERRORS", errors);
                            url = properties.getProperty(MyApplicationConstants.CreateNewCompanyPostFeature.SHOW_CREATE_COMPANY_POST_PAGE);
                            RequestDispatcher rd = request.getRequestDispatcher(url);
                            rd.forward(request, response);
                        } else {
                            //create new company post
                            TblCompany_PostDAO companyPostDAO = new TblCompany_PostDAO();
                            boolean result = companyPostDAO.createNewCompanyPost(company.getCompanyID(), majorID,
                                    tiltePost, txtJobDescription, txtRequirement, txtRemuneration,
                                    txtWorkLocation, quantity, currentDate, expirationDate,
                                    1, vacancy);
                            if (result) {
                                TblAccountDAO accountDAO = new TblAccountDAO();
                                TblAccountDTO systemAccount = accountDAO.GetAccountByRole(4);
                                TblAccountDTO universityAccount = accountDAO.getAccountSchool();
                                String link = "http://localhost:8080/OJTManagementSystem/AdminShowPostManagementController"; 
                                String subject = "The new announcement of a new company post";
                                String message = "Dear " + universityAccount.getName() + ",\n"
                                        + "\n"
                                        + "The OJT system wants to announce you know that you have a new announcement about a post of " + companyAccount.getName() 
                                        + " company that the title is " + tiltePost + ". "
                                        + "Please click on the link " + link
                                        + " so as not to miss any information.\n"
                                        + "\n"
                                        + "Regards,\n"
                                        + "The support OJT team";
                                MyApplicationHelper.sendEmail(universityAccount, systemAccount, message, subject);
                                url = MyApplicationConstants.CreateNewCompanyPostFeature.SHOW_COMPANY_POST_MANAGER_CONTROLLER;
                                response.sendRedirect(url);
                            }
                        }
                } //if company is created
                else {
                    response.sendRedirect(url);
                }
            } // if session exist
            else {
                response.sendRedirect(url);
            }
        } catch (SQLException ex) {
            log("SQLException at CreateNewCompanyPostServlet " + ex.getMessage());
        } catch (NamingException ex) {
            log("NamingException at CreateNewCompanyPostServlet " + ex.getMessage());
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
