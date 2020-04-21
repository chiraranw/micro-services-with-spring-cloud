package com.chiraranw.msmembersservice.control;

import com.chiraranw.msmembersservice.model.Member;
import com.chiraranw.msmembersservice.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/lib/v1/mb")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @RequestMapping(value = "/{mbId}", method = RequestMethod.GET)
    public Member getMb(@PathVariable("mbId") Integer mbId) {
        return memberService.getMb(mbId);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Member> getMembers() {
        return memberService.getMembers();
    }
}
