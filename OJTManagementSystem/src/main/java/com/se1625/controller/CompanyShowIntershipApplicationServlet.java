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
import com.se1625.tblcompany_post.TblCompany_PostDAO;
import com.se1625.tblcompany_post.TblCompany_PostDTO;
import com.se1625.tblsemester.TblSemesterDAO;
import com.se1625.tblsemester.TblSemesterDTO;
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
 * @author ASUS
 */
public class CompanyShowIntershipApplicationServlet extends HttpServlet {

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

        ServletContext context = this.getServletContext();
        Properties prop = (Properties) context.getAttribute("SITE_MAPS");
        String url = MyApplicationConstants.CompanyShowIntershipApplicationFeature.LOGIN_PAGE;

        //get session
        HttpSession session = request.getSession(false);
        int page;
        int numberProductPage = 10;
        int start;
        int size;
        int end;
        int numberPage;
        try {
            if (session != null) {
                //get account
                TblAccountDTO accountDTO = (TblAccountDTO) session.getAttribute("COMPANY_ROLE");
                if (accountDTO != null) {
                    //get current Semester
                    TblSemesterDAO semesterDAO = new TblSemesterDAO();
                    TblSemesterDTO semesterDTO = semesterDAO.getCurrentSemester();
                    int currentSemester = semesterDTO.getSemesterID();
                    //get application of company
                    TblApplicationDAO applicationDAO = new TblApplicationDAO();
                    List<TblApplicationDTO> applicationList = applicationDAO.getApplicationByEmail(accountDTO.getEmail(), -3, currentSemester);
                    // set list application by page
                    List<TblApplicationDTO> listApplicationByPage;
                    if (applicationList == null) {
                        size = 0;
                        listApplicationByPage = applicationList;
                        page = 0;
                        numberPage = 0;
                    } else {
                        size = applicationList.size();

                        String xpage = request.getParameter("page");
                        if (xpage == null || xpage.isEmpty()) {
                            page = 1;
                        } else {
                            page = Integer.parseInt(xpage);
                        }
                        numberPage = size % numberProductPage;
                        if (numberPage == 0) {
                            numberPage = size / numberProductPage;
                        } else {
                            numberPage = (size / numberProductPage) + 1;
                        }
                        start = (page - 1) * numberProductPage;
                        end = Math.min(page * numberProductPage, size);

                        listApplicationByPage = applicationDAO.getListByPage(applicationList, start, end);
                    }
                    //get CompanyID
                    TblCompanyDAO companyDAO = new TblCompanyDAO();
                    String companyID = companyDAO.getCompanyByEmail(accountDTO.getEmail()).getCompanyID();

                    //get All Post of company        
                    TblCompany_PostDAO companyPostDAO = new TblCompany_PostDAO();
                    List<TblCompany_PostDTO> listCompanyPost = companyPostDAO.getAllPostByCompanyID(companyID);

                    request.setAttribute("LIST_COMPANY_POST", listCompanyPost);
//                    request.setAttribute("APPLICATION_LIST", applicationList);
                    request.setAttribute("APPLICATION_LIST_BYPAGE", listApplicationByPage);
                    request.setAttribute("SIZE_PAGE", size);
                    request.setAttribute("PAGE", page);
                    request.setAttribute("NUMBER_PAGE", numberPage);

                    url = prop.getProperty(
                            MyApplicationConstants.CompanyShowIntershipApplicationFeature.COMPANY_APPLICATION_MANAGER_PAGE);
                    RequestDispatcher rd = request.getRequestDispatcher(url);
                    rd.forward(request, response);
                } else {
                    response.sendRedirect(url);
                }
            } else {
                response.sendRedirect(url);
            }
        } catch (NamingException ex) {
            log("NamingException at CompanyShowIntershipApplicationServlet " + ex.getMessage());
        } catch (SQLException ex) {
            log("SQLException at CompanyShowIntershipApplicationServlet " + ex.getMessage());
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
