//package config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity.csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
//                .httpBasic(Customizer.withDefaults());
//        return httpSecurity.build();
//    }
//
//    @Bean
//    public InMemoryUserDetailsManager userDetailsService() {
//        UserDetails userDetails = User.withDefaultPasswordEncoder()
//                .username("eureka")
//                .password("password")
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(userDetails);
//    }
//}
