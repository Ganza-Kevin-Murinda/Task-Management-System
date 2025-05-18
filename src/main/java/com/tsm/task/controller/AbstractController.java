package com.tsm.task.controller;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.ThreadContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.UUID;

public abstract class AbstractController extends HttpServlet {
    protected final Logger logger = LogManager.getLogger(getClass());
    protected final Gson gson = new Gson();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long startTime = System.currentTimeMillis();
        String requestId = UUID.randomUUID().toString();
        ThreadContext.put("requestId", requestId);

        logger.info("{} {}", req.getMethod(), req.getRequestURI());
        req.getParameterMap().forEach((k, v) -> logger.debug("Param: {} = {}", k, String.join(",", v)));

        try {
            super.service(req, resp);
        } catch (Exception e) {
            logger.error("Unhandled exception: ", e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            writeJson(resp, new ErrorResponse("Internal server error"));
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            logger.debug("Request {} processed in {} ms", requestId, duration);
            ThreadContext.clearAll();
        }
    }

    protected <T> T readJson(HttpServletRequest req, Class<T> clazz) throws IOException {
        try (BufferedReader reader = req.getReader()) {
            return gson.fromJson(reader, clazz);
        } catch (JsonSyntaxException e) {
            throw new IllegalArgumentException("Malformed JSON request");
        }
    }

    protected void writeJson(HttpServletResponse resp, Object data) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        gson.toJson(data, resp.getWriter());
    }

    protected record ErrorResponse(String message) {}

    protected void logRequest(HttpServletRequest req, String requestId) {
        logger.info("[{}] {} {}", requestId, req.getMethod(), req.getRequestURI());
        req.getParameterMap().forEach((key, value) ->
                logger.debug("[{}] Param: {} = {}", requestId, key, String.join(",", value))
        );
    }

    protected void respondWithJson(HttpServletResponse resp, int statusCode, String json) throws IOException {
        resp.setStatus(statusCode);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(json);
    }

    protected void logResponse(HttpServletResponse resp, String requestId, long startTime) {
        long duration = System.currentTimeMillis() - startTime;
        logger.info("[{}] Response: {}, Time taken: {} ms", requestId, resp.getStatus(), duration);
    }

    protected void handleError(HttpServletResponse resp, String requestId, Exception e) throws IOException {
        logger.error("[{}] Error occurred: {}", requestId, e.getMessage(), e);
        respondWithJson(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "{\"error\":\"Internal server error\"}");
    }
}
