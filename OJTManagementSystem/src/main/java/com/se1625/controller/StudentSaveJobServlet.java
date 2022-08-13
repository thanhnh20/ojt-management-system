/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se1625.controller;

import com.se1625.tblfollowing_post.TblFollowing_PostDAO;
import com.se1625.tblfollowing_post.TblFollowing_PostDTO;
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
public class StudentSaveJobServlet extends HttpServlet {

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
        String save = request.getParameter("save");
        String stringPostIDOther = request.getParameter("postIDOther");

        ServletContext context = this.getServletContext();
        Properties properties = (Properties) context.getAttribute("SITE_MAPS");
        String url = MyApplicationConstants.StudentSaveJobFeature.LOGIN_PAGE;

        HttpSession session = request.getSession(false);

        try {
            if (session != null) {
                //Info student
                TblStudentDTO student = (TblStudentDTO) session.
                        getAttribute("STUDENT_ROLE");
                if (student != null) {
                    TblFollowing_PostDAO followingPostDAO = new TblFollowing_PostDAO();
                    
                    boolean checkExisted = true;
                    
                    if (stringPostIDOther != null) {
                        int postIDOther = Integer.parseInt(stringPostIDOther);
                        boolean checkExistedFollowingPostOther = followingPostDAO.
                                checkExitsFollowingPost(postIDOther, student.getStudentCode());
                        if (checkExistedFollowingPostOther == false) {
                            boolean statusAddFollowingPostOther = followingPostDAO.
                                    addFollowingPost(postIDOther, student.getStudentCode());
                            if (statusAddFollowingPostOther == true) {
                                if (save.equals("homeShowCompanyDetail")) {
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
                    
                    if (checkExisted == false) {
                        boolean statusAdd = followingPostDAO.
                                addFollowingPost(postID, student.getStudentCode());
                        if (statusAdd == true) {
                            if (save != null) {
                                if (save.equals("studentReviewPage")) {
                                    url = MyApplicationConstants.StudentSaveJobFeature.
                                            STUDENT_REVIEW_INTERNSHIP_CONTROLLER;
                                    response.sendRedirect(url);
                                } else if (save.equals("homePage")) {
                                    url = MyApplicationConstants.StudentSaveJobFeature.
                                            STUDENT_HOME_CONTROLLER;
                                    response.sendRedirect(url);
                                } else if (save.equals("homeShowCompanyDetail")) {
                                    url = properties.getProperty(MyApplicationConstants.StudentSaveJobFeature.
                                            STUDENT_HOME_SHOW_COMPANY_DETAIL_CONTROLLER);
                                    RequestDispatcher rd = request.getRequestDispatcher(url);
                                    rd.forward(request, response);
                                }
                            } else {
                                url = MyApplicationConstants.StudentSaveJobFeature.
                                        STUDENT_DASHBOARD_CONTROLLER;
                                response.sendRedirect(url);
                            }

                        }
                    }
                }//if student is created
                else {
                    response.sendRedirect(url);
                }
            }//if session existed
            else {
                response.sendRedirect(url);
            }
        } catch (SQLException ex) {
            log("SQLException at StudentSaveJobController " + ex.getMessage());
        } catch (NamingException ex) {
            log("NamingException at StudentSaveJobController " + ex.getMessage());
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
