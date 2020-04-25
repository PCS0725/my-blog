// package learning.blog;

// import learning.blog.models.Article;
// import learning.blog.models.Comment;
// import learning.blog.models.Role;
// import learning.blog.models.User;
// import learning.blog.repository.RoleRepository;
// import learning.blog.service.ArticleService;
// import learning.blog.service.UserService;
// import learning.blog.service.impl.RoleService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.CommandLineRunner;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.stereotype.Component;

// import javax.persistence.EntityManager;
// import java.util.*;

// public class Bootstrap implements CommandLineRunner {

//     private final ArticleService articleService;
//     private final RoleService roleService;
    
//     // @Autowired
//     // UserService userService;
    
    
//     public Bootstrap(ArticleService articleService, RoleService roleService) {
//         this.articleService = articleService;
//         this.roleService = roleService;
//     }
//     @Autowired
//     EntityManager entityManager;
//     @Override
//     public void run(String... args) throws Exception {
        
//         Article article = new Article();
//         article.setAuthor("Prabhat");
//         article.setCategory("dsa");
//         article.setContent("This one is coming from the cloud");
//         article.setCreateDate(new Date());
//         article.setTitle("The first Article");
//         Article savedArticle = articleService.save(article);

//         Role role1 = new Role();
//         //role1.setId(1l);
//         role1.setRole("ADMIN");
//         Role savedRole = roleService.save(role1);
        
//         System.out.println("Saved Role is : " + savedRole.getRole());

//         // User user = new User();
//         // user.setPassword(new BCryptPasswordEncoder().encode("ekdoteen"));
//         // user.setActive(true);
//         // user.setEmail("prabhat@gmail.com");
//         // Optional<Role> userRole = roleRepository.findById(1L);
//         // user.setRoles(new HashSet<Role>(Arrays.asList(userRole.get())));
//         // user.setName("Prabhat");
//         // user.setLastName("Sharma");
//         // user.setUsername("sahabji");
//         // List<Comment> holder = new ArrayList<>();
//         // user.setComments(holder);
//         // User savedUser = userService.saveAdmin(user);
//         // System.out.println("Saved Article  ");
//         //System.out.println("Saved user: " + savedUser.getUsername());
//     }
// }
