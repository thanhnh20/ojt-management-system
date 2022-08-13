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
public class AdminViewPostDetailsServlet extends HttpServlet {

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

        //lay postID
        int postID = Integer.parseInt(request.getParameter("postID"));
        //lay trang
        String xpage = request.getParameter("page");
        ServletContext context = this.getServletContext();
        Properties properties = (Properties) context.getAttribute("SITE_MAPS");
        String url = MyApplicationConstants.LoginFeture.LOGIN_PAGE;

        HttpSession session = request.getSession(false);
        try {
            if (session != null) {
                //lay info admin
                TblAccountDTO admin = (TblAccountDTO) session.getAttribute("ADMIN_ROLE");
                if (admin != null) {
                    url = properties.getProperty(MyApplicationConstants.AdminShowPostManagementFeature.ADMIN_VIEW_POST_MANAGE_PAGE);
                    //lay detail post
                    TblCompany_PostDAO companyPostDAO = new TblCompany_PostDAO();
                    TblCompany_PostDTO companyPostDTO = companyPostDAO.getCompanyPost(postID);

                    //set Company
                    TblCompanyDTO companyDTO;
                    TblCompanyDAO companyDAO = new TblCompanyDAO();
                    companyDTO = companyDAO.searchCompanyByCompanyID(companyPostDTO.getCompany().getCompanyID());
                    companyPostDTO.setCompany(companyDTO);
                    request.setAttribute("COMPANY_POST_DETAIL", companyPostDTO);
                    request.setAttribute("page", xpage);
                    RequestDispatcher rd = request.getRequestDispatcher(url);
                    rd.forward(request, response);
                } // if admin exits
                else {
                    response.sendRedirect(url);
                }//if admin not exist
            } //if session exist
            else {
                response.sendRedirect(url);
            }//if not login
        } catch (NumberFormatException ex) {
            log("NumberFormatException at HomeShowCompanyDetailServlet " + ex.getMessage());
        } catch (SQLException ex) {
            log("SQLException at SearchCompanyStudentHomeServlet " + ex.getMessage());
        } catch (NamingException ex) {
            log("NamingException at SearchCompanyStudentHomeServlet " + ex.getMessage());
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
