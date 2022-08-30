package com.company.ROMES.message;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HttpsURLConnection;

import org.ini4j.Ini;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

//import com.company.ROMES.test.testMain;

import Error_code.ResultCode;

public class Message {
	private JSONArray arry;
	private String serviceId;
	private String accessKey;
	private String secretKey;
	private String msgType;
	private String myNumber;
	public final static String SMS = "SMS";
	public final static String LMS = "LMS";
	private String defaultMessage="";
	public Message() throws Exception {
		arry = new JSONArray();
		File file = new File("./setting.ini");
		Ini ini = new Ini(file);
		serviceId = ini.get("sms-api", "service_id");
		accessKey = ini.get("sms-api", "access_key");
		secretKey = ini.get("sms-api", "secret_key");
		myNumber = ini.get("sms-api", "phone_number");
	}
	public Message(String message) throws Exception {
		this.arry = new JSONArray();
		File file = new File("./setting.ini");
		Ini ini = new Ini(file);
		this.serviceId = ini.get("sms-api", "service_id");
		this.accessKey = ini.get("sms-api", "access_key");
		this.secretKey = ini.get("sms-api", "secret_key");
		this.myNumber = ini.get("sms-api", "phone_number");
		this.defaultMessage = message;
	}

	public void addMessage(String phoneNum, String text) {
		JSONObject object = new JSONObject();
		object.put("to", phoneNum);
		object.put("content", text);
		arry.add(object);
	}

	public void setMyNumber(String myNumber) {
		this.myNumber = myNumber;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public void setDefaultMsg(String defualtMsg) {
		this.defaultMessage = defualtMsg;
	}
	public int sendMsg() {
		if(arry.size() == 0) {
			System.out.println("아무런 메시지가 없습니다.");
			return ResultCode.NO_RESULT;
		}else if(defaultMessage.equals("")) {
			System.out.println("기본 메시지가 없습니다. setDefaultMsg(String str) 함수를 통해 기본 메시지를 설정해 주세요");
			return ResultCode.REQUIRE_ELEMENT_ERROR;
		}
		int responseCode = 0;
		String timeStamp = Long.toString(System.currentTimeMillis());
		String requestType = serviceId + "/messages";
		String cryptUrl = "/sms/v2/services/" + requestType;
		String apiUrl = "https://sens.apigw.ntruss.com" + cryptUrl;
		JSONObject object = new JSONObject();
		object.put("type", "SMS");
		object.put("contentType", "COMM");
		object.put("countryCode", "82");
		object.put("from", myNumber);
		object.put("content", defaultMessage);
		object.put("messages", arry);
		try {
			URL url = new URL(apiUrl);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setUseCaches(false);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestProperty("content-type", "application/json");
			conn.setRequestProperty("x-ncp-apigw-timestamp", timeStamp);
			conn.setRequestProperty("x-ncp-iam-access-key", accessKey);
			conn.setRequestProperty("x-ncp-apigw-signature-v2",
					makeSignature(cryptUrl, timeStamp, accessKey, secretKey));
			conn.setRequestMethod("POST");
//				conn.connect();
			DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
			wr.write(object.toJSONString().getBytes());
			wr.flush();
			wr.close();
			responseCode = conn.getResponseCode();
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = null;
			
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return responseCode;
	}

	private String makeSignature(String url, String timestamp, String accessKey, String secretKey) {
		try {
			String space = " "; // one space
			String newLine = "\n"; // new line
			String method = "POST"; // method
			String message = new StringBuilder().append(method).append(space).append(url).append(newLine)
					.append(timestamp).append(newLine).append(accessKey).toString();

			SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
			Mac mac = Mac.getInstance("HmacSHA256");
			mac.init(signingKey);

			byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
			String encodeBase64String = Base64.getEncoder().encodeToString(rawHmac);

			return encodeBase64String;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
