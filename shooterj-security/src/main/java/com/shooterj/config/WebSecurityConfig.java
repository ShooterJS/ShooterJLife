package com.shooterj.config;

import com.shooterj.filter.HtFilterSecurityInterceptor;
import com.shooterj.jwt.JwtAuthenticationEntryPoint;
import com.shooterj.jwt.JwtAuthorizationTokenFilter;
import com.shooterj.jwt.JwtTokenHandler;
import com.shooterj.service.CustomPasswordEncoder;
import com.shooterj.service.HtDecisionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.annotation.Resource;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Resource
    UserDetailsService userDetailsService;
    @Resource
    JwtTokenHandler jwtTokenHandler;
    @Value("${jwt.header:'Authorization'}")
    String tokenHeader;
    @Value("${jwt.route.path:'/auth'}")
    String authenticationPath;
    @Resource
    JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Resource
    HtFilterSecurityInterceptor htFilterSecurityInterceptor;


    @Resource
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoderBean());
    }

    @Bean
    public PasswordEncoder passwordEncoderBean() {
        return new CustomPasswordEncoder();
    }

    // 注册后台权限控制器
    @Bean
    public AccessDecisionManager accessDecisionManager() {
        return new HtDecisionManager();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // we don't need CSRF because our token is invulnerable
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and()
                // don't create session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers("/auth/**").permitAll()
                .anyRequest().authenticated()
                .accessDecisionManager(accessDecisionManager());

        // Custom JWT based security filter
        JwtAuthorizationTokenFilter authenticationTokenFilter = new JwtAuthorizationTokenFilter(userDetailsService(), jwtTokenHandler, tokenHeader);
        httpSecurity
                .addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        httpSecurity
                .addFilterBefore(htFilterSecurityInterceptor, FilterSecurityInterceptor.class);
        httpSecurity
                .addFilterBefore(corsFilter(), ChannelProcessingFilter.class);

        // disable page caching
        httpSecurity
                .headers()
                .frameOptions().sameOrigin()  // required to set for H2 else H2 Console will be blank.
                .cacheControl();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // AuthenticationTokenFilter will ignore the below paths
        web
                .ignoring()
                .antMatchers(
                        HttpMethod.POST,
                        authenticationPath,
                        "/sys/sysLogs/v1/loginLogs",
                        "/mcicustomer/mciCustomer/v1/checkCustomer",
                        "/mcicustomer/mciCustomer/v1/save",
                        "/mcicustomer/mciCustomer/v1/register",
                        "/mcicustomer/MciCustomerJsTicket/v1/getJsSdkSign",
                        "/mcicustomer/MciCustomerJsTicket/v1/getDecrypt",
                        "/mcicustomer/MciCustomerJsTicket/v1/Login",
                        "/mcicustomer/mciCustomer/v1/test",
                        "/mcicustomer/MciCustomerJsTicket/v1/check",
                        "/mciintegral/mciIntegralExtend/v1/mci/getAccessToken",
                        "/mcicustomer/MciCustomerJsTicket/v1/sysRegister",
                        "/mciintegral/mciIntegralBehavior/v1/save",
                        "/mci/getAccessToken",
                        "/mci/getRules",
                        "/mci/saveRules",
                        "/mci/saveIntegral",
                        "/mci/findCustomerIntegral",
                        "/mcicustomer/MciCustomerJsTicket/v1/edaGetToken",
                        "/mcicustomer/MciCustomerJsTicket/v1/customerInfo",
                        "/mcicustomer/MciCustomerJsTicket/v1/getJwtToken",
                        "/mcicustomer/MciCustomerJsTicket/v1/getCustomerInfo",
                        "/mciJdClient/v1/cashier/pay",
                        "/mciJdClient/v1/integral/refund",
                        "/mcicustomer/mciCustomerIntegral/v1/queryIntegralReport",
                        "/mcicustomer/mciCustomerIntegral/v1/exportIntegralReport",
                        "/mci/queryIntegralDetail"
                )
                .antMatchers(
                        HttpMethod.GET,
                        "/sso/**",
                        "/mcicustomer/mciCustomer/v1/sendMsgCode",
                        "/mcicustomer/mciCustomer/v1/queryTerminalByMobile",
                        "/mcicustomer/mciCustomer/v1/queryTerminal",
                        "/mcicustomer/MciCustomerJsTicket/v1/getCustByUserCode",
                        "/mcicustomer/mciCustomer/v1/checkMobileAndName",
                        "/mciJdClient/v1/cashier/pay",
                        "/mciJdClient/v1/integral/refund",
                        "/mcicustomer/mciCustomer/v1/queryCustomerByMobile"

                )
                // allow anonymous resource requests
                .and()
                .ignoring()
                .antMatchers(
                        HttpMethod.GET,
                        "/",
                        "/*.html",
                        "/favicon.ico",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/**/image",
                        "/*.txt"
                )
                .and()
                .ignoring()
                .antMatchers("/v2/api-docs",
                        "/swagger-resources/configuration/ui",
                        "/swagger-resources",
                        "/swagger-resources/configuration/security",
                        "/swagger-ui.html"
                );
    }

    /**
     * 允许跨域访问的源
     *
     * @return
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(source);
    }

    @Bean
    public HtFilterSecurityInterceptor htFilterSecurityInterceptor(AccessDecisionManager accessDecisionManager) throws Exception {
        HtFilterSecurityInterceptor htFilterSecurityInterceptor = new HtFilterSecurityInterceptor();
        htFilterSecurityInterceptor.setAccessDecisionManager(accessDecisionManager);
        return htFilterSecurityInterceptor;
    }


}
