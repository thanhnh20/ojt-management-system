/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se1625.controller;

import com.se1625.tblcompany.TblCompanyDAO;
import com.se1625.tblcompany.TblCompanyDTO;
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
public class SearchSaveJobServlet extends HttpServlet {

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

        String jobName = request.getParameter("txtJob");
        if (jobName != null){
            jobName = jobName.trim();    
        }
        String companyName = request.getParameter("txtCompany");
        if (companyName != null){
            companyName = companyName.trim();
        }
        String nameLocation = request.getParameter("nameLocation");
        String xpage = request.getParameter("page");
        ServletContext context = this.getServletContext();
        Properties properties = (Properties) context.getAttribute("SITE_MAPS");
        String url = MyApplicationConstants.StudentSaveJobFeature.LOGIN_PAGE;

        int page;
        int numberRowsPerPage = 10;
        int start;
        int end;
        int sizeOfList;
        HttpSession session = request.getSession(false);
        try {
            if (session != null) {

                TblStudentDTO student = (TblStudentDTO) session.getAttribute("STUDENT_ROLE");

                if (student != null) {
                    TblFollowing_PostDAO followPostDao = new TblFollowing_PostDAO();
                    List<TblFollowing_PostDTO> listFollowingCompanyPostByFilter = null;
                    if (jobName == null && companyName == null && nameLocation == null) {
                        followPostDao.searchFollowingPostByFilter("", "",
                                "", student.getStudentCode());
                        listFollowingCompanyPostByFilter
                                = followPostDao.getFollowingPostByFilter();
                    } //get list saved jobs for saved job page
                    else {
                        followPostDao.searchFollowingPostByFilter(jobName, companyName,
                                nameLocation, student.getStudentCode());

                        listFollowingCompanyPostByFilter
                                = followPostDao.getFollowingPostByFilter();
                    } //get list saved job for search saved job by filter
                    if (listFollowingCompanyPostByFilter != null) {
                        sizeOfList = listFollowingCompanyPostByFilter.size();

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

                        List<TblFollowing_PostDTO> followingPostPerPage = followPostDao.
                                getListByPage(listFollowingCompanyPostByFilter, start, end);

                        request.setAttribute("LIST_SAVED_POSTS_RESULT", followingPostPerPage);
                        request.setAttribute("SIZE_OF_LIST", sizeOfList);
                        request.setAttribute("page", page);
                        request.setAttribute("numberPage", numberPage);

                    } else {
                        sizeOfList = 0;
                        request.setAttribute("SIZE_OF_LIST", sizeOfList);

                    }
                    
                    //lay list company
                    TblCompanyDAO companyDAO = new TblCompanyDAO();
                    companyDAO.getNameCompaniesFollowed(student.getStudentCode());
                    List<TblCompanyDTO> listNameCompany = companyDAO.getListNameCompanyFollowed();
                    request.setAttribute("LIST_ALL_COMPANY", listNameCompany);

                    
                    url = properties.getProperty(MyApplicationConstants.StudentSaveJobFeature.STUDENT_SAVE_JOB_PAGE);
                    RequestDispatcher rd = request.getRequestDispatcher(url);
                    rd.forward(request, response);
                }//if student is created
                else {
                    response.sendRedirect(url);
                }// if student is not created
            }//if session exist
            else {
                response.sendRedirect(url);
            }//if session does not exist
        } catch (SQLException ex) {
            log("SQLException at SearchSaveJobController " + ex.getMessage());
        } catch (NamingException ex) {
            log("NamingException at SearchSaveJobController " + ex.getMessage());
        } catch (NumberFormatException ex) {
            log("NumberFormatException at SearchSaveJobController " + ex.getMessage());
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
