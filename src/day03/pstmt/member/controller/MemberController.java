package day03.pstmt.member.controller;

import java.util.List;

import day03.pstmt.member.model.dao.MemberDAO;
import day03.pstmt.member.model.vo.Member;

public class MemberController {
//	List<Member> memberList;
	MemberDAO mDao;
	
	
	public MemberController() {
		mDao = new MemberDAO();
	}
	
	//mDao.selectAll(); // 이렇게 하면 안됨
	public List<Member> printAll() {
		List<Member>mList = mDao.selectAll();
		return mList;
	}
	
	public Member printOneById(String memberId) {
		Member member = mDao.selectOneById(memberId);
		return member;
	}
	
	public void registerMember(Member member) {
		mDao.insertMember(member);
	}
	
	public void removeMember(String memberId) {
		mDao.deleteMember(memberId);
	}
	
	public void modifyMember(Member member) {
		mDao.updateMember(member);
	}

	public Member memberLogin(Member member) {
		// TODO Auto-generated method stub
		Member mOne = mDao.selectLoginInfo(member); // controller 적용 안해서 요류남
		return mOne;	// return mOne 안적어서 오류
	}
}
