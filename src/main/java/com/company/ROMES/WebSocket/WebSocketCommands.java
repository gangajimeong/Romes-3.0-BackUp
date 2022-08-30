package com.company.ROMES.WebSocket;

import org.json.simple.JSONObject;

public class WebSocketCommands {
	// JSON primise
	//type: message type ex) {type:NEW_WORKORDER}
	// 
	//notification type
	public final int NEW_NOTIFICATION = 0; // 공지사항
	public final int NEW_WORKORDER = 1; // 작업지시
	public final int NEW_CHECK_DOCUMENT =2; // 결제 문서
	public final int ACCEPT_DESIGN = 3; // 디자인 승인(고객사)
	public final int NEW_RECEIVED_ORDER = 4;// 수주 등록(디자인팀 표시)
	//target list
	public final int TEAM_DESIGN= -1; // 디자인부
	public final int TEAM_MANAGEMENT = -2; // 경영부
	public final int TEAM_PRODUCTION = -3; // 제작부 
	public final int TEAM_CONSTRUCTION = -4; // 시공사 TEST
}
