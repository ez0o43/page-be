package KKSC.page.domain.notice.repository;

import KKSC.page.domain.notice.entity.NoticeFIle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeFileRepository extends JpaRepository<NoticeFIle, Long> {
}
