package com.xrom.ssh.filter;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SessionFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String[] notFilter = new String[]{"iSignInPage.jsp", "iSignUpPage.jsp", "mSignInPage.jsp", "sSignInPage.jsp", "sSignUpPage.jsp", "index.jsp"};
        Boolean isUser = request.getSession().getAttribute("student")==null;
        Boolean isInstitute = request.getSession().getAttribute("institution")==null;
        Boolean isMaster = request.getSession().getAttribute("master")==null;
        String uri = request.getRequestURI();
        Boolean notFilterUri = false;
        System.out.println(uri);
        filterChain.doFilter(request, response);
        return;
    }
}
