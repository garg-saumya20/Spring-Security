package in.sp.main.Repository;



import org.springframework.data.jpa.repository.JpaRepository;

import in.sp.main.Entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);

}
