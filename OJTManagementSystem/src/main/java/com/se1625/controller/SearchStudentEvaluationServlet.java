/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se1625.controller;

import com.se1625.tblaccount.TblAccountDTO;
import com.se1625.tblapplication.TblApplicationDAO;
import com.se1625.tblapplication.TblApplicationDTO;
import com.se1625.tblcompany.TblCompanyDAO;
import com.se1625.tblcompany.TblCompanyDTO;
import com.se1625.tblsemester.TblSemesterDAO;
import com.se1625.tblsemester.TblSemesterDTO;
import com.se1625.utils.MyApplicationConstants;
import java.io.IOException;
import java.sql.SQLException;
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

/**
 *
 * @author Thai Quoc Toan <toantqse151272@fpt.edu.vn>
 */
public class SearchStudentEvaluationServlet extends HttpServlet {

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

        HttpSession session = request.getSession(false);

        int semesterID = Integer.parseInt(request.getParameter("semester"));
        String studentCode = request.getParameter("studentCode");
        String companyID = request.getParameter("txtCompanyName");
        String stringGrade = request.getParameter("garde");
        float grade = -1;
        String isPassString = request.getParameter("isPass");

        ServletContext context = this.getServletContext();
        Properties properties = (Properties) context.getAttribute("SITE_MAPS");
        String url = MyApplicationConstants.SearchStudentEvaluationFeature.LOGIN_PAGE;
        try {
            if (session != null) {
                TblAccountDTO admin = (TblAccountDTO) session.getAttribute("ADMIN_ROLE");
                if (admin != null) {
                    if ("".equals(stringGrade.trim()) == false) {
                        grade = Float.parseFloat(stringGrade);
                    }
                    TblSemesterDAO semesterDAO = new TblSemesterDAO();
                    TblSemesterDTO currentSemester = semesterDAO.getCurrentSemester();

                    if (grade == -1 && "".equals(studentCode) && "".equals(isPassString) && "".equals(companyID)) {
                        url = properties.getProperty(MyApplicationConstants.SearchStudentEvaluationFeature.ADMIN_STUDENT_EVALUATION_CONTROLLER);
                        RequestDispatcher rd = request.getRequestDispatcher(url);
                        rd.forward(request, response);
                    } else {
                        TblApplicationDAO applicationDAO = new TblApplicationDAO();
                        List<TblApplicationDTO> listApplication = applicationDAO.
                                searchListStudentApplicationByFilter(semesterID, grade, studentCode, companyID, isPassString);
                        if (listApplication != null) {
                            url = properties.getProperty(MyApplicationConstants.SearchStudentEvaluationFeature.ADMIN_STUDENT_EVALUATION_CONTROLLER);
                            request.setAttribute("LIST_APPLICATION_SEARCH", listApplication);
                            RequestDispatcher rd = request.getRequestDispatcher(url);
                            rd.forward(request, response);
                        } else {
                            if (request.getParameter("semester") != null) {
                                if (semesterID != currentSemester.getSemesterID()) {
                                    currentSemester.setSemesterID(semesterID);
                                }
                            }
                            //get name of companies
                            TblCompanyDAO companyDAO = new TblCompanyDAO();
                            companyDAO.getNameCompanies();
                            List<TblCompanyDTO> listNameCompany = companyDAO.getListNameCompany();
                            request.setAttribute("COMPANY_NAME", listNameCompany);

                            //get list semester
                            List<TblSemesterDTO> listSemester = semesterDAO.getListSemester();
                            request.setAttribute("LIST_SEMESTER", listSemester);
                            request.setAttribute("CURRENT_SEMESTER", currentSemester);
                            request.setAttribute("LIST_APPLICATION_RESULT", listApplication);
                            request.setAttribute("SIZE_OF_LIST", 0);
                            url = properties.getProperty(MyApplicationConstants.SearchStudentEvaluationFeature.ADMIN_STUDENT_EVALUATION_PAGE);
                            RequestDispatcher rd = request.getRequestDispatcher(url);
                            rd.forward(request, response);
                        }
                    }
                } //if admin is created
                else {
                    response.sendRedirect(url);
                }
            } //if session exist
            else {
                response.sendRedirect(url);
            }
        } catch (SQLException ex) {
            log("SQLException at SearchStudentEvaluationServlet " + ex.getMessage());
        } catch (NamingException ex) {
            log("NamingException at SearchStudentEvaluationServlet " + ex.getMessage());
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
