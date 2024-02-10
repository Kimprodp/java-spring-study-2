package springstudy.secondproject.web.springmvc.v3;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import springstudy.secondproject.domain.member.Member;
import springstudy.secondproject.domain.member.MemberRepository;

@Controller
@RequestMapping("/springmvc/v3/members")
public class SpringMemberControllerV3 {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @GetMapping("/new-form")
    public String newForm() {
        return "new-form";
    }

    @PostMapping("/save")
    public String save(@RequestParam("username") String username,
                             @RequestParam("age") int age,
                             Model model) {

        // 1. Member 저장
        Member member = new Member(username, age);
        memberRepository.save(member);

        // 2. Model에 객체 저장 (이름, 객체)
        model.addAttribute("member", member);

        // 3. View 페이지를 설정
        return "save-result";
    }

    @GetMapping
    public String members(Model model) {

        // 1. 모든 Member 가져오기
        List<Member> members = memberRepository.findAll();

        // 2. Model에 저장
        model.addAttribute("members", members);

        // 3. View 페이지를 설정
        return "members";
    }
}
