package hcmut.spss.be.repository;

import hcmut.spss.be.entity.printer.Printer;
import hcmut.spss.be.entity.printer.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrinterRepository extends JpaRepository<Printer, Long> {
    List<Printer> findAllByStatusPrinter(Status status);
}
