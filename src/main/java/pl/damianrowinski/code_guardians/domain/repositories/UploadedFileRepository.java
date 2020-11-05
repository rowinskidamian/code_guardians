package pl.damianrowinski.code_guardians.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.damianrowinski.code_guardians.domain.model.entites.UploadedFile;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface UploadedFileRepository extends JpaRepository<UploadedFile, Long> {
}
