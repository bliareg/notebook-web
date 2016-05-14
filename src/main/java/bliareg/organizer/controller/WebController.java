package bliareg.organizer.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by bliareg on 11.12.15.
 */

@Controller
public class WebController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String intro(){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(!(auth instanceof AnonymousAuthenticationToken))
            return "redirect:/home";

        return "forward:static/intro.html";
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home(){
        return "forward:static/home.html";
    }

}
