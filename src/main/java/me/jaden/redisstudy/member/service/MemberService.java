package me.jaden.redisstudy.member.service;

import lombok.RequiredArgsConstructor;
import me.jaden.redisstudy.member.api.request.MemberJoin;
import me.jaden.redisstudy.member.api.response.MemberView;
import me.jaden.redisstudy.member.domain.Member;
import me.jaden.redisstudy.member.repository.MemberRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public Long joinMember(MemberJoin memberJoin) {
        Member joinedMember = memberRepository.save(memberJoin.convertToMember());
        return joinedMember.getId();
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "MemberInfo", key = "#memberId")
    public MemberView findById(Long memberId) {
        Member member = findMember(memberId);
        return MemberView.convertFromMember(member);
    }

    private Member findMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new EmptyResultDataAccessException(memberId + "에 해당하는 회원이 존재하지 않습니다.", 1));
    }
}
