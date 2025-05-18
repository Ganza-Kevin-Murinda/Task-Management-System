package com.tsm.task.controller;

import com.google.gson.Gson;
import com.tsm.task.factory.ServiceFactory;
import com.tsm.task.model.User;
import com.tsm.task.service.interfaces.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.UUID;

@WebServlet(name = "UserServlet", urlPatterns = {"/users", "/users/*"})
public class UserServlet extends AbstractController {
    private final UserService userService;
    private final Gson gson = new Gson();

    public UserServlet() {
        this.userService = ServiceFactory.getUserService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long startTime = System.currentTimeMillis();
        String requestId = UUID.randomUUID().toString();

        try {
            logRequest(req, requestId);

            // Handle form submission from JSP
            String username = req.getParameter("username");
            String email = req.getParameter("email");
            String fullName = req.getParameter("fullName");
            String department = req.getParameter("department");
            String joinDateStr = req.getParameter("joinDate");

            User user = new User();
            user.setUsername(username);
            user.setEmail(email);
            user.setFull_name(fullName);
            user.setDepartment(department);

            // Parse date if provided and convert to LocalDateTime for timestamp compatibility
            if (joinDateStr != null && !joinDateStr.isEmpty()) {
                try {
                    // Parse the date from the form (which comes as yyyy-MM-dd)
                    LocalDate localDate = LocalDate.parse(joinDateStr);

                    // Set the join date
                    user.setJoinDate(localDate);
                } catch (DateTimeParseException e) {
                    // Use session for message passing instead of request attributes
                    HttpSession session = req.getSession();
                    session.setAttribute("errorMessage", "Invalid date format. Please use YYYY-MM-DD.");
                    resp.sendRedirect(req.getContextPath() + "/users");
                    return;
                }
            }

            boolean created = userService.createUser(user);

            // Use session for message passing instead of request attributes
            HttpSession session = req.getSession();
            if (created) {
                session.setAttribute("successMessage", "User created successfully.");
            } else {
                session.setAttribute("errorMessage", "Failed to create user.");
            }

            // Redirect instead of forwarding
            resp.sendRedirect(req.getContextPath() + "/users");

            logResponse(resp, requestId, startTime);
        } catch (Exception e) {
            HttpSession session = req.getSession();
            session.setAttribute("errorMessage", "Error: " + e.getMessage());
            resp.sendRedirect(req.getContextPath() + "/users");
            handleError(resp, requestId, e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestId = UUID.randomUUID().toString();
        long startTime = System.currentTimeMillis();

        try {
            logRequest(req, requestId);

            // Get all users from the service
            List<User> users = userService.getAllUsers();

            // Set users in request scope for the JSP
            req.setAttribute("users", users);

            // Remove messages from session after reading them
            HttpSession session = req.getSession();
            String successMessage = (String) session.getAttribute("successMessage");
            String errorMessage = (String) session.getAttribute("errorMessage");

            if (successMessage != null) {
                req.setAttribute("successMessage", successMessage);
                session.removeAttribute("successMessage");
            }

            if (errorMessage != null) {
                req.setAttribute("errorMessage", errorMessage);
                session.removeAttribute("errorMessage");
            }

            // Forward to the user page
            req.getRequestDispatcher("/WEB-INF/views/users/user-management.jsp").forward(req, resp);

            logResponse(resp, requestId, startTime);
        } catch (Exception e) {
            handleError(resp, requestId, e);
        }
    }
}