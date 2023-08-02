package me.jaden.redisstudy.member.api.request;

import me.jaden.redisstudy.member.domain.Member;

public record MemberJoin(String name, int age, String email) {

    public Member convertToMember() {
        return Member.builder()
                .name(name)
                .age(age)
                .email(email)
                .build();
    }
}
