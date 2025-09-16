package com.library.management.controller;

import com.library.management.model.Member;
import com.library.management.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
@CrossOrigin
public class MemberController {
    @Autowired
    private MemberService memberService;

    @PostMapping
    public Member saveMember(@RequestBody Member member){
        return memberService.saveMember(member);
    }

    @GetMapping
    public List<Member> getAllMembers(){
        return memberService.getAllMembers();
    }

    @GetMapping("/{id}")
    public Member getMemberById(@PathVariable long id){
        return memberService.getMemberById(id);
    }

    @PutMapping("/{id}")
    public Member updateMember(@PathVariable long id, @RequestBody Member memberDetails){
        return memberService.updateMember(id,memberDetails);
    }

    @DeleteMapping("/{id}")
    public String deleteMember(@PathVariable long id){
        try{
            memberService.deleteMember(id);
            return "Member Removed Successfuly";

        } catch (Exception e) {
            return "Something Worng "+e ;
        }
    }
}
