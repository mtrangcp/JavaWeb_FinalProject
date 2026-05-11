package com.btvn.projectfinal.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Component
public class AccessLogInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = (auth!=null && auth.isAuthenticated()
                && auth.getName().equalsIgnoreCase("anonymousUser"))
                ? auth.getName()
                : "anonymous";

        String role = (auth!=null && !auth.getAuthorities().isEmpty())
                ? auth.getAuthorities().iterator().next().getAuthority()
                : "NONE";

        log.info("[ACCESS LOG] user={} | role={} | method={} | uri={}",
                username, role, request.getMethod(),  request.getRequestURI());

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        log.info("[RESPONSE] uri={} | status={}",
                request.getRequestURI(),
                response.getStatus());
    }
}
