package bliareg.organizer.controller;

import bliareg.organizer.model.User;
import bliareg.organizer.service.MyUserDetailsService;
import bliareg.organizer.utils.db.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * Created by bliareg on 11.12.15.
 */

@RestController
@CrossOrigin
public class UserRESTController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    //    Повертає користувача бееез пароля
    @RequestMapping(value = "/user", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<User> getUser() {

        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findBylogin(login);

        if (user != null) {
            user.setPassword("");
            return new ResponseEntity<User>(user, HttpStatus.OK);
        } else
            return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }


    /*Обновлює дані користувача, якщо користувач прийшов без пароля
    * то воно використовує старий аа якщо з паролем то шифрує пароль і встановлює нові дані
    * і повертає користувача без пароля*/
    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody User user) {

        if (!userRepository.exists(id))
            return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
        else {
            if (user == null)
                return new ResponseEntity<User>(HttpStatus.EXPECTATION_FAILED);

            if (user.getPassword() == null || user.getPassword().equals("")) {
                User userfromDB = userRepository.findById(id);
                user.setPassword(userfromDB.getPassword());
                userRepository.save(user);
                user.setPassword("");
                return new ResponseEntity<User>(user, HttpStatus.OK);
            } else {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                userRepository.save(user);
                user.setPassword("");
                return new ResponseEntity<User>(user, HttpStatus.OK);
            }

        }
    }

    //    створює користувача і шифрує пароль
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public ResponseEntity createUser(@RequestBody User user) {
        if (userRepository.findBylogin(user.getLogin()) != null)
            return new ResponseEntity(HttpStatus.EXPECTATION_FAILED);
        else {

            user.setPassword(passwordEncoder.encode(user.getPassword()));

            userRepository.save(user);

            Authentication authentication = new UsernamePasswordAuthenticationToken(user.getLogin(), user.getPassword(),
                    AuthorityUtils.createAuthorityList("ROLE_USER"));
                SecurityContextHolder.getContext().setAuthentication(authentication);


            return new ResponseEntity(HttpStatus.OK);
        }

    }

}
