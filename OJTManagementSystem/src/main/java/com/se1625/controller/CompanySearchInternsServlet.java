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
import com.se1625.tblcompany_post.TblCompany_PostDAO;
import com.se1625.tblcompany_post.TblCompany_PostDTO;
import com.se1625.tblsemester.TblSemesterDAO;
import com.se1625.tblsemester.TblSemesterDTO;
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
public class CompanySearchInternsServlet extends HttpServlet {

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

        String fullName = request.getParameter("txtFullName");
        String email = request.getParameter("txtEmail");
        String companyPostIDString = request.getParameter("selectCompanyPost");
        String selectStatus = request.getParameter("status");

        ServletContext context = this.getServletContext();
        Properties prop = (Properties)context.getAttribute("SITE_MAPS");
        String url = MyApplicationConstants.CompanySearchInternsFeature.LOGIN_PAGE;
        
        HttpSession session = request.getSession(false);
        int page;
        int numberProductPage = 10;
        int start;
        int size;
        int end;
        int numberPage;
        try {
            if (session != null) {
                TblAccountDTO accountDTO = (TblAccountDTO) session.getAttribute("COMPANY_ROLE");
                if (accountDTO != null) {

                    // convert data type   
                    int companyPostID = -1;
                    if (!companyPostIDString.isEmpty()) {
                        companyPostID = Integer.parseInt(companyPostIDString);
                    }

                    int status = 1;
                    if (selectStatus.equals("Waiting")) {
                        status = 0;
                    } else if (selectStatus.equals("Denied")) {
                        status = -1;
                    } else if (selectStatus.equals("Interview")) {
                        status = 2;
                    } else if (selectStatus.equals("Failed")){
                        status = -2;
                    }
                    
                    //get current Semester
                    TblSemesterDAO semesterDAO = new TblSemesterDAO();
                    TblSemesterDTO semesterDTO = semesterDAO.getCurrentSemester();
                    int currentSemester = semesterDTO.getSemesterID();
                    //get all application of company
                    TblApplicationDAO applicationDAO = new TblApplicationDAO();
                    List<TblApplicationDTO> applicationList = applicationDAO.getApplicationByEmail(accountDTO.getEmail(), -3, currentSemester);
                    
                    List<TblApplicationDTO> listApplicationByPage;
                    if(applicationList == null){
                        size = 0;
                        listApplicationByPage = applicationList;
                        page = 0;
                        numberPage = 0;
                    }else{
                    // get application result list 
                    List<TblApplicationDTO> resultList = new ArrayList<>();
                    for (TblApplicationDTO tblApplicationDTO : applicationList) {
                        boolean check = true;
                        //check fullname
                        if(fullName.isEmpty() == false && !(tblApplicationDTO.getStudent().getAccount().getName().toLowerCase().contains(fullName.toLowerCase()))){
                            check = false;
                        }
                        //check email
                        if(email.isEmpty() == false && !(tblApplicationDTO.getStudent().getAccount().getEmail().contains(email))){
                            check = false;
                        }
                        //check title job by postID
                        if (companyPostIDString.isEmpty() == false && !(tblApplicationDTO.getCompanyPost().getPostID() == companyPostID)) {
                            check = false;
                        }
                        //check status 
                        if(selectStatus.isEmpty() == false && !(tblApplicationDTO.getCompanyConfirm() == status)){
                            check = false;
                        }
                        
                        if (check) {
                            resultList.add(tblApplicationDTO);
                        }
                    }

                    size = resultList.size();
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

                    listApplicationByPage = applicationDAO.getListByPage(resultList, start, end);                   
                }
                    //get CompanyID
                    TblCompanyDAO companyDAO = new TblCompanyDAO();
                    String companyID = companyDAO.getCompanyByEmail(accountDTO.getEmail()).getCompanyID();              
                    //get All Post of company
                    
                    TblCompany_PostDAO companyPostDAO = new TblCompany_PostDAO();
                    List<TblCompany_PostDTO> listCompanyPost = companyPostDAO.getAllPostByCompanyID(companyID);
                    
                    request.setAttribute("SELECTED", selectStatus);
                    request.setAttribute("LIST_COMPANY_POST", listCompanyPost);
//                    request.setAttribute("APPLICATION_LIST", applicationList);
                    request.setAttribute("APPLICATION_LIST_BYPAGE", listApplicationByPage);
                    request.setAttribute("SIZE_PAGE", size);
                    request.setAttribute("PAGE", page);
                    request.setAttribute("NUMBER_PAGE", numberPage);

                    url = prop.getProperty(
                            MyApplicationConstants.CompanySearchInternsFeature.COMPANY_APPLICATION_MANAGER_PAGE);
                    RequestDispatcher rd = request.getRequestDispatcher(url);
                    rd.forward(request, response);
                }else{
                    response.sendRedirect(url);
                }
            }else{
                response.sendRedirect(url);
            }
        } catch (NamingException ex) {
            log("NamingException at CompanySearchInternsServlet " + ex.getMessage());
        } catch (SQLException ex) {
            log("SQLException at CompanySearchInternsServlet " + ex.getMessage());
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
