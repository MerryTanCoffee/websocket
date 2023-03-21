package kr.or.ddit.mapper;


import kr.or.ddit.vo.DDITMemberVO;

public interface LoginMapper {

	public DDITMemberVO idCheck(String memId);

	public int signup(DDITMemberVO memberVO);
	
	public DDITMemberVO loginCheck(DDITMemberVO member);

	public String findId(DDITMemberVO member);

	public String findPw(DDITMemberVO member);

}
