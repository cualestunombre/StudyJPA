package spring.datajpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import spring.datajpa.entity.Member;

import javax.persistence.Entity;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long>,MemberRepositoryCustom {
    List<Member> findByUsernameAndAgeGreaterThanEqual(String username,int age);

    @Query("select m from Member m where m.username= :username and m.age = :age")
    List<Member> findUser(@Param("username")String username,@Param("age") int age);

    @Query("select m.username from Member m")
    List<String> findUserNameList();

    @Query("select new spring.datajpa.repository.MemberDto(m.id,m.username,t.name) from Member m join m.team t")
    List<MemberDto> findMemberDto();

    @Query("select new spring.datajpa.repository.MemberDto(m.id,m.username,t.name) from Member m join m.team t" +
            " where t.name in :tnames")
    List<MemberDto> findMemberDtoByTeamName(@Param("tnames")List<String> tnames);

    @Query("select m from Member m")
    Page<Member> findByAge(int age, Pageable pageable);

    // count query 분리
    @Query(value="select m from Member m",countQuery = "select count(m.username) from Member m")
    Page<Member> findMemberByAllCountBy(Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);

    @Override
    @EntityGraph(attributePaths = {"team"})
    List<Member> findAll();

    @EntityGraph(attributePaths = {"team"})
    @Query("select m from Member m")
    List<Member> findByAgeGreaterThanEqual(int age);

    Optional<Member> findByUsername(String userName);
}
