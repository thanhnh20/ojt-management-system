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
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * @author Thanh Huy
 */
public class AdminShowInternApplicationServlet extends HttpServlet {

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

        String url = MyApplicationConstants.ShowStudentHomeFeature.LOGIN_PAGE;
        String studentID = request.getParameter("txtStudentID");
        String companyID = request.getParameter("txtCompanyID");
        String titleJob = request.getParameter("txtTitleJob");
        String schoolStatus = request.getParameter("txtSchoolStatus");
        String xpage = request.getParameter("page");

        int page;
        int numberRowsPerPage = 10;
        int start;
        int end;
        int sizeOfList;
        HttpSession session = request.getSession(false);
        try {
            if (session != null) {
                TblAccountDTO account = (TblAccountDTO) session.getAttribute("ADMIN_ROLE");
                if (account != null) {
                    url = properties.getProperty(MyApplicationConstants.AdminInternApplication.ADMIN_SHOW_INTERN_APPLICATION_JSP);

                     TblSemesterDAO semesterDAO = new TblSemesterDAO();
                    TblSemesterDTO currentSemester = semesterDAO.getCurrentSemester();
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
                    
                    List<TblSemesterDTO> listSemester = semesterDAO.getListSemester();
                    
                    request.setAttribute("CURRENT_SEMESTER", currentSemester);
                    request.setAttribute("LIST_SEMESTER", listSemester);
                    
                    TblApplicationDAO applDAO = new TblApplicationDAO();
                    List<TblApplicationDTO> listApplicationByFilter = new ArrayList<>();
                    listApplicationByFilter = applDAO.getApplicationByFilterInAdminIternAppl(studentID, companyID, titleJob, schoolStatus,currentSemester.getSemesterID());

                    if (listApplicationByFilter != null) {
                        sizeOfList = listApplicationByFilter.size();
                        
                        if (xpage == null || "".equals(xpage)) {
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

                        List<TblApplicationDTO> listAppliedJobPerPage = applDAO.
                                getListByPage(listApplicationByFilter, start, end);

                        request.setAttribute("INTERN_APPLICATION", listAppliedJobPerPage);
                        request.setAttribute("SIZE_OF_LIST", sizeOfList);
                        request.setAttribute("page", page);
                        request.setAttribute("numberPage", numberPage);
                         
                    }else {
                        sizeOfList = 0;
                        request.setAttribute("SIZE_OF_LIST", sizeOfList);
                    }
                    //get name of companies
                        TblCompanyDAO companyDAO = new TblCompanyDAO();
                        companyDAO.getNameCompanies();
                        List<TblCompanyDTO> listNameCompany = companyDAO.getListNameCompany();
                        request.setAttribute("COMPANY_NAME", listNameCompany);
                        
                        RequestDispatcher rd = request.getRequestDispatcher(url);
                        rd.forward(request, response);
                } else{
                    response.sendRedirect(url);
                }
            }
            else response.sendRedirect(url);
        } catch (SQLException ex) {
            log("SQLException occurs AdminShowInternApplicationServlet " + ex.getMessage());
        } catch (NamingException ex) {
            log("NamingException occurs AdminShowInternApplicationServlet " + ex.getMessage());
        } catch (NumberFormatException ex) {
            log("NumberFormatException occurs AdminShowInternApplicationServlet " + ex.getMessage());
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
