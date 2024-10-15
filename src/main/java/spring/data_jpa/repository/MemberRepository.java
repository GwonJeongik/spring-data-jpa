package spring.data_jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.data_jpa.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
}
