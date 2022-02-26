package jpabook.japshop.controller;

import jpabook.japshop.domain.Address;
import jpabook.japshop.domain.Member;
import jpabook.japshop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String crateForm(Model model){
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String crate(@Valid MemberForm form, BindingResult result){

        if(result.hasErrors()){
            //에러가 있으면 폼으로 리턴 할것
            return "members/createMemberForm";
            //@NotEmpty(message = "회원 이름은 필수 입니다") 메세지를 리턴한다.
        }

        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);

        memberService.join(member);
        return "redirect:/";
    }
    //valid 쓰면 벨리데이션 가능

    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findMembers();
        // DTO를 변환하여 화면에 필요한 것들을 뿌리기를 권장 드린다. 지금은 트레이드 오프땜에 ㄱㅊ API 를 만들때는 엔티티를 외부로 반환하지말자.
        //API 스펙이 불완전해진다. 템플릿엔진은 덜 고려해도 되지만 제일깔끔한것들이다.
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
