package com.example.demo.servlet;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/hello-servlet")
public class HelloServlet extends HttpServlet {

    private String message;

    @Override
    public void init(ServletConfig config) {
        message = "Hello Tomcat";
    }

    public String getMessage() {
        return message;
    }
}
