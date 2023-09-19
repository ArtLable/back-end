package com.artlable.backend.comment.command.application.service;

import com.artlable.backend.comment.command.domain.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private CommentRepository commentRepository;
    private ModelMapper modelMapper;

    //전체조회

    //댓글작성

    //댓글수정

    //댓글삭제


}
