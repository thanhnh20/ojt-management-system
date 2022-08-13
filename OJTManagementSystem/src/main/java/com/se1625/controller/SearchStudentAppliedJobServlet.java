/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se1625.controller;

import com.se1625.tblapplication.TblApplicationDAO;
import com.se1625.tblapplication.TblApplicationDTO;
import com.se1625.tblcompany.TblCompanyDAO;
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
public class SearchStudentAppliedJobServlet extends HttpServlet {

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

        String jobName = request.getParameter("nameTypeJob");
        String companyName = request.getParameter("nameCompany");
        String nameLocation = request.getParameter("nameLocation");
        String statusName = request.getParameter("nameStatus");
        String xpage = request.getParameter("page");
        if (jobName != null) {
            jobName = jobName.trim();

        }
        if (companyName != null) {
            companyName = companyName.trim();
        }

        HttpSession session = request.getSession(false);

        int page;
        int numberRowsPerPage = 10;
        int start;
        int end;
        int sizeOfList;

        ServletContext context = this.getServletContext();
        Properties properties = (Properties) context.getAttribute("SITE_MAPS");
        String url = MyApplicationConstants.SearchStudentAppliedJobFeature.LOGIN_PAGE;
        try {
            if (session != null) {
                TblStudentDTO student = (TblStudentDTO) session.getAttribute("STUDENT_ROLE");
                TblApplicationDAO applicationDAO = new TblApplicationDAO();
                List<TblApplicationDTO> listApplication = null;
                if (student != null) {
                    if ("".equals(jobName) && "".equals(companyName)
                            && nameLocation.trim().isEmpty() && statusName.isEmpty()) {
                        url = properties.getProperty(MyApplicationConstants.SearchStudentAppliedJobFeature.STUDENT_APPLIED_JOB_PAGE);
                        RequestDispatcher rd = request.getRequestDispatcher(url);
                        rd.forward(request, response);
                    } else {
                        url = properties.getProperty(MyApplicationConstants.SearchStudentAppliedJobFeature.STUDENT_APPLIED_JOBS_PAGE);
                        listApplication = applicationDAO.getApplicationByFilter(student, companyName,
                                jobName, nameLocation, statusName);
                        if (listApplication != null) {
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

                            List<TblApplicationDTO> listAppliedJobPerPage = applicationDAO.
                                    getListByPage(listApplication, start, end);
                            TblCompanyDAO companyDAO = new TblCompanyDAO();
                            List<String> listCompanyNameAppliedAsStudent = companyDAO.getListCompanyNameAppliedAsStudent(student.getStudentCode(), student.getSemester().getSemesterID());
                            request.setAttribute("List_COMPANY_NAME", listCompanyNameAppliedAsStudent);
                            request.setAttribute("LIST_APPLIED_JOB_RESULT", listAppliedJobPerPage);
                            request.setAttribute("page", page);
                            request.setAttribute("numberPage", numberPage);
                        } else {
                            url = properties.getProperty(MyApplicationConstants.SearchStudentAppliedJobFeature.STUDENT_APPLIED_JOBS_PAGE);
                            sizeOfList = 0;
                        }
                        request.setAttribute("SIZE_OF_LIST", sizeOfList);
                        RequestDispatcher rd = request.getRequestDispatcher(url);
                        rd.forward(request, response);
                    }
                }// if student is created 
                else {
                    response.sendRedirect(url);
                }// if student is not created 
            }// if session exist
            else {
                response.sendRedirect(url);
            }// if session does not exist
        } catch (SQLException ex) {
            log("SQLException at SearchStudentAppliedJobServlet " + ex.getMessage());
        } catch (NamingException ex) {
            log("NamingException at SearchStudentAppliedJobServlet " + ex.getMessage());
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
