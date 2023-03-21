package kr.or.ddit.controller.noticeboard.web;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.service.INoticeService;
import kr.or.ddit.vo.NoticeVO;

@Controller
@RequestMapping("/notice")
public class NoticeModifyController {
	
	@Inject
	private INoticeService noticeService;
	
	@RequestMapping(value ="/update.do", method = RequestMethod.GET)
	public String noticeUpdateView(int boNo, Model model) {
		NoticeVO notice = noticeService.selectNotice(boNo);
		model.addAttribute("notice",notice);
		model.addAttribute("status","u");
		return "notice/form";
	}
	
	@RequestMapping(value = "/update.do",method = RequestMethod.POST)
	public String noticeUpdate(
			HttpServletRequest req,
			NoticeVO notice, Model model) {
		String gopage = "";
		ServiceResult result = noticeService.updateNotice(req, notice);
		if(result.equals(ServiceResult.OK)) {
			gopage = "redirect:/notice/detail.do?boNo=" + notice.getBoNo();
			
		} else {
			model.addAttribute("message","수정실패");
			model.addAttribute("notice", notice);
			model.addAttribute("status","u");
			gopage = "notice/form";
		}
		return gopage;
	}
	
	@RequestMapping(value="/delete.do", method = RequestMethod.POST)
	public String noticeDelete(
			HttpServletRequest req,
			int boNo, Model model) {
		String gopage = "";
		ServiceResult result = noticeService.deleteNotice(req, boNo);
		if(result.equals(ServiceResult.OK)) {
			gopage = "redirect:/notice/list.do";
			
		} else {
			gopage = "notice/detail.do?boNo=" + boNo;
		}
		return gopage;
	}
}
