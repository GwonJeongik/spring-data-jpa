package spring.data_jpa.entity;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import spring.data_jpa.repository.MemberRepository;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("정상 회원, 팀 엔티티 매니저 이용해서 저장")
    void memberSave() {
        //given
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");

        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member(teamA, "member1", 10);
        Member member2 = new Member(teamA, "member2", 20);
        Member member3 = new Member(teamB, "member3", 30);
        Member member4 = new Member(teamB, "member4", 40);

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        // 초기화 //
        em.flush();
        em.clear();

        //when
        List<Member> members = em.createQuery("select m from Member m", Member.class)
                .getResultList();

        //then
        assertThat(members.size()).isEqualTo(4);
        assertThat(members).contains(member1, member2, member3, member4);
    }

    @Test
    @DisplayName("정상 스프링 데이터 JPA 회원 저장")
    void memberSaveBySpringDataJpa() {
        //given
        Team teamA = new Team("teamA");
        Member member = new Member(teamA, "memberA", 10);

        //when
        Member savedMember = memberRepository.save(member);
        Member findMember = memberRepository.findById(member.getId())
                .orElseThrow(NoSuchElementException::new);

        //then
        assertThat(findMember).isEqualTo(savedMember);
        assertThat(findMember.getUsername()).isEqualTo(savedMember.getUsername());
    }
}