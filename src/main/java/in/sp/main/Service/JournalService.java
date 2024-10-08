package in.sp.main.Service;

import in.sp.main.Entity.Journal;
import in.sp.main.Entity.User;
import in.sp.main.Repository.JournalRepository;
import in.sp.main.Repository.UserRepository;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;



@Service
public class JournalService {

    @Autowired
    private JournalRepository journalRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserService userService;
    
    
  //--------------------------------------saveWithUpdateJournal Journal--------------------------------------------
    public Journal saveWithUpdateJournal(Journal journal) {
        return journalRepository.save(journal);
    }
    
//--------------------------------------Find By Id--------------------------------------------
    public Optional<Journal> findById(Long id) {
        return journalRepository.findById(id);
    }
    
 //--------------------------------------Delete By Id--------------------------------------------
    @Transactional
    public boolean deleteById(Long id, String username) {
    	boolean removed=false;
        try {
            // Fetch the user by username
            User user = userService.findByUserName(username);

            // Remove the journal with the given ID from the user's journal list
             removed = user.getJournals().removeIf(journal -> journal.getId().equals(id));

            if (removed) {
                // If the journal was removed, save the updated user entity
                userService.saveWithUpdateUser(user);
                
                // Delete the journal by its ID from the database
                journalRepository.deleteById(id);
            } 
        } catch (Exception e) {
            // Handle any exception and provide meaningful feedback
            throw new RuntimeException("Error occurred while deleting the journal: " + e.getMessage(), e);
        }
        return removed;
    }


  //--------------------------------------Create Journal--------------------------------------------
    public Journal createJournal(Journal journal,String username) {
       User user= userRepository.findByUsername(username);
       
       if(user!=null)
       {
    	   Journal journal1 = new Journal();
    	   journal1.setTitle(journal.getTitle());
    	   journal1.setContent(journal.getContent());
    	   journal1.setUsername(username);
    	   journal1.setUser(user);
    	   journal1.setCreatedAt(LocalDateTime.now());
    	   user.addJournal(journal1); 
    	   System.out.println(user.getJournals());
           return journalRepository.save(journal1);
       }
       
       throw new RuntimeException("User not found");
    }


  //--------------------------------------getAllJournalsForUser-------------------------------------------
public List<Journal> getAllJournalsForUser(String username)
{
	User user= userRepository.findByUsername(username);
    return user.getJournals();
    
}
    
    
}