package in.sp.main.Controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.sp.main.Entity.Journal;
import in.sp.main.Entity.User;
import in.sp.main.Service.JournalService;
import in.sp.main.Service.UserService;


@RestController
@RequestMapping("/journal")
public class JournalController {

	
	@Autowired
	private JournalService journalService;
	
	@Autowired
	private UserService userService;
	
	
	
	//--------------------------------------getAllJournalsForUser--------------------------------------------
	@GetMapping("/getAllJournalsForUser")
	 public ResponseEntity<?> getAllJournalsForUser() {
		try {
			Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
			  String username = authentication.getName(); 	
			List<Journal> all=journalService.getAllJournalsForUser(username);
	         return new ResponseEntity<>(all,HttpStatus.OK);
			}catch(Exception e)
			{
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
	}
	
	//--------------------------------------createJournal--------------------------------------------
	@PostMapping("/createJournal")
    public ResponseEntity<Journal> createJournal(@RequestBody Journal journal) {
		
		try {
		Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
         journalService.createJournal(journal,username);
         return new ResponseEntity<>(journal,HttpStatus.CREATED);
		}catch(Exception e)
		{
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
    }
	
	
	//--------------------------------------getJournalById--------------------------------------------
	@GetMapping("/id/{id}")
	public ResponseEntity<?> getJournalById(@PathVariable Long id) {
	    try {
	        // Get the current authenticated user's username
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        String username = authentication.getName();
	        
	        // Fetch the user by username
	        User user = userService.findByUserName(username);

	        // Fetch the journal by id
	        Optional<Journal> journalOpt = journalService.findById(id);

	        // Check if the journal exists and belongs to the current user
	        if (journalOpt.isPresent()) {
	            Journal journal = journalOpt.get();
	            
	            // Ensure that the journal belongs to the current user
	            if (journal.getUser().getId().equals(user.getId())) {
	                return new ResponseEntity<>(journal, HttpStatus.OK);
	            } else {
	                return new ResponseEntity<>("Unauthorized to access this journal", HttpStatus.FORBIDDEN);
	            }
	        } else {
	            return new ResponseEntity<>("Journal not found", HttpStatus.NOT_FOUND);
	        }
	    } catch (Exception e) {
	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	//--------------------------------------deleteJournalById--------------------------------------------
	@DeleteMapping("id/{id}")
	public ResponseEntity<?> deleteJournalById(@PathVariable Long id)
	{
		Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
		String username=authentication.getName();
		boolean removed=journalService.deleteById(id,username);
		if(removed)
		{
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		else
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/id/{id}")
	public ResponseEntity<?> updateJournalById(@PathVariable Long id, @RequestBody Journal updatedJournal) {
	    try {
	        // Fetch the authenticated user
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        String username = authentication.getName();
	        User user = userService.findByUserName(username); // Assuming userService has this method

	        // Fetch the journal by ID
	        Optional<Journal> journalOpt = journalService.findById(id);

	        if (journalOpt.isPresent()) {
	            Journal journal = journalOpt.get();

	            // Check if the journal belongs to the authenticated user
	            if (!journal.getUser().getUsername().equals(username)) {
	                return new ResponseEntity<>("You are not authorized to update this journal", HttpStatus.UNAUTHORIZED);
	            }

	            // Update the journal fields (e.g., title and content)
	            journal.setTitle(updatedJournal.getTitle());
	            journal.setContent(updatedJournal.getContent());

	            // Save the updated journal
	            journalService.saveWithUpdateJournal(journal); // Assuming journalService has saveJournal method

	            return new ResponseEntity<>(journal, HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>("Journal not found", HttpStatus.NOT_FOUND);
	        }

	    } catch (Exception e) {
	        // Handle unexpected errors
	        return new ResponseEntity<>("An error occurred while updating the journal: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

}


