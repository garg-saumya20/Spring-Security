package in.sp.main.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.sp.main.Entity.Journal;

public interface JournalRepository extends JpaRepository<Journal, Long> {
    List<Journal> findByUsername(String username);
}
