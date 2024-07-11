package KKSC.page.domain.notice.service.impl;

import KKSC.page.domain.notice.dto.NoticeBoardDetailResponse;
import KKSC.page.domain.notice.dto.NoticeBoardListResponse;
import KKSC.page.domain.notice.dto.NoticeBoardRequest;
import KKSC.page.domain.notice.entity.NoticeBoard;
import KKSC.page.domain.notice.repository.NoticeBoardRepository;
import KKSC.page.domain.notice.service.NoticeBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class NoticeBoardServiceImpl implements NoticeBoardService {

    private final NoticeBoardRepository noticeBoardRepository;

    /*
     * noticeBoardRequest 를 통해 noticeBoard 객체 생성
     * noticeBoardRepository 에 저장
     */
    @Override
    public void create(NoticeBoardRequest noticeBoardRequest) {
        NoticeBoard noticeBoard = noticeBoardRequest.toEntity();

        noticeBoardRepository.save(noticeBoard);
    }

    /*
     * noticeBoardRepository에서 noticeBoardId로 기존 수정할 게시글 가져오기
     * noticeBoardRequest 내용으로 수정
     * 성공적으로 수정되었다면 NoticeBoardDetailResponse에 수정한 내용 담아서 반환
     */
    @Override
    public void update(Long noticeBoardId, NoticeBoardRequest noticeBoardRequest) {
        NoticeBoard noticeBoard = noticeBoardRepository.findById(noticeBoardId).orElseThrow();
        noticeBoard.update(noticeBoardRequest);

        // NoticeBoard -> NoticeBoardDetailResponse 후 반환
    }

    /*
     * 삭제할 noticeBoardId를 repository 에서 찾아옴
     * del_YN: 0 -> 1
     */
    @Override
    public void delete(Long noticeBoardId) {
        NoticeBoard noticeBoard = noticeBoardRepository.findById(noticeBoardId).orElseThrow();
        noticeBoard.delete();
    }

    @Override
    public List<NoticeBoardListResponse> getBoardList() {
//        List<NoticeBoard> noticeBoards = noticeBoardRepository.findAll();
//        List<NoticeBoardListResponse> listResponses = new ArrayList<>();
//        for (NoticeBoard noticeBoard : noticeBoards) {
//            listResponses.add(new NoticeBoardListResponse(noticeBoard));
//        }
//        return listResponses;

        return null;
    }

    /*
     * noticeBoardId의 글을 repo에서 가져와서 response에 담아서 반환
     * 성공적으로 조회되었다면 200 OK
     */
    @Override
    public NoticeBoardDetailResponse getBoardDetail(Long noticeBoardId) {
//        NoticeBoard noticeBoard = noticeBoardRepository.findById(noticeBoardId).orElseThrow(null);
//        NoticeBoardDetailResponse detailResponse = new NoticeBoardDetailResponse(noticeBoard);
//        return detailResponse;

        return null;
    }

    /*
     * String, keyword: null 이면 title
     * keyword type에 따라 어디서 %like% 쓸지
     */
    @Override
    public List<NoticeBoardListResponse> searchBoardList(String query, String keyword) {
        return null;
    }
}
