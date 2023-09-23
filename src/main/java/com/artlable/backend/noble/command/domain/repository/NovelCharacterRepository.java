package com.artlable.backend.noble.command.domain.repository;

import com.artlable.backend.noble.command.domain.aggregate.entity.NovelCharacter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NovelCharacterRepository extends JpaRepository<NovelCharacter, Long> {

    List<NovelCharacter> findByCharacterOrderByCharacterNoDesc();

    NovelCharacter findByCharacterNo(Long characterNo);
}
