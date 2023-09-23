package com.artlable.backend.noble.command.domain.repository;

import com.artlable.backend.noble.command.domain.aggregate.entity.Novel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NovelRepository extends JpaRepository<Novel, Long> {

    List<Novel> findByNovelOrderByNovelNoDesc();

    Novel findByNovelNo(Long novelNo);
}
