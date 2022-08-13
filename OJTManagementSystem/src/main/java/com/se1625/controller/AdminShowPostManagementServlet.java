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
import com.se1625.tblsemester.TblSemesterDAO;
import com.se1625.tblsemester.TblSemesterDTO;
import com.se1625.utils.MyApplicationConstants;
import java.io.IOException;
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
 * @author ThanhTy
 */
public class AdminShowPostManagementServlet extends HttpServlet {

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

        String xpage = request.getParameter("page");

        ServletContext context = this.getServletContext();
        Properties properties = (Properties) context.getAttribute("SITE_MAPS");
        String url = MyApplicationConstants.AdminShowPostManagementFeature.LOGIN_PAGE;

        HttpSession session = request.getSession(false);

        int page;
        int numberRowsPerPage = 10;
        int start;
        int end;
        int sizeOfList;
        try {
            if (session != null) {
                //lay info admin
                TblAccountDTO admin = (TblAccountDTO) session.getAttribute("ADMIN_ROLE");
                if (admin != null) {
                    url = properties.getProperty(MyApplicationConstants.AdminShowPostManagementFeature.ADMIN_POST_MANAGE_PAGE);
                    //Lay danh sach cac bai post
                    TblCompany_PostDAO companyPostDAO = new TblCompany_PostDAO();
                    //get current Semester
                    TblSemesterDAO semesterDAO = new TblSemesterDAO();
                    TblSemesterDTO currentSemester = semesterDAO.getCurrentSemester();
                    TblSemesterDTO nowSemester = semesterDAO.getSemesterByID(currentSemester.getSemesterID());
                    if (request.getParameter("listSemester") != null) {
                        int semesterID = Integer.parseInt(request.getParameter("semester"));
                        if (xpage == null) {
                            if (semesterID != currentSemester.getSemesterID()) {
                                currentSemester.setSemesterID(semesterID);
                            }
                        } else {
                            currentSemester = semesterDAO.getSemesterByID(semesterID);
                        }
                    }

                    companyPostDAO.getListPost(currentSemester.getSemesterID());
                    List<TblCompany_PostDTO> companyPostList = companyPostDAO.getCompanyPostListAdminPage();
                    //Phan trang
                    if (companyPostList != null) {
                        sizeOfList = companyPostList.size();

                        if (xpage == null) {
                            page = 1;
                        } // load page save job 
                        else {
                            page = Integer.parseInt(xpage);
                        } // when choose number of page

                        int numberPage = sizeOfList % numberRowsPerPage;

                        if (numberPage == 0) {
                            numberPage = sizeOfList / numberRowsPerPage;
                        } else {
                            numberPage = (sizeOfList / numberRowsPerPage) + 1;
                        }
                        start = (page - 1) * numberRowsPerPage;
                        end = Math.min(page * numberRowsPerPage, sizeOfList);

                        List<TblCompany_PostDTO> companyPostPerPage = companyPostDAO.
                                getListByPage(companyPostList, start, end);
                        //Set attribute
                        request.setAttribute("COMPANY_POST_LIST", companyPostPerPage);
                        request.setAttribute("SIZE_OF_LIST", sizeOfList);
                        request.setAttribute("page", page);
                        request.setAttribute("numberPage", numberPage);

                    } // if company post list exisst
                    //lay list company
                    TblCompanyDAO companyDAO = new TblCompanyDAO();
                    companyDAO.getNameCompanies();
                    List<TblCompanyDTO> listNameCompany = companyDAO.getListNameCompany();
                    request.setAttribute("LIST_ALL_COMPANY", listNameCompany);

                    //List semester
                    List<TblSemesterDTO> listSemester = semesterDAO.getListSemester();
                    request.setAttribute("LIST_SEMESTER", listSemester);
                    request.setAttribute("CURRENT_SEMESTER", currentSemester);
                    request.setAttribute("NOW_SEMESTER", nowSemester);
                    
                    RequestDispatcher rd = request.getRequestDispatcher(url);
                    rd.forward(request, response);
                } // if admin exist
                else {
                    response.sendRedirect(url);
                }//if admin not exist
            } //if session exist
            else {
                response.sendRedirect(url);
            }//if not login
        } catch (NumberFormatException ex) {
            log("NumberFormatException at AdminShowPostManagementServlet " + ex.getMessage());
        } catch (SQLException ex) {
            log("SQLException at AdminShowPostManagementServlet " + ex.getMessage());
        } catch (NamingException ex) {
            log("NamingException at AdminShowPostManagementServlet " + ex.getMessage());
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
