package com.bethefirst.lifeweb.repository.member;

import com.bethefirst.lifeweb.dto.member.request.MemberSearchRequirements;
import com.bethefirst.lifeweb.entity.member.Member;
import com.bethefirst.lifeweb.util.QueryDsl4RepositorySupport;
import com.querydsl.core.types.dsl.BooleanExpression;
import io.jsonwebtoken.lang.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static com.bethefirst.lifeweb.entity.member.QMember.member;

public class MemberRepositoryQueryDslImpl extends QueryDsl4RepositorySupport implements MemberRepositoryQueryDsl {
    public MemberRepositoryQueryDslImpl() {
        super(Member.class);
    }


    /** 검색 조건에 따른 회원들을 조회합니다. */
    @Override
    public Page<Member> findAllBySearchRequirements(MemberSearchRequirements requirements, Pageable pageable) {
        return applyPagination(pageable, query -> query
                .selectFrom(member)
                .where(
                        emailContains(requirements.getEmail()),
                        nicknameContains(requirements.getNickname()),
                        nameContains(requirements.getName())

                ),
                countQuery -> countQuery
                .select(member.count())
                .from(member)
                .where(
                        emailContains(requirements.getEmail()),
                        nicknameContains(requirements.getNickname()),
                        nameContains(requirements.getName())
                        )

        );
    }

    /** 회원이름 검색 조건 */
    private BooleanExpression nameContains(String name) {
        return Strings.hasText(name) == false ? null : member.name.contains(name);
    }

    /** 회원 닉네임 검색 조건 */
    private BooleanExpression nicknameContains(String nickname) {
        return Strings.hasText(nickname) == false ? null : member.nickname.contains(nickname);
    }

    /** 회원 email 검색 조건 */
    private BooleanExpression emailContains(String email) {
        return Strings.hasText(email) == false ? null : member.email.contains(email);
    }


}
