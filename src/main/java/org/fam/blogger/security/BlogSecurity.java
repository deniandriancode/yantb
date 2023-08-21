package org.fam.blogger.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// TODO setup admin's stuff, for now focus on user

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class BlogSecurity {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.csrf((csrf) -> csrf.disable())
				.authorizeHttpRequests((requests) -> requests
						.requestMatchers("/").permitAll()
						.requestMatchers("/login/**").permitAll()
						.requestMatchers("/register/**").permitAll()
						.requestMatchers("/dashboard").hasAnyRole("ADMIN", "USER")
						.requestMatchers("/blog/new").hasRole("USER")
						.requestMatchers("/admin/blog/new").hasRole("ADMIN")
						.anyRequest().authenticated())
				.formLogin((form) -> form
						.loginPage("/login")
						.loginProcessingUrl("/login")
						.defaultSuccessUrl("/dashboard")
						.permitAll())
				.logout((logout) -> logout.permitAll());

		return http.build();
	}

}
