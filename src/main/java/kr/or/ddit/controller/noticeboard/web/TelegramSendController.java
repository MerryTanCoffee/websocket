package kr.or.ddit.controller.noticeboard.web;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TelegramSendController {

	public void sendGet(String name, String title) throws Exception{
		String chat_id = "-926307173";
		String urlName = "https://api.telegram.org/bot5845971648:AAE1Di11XM9IshZ0zLTXky_jhESx6eOKA-s/sendMessage";
		String text = "";
		
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date time = new Date();
		String time1 = format1.format(time);
		
		text = name + "님께서 글 작성을 완료했습니다!\n"+
		"제목 : " + title + 
		"[작성일]" + time1 + "\n";
		
		URL url = new URL(urlName + "?chat_id=" + chat_id + "&text=" + URLEncoder.encode(text,"UTF-8"));
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("User-Agent", "Mozilla/5.0");
		int respCode = conn.getResponseCode();
		System.out.println("결과코드 : " + respCode);
	}
}
