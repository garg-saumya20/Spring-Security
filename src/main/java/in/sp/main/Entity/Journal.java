package in.sp.main.Entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.annotation.Nonnull;

@Entity
public class Journal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Nonnull
    @Column(nullable = false)
    private String title;
    private String content;
    private String username;
    
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user; 
//    
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore // Add this annotation if you want to avoid returning user details with journals
    private User user; 

 
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(nullable = false, updatable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;  

    
    // Constructors
    public Journal() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Journal(Long id, String title, String content, String username,User user,LocalDateTime createdAt) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.username = username;
		this.user=user;
		this.createdAt = createdAt;
		
	}
	
//	public Journal(Long id, String title, String content, String username,LocalDateTime createdAt) {
//		super();
//		this.id = id;
//		this.title = title;
//		this.content = content;
//		this.username = username;
//		this.createdAt = createdAt;
//		
//	}


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
}
