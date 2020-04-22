package com.xzcode.ggserver.game.monitor.common.data.model.server;

/**
 * 网络（网卡）信息
 *
 * @author zai
 * 2020-04-22 14:12:40
 */
public class NetworkInfo {
	
	protected String name;//网卡名称
	
	protected String displayName;//显示名称
	protected int mtu;//Maximum Transmission Unit
	protected String mac;//Media Access Control Address
	protected String ipv4;
	protected String ipv6;
	protected long currentUpload;//当前上行速度 bytes/s
	protected long currentDownload;//当前下行发送速度 bytes/s
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
	
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public long getCurrentUpload() {
		return currentUpload;
	}
	public void setCurrentUpload(long currentUploadSpeed) {
		this.currentUpload = currentUploadSpeed;
	}
	public long getCurrentDownload() {
		return currentDownload;
	}
	public void setCurrentDownload(long currentdownloadSpeed) {
		this.currentDownload = currentdownloadSpeed;
	}
	public int getMtu() {
		return mtu;
	}
	
	public void setMtu(int mtu) {
		this.mtu = mtu;
	}

}
