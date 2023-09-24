package com.artlable.backend.novel.command.domain.repository;

import com.artlable.backend.novel.command.domain.aggregate.entity.NovelCharacter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NovelCharacterRepository extends JpaRepository<NovelCharacter, Long> {

    List<NovelCharacter> findByCharacterIsDeletedFalseOrderByCharacterNoDesc();

    NovelCharacter findByCharacterNo(Long characterNo);
}
