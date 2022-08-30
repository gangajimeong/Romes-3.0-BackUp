package com.company.ROMES.WebSocket;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class WebSocketServer extends TextWebSocketHandler {
	private static final List<WebSocketSession> sessionList = new ArrayList<WebSocketSession>();
	private static final int NEW_NOTIFICATION = 0;
	private static final int NEW_WORKORER = 1;
	private static final Map<String, String> users = new HashMap<>();

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		super.afterConnectionEstablished(session);
		sessionList.add(session);
		String username = "Unknown";
		if (session.getPrincipal() != null)
			username = session.getPrincipal().getName();
		users.put(session.getId(), username);
		System.out.println("[WebSocket/" + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
				+ "] New Connection : " + username+"/"+session.getLocalAddress().getAddress().getHostAddress());
		
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		// TODO Auto-generated method stub
		super.handleTextMessage(session, message);
		System.out.println("[WebSocket/" + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + "] "
				+ users.get(session.getId()) + ":" + message.getPayload());
		if (message.getPayload().contains("SET_USERNAME")) {
			
			String username = message.getPayload().substring(message.getPayload().indexOf(":") + 1);
			users.put(session.getId(), username);
			session.sendMessage(new TextMessage("[WebSocket/" + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + "] "
					+ users.get(session.getId()) + ":Change Session Name -> " + username));

		} else {
			for (WebSocketSession s : sessionList) {
				if (session.getId() != s.getId())
					s.sendMessage(new TextMessage(message.getPayload()));
			}
		}
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		// TODO Auto-generated method stub
		super.afterConnectionClosed(session, status);
		System.out.println("[WebSocket/" + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
				+ "] Disconnection : " + users.get(session.getId()));
		sessionList.remove(session);
	}
}
