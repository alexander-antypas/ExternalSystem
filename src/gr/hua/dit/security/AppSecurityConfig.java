package gr.hua.dit.security;

import javax.servlet.Filter;
import javax.sql.DataSource;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.CharacterEncodingFilter;

@Configuration
@ComponentScan(basePackages = { "org.baeldung.security" })
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}
	/*
	@Autowired
	private UserDetailsService userDetailsService;*/
	
	@Autowired
	private DataSource dataSource;

	@Override
	protected void configure(final AuthenticationManagerBuilder auth) throws Exception {

		auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(passwordEncoder())
				.usersByUsernameQuery("select username,password, enabled from user where username=?")
				.authoritiesByUsernameQuery("select username, authority from authorities where username=?");
	}


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		CharacterEncodingFilter filter = new CharacterEncodingFilter();
		filter.setEncoding("UTF-8");
		filter.setForceEncoding(true);
		http.addFilterBefore(filter, (Class<? extends Filter>) CsrfFilter.class);
		
		http.authorizeRequests()
		.antMatchers( "/help", "/about","/contact","/access_denied","/signin","/Servlet","/UserService","/SignUp_External").permitAll()
		.antMatchers( "/application","/info_user").hasRole("USER")
		.antMatchers( "/informStudents","/overviewStudents","/overviewSystem","/secretary_menu","/Update_position","/updateSystem").hasRole("SECRETARIAT")
		.antMatchers( "/admin","/Internal_id_finder","/update_internal").hasRole("ADMIN")
		.antMatchers( "/documents","/points","/user-professor").hasRole("PROFESSOR")
		.anyRequest()
		.authenticated()
		.and()
		.formLogin()
		.loginPage("/signin")
		.loginProcessingUrl("/authUser")
		.defaultSuccessUrl("/definer", true)
		.failureUrl("/denied")
		.permitAll()
		.and()
		.logout()
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		.invalidateHttpSession(true)
		.deleteCookies("JSESSIONID")
		.logoutSuccessUrl("/signin")
		.permitAll()
		.and()
		.exceptionHandling()
		.accessDeniedPage("/denied")
		.and()
		.csrf()
		.disable();

	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**");

	}
}
