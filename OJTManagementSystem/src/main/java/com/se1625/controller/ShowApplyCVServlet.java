/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se1625.controller;

import com.se1625.tblapplication.ApplyCVStudentError;
import com.se1625.tblapplication.TblApplicationDAO;
import com.se1625.tblapplication.TblApplicationDTO;
import com.se1625.tblcompany_post.CompanyPostDetailError;
import com.se1625.tblcompany_post.TblCompany_PostDAO;
import com.se1625.tblcompany_post.TblCompany_PostDTO;
import com.se1625.tblsemester.TblSemesterDAO;
import com.se1625.tblsemester.TblSemesterDTO;
import com.se1625.tblstudent.TblStudentDAO;
import com.se1625.tblstudent.TblStudentDTO;
import com.se1625.utils.MyApplicationConstants;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
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
public class ShowApplyCVServlet extends HttpServlet {

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

        String stringPostID = request.getParameter("postID");

        ServletContext context = this.getServletContext();
        Properties properties = (Properties) context.getAttribute("SITE_MAPS");

        String url = MyApplicationConstants.ShowApplyCVFeature.LOGIN_PAGE;
        HttpSession session = request.getSession(false);

        try {
            if (session != null) {
                TblStudentDTO student = (TblStudentDTO) session.getAttribute("STUDENT_ROLE");
                if (student != null) {
                    CompanyPostDetailError error = new CompanyPostDetailError();
                    boolean found = false;
                    if (stringPostID == null) {
                        stringPostID = (String) request.getAttribute("POST_ID");
                    }
                    String errorQuantity = (String) request.getAttribute("ERROR_RUN_OUT_QUANTITY_INTERNS_COMPANY_DETAILS");
                    if (errorQuantity != null) {
                        url = properties.getProperty(MyApplicationConstants.ShowApplyCVFeature.SHOW_JOB_DETAIL_COMPANY);
                        RequestDispatcher rd = request.getRequestDispatcher(url);
                        rd.forward(request, response);
                    } else {
                        int postID = Integer.parseInt(stringPostID);

                        //check student student completed
                        TblStudentDAO studentDAO = new TblStudentDAO();
                        TblStudentDTO studentDTO = studentDAO.getStudent(student.getStudentCode());

                        //check the student has applied this post yet
                        TblSemesterDAO semesterDAO = new TblSemesterDAO();
                        TblSemesterDTO semester = semesterDAO.getCurrentSemester();

                        TblCompany_PostDAO companyPostDAO = new TblCompany_PostDAO();
                        TblCompany_PostDTO companyPostDTO = companyPostDAO.searchPostByPostID(postID);

                        LocalDate timeDay = LocalDate.now();
                        DateTimeFormatter dayFormat
                                = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        // convert String to date type
                        java.util.Date currentDate = Date.valueOf(timeDay.format(dayFormat));
                        if (companyPostDTO.getExpirationDate().before(currentDate)) {
                            found = true;
                            error.setExpirationDateError("This post has expired.");
                        }

                        TblApplicationDAO applicationDAO = new TblApplicationDAO();
                        TblApplicationDTO application = applicationDAO.getApplication(student.getStudentCode(), postID, semester.getSemesterID());

                        if (application != null) {
                            if (application.isStudentConfirm() == true
                                    && application.getSchoolConfirm() == 1
                                    && application.getCompanyConfirm() == 1
                                    || application.isStudentConfirm() == true
                                    && application.getSchoolConfirm() == 1
                                    && application.getCompanyConfirm() == 0
                                    || application.isStudentConfirm() == true
                                    && application.getSchoolConfirm() == 0
                                    && application.getCompanyConfirm() == 0) {
                                found = true;
                                error.setAppliedTwoTimeError("You applied this company's Job before");
                            }
                        }

                        if (studentDTO.getIsIntern() == 2) {
                            found = true;
                            error.setStudentCompletedError("You have already completed your internship");
                        } else if (studentDTO.getIsIntern() == 1) {
                            found = true;
                            error.setAppliedJobStudentWorkingError("You joined the internship");
                        }

                        if (found == true) {
                            request.setAttribute("ERROR_COMPANY_POST", error);
                        }

                        //check student infor
                        Date checkBirthday = student.getBirthDay();
                        String checkAddress = student.getAddress();
                        String checkPhone = student.getPhone();
                        ApplyCVStudentError errors = new ApplyCVStudentError();
                        if (checkBirthday == null || checkAddress == null || checkPhone == null) {
                            errors.setStudentInformationError("Please enter all personal information first");
                            request.setAttribute("POST_ID", postID);
                            TblCompany_PostDAO postDAO = new TblCompany_PostDAO();
                            TblCompany_PostDTO companyPost = postDAO.getCompanyPost(postID);
                            request.setAttribute("POST_COMPANY_INFOR", companyPost);
                            request.setAttribute("ERRORS", errors);
                        }
                        TblCompany_PostDTO companyPost = companyPostDAO.getCompanyPost(postID);

                        request.setAttribute("POST_COMPANY_INFOR", companyPost);
                        url = properties.getProperty(MyApplicationConstants.ShowApplyCVFeature.APPLY_CV_PAGE_JSP);

                        //request.setAttribute("POST_COMPANY_INFOR", companyPost);
                        RequestDispatcher rd = request.getRequestDispatcher(url);
                        rd.forward(request, response);
                    }

                } //if student is created
                else {
                    response.sendRedirect(url);
                }
            } //if session exist
            else {
                response.sendRedirect(url);
            }
        } catch (SQLException ex) {
            log("SQLException at ShowApplyCVServlet " + ex.getMessage());
        } catch (NamingException ex) {
            log("NamingException at ShowApplyCVServlet " + ex.getMessage());
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
