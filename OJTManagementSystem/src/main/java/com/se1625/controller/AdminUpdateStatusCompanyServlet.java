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
import com.se1625.tblcompany.TblCompanyDAO;
import com.se1625.tblcompany.TblCompanyDTO;
import com.se1625.tblcompany_post.TblCompany_PostDAO;
import com.se1625.tblcompany_post.TblCompany_PostDTO;
import com.se1625.tblsemester.TblSemesterDAO;
import com.se1625.tblsemester.TblSemesterDTO;
import com.se1625.utils.MyApplicationConstants;
import com.se1625.utils.MyApplicationHelper;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
public class AdminUpdateStatusCompanyServlet extends HttpServlet {

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

        String companyID = request.getParameter("companyID");
        String companyStatus = request.getParameter("Status");
        String page = request.getParameter("page");
        ServletContext context = this.getServletContext();
        Properties prop = (Properties) context.getAttribute("SITE_MAPS");

        String url = MyApplicationConstants.AdminUpdateStatusCompanyFeature.LOGIN_PAGE;
        HttpSession session = request.getSession(false);
        try {
            boolean status = false;
            if ("Success".equals(companyStatus)) {
                status = true;
            }
            if (session != null) {
                TblAccountDTO accountDTO = (TblAccountDTO) session.getAttribute("ADMIN_ROLE");
                if (accountDTO != null) {
                    TblCompanyDAO companyDAO = new TblCompanyDAO();
                    boolean action = true;
                    if (status == false) {
                        // cần check xem trong công ty có có sinh viên đang thực tập hay không
                        // nếu có thì thông báo có sinh viên đang thực tập chưa thể chuyển trạng thái kí kết
                        // nếu không có thì 
                        //cần check xem công ty đó đang có sinh viên applied không
                        //nếu có thì báo sẽ chuyển trạng thái sang schoolConfirm = denied (0)
                        //cần check xem công ty đó đang có bài post nào không
                        // nếu có thì sẽ chuyển trạng thái isActive sang unactive (0)
                        TblApplicationDAO applicationDAO = new TblApplicationDAO();
                        TblSemesterDAO semesterDAO = new TblSemesterDAO();
                        TblSemesterDTO currentSemester = semesterDAO.getCurrentSemester();
                        List<TblApplicationDTO> listApplication = applicationDAO.getListApplicationOfCompany(companyID, currentSemester.getSemesterID());
                        int numberOfStudents = 0;
                        if (listApplication != null) {
                            for (TblApplicationDTO tblApplicationDTO : listApplication) {
                                if (tblApplicationDTO.isStudentConfirm() && (tblApplicationDTO.getCompanyConfirm() == 1 || tblApplicationDTO.getCompanyConfirm() == 2)
                                        && tblApplicationDTO.getSchoolConfirm() == 1
                                        && (tblApplicationDTO.getStudent().getIsIntern() == 1 || tblApplicationDTO.getStudent().getIsIntern() == 0)) {
                                    numberOfStudents++;
                                }
                            }
                        }
                        if (numberOfStudents > 0) {
                            action = false;
                            request.setAttribute("WARING_CHANGE_SIGNING_STATUS", "This company has " + numberOfStudents + " students who are joining the internship.");
                            url = prop.getProperty(MyApplicationConstants.AdminUpdateStatusCompanyFeature.ADMIN_COMPANY_MANAGER_CONTROLLER);
                            RequestDispatcher rd = request.getRequestDispatcher(url);
                            rd.forward(request, response);
                        } else {
                            if (listApplication != null) {
                                for (TblApplicationDTO tblApplicationDTO : listApplication) {
                                    if (tblApplicationDTO.isStudentConfirm() && tblApplicationDTO.getCompanyConfirm() == 0
                                            && tblApplicationDTO.getSchoolConfirm() == 1) {
                                        applicationDAO.changeStatusSchool(tblApplicationDTO.getApplicationID(), -1);
                                    }
                                }
                            }
                            TblCompany_PostDAO companyPostDAO = new TblCompany_PostDAO();
                            List<TblCompany_PostDTO> listCompanyPost = companyPostDAO.getAllPostByCompanyID(companyID);
                            if (listCompanyPost != null) {
                                for (TblCompany_PostDTO tblCompany_PostDTO : listCompanyPost) {
                                    companyPostDAO.updateStatusForExpirationPost(tblCompany_PostDTO.getPostID(), 0);
                                }
                            }
                        }
                    }
                    if (action) {
                        boolean result = companyDAO.updateCompanyStatus(companyID, status);
                        TblCompanyDTO newCompany = companyDAO.getCompany(companyID);
                        session.setAttribute("COMPANY_ROLE_INFO", newCompany);
                        if (result) {
                            TblCompanyDTO company = companyDAO.getCompany(companyID);
                            TblAccountDAO accountDAO = new TblAccountDAO();
                            TblAccountDTO systemAccount = accountDAO.GetAccountByRole(4);
                            if (status) {
                                String subject = "The signing result";
                                String message = "Dear " + company.getAccount().getName() + " company,\n"
                                        + "\n"
                                        + "The OJT system wants to announce that your company was changed signed status by FPT University."
                                        + "You can create new posts to start recruiting interns for your jobs.\n"
                                        + "\n"
                                        + "Regards,\n"
                                        + "The support OJT team";
                                MyApplicationHelper.sendEmail(company.getAccount(), systemAccount, message, subject);
                                url = MyApplicationConstants.AdminUpdateStatusCompanyFeature.ADMIN_COMPANY_MANAGER_CONTROLLER
                                        + "?page=" + page;
                                response.sendRedirect(url);
                            } else {
                                String subject = "The signing result";
                                String message = "Dear " + company.getAccount().getName() + " company,\n"
                                        + "\n"
                                        + "The OJT system wants to announce that your company was changed unsigned status by FPT University."
                                        + "\n"
                                        + "Regards,\n"
                                        + "The support OJT team";
                                MyApplicationHelper.sendEmail(company.getAccount(), systemAccount, message, subject);
                                url = MyApplicationConstants.AdminUpdateStatusCompanyFeature.ADMIN_COMPANY_MANAGER_CONTROLLER
                                        + "?page=" + page;
                                response.sendRedirect(url);
                            }
                        }
                    }
                } else {
                    response.sendRedirect(url);
                }
            } else {
                response.sendRedirect(url);
            }
        } catch (NamingException ex) {
            log("AdminUpdateStatusCompanyServlet_NamingException " + ex.getMessage());
        } catch (SQLException ex) {
            log("AdminUpdateStatusCompanyServlet_SQLException " + ex.getMessage());
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
