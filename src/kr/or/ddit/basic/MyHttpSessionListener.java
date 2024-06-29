package kr.or.ddit.basic;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class MyHttpSessionListener implements HttpSessionListener, HttpSessionAttributeListener {

	@Override
	public void attributeAdded(HttpSessionBindingEvent hsbe) {
		System.out.println("[MyHttpSessionListner] attributeAdded 호출됨 : "
				+hsbe.getName() + "=" + hsbe.getValue()+ " 추가됨");
	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent hsbe) {
		System.out.println("[MyHttpSessionListner] attributeRemoved 호출됨 : "
				+hsbe.getName() + " 삭제됨");
	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent hsbe) {
		System.out.println("[MyHttpSessionListner] attributeReplaced 호출됨 : "
				+hsbe.getName() + "=" + hsbe.getValue()+ " 변경됨");
	}

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		System.out.println("[MyHttpSessionListner] sessionCreated 호출됨...");
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		System.out.println("[MyHttpSessionListner] sessionDestroyed 호출됨...");
	}

}
