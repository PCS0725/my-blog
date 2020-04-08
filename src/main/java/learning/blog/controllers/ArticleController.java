package learning.blog.controllers;

import learning.blog.models.Article;
import learning.blog.service.ArticleService;
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
public class ArticleController {
    private final ArticleService articleService;
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    //returns a requested article
    @RequestMapping(value = "/article/{id}", method = RequestMethod.GET)
    public String getPostWithId(@PathVariable Long id,
                                Principal principal,
                                Model model) {
        Optional<Article> optionalPost = articleService.findForId(id);

        // if the article with requested id does not exist, return the error page
        if (optionalPost.isPresent()) {
            Article post = optionalPost.get();

            model.addAttribute("article", post);
            //if the user is not logged in , don't show the article page
            if(principal == null){
                model.addAttribute("errorMessage", "You do not have permission to access this page");
                return "/error";
            }
            return "/article";
        } else{
            model.addAttribute("errorMessage","Requested article does not exist");
            return "/error";
        }
    }

    //For posting a new article
    @RequestMapping(value = "/admin/newPost", method = RequestMethod.GET)
    public String newPost(Principal principal,
                          Model model) {

            //this page is visible only to the ADMIN. hence we need not add another check for principal here
            //spring will ensure that only ADMIN accesses this url
            Article article = new Article();
            model.addAttribute("article", article);
            return "admin/postForm";
    }

    @RequestMapping(value = "/admin/newPost", method = RequestMethod.POST)
    public String createNewPost(@Valid Article article,
                                BindingResult bindingResult) {
        //binding result helps us in checking if any of the validation constraints specified
        //in entity definition is violated.
        //if there are errors, fields will be set and errors displayed in the form
        if (bindingResult.hasErrors()) {
            return "admin/postForm";
        } else {
            articleService.save(article);
            return "redirect:/home";
        }
    }

    //for editing an existing post
    @RequestMapping(value = "admin/editPost/{id}", method = RequestMethod.GET)
    public String editPostWithId(@PathVariable Long id,
                                 Principal principal,
                                 Model model) {
        //spring allows only the admin to access this url therefore, no need to add extra check here.
        Optional<Article> optionalPost = articleService.findForId(id);

        if (optionalPost.isPresent()) {
            Article article = optionalPost.get();
                model.addAttribute("article", article);
                return "admin/postForm";
            }
        else {
            model.addAttribute("errorMessage", "No such article found!");
            return "redirect:/error";
        }
    }

    @RequestMapping(value = "admin/deletePost/{id}", method = RequestMethod.POST)
    public String deletePostWithId(@PathVariable Long id,
                                   Principal principal) {

        Optional<Article> optionalPost = articleService.findForId(id);

        if (optionalPost.isPresent()) {
            Article article = optionalPost.get();
            articleService.delete(article);
            return "redirect:/home";
        }
        else {
            return "redirect:/error";
        }
    }
}
