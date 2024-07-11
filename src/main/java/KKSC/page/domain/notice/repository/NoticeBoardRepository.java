package KKSC.page.domain.notice.repository;

import KKSC.page.domain.notice.dto.NoticeBoardDetailResponse;
import KKSC.page.domain.notice.entity.NoticeBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NoticeBoardRepository extends JpaRepository<NoticeBoard, Long> {

    @Query("select new KKSC.page.domain.notice.dto.NoticeBoardDetailResponse(" +
            "nb.title, " +
            "nb.content, " +
            "m.name as createdBy, " +
            "nb.view, " +
            "nb.delYN, " +
            "nf.id as fileId, " +
            "nf.name as fileName, " +
            "nb.createdAt, " +
            "nb.modifiedAt)" +
            "from NoticeBoard nb " +
            "left join nb.noticeFiles nf " +
            "left join nb.member m " +
            "where nb.id = :id")
    NoticeBoardDetailResponse findNoticeBoardDetail(@Param("id") Long id);

    // List<NoticeBoardListResponse>

}
