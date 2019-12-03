/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.brijesh.servlet.user;

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
import pl.polsl.brijesh.ejb.model.Book;
import pl.polsl.brijesh.ejb.model.BookBean;
import pl.polsl.brijesh.ejb.model.User;
import pl.polsl.brijesh.ejb.model.UserBean;

/**
 *
 * @author b___b
 */
public class FindUserServlet extends HttpServlet {

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
            out.println("<title> Find User Servlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1> Find User by Id or Find All User</h1>");
            out.println("<form action =\"FindUserServlet?find=1\" method=\"post\">");
            out.println("Find All User: <input type=\"submit\" value=\"FiindAll\"><br/></br>");
            out.println("</form>");
            out.println("<form action =\"FindUserServlet?find=2\" method=\"post\">");
            out.println("Find User by Id: <input typ=\"text\" name=\"find_by_id\">");
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
        List<User> users = new ArrayList<>();

        if (findOption.equals("1")) {
            users = userController.getAllUsers();

        } else if (findOption.equals("2")) {

            Integer id = 0;
            String findById = request.getParameter("find_by_id");
            try {
                id = Integer.parseInt(findById);
            } catch (NumberFormatException err) {
                request.setAttribute("msgType", " User id can't be float or empty");
                request.getRequestDispatcher("/ErrorServlet").include(request, response);
            }

            User user = userController.findUserById(id);
            
            if (user != null) {
                users.add(user);
            }else{
                request.setAttribute("msgType", " User with this Id doesn't exist");
                request.getRequestDispatcher("/ErrorServlet").include(request, response);
            }
        }
        try (PrintWriter out = response.getWriter()) {

            //Counter
            incrementCounter(request);

            out.println("<table width='100%' border='2'>");
            out.println("<tr><th>User Id</th><th>User Name</th><th>User Address</th></tr>");
            for (User u : users) {
                out.println("<tr align=\"center\"><td>" + u.getId() + "</td>");
                out.println("<td>" + u.getName() + "</td>");
                out.println("<td>" + u.getAddress() + "</td>");
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
        String name = "findUser";
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
        String name = "findUser";
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
