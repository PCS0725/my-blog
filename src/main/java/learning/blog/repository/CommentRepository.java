package learning.blog.repository;

import learning.blog.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
