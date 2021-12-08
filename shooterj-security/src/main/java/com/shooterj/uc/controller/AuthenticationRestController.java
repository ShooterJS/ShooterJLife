package com.shooterj.uc.controller;

import com.winway.meetingbase.common.controller.BaseController;
import com.winway.meetingbase.common.exception.CertificateException;
import com.shooterj.jwt.JwtAuthenticationRequest;
import com.shooterj.jwt.JwtAuthenticationResponse;
import com.shooterj.jwt.JwtTokenHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@RestController
public class AuthenticationRestController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationRestController.class);

    @Value("${jwt.header:'Authorization'}")
    private String tokenHeader;

    @Resource
    JwtTokenHandler jwtTokenHandler;

    @Resource
    AuthenticationManager authenticationManager;

    @Resource
    UserDetailsService userDetailsService;


    @PostMapping(value = "/auth", produces = {"application/json; charset=utf-8"})
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest)
            throws AuthenticationException, CertificateException {

        String reqAccount = authenticationRequest.getUsername();

        authenticate(reqAccount, authenticationRequest.getPassword());
        // Reload password post-security so we can generate the token
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenHandler.generateToken(userDetails);
        String userName = userDetails.getUsername();
        String account = userDetails.getUsername();
        String userId = userDetails.getUsername();

        // Return the token
        return ResponseEntity.ok(new JwtAuthenticationResponse(token, userName, account, userId, "", "", "", "", ""));
    }

    @RequestMapping(value = "/auths", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
//    @ApiOperation(value = "登录系统", httpMethod = "POST", notes = "登录系统")
    public ResponseEntity<?> createAuthenticationTokenWithoutPassword(@RequestBody JwtAuthenticationRequest authenticationRequest) throws AuthenticationException,
            CertificateException {
        String reqAccount = authenticationRequest.getUsername();
        //清除用户缓存
        // Reload password post-security so we can generate the token
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        authenticate(reqAccount, userDetails.getPassword());

        final String token = jwtTokenHandler.generateToken(userDetails);
        String userName = userDetails.getUsername();
        String account = userDetails.getUsername();
        String userId = userDetails.getPassword();

        // Return the token
        return ResponseEntity.ok(new JwtAuthenticationResponse(token, userName, account, userId, "", "", "", "", ""));
    }


    @RequestMapping(value = "${jwt.route.refresh:/refresh}", method = RequestMethod.GET)
    public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) {
        String authToken = request.getHeader(tokenHeader);
        final String token = authToken.substring(7);
        String refreshedToken = jwtTokenHandler.refreshToken(token);
        return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken, "", "", "", "", "", "", "", ""));
    }

    /**
     * Authenticates the user. If something is wrong, an {@link AuthenticationException} will be thrown
     */
    private void authenticate(String username, String password) throws AuthenticationException, CertificateException {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }


}