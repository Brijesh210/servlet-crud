/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.brijesh.servlet.book;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import pl.polsl.brijesh.ejb.model.BookBean;
import pl.polsl.brijesh.ejb.model.Book;
import pl.polsl.brijesh.ejb.model.User;
import pl.polsl.brijesh.ejb.model.UserBean;

/**
 *
 * @author b___b
 */
public class UpdateBookServlet extends HttpServlet {

    @EJB
    BookBean bookController;
    @EJB
    UserBean userController;

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
        processRequest(request, response);
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title> Update Book Servlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<form action=\"UpdateBookServlet\" method=\"post\">");
            out.println("Book Id: <input type=\"text\" name=\"bookId\"><br>");
            out.println("Book Name: <input type=\"text\" name=\"bookName\"><br>");
            out.println("Book Type: <input type=\"text\" name=\"bookType\"><br>");
            out.println("Book Auther: <input type=\"text\" name=\"bookAuther\"><br>");
            out.println("User Id: <input type=\"text\" name=\"userId\"><br>");
            out.println("<input type=\"submit\" value=\"Submit\">");
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
        processRequest(request, response);

        try (PrintWriter out = response.getWriter()) {

            Integer id = 0;
            // Integer userId = 0;
            String bookIdUpdate = request.getParameter("bookId");
            String bookNameUpdate = request.getParameter("bookName");
            String bookTypeUpdate = request.getParameter("bookType");
            String bookAutherUpdate = request.getParameter("bookAuther");
            String userIdUpdate = request.getParameter("userId");
            try {
                id = Integer.parseInt(bookIdUpdate);
            } catch (NumberFormatException err) {
                request.setAttribute("msgType", " Book id can't be float or empty");
                request.getRequestDispatcher("/ErrorServlet").include(request, response);
            }

            User user;

            if (!userIdUpdate.isEmpty()) {
                try {
                    Integer userId = Integer.parseInt(userIdUpdate);
                    user = userController.findUserById(userId);
                    if (user == null) {
                        request.setAttribute("msgType", " User with this Id doesn't exist!");
                        request.getRequestDispatcher("/ErrorServlet").include(request, response);
                    }

                } catch (NumberFormatException e) {
                    user = null;
                    request.setAttribute("msgType", " User id can't be float");
                    request.getRequestDispatcher("/ErrorServlet").include(request, response);
                }
            } else {
                user = null;
            }

            Book book = bookController.findBookById(id);

            if (book == null) {
                request.setAttribute("msgType", " Book Doesn't exist");
                request.getRequestDispatcher("/ErrorServlet").include(request, response);
            }

            if (!bookNameUpdate.equals("")) {
                book.setName(bookNameUpdate);
            }
            if (!bookAutherUpdate.equals("")) {
                book.setAuthor(bookAutherUpdate);
            }
            if (!bookTypeUpdate.equals("")) {
                book.setType(bookTypeUpdate);
            }
            if (user != null) {
                book.setUser(user);
            }

            bookController.updateBook(book);
            incrementCounter(request);

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>[CRUD]Update Book</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Updating a Book</h1>");
            out.println("<p>Book updated successfully!</p>");
            out.println("</br><a href=\"" + request.getContextPath() + "/\">Go back</a>");
            out.println("</body>");
            out.println("</html>");

        }

    }

    private void incrementCounter(HttpServletRequest request) {

        HttpSession session = request.getSession();

        Map<String, Integer> counterMap
                = (Map<String, Integer>) session.getAttribute("counterMap");

        if (counterMap == null) {
            counterMap = new HashMap<>();
        }
        String name = "updateBook";
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
        String name = "updateBook";
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
