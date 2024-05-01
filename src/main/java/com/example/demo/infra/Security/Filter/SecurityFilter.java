package com.example.demo.infra.Security.Filter;

import com.example.demo.Repository.UsersRepository;
import com.example.demo.infra.Security.Token.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private TokenService tokenService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        var token = recuperaToken(request);
        if (token != null) {
            token = token.replace("Bearer ", "").trim();
        }

        if (token != null) {
            var subject = tokenService.getSubject(token);
            var user =  usersRepository.findByEmail(subject);

            // Cria um objeto de autenticação do Spring Security
            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            // Seta o usuário autenticado no contexto do Spring Security
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    // Recupera o token do cabeçalho da requisição
    private String recuperaToken(HttpServletRequest request) {
        var token = request.getHeader("Authorization");
        return token;
    }
}
