/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se1625.controller;

import com.se1625.tblaccount.TblAccountDAO;
import com.se1625.tblaccount.TblAccountDTO;
import com.se1625.tblapplication.TblApplicationDAO;
import com.se1625.tblapplication.TblApplicationDTO;
import com.se1625.tblcompany_post.CompanyPostDetailError;
import com.se1625.tblcompany_post.TblCompany_PostDAO;
import com.se1625.tblcompany_post.TblCompany_PostDTO;
import com.se1625.tblsemester.TblSemesterDAO;
import com.se1625.tblsemester.TblSemesterDTO;
import com.se1625.tblstudent.TblStudentDAO;
import com.se1625.tblstudent.TblStudentDTO;
import com.se1625.utils.MyApplicationConstants;
import com.se1625.utils.MyApplicationHelper;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
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
public class CompanyUpdateStatusIntershipApplicationServlet extends HttpServlet {

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
        String companyConfirmString = request.getParameter("action");

        ServletContext context = this.getServletContext();
        Properties prop = (Properties) context.getAttribute("SITE_MAPS");
        String url = MyApplicationConstants.CompanyUpdateStatusIntershipApplicationFeature.LOGIN_PAGE;

        HttpSession session = request.getSession(false);
        try {
            if (session != null) {
                TblAccountDTO accountDTO = (TblAccountDTO) session.getAttribute("COMPANY_ROLE");
                if (accountDTO != null) {

                    //convert data type
                    int companyPostID = Integer.parseInt(companyPostIDString);
                    int companyConfirm = -2;
                    if (companyConfirmString.equals("Interview")) {
                        companyConfirm = 2;
                    } else if (companyConfirmString.equals("Accept")) {
                        companyConfirm = 1;
                    } else if (companyConfirmString.equals("Reject")) {
                        companyConfirm = -1;
                    }
                    //get quantityInterns of company
                    TblCompany_PostDAO companyPostDAO = new TblCompany_PostDAO();
                    TblCompany_PostDTO companyPostDTO = companyPostDAO.getCompanyPost(companyPostID);
                    int quantityIterns = companyPostDTO.getQuantityIterns();

                    if (quantityIterns <= 0 && companyConfirm == 1) {
                        CompanyPostDetailError error = new CompanyPostDetailError();
                        error.setQuantitytInternsNotEngough("The number of applications is enough");
                        request.setAttribute("ERROR_QUANTITY_INTERNS", error);
                        url = prop.getProperty(
                                MyApplicationConstants.CompanyUpdateStatusIntershipApplicationFeature.COMPANY_SEARCH_INTERNS_CONTROLLER);
                        RequestDispatcher rd = request.getRequestDispatcher(url);
                        rd.forward(request, response);
                    } else {
                        TblApplicationDAO applicationDAO = new TblApplicationDAO();
                        boolean result = applicationDAO.updateStatusCompanyConfirm(studentCode, companyPostID, companyConfirm);
                        if (result) {
                            String subject = "The result of application internship";
                            TblStudentDAO studentDAO = new TblStudentDAO();
                            TblStudentDTO studentInfor = studentDAO.getStudent(studentCode);
                            TblAccountDAO accountDAO = new TblAccountDAO();
                            TblAccountDTO systemAccount = accountDAO.GetAccountByRole(4);
                            String link = "http://localhost:8080/OJTManagementSystem/ShowStudentAppliedJobController";
                            String message = "Dear " + studentInfor.getAccount().getName() + ",\n"
                                    + "\n";
                            if (companyConfirm == -2) {
                                message += "The OJT system wants to announce that you were denied an interview by " + companyPostDTO.getCompany().getAccount().getName() + " company."
                                        + " Please click on the link " + link
                                        + " so as not to miss any information."
                                        + "\n"
                                        + "Regards,"
                                        + "The support OJT team";
                            }
                            if (companyConfirm == 2) {
                                message += "The OJT system wants to announce that you were accepted an interview by " + companyPostDTO.getCompany().getAccount().getName() + " company."
                                        + " You should actively send an email to the company " + companyPostDTO.getCompany().getAccount().getEmail() + " so that you can receive an interview schedule."
                                        + " Please click on the link " + link
                                        + " so as not to miss any information.\n"
                                        + "\n"
                                        + "Regards,"
                                        + "The support OJT team";
                            }
                            if (companyConfirm == -1) {
                                message += "The OJT system wants to announce that you were Denied for the job " + companyPostDTO.getTitle_Post() + " by " + companyPostDTO.getCompany().getAccount().getName() + " company."
                                        + " Please click on the link " + link
                                        + " so as not to miss any information."
                                        + "\n"
                                        + "Regards,"
                                        + "The support OJT team";
                            }
                            if (companyConfirm == 1) {
                                TblSemesterDAO semesterDAO = new TblSemesterDAO();
                                TblSemesterDTO currentSemester = semesterDAO.getCurrentSemester();
                                //update quantityInterns if accepted
                                companyPostDAO.updateQuantityInterns(companyPostID);
                                //update isIntern của sinh viên 
                                studentDAO.updateStatusInternOfStudent(studentCode, 1);
                                //update status all of application of student when the post was accepted by company
                                //get list application of this student except this accepted post
                                TblStudentDTO student = studentDAO.getStudentInfor(studentCode);
                                List<TblApplicationDTO> listApplicationOfStudent = applicationDAO.getApplicationOfAStudent(student);
                                List<TblApplicationDTO> listApplicationChageStatus = new ArrayList<>();
                                for (TblApplicationDTO application : listApplicationOfStudent) {
                                    //application đang waiting
                                    // true 0 0
                                    // true 1 0
                                    // true 1 2
                                    if (application.getStudent().getStudentCode().equals(student.getStudentCode())
                                            && application.getSemester().getSemesterID() == currentSemester.getSemesterID()
                                            && application.isStudentConfirm() == true && application.getSchoolConfirm() == 0
                                            && application.getCompanyConfirm() == 0
                                            || application.getStudent().getStudentCode().equals(student.getStudentCode())
                                            && application.getSemester().getSemesterID() == currentSemester.getSemesterID()
                                            && application.isStudentConfirm() == true
                                            && application.getSchoolConfirm() == 1
                                            && application.getCompanyConfirm() == 0
                                            || application.getStudent().getStudentCode().equals(student.getStudentCode())
                                            && application.getSemester().getSemesterID() == currentSemester.getSemesterID()
                                            && application.isStudentConfirm() == true
                                            && application.getSchoolConfirm() == 1
                                            && application.getCompanyConfirm() == 2) {
                                        listApplicationChageStatus.add(application);
                                    }
                                }
                                if (listApplicationChageStatus.isEmpty() == false) {
                                    //change status to cancel
                                    applicationDAO.changeStudentConfirmStatus(listApplicationChageStatus);
                                }
                                message = "The OJT system wants to announce that you were accepted for the job " + companyPostDTO.getTitle_Post() + " by " + companyPostDTO.getCompany().getAccount().getName() + " company."
                                        + " You should actively send email to the company " + companyPostDTO.getCompany().getAccount().getEmail() + " so that you can receive information about the job."
                                        + " Please click on the link " + link
                                        + " so as not to miss any information."
                                        + "\n"
                                        + "Regards,"
                                        + "The support OJT team";
                            }
                            MyApplicationHelper.sendEmail(studentInfor.getAccount(), systemAccount, message, subject);
                        }
                        String fullName = request.getParameter("txtFullName");
                        String email = request.getParameter("txtEmail");
                        String selectStatus = request.getParameter("status");
                        String xpage = request.getParameter("page");
                        url =  MyApplicationConstants.CompanyUpdateStatusIntershipApplicationFeature.COMPANY_SEARCH_INTERNS_CONTROLLER
                                +"?txtFullName="+fullName
                                +"&txtEmail="+email
                                +"&selectCompanyPost="+companyPostIDString
                                +"&status="+selectStatus
                                +"&page="+xpage;

                        response.sendRedirect(url);
                    }
                } else {
                    response.sendRedirect(url);
                }
            } else {
                response.sendRedirect(url);
            }
        } catch (NamingException ex) {
            log("NamingException at CompanyUpdateStatusIntershipApplicationServlet " + ex.getMessage());
        } catch (SQLException ex) {
            log("SQLException at CompanyUpdateStatusIntershipApplicationServlet " + ex.getMessage());
        } catch (AddressException ex) {
            log("AddressException at CreateNewCompanyPostServlet " + ex.getMessage());
        } catch (MessagingException ex) {
            log("MessagingException at CreateNewCompanyPostServlet " + ex.getMessage());
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
