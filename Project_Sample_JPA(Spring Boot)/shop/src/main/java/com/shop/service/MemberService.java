package com.shop.service;
import com.shop.entity.Member;
import com.shop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Service
@Transactional // 여러 절차의 일을 묶어서 처리
@RequiredArgsConstructor // 이게 없으면 생성자를 따로 만들어서 의존성 주입을 받아야함
public class MemberService {
    private final MemberRepository memberRepository; // RequiredArgsConstructor가 있으면 이렇게 한줄로 끝날 수 있음 // final 꼭 붙여야 의존성 주입 가능

    //  but, RequiredArgsConstructor가 없으면 이렇게 해야함
//    private MemberRepository memberRepository;
//    public MemberService(MemberRepository memberRepository) { // MemberService라는 생성자를 만듦
//        this.memberRepository = memberRepository;
//    }

    public Member saveMember(Member member) {
        // 회원가입할때, 중복회원인지 체크해줘야함(디비에 저장하기 직전에 중복체크를 하고, 중복이 아니면 디비에 저장)
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }
    private void validateDuplicateMember(Member member) {
        Member findMember = memberRepository.findByEmail(member.getEmail());
        if(findMember != null) { // findMember가 NOT NULL이면 이미 가입된 회원 => 에러처리
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }
}
