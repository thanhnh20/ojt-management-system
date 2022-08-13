/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se1625.listener;

import com.se1625.tblcompany_post.TblCompany_PostDAO;
import com.se1625.tblcompany_post.TblCompany_PostDTO;
import com.se1625.utils.MyApplicationConstants;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * Web application lifecycle listener.
 *
 * @author Thai Quoc Toan <toantqse151272@fpt.edu.vn>
 */
public class StudentApplyCompanyJobServletListener implements ServletRequestListener {

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
        request.removeAttribute("ERROR_RUN_OUT_QUANTITY_INTERNS");
        request.removeAttribute("LIST_PARAMETERS");
        request.removeAttribute("ERROR_RUN_OUT_QUANTITY_INTERNS_COMPANY_DETAILS");
    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        ServletContext context = sre.getServletContext();
        
        try {
            HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
            request.setCharacterEncoding("UTF-8");
            String button = request.getParameter("btAction");
            if (button != null) {
                
                if (button.equals("Apply")) {
                    int postID = Integer.parseInt(request.getParameter("postID"));
                    //check quantity of the post
                    TblCompany_PostDAO companyPostDAO = new TblCompany_PostDAO();
                    TblCompany_PostDTO companyPost = companyPostDAO.getCompanyPost(postID);
                    if (companyPost.getQuantityIterns() == 0) {
                        request.setAttribute("ERROR_RUN_OUT_QUANTITY_INTERNS_COMPANY_DETAILS", "This job of " + companyPost.getCompany().getAccount().getName()
                                    + " run out of the number of vacancies");
                    }
                }
            } else {
                // get parameters
                // Create a factory for disk-based file items
                DiskFileItemFactory factory = new DiskFileItemFactory();

                File repository = (File) context.getAttribute("javax.servlet.context.tempdir");
                factory.setRepository(repository);

                // Create a new file upload handler
                ServletFileUpload upload = new ServletFileUpload(factory);
                if (upload.isMultipartContent(request)) {
                    // Parse the request
                    List<FileItem> items = upload.parseRequest(request);
                    Iterator<FileItem> iter = items.iterator();
                    HashMap<String, String> params = new HashMap<>();
                    String name = "";
                    String value = "";
                    while (iter.hasNext()) {
                        FileItem item = iter.next();
                        if (item.isFormField()) {
                            name = item.getFieldName();
                            value = item.getString("UTF-8");
                            params.put(name, value);
                        }
                    }
                    button = params.get("btAction");
                    if ("Apply Now".equals(button)) {
                        int postID = Integer.parseInt(params.get("postID"));
                        //check quantity của bài post còn hay không
                        TblCompany_PostDAO companyPostDAO = new TblCompany_PostDAO();
                        TblCompany_PostDTO companyPost = companyPostDAO.getCompanyPost(postID);
                        if (companyPost.getQuantityIterns() == 0) {
                            request.setAttribute("ERROR_RUN_OUT_QUANTITY_INTERNS", "This job of " + companyPost.getCompany().getAccount().getName()
                                    + " run out of the number of vacancies");
                        }
                    }
                    request.setAttribute("LIST_PARAMETERS", items);
                }
            }
        } catch (SQLException ex) {
            context.log("SQLException at StudentApplyCompanyJobServletListener " + ex.getMessage());
        } catch (NamingException ex) {
            context.log("NamingException at StudentApplyCompanyJobServletListener " + ex.getMessage());
        } catch (FileUploadException ex) {
            context.log("FileUploadException at StudentApplyCompanyJobServletListener " + ex.getMessage());
        } catch (UnsupportedEncodingException ex) {
            context.log("UnsupportedEncodingException at StudentApplyCompanyJobServletListener " + ex.getMessage());
        }
    }
}
