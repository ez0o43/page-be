package KKSC.page.domain.notice.repository.impl;

import KKSC.page.domain.notice.entity.*;
import KKSC.page.domain.notice.repository.NoticeBoardRepositoryCustom;
import com.querydsl.core.types.dsl.BooleanExpression;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static KKSC.page.domain.notice.entity.QNoticeBoard.noticeBoard;

@Repository
@RequiredArgsConstructor
public class NoticeBoardRepositoryImpl implements NoticeBoardRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<NoticeBoard> searchBoardList(Keyword keyword, String query) {
        return queryFactory
                .selectFrom(noticeBoard)
                .where(
                        noticeBoard.delYN.eq(0L),
                        getKeywordQuery(keyword, query)
                )
                .fetch();
    }

    private BooleanExpression getKeywordQuery(Keyword keyword, String query) {
        return switch (keyword) {
            case TITLE -> noticeBoard.title.contains(query);
            case CONTENT -> noticeBoard.content.contains(query);
            case TITLE_CONTENT -> (noticeBoard.title.contains(query)).or(noticeBoard.content.contains(query));
            case CREATED_BY -> noticeBoard.memberName.contains(query);
        };
    }
}
