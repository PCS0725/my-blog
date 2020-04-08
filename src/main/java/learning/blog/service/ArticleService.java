package learning.blog.service;
import learning.blog.models.Article;
import learning.blog.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ArticleService {

    Optional<Article> findForId(Long id);

    Article save(Article article);

    Page<Article> findAllOrderedByDatePageable(int page);

    Page<Article> findAll(Pageable pageable);

    void delete(Article article);
}
