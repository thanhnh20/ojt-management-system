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
import com.se1625.tblmajor.TblMajorDAO;
import com.se1625.tblmajor.TblMajorDTO;
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
public class SearchCompanyStudentHomeServlet extends HttpServlet {

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
        request.setCharacterEncoding("UTF-8");

        String companyID = request.getParameter("nameCompany");
        String majorID = request.getParameter("nameMajor");
        String nameLocation = request.getParameter("nameLocation");

        ServletContext context = this.getServletContext();
        Properties properties = (Properties) context.getAttribute("SITE_MAPS");

        String url = MyApplicationConstants.SearchComanyStudentHomeFeature.LOGIN_PAGE;
        int page;
        int numberProductPage = 10;
        int start;
        int size;
        int end;
        HttpSession session = request.getSession(false);
        try {
            if (session != null) {
                TblStudentDTO student = (TblStudentDTO) session.getAttribute("STUDENT_ROLE");
                if (student != null) {
                    int idMajor =0;
                    if (majorID != null) {
                        if (majorID.isEmpty()) {
                            idMajor = 0;
                        } else {
                            idMajor = Integer.parseInt(majorID);
                        }
                    }
                    

                    List<TblCompany_PostDTO> listCompanyPostByFilter = null;
                    TblCompany_PostDAO companyPostDAO = new TblCompany_PostDAO();

                    if (companyID.isEmpty() == false || idMajor != 0
                            || nameLocation.isEmpty() == false) {
                        companyPostDAO.searchPostByFilter(companyID, idMajor, nameLocation);
                        listCompanyPostByFilter = companyPostDAO.getCompanyPostByFilter();
                    } //any parameter has value
                    else {
                        companyPostDAO.searchPostByFilter(companyID, idMajor, nameLocation);
                        listCompanyPostByFilter = companyPostDAO.getCompanyPostByFilter();
                    }//all parameter do not have value
                    if (listCompanyPostByFilter != null) {
                        size = listCompanyPostByFilter.size();
                        String xpage = request.getParameter("page");
                        if (xpage == null) {
                            page = 1;
                        } else {
                            page = Integer.parseInt(xpage);
                        }
                        int numberPage = size % numberProductPage;
                        if (numberPage == 0) {
                            numberPage = size / numberProductPage;
                        } else {
                            numberPage = (size / numberProductPage) + 1;
                        }
                        start = (page - 1) * numberProductPage;
                        end = Math.min(page * numberProductPage, size);
                        List<TblCompany_PostDTO> companyPostPerPage = companyPostDAO.
                                getListByPage(listCompanyPostByFilter, start, end);

                        request.setAttribute("LIST_RESULT", companyPostPerPage);
                        request.setAttribute("SIZE_OF_LIST", size);
                        request.setAttribute("page", page);
                        request.setAttribute("numberPage", numberPage);

                    } //if the list of post has value
                    else {
                        size = 0;
                        request.setAttribute("SIZE_OF_LIST", size);
                    }
                    TblCompanyDAO companyDAO = new TblCompanyDAO();
                    companyDAO.getNameCompanies();
                    List<TblCompanyDTO> listNameCompany = companyDAO.getListNameCompany();
                    request.setAttribute("COMPANY_NAME", listNameCompany);

                    TblMajorDAO majorDAO = new TblMajorDAO();
                    majorDAO.getNameMajor();
                    List<TblMajorDTO> listNameMajor = majorDAO.getListNameMajor();
                    request.setAttribute("LIST_NAME_MAJOR", listNameMajor);
                    
                    url = properties.getProperty(MyApplicationConstants.SearchComanyStudentHomeFeature.SEARCH_COMPANY_POST_PAGE);
                    RequestDispatcher rd = request.getRequestDispatcher(url);
                    rd.forward(request, response);
                } //if student is created
                else {
                    response.sendRedirect(url);
                }
            } //if session exist
            else {
                response.sendRedirect(url);
            }
        } catch (SQLException ex) {
            log("SQLException occurs at SearchCompanyStudentHomeServlet " + ex.getMessage());
        } catch (NamingException ex) {
            log("NamingException occurs at SearchCompanyStudentHomeServlet " + ex.getMessage());
        } catch (NumberFormatException ex) {
            log("NumberFormatException occurs at SearchCompanyStudentHomeServlet " + ex.getMessage());
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
