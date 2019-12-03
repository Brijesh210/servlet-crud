/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.brijesh.servlet.book;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import pl.polsl.brijesh.ejb.model.BookBean;
import pl.polsl.brijesh.ejb.model.Book;
import pl.polsl.brijesh.ejb.model.User_;

/**
 *
 * @author b___b
 */
public class FindBookServlet extends HttpServlet {

    @EJB
    BookBean bookController;

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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title> Find Book Servlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1> Find Book by Id  and Find All Book</h1>");
            out.println("<form action =\"FindBookServlet?find=1\" method=\"post\">");
            out.println("Find All Book: <input type=\"submit\" value=\"FiindAll\"><br/></br>");
            out.println("</form>");
            out.println("<form action =\"FindBookServlet?find=2\" method=\"post\">");
            out.println("Find book by Id: <input typ=\"text\" name=\"find_by_id\">");
            out.println("<input type=\"submit\" value=\"FindById\">");
            out.println("</form>");
            out.println("<h2>Total Operations:" + getOperationCounter(request) + "</h2>");
            out.println("</body>");
            out.println("</html>");
        }
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
        response.setContentType("text/html;charset=UTF-8");

        String findOption = request.getParameter("find");
        List<Book> books = new ArrayList<>();

        if (findOption.equals("1")) {
            books = bookController.getAllBooks();

        } else if (findOption.equals("2")) {

            Integer id = 0;
            String findById = request.getParameter("find_by_id");
            try {
                id = Integer.parseInt(findById);
            } catch (NumberFormatException err) {
                request.setAttribute("msgType", " User id can't be float");
                request.getRequestDispatcher("/ErrorServlet").include(request, response);
            }

            Book book = bookController.findBookById(id);
            if (book != null) {
                books.add(book);
            } else {
                request.setAttribute("msgType", " Book with this Id Doesn't exist ");
                request.getRequestDispatcher("/ErrorServlet").include(request, response);
            }

        }
        try (PrintWriter out = response.getWriter()) {

            //Counter
            incrementCounter(request);

            out.println("<table width='100%' border='2' align=\"center\">");
            out.println("<tr><th>Book Id</th><th>Book Name</th><th>Book Auther</th><th>Book Type</th><th>User Id</th></tr>");
            for (Book b : books) {
                out.println("<tr align=\"center\"><td>" + b.getId() + "</td>");
                out.println("<td>" + b.getName() + "</td>");
                out.println("<td>" + b.getAuthor() + "</td>");
                out.println("<td>" + b.getType() + "</td>");
                out.println("<td >" + b.getUser().getId() + "</td>");
            }
            out.println("</table>");
            out.println("</br><a href=\"" + request.getContextPath() + "/\">Go back</a>");

        }
    }

    private void incrementCounter(HttpServletRequest request) {

        HttpSession session = request.getSession();

        Map<String, Integer> counterMap
                = (Map<String, Integer>) session.getAttribute("counterMap");

        if (counterMap == null) {
            counterMap = new HashMap<>();
        }
        String name = "findBook";
        if (counterMap.containsKey(name)) {
            counterMap.put(name, counterMap.get(name) + 1);
        } else {
            counterMap.put(name, 1);
            session.setAttribute("counterMap", counterMap);
        }
    }

    /**
     * Method returns counter of performed operations
     *
     * @param request servlet request
     * @return number of performed operations
     */
    private int getOperationCounter(HttpServletRequest request) {
        HttpSession session = request.getSession();

        Map<String, Integer> counterMap
                = (Map<String, Integer>) session.getAttribute("counterMap");
        if (counterMap == null) {
            return 0;
        }
        String name = "findBook";
        if (counterMap.containsKey(name)) {
            return counterMap.get(name);
        } else {
            return 0;
        }
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
