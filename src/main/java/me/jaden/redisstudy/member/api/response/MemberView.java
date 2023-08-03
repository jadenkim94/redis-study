package me.jaden.redisstudy.member.api.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.jaden.redisstudy.member.domain.Member;

@Getter
@NoArgsConstructor
public class MemberView {

    private String name;
    private int age;
    private String email;

    @Builder
    private MemberView(String name, int age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
    }

    public static MemberView convertFromMember(Member member) {
        return MemberView.builder()
                .name(member.getName())
                .age(member.getAge())
                .email(member.getEmail())
                .build();
    }
}
