/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se1625.controller;

import com.se1625.tblaccount.TblAccountDTO;
import com.se1625.tblfollowing_post.TblFollowing_PostDAO;
import com.se1625.tblstudent.TblStudentDAO;
import com.se1625.tblstudent.TblStudentDTO;
import com.se1625.utils.MyApplicationConstants;
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
 * @author ThanhTy
 */
public class StudentDeleteSaveJobServlet extends HttpServlet {

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

        int postID = Integer.parseInt(request.getParameter("postID"));
        String unSave = request.getParameter("unSave");
        String stringPostIDOther = request.getParameter("postIDOther");

        ServletContext context = this.getServletContext();
        Properties properties = (Properties) context.getAttribute("SITE_MAPS");
        String url = MyApplicationConstants.studentDeleteSaveJobFeature.LOGIN_PAGE;

        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                TblStudentDTO student = (TblStudentDTO) session.getAttribute("STUDENT_ROLE");
                if (student != null) {
                    TblFollowing_PostDAO followingPostDAO = new TblFollowing_PostDAO();
                    
                    boolean checkExisted = false;

                    if (stringPostIDOther != null) {
                        int postIDOther = Integer.parseInt(stringPostIDOther);
                        boolean checkExistedFollowingPostOther = followingPostDAO.
                            checkExitsFollowingPost(postIDOther, student.getStudentCode());
                        if (checkExistedFollowingPostOther == true) {
                            boolean statusDeleteFollowingPostOther = 
                                    followingPostDAO.deleteFollowingPost(postIDOther, student.getStudentCode());
                            if (statusDeleteFollowingPostOther) {
                                if (unSave.equals("homeShowCompanyDetail")) {
                                    url = properties.getProperty(MyApplicationConstants.StudentSaveJobFeature.STUDENT_HOME_SHOW_COMPANY_DETAIL_CONTROLLER);
                                    RequestDispatcher rd = request.getRequestDispatcher(url);
                                    rd.forward(request, response);
                                }
                            }
                        }
                    } else {
                        checkExisted = followingPostDAO.
                            checkExitsFollowingPost(postID, student.getStudentCode());
                    }
                    
                    if (checkExisted == true) {
                        boolean statusDelete = followingPostDAO.
                                deleteFollowingPost(postID, student.getStudentCode());
                        if (statusDelete) {
                            if (unSave != null) {
                                if (unSave.equals("studentReviewPage")) {
                                    url = MyApplicationConstants.studentDeleteSaveJobFeature.STUDENT_REVIEW_INTERNSHIP_CONTROLLER;
                                    response.sendRedirect(url);
                                } else if (unSave.equals("studentDashboardPage")) {
                                    url = MyApplicationConstants.studentDeleteSaveJobFeature.STUDENT_DASHBOARD_CONTROLLER;
                                    response.sendRedirect(url);
                                } else if (unSave.equals("homePage")) {
                                    url = MyApplicationConstants.studentDeleteSaveJobFeature.STUDENT_HOME_CONTROLLER;
                                    response.sendRedirect(url);
                                } else if (unSave.equals("homeShowCompanyDetail")) {
                                    url = properties.getProperty(MyApplicationConstants.StudentSaveJobFeature.STUDENT_HOME_SHOW_COMPANY_DETAIL_CONTROLLER);
                                    RequestDispatcher rd = request.getRequestDispatcher(url);
                                    rd.forward(request, response);
                                }
                            } else {
                                url = MyApplicationConstants.studentDeleteSaveJobFeature.STUDENT_SEARCH_SAVE_JOB_CONTROLLER;
                                response.sendRedirect(url);
                            }

                        }
                    }
                }//if student is created
                else {
                    response.sendRedirect(url);
                }
            }//if session existe
            else {
                response.sendRedirect(url);
            }
        } catch (SQLException ex) {
            log("SQLException at DeleteServlet" + ex.getMessage());
        } catch (NamingException ex) {
            log("NamingException at DeleteServlet" + ex.getMessage());
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
