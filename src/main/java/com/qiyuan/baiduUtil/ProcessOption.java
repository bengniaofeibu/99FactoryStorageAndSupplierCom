package com.qiyuan.baiduUtil;

public class ProcessOption {
	private int need_denoise;
	
	private int need_mapmatch;
	
	private int radius_threshold;
	
	private String transport_mode;

	public int getNeed_denoise() {
		return need_denoise;
	}

	public void setNeed_denoise(int need_denoise) {
		this.need_denoise = need_denoise;
	}

	public int getNeed_mapmatch() {
		return need_mapmatch;
	}

	public void setNeed_mapmatch(int need_mapmatch) {
		this.need_mapmatch = need_mapmatch;
	}

	public int getRadius_threshold() {
		return radius_threshold;
	}

	public void setRadius_threshold(int radius_threshold) {
		this.radius_threshold = radius_threshold;
	}

	public String getTransport_mode() {
		return transport_mode;
	}

	public void setTransport_mode(String transport_mode) {
		this.transport_mode = transport_mode;
	}
	
	
}
