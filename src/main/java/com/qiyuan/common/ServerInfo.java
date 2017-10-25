package com.qiyuan.common;

import java.util.Date;

public class ServerInfo {
	private String ip;
	private int port;
	private Date lastlinktime;
	
	public Date getLastlinktime() {
		return lastlinktime;
	}
	public void setLastlinktime(Date lastlinktime) {
		this.lastlinktime = lastlinktime;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	
	
}
