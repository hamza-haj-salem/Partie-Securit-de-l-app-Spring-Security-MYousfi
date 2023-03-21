package com.gtt.springboot.sec;

public interface SecurityParams {
												  
	public static final String JWT_HEADER_NAME = "authorization";
	public static final String SECRET = "hamza@hajsalem.net";
	public static final long EXPIRATION = 10*24*3600*1000;
	public static final String HEADER_PREFIX = "Bearer ";
}
