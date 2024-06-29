package kr.or.ddit.basic;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class T05ServletSessionTest extends HttpServlet{
/*
	세션(HttpSession) 객체에 대하여...
	
		- 세션 객체를 이용하여 사용자(웹브라우저)별로 구분하여 정보를 관리할 수 있다.(세션 ID 이용)
		- 쿠키를 사용 할 때보다 보안이 향상된다. (정보가 서버에 저장되기 때문에...)
		
		- 세션객체를 가져오는(생성하는) 방법
		HttpSession session = req.getSession(boolean값);
		boolean값 : true인 경우 => 세션객체가 존재하지 않으면 새로 생성하여 반환한다.
					false인 경우 => 세션객체가 존재하지 않으면 null을 리턴한다.
		- 세션객체를 삭제처리 하는 방법
		1. invalidate() 메서드 이용하기 ///인스턴스메소드이다, 무효화시킨다
		2. setMaxInactiveInterval(int interval) 메서드 이용하기 (초단위) 
		///최대 비활성화된 간격을 설정해주는 setter 메서드이다, 이 기간동안 접근하지 않으면 세션이 무효화된다.
		3. web.xml 파일에 <sesson-config> 설정 이용하기 (분단위)

 		
*/
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 세션객체를 가져오는데 없으면 새로 생성한다.
		HttpSession httpSession = req.getSession(true);
		
		// 새션 생성시간 가져오기
		Date createTime = new Date(httpSession.getCreationTime());
		///import 할때  util이 아닌 sql로 하면 날짜 형식이 다르게 나온다
		
		// 세션에 마지막 접근한 시간 가져오기
		Date lastAccessTime = new Date(httpSession.getLastAccessedTime());
		
		String title = "재방문을 환영합니다.";
		int visitCnt = 0; //방문횟수
		String userId = "sem"; //사용자 ID
		
		if(httpSession.isNew()) { //새로 만들어진 세션 객체인지 확인...
			title = "처음 방문을 환영합니다.";
			httpSession.setAttribute("userId", userId);
		}else {
			visitCnt = (Integer) httpSession.getAttribute("visitCnt");
			visitCnt++;
			userId = (String) httpSession.getAttribute("userId");
		}
		httpSession.setAttribute("visitCnt", visitCnt);
		
		///세션 삭제하는 첫번째 방법
//		httpSession.invalidate(); //세션 무효화 처리하기...
		
		///세션 삭제하는 두번째 방법
//		httpSession.setMaxInactiveInterval(30); //초단위
		
		
		
		////////////////////////////////////////////////////////////////////////
		
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html");
		
		PrintWriter out = resp.getWriter();
		
		out.print("<DOCTYPE html><html><head><title>"+title+"</title></head>"
				+ "<body><h1 align=\"center\">"+title+"</h1>"
				+ "<h2 align=\"center\">세션정보</h2>"
				+ "<table border=\"1\" align=\"center\">"
				+ "<tr bgcolor=\"orange\"><th>구분</th><th>값</th></tr>"
				+ "<tr><td>세션ID</td><td>"+httpSession.getId()+"</td></tr>"
				+ "<tr><td>생성시간</td><td>"+createTime+"</td></tr>"
				+ "<tr><td>마지막 접근시간</td><td>"+lastAccessTime+"</td></tr>"
				+ "<tr><td>사용자ID</td><td>"+userId+"</td></tr>"
				+ "<tr><td>방문횟수</td><td>"+visitCnt+"</td></tr>"
						+ "</table></body></html>");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	
}
