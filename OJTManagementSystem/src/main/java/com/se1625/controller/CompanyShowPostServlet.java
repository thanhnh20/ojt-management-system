/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se1625.controller;

import com.se1625.tblcompany.TblCompanyDAO;
import com.se1625.tblcompany.TblCompanyDTO;
import com.se1625.tblcompany_post.TblCompany_PostDAO;
import com.se1625.tblcompany_post.TblCompany_PostDTO;
import com.se1625.tblmajor.TblMajorDAO;
import com.se1625.tblmajor.TblMajorDTO;
import com.se1625.utils.MyApplicationConstants;
import java.io.IOException;
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
public class CompanyShowPostServlet extends HttpServlet {

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

        //get parameter
        String xpage = request.getParameter("page");

        int page = 0;
        int numberRowsPerPage = 10;
        int start = 0;
        int end = 0;
        int sizeOfList = 0;
        int numberPage = 0;
        //get session
        HttpSession session = request.getSession(false);
        try {

            if (session != null) {
                TblCompanyDTO companyDTO = (TblCompanyDTO) session.getAttribute("COMPANY_ROLE_INFO");
                TblCompanyDAO companyDAO = new TblCompanyDAO();
                TblCompanyDTO newCompany = companyDAO.getCompany(companyDTO.getCompanyID());
                session.setAttribute("COMPANY_ROLE_INFO", newCompany);
                TblCompany_PostDAO companyPostDAO = new TblCompany_PostDAO();
                if (companyDTO != null) {
                    List<TblCompany_PostDTO> companyPostPerPage = null;
                    if (companyDTO.isIs_Signed() == false) {
                        url = properties.getProperty(MyApplicationConstants.CompanyFeatures.COMPANY_POST_MANAGE_PAGE);
                        request.setAttribute("COMPANY_NOT_ALLOW_CREATE_POST", "Company isn's signed, so can't not create a new post!");
                    } else {

                        url = properties.getProperty(MyApplicationConstants.CompanyFeatures.COMPANY_POST_MANAGE_PAGE);
                    }
                    //Lay danh sach cac bai post
                    companyPostDAO.getCompanyPostByCompanyID(companyDTO.getCompanyID());
                    List<TblCompany_PostDTO> companyPostList = companyPostDAO.getCompanyPostByFilter();
                    //Phan trang
                    if (companyPostList != null) {
                        sizeOfList = companyPostList.size();
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
                                getListByPage(companyPostList, start, end);
                    } // if company have posts
                    else {
                        companyPostPerPage = companyPostList;
                        sizeOfList = 0;
                        page = 0;
                        numberPage = 0;
                    } // if company haven't post
                    //Set attribute
                    //}

                    request.setAttribute("COMPANY_POST_LIST", companyPostPerPage);
                    request.setAttribute("SIZE_OF_LIST", sizeOfList);
                    request.setAttribute("page", page);
                    request.setAttribute("numberPage", numberPage);

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
                }// if company not exist
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
