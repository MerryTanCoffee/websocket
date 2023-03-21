package kr.or.ddit.controller.noticeboard.web;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/chat")
public class WebSocketChat extends TextWebSocketHandler {
	
	
	@RequestMapping(value="/home", method = RequestMethod.GET) 
	public String chatHome() {
		return "home";
	}
	
	
	private Map<String, WebSocketSession> users = new ConcurrentHashMap<>();
	private List<WebSocketSession> sessionList = new ArrayList<WebSocketSession>();
	
	
	// 연결이 되면 Map에  session.getId를 넣는다.
	// 최초 연결시 session이 고정된다.
	// 새로운 연결을 할 때마다 session을 새로 발급받는다.
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		log.info(session.getId() + "연결됨");
		users.put(session.getId(),session);
		sessionList.add(session);
	}
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		log.info(session.getId() + "로부터 메시지 수신: " + message.getPayload());
		// 모든 유저에게 발송
		
		
		for(WebSocketSession sess: sessionList) {
			sess.sendMessage(new TextMessage(message.getPayload()));
		}
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		
		sessionList.remove(session);
		
		log.info(session.getId() + " 연결 종료됨");
		users.remove(session.getId());
		
	}
	
	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		log.info(session.getId() + " 예외 발생: " + exception.getMessage());
	}

}