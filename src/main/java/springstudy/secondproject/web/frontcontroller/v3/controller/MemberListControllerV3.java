package springstudy.secondproject.web.frontcontroller.v3.controller;

import java.util.List;
import java.util.Map;
import springstudy.secondproject.domain.member.Member;
import springstudy.secondproject.domain.member.MemberRepository;
import springstudy.secondproject.web.frontcontroller.ModelView;
import springstudy.secondproject.web.frontcontroller.MyView;
import springstudy.secondproject.web.frontcontroller.v3.ControllerV3;

public class MemberListControllerV3 implements ControllerV3 {

    private final MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public ModelView process(Map<String, String> paramMap) {

        List<Member> members = memberRepository.findAll();

        ModelView mv = new ModelView("members");
        mv.getModel().put("members", members);

        return mv;
    }
}
