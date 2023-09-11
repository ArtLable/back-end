package com.artlable.backend.member.command.application.service;

import com.artlable.backend.member.command.domain.repository.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateMemberService {

    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;
    
}
