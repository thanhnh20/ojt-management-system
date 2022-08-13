/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se1625.controller;

import com.se1625.tblapplication.TblApplicationDAO;
import com.se1625.tblapplication.TblApplicationDTO;
import com.se1625.tblcompany.TblCompanyDAO;
import com.se1625.tblcompany.TblCompanyDTO;
import com.se1625.tblcompany_post.CompanyPostDetailError;
import com.se1625.tblcompany_post.TblCompany_PostDAO;
import com.se1625.tblcompany_post.TblCompany_PostDTO;
import com.se1625.tblfollowing_post.TblFollowing_PostDAO;
import com.se1625.tblfollowing_post.TblFollowing_PostDTO;
import com.se1625.tblmajor.TblMajorDAO;
import com.se1625.tblmajor.TblMajorDTO;
import com.se1625.tblsemester.TblSemesterDAO;
import com.se1625.tblsemester.TblSemesterDTO;
import com.se1625.tblstudent.TblStudentDAO;
import com.se1625.tblstudent.TblStudentDTO;
import com.se1625.utils.MyApplicationConstants;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * @author Thanh Huy
 */
public class HomeShowCompanyDetailServlet extends HttpServlet {

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

        String postID = request.getParameter("postID");
        String xpage = request.getParameter("page");

        ServletContext context = this.getServletContext();
        Properties properties = (Properties) context.getAttribute("SITE_MAPS");
        String url = MyApplicationConstants.SearchComanyStudentHomeFeature.LOGIN_PAGE;

        HttpSession session = request.getSession(false);
        int page;
        int numberRowsPerPage = 4;
        int start;
        int end;
        int sizeOfList;
        try {
            if (session != null) {
                TblStudentDTO student = (TblStudentDTO) session.getAttribute("STUDENT_ROLE");
                CompanyPostDetailError error = new CompanyPostDetailError();
                boolean found = false;
                if (student != null) {
                    url = properties.getProperty(MyApplicationConstants.SearchComanyStudentHomeFeature.HOME_SHOW_COMPANY_DETAIL_JSP);
                    int nPostID = Integer.parseInt(postID);

                    TblCompany_PostDAO companyPostDAO = new TblCompany_PostDAO();
                    TblCompanyDAO companyDAO = new TblCompanyDAO();
                    TblCompanyDTO companyDTO;

                    TblCompany_PostDTO companyPostDTO = companyPostDAO.searchPostByPostID(nPostID);

                    companyDTO = companyDAO.searchCompanyByCompanyID(companyPostDTO.getCompany().getCompanyID());
                    companyPostDTO.setCompany(companyDTO);

                    //check student student completed
                    TblStudentDAO studentDAO = new TblStudentDAO();
                    TblStudentDTO studentDTO = studentDAO.getStudent(student.getStudentCode());

                    //check the student has applied this post yet
                    TblSemesterDAO semesterDAO = new TblSemesterDAO();
                    TblSemesterDTO semester = semesterDAO.getCurrentSemester();

                    TblApplicationDAO applicationDAO = new TblApplicationDAO();
                    TblApplicationDTO application = applicationDAO.getApplication(student.getStudentCode(), nPostID, semester.getSemesterID());

                    if (companyPostDTO.getQuantityIterns() == 0) {
                        found = true;
                        error.setQuantitytInternsNotEngough("This post has recruited enough interns.");
                    }
                    LocalDate timeDay = LocalDate.now();
                    DateTimeFormatter dayFormat
                            = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    // convert String to date type
                    java.util.Date currentDate = Date.valueOf(timeDay.format(dayFormat));
                    if (companyPostDTO.getExpirationDate().before(currentDate)) {
                        found = true;
                        error.setExpirationDateError("This post has expired.");
                    }
                    if (application != null) {
                        if (application.isStudentConfirm() == true
                                && application.getSchoolConfirm() == 1
                                && application.getCompanyConfirm() == 1
                                || application.isStudentConfirm() == true
                                && application.getSchoolConfirm() == 1
                                && application.getCompanyConfirm() == 0
                                || application.isStudentConfirm() == true
                                && application.getSchoolConfirm() == 0
                                && application.getCompanyConfirm() == 0
                                ||application.isStudentConfirm() == true
                                && application.getCompanyConfirm() == 2
                                && application.getSchoolConfirm() == 1) {
                            found = true;
                            error.setAppliedTwoTimeError("You applied this company's Job before");
                        }
                    }

                    if (studentDTO.getIsIntern() == 2) {
                        found = true;
                        error.setStudentCompletedError("You have already completed your internship");
                    } else if (studentDTO.getIsIntern() == 1) {
                        //get applcation of this student in this semester 
                        // is enough studentconfirm true, schoolConfirm 1, companyConfirm 1
                        //=> không cho apply thêm bất kì job nào nữa

                        /* Thanh comment
                        TblApplicationDTO applicationStudentWorking = applicationDAO.
                                getApplicationStudentWorking(student.getStudentCode(), semester.getSemesterID());
                                                     
                        if (applicationStudentWorking != null) {                           
                            found = true;
                            error.setAppliedJobStudentWorkingError("You joined the internship");
                        }*/
                        found = true;
                        error.setAppliedJobStudentWorkingError("You joined the internship");
                    }
                    if (found == true) {
                        request.setAttribute("ERROR_COMPANY_POST", error);
                    }
                    request.setAttribute("POST_DETAIL", companyPostDTO);

                    // get majorID from majorName
                    // call searchPostByFilter()
                    TblMajorDAO majorDAO = new TblMajorDAO();
                    int majorID = majorDAO.getMajorIDByMajorName(companyPostDTO.getMajorName());
                    companyPostDAO.searchPostByFilter("", majorID, "");
                    List<TblCompany_PostDTO> listOtherCompanies = companyPostDAO.getCompanyPostByFilter();
                    if (listOtherCompanies == null) {
                        sizeOfList = 0;
                    } else {
                        sizeOfList = listOtherCompanies.size();
                    }

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

                    List<TblCompany_PostDTO> listOtherCompaniesPerPage = companyPostDAO.
                            getListByPage(listOtherCompanies, start, end);

                    request.setAttribute("LIST_OTHER_COMPANIES", listOtherCompaniesPerPage);
                    request.setAttribute("SIZE_OF_LIST", sizeOfList);
                    request.setAttribute("page", page);
                    request.setAttribute("numberPage", numberPage);

                    companyDAO.getNameCompanies();
                    List<TblCompanyDTO> listNameCompany = companyDAO.getListNameCompany();
                    request.setAttribute("COMPANY_NAME", listNameCompany);

                    majorDAO.getNameMajor();
                    List<TblMajorDTO> listNameMajor = majorDAO.getListNameMajor();
                    request.setAttribute("LIST_NAME_MAJOR", listNameMajor);

                    TblFollowing_PostDAO followPostDao = new TblFollowing_PostDAO();
                    followPostDao.getFollowingPost(student.getStudentCode());

                    //get number of following post of student
                    List<TblFollowing_PostDTO> listFollowingCompanyPostByFilter
                            = followPostDao.getFollowingPostByFilter();

                    request.setAttribute("LIST_FOLLOWING_POST", listFollowingCompanyPostByFilter);

                    RequestDispatcher rd = request.getRequestDispatcher(url);
                    rd.forward(request, response);
                }// if student is created
                else {
                    response.sendRedirect(url);
                } //if student is not created
            }// if session exist
            else {
                response.sendRedirect(url);
            } // if session does not exist
        } catch (NumberFormatException ex) {
            log("NumberFormatException at HomeShowCompanyDetailServlet " + ex.getMessage());
        } catch (SQLException ex) {
            log("SQLException at SearchCompanyStudentHomeServlet " + ex.getMessage());
        } catch (NamingException ex) {
            log("NamingException at SearchCompanyStudentHomeServlet " + ex.getMessage());
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
