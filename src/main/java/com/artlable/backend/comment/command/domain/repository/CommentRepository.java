package com.artlable.backend.comment.command.domain.repository;

import com.artlable.backend.comment.command.domain.aggregate.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {

}
