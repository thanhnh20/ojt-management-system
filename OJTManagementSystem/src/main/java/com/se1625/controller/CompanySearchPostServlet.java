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
 * @author ThanhTy
 */
public class CompanySearchPostServlet extends HttpServlet {

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

        //get Paramente
        String tittle_Post = request.getParameter("title_Post");
        String majorID = request.getParameter("nameMajor");
        String nameStatus = request.getParameter("nameStatus");
        String companyID = request.getParameter("companyID");
        String xpage = request.getParameter("page");
        //Phan trang
        int page = 0;
        int numberRowsPerPage = 10;
        int start = 0;
        int end = 0;
        int sizeOfList = 0;
        int numberPage = 0;
        //URL
        ServletContext context = this.getServletContext();
        Properties properties = (Properties) context.getAttribute("SITE_MAPS");
        String url = MyApplicationConstants.CompanyFeatures.LOGIN_PAGE;
        //Lay session
        HttpSession session = request.getSession(false);
        try {
            if (session != null) {
                //get CompanyoPost
                TblCompany_PostDAO companyPostDAO = new TblCompany_PostDAO();

                //get company info
                TblCompanyDAO companyDAO = new TblCompanyDAO();
                TblCompanyDTO companyDTO = (TblCompanyDTO) session.getAttribute("COMPANY_ROLE_INFO");
                if (companyDTO != null) {
                    List<TblCompany_PostDTO> companyPostPerPage = null;
                    if (companyDTO.isIs_Signed() == false) {
                        url = properties.getProperty(MyApplicationConstants.CompanyFeatures.COMPANY_POST_MANAGE_PAGE);
                        request.setAttribute("COMPANY_NOT_ALLOW_CREATE_POST", "Company isn's signed, so can't not create a new post!");
                    } else {
                        url = properties.getProperty(MyApplicationConstants.CompanyFeatures.COMPANY_POST_MANAGE_PAGE);
                    }
                        int idMajor = 0;
                        if (majorID != null) {
                            if (majorID.trim().isEmpty()) {
                                idMajor = 0;
                            } else {
                                idMajor = Integer.parseInt(majorID);
                            }
                        }
                        if (tittle_Post == null) {
                            tittle_Post = "";
                        } else {
                            tittle_Post = tittle_Post.trim();
                        }
                        if (nameStatus == null) {
                            nameStatus = "";
                        }
                        companyPostDAO.searchPostByFilterAsCompanyRole(companyID, tittle_Post, idMajor, nameStatus);
                        List<TblCompany_PostDTO> listCompanyPost = companyPostDAO.getCompanyPostByFilter();
                        //Phan trang
                        if (listCompanyPost != null) {
                            sizeOfList = listCompanyPost.size();

                            if (xpage == null) {
                                page = 1;
                            } // load page save job 
                            else {
                                page = Integer.parseInt(xpage);
                            } // when choose number of page

                            numberPage = sizeOfList % numberRowsPerPage;

                            if (numberPage == 0) {
                                numberPage = sizeOfList / numberRowsPerPage;
                            } else {
                                numberPage = (sizeOfList / numberRowsPerPage) + 1;
                            }
                            start = (page - 1) * numberRowsPerPage;
                            end = Math.min(page * numberRowsPerPage, sizeOfList);

                            companyPostPerPage = companyPostDAO.
                                    getListByPage(listCompanyPost, start, end);
                            //Set attribute                                                

                        } // if company post list exisst
                        else {
                            url = properties.getProperty(MyApplicationConstants.CompanyFeatures.COMPANY_POST_MANAGE_PAGE);
                            sizeOfList = 0;
                        } // if company post list NOT exisst
                    //}
                    request.setAttribute("COMPANY_POST_LIST", companyPostPerPage);
                    request.setAttribute("page", page);
                    request.setAttribute("numberPage", numberPage);
                    request.setAttribute("SIZE_OF_LIST", sizeOfList);
                    //get list major
                    TblMajorDAO majorDAO = new TblMajorDAO();
                    majorDAO.getNameMajor();
                    List<TblMajorDTO> listNameMajor = majorDAO.getListNameMajor();
                    request.setAttribute("LIST_MAJOR_NAME", listNameMajor);

                    RequestDispatcher rd = request.getRequestDispatcher(url);
                    rd.forward(request, response);
                }//if company exist
                else {
                    response.sendRedirect(url);
                } // if company NOT exist
            } //if session exist
            else {
                response.sendRedirect(url);
            }//if session NOT exist
        } catch (SQLException ex) {
            log("SQLException at SearchCompanyPostServlet " + ex.getMessage());
        } catch (NamingException ex) {
            log("NamingException at SearchCompanyPostServlet " + ex.getMessage());
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
