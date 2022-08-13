/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se1625.controller;

import com.se1625.tblaccount.TblAccountDAO;
import com.se1625.tblaccount.TblAccountDTO;
import com.se1625.tblaccount.TblAccountError;
import com.se1625.tblsemester.TblSemesterDAO;
import com.se1625.tblsemester.TblSemesterDTO;
import com.se1625.tblstudent.TblStudentDAO;
import com.se1625.tblstudent.TblStudentDTO;
import com.se1625.usergoogle.UserGoogleDTO;
import com.se1625.utils.MyApplicationConstants;
import com.se1625.utils.MyApplicationHelper;
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
 * @author ASUS
 */
public class LoginGoogleServlet extends HttpServlet {

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

        String code = request.getParameter("code");
        //get user google
        String accessToken = MyApplicationHelper.getToken(code);
        UserGoogleDTO userInfo = MyApplicationHelper.getUserInfo(accessToken);

        //get properties
        ServletContext context = this.getServletContext();
        Properties siteMap = (Properties) context.getAttribute("SITE_MAPS");

        String url = siteMap.getProperty(MyApplicationConstants.LoginGoogleFeture.LOGIN_PAGE);

        String email = userInfo.getEmail();

        try {
            TblAccountError error;
            TblAccountDAO dao = new TblAccountDAO();
            TblAccountDTO dto = dao.getAccount(email);
            //check existed email
            if (dto != null) {
                if (dto.getIs_Admin() == 1) {
                    HttpSession session = request.getSession();
                    session.setAttribute("ADMIN_ROLE", dto);
                    url = MyApplicationConstants.LoginGoogleFeture.ADMIN_DASHBOARD_PAGE;
                    response.sendRedirect(url);
                } //account's role is admin
                else if (dto.getIs_Admin() == 2) {
                    HttpSession session = request.getSession();
                    //get current semester
                    TblSemesterDAO semesterDAO = new TblSemesterDAO();
                    TblSemesterDTO currentSemester = semesterDAO.getCurrentSemester();
                    TblStudentDAO studentDAO = new TblStudentDAO();
                    TblStudentDTO student = studentDAO.getStudent(dto.getEmail(), currentSemester);
                    if (student != null) {
                        if (student.isIsDisabled() == false) {
                            session.setAttribute("STUDENT_ROLE", student);
                            url = MyApplicationConstants.LoginGoogleFeture.STUDENT_DASHBOARD_CONTROLLER;
                            response.sendRedirect(url);
                        } else {
                            error = new TblAccountError();
                            error.setUserEmailNotAllow("Your account is not allowed to login into this system by email!");
                            request.setAttribute("ERROR", error);
                            RequestDispatcher rd = request.getRequestDispatcher(url);
                            rd.forward(request, response);
                        }

                    } else {
                        error = new TblAccountError();
                        error.setUserEmailNotAllow("Your account is not allowed to login into this system by email!");
                        request.setAttribute("ERROR", error);
                        RequestDispatcher rd = request.getRequestDispatcher(url);
                        rd.forward(request, response);
                    }
                } //account's role is student
                else {
                    error = new TblAccountError();
                    error.setUserEmailNotAllow("Your account is not allowed to login into this system by email!");
                    request.setAttribute("ERROR", error);
                    RequestDispatcher rd = request.getRequestDispatcher(url);
                    rd.forward(request, response);
                }//account's role is company but this account is not allowed login by email
            } //account exist
            else {
                error = new TblAccountError();
                error.setUserEmailNotAllow("Your account is not allowed to login into this system by email!");
                request.setAttribute("ERROR", error);
                RequestDispatcher rd = request.getRequestDispatcher(url);
                rd.forward(request, response);
            } // account don't exist in database
        } catch (NamingException ex) {
            log("LoginGoogleServlet_NamingException at LoginGoogleServlet " + ex.getMessage());
        } catch (SQLException ex) {
            log("LoginGoogleServlet_SQLException at LoginGoogleServlet " + ex.getMessage());
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
