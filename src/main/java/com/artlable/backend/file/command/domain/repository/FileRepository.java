package com.artlable.backend.file.command.domain.repository;

import com.artlable.backend.file.command.domain.aggregate.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
}
