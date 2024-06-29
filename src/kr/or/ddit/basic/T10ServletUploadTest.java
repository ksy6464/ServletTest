package kr.or.ddit.basic;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.swing.plaf.synth.SynthScrollPaneUI;

/**
 * 서블릿 3부터 지원하는 Part 인터페이스를 이용한 파일업로드 예제
 */

@MultipartConfig(
fileSizeThreshold = 1024*1024*3, //메모리 임계크기(이 크기가 넘어가면 레파지토리 위치에 임시파일로 저장됨.) 
maxFileSize = 1024*1024*40,		// 파일 1개당 최대 크기
maxRequestSize = 1024*1024*50)	// 요청 파일 최대 크기
@WebServlet("/uploadTest")
public class T10ServletUploadTest extends HttpServlet{
	
	private static final String UPLOAD_DIR = "upload_files"; ///우리가 정한 폴더 이름이다
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("==================================================================");

		
		// 여기 부분 주석처리 한 이유가 multipart가 어떤 모양인지 알고 싶었던거라서 모양 살펴봤으니 주석처리하겠다
//		///InputStreamReader은 바이트 기반이다
//		BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));
//		///한줄 한줄 읽고 싶어서 이 보조스트림 사용했다(문자열?..)
//		
//		/// 이 결과가 한줄 한줄 읽은거다
//		String readLine = "";
//		while ((readLine=br.readLine())!=null) {
//			System.out.println(readLine);
//		}
//		System.out.println("==================================================================");
		
		///필터 적용 안해서 인코딩 따로 해주겠다, 한글이 들어있을까봐...
		req.setCharacterEncoding("UTF-8");
		
		// Multipart 파싱전 파라미터 정보 가져오기
		System.out.println("Multipart 파싱 전 sender : "+ req.getParameter("sender"));
		
//		String uploadPath = req.getRealPath("/") + File.separator+UPLOAD_DIR; /// req.getRealPath("/") 이부부 밑줄쳐서 나온다 더이상쓰지 말라는 뜻, 그래서 아래 코드로 바꿔줬다
		String uploadPath = req.getServletContext().getRealPath("/") + File.separator+UPLOAD_DIR;
		/// File.separator=/(슬래쉬 역할을한다) : 구분자
		File uploadDir = new File(uploadPath);
		if(!uploadDir.exists()) {
			uploadDir.mkdir();
		}
		
		try {
			String fileName = "";
			
			for (Part part : req.getParts()) {
				System.out.println(part.getHeader("Content-Disposition"));
//				fileName = getFileName(part);
				///파일 이름 알아내려고 해드정보갖고 파싱한거다
				
				///파일 이름 알아내는 메소드
				fileName = part.getSubmittedFileName();
				
				if(fileName != null && !fileName.equals("")) {
					///fileName != null 파일이 아닌거
					/// 파일은 파일인데 선택을 안하면 "":empty String이된다
					part.write(uploadPath+File.separator+fileName);//파일 업로드...
					
					System.out.println("업로드 처리 완료....");
					System.out.println(uploadPath + File.separator+fileName);
					
					
					
				}
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		
		
	}
	
	/**
	 * Part객체 파싱하여 파일이름 추출하기
	 * @param part 파일이름 파싱할 Part 객체
	 * @return 파일명 : 파일명이 존재하지 않으면 null값 리턴함
	 */
	private String getFileName(Part part) {
		/// 콘솔창에 있는거 복붙함, 어떤 모양인지 알아야지 파싱할 수 있을거 같아서
		///form-data; name="multiPartServlet"; filename="Online_Shopping_240514_java.txt"
		
		//Content-Dispositon:form-data; name="multiPartServlet"; filename="Online_Shopping_240514_java.txt"
		for(String content : part.getHeader("Content-Disposition").split(";")) { ///파일이면 세조각 나온다
			if(content.trim().startsWith("filename")) {///content가 filename으로 시작한다면
				return content.substring(content.indexOf("=")+1).trim().replace("\"", "");///"을 없앤다는 뜻이다
				
			}
			
		}
		return null; ///filename이 존재 하지 않으면 null값이 나온다
	}

}
