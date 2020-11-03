package pl.karas.springboot2securitydb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.karas.springboot2securitydb.repository.entity.AppUser;

@Repository
public interface AppUserRepo extends JpaRepository<AppUser, Long> {

    AppUser findAllByUsername(String username);
}
