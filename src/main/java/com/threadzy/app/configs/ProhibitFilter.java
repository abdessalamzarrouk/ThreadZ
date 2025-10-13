package com.threadzy.app.configs;

import java.io.IOException;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
// This is a testing filter which is not used in the app
class ProhibitFilter extends OncePerRequestFilter {
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if(Objects.equals(request.getHeader("x-prohibit"),"si")){  // Why not use request.gethaeder ..... then .equals("si") ????
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setCharacterEncoding("UTF-8");
            response.setHeader("ContentType","text/plain;charset=UTF-8");
            response.getWriter().write("ACCESS DENIED");
            return;
        }
        else{
            filterChain.doFilter(request, response);
        }
    }




}