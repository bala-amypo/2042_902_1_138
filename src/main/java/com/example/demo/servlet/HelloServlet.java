package com.example.demo.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    
    private String message;
    
    @Override
    public void init() throws ServletException {
        message = "Hello Tomcat";
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/plain");
        response.getWriter().write("Hello from servlet");
    }
    
    public String getMessage() {
        return message;
    }
}