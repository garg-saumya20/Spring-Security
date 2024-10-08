package in.sp.main.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.sp.main.Entity.User;
import in.sp.main.Service.UserService;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/getAllUsers")
	public ResponseEntity<?> getAllUsers()
	{
			List<User> all=userService.getAllUsers();
				if(all!=null && !all.isEmpty())
				{
					return new ResponseEntity<>(all,HttpStatus.OK);
				}
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
