package spring.datajpa.repository;

import lombok.RequiredArgsConstructor;
import spring.datajpa.entity.Member;

import javax.persistence.EntityManager;
import java.util.List;
@RequiredArgsConstructor
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom{
    private final EntityManager em;
    @Override
    public List<Member> findMemberCustom(int age) {
        return em.createQuery("select m from Member m left join fetch m.team where m.age>:age",Member.class).setParameter("age",age).getResultList();
    }
}
