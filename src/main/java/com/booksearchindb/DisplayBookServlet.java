package com.booksearchindb;

import com.pojo.BookPojo;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "DisplayBookServlet", value = "/DisplayBookServlet")
public class DisplayBookServlet extends HttpServlet {
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

        List<BookPojo> bookList = (List<BookPojo>) req.getAttribute("books");
        String subject = (String) req.getAttribute("subjectName");
        pw.println("<html>");
        pw.println("<head>");
        pw.println("<title>Book Details</title>");
        pw.println("</head>");
        pw.println("<body>");
        if(bookList.isEmpty()) {
            pw.println("<h3 style='color : red;'>No book found for "+subject+"!</h3>");
        }

        pw.println("<h1>Books Details</h1>");
        pw.println("<p><strong>Total books found: "+bookList.size()+"</strong></p>");
        pw.println("<table border='2'>");
        pw.println("<tr><th>BookId</th><th>BookName</th><th>BookPrice</th><th>Subject</th></tr>");
        for(BookPojo book : bookList) {
            pw.println("<tr><td>" + book.getBookId() + "</td><td>" + book.getBookName() + "</td><td>" + book.getBookPrice() + "</td><td>" + book.getSubject() + "</td></tr>");
        }
        pw.println("</table>");
        pw.println("</body>");
        pw.println("</html>");
        pw.close();
    }
}