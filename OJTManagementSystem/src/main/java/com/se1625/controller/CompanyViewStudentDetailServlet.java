/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se1625.controller;

import com.se1625.tblaccount.TblAccountDTO;
import com.se1625.tblapplication.TblApplicationDAO;
import com.se1625.tblapplication.TblApplicationDTO;
import com.se1625.tblcompany_post.TblCompany_PostDAO;
import com.se1625.tblcompany_post.TblCompany_PostDTO;
import com.se1625.tblsemester.TblSemesterDAO;
import com.se1625.tblsemester.TblSemesterDTO;
import com.se1625.tblstudent.TblStudentDAO;
import com.se1625.tblstudent.TblStudentDTO;
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
public class CompanyViewStudentDetailServlet extends HttpServlet {

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

        String studentCode = request.getParameter("studentCode");
        String companyPostIDString = request.getParameter("companyPostID");
        String companyConfirm = request.getParameter("companyConfirm");

        ServletContext context = this.getServletContext();
        Properties prop = (Properties) context.getAttribute("SITE_MAPS");
        String url = MyApplicationConstants.CompanyViewStudentDetailFeature.LOGIN_PAGE;

        HttpSession session = request.getSession(false);
        try {
            if (session != null) {
                TblAccountDTO accountDTO = (TblAccountDTO) session.getAttribute("COMPANY_ROLE");
                if (accountDTO != null) {
                    int companyPostID = Integer.parseInt(companyPostIDString);
                    //get Student information
                    TblStudentDAO studentDAO = new TblStudentDAO();
                    TblStudentDTO studentDTO = studentDAO.getStudentInformation(studentCode);
                    //get current Semester
                    TblSemesterDAO semesterDAO = new TblSemesterDAO();
                    TblSemesterDTO semesterDTO = semesterDAO.getCurrentSemester();
                    int currentSemester = semesterDTO.getSemesterID();
                    // get Company post
                    TblCompany_PostDAO companyPostDAO = new TblCompany_PostDAO();
                    TblCompany_PostDTO companyPostDTO = companyPostDAO.getCompanyPost(companyPostID);

                    TblApplicationDAO applicationDAO = new TblApplicationDAO();
                    List<TblApplicationDTO> applicationList = applicationDAO.getApplicationByEmail(accountDTO.getEmail(), -3, currentSemester);
                    
                    //get Application of Student apply to this company
                    TblApplicationDTO applicationDTO = new TblApplicationDTO();
                    for (TblApplicationDTO tblApplicationDTO : applicationList) {
                        if(tblApplicationDTO.getStudent().getStudentCode().equals(studentDTO.getStudentCode())){
                            applicationDTO = tblApplicationDTO;
                        }
                    }
                    
                    request.setAttribute("APPLICATION_DTO", applicationDTO);
                    request.setAttribute("STUDENT_INFOR", studentDTO);
                    request.setAttribute("COMPANY_POST_INFOR", companyPostDTO);
                    request.setAttribute("COMPANY_COMFIRM", companyConfirm);
                    url = prop.getProperty(
                            MyApplicationConstants.CompanyViewStudentDetailFeature.COMPANY_VIEW_STUDENT_DETAILE_PAGE);
                    RequestDispatcher rd = request.getRequestDispatcher(url);
                    rd.forward(request, response);
                } else {
                    response.sendRedirect(url);
                }
            } else {
                response.sendRedirect(url);
            }
        } catch (NamingException ex) {
            log("NamingException at CompanyViewStudentDetailServlet " + ex.getMessage());
        } catch (SQLException ex) {
            log("SQLException at CompanyViewStudentDetailServlet " + ex.getMessage());
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
