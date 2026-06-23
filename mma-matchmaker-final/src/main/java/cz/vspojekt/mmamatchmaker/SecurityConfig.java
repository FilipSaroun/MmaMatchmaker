package cz.vspojekt.mmamatchmaker;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

// Konfigurační třída pro zabezpečení aplikace pomocí Spring Security
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Vypneme CSRF ochranu, protože jde o bezstavové REST API
            .csrf(csrf -> csrf.disable())
            
            .authorizeHttpRequests(auth -> auth
                // Definice veřejně přístupných cest (dokumentace, konzole, základní zdroje)
                .requestMatchers("/", "/index.html", "/static/**", "/h2-console/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                
                // Veřejný přístup pouze pro čtení dat (GET metody)
                .requestMatchers(HttpMethod.GET, "/api/fighters/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/matchmaking/**").permitAll()
                
                // Omezení zápisu a úprav dat pouze pro roli ADMIN
                .requestMatchers(HttpMethod.POST, "/api/fighters/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/fighters/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/fighters/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/matchmaking/**").hasRole("ADMIN")
                
                .anyRequest().authenticated()
            )
            // Nastavení základní HTTP autentizace (Basic Auth)
            .httpBasic(Customizer.withDefaults())
            
            // Povolení zobrazení H2 konzole v rámci frame (potřebné pro lokální vývoj)
            .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()));

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // Vytvoření administrátorského účtu v paměti pro autentizaci
        UserDetails admin = User.withDefaultPasswordEncoder()
            .username("admin")
            .password("admin123")
            .roles("ADMIN")
            .build();
            
        return new InMemoryUserDetailsManager(admin);
    }
}