package kr.or.ddit.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.vo.NoticeFileVO;
import kr.or.ddit.vo.NoticeVO;
import kr.or.ddit.vo.PaginationInfoVO;
import kr.or.ddit.vo.DDITMemberVO;

public interface INoticeService {

	public NoticeVO selectNotice(int boNo);
	
	public int selectNoticeCount(PaginationInfoVO<NoticeVO> pagingVO);
	
	public List<NoticeVO> selectNoticeList(PaginationInfoVO<NoticeVO> pagingVO);

	public ServiceResult insertNotice(HttpServletRequest req, NoticeVO noticeVO);

	public ServiceResult updateNotice(HttpServletRequest req, NoticeVO notice);

	public ServiceResult deleteNotice(HttpServletRequest req,int boNo);

	public ServiceResult idCheck(String memId);

	public ServiceResult signup(HttpServletRequest req, DDITMemberVO memberVO);

	public DDITMemberVO loginCheck(DDITMemberVO member);

	public String findId(DDITMemberVO member);

	public String findPw(DDITMemberVO member);

	// 공지사항(Notice) + 공지사항 첨부파일(NoticeFile) 목록
	public List<NoticeVO> selectListNotice();

	// 파일 다운로드
	public NoticeFileVO noticeDownload(int fileNo);

	public DDITMemberVO selectMember(DDITMemberVO sessionMember);

	public ServiceResult profileUpdate(HttpServletRequest req, DDITMemberVO memberVO);


	
}
