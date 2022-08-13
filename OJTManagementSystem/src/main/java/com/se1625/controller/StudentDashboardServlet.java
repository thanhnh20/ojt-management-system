/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se1625.controller;

import com.se1625.tblapplication.TblApplicationDAO;
import com.se1625.tblapplication.TblApplicationDTO;
import com.se1625.tblcompany_post.TblCompany_PostDAO;
import com.se1625.tblcompany_post.TblCompany_PostDTO;
import com.se1625.tblfollowing_post.TblFollowing_PostDAO;
import com.se1625.tblfollowing_post.TblFollowing_PostDTO;
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
 * @author ThanhTy
 */
public class StudentDashboardServlet extends HttpServlet {

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
        String url = MyApplicationConstants.StudentDasboardFeature.LOGIN_PAGE;
        
        HttpSession session = request.getSession(false);
        
        int numberOfFollowingPost = 0;
        try {
            if (session != null) {
                //get student from session
                TblStudentDTO student = (TblStudentDTO) session.getAttribute("STUDENT_ROLE");
                if (student != null) {                                       
                    url = properties.getProperty(MyApplicationConstants.
                            StudentDasboardFeature.STUDENT_DASHBOARD_PAGE);

                    TblFollowing_PostDAO followPostDao = new TblFollowing_PostDAO();
                    followPostDao.getFollowingPost(student.getStudentCode());

                    //get number of following post of student
                    List<TblFollowing_PostDTO> listFollowingCompanyPostByFilter
                            = followPostDao.getFollowingPostByFilter();

                    if (listFollowingCompanyPostByFilter != null) {
                        numberOfFollowingPost = listFollowingCompanyPostByFilter.size();
                    }

                    request.setAttribute("NUMBER_OF_FOLLOWING_POST", numberOfFollowingPost);
                    
                    //get number of applied post of student
                    TblApplicationDAO applicationDAO = new TblApplicationDAO();
                    List<TblApplicationDTO> listAppliedJob = applicationDAO.getApplicationOfAStudent(student);
                    int numberOfAppliedJob = 0;
                    if (listAppliedJob != null) {
                        numberOfAppliedJob = listAppliedJob.size();
                    }
                    
                    request.setAttribute("NUMBER_OF_APLLIED_JOB", numberOfAppliedJob);
                    //get 6 Recomend post has longest expiration date
                    TblCompany_PostDAO companyPostDAO = new TblCompany_PostDAO();
                    companyPostDAO.getListRecomendPost(student.getMajor());
                    List<TblCompany_PostDTO> listRecommendPost = companyPostDAO.
                            getCompanyPostListHome();
                    request.setAttribute("LIST_RECOMMEND_POST", listRecommendPost);
                    
                    //get list post that student followed
                    request.setAttribute("LIST_FOLLOWING_POST", listFollowingCompanyPostByFilter);

                    RequestDispatcher rd = request.getRequestDispatcher(url);
                    rd.forward(request, response);
                }// if attribute exist in session
                else {
                    response.sendRedirect(url);
                } //if attribute does not exist in session
            }//if session is created
            else {
                response.sendRedirect(url);
            }//if session is not created
        } catch (SQLException ex) {
            log("SQL Exception at StudentDashboardController " + ex.getMessage());
        } catch (NamingException ex) {
            log("Naming Exception at StudentDashboardController" + ex.getMessage());
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
