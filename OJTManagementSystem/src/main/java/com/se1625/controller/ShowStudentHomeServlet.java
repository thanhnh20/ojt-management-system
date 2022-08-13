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
import com.se1625.tblfollowing_post.TblFollowing_PostDAO;
import com.se1625.tblfollowing_post.TblFollowing_PostDTO;
import com.se1625.tblmajor.TblMajorDAO;
import com.se1625.tblmajor.TblMajorDTO;
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
 * @author Thai Quoc Toan <toantqse151272@fpt.edu.vn>
 */
public class ShowStudentHomeServlet extends HttpServlet {

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

        HttpSession session = request.getSession(false);

        TblCompany_PostDAO companyPostDAO = new TblCompany_PostDAO();
        TblCompanyDAO companyDAO = new TblCompanyDAO();
        TblMajorDAO majorDAO = new TblMajorDAO();
        String url = MyApplicationConstants.ShowStudentHomeFeature.LOGIN_PAGE;
        try {
            if (session != null) {
                TblStudentDTO student = (TblStudentDTO) session.getAttribute("STUDENT_ROLE");
                if (student != null) {
                    //get top 8 of the post
                    companyPostDAO.getListPostHome();
                    List<TblCompany_PostDTO> listPostHome = companyPostDAO.getCompanyPostListHome();
                    request.setAttribute("LIST_POST_HOME", listPostHome);

                    //get name of companies
                    companyDAO.getNameCompanies();
                    List<TblCompanyDTO> listNameCompany = companyDAO.getListNameCompany();
                    request.setAttribute("COMPANY_NAME", listNameCompany);

                    //get avatar of signed companies
                    companyDAO.getAvatarSignedCompany();
                    List<TblCompanyDTO> listAvatarSignedCompany = companyDAO.getListAvatarSignedCompany();
                    request.setAttribute("LIST_AVATAR_SIGNED_COMPANY", listAvatarSignedCompany);

                    //get major 
                    majorDAO.getNameMajor();
                    List<TblMajorDTO> listNameMajor = majorDAO.getListNameMajor();
                    request.setAttribute("LIST_NAME_MAJOR", listNameMajor);
                    
                    TblFollowing_PostDAO followPostDao = new TblFollowing_PostDAO();
                    followPostDao.getFollowingPost(student.getStudentCode());

                    //get number of following post of student
                    List<TblFollowing_PostDTO> listFollowingCompanyPostByFilter
                            = followPostDao.getFollowingPostByFilter();
                    
                    request.setAttribute("LIST_FOLLOWING_POST", listFollowingCompanyPostByFilter);
                    
                    url = properties.getProperty(MyApplicationConstants.ShowStudentHomeFeature.STUDENT_HOME_PAGE);
                    RequestDispatcher rd = request.getRequestDispatcher(url);
                    rd.forward(request, response);
                } //if student is created
                else {
                    response.sendRedirect(url);
                } //if student is not created
            } //if session exist
            else {
                response.sendRedirect(url);
            }// if session does not exist

        } catch (SQLException ex) {
            log("SQLException at processRequest " + ex.getMessage());
        } catch (NamingException ex) {
            log("NamingException at processRequest " + ex.getMessage());
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
