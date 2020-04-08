package learning.blog.config;

import learning.blog.service.impl.SearchServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/*
We'll use this class only when we need to initialize hibernate search for the first time
For creating the index base, there is segment of code we need to run one time
We create this configuration class and run that code by creating a bean. After that, we can comment it out.
*/
@Configuration
public class SearchConfig{

    private final EntityManager bentityManager;
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    public SearchConfig(EntityManagerFactory entityManager) {
        this.bentityManager = entityManager.createEntityManager();
    }

    /* The SearchServiceImp bean has to be created like this one time only.. to create the initial index base
        After that, we can use the created index. Using this method, we create the bean and use @PostConstruct
        annotation to call the initializer function: initializeHibernateSearch().
     */
//    @Bean
//    SearchServiceImp searchServiceImp() {
//        SearchServiceImp searchServiceImp = new SearchServiceImp(entityManagerFactory);
//        searchServiceImp.initializeHibernateSearch();
//        return searchServiceImp;
//    }
}