package kr.or.ddit.basic;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.plaf.TextUI;

public class T04ServletCookieTest extends HttpServlet{
/*
	쿠키(Cookie)에 대하여...
	
	=> 웹서버와 브라우저는 애플리케이션을 사용하는 동안 필요한 값을 쿠키를 통해 공유하여 상태를 유지한다.
	
	1. 구성 요소?
	- 이름
	- 값
	- 유효시간(초)
	- 도메인 : ex) www.somehost.com, .somehost.com => 쿠키의 도메인이 쿠키를 생성한 서버의도메인을 벗어나면
	 브라우저는 쿠키를 저장(생성)하지 않는다. (보안위험 때문에...)
	- 경로 : 쿠키를 공유할 기준경로를 지정한다.(도메인 이후 부분으로 디렉토리 수준) 
			=> 지정하지 않으면 URL경로부분이 사용된다.
	
	2. 동작 방식
	- 쿠키 생성단계 : 생성한 쿠키를 응답메시지의 헤더에 저장하여 웹브라우저에 전송한다.
	- 쿠키 저장단계 : 웹브라우저는 응답데이터에 포함된 쿠키를 쿠키저장소에 보관한다.
	- 쿠키 전송단계 : 웹브라우저는 저장한 쿠키를 요청이 있을 때마다 웹서버에 전송한다.(삭제 되기 전까지...)
			웹서버는 브라우저가 전송한 쿠키를 사용해서 필요한 작업을 수행한다.
*/
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// 쿠키 생성 예제
//		setCookieExam(req, resp);
		///잘되는거 확인했으니 주석처리 해줬다
		
		//쿠키 정보 읽기 예제
//		readCookieExam(req, resp);
		
		// 쿠키 정보 삭제 예제
		deleteCookieExam(req, resp);
		
	}
	
	private void deleteCookieExam(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		/*
		 	사용중인 쿠키정보를 삭제하는 방법...
		 	
		 	 1. 사용중인 쿠키정보를 이용하여 쿠키객체를 삭제한다.
		 	 2. 쿠키객체의 최대지속시간을 0으로 설정한다.
		 	 3. 설정한 쿠키객체를 응답헤더에 추가하여 전송한다.
		 */
		
		Cookie[] cookies = req.getCookies();
		
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html");
		
		PrintWriter out = resp.getWriter();
		
		String title = "쿠키정보 삭제 예제";
		
		out.print("<DOCTYPE html><html><head><title>"+title+"</title></head>"
				+ "<body>");
	
		if (cookies != null) {
			out.print("<h2>"+title+"</h2>");
			
			for (Cookie cookie : cookies) {
				if(cookie.getName().equals("userId")) {
//				if(cookie.getName().equals("name")) { ///아니 userId 쿠키 안떠서 나는 name로 바꿈
					//쿠키 삭제하기...
					cookie.setMaxAge(0); ///소멸시간이 0이어서 쿠키가 삭제된다
					
					resp.addCookie(cookie);
					out.println("<p> 삭제 처리 한 쿠키 : "+cookie.getName()+"</p><br>");
				}
				
				
				out.print("<p>name : "+cookie.getName()+"</p><br>");
				out.print("<p>value : "+URLDecoder.decode(cookie.getValue(), "UTF-8")+"</p><br>");
				out.print("<hr>");
			}
		}else {
			out.print("<h2>쿠키 정보가 없습니다. </h2>");
		}
		out.print("</body></html>");
		
		
	}

	private void readCookieExam(HttpServletRequest req, HttpServletResponse resp) throws IOException {
	
		// 현재 도메인에서 사용중인 쿠키겍체 배열 가져오기
		Cookie[] cookies = req.getCookies(); ///쿠키타입의 객체를 리턴한다
		
		//////////////////////////////////////////////////////////////////////
		
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html");
		
		PrintWriter out = resp.getWriter();
		
		String title = "쿠키정보 읽기 예제";
		
		out.print("<DOCTYPE html><html><head><title>"+title+"</title></head>"
				+ "<body>");
	
		if (cookies != null) {
			out.print("<h2>"+title+"</h2>");
			
			for (Cookie cookie : cookies) {
				out.print("<p>name : "+cookie.getName()+"</p><br>");
//				out.print("<p>value : "+cookie.getValue()+"</p><br>"); 
				///원래 이코드였는데 vlaue값이 한글이라 인코딩(%%들어가 있다)여서 그것을 디코딩 해줘야지 한글로 볼 수가 있다 
				out.print("<p>value : "+URLDecoder.decode(cookie.getValue(), "UTF-8")+"</p><br>");

				out.print("<hr>");
			}
		}else {
			out.print("<h2>쿠키 정보가 없습니다. </h2>");
		}
		out.print("</body></html>");
	}

	private void setCookieExam(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		req.setCharacterEncoding("UTF-8");
		
		// 쿠키 생성하기
		Cookie userId = new Cookie("userId", req.getParameter("userId"));
		
		// 쿠키값에 한글을 사용시 인코딩 처리 해준다.
		Cookie name = new Cookie("name",
				URLEncoder.encode(req.getParameter("name"), "UTF-8"));
		
		// 쿠키 소멸 시간 설정하기(초단위) => 지정하지 않으면 웹브라우즈 종료할 때 쿠키를 함께 삭제한다.
		userId.setMaxAge(60*60&24); // 1일
		userId.setHttpOnly(true); // Javascript를 이용한 직접 접근 방지(XSS 공격대비)
		
		name.setMaxAge(60*60*48); ///2일
		
		
		//////////////////////////////////////////////////////////////////////
		
		// 응답헤더에 쿠키 추가하기
		resp.addCookie(userId);
		resp.addCookie(name);
		
		// 응답헤더에 인코딩 및 Content-Type 설정
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html");
		
		PrintWriter out = resp.getWriter();
		String title = "쿠키 설정 예제";
		
		out.print("<!DOCTYPE html><html><head><title>"+title+"</title></head>"
				+ "<body>"
				+ "<h1 align=\"center\">"+ title+"</h1>"
				+ "<ul>"
				+ "<li><b>ID : </b>"
				+ req.getParameter("userId") +"</li>"
				+ "<li><b>이름: </b>"
				+ req.getParameter("name") +"</li>"
				+ "</ul></body></html>");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	
}
