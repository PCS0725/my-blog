package learning.blog.repository;

import learning.blog.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
public interface UserRepository extends JpaRepository<User, Long> {
     Optional<User> findByEmail(@Param("email") String email);

     Optional<User> findByUsername(@Param("username") String username);
}
