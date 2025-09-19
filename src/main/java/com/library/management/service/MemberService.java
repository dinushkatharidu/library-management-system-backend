package com.library.management.service;

import com.library.management.model.Member;
import com.library.management.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public List<Member> getAllMembers(){
        return memberRepository.findAll();
    }

    public Member getMemberById(Long memberId){
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found with this ID"));
    }



    public Member saveMember(Member newMember){
        return memberRepository.save(newMember);
    }

    public Member updateMember(Long memberId, Member memberDetails){
        Member currentMember = getMemberById(memberId);

        currentMember.setName(memberDetails.getName());
        currentMember.setEmail(memberDetails.getEmail());
        currentMember.setAddress(memberDetails.getAddress());
        currentMember.setPhone(memberDetails.getPhone());
        currentMember.setRegistrationDate(memberDetails.getRegistrationDate());
        return memberRepository.save(currentMember);
    }

    public void deleteMember(Long memberId){
           Member deletingMember =  getMemberById(memberId);
           memberRepository.delete(deletingMember);

    }

}
