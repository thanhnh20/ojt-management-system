/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se1625.controller;

import com.se1625.tblaccount.TblAccountDAO;
import com.se1625.tblaccount.TblAccountDTO;
import com.se1625.tblcompany.RegisterCompanyError;
import com.se1625.tblcompany.TblCompanyDAO;
import com.se1625.tblcompany.TblCompanyDTO;
import com.se1625.utils.MyApplicationConstants;
import com.se1625.utils.MyApplicationHelper;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
import org.apache.commons.fileupload.FileItem;

/**
 *
 * @author Thai Quoc Toan <toantqse151272@fpt.edu.vn>
 */
@WebServlet(name = "RegisterCompanyDetailsServlet", urlPatterns = {"/RegisterCompanyDetailsServlet"})
public class RegisterCompanyDetailsServlet extends HttpServlet {

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
        response.setCharacterEncoding("UTF-8");

        ServletContext context = this.getServletContext();
        Properties properties = (Properties) context.getAttribute("SITE_MAPS");
        String url = MyApplicationConstants.RegisterCompanyFeature.REGISTER_COMPANY_PAGE_1;

        HttpSession session = request.getSession(false);

        try {
            if (session != null) {
                TblAccountDTO accountCompany = (TblAccountDTO) session.getAttribute("ACCOUNT_COMPANY");
                if (accountCompany != null) {
                    url = properties.getProperty(MyApplicationConstants.RegisterCompanyFeature.REGISTER_COMPANY_PAGE_1);
                    // Parse the request
                    List<FileItem> items = (List<FileItem>) request.getAttribute("LIST_PARAMETERS");

                    Iterator<FileItem> iter = items.iterator();
                    HashMap<String, String> params = new HashMap<>();
                    String name = "";
                    String value = "";
                    String fileName = "";
                    String logoName = "";
                    long fileLength = 0;
                    while (iter.hasNext()) {
                        FileItem item = iter.next();

                        if (item.isFormField()) {
                            name = item.getFieldName();
                            value = item.getString("UTF-8");
                            params.put(name, value);
                        } else {
                            fileName = item.getName();
                            if (fileName == null || fileName.equals("")) {
                                break;
                            } else {
                                Path path = Paths.get(fileName);
                                String realPath = request.getServletContext().getRealPath("/avatars");
                                logoName = params.get("companyPhone") + "_" + path.getFileName().toString();
                                File uploadFile = new File(realPath + "/" + logoName);
                                if (Files.exists(Paths.get(realPath)) == false) {
                                    Files.createDirectories(Paths.get(realPath));
                                }
                                if (uploadFile.exists()) {
                                    // bị trùng tên
                                } else {
                                    item.write(uploadFile);
                                }
                                fileLength = Files.size(Paths.get(uploadFile.toString()));
                            }
                        }
                    }

                    TblAccountDAO accountDAO = new TblAccountDAO();
                    String companyName = params.get("companyName");
                    String city = params.get("city");
                    String address = params.get("companyAddress");
                    String description = params.get("companyDescription");
                    String phone = params.get("companyPhone");
                    String email = params.get("email");
                    String password = params.get("password");
                    boolean found = false;
                    RegisterCompanyError errors = new RegisterCompanyError();

                    if (companyName.trim().length() > 50 || companyName.trim().length() < 10) {
                        //quang loi
                        found = true;
                        errors.setCompanyNameLengthError("Company name is required 10-50 characters");
                    }
                    if (city.trim().equals("")) {
                        found = true;
                        errors.setCompanyCityError("City is required.");
                    }
                    if (address.trim().length() > 100 || address.trim().length() < 10) {
                        //quang loi
                        found = true;
                        errors.setCompanyAddressLengthError("Company address is required 10-100 characters");
                    }
                    if (description.trim().length() > 2000 || description.trim().length() < 50) {
                        //quang loi
                        found = true;
                        errors.setCompanyDescriptionLegthError("company description is required 50-2000 characters");
                    }
                    if (phone.trim().length() > 10 || phone.trim().length() < 10) {
                        //quang loi
                        found = true;
                        errors.setCompanyPhoneLengthError("company Phone is required 10 characters");
                    }
                    fileLength = (fileLength / (1024));
                    long sizeMax = 800;
                    if (fileName == null || fileName.equals("")) {
                        found = true;
                        errors.setCompanyLogoLengthError("Company Logo is required.");
                    } else {
                        if (fileLength > sizeMax) {
                            found = true;
                            errors.setCompanyLogoLengthError("File's size must not exceed 800KB");
                        } else {
                            if (logoName.endsWith(".png") == false
                                    && logoName.endsWith(".jpg") == false
                                    && logoName.endsWith(".jpeg") == false
                                    && logoName.endsWith(".svg") == false) {
                                found = true;
                                errors.setCompanyLogoTypeError("File type must be .png, .jpg, .jpeg, .svg.");
                            }
                        }
                    }

                    if (found) {
                        request.setAttribute("companyName", companyName);
                        request.setAttribute("city", city);
                        request.setAttribute("companyAddress", address);
                        request.setAttribute("companyDescription", description);
                        request.setAttribute("companyPhone", phone);
                        request.setAttribute("companyLogo", fileName);
                        request.setAttribute("ERROR_REGISTER_COMPANY", errors);
                        url = properties.getProperty(MyApplicationConstants.RegisterCompanyFeature.REGISTER_COMPANY_PAGE_2_JSP);
                    } else {
                        //loi add account company
                        accountCompany.setEmail(accountCompany.getEmail());
                        accountCompany.setPassword(accountCompany.getPassword());
                        accountCompany.setName(companyName);
                        accountCompany.setAvatar(logoName);
                        accountCompany.setIs_Admin(3);
                        //add account vao tblaccount
                        boolean isAdded = accountDAO.addAccountCompany(accountCompany);
                        if (isAdded) {
                            TblCompanyDTO companyDetail = new TblCompanyDTO();
                            companyDetail.setAccount(accountCompany);
                            companyDetail.setAddress(address);
                            companyDetail.setCity(city);
                            companyDetail.setCompany_Description(description);
                            companyDetail.setPhone(phone);
                            companyDetail.setIs_Signed(false);

                            TblCompanyDAO companyDAO = new TblCompanyDAO();
                            String lasID = companyDAO.getLastIDCompany();
                            String newComanyID = MyApplicationHelper.createIdCompany(lasID);
                            companyDetail.setCompanyID(newComanyID);

                            boolean statusAdded = companyDAO.AddCompany(companyDetail);
                            if (statusAdded) {
                                session.invalidate();
                                //hệ thống gửi email cho university để thống báo có công ty mới chưa signed
                                TblAccountDTO universityAccount = accountDAO.getAccountSchool();
                                TblAccountDTO systemAccount = accountDAO.GetAccountByRole(4);
                                String subject = "The new company registered into the system";
                                String link = "http://localhost:8080/OJTManagementSystem/AdminCompanyManagerController";
                                String message = "Dear " + universityAccount.getName() + ",\n"
                                                + "\n"
                                                + "The OJT system wants to announce that The new company regitered into the system. "
                                                + "The company name is " + companyName + " and Email is " + email + ". "
                                                + "Please click on the link " + link 
                                                + " so as not to miss any information.\n"
                                                + "\n"
                                                + "Regards,"
                                                + "The support OJT team";
                                MyApplicationHelper.sendEmail(universityAccount, systemAccount, message, subject);
                                url = properties.getProperty(MyApplicationConstants.RegisterCompanyFeature.LOGIN_PAGE);
                            }
                        }

                    }
                    RequestDispatcher rd = request.getRequestDispatcher(url);
                    rd.forward(request, response);
                }
            } else {
                response.sendRedirect(url);
            }

        } catch (SQLException ex) {
            log("SQLException occurs RegisterCompanyDetailsServlet " + ex.getMessage());
        } catch (NamingException ex) {
            log("NamingException occurs RegisterCompanyDetailsServlet " + ex.getMessage());
        } catch (AddressException ex) {
            log("AddressException at CreateNewCompanyPostServlet " + ex.getMessage());
        } catch (MessagingException ex) {
            log("MessagingException at CreateNewCompanyPostServlet " + ex.getMessage());
        }catch (Exception ex) {
            ex.printStackTrace();
            log("Exception occurs RegisterCompanyDetailsServlet " + ex.getMessage());
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
