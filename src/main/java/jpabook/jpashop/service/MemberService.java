package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // 읽기 전용 트랜잭션이기 때문에 리소스 최적화
@RequiredArgsConstructor // final 필드의 생성자를 자동으로 만들어줌
public class MemberService {

    private final MemberRepository memberRepository; // compile 시점에 세팅 안하면 오류 발생.

    /**
     * 회원 가입
     * @param member 회원정보
     * @return 회원ID
     */
    @Transactional // 트랜잭션 안에서 데이터 변경이 있는 경우 사용
    public Long join(Member member) {
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    /**
     * 중복 회원 검사
     * @param member 회원정보
     */
    private void validateDuplicateMember(Member member) {

        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 회원 전체 조회
     * @return
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
