package kr.or.ddit.controller.noticeboard.web;

import java.security.Principal;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.ddit.service.INoticeService;
import kr.or.ddit.vo.NoticeVO;
import kr.or.ddit.vo.PaginationInfoVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/notice")
public class NoticeRetrieveController {

	/*
	 * 목록, 상세보기 페이지를 관여
	 */
	
	@Autowired
	private INoticeService noticeService;
	
	@RequestMapping(value="/list.do")
	public String noticeList(@RequestParam(name="page",required = false, defaultValue = "1")int currentPage, 
			@RequestParam(required = false, defaultValue = "title") String searchType, 
			@RequestParam(required = false)String searchWord,
			Model model) {
		PaginationInfoVO<NoticeVO> pagingVO = new PaginationInfoVO<NoticeVO>();
		
		// searchWord가 공백이 아니면 검색을 한 상태일 테니까
		if(StringUtils.isNotBlank(searchWord)) {
			pagingVO.setSearchType(searchType);
			pagingVO.setSearchWord(searchWord);
			model.addAttribute("searchType",searchType);
			model.addAttribute("searchWord",searchWord);
		}
		pagingVO.setCurrentPage(currentPage);
		int totalRecord = noticeService.selectNoticeCount(pagingVO);
		pagingVO.setTotalRecord(totalRecord);
		
		List<NoticeVO> noticeList = noticeService.selectNoticeList(pagingVO);
		pagingVO.setDataList(noticeList);
		
		model.addAttribute("pagingVO",pagingVO);
		
		return"notice/list";
	}
	
	@RequestMapping(value="/detail.do", method=RequestMethod.GET)
	public String noticeDetail(int boNo, Model model) {
		
		NoticeVO notice = noticeService.selectNotice(boNo);
		model.addAttribute("notice",notice);
		return"notice/detail";
	}
	
	
	// 공지사항(NOTICE) + 공지사항 첨부파일(Noticefile) 목록
	// 요청 URI : /notice/selectListNotice
	// 골뱅이 PreAuthorize("hasRole('ROLE_MEMBER')")
	// 골뱅이 PreAuthorize("hasAnyRole('ROLE_MEMBER','ROLE_ADMIN')")
	// 모든 접근 허용 반대는 denyAll 모든 접근 허용은 permitAll
	// 인가 받은 사용자만 isAuthenticated(), 익명도 가능 isAnonymous() 
	// 현재 사용자가 익명이거나 RememberMe 사용자가 아니면 true isFullyAuthenticated()
	// 기억된 사용자만 접근 가능 isRememberMe
	@PreAuthorize("hasAnyRole('ROLE_MEMBER','ROLE_ADMIN')")
	@RequestMapping(value="/selectListNotice")
	public String selectListNotice(Model model, Principal principal) {
		
		// 로그인한 사용자의 아이디(시큐리티 UserDetails 입장에서는 username
		// 우리는 memberVO userNo을 리턴)
		
		int userNo = Integer.parseInt(principal.getName());
		log.info("userNo : " + userNo);
		
		List<NoticeVO> noticeVOList = this.noticeService.selectListNotice();
		log.info("noticeVOList : " + noticeVOList);
		
		model.addAttribute("noticeVOList", noticeVOList);

		// forwarding
		// views > noticeboard > noticeList.jsp
		return "noticeboard/noticeList";
	}
}
