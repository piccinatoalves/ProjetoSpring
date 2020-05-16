package br.biblioteca.livros.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class WebSecurityConfig  extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
	   return super.authenticationManager();
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	// HABILITA O FRAME DO H2-CONSOLE
	  http.headers().frameOptions().disable();
      http.csrf().disable()
      .authorizeRequests()
      .antMatchers(HttpMethod.GET, "/auth/registration").permitAll()
      .antMatchers(HttpMethod.POST, "/auth/registration").permitAll()
      .antMatchers(HttpMethod.GET, "/auth/list").hasRole("BASIC")
      .antMatchers(HttpMethod.GET, "/auth/listadmin").hasRole("ADMIN")
      .antMatchers(HttpMethod.GET, "/livros/list").hasRole("BASIC")
      .antMatchers(HttpMethod.GET, "/livros/form").hasRole("BASIC")
      .antMatchers(HttpMethod.POST, "/livros/form").hasRole("BASIC")
      .antMatchers(HttpMethod.GET, "/autores/list").hasRole("ADMIN")
      .antMatchers(HttpMethod.GET, "/autores/form").hasRole("ADMIN")
      .antMatchers(HttpMethod.POST, "/autores/form").hasRole("ADMIN")
      .and()
      .formLogin()
      .loginPage("/auth/login")
      .permitAll()
      .and() .logout().permitAll();

	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	    auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}
	
}