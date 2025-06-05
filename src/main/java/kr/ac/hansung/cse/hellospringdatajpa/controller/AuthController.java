package kr.ac.hansung.cse.hellospringdatajpa.controller;

import jakarta.validation.Valid;
import kr.ac.hansung.cse.hellospringdatajpa.dto.UserRegistrationDto;
import kr.ac.hansung.cse.hellospringdatajpa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;
    
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }
    
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserRegistrationDto());
        return "register";
    }
    
    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") UserRegistrationDto registrationDto,
                              BindingResult result,
                              RedirectAttributes redirectAttributes) {
        
        if (result.hasErrors()) {
            return "register";
        }
        
        try {
            userService.registerNewUser(registrationDto);
            redirectAttributes.addFlashAttribute("successMessage", "회원가입이 성공적으로 완료되었습니다! 로그인해주세요.");
            return "redirect:/login";
        } catch (RuntimeException e) {
            result.rejectValue("email", "error.user", e.getMessage());
            return "register";
        }
    }
}
