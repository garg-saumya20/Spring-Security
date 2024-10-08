package in.sp.main.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.sp.main.Entity.User;
import in.sp.main.Repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	
	public User findByUserName(String username)
	{
		return userRepository.findByUsername(username);
	}
	
	public User saveWithUpdateUser(User user)
	{
		return userRepository.save(user);
	}

	public List<User> getAllUsers() {
		
		return userRepository.findAll();
	}

}

