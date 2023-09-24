package com.artlable.backend.novel.command.domain.repository;

import com.artlable.backend.novel.command.domain.aggregate.entity.Novel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NovelRepository extends JpaRepository<Novel, Long> {

    List<Novel> findByNovelIsDeletedFalseOrderByNovelNoDesc();

    Novel findByNovelNo(Long novelNo);
}
