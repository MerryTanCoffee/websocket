package kr.or.ddit.controller.noticeboard.web;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.service.INoticeService;
import kr.or.ddit.vo.DDITMemberVO;

@Controller
@RequestMapping("/notice")
public class NoticeProfileController {

	@Inject
	private INoticeService noticeService;
	
	@RequestMapping(value="/profile.do", method = RequestMethod.GET)
	public String noticeProfile 
	(HttpServletRequest req, RedirectAttributes ra, Model model) { // redirectAttributes는 일회성
		String goPage = "";
		HttpSession session = req.getSession();
		DDITMemberVO sessionMember = (DDITMemberVO) session.getAttribute("SessionInfo");
		
		if(sessionMember == null) {
			ra.addFlashAttribute("message","로그인 후 이용 가능");
			return "redirect:/notice/login.do";
		}
		
		DDITMemberVO member = noticeService.selectMember(sessionMember);

		if(member != null) {
			model.addAttribute("member", member);
			goPage = "notice/profile";
		} else {
			ra.addFlashAttribute("message","로그인 후 이용 가능");
			goPage = "redirect:/notice/login.do";
		}
		return "notice/profile";
	}
	
	@RequestMapping(value="/profileUpdate.do", method=RequestMethod.POST)
	public String noticeProfileUpdate(
				HttpServletRequest req, RedirectAttributes ra, Model model, DDITMemberVO memberVO) {
		String goPage = "";
		
		ServiceResult result = noticeService.profileUpdate(req, memberVO);
		if(result.equals(ServiceResult.OK)) {
			ra.addFlashAttribute("message","수정 완료");
			goPage = "redirect:/notice/profile.do";
		} else {
			model.addAttribute("member",memberVO);
			goPage = "notice/profile";
		}
		return goPage;
		
	}
}
