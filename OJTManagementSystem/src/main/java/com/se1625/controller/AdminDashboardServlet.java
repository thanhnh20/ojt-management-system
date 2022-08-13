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
import com.se1625.tblsemester.TblSemesterDAO;
import com.se1625.tblsemester.TblSemesterDTO;
import com.se1625.tblstudent.TblStudentDAO;
import com.se1625.utils.MyApplicationConstants;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Array;
import java.sql.Date;
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
 * @author Thanh Huy
 */
public class AdminDashboardServlet extends HttpServlet {

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

        ServletContext context = this.getServletContext();
        Properties properties = (Properties) context.getAttribute("SITE_MAPS");
        String url = MyApplicationConstants.AdminDashboardFeature.LOGIN_PAGE;

        HttpSession session = request.getSession(false);

        String sSemesterID = request.getParameter("txtSemesterID");

        try {
            if (session != null) {
                TblAccountDTO account = (TblAccountDTO) session.getAttribute("ADMIN_ROLE");
                if (account != null) {
                    url = properties.getProperty(MyApplicationConstants.AdminDashboardFeature.ADMIN_DASHBOARD_PAGE);

                    TblSemesterDAO semesterDAO = new TblSemesterDAO();
                    TblSemesterDTO currentSemester = semesterDAO.getCurrentSemester();

                    int semesterID = currentSemester.getSemesterID();
                    if (sSemesterID != null) {
                        semesterID = Integer.parseInt(sSemesterID);
                    }
                    request.setAttribute("SELECTED_SEMESTER", semesterID);

                    TblStudentDAO studentDAO = new TblStudentDAO();
                    int totalStudent = 0;
                    if(studentDAO.getListStudent(currentSemester.getSemesterID()) != null){
                        totalStudent = studentDAO.getListStudent(currentSemester.getSemesterID()).size();
                    }
                    
                    request.setAttribute("TOTAL_STUDENT", totalStudent);

                    TblCompany_PostDAO companyPostDAO = new TblCompany_PostDAO();
                    companyPostDAO.searchPostByFilterAsAdminRole("", "", "Waiting", currentSemester.getSemesterID());
                    List<TblCompany_PostDTO> listCompanyPost = companyPostDAO.getCompanyPostListAdminPage();
                    int totalPendingPost = 0;
                    if (listCompanyPost != null) {
                        totalPendingPost = listCompanyPost.size();
                    }
                    request.setAttribute("TOTAL_PENDING_POST", totalPendingPost);

                    TblCompanyDAO companydao = new TblCompanyDAO();
                    companydao.getAllCompany();
                    List<TblCompanyDTO> listAllCompany = companydao.getListAllCompany();
                    int totalSignedCompany = 0;
                    int totalUnsignedCompany = 0;
                    if (listAllCompany != null) {
                        for (TblCompanyDTO tblCompanyDTO : listAllCompany) {
                            if (tblCompanyDTO.isIs_Signed()) {
                                totalSignedCompany++;
                            } else {
                                totalUnsignedCompany++;
                            }
                        }
                    }
                    request.setAttribute("TOTAL_SIGNED_COMPANY", totalSignedCompany);
                    request.setAttribute("TOTAL_UNSIGNED_COMPANY", totalUnsignedCompany);

                    List<TblSemesterDTO> listSemester = semesterDAO.getListSemester();
                    //List<TblSemesterDTO> listDeleteSemester = new ArrayList<>();
//                    long millis = System.currentTimeMillis();
//                    Date dateNow = new java.sql.Date(millis);
//                    for (TblSemesterDTO tblSemesterDTO : listSemester) {
//                        if (tblSemesterDTO.getEndDate().after(dateNow)) {
//                            listDeleteSemester.add(tblSemesterDTO);
//                        }
//                    }
//                    if (!listDeleteSemester.isEmpty()) {
//                        for (TblSemesterDTO tblSemesterDTO : listDeleteSemester) {
//                            listSemester.remove(tblSemesterDTO);
//                        }
//                    }

                    request.setAttribute("LIST_SEMESTERS", listSemester);

                    TblApplicationDAO applDAO = new TblApplicationDAO();
                    List<TblApplicationDTO> listApplicationGrade = applDAO.getListStudentApplications(semesterID);
                    int totalGrade0 = 0;
                    int totalGrade1 = 0;
                    int totalGrade2 = 0;
                    int totalGrade3 = 0;
                    int totalGrade4 = 0;
                    int totalGrade5 = 0;
                    int totalGrade6 = 0;
                    int totalGrade7 = 0;
                    int totalGrade8 = 0;
                    int totalGrade9 = 0;
                    int totalGrade10 = 0;

                    if (listApplicationGrade != null) {
                        for (TblApplicationDTO tblApplicationDTO : listApplicationGrade) {
                            if (tblApplicationDTO.getIsPass() != 0) {
                                switch ((int) tblApplicationDTO.getGrade()) {
                                    case 0:
                                        totalGrade0++;
                                        break;
                                    case 1:
                                        totalGrade1++;
                                        break;
                                    case 2:
                                        totalGrade2++;
                                        break;
                                    case 3:
                                        totalGrade3++;
                                        break;
                                    case 4:
                                        totalGrade4++;
                                        break;
                                    case 5:
                                        totalGrade5++;
                                        break;
                                    case 6:
                                        totalGrade6++;
                                        break;
                                    case 7:
                                        totalGrade7++;
                                        break;
                                    case 8:
                                        totalGrade8++;
                                        break;
                                    case 9:
                                        totalGrade9++;
                                        break;
                                    case 10:
                                        totalGrade10++;
                                        break;
                                }
                            }
                        }
                    }
                    String listGrade = totalGrade0 + ", "
                            + totalGrade1 + ", "
                            + totalGrade2 + ", "
                            + totalGrade3 + ", "
                            + totalGrade4 + ", "
                            + totalGrade5 + ", "
                            + totalGrade6 + ", "
                            + totalGrade7 + ", "
                            + totalGrade8 + ", "
                            + totalGrade9 + ", "
                            + totalGrade10;

                    request.setAttribute("LIST_GRADE", listGrade);

                    String listFailed = "";
                    String listPassed = "";
                    String sListSemester = "";
                    for (TblSemesterDTO tblSemesterDTO : listSemester) {
                        List<TblApplicationDTO> listAppl = applDAO.getListStudentApplications(tblSemesterDTO.getSemesterID());
                        int totalFailed = 0;
                        int totalPassed = 0;
                        if (listAppl != null) {
                            for (TblApplicationDTO tblApplicationDTO : listAppl) {
                                if (tblApplicationDTO.getIsPass() == 1) {
                                    totalPassed++;
                                }
                                if (tblApplicationDTO.getIsPass() == -1) {
                                    totalFailed++;
                                }
                            }
                        }
                        listFailed += totalFailed + ", ";
                        listPassed += totalPassed + ", ";
                        sListSemester += "'" + tblSemesterDTO.getSemesterName() + "'" + ", ";
                    }

                    request.setAttribute("LIST_FAILED", listFailed);
                    request.setAttribute("LIST_PASSED", listPassed);
                    request.setAttribute("LIST_SEMESTER_STRING", sListSemester);

                    RequestDispatcher rd = request.getRequestDispatcher(url);
                    rd.forward(request, response);

                } else {
                    response.sendRedirect(url);
                }
            } else {
                response.sendRedirect(url);
            }
        } catch (SQLException ex) {
            log("SQLException occurs AdminShowInternApplicationServlet " + ex.getMessage());
        } catch (NamingException ex) {
            log("NamingException occurs AdminShowInternApplicationServlet " + ex.getMessage());
        } catch (NumberFormatException ex) {
            log("NumberFormatException occurs AdminShowInternApplicationServlet " + ex.getMessage());
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
