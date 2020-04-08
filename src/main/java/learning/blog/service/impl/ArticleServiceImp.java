package learning.blog.service.impl;

import learning.blog.models.Article;
import learning.blog.models.Comment;
import learning.blog.repository.ArticleRepository;
import learning.blog.service.ArticleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleServiceImp implements ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleServiceImp(ArticleRepository postRepository) {
        this.articleRepository = postRepository;
    }

    @Override
    public Optional<Article> findForId(Long id) {
        return articleRepository.findById(id);
    }

    @Override
    public Article save(Article post) {
        //we have to initialize the empty list. Otherwise we'll get a NPE.
        //todo a bug here buddy, find out on edit?
        List<Comment> comments = new ArrayList<>();
        post.setComments(comments);
        if(post.getReadTime() == null)
            post.setReadTime("No time provided");
        if(post.getImageURL() == null)
            post.setImageURL("/assets/Blog-post/blog2.png");
        return articleRepository.save(post);
    }
    //I used this method to delete the test articles. Exploiting the bean lifecycle control which spring provided me
    //with. It would have been difficult to delete the article from the database directly.
//    @PostConstruct
//    public void deleteOld(){
//        long i =0;
//        for(i = 120;i>=0;--i){
//            try {
//                System.out.println("Deleting aarticle with id : " + i);
//                articleRepository.deleteById(i);
//            }catch (Exception e){
//                continue;
//            }
//        }
//    }
    @Override
    public Page<Article> findAllOrderedByDatePageable(int page) {
        int page2 = subtractPageByOne(page);
        return articleRepository.findAllByOrderByCreateDateDesc(PageRequest.of(page2, 5));
    }

    @Override
    public Page<Article> findAll(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Article> list;
        List<Article> articles = articleRepository.findAll();

        if (articles.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, articles.size());
            list = articles.subList(startItem, toIndex);
        }

        Page<Article> articlePage
                = new PageImpl<Article>(list, PageRequest.of(currentPage, pageSize), articles.size());

        return articlePage;
    }

    @Override
    public void delete(Article post) {
        articleRepository.delete(post);
    }

    private int subtractPageByOne(int page){
        return (page < 1) ? 0 : page - 1;
    }
}
