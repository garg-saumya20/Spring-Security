package in.sp.main.Controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.sp.main.Entity.User;
import in.sp.main.Repository.UserRepository;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PutMapping("/update")
    public String updateUser(@RequestBody User user) {
    	Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
    	String userName=authentication.getName();
    	User userInDb=userRepository.findByUsername(userName);
    	if(userInDb !=null)
    	{
    		userInDb.setUsername(user.getUsername());
    		userInDb.setPassword(passwordEncoder.encode(user.getPassword()));
    		userInDb.setRole(user.getRole());
    		userRepository.save(userInDb);
    	}
    	return "User updated successfully"; 
    	
    	
        
    }

    
}
