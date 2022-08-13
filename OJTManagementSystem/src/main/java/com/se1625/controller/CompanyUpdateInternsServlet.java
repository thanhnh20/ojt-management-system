/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se1625.controller;

import com.se1625.tblaccount.TblAccountDAO;
import com.se1625.tblaccount.TblAccountDTO;
import com.se1625.tblapplication.TblApplicationDAO;
import com.se1625.tblapplication.TblApplicationError;
import com.se1625.tblcompany.TblCompanyDAO;
import com.se1625.tblcompany_post.TblCompany_PostDAO;
import com.se1625.tblcompany_post.TblCompany_PostDTO;
import com.se1625.tblcompany.TblCompanyDTO;
import com.se1625.tblstudent.TblStudentDAO;
import com.se1625.tblstudent.TblStudentDTO;
import com.se1625.utils.MyApplicationConstants;
import com.se1625.utils.MyApplicationHelper;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
 * @author ASUS
 */
public class CompanyUpdateInternsServlet extends HttpServlet {

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

        String studentCode = request.getParameter("studentCode");
        String txtMark = request.getParameter("txtMark");
        String txtEvaluation = request.getParameter("txtEvaluation");
        String txtPostID = request.getParameter("txtPostID");

        ServletContext context = this.getServletContext();
        Properties prop = (Properties) context.getAttribute("SITE_MAPS");
        String url = MyApplicationConstants.CompanyUpdateInternsFeature.LOGIN_PAGE;

        HttpSession session = request.getSession(false);
        TblApplicationError error = new TblApplicationError();
        boolean checkError = false;
        try {
            if (session != null) {
                TblCompanyDTO companyDTO = (TblCompanyDTO) session.getAttribute("COMPANY_ROLE_INFO");
                if (companyDTO != null) {
                    float mark = 0;
                    if (txtMark.isEmpty()) {
                        error.setErrorEmptyMark("Please enter mark");
                        checkError = true;
                    } else {
                        mark = Float.parseFloat(txtMark);
                    }

                    if (checkError) {
                        request.setAttribute("ERROR_MARK", error);
                        url = prop.getProperty(MyApplicationConstants.CompanyUpdateInternsFeature.COMPANY_SEARCH_INTERNS_MANAGEMENT_CONTROLLER);
                        RequestDispatcher rd = request.getRequestDispatcher(url);
                        rd.forward(request, response);
                    } else {
                        if (mark < 0 || mark > 10) {
                            error.setErrorInputInvalidMark("Please enter mark 0-10");
                            checkError = true;
                        }
                        if (checkError) {
                            request.setAttribute("ERROR_MARK", error);
                            url = prop.getProperty(MyApplicationConstants.CompanyUpdateInternsFeature.COMPANY_SEARCH_INTERNS_MANAGEMENT_CONTROLLER);
                            RequestDispatcher rd = request.getRequestDispatcher(url);
                            rd.forward(request, response);
                        } else {
                            float grade = Float.parseFloat(txtMark);
                            int postID = Integer.parseInt(txtPostID);
                            TblApplicationDAO applicationDAO = new TblApplicationDAO();
                            int isPass = 1;
                            if (grade < 5) {
                                isPass = -1;
                            }

                            boolean result = applicationDAO.updateGradeAndEvaluation(studentCode, postID, grade, txtEvaluation, isPass);
                            if (result) {
                                TblStudentDAO studentDAO = new TblStudentDAO();
                                TblStudentDTO student = studentDAO.getStudentInfor(studentCode);
                                if (isPass == 1) {
                                    //cập nhật số tín chỉ khi pass + 10 tín chỉ
                                    studentDAO.updateCreditOfStudent(studentCode, student.getNumberOfCredit() + 10);
                                }
                                studentDAO.updateStatusInternOfStudent(studentCode, 2);
                                TblCompany_PostDAO postCompanyDAO = new TblCompany_PostDAO();
                                TblCompany_PostDTO companyPost = postCompanyDAO.getCompanyPost(postID);
                                TblAccountDAO accountDAO = new TblAccountDAO();
                                TblAccountDTO systemAccount = accountDAO.GetAccountByRole(4);
                                String courseStatus = "";
                                if (isPass == 1) {
                                    courseStatus = "Passed";
                                } else {
                                    courseStatus = "Not Pass";
                                }
                                String link = "http://localhost:8080/OJTManagementSystem/ShowStudentEvaluationController";
                                String subject = "The result of the internship";
                                String message = "Dear " + student.getAccount().getName() + ",\n"
                                        + "\n"
                                        + "The OJT system wants to announce that the company " + companyPost.getCompany().getAccount().getName()
                                        + " evaluated your internship. The result of your internship with the grade: " + grade
                                        + " and course status: " + courseStatus + ". "
                                        + "Please click on the link " + link
                                        + " so as not to miss any information.\n"
                                        + "\n"
                                        + "Regards,\n"
                                        + "The support OJT team";
                                MyApplicationHelper.sendEmail(student.getAccount(), systemAccount, message, subject);

                                String fullName = request.getParameter("txtFullName");
                                String email = request.getParameter("txtEmail");
                                String companyPostIDString = request.getParameter("selectCompanyPost");
                                String selectStatus = request.getParameter("status");
                                String xpage = request.getParameter("page");
                                url = prop.getProperty(MyApplicationConstants.CompanyUpdateInternsFeature.COMPANY_SEARCH_INTERNS_MANAGEMENT_CONTROLLER)
                                        + "?txtFullName=" + fullName
                                        + "&txtEmail=" + email
                                        + "&selectCompanyPost=" + companyPostIDString
                                        + "&status=" + selectStatus
                                        + "&page=" + xpage;
                                response.sendRedirect(url);
                            }
                        }
                    }
                } else {
                    response.sendRedirect(url);
                }
            } else {
                response.sendRedirect(url);
            }
        } catch (NamingException ex) {
            log("NamingException at CompanyUpdateInternsServlet " + ex.getMessage());
        } catch (SQLException ex) {
            log("SQLException at CompanyUpdateInternsServlet" + ex.getMessage());
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
