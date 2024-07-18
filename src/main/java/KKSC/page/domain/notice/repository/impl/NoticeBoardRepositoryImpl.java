package KKSC.page.domain.notice.repository.impl;

import KKSC.page.domain.notice.dto.NoticeBoardListResponse;
import KKSC.page.domain.notice.entity.Keyword;
import KKSC.page.domain.notice.entity.NoticeBoard;
import KKSC.page.domain.notice.repository.NoticeBoardRepositoryCustom;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static KKSC.page.domain.notice.entity.QNoticeBoard.noticeBoard;

@Slf4j
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

    @Override
    public Page<NoticeBoardListResponse> loadNoticeBoardList(Pageable pageable) {
        List<NoticeBoardListResponse> result = queryFactory
                .select(Projections.constructor(NoticeBoardListResponse.class,
                        noticeBoard.title,
                        noticeBoard.memberName.as("createdBy"),
                        noticeBoard.fileYN,
                        noticeBoard.view,
                        noticeBoard.delYN,
                        noticeBoard.createdAt
                ))
                .from(noticeBoard)
                .where(noticeBoard.delYN.eq(0L))
                .orderBy(noticeBoard.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long totalCount = result.size();

        return new PageImpl<>(result, pageable, totalCount);
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
