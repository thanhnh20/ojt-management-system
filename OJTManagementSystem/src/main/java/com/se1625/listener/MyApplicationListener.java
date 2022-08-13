    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se1625.listener;

import com.se1625.utils.MyApplicationHelper;
import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Web application lifecycle listener.
 *
 * @author Thai Quoc Toan <toantqse151272@fpt.edu.vn>
 */
public class MyApplicationListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //1. get servletContext
        ServletContext context = sce.getServletContext();
        //2. getSiteMaps
        try {
            MyApplicationHelper.getSiteMaps(context);
        } catch (IOException ex) {
            context.log("IO Exception occours in process "
                    + "at MyApplicationListener", ex.getCause());
        } 
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        context.removeAttribute("SITE_MAPS");
    }
}
