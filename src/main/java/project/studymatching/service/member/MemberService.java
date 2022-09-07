package project.studymatching.service.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.studymatching.dto.member.MemberDto;
import project.studymatching.entity.member.Member;
import project.studymatching.exception.MemberNotFoundException;
import project.studymatching.repository.member.MemberRepository;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberDto read(Long id) {
        return MemberDto.toDto(memberRepository.findById(id).orElseThrow(MemberNotFoundException::new));
    }

    @Transactional
    @PreAuthorize("@memberGuard.check(#id)")
    public void delete(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(MemberNotFoundException::new);
        memberRepository.delete(member);
    }

}
