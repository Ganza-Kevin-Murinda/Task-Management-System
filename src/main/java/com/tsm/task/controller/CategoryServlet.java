package com.tsm.task.controller;

import com.google.gson.Gson;
import com.tsm.task.factory.ServiceFactory;
import com.tsm.task.model.Category;
import com.tsm.task.service.interfaces.CategoryService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@WebServlet(name = "CategoryServlet", urlPatterns = {"/categories", "/categories/*"})
public class CategoryServlet extends AbstractController {
    private final CategoryService categoryService;
    private final Gson gson = new Gson();

    public CategoryServlet() {
        this.categoryService = ServiceFactory.getCategoryService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestId = UUID.randomUUID().toString();
        long startTime = System.currentTimeMillis();

        try {
            logRequest(req, requestId);

            BufferedReader reader = req.getReader();
            Category category = gson.fromJson(reader, Category.class);
            boolean created = categoryService.createCategory(category);

            if (created) {
                respondWithJson(resp, HttpServletResponse.SC_CREATED, "{\"message\":\"Category created successfully\"}");
            } else {
                respondWithJson(resp, HttpServletResponse.SC_BAD_REQUEST, "{\"error\":\"Failed to create category\"}");
            }

            logResponse(resp, requestId, startTime);
        } catch (Exception e) {
            handleError(resp, requestId, e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestId = UUID.randomUUID().toString();
        long startTime = System.currentTimeMillis();

        try {
            logRequest(req, requestId);
            List<Category> categories = categoryService.getAllCategories();
            String json = gson.toJson(categories);
            respondWithJson(resp, HttpServletResponse.SC_OK, json);
            logResponse(resp, requestId, startTime);
        } catch (Exception e) {
            handleError(resp, requestId, e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String requestId = UUID.randomUUID().toString();
        long startTime = System.currentTimeMillis();
        String pathInfo = req.getPathInfo();

        try {
            logRequest(req, requestId);

            if (pathInfo == null || pathInfo.equals("/")) {
                respondWithJson(resp, HttpServletResponse.SC_BAD_REQUEST, "{\"error\":\"Category ID is required in the URL\"}");
                return;
            }

            int id = Integer.parseInt(pathInfo.substring(1));
            Category category = gson.fromJson(req.getReader(), Category.class);
            category.setCategoryId(id);

            boolean updated = categoryService.updateCategory(category);
            if (updated) {
                respondWithJson(resp, HttpServletResponse.SC_OK, gson.toJson(category));
            } else {
                respondWithJson(resp, HttpServletResponse.SC_NOT_FOUND, "{\"error\":\"Category not found or not updated\"}");
            }

            logResponse(resp, requestId, startTime);
        } catch (Exception e) {
            handleError(resp, requestId, e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String requestId = UUID.randomUUID().toString();
        long startTime = System.currentTimeMillis();
        String pathInfo = req.getPathInfo();

        try {
            logRequest(req, requestId);

            if (pathInfo == null || pathInfo.equals("/")) {
                respondWithJson(resp, HttpServletResponse.SC_BAD_REQUEST, "{\"error\":\"Category ID is required in the URL\"}");
                return;
            }

            int id = Integer.parseInt(pathInfo.substring(1));
            boolean deleted = categoryService.deleteCategory(id);

            if (deleted) {
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } else {
                respondWithJson(resp, HttpServletResponse.SC_NOT_FOUND, "{\"error\":\"Category not found\"}");
            }

            logResponse(resp, requestId, startTime);
        } catch (Exception e) {
            handleError(resp, requestId, e);
        }
    }

}