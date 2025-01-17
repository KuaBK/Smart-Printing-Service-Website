package hcmut.spss.be.repository;

import hcmut.spss.be.entity.user.UserVisit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface UserVisitRepository extends JpaRepository<UserVisit, Long> {

    @Query(value = "SELECT COUNT(*) FROM user_visit " +
            "WHERE creation_date >= :startOfDay AND creation_date < :endOfDay", nativeQuery = true)
    Integer countVisitsInDay(@Param("startOfDay") String startOfDay, @Param("endOfDay") String endOfDay);

    @Query(value = "SELECT DATE(creation_date) AS visit_date, COUNT(*) AS visit_count " +
            "FROM user_visit " +
            "GROUP BY DATE(creation_date) " +
            "ORDER BY visit_date ASC", nativeQuery = true)
    List<Object[]> countVisitsByDay();

}
