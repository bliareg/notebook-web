package bliareg.organizer.config;

import bliareg.organizer.service.MyUserDetailsService;
import bliareg.organizer.utils.db.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

/**
 * Created by bliareg on 11.12.15.
 */

@Configuration
@EnableWebSecurity
@ComponentScan
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    private MyUserDetailsService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin().loginPage("/").passwordParameter("password").usernameParameter("login")
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET,"/user").authenticated()
                .antMatchers(HttpMethod.POST,"/user").permitAll()
                .antMatchers(HttpMethod.PUT,"/user*").authenticated()
                .antMatchers(HttpMethod.DELETE,"/user").authenticated()
                .antMatchers(HttpMethod.DELETE,"/user/*").authenticated()
                .antMatchers(HttpMethod.GET,"/note").authenticated()
                .antMatchers(HttpMethod.GET,"/note/*").authenticated()
                .antMatchers(HttpMethod.POST,"/note").authenticated()
                .antMatchers(HttpMethod.PUT,"/note*").authenticated()
                .antMatchers(HttpMethod.DELETE,"/note").authenticated()
                .antMatchers(HttpMethod.DELETE,"/note/*").authenticated()
                .antMatchers("/home").authenticated()
                .antMatchers("/").permitAll()
//                .anyRequest().denyAll()
                .and()
                .logout().logoutUrl("/logout").permitAll()
                .and()
                .csrf().disable();
//                .authorizeRequests().anyRequest().permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Bean(name = "passwordEncoder")
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    public MyUserDetailsService getUserService() {
        return userService;
    }

    public void setUserService(MyUserDetailsService userService) {
        this.userService = userService;
    }
}
