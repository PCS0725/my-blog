package learning.blog.controllers;

import learning.blog.models.Article;
import learning.blog.service.impl.SearchServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@Profile("search")
@Controller
public class SearchController {

    @Autowired
    private final SearchServiceImp searchServiceImp;

    public SearchController(SearchServiceImp searchServiceImp) {
        this.searchServiceImp = searchServiceImp;
    }


    @RequestMapping(value = "/search",method = RequestMethod.GET)
    public String getsearchPage(Model model){
        //ask the user for what kind of search he/she wants to perform and set the searchBy field
        //By default, this attribute should be set to random.
        //Resolve the searchBy attribute in the POST method and perform the appropriate query.
        String searchBy = "category";
        String query = "Misc";
        model.addAttribute("query",query);
        model.addAttribute("searchBy", searchBy);
        return "searchForm";
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String searchPhrase(Model model){
        String phrase = "Article Page";
        List<Article> results = searchServiceImp.searchByPhrase(phrase);
        model.addAttribute("pager",results);
        return "searchForm";
    }

    @RequestMapping(value = "/searchCategory",method = RequestMethod.GET)
    public String executeSearch(@RequestParam String term, Model model){
            List<Article> results = searchServiceImp.searchByCategory(term);
            System.out.println("Term is " + term);
        System.out.println("First result : " + results.get(0).getTitle());
            model.addAttribute("pager",results);
        return "searchForm";
    }
}
