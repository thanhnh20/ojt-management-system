/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se1625.controller;

import com.se1625.tblaccount.TblAccountDTO;
import com.se1625.tblcompany.TblCompanyDAO;
import com.se1625.tblcompany.TblCompanyDTO;
import com.se1625.utils.MyApplicationConstants;
import java.io.IOException;
import java.sql.SQLException;
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
 * @author ThanhTy
 */
public class CompanyShowProfileServlet extends HttpServlet {

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
        String url = MyApplicationConstants.ShowStudentProfileFeature.LOGIN_PAGE;
        //get session
        HttpSession session = request.getSession(false);
        try {

            if (session != null) {
                TblAccountDTO company = (TblAccountDTO) session.getAttribute("COMPANY_ROLE");
                if (company != null) {
                    TblCompanyDAO companyDAO = new TblCompanyDAO();
                    TblCompanyDTO companyDTO = companyDAO.getCompanyByEmail(company.getEmail());

                    if (companyDTO != null) {
                        request.setAttribute("COMPANY_PROFILE", companyDTO);
                        url = properties.getProperty(MyApplicationConstants.CompanyFeatures.COMPANY_PROFILE_PAGE_JSP);
                        RequestDispatcher rd = request.getRequestDispatcher(url);
                        rd.forward(request, response);
                    }//if company exist
                    else {
                        response.sendRedirect(url);
                    }// if company not exist
                } //if company session exist
                else {
                    response.sendRedirect(url);
                } // if session company not exist

            }//if session exist
            else {
                response.sendRedirect(url);
            }//if session not exist
        } catch (NamingException ex) {
            log("CompanyShowProfileServlet_NamingException at CompanyShowProfileServlet " + ex.getMessage());
        } catch (SQLException ex) {
            log("CompanyShowProfileServlet_SQLException at CompanyShowProfileServlet " + ex.getMessage());
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
