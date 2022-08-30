package com.company.ROMES.message;

import com.google.auto.value.AutoValue.Builder;

public class FcmMessage {
	private boolean validate_only;
	public static class Message{
		private String token;
		private Message message;
		
		public Message getMessage() {
			return message;
		}
		public void setMessage(Message message) {
			this.message = message;
		}
		private Notification notification;
		public String getToken() {
			return token;
		}
		public void setToken(String token) {
			this.token = token;
		}
		public Notification getNotification() {
			return notification;
		}
		public void setNotification(Notification notification) {
			this.notification = notification;
		}
		
	}
	public static class Notification{
		private String title;
		private String body;
		private String image;
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getBody() {
			return body;
		}
		public void setBody(String body) {
			this.body = body;
		}
		public String getImage() {
			return image;
		}
		public void setImage(String image) {
			this.image = image;
		}
		
	}
}
