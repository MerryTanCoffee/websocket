package kr.or.ddit.controller.noticeboard.web;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.service.INoticeService;
import kr.or.ddit.vo.DDITMemberVO;


@Controller
@RequestMapping("/notice")
public class NoticeLoginController {

	@Autowired
	private INoticeService noticeService;
	
	@RequestMapping(value ="/login.do", method = RequestMethod.GET)
	public String noticeLogin(
			@RequestParam(name="stat", required=false, defaultValue ="0") int stat,
			Model model) {
		if(stat == 1) {
			model.addAttribute("message","회원가입 완료");
		}
		
		model.addAttribute("bodyText","login-page");
		return "conn/login";
	}
	
	@RequestMapping(value ="/loginCheck.do", method = RequestMethod.POST)
	public String loginCheck(
			HttpServletRequest req,
			DDITMemberVO member, Model model) {
		
		// 추가
		
		String goPage = "";
		
		Map<String, String> errors = new HashMap<String, String>();
		if(StringUtils.isBlank(member.getMemId())) {
			errors.put("memId", "아이디 입력 필");
		}
		if(StringUtils.isBlank(member.getMemPw())) {
			errors.put("memPw", "비밀번호 입력 필");
		}
		if(errors.size() > 0) {
			model.addAttribute("errors", errors);
			model.addAttribute("member", member);
			model.addAttribute("bodyText", "login-page");
			goPage = "conn/login"; 
		} else {
			DDITMemberVO memberVO = noticeService.loginCheck(member);
			if(memberVO != null) { // 로그인 성공
				HttpSession session = req.getSession();
				session.setAttribute("SessionInfo", memberVO);
				goPage = "redirect:/notice/list.do";
			} else {
				model.addAttribute("message","서버에러");
				model.addAttribute("member", member);
				model.addAttribute("bodyText", "login-page");
				goPage = "conn/login"; 
				
			}
		}
		return goPage;
	}

	@RequestMapping(value ="/register.do", method = RequestMethod.GET)
	public String noticeRegister(Model model) {
		model.addAttribute("bodyText","register-page");
		return "conn/register";
	}
	
	
	@RequestMapping(value ="/idCheck.do", method = RequestMethod.POST)
	public ResponseEntity<ServiceResult> idCheck(String memId){ // 아작스의 키값
		ServiceResult result = noticeService.idCheck(memId);
		return new ResponseEntity<ServiceResult>(result, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/signup.do", method = RequestMethod.POST)
	public String signup(HttpServletRequest req, DDITMemberVO memberVO, Model model) {
		
		String goPage= "";
		
		Map<String, String> errors = new HashMap<String, String>();
		if(StringUtils.isBlank(memberVO.getMemId())) {
			errors.put("memId", "아이디 입력 필");
		}
		if(StringUtils.isBlank(memberVO.getMemPw())) {
			errors.put("memPw", "비밀번호 입력 필");
		}
		if(StringUtils.isBlank(memberVO.getMemName())) {
			errors.put("memName", "이름 입력 필");
		}
		
		if(errors.size() > 0 ) {
			model.addAttribute("bodyText","register-page");
			model.addAttribute("errors",errors);
			model.addAttribute("member",memberVO);
			goPage = "conn/register";
		} else {
			ServiceResult result = noticeService.signup(req, memberVO);
			if(result.equals(ServiceResult.OK)) {
				goPage = "redirect:/notice/login.do?stat=1";
			
			} else {
		
				model.addAttribute("bodyText","register-page");
				model.addAttribute("message","서버에러 다시 시도해주세요");
				model.addAttribute("member",memberVO);
				goPage = "conn/register";
			}
		}
		return goPage;
	}	
	

	@RequestMapping(value = "/forget.do", method = RequestMethod.GET)
	public String forget(Model model) {
		model.addAttribute("bodyText", "login-page");
		return "conn/forget";
	}
	
	@RequestMapping(value = "/forgetId.do", method = RequestMethod.POST)
	public ResponseEntity<String> forgetId(DDITMemberVO member) {
		String memId = noticeService.findId(member);
		return new ResponseEntity<String>(memId, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/forgetPw.do", method = RequestMethod.POST)
	public ResponseEntity<String> forgetPw(DDITMemberVO member) {
		String memPw = noticeService.findPw(member);
		System.out.println("memPw : " + memPw);
		return new ResponseEntity<String>(memPw, HttpStatus.OK);
	}
}
