package KKSC.page.domain.notice.service.impl;

import KKSC.page.domain.notice.dto.NoticeBoardDetailResponse;
import KKSC.page.domain.notice.dto.NoticeBoardListResponse;
import KKSC.page.domain.notice.dto.NoticeBoardRequest;
import KKSC.page.domain.notice.repository.NoticeBoardRepository;
import KKSC.page.domain.notice.service.NoticeBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeBoardServiceImpl implements NoticeBoardService {

    private final NoticeBoardRepository noticeBoardRepository;

    @Override
    public void create(NoticeBoardRequest noticeBoardRequest) {
        /*
         * 사용자에게 공지사항 게시판을 작성할 권한이 있는지 확인
         * noticeBoardRequest 를 통해 noticeBoard 객체 생성
         * noticeBoardRepository 에 저장
         * 성공적으로 저장되었다면 200 OK
         */
        noticeBoardRepository.save(noticeBoardRequest.toEntity());
    }

    @Override
    public void update(Long noticeBoardId, NoticeBoardRequest noticeBoardRequest) {
        /*
         * noticeBoardRepository에서 noticeBoardId로 기존 수정할 게시글 가져오기
         * noticeBoardRequest 내용으로 수정
         * 성공적으로 수정되었다면 NoticeBoardResponse에 수정한 내용 담아서 반환
         */

        /* 1. 권한 확인
           2. title, content, updateAt 업데이트
           3. 변경사항 저장 save() */
        NoticeBoard noticeBoard = noticeBoardRepository.findById(noticeBoardId).orElseThrow();
        noticeBoard.update(noticeBoardRequest);
        noticeBoardRepository.save(noticeBoard);
    }

    @Override
    public void delete(Long noticeBoardId) {
        /*
         * 사용자에게 삭제할 권한이 있는지 확인
         * 삭제할 noticeBoardId를 repository 에서 찾아옴
         * del_YN의 여부를 바꾼 뒤 200 OK 반환
         */
        NoticeBoard noticeBoard = noticeBoardRepository.findById(noticeBoardId).orElseThrow();
        noticeBoard.setDelYN(noticeBoard.getId());
        noticeBoardRepository.delete(noticeBoard);
    }

    @Override
    public List<NoticeBoardListResponse> getBoardList() {
        /*
         * repository Native Query 작성
         */
//        return List.of();
        List<NoticeBoard> noticeBoards = noticeBoardRepository.findAll();
        List<NoticeBoardListResponse> listResponses = new ArrayList<>();
        for (NoticeBoard noticeBoard : noticeBoards) {
            listResponses.add(new NoticeBoardListResponse(noticeBoard));
        }
        return listResponses;
    }

    @Override
    public NoticeBoardDetailResponse getBoardDetail(Long noticeBoardId) {
        /*
         * repository Native Query 작성
         * noticeBoardId의 글을 repo에서 가져와서 response에 담아서 반환
         * 성공적으로 조회되었다면 200 OK
         */
        NoticeBoard noticeBoard = noticeBoardRepository.findById(noticeBoardId).orElseThrow(null);
        NoticeBoardDetailResponse detailResponse = new NoticeBoardDetailResponse(noticeBoard);
        return detailResponse;
    }

    @Override
    public List<NoticeBoardListResponse> searchBoardList(String cmd) {
        /*
         * repository Native Query 작성
         * cmd를 기준으로 쿼리 작성
         */
        return null;
    }
}
