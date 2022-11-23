package com.kh.RestApi.controller;
import com.kh.RestApi.service.MemberService;
import com.kh.RestApi.vo.MemberDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController // 요청에 대한 응답을 해줌 // 뷰...단으로 날림? MVC?
public class MemberController {
    // Service 로직 연결
    private MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    // 전체 회원 조회
    @GetMapping("/GetMember/list")
    public ResponseEntity<List<MemberDTO>> memberlist() { // 요청이 들어오면 서블릿에서 memberlist 불러줌 // 전체 조회라서 입력 파라미터 값이 없어서 memberlist()로만 불러줌
        List<MemberDTO> list = memberService.getMemberList(); // 서비스 로직 불러주기
        return new ResponseEntity(list, HttpStatus.OK); // 안에 값을 넣어줘야 하기 때문에 new 생성 // OK = 200번
    }

    // userID로 회원 조회하기
    @GetMapping("/GetMember")
    public ResponseEntity<List<MemberDTO>> memberList(@RequestParam String userId) {
        // 서비스 로직 호출
        MemberDTO memberDTO = memberService.getMemberList(userId);
        return new ResponseEntity(memberDTO, HttpStatus.OK);
    }

//    // 회원가입 만들기
//    @PostMapping("/RegMember")
//    public ResponseEntity<Boolean> registerMember(@RequestBody Map<String, String> regData) { // Boolean 왜 대문자? 객체형을 넣어줘야해서...(?)
//        String userId = regData.get("user");
//        String pwd = regData.get("pwd");
//        String name = regData.get("name");
//        String mail = regData.get("mail");
//        boolean result = memberService.regMember(userId, pwd, name, mail);
//        if(result) {
//            return new ResponseEntity(true, HttpStatus.OK);
//        } else {
//            return new ResponseEntity(false, HttpStatus.BAD_REQUEST);
//        }
//    }

    // 회원 가입
    @PostMapping("/reg-member")
    public ResponseEntity<Map<String, String>> memberRegister(@RequestBody Map<String, String> regData) {
        String getUserId = regData.get("user");
        String getPwd = regData.get("pwd");
        String getName = regData.get("name");
        String getMail = regData.get("mail");
        boolean result = memberService.regMember(getUserId, getPwd, getName, getMail);
        if(result) {
            return new ResponseEntity(true, HttpStatus.OK);
        } else {
            return new ResponseEntity(false, HttpStatus.BAD_REQUEST);
        }
    }

    // 로그인 체크
    @PostMapping("/Login")
    public ResponseEntity<Boolean> memberLogin(@RequestBody Map<String, String> loginData) {
        String user = loginData.get("user");
        String pwd = loginData.get("pwd");
        boolean result = memberService.loginCheck(user, pwd);
        if(result) {
            return new ResponseEntity(true, HttpStatus.OK);
        } else {
            return new ResponseEntity(false, HttpStatus.BAD_REQUEST);
        }
    }
}