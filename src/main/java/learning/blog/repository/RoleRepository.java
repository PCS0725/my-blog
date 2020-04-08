package learning.blog.repository;

import learning.blog.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRole(String role);
}
