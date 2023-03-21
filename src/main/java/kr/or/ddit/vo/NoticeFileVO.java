package kr.or.ddit.vo;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class NoticeFileVO {

	private MultipartFile item;
	private int boNo;
	private int fileNo;
	private String fileName;
	private long fileSize;
	private String fileFancysize;
	private String fileMime;
	private String fileSavePath;
	private int fileDowncount;
	
	public NoticeFileVO() {}
	public NoticeFileVO(MultipartFile item) {
		this.item = item;
		fileName = item.getOriginalFilename();
		fileSize = item.getSize();
		fileMime = item.getContentType();
		fileFancysize = FileUtils.byteCountToDisplaySize(fileSize);
		
	}
}
