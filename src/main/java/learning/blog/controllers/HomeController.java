package learning.blog.controllers;

import learning.blog.config.ConfigData;
import learning.blog.models.Article;
import learning.blog.models.Pop;
import learning.blog.service.ArticleService;
import learning.blog.service.PopService;
import learning.blog.util.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.security.auth.login.Configuration;
import java.util.List;
import java.util.*;

@Controller
public class HomeController {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private PopService popService;
    
    

    @RequestMapping(value = {"/home"," ","/"})
    public String home(@RequestParam(defaultValue = "0") int page,
                       Model model) {

        Page<Article> posts = articleService.findAllOrderedByDatePageable(page);
        String URL = ConfigData.BACKGROUND_URL;
        String QUOTE = ConfigData.HOME_QUOTE;
        String searchURL = "/searchCategory";
        Pager pager = new Pager(posts);

        List<Pop> pops =  popService.findAll();
       // System.out.println("Total pops: " + pops.size());

        model.addAttribute("pops",pops);
        model.addAttribute("searchURL",searchURL);
        model.addAttribute("URL",URL);
        model.addAttribute("QUOTE", QUOTE);
        model.addAttribute("pager", pager);
        
        return "home";
    }
}
