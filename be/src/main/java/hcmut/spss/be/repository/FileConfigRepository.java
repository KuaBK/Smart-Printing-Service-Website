package hcmut.spss.be.repository;

import hcmut.spss.be.entity.fileConfig.FileConfig;
import hcmut.spss.be.entity.user.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface FileConfigRepository extends JpaRepository<FileConfig, Long> {
//    @Query(value = "select fc.id, fc.paper_size, fc.paper_range, fc.sides, fc.number_of_copies, fc.layout, fc.color, fc.scale, fc.page_of_sheet, fc.margin, fc.qr_code, fc.document_id from file_config fc " +
//            "inner join file_config_by_student fcbs on fc.id = fcbs.file_config_id" +
//            "where fcbs.student_id = :id", nativeQuery = true)
//    List<FileConfig> findAllByStudentId(@Param("id") Long id);

    List<FileConfig> getFileConfigsByStudent(User student);
}
