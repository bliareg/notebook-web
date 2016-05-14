package bliareg.organizer.config;

import bliareg.organizer.service.MyUserDetailsService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by bliareg on 12.12.15.
 */

@Configuration
@ComponentScan
public class Config {

    @Bean
    public MyUserDetailsService userService(){
           return new MyUserDetailsService();
    }

}
