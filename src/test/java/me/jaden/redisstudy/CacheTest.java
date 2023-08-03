package me.jaden.redisstudy;

import me.jaden.redisstudy.member.api.response.MemberView;
import me.jaden.redisstudy.member.domain.Member;
import me.jaden.redisstudy.member.repository.MemberRepository;
import me.jaden.redisstudy.member.service.MemberService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class CacheTest {
    @MockBean
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private CacheManager cacheManager;

    private static final Long MEMBER_ID = 1l;

    @AfterEach
    void removeCache() {
        Cache cache = cacheManager.getCache("memberView");
        if (cache != null) {
            cache.evict(MEMBER_ID);
        }
    }

    @Test
    void cacheTest() {
        Member member = Member.builder()
                .id(MEMBER_ID)
                .name("jaden")
                .age(29)
                .email("jaden@email.com")
                .build();

        given(memberRepository.findById(MEMBER_ID))
                .willReturn(Optional.of(member));

        MemberView cacheMiss = memberService.findById(MEMBER_ID);
        MemberView cacheHit = memberService.findById(MEMBER_ID);

        assertThat(cacheMiss).isEqualTo(MemberView.convertFromMember(member));
        assertThat(cacheHit).isEqualTo(MemberView.convertFromMember(member));

        verify(memberRepository, times(1)).findById(MEMBER_ID);
    }
}
