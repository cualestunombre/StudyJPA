package hello.jpa.service;

import hello.jpa.domain.Member;
import hello.jpa.repository.MemberRepository;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class MemberServiceTest {
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void joinTest() {
        Member member = new Member();
        member.setName("kim");

        Long saveId = memberService.join(member);
        assertThat(member).isEqualTo(memberRepository.findOne(saveId));

    }

    @Test
    public void sameMemberTest(){
        Member member1 = new Member();
        member1.setName("kim");
        Member member2 = new Member();
        member2.setName("kim");

        memberService.join(member1);
        assertThatThrownBy(()->memberService.join(member2)).isInstanceOf(IllegalStateException.class);


    }

}
