package pl.karas.springboot2securitydb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.karas.springboot2securitydb.repository.entity.VerificationToken;

@Repository
public interface VerificationTokenRepo extends JpaRepository<VerificationToken, Long> {

    VerificationToken findByValue(String value);

}
