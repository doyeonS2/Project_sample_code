package com.kh.RestApi.service;
import com.kh.RestApi.dao.MemberRepository;
import com.kh.RestApi.entity.Member;
import com.kh.RestApi.vo.MemberDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service // 서비스 레이어, 비즈니스 로직을 가진 클래스로 서비스를 빈(bean)에 등록 // Bean : 스프링에서 관리하는 객체
@Slf4j // 로그를 찍기 위함
public class MemberService {
    // 레파지토리를 가져와서 등록
    private MemberRepository memberRepository; // 의존성 주입 : 제어 역전의 방법 중 하나로 사용할 객체를 직접 생성하지 않고 외부 컨테이너가 생성한 객체를 주입받아 사용하는 방식을 의미
    public MemberService(MemberRepository memberRepository) { // 매개변수로 전달받는 방법(레파지토리를 외부에서 생성)
        this.memberRepository = memberRepository;
    }
    // 컨트롤러가 정보에 개입은 못하고 단지 요청과 응답만! 전달만! (데이터 전달용)
    // 전체 회원 조회
    public List<MemberDTO> getMemberList() {
        List<MemberDTO> memberDTOS = new ArrayList<>(); // 회원정보에 대한 전체 조회, 프엔이 요청하는 정보가 똑같다면 필요없는 코드
        List<Member> memberList = memberRepository.findAll(); // 엔티티와 똑같은 db모양으로 담아야 함
        // 향상된 for문 : 배열요소를 자동으로 순회
        for(Member e : memberList) {
            MemberDTO memberDTO = new MemberDTO();
            memberDTO.setUser(e.getUserId()); // 값을 담음
            memberDTO.setPwd(e.getPwd());
            memberDTO.setName(e.getName());
            memberDTO.setEmail(e.getEmail());
            memberDTO.setGrade("VIP"); // grade는 db테이블에 없었음 => 조인관계 이용(지금은 임시로 VIP넣음)
            memberDTOS.add(memberDTO);
        }
        return memberDTOS;
    }


    // userID로 회원 조회하기
    public MemberDTO getMemberList(String user) {
        Member member = memberRepository.findByUserId(user); // 엔티티와 똑같은 db모양으로 담아야 함
        // 향상된 for문 : 배열요소를 자동으로 순회
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setUser(member.getUserId()); // 값을 담음
        memberDTO.setPwd(member.getPwd());
        memberDTO.setName(member.getName());
        memberDTO.setEmail(member.getEmail());
        memberDTO.setGrade("VIP"); // grade는 db테이블에 없었음 => 조인관계 이용(지금은 임시로 VIP넣음)
        return memberDTO;
    }


    // 회원가입
    public boolean regMember(String userId, String pwd, String name, String mail) { // 회원가입은 성공, 실패 두가지여서 boolean
        Member memberInfo = new Member(); // 객체 생성(여기다가 정보를 담아줄거야~)
        memberInfo.setUserId(userId); // 프엔에서 날라온 정보를 담아주기
        memberInfo.setPwd(pwd);
        memberInfo.setName(name);
        memberInfo.setEmail(mail);
        memberInfo.setRegData(LocalDateTime.now());
        // 레파지토리를 호출
        Member rst = memberRepository.save(memberInfo);
        log.warn(rst.toString()); // 찍어보기(확인용)
        return true;
    }
    // 로그인
    public boolean loginCheck(String user, String pwd) {
        List<Member> memberInfoList = memberRepository.findByUserIdAndPwd(user, pwd);
        for(Member e : memberInfoList) {
            return true;
        }
        return false;
    }
}
