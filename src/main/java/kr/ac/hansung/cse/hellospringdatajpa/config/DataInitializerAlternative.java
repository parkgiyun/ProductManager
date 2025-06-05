package kr.ac.hansung.cse.hellospringdatajpa.config;

import kr.ac.hansung.cse.hellospringdatajpa.entity.Role;
import kr.ac.hansung.cse.hellospringdatajpa.entity.User;
import kr.ac.hansung.cse.hellospringdatajpa.repo.RoleRepository;
import kr.ac.hansung.cse.hellospringdatajpa.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DataInitializerAlternative implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        initializeRoles();
        initializeUsers();
    }
    
    private void initializeRoles() {
        if (roleRepository.findByName("ROLE_ADMIN") == null) {
            roleRepository.save(new Role("ROLE_ADMIN"));
        }
        
        if (roleRepository.findByName("ROLE_USER") == null) {
            roleRepository.save(new Role("ROLE_USER"));
        }
    }
    
    private void initializeUsers() {
        // Create admin user
        if (userRepository.findByEmail("admin@example.com") == null) {
            User adminUser = new User(
                "admin@example.com",
                passwordEncoder.encode("admin123"),
                "Administrator"
            );
            
            // 사용자 먼저 저장
            adminUser = userRepository.save(adminUser);
            
            // 그 다음 역할 추가
            Role adminRole = roleRepository.findByName("ROLE_ADMIN");
            adminUser.addRole(adminRole);
            userRepository.save(adminUser);
        }
        
        // Create regular user
        if (userRepository.findByEmail("user@example.com") == null) {
            User regularUser = new User(
                "user@example.com",
                passwordEncoder.encode("user123"),
                "Regular User"
            );
            
            // 사용자 먼저 저장
            regularUser = userRepository.save(regularUser);
            
            // 그 다음 역할 추가
            Role userRole = roleRepository.findByName("ROLE_USER");
            regularUser.addRole(userRole);
            userRepository.save(regularUser);
        }
    }
}
