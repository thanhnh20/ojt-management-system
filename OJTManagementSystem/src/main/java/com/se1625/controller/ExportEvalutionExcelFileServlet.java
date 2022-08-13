/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se1625.controller;

import com.se1625.tblaccount.TblAccountDTO;
import com.se1625.tblapplication.TblApplicationDAO;
import com.se1625.tblapplication.TblApplicationDTO;
import com.se1625.utils.MyApplicationConstants;
import com.se1625.utils.MyApplicationHelper;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Thai Quoc Toan <toantqse151272@fpt.edu.vn>
 */
public class ExportEvalutionExcelFileServlet extends HttpServlet {

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
        String url = MyApplicationConstants.ExportEvalutionExcelFileFeature.LOGIN_PAGE;
        // get list cần ghi vào excel
        // studentCode, Major, CompanyName, Job, Grade, Evalution ,status
        // tblStudent, tblAccount, tblcompany, tblcompany_Post, tblApplication
        try {
            if (session != null) {
                TblAccountDTO admin = (TblAccountDTO) session.getAttribute("ADMIN_ROLE");
                if (admin != null) {
                    String semesterID = request.getParameter("semesterID");
                    TblApplicationDAO applicationDAO = new TblApplicationDAO();
                    applicationDAO.getApplicationToWriteExcel(Integer.parseInt(semesterID));
                    List<TblApplicationDTO> listApplication = applicationDAO.getListApplication();
                    String table = "evaluarionStudent";
                    String realPath = getServletContext().getRealPath("/excels");
                    if (Files.exists(Paths.get(realPath)) == false) {
                        Files.createDirectories(Paths.get(realPath));
                    }
                    String excelPath = realPath + "/" + table.concat(".xlsx");
                    //create workbook
                    Workbook workbook = new XSSFWorkbook();

                    //create sheet
                    Sheet sheet = workbook.createSheet(table);

                    int rowIndex = 0;
                    MyApplicationHelper.writeHeaderLine(sheet, rowIndex);

                    rowIndex++;
                    for (TblApplicationDTO application : listApplication) {
                        //create row
                        Row row = sheet.createRow(rowIndex);
                        //write Data on row
                        MyApplicationHelper.writeStudentEvaluation(application, row);
                        rowIndex++;
                    }

                    MyApplicationHelper.createExcelFile(workbook, excelPath);

                    String dowloadFile = realPath + File.separator + table.concat(".xlsx");

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
        } catch (SQLException ex) {
            log("SQLException occurs ExportEvalutionExcelFileServlet " + ex.getMessage());
        } catch (NamingException ex) {
            log("SQLException occurs ExportEvalutionExcelFileServlet " + ex.getMessage());
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
