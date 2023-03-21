package kr.or.ddit.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.controller.noticeboard.web.TelegramSendController;
import kr.or.ddit.mapper.LoginMapper;
import kr.or.ddit.mapper.NoticeMapper;
import kr.or.ddit.mapper.ProfileMapper;
import kr.or.ddit.service.INoticeService;
import kr.or.ddit.vo.NoticeFileVO;
import kr.or.ddit.vo.NoticeVO;
import kr.or.ddit.vo.PaginationInfoVO;
import kr.or.ddit.vo.DDITMemberVO;

@Service
public class NoticeServiceImpl implements INoticeService{

	@Autowired
	private NoticeMapper noticeMapper;
	
	@Autowired
	private LoginMapper loginMapper;

	@Autowired
	private ProfileMapper profileMapper;
	
	TelegramSendController tst = new TelegramSendController();
	
	@Override
	public ServiceResult insertNotice(HttpServletRequest req, NoticeVO noticeVO) {
		ServiceResult result = null;
		int status = noticeMapper.insertNotice(noticeVO);
		if(status > 0) { // 성공\
			
		// 넘겨받은 데이터 중 파일 데이터들을 등록 처리
		List<NoticeFileVO> noticeFileList = noticeVO.getNoticeFileList();
		try {
			processNoticeFile(noticeFileList, noticeVO.getBoNo(), req);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
			try {
				tst.sendGet("지선", noticeVO.getBoTitle());
			} catch (Exception e) {
				e.printStackTrace();
			}
			result = ServiceResult.OK;
		} else { // 실패
			result = ServiceResult.FAILED;
		}
		return result;
	}

	@Override
	public NoticeVO selectNotice(int boNo) {
		
		noticeMapper.incrementHit(boNo);
		
		return noticeMapper.selectNotice(boNo);
	}

	@Override
	public int selectNoticeCount(PaginationInfoVO<NoticeVO> pagingVO) {
	
		return noticeMapper.selectNoticeCount(pagingVO);
	}

	@Override
	public List<NoticeVO> selectNoticeList(PaginationInfoVO<NoticeVO> pagingVO) {

		return noticeMapper.selectNoticeList(pagingVO);
	}

	@Override
	public ServiceResult updateNotice(HttpServletRequest req, NoticeVO notice) {
		
		ServiceResult result = null;
		int cnt = noticeMapper.updateNotice(notice);
		if(cnt > 0) {
			List<NoticeFileVO> noticeFileList =notice.getNoticeFileList();
			try {
				processNoticeFile(noticeFileList, notice.getBoNo(), req);
				notice.getDelNoticeNo();
				Integer[] delNoticeNo = notice.getDelNoticeNo();
				if(delNoticeNo != null) {
					for(int i = 0; i <delNoticeNo.length; i++) {
						NoticeFileVO noticeFileVO = noticeMapper.selectNoticeFile(delNoticeNo[i]);
						noticeMapper.deleteNoticeFile(delNoticeNo[i]);
						File file = new File(noticeFileVO.getFileSavePath());
						file.delete();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			result = ServiceResult.OK;
		} else {
			result = ServiceResult.FAILED;
		}
		return result;
	}

	@Override
	public ServiceResult deleteNotice(HttpServletRequest req, int boNo) {
		
		ServiceResult result = null;
		NoticeVO noticeVO = noticeMapper.selectNotice(boNo);
		noticeMapper.deleteNoticeFileByBoNo(boNo);
		int cnt = noticeMapper.deleteNotice(boNo);
		if(cnt > 0) {
			List<NoticeFileVO> noticeFileList = noticeVO.getNoticeFileList();
			
			try {
				if(noticeFileList != null) {
					String[] filePath = noticeFileList.get(0).getFileSavePath().split("/");
					int cutNum = noticeFileList.get(0).getFileSavePath().lastIndexOf(filePath[filePath.length-1]);
					String path = noticeFileList.get(0).getFileSavePath().substring(0,cutNum);
					deleteFolder(req,path);
				}
				
			} catch (Exception e ){
				e.printStackTrace();
			}
			result = ServiceResult.OK;
		} else {
			result = ServiceResult.FAILED;
		}
		return result;
	}

	private void deleteFolder(HttpServletRequest req, String path) {
		File folder = new File(path);
		
		try {
			
			if(folder.exists()) {
				File[] folderList = folder.listFiles();
				
				for(int i = 0; i < folderList.length; i++) { // 폴더 안에 있는 파일이 파일 일 때
					if(folderList[i].isFile()) {
						folderList[i].delete(); 
					} else { // 폴더 안에 있는 파일 이 폴더 일때 재귀함수 호출(폴더 안으로 들어가기)
						deleteFolder(req, folderList[i].getPath());
					}
				}
				folder.delete(); // 폴더 삭제
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public ServiceResult idCheck(String memId) {
		ServiceResult result = null;
		DDITMemberVO member = loginMapper.idCheck(memId);
	
		if(member != null) {
			result = ServiceResult.EXIST;
		} else {
			result = ServiceResult.NOTEXIST;
		}
		
		return result;
	}

	@Override
	public ServiceResult signup(HttpServletRequest req, DDITMemberVO memberVO) {
		
		ServiceResult result = null;
		
		String uploadPath = req.getServletContext().getRealPath("/resources/profile");
		File file = new File(uploadPath);
		if(!file.exists()) {
			file.mkdirs();
		}
		
		
		String profileImg = "";
		try {
			MultipartFile profileImgFile = memberVO.getImgFile();
			if(profileImgFile.getOriginalFilename() != null
					&& !profileImgFile.getOriginalFilename().equals("")) {
				String fileName = UUID.randomUUID().toString();
				fileName += "_" + profileImgFile.getOriginalFilename();
				uploadPath += "/" + fileName;
				profileImgFile.transferTo(new File(uploadPath));
				profileImg = "/resources/profile/" +fileName;
			}
			 memberVO.setMemProfileImg(profileImg);
		} catch(IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int status = loginMapper.signup(memberVO);
	
		if(status > 0) {
			result = ServiceResult.OK;
		} else {
			result = ServiceResult.FAILED;
		}
		
		return result;
	}


	@Override
	public DDITMemberVO loginCheck(DDITMemberVO member) {
		return loginMapper.loginCheck(member);
	}

	@Override
	public String findId(DDITMemberVO member) {
		return loginMapper.findId(member);
	}

	@Override
	public String findPw(DDITMemberVO member) {
		return loginMapper.findPw(member);
	}
	
	private void processNoticeFile(List<NoticeFileVO> noticeFileList, int boNo, HttpServletRequest req) throws Exception{
		if(noticeFileList != null && noticeFileList.size() > 0) { // 파일데이터가 무조건 있음
			for(NoticeFileVO noticeFileVO : noticeFileList) {
				String savedName = UUID.randomUUID().toString();
				
				// UUID를 활용해 만든 파일명_원본파일명(원본파일명에서 공백이 있는 경우 공백을 전부 _로 대체)
				savedName = savedName + "_" + noticeFileVO.getFileName().replaceAll(" ", "_");
				 String endFilename = noticeFileVO.getFileName().split("\\.")[1];
				 String saveLocate = req.getServletContext().getRealPath("/resources/notice/"+boNo);
				 
				 File file = new File(saveLocate);
				 if(!file.exists()) {
					 file.mkdirs();
				 }
				saveLocate += "/" + savedName;
				File saveFile = new File(saveLocate);
				noticeFileVO.setBoNo(boNo);
				noticeFileVO.setFileSavePath(saveLocate);
				noticeMapper.insertNoticeFile(noticeFileVO);
				
				// 방법1
				noticeFileVO.getItem().transferTo(saveFile); // 파일 복사
			
				// 방법2
			//	InputStream is = noticeFileVO.getItem().getInputStream();
			//	FileUtils.copyInputStreamToFile(is, saveFile);
			//	is.close();
			}
		}
		
	}
	@Override
	public List<NoticeVO> selectListNotice()  {
		return this.noticeMapper.selectListNotice();
		
	}

	@Override
	public NoticeFileVO noticeDownload(int fileNo) {
		NoticeFileVO noticeFileVO = noticeMapper.noticeDownload(fileNo);
		
		if(noticeFileVO == null) {
			throw new RuntimeException();
		}
		noticeMapper.incrementNoticeDowncount(fileNo);
		
		return noticeFileVO;
	}

	@Override
	public DDITMemberVO selectMember(DDITMemberVO sessionMember) {
		return profileMapper.selectMember(sessionMember);
	}

	@Override
	public ServiceResult profileUpdate(HttpServletRequest req, DDITMemberVO memberVO) {
		ServiceResult result = null;
		
		String uploadPath = req.getServletContext().getRealPath("/resources/profile");
		File file = new File(uploadPath);
		if(!file.exists()) {
			file.mkdirs();
		}
		
		
		String profileImg = "";
		try {
			MultipartFile profileImgFile = memberVO.getImgFile();
			if(profileImgFile.getOriginalFilename() != null
					&& !profileImgFile.getOriginalFilename().equals("")) {
				String fileName = UUID.randomUUID().toString();
				fileName += "_" + profileImgFile.getOriginalFilename();
				uploadPath += "/" + fileName;
				profileImgFile.transferTo(new File(uploadPath));
				profileImg = "/resources/profile/" +fileName;
			}
			 memberVO.setMemProfileImg(profileImg);
		} catch(IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int status = profileMapper.profileUpdate(memberVO);
	
		if(status > 0) {
			result = ServiceResult.OK;
		} else {
			result = ServiceResult.FAILED;
		}
		
		return result;
	}
}
