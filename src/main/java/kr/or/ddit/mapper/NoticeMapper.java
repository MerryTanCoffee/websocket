package kr.or.ddit.mapper;
import java.util.List;

import kr.or.ddit.vo.NoticeFileVO;
import kr.or.ddit.vo.NoticeVO;
import kr.or.ddit.vo.PaginationInfoVO;

public interface NoticeMapper {
	public int insertNotice(NoticeVO noticeVO);

	public void incrementHit(int boNo);

	public NoticeVO selectNotice(int boNo);

	public int selectNoticeCount(PaginationInfoVO<NoticeVO> pagingVO);

	public List<NoticeVO> selectNoticeList(PaginationInfoVO<NoticeVO> pagingVO);

	public int updateNotice(NoticeVO notice);

	public int deleteNotice(int boNo);

	public void insertNoticeFile(NoticeFileVO noticeFileVO);

	public NoticeFileVO selectNoticeFile(Integer integer);

	public void deleteNoticeFile(Integer integer);

	public void deleteNoticeFileByBoNo(int boNo);

	//공지사항(NOTICE) + 공지사항 첨부파일(NOTICEFILE) 목록 
	//<select id="selectListNotice" resultMap="noticeMap2">
	public List<NoticeVO> selectListNotice();

	// 파일 다운로드
	public NoticeFileVO noticeDownload(int fileNo);

	public void incrementNoticeDowncount(int fileNo);

}


