package tn.spring.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	 @Override
	    protected void configure(HttpSecurity http) throws Exception {
	        http.authorizeRequests()
	            .antMatchers("/register", "/login", "/SpringMVC/error").permitAll() // Permit access to these endpoints without authentication
	            .antMatchers("/profile").permitAll()
	            .antMatchers("/list-users").permitAll()
	            .antMatchers("/show-user-profile/{user_id}").permitAll()
	            .antMatchers("/change-password").permitAll()
	            .antMatchers("/add-post").permitAll() // Permit access to addPost endpoint without authentication
	            .antMatchers("/posts").permitAll() // Permit access to posts endpoint without authentication
	            .anyRequest().authenticated() // Require authentication for all other endpoints
	            .and()
	            .csrf().disable();
	        
	        
	    }

	    @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
}