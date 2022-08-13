/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se1625.controller;

import com.se1625.tblaccount.TblAccountDTO;
import com.se1625.utils.MyApplicationConstants;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Thai Quoc Toan <toantqse151272@fpt.edu.vn>
 */
public class DownloadFileModelServlet extends HttpServlet {

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

        HttpSession session = request.getSession(false);
        String url = MyApplicationConstants.DownloadFileModelFeature.LOGIN_PAGE;
        if (session != null) {
            TblAccountDTO admin = (TblAccountDTO) session.getAttribute("ADMIN_ROLE");
            if (admin != null) {
                String dowloadFile = getServletContext().getRealPath("/excels/FileImportStudent_OJT.xlsx");
                File dwFile = new File(dowloadFile);
                response.setContentType("APPLICATION/OCTET-STREAM");
                //force to download
                String hkey = "Content-Disposition";
                String hvalue = String.format("attachment; filename=\"%s\"", dwFile.getName());
                response.setHeader(hkey, hvalue);
                OutputStream output = response.getOutputStream();
                FileInputStream input = new FileInputStream(dwFile);
                byte[] buffer = new byte[1024 * 10];
                int i = -1;
                while ((i = input.read(buffer)) != -1) {
                    output.write(buffer, 0, i);
                }

                input.close();
                output.close();
            } else {
                response.sendRedirect(url);
            }
        } else {
            response.sendRedirect(url);
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
