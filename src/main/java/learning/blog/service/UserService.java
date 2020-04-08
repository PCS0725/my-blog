package learning.blog.service;
import learning.blog.models.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findUserByUsername(String username);
    Optional<User> findUserByEmail(String email);
    User save(User user);

    User saveAdmin(User user);
}
