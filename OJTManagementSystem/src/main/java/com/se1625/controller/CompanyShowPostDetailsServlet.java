/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se1625.controller;

import com.se1625.tblcompany.TblCompanyDTO;
import com.se1625.tblcompany_post.TblCompany_PostDAO;
import com.se1625.tblcompany_post.TblCompany_PostDTO;
import com.se1625.tblmajor.TblMajorDAO;
import com.se1625.tblmajor.TblMajorDTO;
import com.se1625.utils.MyApplicationConstants;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
 * @author ThanhTy
 */
public class CompanyShowPostDetailsServlet extends HttpServlet {

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
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        ServletContext context = this.getServletContext();
        Properties properties = (Properties) context.getAttribute("SITE_MAPS");
        String url = MyApplicationConstants.UpdateStudentProfileFeature.LOGIN_PAGE;

        //get session
        HttpSession session = request.getSession(false);
        try {
            if (session != null) {
                TblCompanyDTO companyDTO = (TblCompanyDTO) session.getAttribute("COMPANY_ROLE_INFO");
                if (companyDTO != null) {
                    if (companyDTO.isIs_Signed() == false) {
                        url = properties.getProperty(MyApplicationConstants.CompanyFeatures.COMPANY_SHOW_POST_CONTROLLER);
                        response.sendRedirect(url);
                    } else {
                        //lay postID
                        int postID = Integer.parseInt(request.getParameter("postID"));
                        url = properties.getProperty(MyApplicationConstants.CompanyFeatures.COMPANY_POST_EDIT_PAGE);

                        //lay detail post
                        TblCompany_PostDAO companyPostDAO = new TblCompany_PostDAO();
                        TblCompany_PostDTO companyPostDTO = companyPostDAO.getCompanyPost(postID);

                        //set Company
                        companyPostDTO.setCompany(companyDTO);
                        request.setAttribute("COMPANY_POST_DETAIL", companyPostDTO);
                        //get list major
                        TblMajorDAO majorDAO = new TblMajorDAO();
                        majorDAO.getNameMajor();
                        List<TblMajorDTO> listNameMajor = majorDAO.getListNameMajor();
                        request.setAttribute("LIST_NAME_MAJOR", listNameMajor);

                        RequestDispatcher rd = request.getRequestDispatcher(url);
                        rd.forward(request, response);
                    }

                } //if conpany exist
                else {
                    response.sendRedirect(url);
                } //if company not exist
            }//if session exist
            else {
                response.sendRedirect(url);
            }//if session not exist
        } catch (NamingException ex) {
            log("NamingException at CompanyUpdatePostServlet " + ex.getMessage());
        } catch (SQLException ex) {
            log("SQLException at CompanyUpdatePostServlet " + ex.getMessage());
        } catch (Exception ex) {
            log("Exception at CompanyUpdatePostServlet " + ex.getMessage());
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
