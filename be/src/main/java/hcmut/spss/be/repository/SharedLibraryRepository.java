package hcmut.spss.be.repository;

import hcmut.spss.be.entity.sharedLibrary.SharedLibrary;
import hcmut.spss.be.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SharedLibraryRepository extends JpaRepository<SharedLibrary, Long> {
    Optional<SharedLibrary> findByDefaultLibraryTrue();
}
