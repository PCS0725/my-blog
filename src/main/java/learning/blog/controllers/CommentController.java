package learning.blog.controllers;


import learning.blog.models.Article;
import learning.blog.models.Comment;
import learning.blog.models.Pop;
import learning.blog.models.User;
import learning.blog.service.ArticleService;
import learning.blog.service.CommentService;
import learning.blog.service.PopService;
import learning.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class CommentController {

    private final ArticleService postService;
    private final UserService userService;
    private final CommentService commentService;
    private final PopService popService;

    @Autowired
    public CommentController(ArticleService postService, UserService userService, CommentService commentService, PopService popService) {
        this.postService = postService;
        this.userService = userService;
        this.commentService = commentService;
        this.popService = popService;
    }

    //creating the commment
    @RequestMapping(value = "/createComment", method = RequestMethod.POST)
    public String createNewPost(@Valid Comment comment,
                                BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "/commentForm";

        } else {
            Optional<Article> article = postService.findForId(comment.getArticle().getId());
            if(article.isPresent()) {
                commentService.save(comment);
                return "redirect:/article/" + comment.getArticle().getId();
            }
            else{
                return "redirect:/error";
            }

        }
    }

    //when createComment is requested with get method,
    //Return the form with the user and article objects set.
    //these objects will be used by the "POST request there.
    @RequestMapping(value = "/createComment/{id}", method = RequestMethod.GET)
    public String commentPostWithId(@PathVariable Long id,
                                    Principal principal,
                                    Model model) {

        Optional<Article> post = postService.findForId(id);

        if (post.isPresent()) {
            Optional<User> user = userService.findUserByUsername(principal.getName());

            if (user.isPresent()) {
                Comment comment = new Comment();
                comment.setCommentor(user.get());
                comment.setArticle(post.get());

                model.addAttribute("comment", comment);

                return "/commentForm";

            } else {
                model.addAttribute("errorMessage","Log in to comment");
                return "/error";
            }

        } else {
            model.addAttribute("errorMessage","No such post found");
            return "/error";
        }
    }

}
