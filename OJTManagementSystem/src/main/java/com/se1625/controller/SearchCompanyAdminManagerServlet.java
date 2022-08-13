/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se1625.controller;

import com.se1625.tblaccount.TblAccountDAO;
import com.se1625.tblaccount.TblAccountDTO;
import com.se1625.tblcompany.TblCompanyDAO;
import com.se1625.tblcompany.TblCompanyDTO;
import com.se1625.utils.MyApplicationConstants;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
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
public class SearchCompanyAdminManagerServlet extends HttpServlet {

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

        String companyID = request.getParameter("selectCompany");
        String email = request.getParameter("txtEmail");
        String status = request.getParameter("selectStatus");

        ServletContext context = this.getServletContext();
        Properties prop = (Properties) context.getAttribute("SITE_MAPS");

        String url = MyApplicationConstants.SearchComanyStudentHomeFeature.LOGIN_PAGE;

        HttpSession session = request.getSession(false);
        int page;
        int numberProductPage = 10;
        int start;
        int size;
        int end;
        try {
            if (session != null) {
                TblAccountDTO adminDTO = (TblAccountDTO) session.getAttribute("ADMIN_ROLE");
                if (adminDTO != null) {
                    boolean is_Signed = false;
                    if ("Success".equals(status)) {
                        is_Signed = true;
                    }
                    TblCompanyDAO companydao = new TblCompanyDAO();
                    companydao.getAllCompany();
                    List<TblCompanyDTO> listAllCompany = companydao.getListAllCompany();
                    List<TblCompanyDTO> listSearch = new ArrayList<>();
                    for (TblCompanyDTO tblCompanyDTO : listAllCompany) {
                        boolean check = true;
                        if (companyID.isEmpty() == false && !(tblCompanyDTO.getCompanyID().equals(companyID))) {
                            check = false;
                        }
                        if (email.isEmpty() == false && !(tblCompanyDTO.getAccount().getEmail().contains(email))) {
                            check = false;
                        }
                        if (status.isEmpty() == false && !(tblCompanyDTO.isIs_Signed() == is_Signed)) {
                            check = false;
                        }
                        if (check) {
                            listSearch.add(tblCompanyDTO);
                        }
                    }

                    size = listSearch.size();
                    String xpage = request.getParameter("page");
                    if (xpage == null || xpage.isEmpty()) {
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

                    List<TblCompanyDTO> listCompanyByPage = companydao.getListByPage(listSearch, start, end);
                    request.setAttribute("LIST_ALL_COMPANY", listAllCompany);
                    request.setAttribute("LIST_COMPANY", listCompanyByPage);
                    request.setAttribute("SIZE_PAGE", size);
                    request.setAttribute("page", page);
                    request.setAttribute("numberPage", numberPage);
                    url = prop.getProperty(MyApplicationConstants.SearchCompanyAdminManagerFeature.ADMIN_COMPANY_MANAGER_PAGE);
                    RequestDispatcher rd = request.getRequestDispatcher(url);;
                    rd.forward(request, response);
                } else {
                    response.sendRedirect(url);
                }
            } else {
                response.sendRedirect(url);
            }
        } catch (NamingException ex) {
            log("SearchCompanyAdminManagerServlet_NamingException " + ex.getMessage());
        } catch (SQLException ex) {
            log("SearchCompanyAdminManagerServlet_SQLException " + ex.getMessage());
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
