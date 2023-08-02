package me.jaden.redisstudy.member.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.jaden.redisstudy.member.api.request.MemberJoin;
import me.jaden.redisstudy.member.api.response.MemberInfo;
import me.jaden.redisstudy.member.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberApi {

    private final MemberService memberService;
    private static final String MEMBER_URL_PREFIX = "/members/";

    @PostMapping("/join")
    public ResponseEntity<Long> join(@RequestBody MemberJoin memberJoin) {
        Long joinedMemberId = memberService.joinMember(memberJoin);

        return ResponseEntity
                .created(URI.create(MEMBER_URL_PREFIX + joinedMemberId))
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberInfo> getMember(@PathVariable("id") Long memberId) {
        MemberInfo memberInfo = memberService.findById(memberId);
        return ResponseEntity.ok(memberInfo);
    }

}
