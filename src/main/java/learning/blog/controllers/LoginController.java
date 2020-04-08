package learning.blog.controllers;

import learning.blog.models.User;
import learning.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping(value="/login", method = RequestMethod.GET)
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }


    @RequestMapping(value="/registration", method = RequestMethod.GET)
    public ModelAndView registration(){
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        //check the username submitted in the database.
        Optional<User> userOptional = userService.findUserByUsername(user.getUsername());
        User userExists = userOptional.isPresent()?userOptional.get():null;
        //if the username already exists
        if(userExists!=null){
            bindingResult
                    .rejectValue("username", "error.user",
                            "There is already a user registered with the user name provided");
        }
        //if there is any validation constraint violation in the input.
        //Validation constraints were specified during entity definition.
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        }
        else{
            userService.save(user);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("registration");
        }

        return modelAndView;
    }

    @RequestMapping(value="/admin/home", method = RequestMethod.GET)
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> userOptional = userService.findUserByUsername(auth.getName());
        User user = userOptional.isPresent()?userOptional.get():null;
        modelAndView.addObject("userName", "Welcome " + user.getUsername() + "/" + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
        modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");
        modelAndView.setViewName("admin/home");
        return modelAndView;
    }

}
