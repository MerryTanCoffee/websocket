package kr.or.ddit.controller.noticeboard.view;

import java.io.File;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.view.AbstractView;

public class NoticeDownloadView extends AbstractView{

	
	// 파일 다운로드의 형태는 무조건 이 형태
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String,Object> noticeFileMap = (Map<String, Object>) model.get("noticeFileMap");
	
		File saveFile = new File(noticeFileMap.get("fileSavePath").toString());
		
		String filename = noticeFileMap.get("fileName").toString();
		String agent = request.getHeader("User-Agent");
		
		// 파일 다운로드를 할 때 한글이 깨지는 경우가 있음 그 때 이걸 넣어줘야함
		if(StringUtils.containsIgnoreCase(agent, "msie")) {
			filename = URLEncoder.encode(filename,"UTF-8"); // IE, Chrome
		} else {
			filename = new String(filename.getBytes(), "ISO-8859-1");
		}
		response.setHeader("Content-Disposition", "attachment;filename=\""+filename +"\"");
		response.setHeader("Content-Length",noticeFileMap.get("fileSize").toString());
		OutputStream os = response.getOutputStream();
		FileUtils.copyFile(saveFile, os);
	}

}
