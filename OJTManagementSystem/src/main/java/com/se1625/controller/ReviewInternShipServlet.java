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
import com.se1625.tblcompany.TblCompanyDTO;
import com.se1625.tblcompany_post.TblCompany_PostDAO;
import com.se1625.tblcompany_post.TblCompany_PostDTO;
import com.se1625.tblfollowing_post.TblFollowing_PostDAO;
import com.se1625.tblfollowing_post.TblFollowing_PostDTO;
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
public class ReviewInternShipServlet extends HttpServlet {

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
        String url = MyApplicationConstants.ReviewInternShipFeature.LOGIN_PAGE;

        HttpSession session = request.getSession(false);
        try {
            if (session != null) {
                //lấy Account của Student
                TblStudentDTO student = (TblStudentDTO) session.getAttribute("STUDENT_ROLE");
                if (student != null) {
                    url = properties.getProperty(MyApplicationConstants.ReviewInternShipFeature.STUDENT_REVIEW_INTERNSHIP_PAGE);
                    //lấy profile của Student 
                    //set Attribute
                    request.setAttribute("STUDENT_PROFILE", student);

                    //lấy Application của Student apply đã được Company và school và student duyệt
                    TblApplicationDAO applicationDAO = new TblApplicationDAO();
                    TblApplicationDTO application = applicationDAO.getApplication(student.getStudentCode());
                    
                    if (application != null) {
                        //set Attribute
                        request.setAttribute("STUDENT_APPLICATION", application);

                        //get bài post của Company mà Student apply
                        TblCompany_PostDAO companyPostDAO = new TblCompany_PostDAO();
                        TblCompany_PostDTO companyPost = companyPostDAO.searchPostByPostID(application.getCompanyPost().getPostID());
                        //set Attribute
                        request.setAttribute("COMPANY_POST_APPLIED_BY_STUDENT", companyPost);

                        if (companyPost != null) {

                            //get Company đăng bài Post 
                            String companyID = companyPost.getCompany().getCompanyID();
                            TblCompanyDAO daoCompany = new TblCompanyDAO();
                            TblCompanyDTO dtoCompany = daoCompany.getCompany(companyID);//set Attribute

                            //          get tên Company
                            //          String nameCompany = dtoCompany.getAccount().getName();
                            //          System.out.println(nameCompany);
                            //set attribute
                            request.setAttribute("COMPANY_PROFILE", dtoCompany);
                            RequestDispatcher rd = request.getRequestDispatcher(url);
                            rd.forward(request, response);
                        }
                    } else {
                        //Recomend post
                        TblCompany_PostDAO companyPostDAO = new TblCompany_PostDAO();
                        //lấy 6 bài post
                        companyPostDAO.getListRecomendPost(student.getMajor());
                        List<TblCompany_PostDTO> listPostHome = companyPostDAO.getCompanyPostListHome();
                        request.setAttribute("LIST_RECOMMEND_POST", listPostHome);

                        TblFollowing_PostDAO followPostDao = new TblFollowing_PostDAO();
                        followPostDao.getFollowingPost(student.getStudentCode());
                        List<TblFollowing_PostDTO> listFollowingCompanyPostByFilter
                                = followPostDao.getFollowingPostByFilter();
                        request.setAttribute("LIST_FOLLOWING_POST", listFollowingCompanyPostByFilter);
                        RequestDispatcher rd = request.getRequestDispatcher(url);
                        rd.forward(request, response);
                    }

                } //if student is created
                else {
                    response.sendRedirect(url);
                } // if student is not created
            }//if session exist
            else {
                response.sendRedirect(url);
            }// if session does not exist
        } catch (NamingException ex) {
            log("ReviewInternShipServlet_NamingException " + ex.getMessage());
        } catch (SQLException ex) {
            log("ReviewInternShipServlet_SQLException " + ex.getMessage());
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
