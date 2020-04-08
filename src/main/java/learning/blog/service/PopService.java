package learning.blog.service;

import learning.blog.models.Pop;

import java.util.List;
import java.util.Optional;

public interface PopService {
     Pop save(Pop pop);
     void delete(Pop pop);
     List<Pop> findAll();
     Optional<Pop> findById(Long id);
}
