/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se1625.controller;

import com.se1625.tblaccount.TblAccountDTO;
import com.se1625.tblcompany.TblCompanyDAO;
import com.se1625.tblcompany.TblCompanyDTO;
import com.se1625.tblcompany_post.TblCompany_PostDAO;
import com.se1625.tblcompany_post.TblCompany_PostDTO;
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
public class ShowCompanyDashBoardServlet extends HttpServlet {

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

        ServletContext context = this.getServletContext();
        Properties properties = (Properties) context.getAttribute("SITE_MAPS");

        String url = MyApplicationConstants.ShowCompanyDashBoardFeature.LOGIN_PAGE;
        int numberOfActiveJobs = 0;
        int numberOfInactiveJobs = 0;
        int numberOfPendingJobs = 0;
        int numberOfExpiredJobs = 0;
        try {
            if (session != null) {
                TblAccountDTO accountCompany = (TblAccountDTO) session.getAttribute("COMPANY_ROLE");
                if (accountCompany != null) {
                    //get information of company
                    TblCompanyDAO companyDAO = new TblCompanyDAO();
                    TblCompanyDTO company = companyDAO.getCompanyInformation(accountCompany.getEmail());
                    //get all of types job
                    TblCompany_PostDAO companyPostDAO = new TblCompany_PostDAO();
                    //1. get active job
                    List<TblCompany_PostDTO> listActiveJob = companyPostDAO.getTypesPost("Active", company.getCompanyID());
                    if (listActiveJob != null) {
                        numberOfActiveJobs = listActiveJob.size();
                    }
                    //2. get pendingJob 
                    List<TblCompany_PostDTO> listPendingJob = companyPostDAO.getTypesPost("Pending", company.getCompanyID());
                    if (listPendingJob != null) {
                        numberOfPendingJobs = listPendingJob.size();
                    }
                    //3. get inactive job
                    List<TblCompany_PostDTO> listInactiveJob = companyPostDAO.getTypesPost("Inactive", company.getCompanyID());
                    if (listInactiveJob != null) {
                        numberOfInactiveJobs = listInactiveJob.size();
                    }
                    //4. get expired Job
                    List<TblCompany_PostDTO> listExpiredJob = companyPostDAO.getTypesPost("Expired", company.getCompanyID());
                    if (listExpiredJob != null) {
                        numberOfExpiredJobs = listExpiredJob.size();
                    }
                    
                    request.setAttribute("NUMBER_OF_ACTIVE_JOBS", numberOfActiveJobs);
                    request.setAttribute("LIST_ACTIVE_JOBS", listActiveJob);
                    request.setAttribute("NUMBER_OF_INACTIVE_JOBS", numberOfInactiveJobs);
                    request.setAttribute("NUMBER_OF_PENDING_JOBS", numberOfPendingJobs);
                    request.setAttribute("NUMBER_OF_EXPIRED_JOBS", numberOfExpiredJobs);
                    request.setAttribute("COMPANY_INFORMATION", company);
                    
                    url = properties.getProperty(MyApplicationConstants.ShowCompanyDashBoardFeature.COMPANY_DASHBOARD_PAGE);
                    RequestDispatcher rd = request.getRequestDispatcher(url);
                    rd.forward(request, response);
                } //if account company is created
                else {
                    response.sendRedirect(url);
                } //if account company is not created
            } //if session exist
            else {
                response.sendRedirect(url);
            } // if session does not exist
        } catch (SQLException ex) {
            log("SQLException at ShowCompanyDashBoardServlet " + ex.getMessage());
        } catch (NamingException ex) {
            log("NamingException at ShowCompanyDashBoardServlet " + ex.getMessage());
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
