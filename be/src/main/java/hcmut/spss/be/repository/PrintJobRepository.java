package hcmut.spss.be.repository;

import hcmut.spss.be.entity.printJob.PrintJob;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrintJobRepository extends JpaRepository<PrintJob, Long> {
    public List<PrintJob> findAllByStudent_UserId(Long userId);
}
