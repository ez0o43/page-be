package KKSC.page.domain.notice.service.impl;

import KKSC.page.domain.notice.dto.NoticeBoardDetailResponse;
import KKSC.page.domain.notice.dto.NoticeBoardListResponse;
import KKSC.page.domain.notice.dto.NoticeBoardRequest;
import KKSC.page.domain.notice.dto.NoticeFileResponse;
import KKSC.page.domain.notice.entity.Keyword;
import KKSC.page.domain.notice.entity.NoticeBoard;
import KKSC.page.global.exception.ErrorCode;
import KKSC.page.domain.notice.exeption.NoticeBoardException;
import KKSC.page.domain.notice.repository.NoticeBoardRepository;
import KKSC.page.domain.notice.repository.NoticeFileRepository;
import KKSC.page.domain.notice.service.NoticeBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class NoticeBoardServiceImpl implements NoticeBoardService {

    private final NoticeBoardRepository noticeBoardRepository;
    private final NoticeFileRepository noticeFileRepository;

    /*
     * noticeBoardRequest 를 통해 noticeBoard 객체 생성
     * noticeBoardRepository 에 저장
     */
    @Override
    public Long create(NoticeBoardRequest noticeBoardRequest, String memberName) {
        NoticeBoard noticeBoard = noticeBoardRequest.toEntity(memberName);
        log.info("noticeBoard = {}", noticeBoard);

        return noticeBoardRepository.save(noticeBoard).getId();
    }

    /*
     * noticeBoardRepository에서 noticeBoardId로 기존 수정할 게시글 가져오기
     * noticeBoardRequest 내용으로 수정
     * 성공적으로 수정되었다면 NoticeBoardDetailResponse에 수정한 내용 담아서 반환
     */
    @Override
    public NoticeBoardDetailResponse update(Long noticeBoardId, NoticeBoardRequest noticeBoardRequest) {
        NoticeBoard noticeBoard = noticeBoardRepository.findById(noticeBoardId)
                .orElseThrow(() -> new NoticeBoardException(ErrorCode.NOT_FOUND_BOARD));

        List<NoticeFileResponse> noticeFileResponses = noticeFileRepository.findNoticeFilesByNoticeBoardId(noticeBoardId);
        noticeBoard.update(noticeBoardRequest);

        return NoticeBoardDetailResponse.fromEntity(noticeBoard, noticeFileResponses);
    }

    /*
     * 삭제할 noticeBoardId를 repository 에서 찾아옴
     * del_YN: 0 -> 1
     */
    @Override
    public void delete(Long noticeBoardId) {
        NoticeBoard noticeBoard = noticeBoardRepository.findById(noticeBoardId)
                .orElseThrow(() -> new NoticeBoardException(ErrorCode.NOT_FOUND_BOARD));

        if (noticeBoard.getDelYN() == 1L) {
            throw new NoticeBoardException(ErrorCode.ALREADY_DELETED);
        }
        noticeBoard.delete();
    }

    @Override
    public List<NoticeBoardListResponse> getBoardList() {
        List<NoticeBoard> noticeBoards = noticeBoardRepository.findAll();

        List<NoticeBoardListResponse> listResponses = new ArrayList<>();

        for (NoticeBoard noticeBoard : noticeBoards) {
            List<NoticeFileResponse> noticeFileResponses = noticeFileRepository.findNoticeFilesByNoticeBoardId(noticeBoard.getId());

            listResponses.add(NoticeBoardListResponse.fromEntity(noticeBoard, noticeFileResponses));
        }

        return listResponses;
    }

    /*
     * noticeBoardId의 글을 repo에서 가져와서 response에 담아서 반환
     * 성공적으로 조회되었다면 200 OK
     */
    @Override
    public NoticeBoardDetailResponse getBoardDetail(Long noticeBoardId) {
        NoticeBoard noticeBoard = noticeBoardRepository.findById(noticeBoardId)
                .orElseThrow(() -> new NoticeBoardException(ErrorCode.NOT_FOUND_BOARD));
        List<NoticeFileResponse> noticeFileResponses = noticeFileRepository.findNoticeFilesByNoticeBoardId(noticeBoardId);

        return NoticeBoardDetailResponse.fromEntity(noticeBoard, noticeFileResponses);
    }

    /*
     * String, keyword: null 이면 title
     * keyword type에 따라 어디서 %like% 쓸지
     */
    @Override
    public List<NoticeBoardListResponse> searchBoardList(Keyword keyword, String query) {
        List<NoticeBoard> noticeBoards = noticeBoardRepository.searchBoardList(keyword, query);

        List<NoticeBoardListResponse> listResponses = new ArrayList<>();

        for (NoticeBoard noticeBoard : noticeBoards) {
            List<NoticeFileResponse> noticeFileResponses = noticeFileRepository.findNoticeFilesByNoticeBoardId(noticeBoard.getId());

            listResponses.add(NoticeBoardListResponse.fromEntity(noticeBoard, noticeFileResponses));
        }
        return listResponses;
    }
}
