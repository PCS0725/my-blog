package learning.blog.service.impl;

import learning.blog.models.Comment;
import learning.blog.models.Pop;
import learning.blog.repository.PopRepository;
import learning.blog.service.PopService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PopServiceImp implements PopService {
    private final PopRepository popRepository;

    public PopServiceImp(PopRepository popRepository) {
        this.popRepository = popRepository;
    }

    @Override
    public Pop save(Pop pop) {
        //this check is performed for editing the pop post.
        //if we are editing a post, we don't want to set comments to an empty string.
        return popRepository.save(pop);
    }

    @Override
    public void delete(Pop pop) {
        popRepository.delete(pop);
    }

    @Override
    public List<Pop> findAll() {
        return popRepository.findAll();
    }

    @Override
    public Optional<Pop> findById(Long id) {
        return popRepository.findById(id);
    }
}
