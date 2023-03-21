package kr.or.ddit.controller.noticeboard.web;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.service.INoticeService;
import kr.or.ddit.vo.NoticeVO;
import kr.or.ddit.vo.DDITMemberVO;

@Controller
@RequestMapping("/notice")
public class NoticeInsertController {
   
   @Inject
   private INoticeService noticeService;
   
   @GetMapping("/form.do")
   public String noticeForm() {
      return "notice/form";
   }
   
   @PostMapping("/insert.do")
   public String noticeInsert(
         HttpServletRequest req,
         NoticeVO noticeVO,
         Model model) {
      String goPage = "";
      Map<String, String> errors = new HashMap<String, String>();
      if(StringUtils.isBlank(noticeVO.getBoTitle())) {
         errors.put("boTitle","제목을 입력해주세요");
      }
      if(StringUtils.isBlank(noticeVO.getBoContent())) {
         errors.put("boContent","내용을 입력해주세요");
      }
      if(errors.size() >0) {//정상
         model.addAttribute("errors", errors);
         model.addAttribute("notice", noticeVO);
         goPage = "notice/form";
      }else {//에러
         HttpSession session = req.getSession();
         DDITMemberVO memberVO = (DDITMemberVO)session.getAttribute("SessionInfo");//자바빈즈 클래스에 있는 멤버를 이곳에 셋팅
         if(memberVO != null) {//로그인이 되어있어야만 정상 실행
            noticeVO.setBoWriter(memberVO.getMemId());
            ServiceResult result = noticeService.insertNotice(req,noticeVO);
            if(result.equals(ServiceResult.OK)) {
               goPage = "redirect:/notice/detail.do?boNo="+ noticeVO.getBoNo();
            }else {
               errors.put("message", "서버에러, 다시시도해주세여");
               model.addAttribute("errors", errors);
               goPage="notice/form";
               }
            }else {
               model.addAttribute("msg", "로그인 후에 사용 가능합니다");
               model.addAttribute("notice", noticeVO);
               goPage = "notice/form";
            
         }
      }
      return goPage;
   }
}