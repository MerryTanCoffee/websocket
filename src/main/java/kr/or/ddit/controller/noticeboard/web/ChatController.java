package kr.or.ddit.controller.noticeboard.web;


import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.or.ddit.service.INoticeService;
import kr.or.ddit.vo.DDITMemberVO;
import lombok.extern.slf4j.Slf4j;



@Slf4j
@Controller
@RequestMapping("/notice")
public class ChatController {
	
	@Inject
	private INoticeService noticeService;
	
	@RequestMapping(value = "/chat.do", method = RequestMethod.GET)
	public String chatting(HttpServletRequest req, Model model,DDITMemberVO member) throws Exception{
		DDITMemberVO memberVO = noticeService.loginCheck(member);
		if(memberVO != null) { // 로그인 성공
		HttpSession session = req.getSession();
		session.setAttribute("SessionInfo", memberVO);
		model.addAttribute("member",memberVO);
		}
		return "notice/chat";
		
	}
}
