package in.sp.main.Controller;

import java.util.Arrays;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.sp.main.Entity.User;
import in.sp.main.Repository.UserRepository;

@RestController
@RequestMapping("/public")
public class PublicController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public PublicController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }



//    @PostMapping("/register")
//  public String saveUser(@RequestBody User user) {
//      user.setPassword(passwordEncoder.encode(user.getPassword()));
//      user.setRole(Arrays.asList("User","Admin"));
//      userRepository.save(user);
//      return "User registered successfully";
//  }
    
    @PostMapping("/register")
    public String saveUser(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User registered successfully";
    }

    
    @PostMapping("/login")
    public String loginUser() {
        // Spring Security handles the authentication automatically
        return "Login successful";
    }
}
