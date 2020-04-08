package learning.blog.controllers;

import learning.blog.models.Article;
import learning.blog.models.Pop;
import learning.blog.service.PopService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@Controller
public class PopController {
    private final PopService popService;

    public PopController(PopService popService) {
        this.popService = popService;
    }

    @RequestMapping(value = "/pop/{id}", method = RequestMethod.GET)
    public String getPop(@PathVariable Long id, Principal principal, Model model){
        Optional<Pop> pop = popService.findById(id);
        if(pop.isPresent()){
            if(principal != null){
                model.addAttribute("article",pop.get());
                return "/pop";
            }
            else{
                model.addAttribute("errorMessage", "You do not have permission to access this page");
                return "/error";
            }
        }
        else {
            model.addAttribute("errorMessage","Requested article does not exist");
            return "/error";
        }
    }

    @RequestMapping(value = "/admin/newPop", method = RequestMethod.GET)
    public String newPop(Principal principal,
                          Model model) {

        //this page is visible only to the ADMIN. hence we need not add another check for principal here
        //spring will ensure that only ADMIN accesses this url
        Pop pop = new Pop();
        model.addAttribute("article", pop);
        return "admin/popForm";
    }

    @RequestMapping(value = "/admin/newPop", method = RequestMethod.POST)
    public String createNewPop(@Valid Pop pop,
                                BindingResult bindingResult) {
        //binding result helps us in checking if any of the validation constraints specified
        //in entity definition is violated.
        //if there are errors, fields will be set and errors displayed in the form
        if (bindingResult.hasErrors()) {
            return "admin/popForm";
        } else {
            popService.save(pop);
            return "redirect:/home";
        }
    }

    //for editing an existing post
    @RequestMapping(value = "admin/editPop/{id}", method = RequestMethod.GET)
    public String editPopWithId(@PathVariable Long id,
                                 Principal principal,
                                 Model model) {
        //spring allows only the admin to access this url therefore, no need to add extra check here.
        Optional<Pop> optionalPost = popService.findById(id);

        if (optionalPost.isPresent()) {
            Pop pop = optionalPost.get();
            model.addAttribute("article", pop);
            return "admin/popForm";
        }
        else {
            model.addAttribute("errorMessage", "No such pop found!");
            return "redirect:/error";
        }
    }

    @RequestMapping(value = "/admin/deletePop/{id}", method = RequestMethod.POST)
    public String deletePostWithId(@PathVariable Long id,
                                   Principal principal) {

        Optional<Pop> optionalPost = popService.findById(id);

        if (optionalPost.isPresent()) {
            Pop pop = optionalPost.get();
            popService.delete(pop);
            return "redirect:/home";
        }
        else {
            return "redirect:/error";
        }
    }

}
