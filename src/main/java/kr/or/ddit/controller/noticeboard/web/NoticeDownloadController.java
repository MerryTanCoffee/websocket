package kr.or.ddit.controller.noticeboard.web;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import kr.or.ddit.controller.noticeboard.view.NoticeDownloadView;
import kr.or.ddit.service.INoticeService;
import kr.or.ddit.vo.NoticeFileVO;

@Controller
@RequestMapping("/notice")
public class NoticeDownloadController {

	@Inject
	private INoticeService noticeService;

	@RequestMapping("/download.do")
	   public View noticeDownload(int fileNo, Model model) {
	      NoticeFileVO noticeFileVO = noticeService.noticeDownload(fileNo);
	      Map<String,Object> noticeFileMap = new HashMap<String,Object>();
	      noticeFileMap.put("fileName", noticeFileVO.getFileName());
	      noticeFileMap.put("fileSize", noticeFileVO.getFileSize());
	      
	      // fileSavePath 경로 다르면 안됨
	      noticeFileMap.put("fileSavePath", noticeFileVO.getFileSavePath());
	      
	      model.addAttribute("noticeFileMap", noticeFileMap);
	      
	      //리턴되는 noticeDownloadView는 jsp 페이지로 존재하는 페이지 Name을 요청하는게 아니라,
	      //클래스를 요청하는 것 인데, 해당 클래스가 스프링에서 제공하는 AbstractView클래스를 상속받는 클래스인데
	      //그 클래스는 AbstractView를 상속받아renderMergedOutputModel 함수를 재정의 할 때 View로 취급할 수 있게 해준다.
	      return new NoticeDownloadView(); // 컨트롤러는 항상 페이지를 리턴한다. 여기서는 클래스지만 페이지를 리턴하는게 됨
	      
	   }
}
