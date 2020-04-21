package com.xzcode.ggserver.game.monitor.common.data.model.server;

public class NetworkInfo {
	
	protected String name;
	protected String mac;
	protected String ipv4;
	protected String ipv6;
	protected long sendSpeed;
	protected long receiveSpeed;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public String getIpv4() {
		return ipv4;
	}
	public void setIpv4(String ipv4) {
		this.ipv4 = ipv4;
	}
	public String getIpv6() {
		return ipv6;
	}
	public void setIpv6(String ipv6) {
		this.ipv6 = ipv6;
	}
	public long getSendSpeed() {
		return sendSpeed;
	}
	public void setSendSpeed(long sendSpeed) {
		this.sendSpeed = sendSpeed;
	}
	public long getReceiveSpeed() {
		return receiveSpeed;
	}
	public void setReceiveSpeed(long receiveSpeed) {
		this.receiveSpeed = receiveSpeed;
	}

}
