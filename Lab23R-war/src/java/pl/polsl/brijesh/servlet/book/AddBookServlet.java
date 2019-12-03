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
import pl.polsl.brijesh.ejb.model.UserBean;
import pl.polsl.brijesh.ejb.model.Book;
import pl.polsl.brijesh.ejb.model.User;

/**
 *
 * @author b___b
 */
public class AddBookServlet extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Add Book Servlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<form action=\"AddBookServlet\" method=\"post\">");
            out.println("Book Name: <input type=\"text\" name=\"bookName\"><br>");
            out.println("Book Auther: <input type=\"text\" name=\"bookAuther\"><br>");
            out.println("Book Type: <input type=\"text\" name=\"bookType\"><br>");
            out.println("User Id: <input type=\"text\" name=\"bookUserId\"><br>");
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

        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            String bookName = request.getParameter("bookName");
            String bookType = request.getParameter("bookType");
            String bookAuther = request.getParameter("bookAuther");
            String bookUserId = request.getParameter("bookUserId");

            User user;
            if (!bookUserId.isEmpty()) {
                try {
                    Integer userId = Integer.parseInt(bookUserId);
                    user = userController.findUserById(userId);

                } catch (NumberFormatException e) {
                    user = null;
                    request.setAttribute("msgType", " User id can't be float");
                    request.getRequestDispatcher("/ErrorServlet").include(request, response);
                }
            } else {
                user = null;
            }
//            if(user != null)

            if (bookName.equals("")) {
                // response.sendError(HttpServletResponse.SC_BAD_REQUEST,"Name cannot be blank");
                request.setAttribute("msgType", "Name cannot be emprty");
                request.getRequestDispatcher("/ErrorServlet").include(request, response);
            }
            if (bookAuther.equals("")) {
                request.setAttribute("msgType", "Auther cannot be emprty");
                request.getRequestDispatcher("/ErrorServlet").include(request, response);
            }

            Book book = new Book(bookName, bookType, bookAuther, user);

            bookController.addBook(book);

            incrementCounter(request);

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Add Book</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Add a Book</h1>");
            out.println("<p>Book added successfully</p>");
            out.println("</br><a href=\"" + request.getContextPath() + "/\">Go back to Menu</a>");
            out.println("<a href=\"AddBookServlet\">Go To Add Book</a>");
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
        String name = "addBook";
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
        String name = "addBook";
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
