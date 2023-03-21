package kr.or.ddit.vo;


import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class DDITMemberVO {

	private int memNo;
	private String memId;
	private String memPw;
	private String memName;
	private String memGender;
	private String memEmail;
	private String memPhone;
	private String memPostCode;
	private String memAddress1;
	private String memAddress2;
	private String memAgree;
	private String memProfileImg;
	private String memRegDate;
	private  MultipartFile imgFile;
}
