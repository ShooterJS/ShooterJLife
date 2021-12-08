package com.shooterj.jwt;


import com.shooterj.AuthenticationUtil;
import com.shooterj.utils.JwtTokenUtils;
import com.shooterj.utils.SecurityUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationTokenFilter extends OncePerRequestFilter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private UserDetailsService userDetailsService;
    private com.shooterj.jwt.JwtTokenHandler jwtTokenHandler;
    private String tokenHeader;

    public JwtAuthorizationTokenFilter(UserDetailsService userDetailsService, com.shooterj.jwt.JwtTokenHandler jwtTokenHandler, String tokenHeader) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenHandler = jwtTokenHandler;
        this.tokenHeader = tokenHeader;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        logger.debug("processing authentication for '{}'", request.getRequestURL());

        final String requestHeader = request.getHeader(this.tokenHeader);

        String username = null;
        String authToken = null;

        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            authToken = requestHeader.substring(7);
            try {
                username = jwtTokenHandler.getUsernameFromToken(authToken);
            } catch (IllegalArgumentException e) {

                logger.error("an error occurred during getting username from token", e);
                response.sendError(HttpStatus.UNAUTHORIZED.value(), e.getMessage());

            } catch (ExpiredJwtException e) {

                logger.warn("the token is expired and not valid anymore", e);
                response.sendError(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
            }
        } else if (requestHeader != null && requestHeader.startsWith("Basic ")) {

            String basicToken = requestHeader.substring(6);
            String userPassword = JwtTokenUtils.decodeBase64Token(basicToken);
            String[] arrays = userPassword.split(":");
            if (arrays.length == 2) {
                try {
                    SecurityUtil.login(request, arrays[0], arrays[1], false);
                } catch (Exception e) {
                    logger.error("authentication failed", e);
                    response.sendError(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
                }
            }
        } else {
            logger.warn("request url '{}' couldn't find bearer string, will ignore the header", request.getRequestURL());
        }

        logger.debug("checking authentication for user '{}'", username);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            logger.debug("security context was null, so authorizing user");

            // It is not compelling necessary to load the use details from the database. You could also store the information
            // in the token and read it from it. It's up to you ;)
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            // For simple validation it is completely sufficient to just check the token integrity. You don't have to call
            // the database compellingly. Again it's up to you ;)
            if (jwtTokenHandler.validateToken(authToken, userDetails)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                logger.info("authorized user '{}', setting security context", username);

                SecurityContextHolder.getContext().setAuthentication(authentication);

                AuthenticationUtil.setAuthentication(authentication);
            }
        }

        chain.doFilter(request, response);

        AuthenticationUtil.removeAll();

    }

}
