package kr.ac.hansung.cse.hellospringdatajpa.config;

import kr.ac.hansung.cse.hellospringdatajpa.entity.Role;
import kr.ac.hansung.cse.hellospringdatajpa.entity.User;
import kr.ac.hansung.cse.hellospringdatajpa.repo.RoleRepository;
import kr.ac.hansung.cse.hellospringdatajpa.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // 역할이 존재하지 않으면 생성
        Role adminRole = roleRepository.findByName("ROLE_ADMIN");
        if (adminRole == null) {
            adminRole = roleRepository.save(new Role("ROLE_ADMIN"));
        }
        
        Role userRole = roleRepository.findByName("ROLE_USER");
        if (userRole == null) {
            userRole = roleRepository.save(new Role("ROLE_USER"));
        }
        
        // 관리자 사용자가 존재하지 않으면 생성
        if (userRepository.findByEmail("admin@example.com") == null) {
            User adminUser = new User(
                "admin@example.com",
                passwordEncoder.encode("admin123"),
                "관리자"
            );
            
            // 중요: 데이터베이스에서 다시 조회한 Role 사용
            Role managedAdminRole = roleRepository.findByName("ROLE_ADMIN");
            adminUser.addRole(managedAdminRole);
            
            userRepository.save(adminUser);
        }
        
        // 일반 사용자가 존재하지 않으면 생성
        if (userRepository.findByEmail("user@example.com") == null) {
            User regularUser = new User(
                "user@example.com",
                passwordEncoder.encode("user123"),
                "일반 사용자"
            );
            
            // 중요: 데이터베이스에서 다시 조회한 Role 사용
            Role managedUserRole = roleRepository.findByName("ROLE_USER");
            regularUser.addRole(managedUserRole);
            
            userRepository.save(regularUser);
        }
    }
}
