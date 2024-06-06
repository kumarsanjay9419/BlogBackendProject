package com.sk.blog.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.sk.blog.security.JwtAuthenticationEntryPoint;
import com.sk.blog.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig{
	
	public static final String[] PUBLIC_URL = { 
			"/api/auth/**",
			"/v3/api-docs/**", 
			"/v2/api-docs",
			"/swagger-ui/**",
			"/swagger-resources/**",
			"/webjars/**"};
	
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	
	    http
	    .cors(Customizer.withDefaults())
	    .csrf()
	    .disable()
        .authorizeHttpRequests()
        .requestMatchers(PUBLIC_URL).permitAll()
        .requestMatchers(HttpMethod.GET).permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .exceptionHandling()
        .authenticationEntryPoint(this.jwtAuthenticationEntryPoint)
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	    
	    http.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

	    return http.build();
    }
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	       return new BCryptPasswordEncoder();
	   }
	
	@Bean
    public AuthenticationManager authenticationManager(UserDetailsService customUserDetailsService) {

        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        List<AuthenticationProvider> providers =  List.of(authProvider);

        return new ProviderManager(providers);
    }
	
//	@Bean
//	public FilterRegistrationBean corsFilter() {
//		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		CorsConfiguration corsConfiguration = new CorsConfiguration();
//		corsConfiguration.setAllowCredentials(true);
//		corsConfiguration.addAllowedOriginPattern("*");
//		corsConfiguration.addAllowedHeader("Authorization");
//		corsConfiguration.addAllowedHeader("Content-Type");
//		corsConfiguration.addAllowedHeader("Accept");
//		corsConfiguration.addAllowedHeader("POST");
//		corsConfiguration.addAllowedHeader("GET");
//		corsConfiguration.addAllowedHeader("DELETE");
//		corsConfiguration.addAllowedHeader("PUT");
//		corsConfiguration.addAllowedHeader("OPTIONS");
//		corsConfiguration.setMaxAge(3600L);
//		
//		source.registerCorsConfiguration("/**", corsConfiguration);
//		FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
//		
//		return bean;
//	}
//	
//	  @Bean
//	  CorsConfigurationSource corsConfigurationSource() {
//	      CorsConfiguration corsConfiguration = new CorsConfiguration();
//			corsConfiguration.setAllowCredentials(true);
//			corsConfiguration.addAllowedOriginPattern("*");
//			corsConfiguration.addAllowedHeader("Authorization");
//			corsConfiguration.addAllowedHeader("Content-Type");
//			corsConfiguration.addAllowedHeader("Accept");
//			corsConfiguration.addAllowedHeader("POST");
//			corsConfiguration.addAllowedHeader("GET");
//			corsConfiguration.addAllowedHeader("DELETE");
//			corsConfiguration.addAllowedHeader("PUT");
//			corsConfiguration.addAllowedHeader("OPTIONS");
//			corsConfiguration.setMaxAge(3600L);
//	      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//	      source.registerCorsConfiguration("/**", corsConfiguration);
//	      return source;
//	  }
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
	    CorsConfiguration configuration = new CorsConfiguration();
	    configuration.setAllowedOriginPatterns(Arrays.asList("*"));
	    configuration.setAllowCredentials(true);
	    configuration.setAllowedHeaders(Arrays.asList("Access-Control-Allow-Headers",
	    		"Access-Control-Allow-Origin","Access-Control-Request-Method", "Access-Control-Request-Headers","Origin","Cache-Control", "Content-Type", "Authorization"));
	    configuration.setAllowedMethods(Arrays.asList("DELETE", "GET", "POST", "PATCH", "PUT"));
	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", configuration);
	    return source;
	}
	
	
}
