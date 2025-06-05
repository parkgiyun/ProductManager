package kr.ac.hansung.cse.hellospringdatajpa.service;

import kr.ac.hansung.cse.hellospringdatajpa.dto.UserRegistrationDto;
import kr.ac.hansung.cse.hellospringdatajpa.entity.Role;
import kr.ac.hansung.cse.hellospringdatajpa.entity.User;
import kr.ac.hansung.cse.hellospringdatajpa.repo.RoleRepository;
import kr.ac.hansung.cse.hellospringdatajpa.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public User registerNewUser(UserRegistrationDto registrationDto) {
        // 이미 존재하는 사용자인지 확인
        if (userRepository.existsByEmail(registrationDto.getEmail())) {
            throw new RuntimeException("이미 사용 중인 이메일입니다");
        }
        
        // 새 사용자 생성
        User user = new User(
            registrationDto.getEmail(),
            passwordEncoder.encode(registrationDto.getPassword()),
            registrationDto.getName()
        );
        
        // 기본 역할 할당 (ROLE_USER)
        Role userRole = roleRepository.findByName("ROLE_USER");
        if (userRole == null) {
            userRole = new Role("ROLE_USER");
        }
        user.addRole(userRole);
        
        return userRepository.save(user);
    }
    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
