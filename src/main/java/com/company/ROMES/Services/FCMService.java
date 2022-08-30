package com.company.ROMES.Services;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
@Service
public class FCMService {
	private static final String FIREBASE_CONFIG_PATH = "./firebase_service_key.json";

	private String getAccessToken() throws IOException {
		GoogleCredentials credentials = GoogleCredentials
				.fromStream(new ClassPathResource(FIREBASE_CONFIG_PATH).getInputStream())
				.createScoped(Arrays.asList("https://www.googleapis.com/auth/cloud-platform"));
		credentials.refreshIfExpired();
		return credentials.getAccessToken().getTokenValue();
	}

	public void sendMessage(String token, String title, String message) {
		Notification notification = new Notification(title, message);
		Message msg = Message.builder().setToken(token).setNotification(notification).build();
		String response = null;
		try {
			response = FirebaseMessaging.getInstance(initialize()).send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private FirebaseApp initialize() {
		FirebaseApp app = null;
		try {
			FirebaseOptions options = new FirebaseOptions.Builder()
					.setCredentials(
							GoogleCredentials.fromStream(new ClassPathResource(FIREBASE_CONFIG_PATH).getInputStream()))
					.build();
			if (FirebaseApp.getApps().isEmpty()) {
				app = FirebaseApp.initializeApp(options);
			} else {
				app = FirebaseApp.getApps().get(0);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return app;
	}

	public void sendTopickMessage(String topick, String title, String message) {
		Notification notification = new Notification(title, message);
		Message msg = Message.builder().setTopic(topick).setNotification(notification).build();
		String response = null;
		try {
			response = FirebaseMessaging.getInstance(initialize()).send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		FCMService s = new FCMService();
		s.sendTopickMessage("LGWing", "D", "개세끼");
	}
}
