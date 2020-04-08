package learning.blog.service.impl;

import learning.blog.models.Role;
import learning.blog.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {
    private RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role save(Role role){
        return roleRepository.save(role);
    }
    public Role findByID(Long id){
        Optional<Role> roleOptional = roleRepository.findById(id);
        return roleOptional.isPresent()?roleOptional.get():null;
    }
    public Role findByRole(String role){
        return  roleRepository.findByRole(role);

    }

}
