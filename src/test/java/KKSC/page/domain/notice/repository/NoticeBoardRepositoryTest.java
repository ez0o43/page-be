package KKSC.page.domain.notice.repository;

import KKSC.page.domain.member.entity.Member;
import KKSC.page.domain.member.repository.MemberRepository;
import KKSC.page.domain.notice.dto.NoticeBoardDetailResponse;
import KKSC.page.domain.notice.entity.NoticeBoard;
import KKSC.page.domain.notice.entity.NoticeFile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class NoticeBoardRepositoryTest {

    @Autowired NoticeBoardRepository noticeBoardRepository;
    @Autowired NoticeFileRepository noticeFileRepository;
    @Autowired MemberRepository memberRepository;

    @Test
    void dto_가져오기_성공() throws Exception {
        //given
        Member memberRequest = Member.builder().name("Kim").build();
        Member member = memberRepository.save(memberRequest);

        NoticeFile noticeFile = new NoticeFile();
        noticeFileRepository.save(noticeFile);

        NoticeBoard noticeBoard = new NoticeBoard(1L, member,
                null, null, "title", "content",
                1L, 0L, 0L);

        //when
        noticeBoardRepository.save(noticeBoard);
        NoticeBoardDetailResponse noticeBoardDetail = noticeBoardRepository.findNoticeBoardDetail(1L);

        //then
        assertEquals("title", noticeBoardDetail.title());
    }
}