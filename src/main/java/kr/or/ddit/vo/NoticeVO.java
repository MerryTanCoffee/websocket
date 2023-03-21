package kr.or.ddit.vo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import org.apache.commons.lang3.StringUtils;
import lombok.Data;

@Data
public class NoticeVO {
	private int boNo;
	private int rnum;
	private String boTitle;
	private String boContent;
	private String boWriter;
	private String boDate;
	// notice : noticefile = 1 : N
	private String boHit;
	
	
	// 파일데이터가 세팅되는 공간
	// 서버의 용량 관리를 위해 삭제 번호를 부여하여 비동기로 삭제 동작을 하면 배열에 그 값들을 모아 서버로 보내고 싶어서 만든 변수
	private Integer[] delNoticeNo;
	
	private MultipartFile[] boFile;
	
	private List<NoticeFileVO> noticeFileList;
	
	
	// 파일데이터가 세팅되는 공간
	public void setBoFile(MultipartFile[] boFile) {
		this.boFile = boFile;
		if(boFile != null) {
			List<NoticeFileVO> noticeFileList = new ArrayList<NoticeFileVO>();
			for(MultipartFile item : boFile) {
				if(StringUtils.isBlank(item.getOriginalFilename())) {
					continue;
				}
				
				
				
				NoticeFileVO noticeFileVO = new NoticeFileVO(item);
				noticeFileList.add(noticeFileVO);
			}
			this.noticeFileList = noticeFileList;
		}
	}
}
