package com.example.springtest1.config;

import com.example.springtest1.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.stereotype.Component;

@Component
public class JwtInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authHeader = request.getHeader("Authorization");
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            response.setStatus(401);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write("{\"error\":\"Invalid token\"}");
            return false;
        }
        String token = authHeader.substring(7);
        Long userId = JwtUtil.validateToken(token);
        if(userId == null){
            response.setStatus(401);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write("{\"error\":\"Invalid token\"}");
            return false;
        }
        request.setAttribute("userId", userId);
        return true;
    }
}
