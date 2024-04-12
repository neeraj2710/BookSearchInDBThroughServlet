package com.booksearchindb;

import com.pojo.BookPojo;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "BookSearchServlet", value = "/BookSearchServlet")
public class BookSearchServlet extends HttpServlet {
    PreparedStatement ps;
    @Override
    public void init() throws ServletException {
        ServletContext ctxt = super.getServletContext();
        ServletConfig cfg = super.getServletConfig();

        Connection conn = (Connection) ctxt.getAttribute("dbconnection");
        try{
            if(conn == null)
                throw new SQLException();

            ps = conn.prepareStatement("select * from allbooks where subject=?");
        }catch (SQLException ex){
            throw new ServletException(ex.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    protected void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter pw = resp.getWriter();
        resp.setContentType("text/html");
        RequestDispatcher rd = null;
        String subjectName = req.getParameter("subject");
        if(subjectName.isEmpty()){
            rd = req.getRequestDispatcher("home.html");
            pw.println("<h3 style='color : red;'>Please input a subject name!!</h3>");
            rd.include(req, resp);
        }
        else{
            try {

                ps.setString(1,subjectName);
                ResultSet rs = ps.executeQuery();
                List<BookPojo> bookList = new ArrayList<>();
                while (rs.next()) {
                    BookPojo book = new BookPojo();
                    book.setBookId(rs.getInt("bookid"));
                    book.setBookName(rs.getString("bookname"));
                    book.setBookPrice(rs.getDouble("bookprice"));
                    book.setSubject(rs.getString("subject"));
                    bookList.add(book);
                }
                req.setAttribute("subjectName", subjectName);
                req.setAttribute("books", bookList);
                rd = req.getRequestDispatcher("DisplayBookServlet");
                rd.forward(req, resp);

            }catch (SQLException ex){
                throw new ServletException(ex.getMessage());
            }
        }
    }
}