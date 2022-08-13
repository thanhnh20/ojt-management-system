/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se1625.controller;

import com.se1625.tblaccount.TblAccountDAO;
import com.se1625.tblaccount.TblAccountDTO;
import com.se1625.tblsemester.TblSemesterDAO;
import com.se1625.tblsemester.TblSemesterDTO;
import com.se1625.tblstudent.TblStudentDTO;
import com.se1625.utils.MyApplicationConstants;
import com.se1625.utils.MyApplicationHelper;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Iterator;
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
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author Thai Quoc Toan <toantqse151272@fpt.edu.vn>
 */
public class ImportStudentExcelFileServlet extends HttpServlet {

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
        HttpSession session = request.getSession(false);
        String url = MyApplicationConstants.ImportStudentExcelFileFeature.LOGIN_PAGE;
        String fileError = "";
        try {
            if (session != null) {
                TblAccountDTO admin = (TblAccountDTO) session.getAttribute("ADMIN_ROLE");
                if (admin != null) {
                    List<FileItem> items = (List<FileItem>) request.getAttribute("LIST_PARAMETERS");

                    Iterator<FileItem> iter = items.iterator();
                    String filePath = "";
                    String fileName = "";
                    String fileImportPath = "";

                    while (iter.hasNext()) {
                        FileItem item = iter.next();

                        if (item.isFormField()) {
                        } else {
                            filePath = item.getName();
                            if (filePath == null || filePath.equals("")) {
                                break;
                            } else {
                                Path path = Paths.get(filePath);
                                String realPath = request.getServletContext().getRealPath("/excels");
                                fileName = path.getFileName().toString();
                                File uploadFile = new File(realPath + "/" + fileName);

                                fileImportPath = uploadFile.toString();
                                if (Files.exists(Paths.get(realPath)) == false) {
                                    Files.createDirectories(Paths.get(realPath));
                                }
                                item.write(uploadFile);
                                fileError = fileImportPath;
                            }
                        }
                    }
                    if (fileName.isEmpty()) {
                        request.setAttribute("ERROR_IMPORT_EXCEL", "Please Choose file to import!");
                        url = properties.getProperty(MyApplicationConstants.ImportStudentExcelFileFeature.ADMIN_STUDENT_MANAGEMENT_PAGE);
                        RequestDispatcher rd = request.getRequestDispatcher(url);
                        rd.forward(request, response);
                    } else {
                        //check size của file
                        List<TblStudentDTO> studentList = MyApplicationHelper.readExcel(fileImportPath);
                        if (studentList != null) {
                            TblAccountDAO dao = new TblAccountDAO();
                            TblSemesterDAO semesterDAO = new TblSemesterDAO();
                            TblSemesterDTO currentSemester = semesterDAO.getCurrentSemester();
                            for (TblStudentDTO student : studentList) {
                                if (dao.checkExistedAccount(student.getAccount().getEmail()) == false) {
                                    dao.addStudentAccount(student, currentSemester.getSemesterID());
                                }
                            }
                            Files.deleteIfExists(Paths.get(fileImportPath));
                            url = MyApplicationConstants.ImportStudentExcelFileFeature.ADMIN_STUDENT_MANAGEMENT_PAGE;
                            response.sendRedirect(url);
                        }
                    }
                } else {
                    response.sendRedirect(url);
                }
            } else {
                response.sendRedirect(url);
            }
        } catch (FileUploadException ex) {
            log("FileUploadException at ImportStudentExcelFileServlet " + ex.getMessage());
        } catch (IOException ex) {
            log("IOException at ImportStudentExcelFileServlet " + ex.getMessage());
        } catch (SQLException ex) {
            log("SQLException at ImportStudentExcelFileServlet " + ex.getMessage());
        } catch (NamingException ex) {
            log("NamingException at ImportStudentExcelFileServlet " + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            log("IllegalArgumentException occurs ImportStudentExcelFileServlet " + ex.getMessage());
            if ("The specified file is not Excel file".equals(ex.getMessage())) {
                request.setAttribute("ERROR_IMPORT_EXCEL", "This file is not a excel(.xlsx) file. Please check again!");
                url = properties.getProperty(MyApplicationConstants.ImportStudentExcelFileFeature.ADMIN_STUDENT_MANAGEMENT_PAGE);
                RequestDispatcher rd = request.getRequestDispatcher(url);
                rd.forward(request, response);
                Files.deleteIfExists(Paths.get(fileError));
            }
        } catch (Exception ex) {
            log("Exception at ImportStudentExcelFileServlet " + ex.getMessage());
            if ("Sheet is empty".equals(ex.getMessage())) {
                request.setAttribute("ERROR_IMPORT_EXCEL", "This file is empty. Please check again!");
                url = properties.getProperty(MyApplicationConstants.ImportStudentExcelFileFeature.ADMIN_STUDENT_MANAGEMENT_PAGE);
                RequestDispatcher rd = request.getRequestDispatcher(url);
                rd.forward(request, response);
                Files.deleteIfExists(Paths.get(fileError));
            }
            if ("Error Import Excel File".equals(ex.getMessage())) {
                request.setAttribute("ERROR_IMPORT_EXCEL", "This file has some invalid data. Please check again!");
                url = properties.getProperty(MyApplicationConstants.ImportStudentExcelFileFeature.ADMIN_STUDENT_MANAGEMENT_PAGE);
                RequestDispatcher rd = request.getRequestDispatcher(url);
                rd.forward(request, response);
                Files.deleteIfExists(Paths.get(fileError));
            }
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
