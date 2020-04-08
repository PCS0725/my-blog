package learning.blog.repository;

import learning.blog.models.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.RepositoryDefinition;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    Page<Article> findByCategoryOrderByCreateDateDesc(String Category, Pageable pageable);
    Page<Article> findAllByOrderByCreateDateDesc(Pageable pageable);
}
