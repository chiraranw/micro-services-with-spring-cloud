package com.chiraranw.msmembersservice.service;


import com.chiraranw.msmembersservice.model.Member;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class MemberService {

    List<Member> members = Arrays.asList(
            new Member(111, "Nation"),
            new Member(222, "Jim"),
            new Member(333, "Tim")
    );


    public List<Member> getMembers() {
        return this.members;
    }

    public Member getMb(int mbId) {
        final Member[] temp = new Member[1];
        members.stream()
                .filter(m -> m.getId() == mbId)
                .findFirst()
                .ifPresent(optMb -> {
                    temp[0] = optMb;
                });

        return temp[0];
    }


}
