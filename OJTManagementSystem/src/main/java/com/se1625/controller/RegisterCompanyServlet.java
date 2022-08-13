/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se1625.controller;

import com.se1625.tblaccount.RegisterCompanyAccountError;
import com.se1625.tblaccount.TblAccountDAO;
import com.se1625.tblaccount.TblAccountDTO;
import com.se1625.utils.MyApplicationConstants;
import com.se1625.utils.MyApplicationHelper;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Thai Quoc Toan <toantqse151272@fpt.edu.vn>
 */
@WebServlet(name = "RegisterCompanyServlet", urlPatterns = {"/RegisterCompanyServlet"})
public class RegisterCompanyServlet extends HttpServlet {

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
        request.setCharacterEncoding("UTF-8");

        ServletContext context = this.getServletContext();
        Properties properties = (Properties) context.getAttribute("SITE_MAPS");
        String url = MyApplicationConstants.RegisterCompanyFeature.REGISTER_COMPANY_PAGE_1;

        TblAccountDAO accountDAO = new TblAccountDAO();
        try {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String confirmPassword = request.getParameter("confirmPassword");

            String parttern = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";

            boolean errorFound = false;
            RegisterCompanyAccountError error = new RegisterCompanyAccountError();
            url = properties.getProperty(MyApplicationConstants.RegisterCompanyFeature.REGISTER_COMPANY_PAGE_1_JSP);
            if (email.trim().matches(parttern) == false) {
                errorFound = true;
                error.setEmailFormatError("Email is incorrect format.");
            } else {
                boolean isExist = accountDAO.checkExistedAccount(email.trim());
                if (isExist) {
                    errorFound = true;
                    error.setEmailDuplicateError("Email is existed.");
                }
            }
            if (password.trim().length() < 6 || password.trim().length() > 20) {
                errorFound = true;
                error.setPasswordLengthError("Password is required with 6 to 20 characters.");
            } else if (password.trim().equals(confirmPassword.trim()) == false) {
                errorFound = true;
                error.setPasswordConfirmError("Confirm password does not match with your password.");
            }
            HttpSession session = request.getSession();
            if (errorFound) {
                // thong bao lai trang register
                request.setAttribute("ERROR_REGISTER", error);
                session.removeAttribute("ACCOUNT_COMPANY");
            } else {
                //gui ma xac nhan
                //TblAccountDTO accountSchool = accountDAO.getAccountSchool();
                TblAccountDTO systemAccount = accountDAO.GetAccountByRole(4);
                TblAccountDTO accountCompany = new TblAccountDTO();
                accountCompany.setEmail(email);
                accountCompany.setPassword(password);
                String code = MyApplicationHelper.getRandom();
                accountCompany.setVerifyCode(code);
                String subject = "User Email Verification";
                String message = "Dear " + accountCompany.getEmail() + " ,\n"
                        + "\n"
                        + "Your verification code is " + accountCompany.getVerifyCode() + ".\n"
                        + "\n"
                        + "Enter this code in our website to activate your account.\n"
                        + "\n"
                        + "If you have any questions, send us an email " + systemAccount.getEmail() + ".\n"
                        + "\n"
                        + "We’re glad you’re here,\n"
                        + "The support OJT team";
                boolean test = MyApplicationHelper.sendEmail(accountCompany, systemAccount, message, subject);
                if (test) {
                    //request.setAttribute("AccountCompany", accountCompany);
                    session.setAttribute("ACCOUNT_COMPANY", accountCompany);
                }
            }
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
        } catch (SQLException ex) {
            log("SQLException occurs RegisterCompanyServlet " + ex.getMessage());
        } catch (NamingException ex) {
            log("NamingException occurs RegisterCompanyServlet " + ex.getMessage());
        } catch (AddressException ex) {
            log("AddressException occurs RegisterCompanyServlet " + ex.getMessage());
        } catch (MessagingException ex) {
            log("MessagingException occurs RegisterCompanyServlet " + ex.getMessage());
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
