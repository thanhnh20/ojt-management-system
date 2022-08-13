/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se1625.listener;

import com.se1625.tblcompany_post.TblCompany_PostDAO;
import com.se1625.tblcompany_post.TblCompany_PostDTO;
import com.se1625.utils.MyApplicationHelper;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Web application lifecycle listener.
 *
 * @author Thai Quoc Toan <toantqse151272@fpt.edu.vn>
 */
public class AutoCheckExpirationPostServletListener implements ServletContextListener {

    private Timer timer;
    private Properties properties;
    private long periodTime;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //get list expiration post
        final ServletContext context = sce.getServletContext();
        try {
            MyApplicationHelper.getCheckingExpirationPost(context);
            properties = (Properties) context.getAttribute("CHECKING_EXPIRATION_POST_TIME");
            periodTime = Long.parseLong(properties.getProperty("checkingPeriod"));
            startTimer(context);
        } catch (IOException ex) {
            context.log("IOException at AutoCheckExpirationPostServletListener " + ex.getMessage());
        }
    }

    private void startTimer(ServletContext servletContext) {
        timer = new Timer();
        // Delay 1 second to first execution
        long initialDelay = 1000;
        final ServletContext context = servletContext;
        final long oldPeriod = periodTime;
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                //nếu list expiration post != null thì thực hiện update
                try {
                    MyApplicationHelper.getCheckingExpirationPost(context);
                    properties = (Properties) context.getAttribute("CHECKING_EXPIRATION_POST_TIME");
                    periodTime = Long.parseLong(properties.getProperty("checkingPeriod"));
                    TblCompany_PostDAO companyPostDAO = new TblCompany_PostDAO();
                    List<TblCompany_PostDTO> companyExpirationPostList = companyPostDAO.getListExpirationPost();
                    if (companyExpirationPostList != null) {
                        if (companyExpirationPostList.size() > 0) {
                            //update status cho các bài post hết hạn này
                            for (TblCompany_PostDTO expirationPost : companyExpirationPostList) {
                                companyPostDAO.updateStatusForExpirationPost(expirationPost.getPostID());
                            }
                        }
                    }
                    if (oldPeriod != periodTime) {
                        this.cancel();
                        startTimer(context);
                    }
                } catch (SQLException ex) {
                    context.log("SQLException at AutoCheckExpirationPostServletListener " + ex.getMessage());
                } catch (NamingException ex) {
                    context.log("NamingException at AutoCheckExpirationPostServletListener " + ex.getMessage());
                } catch(IOException ex) {
                    context.log("IOException at AutoCheckExpirationPostServletListener " + ex.getMessage());
                }
            }
        };
        timer.schedule(timerTask, initialDelay, periodTime);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        sce.getServletContext().removeAttribute("CHECKING_EXPIRATION_POST_TIME");
        timer.cancel();
    }
}
