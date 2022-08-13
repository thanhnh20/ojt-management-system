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
public class ShowStudentEvaluationServlet extends HttpServlet {

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

        String url = MyApplicationConstants.ShowStudentEvaluationFeature.LOGIN_PAGE;

        String xpage = request.getParameter("page");
        int page;
        int numberRowsPerPage = 10;
        int start;
        int end;
        int sizeOfList;

        HttpSession session = request.getSession(false);
        try {
            if (session != null) {
                TblAccountDTO admin = (TblAccountDTO) session.getAttribute("ADMIN_ROLE");
                if (admin != null) {
                    //get list result application
                    TblApplicationDAO applicationDAO = new TblApplicationDAO();
                    List<TblApplicationDTO> listApplication = null;
                            //= applicationDAO.getListStudentApplications();
                    //get current Semester
                    TblSemesterDAO semesterDAO = new TblSemesterDAO();
                    TblSemesterDTO currentSemester = semesterDAO.getCurrentSemester();
                    if (request.getParameter("semester") != null) {
                        int semesterID = Integer.parseInt(request.getParameter("semester"));
                        if (semesterID != currentSemester.getSemesterID()) {
                            currentSemester.setSemesterID(semesterID);
                        }
                    }

                    //get list semester
                    List<TblSemesterDTO> listSemester = semesterDAO.getListSemester();
                    listApplication = (List<TblApplicationDTO>) request.getAttribute("LIST_APPLICATION_SEARCH");
                    if (listApplication == null) {
                        ///get list application
                        listApplication = applicationDAO.getListStudentApplications(currentSemester.getSemesterID());
                    }
                    if (listApplication != null) {
                        url = properties.getProperty(MyApplicationConstants.ShowStudentEvaluationFeature.ADMIN_EVALUATION_PAGE);
                        sizeOfList = listApplication.size();
                        
                        if (xpage == null) {
                            page = 1;
                        } // load page save job 
                        else {
                            page = Integer.parseInt(xpage);
                        } // when choose number of page

                        int numberPage = sizeOfList % numberRowsPerPage;

                        if (numberPage == 0) {
                            numberPage = sizeOfList / numberRowsPerPage;
                        } else {
                            numberPage = (sizeOfList / numberRowsPerPage) + 1;
                        }
                        start = (page - 1) * numberRowsPerPage;
                        end = Math.min(page * numberRowsPerPage, sizeOfList);

                        //get name of companies
                        TblCompanyDAO companyDAO = new TblCompanyDAO();
                        companyDAO.getNameCompanies();
                        List<TblCompanyDTO> listNameCompany = companyDAO.getListNameCompany();
                        request.setAttribute("COMPANY_NAME", listNameCompany);

                        List<TblApplicationDTO> listApplicationByPage = applicationDAO.
                                getListByPage(listApplication, start, end);
                        request.setAttribute("CURRENT_SEMESTER", currentSemester);
                        request.setAttribute("LIST_SEMESTER", listSemester);
                        request.setAttribute("LIST_APPLICATION_RESULT", listApplicationByPage);
                        request.setAttribute("SIZE_OF_LIST", sizeOfList);
                        request.setAttribute("page", page);
                        request.setAttribute("numberPage", numberPage);
                        RequestDispatcher rd = request.getRequestDispatcher(url);
                        rd.forward(request, response);
                    } else {
                        if (request.getParameter("semester") != null) {
                            int semesterID = Integer.parseInt(request.getParameter("semester"));
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
                        request.setAttribute("LIST_SEMESTER", listSemester);
                        request.setAttribute("CURRENT_SEMESTER", currentSemester);
                        request.setAttribute("LIST_APPLICATION_RESULT", listApplication);
                        request.setAttribute("SIZE_OF_LIST", 0);
                        url = properties.getProperty(MyApplicationConstants.ShowStudentEvaluationFeature.ADMIN_EVALUATION_PAGE);
                        RequestDispatcher rd = request.getRequestDispatcher(url);
                        rd.forward(request, response);
                    }

                }// admin is created
                else {
                    response.sendRedirect(url);
                }
            } //if session exist
            else {
                response.sendRedirect(url);
            }
        } catch (SQLException ex) {
            log("SQLException at ShowStudentEvaluationServlet " + ex.getMessage());
            ex.printStackTrace();
        } catch (NamingException ex) {
            log("NamingException at ShowStudentEvaluationServlet " + ex.getMessage());
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
