package com.artlable.backend.webtoon.command.domain.repository;

import com.artlable.backend.webtoon.command.domain.aggregate.entity.Inference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InferenceRepository extends JpaRepository<Inference, Long> {

    List<Inference> findByInferenceIsDeletedFalseOrderByInferenceNoDesc();

    Inference findByInferenceNo(Long inferenceNo);
}
