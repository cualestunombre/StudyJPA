package spring.datajpa.repository;

import static org.assertj.core.api.Assertions.*;

import org.hibernate.Hibernate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import spring.datajpa.entity.Member;
import spring.datajpa.entity.Team;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    TeamRepository teamRepository;

    @Autowired
    EntityManager em;

    @Test
    public void jpaEventBaseEntity() throws  Exception{
        Member member = new Member("member1");
        memberRepository.save(member);

        Thread.sleep(100);
        member.setUsername("member2");

        em.flush();
        em.clear();

        Member findMember = memberRepository.findById(member.getId()).orElse(null);

        System.out.println(findMember.getUpdatedDate());
        System.out.println(findMember.getCreatedDate());



    }
    @Test
    public void customTest(){
        memberRepository.save(new Member("member1",10));
        memberRepository.save(new Member("member2",15));
        memberRepository.save(new Member("member3",21));
        memberRepository.save(new Member("member4",22));
        memberRepository.save(new Member("member5",23));

        List<Member> members = memberRepository.findMemberCustom(16);
        assertThat(members.size()).isEqualTo(3);

    }
    @Test
    public void graphTest(){
        memberRepository.save(new Member("member1",10));
        memberRepository.save(new Member("member2",15));
        memberRepository.save(new Member("member3",21));
        memberRepository.save(new Member("member4",22));
        memberRepository.save(new Member("member5",23));

        List<Member> members = memberRepository.findByAgeGreaterThanEqual(15);
        //초기화 되어 있는 지 여부, left outer join으로 fetch 조인 함
        members.stream().forEach(e->{System.out.println(Hibernate.isInitialized(e.getTeam()));});

    }
    @Test
    public void bulkUpdate() throws Exception{
        memberRepository.save(new Member("member1",10));
        memberRepository.save(new Member("member2",15));
        memberRepository.save(new Member("member3",21));
        memberRepository.save(new Member("member4",22));
        memberRepository.save(new Member("member5",23));

        int resultCount = memberRepository.bulkAgePlus(20);
        assertThat(resultCount).isEqualTo(3);

    }

    @Test
    public void page() throws Exception{
        memberRepository.save(new Member("member1",10));
        memberRepository.save(new Member("member2",10));
        memberRepository.save(new Member("member3",10));
        memberRepository.save(new Member("member4",10));
        memberRepository.save(new Member("member5",10));

        PageRequest pageRequest = PageRequest.of(0,3, Sort.by(Sort.Direction.DESC,"username").and(Sort.by(Sort.Direction.ASC,"age")));
        Page<Member> page = memberRepository.findByAge(10,pageRequest);

        List<Member> content = page.getContent();
        assertThat(content.size()).isEqualTo(3);
        assertThat(page.getTotalElements()).isEqualTo(5);
        assertThat(page.getNumber()).isEqualTo(0);
        assertThat(page.getTotalPages()).isEqualTo(2);
        assertThat(page.isFirst()).isTrue();
        assertThat(page.hasNext()).isTrue();


    }
    @Test
    public void findByTeamName(){
        Member member = new Member("memberA");
        Member member1 = new Member("memberB");
        memberRepository.save(member);
        memberRepository.save(member1);
        Team team = new Team("teamA");
        member.setTeam(team);
        member1.setTeam(team);
        teamRepository.save(team);
        List<String> teamList = new ArrayList<>();
        teamList.add("teamA");
        List<MemberDto> memberDtos = memberRepository.findMemberDtoByTeamName(teamList);
        assertThat(memberDtos.size()).isEqualTo(2);
    }
    @Test
    public void testMember(){
        Member member = new Member("memberA");
        Member savedMember = memberRepository.save(member);

        Member findMember = memberRepository.findById(savedMember.getId()).get();

        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());

        assertThat(findMember).isEqualTo(member);
    }

    @Test
    public void basicCRUDTest(){
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberRepository.save(member1);
        memberRepository.save(member2);

        Member findMember1 = memberRepository.findById(member1.getId()).get();
        Member findMember2 = memberRepository.findById(member2.getId()).get();
        assertThat(findMember1).isEqualTo(member1);
        assertThat(findMember2).isEqualTo(member2);

        List<Member> all = memberRepository.findAll();
        assertThat(all.size()).isEqualTo(2);

        long count = memberRepository.count();
        assertThat(count).isEqualTo(2);

        memberRepository.delete(member1);
        memberRepository.delete(member2);

        long deletedCount = memberRepository.count();
        assertThat(deletedCount).isEqualTo(0);
    }
    @Test
    void findTest(){
        Member member1 = new Member("seokwoo",20);
        Member member2 = new Member("seokwoo",15);
        memberRepository.save(member1);
        memberRepository.save(member2);
        List<Member> members = memberRepository.findByUsernameAndAgeGreaterThanEqual("seokwoo",16);
        assertThat(members.get(0)).isEqualTo(member1);
    }

    @Test
    void queryTest(){
        Member member1 = new Member("seokwoo",20);
        memberRepository.save(member1);
        Member findMember = memberRepository.findUser("seokwoo",20).get(0);
        assertThat(member1).isEqualTo(findMember);

    }

    @Test
    void dtoTest(){
        Member member = new Member("seokwoo",11);
        memberRepository.save(member);
        Team team = new Team("teamA");
        teamRepository.save(team);
        member.setTeam(team);
        List<MemberDto> dtos = memberRepository.findMemberDto();
        assertThat(dtos.size()).isEqualTo(1);

    }
}
