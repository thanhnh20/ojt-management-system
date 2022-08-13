/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se1625.controller;

import com.se1625.tblaccount.TblAccountDAO;
import com.se1625.tblaccount.TblAccountDTO;
import com.se1625.tblmajor.TblMajorDAO;
import com.se1625.tblsemester.TblSemesterDAO;
import com.se1625.tblsemester.TblSemesterDTO;
import com.se1625.tblstudent.AddStudentError;
import com.se1625.tblstudent.TblStudentDAO;
import com.se1625.tblstudent.TblStudentDTO;
import com.se1625.utils.MyApplicationConstants;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import java.util.regex.Pattern;
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
public class AdminAddStudentServlet extends HttpServlet {

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

        String studentID = request.getParameter("txtStudentID");
        String studentName = request.getParameter("txtStudentName");
        String major = request.getParameter("majorID");
        String email = request.getParameter("txtEmail");
        String phone = request.getParameter("txtPhone");
        String creditString = request.getParameter("txtCredit");
        int credit = -1;

        ServletContext context = this.getServletContext();
        Properties properties = (Properties) context.getAttribute("SITE_MAPS");
        String url = MyApplicationConstants.AdminAddStudentFeature.LOGIN_PAGE;

        HttpSession session = request.getSession(false);

        AddStudentError errors = new AddStudentError();
        boolean found = false;
        String patternNumberPhone = "^(03|05|07|08|09)([0-9]{8,8})$";
        try {
            if (session != null) {
                TblAccountDTO adminAccount = (TblAccountDTO) session.getAttribute("ADMIN_ROLE");
                if (adminAccount != null) {
                    if (studentID.trim().length() != 8) {
                        found = true;
                        errors.setStudentIDLengthError("Student ID is required 8 characters.(Example: SE151272)");
                    } else {
                        TblStudentDAO studentDAO = new TblStudentDAO();
                        boolean checkExistedStudentID = studentDAO.checkExistedStudent(studentID);
                        if (checkExistedStudentID) {
                            found = true;
                            errors.setExistedStudentIDError("Student ID is existed in the system.");
                        }
                    }

                    if (studentName.trim().length() == 0 || studentName.trim().length() > 50 || studentName.trim().length() < 6) {
                        found = true;
                        errors.setStudentNameLengthError("Student name is required 6 - 50 character.");
                    } else {
                        String patternName = "^[^\\p{L}\\s]*$";
                        if (studentName.matches(patternName)) {
                            found = true;
                            errors.setStudentNameContainSpecialCharacter("Student name does not contain any special characters.");
                        } else {
                            char[] nameArray = studentName.toCharArray();
                            boolean isNumber = false;
                            for (char c : nameArray) {
                                if (Character.isDigit(c)) {
                                    isNumber = true;
                                    break;
                                }
                            }
                            if (isNumber) {
                                found = true;
                                errors.setStudentNameContainSpecialCharacter("Student name does not contain any special characters.");
                            } else {
                                String pattern = "[\\p{L}\\s]*";
                                if (studentName.matches(pattern) == false) {
                                    found = true;
                                    errors.setStudentNameContainSpecialCharacter("Student name does not contain any special characters.");
                                }
                            }
                        }
                    }

                    if (major.trim().length() == 0) {
                        found = true;
                        errors.setMajorLengthError("Major is required.");
                    }

                    if (email.trim().length() == 0 || email.trim().length() > 50) {
                        found = true;
                        errors.setEmailLengthError("Email is required less than 50 characters.");
                    } else {
                        if (email.endsWith("@fpt.edu.vn") == false) {
                            found = true;
                            errors.setEmailFormatError("Email format is not suitable.");
                        }
                        TblAccountDAO accountDAO = new TblAccountDAO();
                        boolean checkAccount = accountDAO.checkExistedAccount(email);
                        if (checkAccount) {
                            found = true;
                            errors.setExistedEmailError("Email is existed in the system.");
                        }
                    }

                    if (phone.matches(patternNumberPhone) == false) {
                        found = true;
                        errors.setPhoneFormatError("Phone format is not suitable.");
                    } else {
                        TblStudentDAO studentDAO = new TblStudentDAO();
                        boolean checkExistedPhone = studentDAO.checkExistedNumberPhone(phone);
                        if (checkExistedPhone) {
                            found = true;
                            errors.setExistedPhoneError("Phone is used by another student.");
                        }
                    }
                    if (creditString.trim().length() == 0) {
                        found = true;
                        errors.setCreditEmptyError("Credit is required.");
                    } else {
                        credit = Integer.parseInt(creditString);
                        if (credit < 68 || credit > 200) {
                            found = true;
                            errors.setCreditError("Credit must be more than 68.");
                        }
                    }

                    if (found) {
                        request.setAttribute("ERRORS", errors);
                        url = properties.getProperty(MyApplicationConstants.AdminAddStudentFeature.ADMIN_SHOW_ADDING_STUDENT_CONTROLLER);
                        RequestDispatcher rd = request.getRequestDispatcher(url);
                        rd.forward(request, response);
                    } else {
                        TblStudentDTO student = new TblStudentDTO();
                        student.setStudentCode(studentID);
                        student.setMajor(major);
                        student.setPhone(phone);
                        student.setNumberOfCredit(credit);
                        TblAccountDTO account = new TblAccountDTO();
                        account.setEmail(email);
                        account.setName(studentName);
                        student.setAccount(account);
                        TblSemesterDAO semesterDAO = new TblSemesterDAO();
                        TblSemesterDTO semester = semesterDAO.getCurrentSemester();
                        TblAccountDAO accountDAO = new TblAccountDAO();
                        boolean result = accountDAO.addStudentAccount(student, semester.getSemesterID());
                        if (result) {
                            url = properties.getProperty(MyApplicationConstants.AdminAddStudentFeature.SHOW_ADMIN_STUDENT_MANAGEMENT_CONTROLLER);
                            response.sendRedirect(url);
                        }
                    }   
                } else {
                    response.sendRedirect(url);
                }
            } else {
                response.sendRedirect(url);
            }
        } catch (NamingException ex) {
            log("NamingException at AdminShowAddingStudentServlet " + ex.getMessage());
        } catch (SQLException ex) {
            log("SQLException at AdminShowAddingStudentServlet " + ex.getMessage());
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
