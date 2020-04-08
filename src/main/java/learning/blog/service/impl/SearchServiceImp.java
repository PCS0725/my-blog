package learning.blog.service.impl;

import learning.blog.models.Article;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import java.util.List;

@Service
@Profile("search")
public class SearchServiceImp {
    private final EntityManager centityManager;

    @Autowired
    public SearchServiceImp(final EntityManagerFactory entityManagerFactory) {
        this.centityManager = entityManagerFactory.createEntityManager();
    }

    @PostConstruct
    public void initializeHibernateSearch() {

        try {
            FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(centityManager);
            fullTextEntityManager.createIndexer().startAndWait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public List<Article> searchByCategory(String searchTerm) {
        //the search term will be category. Limit category per article to 3
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(centityManager);
        QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Article.class).get();
       //Only this will differ for different queries
        String search = "*" + searchTerm + "*";
        Query query = qb
                    .keyword()
                    .onField("category")
                    .matching(searchTerm)
                    .createQuery();
        javax.persistence.Query jpaQuery = fullTextEntityManager.createFullTextQuery(query, Article.class);
        // execute search
        List<Article> articles = null;
        try {
            articles = jpaQuery.getResultList();
        } catch (NoResultException nre) {
            ;// do nothing
        }
        return articles;
    }

    //phrase queries
    //todo: add search for category, author, title, with sloping and deviations
    //todo : use patterns for phrases
    //combine multiple queries
    @Transactional
    public List<Article> searchByPhrase(String phrase){
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(centityManager);
        QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Article.class).get();
        //Only this will differ for different queries
        Query query = qb
                .phrase()
                .withSlop(4)
                .onField("title")
                .sentence(phrase)
                .createQuery();
        javax.persistence.Query jpaQuery = fullTextEntityManager.createFullTextQuery(query, Article.class);
        // execute search
        List<Article> articles = null;
        try {
            articles = jpaQuery.getResultList();
        } catch (NoResultException nre) {
            ;// do nothing
        }
        return articles;
    }
}


