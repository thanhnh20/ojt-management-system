/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se1625.controller;

import com.se1625.tblaccount.TblAccountDAO;
import com.se1625.tblaccount.TblAccountDTO;
import com.se1625.tblaccount.TblAccountError;
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
 * @author ASUS
 */
public class LoginAccountsServlet extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");

        //get parameters 
        String username = request.getParameter("txtEmail");
        String password = request.getParameter("txtPassword");
        ServletContext context = this.getServletContext();
        Properties siteMap = (Properties) context.getAttribute("SITE_MAPS");

        String url = siteMap.getProperty(
                MyApplicationConstants.LoginFeture.LOGIN_PAGE);

        HttpSession session = request.getSession();
        try {

            //check format input login
            TblAccountError error = new TblAccountError();
            boolean foundError = false;

            if (username.trim().length() == 0) {
                error.setUserEmailEmpty("Please enter your email!");
                foundError = true;
            }// check username is not empty
            else {
                String pattern = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
                if (username.trim().matches(pattern) == false) {
                    foundError = true;
                    error.setUserEmailFormatError("Your email is invalid format!");
                }
            }
            if (password.trim().length() == 0) {
                error.setUserPasswordEmpty("Please enter your password");
                foundError = true;
            }

            if (foundError) {
                request.setAttribute("ERROR", error);
                RequestDispatcher rd = request.getRequestDispatcher(url);
                rd.forward(request, response);
            } //throw error if error is found
            else {
                TblAccountDAO dao = new TblAccountDAO();
                boolean result = dao.checkLoginForCompanyAccount(username, password);
                if (result) {
                    TblAccountDTO account = dao.getAccount(username);
                    TblCompanyDAO companyDAO = new TblCompanyDAO();
                    TblCompanyDTO companyDTO = companyDAO.getCompanyByEmail(username);
                    session.setAttribute("COMPANY_ROLE", account);
                    url = MyApplicationConstants.LoginFeture.COMPANY_DASHBOARD_CONTROLLER;
                    session.setAttribute("COMPANY_ROLE_INFO", companyDTO);
                    //url = MyApplicationConstants.LoginFeture.COMPANY_DASHBOARD_PAGE;
                    response.sendRedirect(url);
                } //check username and password exist
                else {
                    error.setAccountError("Your email or password is invalid!");
                    request.setAttribute("ERROR", error);
                    RequestDispatcher rd = request.getRequestDispatcher(url);
                    rd.forward(request, response);
                }// if account don't exist throw error
            } // if don't have error

        } catch (NamingException ex) {
            log("LoginAccountServlet_NamingException " + ex.getMessage());
        } catch (SQLException ex) {
            log("LoginAccountServlet_SQLException " + ex.getMessage());
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
