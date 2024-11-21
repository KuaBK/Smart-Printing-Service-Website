package hcmut.spss.be.repository;

import hcmut.spss.be.entity.printJob.PrintJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Month;
import java.util.List;

public interface PrintJobRepository extends JpaRepository<PrintJob, Long> {
    List<PrintJob> findAllByStudent_UserId(Long userId);
    @Query("SELECT p FROM PrintJob p WHERE p.student.userId = :userId AND FUNCTION('MONTH', p.jobStartTime) = :month")
    List<PrintJob> findAllByStudent_UserIdAndJobStartTimeMonth(@Param("userId") Long userId, @Param("month") int month);
}
