package springstudy.secondproject.web.frontcontroller.v4.contorller;

import java.util.List;
import java.util.Map;
import springstudy.secondproject.domain.member.Member;
import springstudy.secondproject.domain.member.MemberRepository;
import springstudy.secondproject.web.frontcontroller.v4.ControllerV4;

public class MemberListControllerV4 implements ControllerV4 {

    private final MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public String process(Map<String, String> paramMap, Map<String, Object> model) {
        List<Member> members = memberRepository.findAll();

        model.put("members", members);
        return "members";
    }
}
