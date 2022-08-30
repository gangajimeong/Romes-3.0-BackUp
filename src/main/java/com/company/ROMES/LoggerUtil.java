package com.company.ROMES;

import org.slf4j.LoggerFactory;

public class LoggerUtil {

	public static void info(String title, String msg){
		LoggerFactory.getLogger(title).info(msg);;
	
	}
	public static void info(String title,String who,String msg){
		LoggerFactory.getLogger(title).info("["+who+"] "+msg);;
	}
}
