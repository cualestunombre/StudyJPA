package spring.datajpa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import spring.datajpa.entity.Member;
import spring.datajpa.repository.MemberDto;
import spring.datajpa.repository.MemberRepository;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;
    @GetMapping("/member/v1/{id}")
    public String getMember(@PathVariable("id")Long id){
        return memberRepository.findById(id).orElse(new Member("hey")).getUsername();
    }

    @GetMapping("/member/v2/{id}")
    public String getMember(@PathVariable("id")Member member){
        return member.getUsername();
    }

    @GetMapping("/members")
    public Page<MemberDto> list(Pageable pageable){
        Page<Member> page = memberRepository.findAll(pageable);
        Page<MemberDto> pageDto = page.map(ele->{
            String teamName = "";
            if(ele.getTeam()!=null){
                teamName = ele.getTeam().getName();
            }
            return new MemberDto(ele.getId(),ele.getUsername(),teamName);
        });

        return pageDto;
    }


}
