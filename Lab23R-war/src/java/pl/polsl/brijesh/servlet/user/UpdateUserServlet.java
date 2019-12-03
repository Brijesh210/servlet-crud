/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.brijesh.servlet.user;

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
import pl.polsl.brijesh.ejb.model.Book;
import pl.polsl.brijesh.ejb.model.User;
import pl.polsl.brijesh.ejb.model.UserBean;

/**
 *
 * @author b___b
 */
public class UpdateUserServlet extends HttpServlet {

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
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Update User Servlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<form action=\"UpdateUserServlet\" method=\"post\">");
            out.println("User Id: <input type=\"text\" name=\"userId\"><br>");
            out.println("User Name: <input type=\"text\" name=\"userName\"><br>");
            out.println("User Address: <input type=\"text\" name=\"userAddress\"><br>");
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

            String userIdUpdate = request.getParameter("userId");
            String userNameUpdate = request.getParameter("userName");
            String userAddressUpdate = request.getParameter("userAddress");

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
                    request.setAttribute("0msgType", " User id can't be float");
                    request.getRequestDispatcher("/ErrorServlet").include(request, response);
                }
            } else {
                user = null;
            }

            if (user == null) {
                request.setAttribute("msgType", " User Id cannot be empty");
                request.getRequestDispatcher("/ErrorServlet").include(request, response);
            }

            if (!userNameUpdate.equals("")) {
                user.setName(userNameUpdate);
            }
            if (!userAddressUpdate.equals("")) {
                user.setAddress(userAddressUpdate);
            }

            userController.updateUser(user);
            incrementCounter(request);

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title> Update User</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Updating a User</h1>");
            out.println("<p>User updated successfully</p>");
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
        String name = "addUser";
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
        String name = "addUser";
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
