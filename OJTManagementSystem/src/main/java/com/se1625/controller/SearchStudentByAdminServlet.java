/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se1625.controller;

import com.se1625.tblaccount.TblAccountDTO;
import com.se1625.tblmajor.TblMajorDAO;
import com.se1625.tblmajor.TblMajorDTO;
import com.se1625.tblsemester.TblSemesterDAO;
import com.se1625.tblsemester.TblSemesterDTO;
import com.se1625.tblstudent.TblStudentDAO;
import com.se1625.tblstudent.TblStudentDTO;
import com.se1625.utils.MyApplicationConstants;
import java.io.IOException;
import java.io.PrintWriter;
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
public class SearchStudentByAdminServlet extends HttpServlet {

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

        String studentCode = request.getParameter("txtStudentCode");
        String stringCredit = request.getParameter("txtCredit");
        String major = request.getParameter("txtMajor");
        String stringIsIntern = request.getParameter("isIntern");
        int semesterID = Integer.parseInt(request.getParameter("semester"));
        int numberOfCredit = -1;
        int isIntern = -1;

        ServletContext context = this.getServletContext();
        Properties properties = (Properties) context.getAttribute("SITE_MAPS");

        String url = MyApplicationConstants.SearchStudentByAdminFeature.LOGIN_PAGE;

        HttpSession session = request.getSession(false);
        try {
            if (session != null) {
                TblAccountDTO admin = (TblAccountDTO) session.getAttribute("ADMIN_ROLE");
                if (admin != null) {
                    if ("".equals(stringCredit) == false) {
                        numberOfCredit = Integer.parseInt(stringCredit);
                    }
                    if ("".equals(stringIsIntern) == false) {
                        isIntern = Integer.parseInt(stringIsIntern);
                    }
                    TblSemesterDAO semesterDAO = new TblSemesterDAO();
                    TblSemesterDTO currentSemester = semesterDAO.getCurrentSemester();
                    TblSemesterDTO nowSemester = currentSemester;
                    TblStudentDAO studentDAO = new TblStudentDAO();
                    if ("".equals(studentCode) && "".equals(major)
                            && numberOfCredit == -1 && isIntern == -1 && semesterID == currentSemester.getSemesterID()) {
                        url = MyApplicationConstants.SearchStudentByAdminFeature.ADMIN_MANAGEMENT_STUDENT_CONTROLLER;
                        response.sendRedirect(url);
                    } else {
                        List<TblStudentDTO> listStudent = null;
                        if (semesterID == currentSemester.getSemesterID()) {
                            listStudent = studentDAO.
                                searchStudentByFilter(studentCode, major, numberOfCredit, isIntern, semesterID, 0);
                        } else {
                            listStudent = studentDAO.
                                searchStudentByFilter(studentCode, major, numberOfCredit, isIntern, semesterID, 1);
                        }
                        
                        if (listStudent != null) {
                            url = properties.getProperty(MyApplicationConstants.SearchStudentByAdminFeature.ADMIN_MANAGEMENT_STUDENT_CONTROLLER);
                            request.setAttribute("LIST_STUDENT_SEARCH", listStudent);
                            RequestDispatcher rd = request.getRequestDispatcher(url);
                            rd.forward(request, response);
                        } else {
                            if (request.getParameter("semester") != null) {
                                if (semesterID != currentSemester.getSemesterID()) {
                                    currentSemester.setSemesterID(semesterID);
                                }
                            }
                            TblMajorDAO majorDAO = new TblMajorDAO();
                            majorDAO.getNameMajor();
                            List<TblMajorDTO> listNameMajor = majorDAO.getListNameMajor();
                            request.setAttribute("LIST_NAME_MAJOR", listNameMajor);
                            //get list semester
                            List<TblSemesterDTO> listSemester = semesterDAO.getListSemester();
                            request.setAttribute("SERVLET_CONTEXT", context);
                            request.setAttribute("LIST_SEMESTER", listSemester);
                            request.setAttribute("CURRENT_SEMESTER", currentSemester);
                            request.setAttribute("NOW_SEMESTER", nowSemester);
                            request.setAttribute("LIST_APPLICATION_RESULT", listStudent);
                            request.setAttribute("SIZE_OF_LIST", 0);
                            url = properties.getProperty(MyApplicationConstants.SearchStudentByAdminFeature.ADMIN_MANAGEMENT_STUDENT_PAGE);
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
            log("SQLException at SearchStudentByAdminServlet " + ex.getMessage());
        } catch (NamingException ex) {
            log("NamingException at SearchStudentByAdminServlet " + ex.getMessage());
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
