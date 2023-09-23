package com.artlable.backend.noble.command.domain.service;

import com.artlable.backend.jwt.TokenProvider;
import com.artlable.backend.member.command.domain.aggregate.entity.Member;
import com.artlable.backend.member.command.domain.repository.MemberRepository;
import com.artlable.backend.noble.command.application.dto.novelcharacter.NovelCreateCharacter;
import com.artlable.backend.noble.command.application.dto.novelcharacter.NovelReadCharacter;
import com.artlable.backend.noble.command.application.dto.novelcharacter.NovelUpdateCharacter;
import com.artlable.backend.noble.command.domain.aggregate.entity.NovelCharacter;
import com.artlable.backend.noble.command.domain.repository.NovelCharacterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NovelCharacterService {

    private final NovelCharacterRepository novelCharacterRepository;
    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;

    // 전체 캐릭터 조회
    @Transactional(readOnly = true)
    public List<NovelReadCharacter> findAllNovelCharacters() {

        List<NovelCharacter> characters = novelCharacterRepository.findByCharacterOrderByCharacterNoDesc();
        List<NovelReadCharacter> characterList = new ArrayList<>();

        for (NovelCharacter novelCharacter : characters) {
            NovelReadCharacter novelReadCharacter = new NovelReadCharacter(novelCharacter);
            characterList.add(novelReadCharacter);
        }

        return characterList;
    }

    // 특정 캐릭터 조회
    @Transactional(readOnly = true)
    public NovelReadCharacter findCharacter(Long characterNo) {

        NovelCharacter novelCharacter = novelCharacterRepository.findByCharacterNo(characterNo);
        NovelReadCharacter readCharacter = new NovelReadCharacter(novelCharacter);

        return readCharacter;
    }

    // 캐릭터 생성
    @Transactional
    public Long createCharacter(NovelCreateCharacter novelCreateCharacter, String accessToken) {

        // 토큰의 유효성 검사
        if (!tokenProvider.validateToken(accessToken)) {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }

        // accessToken을 사용하여 사용자를 인증하고 해당 사용자의 정보를 가져옵니다.
        Authentication authentication = tokenProvider.getAuthentication(accessToken);
        String userEmail = authentication.getName();
        Member member = memberRepository.findMemberByMemberEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));

        NovelCharacter novelCharacter = novelCreateCharacter.toEntity();
        novelCharacter.setMember(member);

        novelCharacterRepository.save(novelCharacter);

        return novelCharacter.getCharacterNo();
    }

    // 소설 수정
    @Transactional
    public Long updateCharacter(Long characterNo, NovelUpdateCharacter updateCharacter, String accessToken) {

        // 토큰의 유효성 검사
        if (!tokenProvider.validateToken(accessToken)) {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }

        // accessToken을 사용하여 사용자를 인증하고 해당 사용자의 정보를 가져옵니다.
        Authentication authentication = tokenProvider.getAuthentication(accessToken);
        String userEmail = authentication.getName();
        Member authenticatedMember = memberRepository.findMemberByMemberEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));

        NovelCharacter novelCharacter = novelCharacterRepository.findByCharacterNo(characterNo);

        if (!novelCharacter.getMember().getMemberEmail().equals(authenticatedMember.getMemberEmail())) {
            throw new IllegalArgumentException("해당 캐릭터를 수정할 권한이 없습니다.");
        }

        novelCharacter.setCharacterName(updateCharacter.getCharacterName());
        novelCharacter.setCharacterGender(updateCharacter.getCharacterGender());
        novelCharacter.setCharacterAppearance(updateCharacter.getCharacterAppearance());
        novelCharacter.setCharacterPersonality(updateCharacter.getCharacterPersonality());
        novelCharacter.setCharacterResult(updateCharacter.getCharacterResult());

        return novelCharacter.getCharacterNo();
    }

    // 캐릭터 삭제
    @Transactional
    public Long deleteCharacter(Long characterNo, String accessToken) {

        // accessToken을 사용하여 사용자를 인증하고 해당 사용자의 정보를 가져옵니다.
        Authentication authentication = tokenProvider.getAuthentication(accessToken);
        String userEmail = authentication.getName();
        Member authenticatedMember = memberRepository.findMemberByMemberEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));

        // 요청된 캐릭터 번호로 캐릭터를 조회합니다.
        NovelCharacter novelCharacter = novelCharacterRepository.findByCharacterNo(characterNo);

        // 인증된 사용자가 캐릭터의 소유자인지 확인합니다.
        if (!novelCharacter.getMember().getMemberEmail().equals(authenticatedMember.getMemberEmail())) {
            throw new IllegalArgumentException("해당 캐릭터를 삭제할 권한이 없습니다.");
        }

        return novelCharacter.getCharacterNo();
    }
}
