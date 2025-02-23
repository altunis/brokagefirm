package brokagefirm.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;
import brokagefirm.model.Customer;
import brokagefirm.service.CustomerService;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	 
	@Autowired
	 private CustomerService customerService;
	
	
	@Value("${security.username.admin}")
	public String adminUserName;
	
	@Value("${security.password.admin}")
	public String password;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.formLogin(formLogin ->
		formLogin.permitAll().defaultSuccessUrl("/swagger-ui/index.html", true)
        .permitAll());
		http.csrf(AbstractHttpConfigurer::disable);
//		http.headers().frameOptions().disable();
		http.authorizeHttpRequests(auth -> auth
				  .requestMatchers("/login").permitAll()).csrf(AbstractHttpConfigurer::disable);;
		http.authorizeHttpRequests(auth -> auth
				  .requestMatchers("/customer").authenticated()).csrf(AbstractHttpConfigurer::disable);
		http.authorizeHttpRequests(auth -> auth
				  .requestMatchers("/order").authenticated()).csrf(AbstractHttpConfigurer::disable);
		http.authorizeHttpRequests(auth -> auth
				  .requestMatchers("/asset").authenticated()).csrf(AbstractHttpConfigurer::disable);
		http.authorizeHttpRequests(h2 -> h2.requestMatchers(toH2Console()).permitAll()).csrf().ignoringRequestMatchers(toH2Console());
		http.authorizeHttpRequests(auth -> auth
				  .requestMatchers("/").permitAll()).csrf(AbstractHttpConfigurer::disable);;
		http.authorizeHttpRequests(auth -> auth
				  .requestMatchers("/**").authenticated()).csrf(AbstractHttpConfigurer::disable);;
		return http.build();
	}
	
	public void addViewControllers(ViewControllerRegistry registry) {
	    registry.addViewController("/").setViewName("forward:/login");
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
		InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
		UserDetails userAdmin =
			 User.withDefaultPasswordEncoder()
				.username(adminUserName)
				.password(password)
				.roles("ADMIN")
				.authorities("ADMIN")
				.build();
		userDetailsManager.createUser(userAdmin);
		List<Customer> customers= customerService.getAllCustomers();
		UserDetails sampleUser =
				 User.withDefaultPasswordEncoder()
					.username("USER")
					.password("1234")
					.roles("USER")
					.build();
		userDetailsManager.createUser(sampleUser);
		for (Customer customer : customers) {
			UserDetails user =
					 User.withDefaultPasswordEncoder()
						.username(customer.getName())
						.password(customer.getPassword())
						.roles("USER")
						.build();

			
			userDetailsManager.createUser(user);
			
		}
		
		return userDetailsManager;
	}

}
