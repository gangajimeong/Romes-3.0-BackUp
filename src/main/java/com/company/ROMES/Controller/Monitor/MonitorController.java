package com.company.ROMES.Controller.Monitor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.company.ROMES.Dto.WorkOrderDto;
import com.company.ROMES.Services.WorkOrderService;
import com.company.ROMES.dao.WorkOrderDAO;

@Controller
public class MonitorController {
	
	private List<SseEmitter> connections = new ArrayList<SseEmitter>();
	
	@Autowired
	private SessionFactory factory;
	
	@Autowired
	private WorkOrderDAO service;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping("/monitor/home")
	public String monitorPage() {
		return "statics/Monitor";
	}
	@GetMapping(value="/monitor/sse",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public SseEmitter registSse() {
		System.out.println("add new connection");
		SseEmitter emitter = new SseEmitter(60*60*1000L);
		connections.add(emitter);
//		emitter.onCompletion(()->connections.remove(emitter));
//		emitter.onTimeout(()->connections.remove(emitter));
		return emitter;
	}
	@GetMapping("/monitor/refresh")
	@ResponseBody
	public void test() {
		publisher.publishEvent("refresh");
	}
	
	@EventListener
	public void sendRefresh(String msg) {

		List<SseEmitter> deleteList = new ArrayList<SseEmitter>();
		Session session = factory.openSession();
		List<WorkOrderDto> dto = service.getWorkOrderInfo(session);
		JSONArray arry = new JSONArray();
		for(WorkOrderDto d : dto) {
			arry.add(d.toJSON());
		}
		connections.stream().forEach(e->{
			try {
				
				e.send(SseEmitter.event().name("monitor").data(arry,MediaType.APPLICATION_JSON));
			}catch(Exception f) {
				deleteList.add(e);
			}
		});
		session.close();
		connections.removeAll(deleteList);
	}
	
	@EventListener
	public void bottomEvent(String msg) {

		List<SseEmitter> deleteList = new ArrayList<SseEmitter>();
		Session session = factory.openSession();
		LocalDate date = LocalDate.now();
		List<WorkOrderDto> dto = service.getCompleteWork(session, date, true);
		JSONArray arry = new JSONArray();
		for(WorkOrderDto d : dto) {
			arry.add(d.toJSON());
		}
		connections.stream().forEach(e->{
			try {
				
				e.send(SseEmitter.event().name("completeList").data(arry,MediaType.APPLICATION_JSON));
			}catch(Exception f) {
				deleteList.add(e);
			}
		});
		session.close();
		connections.removeAll(deleteList);
	}
}
