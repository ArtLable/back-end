package com.artlable.backend.files.command.domain.repository;

import com.artlable.backend.files.command.domain.aggregate.entity.Files;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilesRepository extends JpaRepository<Files, Long> {
}
