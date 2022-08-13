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
public class ShowAdminStudentManagementServlet extends HttpServlet {

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
        response.setCharacterEncoding("UTF8");
        request.setCharacterEncoding("UTF-8");

        String xpage = request.getParameter("page");

        ServletContext context = this.getServletContext();
        Properties properties = (Properties) context.getAttribute("SITE_MAPS");
        String url = MyApplicationConstants.ShowAdminStudentManagementFeature.LOGIN_PAGE;

        HttpSession session = request.getSession(false);
        int page;
        int numberRowsPerPage = 10;
        int start;
        int end;
        int sizeOfList;
        List<TblStudentDTO> listStudent = null;
        try {
            if (session != null) {
                TblAccountDTO admin = (TblAccountDTO) session.getAttribute("ADMIN_ROLE");
                TblStudentDAO studentDAO = new TblStudentDAO();
                if (admin != null) {
                    //get current Semester
                    TblSemesterDAO semesterDAO = new TblSemesterDAO();
                    TblSemesterDTO currentSemester = semesterDAO.getCurrentSemester();
                    TblSemesterDTO nowSemester = semesterDAO.getSemesterByID(currentSemester.getSemesterID());
                    if (request.getParameter("semester") != null) {
                        int semesterID = Integer.parseInt(request.getParameter("semester"));
                        if (xpage == null) {
                            if (semesterID != currentSemester.getSemesterID()) {
                                currentSemester.setSemesterID(semesterID);
                            }
                        } else {
                            currentSemester = semesterDAO.getSemesterByID(semesterID);
                        }
                    }
                    //get list semester
                    List<TblSemesterDTO> listSemester = semesterDAO.getListSemester();
                    listStudent = (List<TblStudentDTO>) request.getAttribute("LIST_STUDENT_SEARCH");
                    if (listStudent == null) {
                        ///get list student
                        listStudent = studentDAO.getListStudent(currentSemester.getSemesterID());
                    }
                    //phân trang
                    url = properties.getProperty(MyApplicationConstants.ShowAdminStudentManagementFeature.ADMIN_MANAGEMENT_STUDENT_PAGE);
                    if (listStudent != null) {
                        sizeOfList = listStudent.size();

                        if (xpage == null) {
                            page = 1;
                        } // load page save job 
                        else {
                            page = Integer.parseInt(xpage);
                        } // when choose number of page
                        String is_Disable = request.getParameter("isDisabled");
                        boolean isDisable = Boolean.parseBoolean(is_Disable);
                        if (isDisable) {
                            page = 1;
                        }
                        int numberPage = sizeOfList % numberRowsPerPage;

                        if (numberPage == 0) {
                            numberPage = sizeOfList / numberRowsPerPage;
                        } else {
                            numberPage = (sizeOfList / numberRowsPerPage) + 1;
                        }
                        start = (page - 1) * numberRowsPerPage;
                        end = Math.min(page * numberRowsPerPage, sizeOfList);

                        List<TblStudentDTO> listApplicationPage = studentDAO.
                                getListByPage(listStudent, start, end);
                        request.setAttribute("LIST_APPLICATION_RESULT", listApplicationPage);
                        request.setAttribute("SIZE_OF_LIST", sizeOfList);
                        request.setAttribute("page", page);
                        request.setAttribute("numberPage", numberPage);
                    }
                    request.setAttribute("SERVLET_CONTEXT", context);
                    request.setAttribute("CURRENT_SEMESTER", currentSemester);
                    request.setAttribute("NOW_SEMESTER", nowSemester);
                    request.setAttribute("LIST_SEMESTER", listSemester);
                    TblMajorDAO majorDAO = new TblMajorDAO();
                    majorDAO.getNameMajor();
                    List<TblMajorDTO> listNameMajor = majorDAO.getListNameMajor();
                    request.setAttribute("LIST_NAME_MAJOR", listNameMajor);
                    RequestDispatcher rd = request.getRequestDispatcher(url);
                    rd.forward(request, response);
                }//if admin is created
                else {
                    response.sendRedirect(url);
                }
            }//if session exist
            else {
                response.sendRedirect(url);
            }
        } catch (SQLException ex) {
            log("SQLException at ShowAdminStudentManagementServlet " + ex.getMessage());
        } catch (NamingException ex) {
            log("NamingException at ShowAdminStudentManagementServlet " + ex.getMessage());
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
