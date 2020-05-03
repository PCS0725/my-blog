package learning.blog.service.impl;

import learning.blog.models.Comment;
import learning.blog.models.Role;
import learning.blog.models.User;
import learning.blog.repository.RoleRepository;
import learning.blog.repository.UserRepository;
import learning.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleService roleService;
    @Autowired
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    
    public UserServiceImp(){
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findUserByUsername(String userName) {
//        Optional<User> userOptional = userRepository.findByUsername(userName);
        return userRepository.findByUsername(userName);
    }

    public User save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(true);
        Optional<Role> userRole = roleRepository.findById(2L);
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole.get())));
        List<Comment> holder = new ArrayList<>();
        user.setComments(holder);
        return userRepository.save(user);
    }
    // public void saveAdmin() {
    //     User user = new User();
    //     user.setPassword(new BCryptPasswordEncoder().encode("hahaha"));
    //     user.setActive(true);
    //     user.setEmail("guru@gmail.com");
    //     Optional<Role> userRole = roleRepository.findById(1L);
    //     user.setRoles(new HashSet<Role>(Arrays.asList(userRole.get())));
    //     user.setName("Prabhat");
    //     user.setLastName("Sharma");
    //     user.setUsername("guru");
    //     List<Comment> holder = new ArrayList<>();
    //     user.setComments(holder);
    //     User savedUser = save(user);
    //     System.out.println("Saved user: " + savedUser.getUsername());
    // }
    
}
