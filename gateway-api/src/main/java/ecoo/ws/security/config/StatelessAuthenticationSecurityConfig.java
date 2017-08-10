package ecoo.ws.security.config;

import ecoo.service.UserService;
import ecoo.ws.security.filter.CORSFilter;
import ecoo.ws.security.filter.StatelessAuthenticationFilter;
import ecoo.ws.security.filter.StatelessLoginFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@Order(1)
@EnableWebSecurity
@Configuration
public class StatelessAuthenticationSecurityConfig extends WebSecurityConfigurerAdapter {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private UserService userService;

    public StatelessAuthenticationSecurityConfig() {
        super(true);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        final String secret = "dAm8#uba668abusp123231sfd34fss";

        http
                .addFilterBefore(new CORSFilter(), ChannelProcessingFilter.class)
                .csrf().disable()
                .exceptionHandling().and()
                .anonymous().and()
                .servletApi().and()
                .headers().cacheControl().and()
                .and().authorizeRequests()

                //allow anonymous resource requests
                .antMatchers("/").permitAll()
                .antMatchers("/favicon.ico").permitAll()
                .antMatchers("/resources/**").permitAll()

                //allow anonymous POSTs to login
                .antMatchers(HttpMethod.POST, "/api/login").permitAll()
                //.antMatchers(HttpMethod.POST, "/api/signatures").permitAll()
                .antMatchers(HttpMethod.POST, "/api/users/register").permitAll()
                .antMatchers(HttpMethod.POST, "/api/users/register/validate").permitAll()
                .antMatchers(HttpMethod.POST, "/api/users/forgotPassword").permitAll()

                //allow anonymous GETs to health
                .antMatchers(HttpMethod.GET, "/health/**").permitAll()
                .antMatchers(HttpMethod.GET, "/metrics/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/profile-info").permitAll()

                //allow anonymous GETs to API
                .antMatchers(HttpMethod.OPTIONS, "/api/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/**").permitAll()
                .antMatchers(HttpMethod.PATCH, "/api/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/**").permitAll()

                //defined Admin only API area
                //.antMatchers("/admin/**").hasRole("ADMIN")

                //all other request need to be authenticated
//                .anyRequest().hasRole("USER").and()
                .anyRequest().authenticated().and()

                // custom JSON based authentication by POST of {"username":"<name>","password":"<password>"} which sets the token header upon authentication
                .addFilterBefore(new StatelessLoginFilter("/api/login", secret, userService, authenticationManager())
                        , UsernamePasswordAuthenticationFilter.class)

                // custom JWTToken based authentication based on the header previously given to the client
                .addFilterBefore(new StatelessAuthenticationFilter(secret)
                        , UsernamePasswordAuthenticationFilter.class);
    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
    }
}
