package in.sp.main.Entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String role;

    // One-to-many relationship with Journal entity
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore // Add this annotation to prevent infinite recursion
    private List<Journal> journals = new ArrayList<>();

    // Default constructor
    public User() {
    }

    // Getter for journals
    public List<Journal> getJournals() {
        return journals;
    }

    // Setter for journals
    public void setJournals(List<Journal> journals) {
        this.journals = journals;
    }

    // Getter for id
    public Long getId() {
        return id;
    }

    // Setter for id
    public void setId(Long id) {
        this.id = id;
    }

    // Getter for username
    public String getUsername() {
        return username;
    }

    // Setter for username
    public void setUsername(String username) {
        this.username = username;
    }

    // Getter for password
    public String getPassword() {
        return password;
    }

    // Setter for password
    public void setPassword(String password) {
        this.password = password;
    }

    // Getter for role
    public String getRole() {
        return role;
    }

    // Setter for role
    public void setRole(String role) {
        this.role = role;
    }

    // Parameterized constructor
    public User(Long id, String username, String password, String role, List<Journal> journals) {
        super();
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.journals = journals;
    }

    // Helper method to add journals to the user
    public void addJournal(Journal journal) {
        journals.add(journal);
        journal.setUser(this); // Set the user reference in the journal
    }
}
