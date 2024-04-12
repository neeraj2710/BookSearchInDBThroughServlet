package com.booksearchindb;

import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBListener implements ServletContextListener, HttpSessionListener, HttpSessionAttributeListener {

    private Connection conn;
    public DBListener() {
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        /* This method is called when the servlet context is initialized(when the Web application is deployed). */
        ServletContext ctxt = sce.getServletContext();
        String url = ctxt.getInitParameter("url");
        String username = ctxt.getInitParameter("username");
        String password = ctxt.getInitParameter("password");
        try {
            conn = DriverManager.getConnection(url,username,password);
        }catch (SQLException ex){
            conn = null;
            System.out.println("Error in opening connection: "+ex.getMessage());
        }finally {
            ctxt.setAttribute("dbconnection",conn);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        /* This method is called when the servlet Context is undeployed or Application Server shuts down. */
        try {
            if(conn!=null)
                conn.close();
        }catch (SQLException ex){
            System.out.println("Error in closing the connection: "+ex.getMessage());
        }
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        /* Session is created. */
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        /* Session is destroyed. */
    }

    @Override
    public void attributeAdded(HttpSessionBindingEvent sbe) {
        /* This method is called when an attribute is added to a session. */
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent sbe) {
        /* This method is called when an attribute is removed from a session. */
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent sbe) {
        /* This method is called when an attribute is replaced in a session. */
    }
}