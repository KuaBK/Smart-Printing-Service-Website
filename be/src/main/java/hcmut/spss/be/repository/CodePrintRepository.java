package hcmut.spss.be.repository;

import hcmut.spss.be.entity.codePrint.CodePrint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CodePrintRepository extends JpaRepository<CodePrint, Long> {
    Optional<CodePrint> findByGeneratedCode(String generatedCode);
}
