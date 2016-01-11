package net.is_bg.updatercenter.common.resources;

import net.is_bg.updatercenter.common.Enumerators.SESSION_STATUS;

public class Session {
	private String sessionId;
	private long createTime;
	private long lastAccesTime;
	private String ipAddress;
	String fileName;
	private long municipalityId;
	private String municipalityName;
	public String currentVersion;
	
	private SESSION_STATUS status;
	
	public Session(){
		this.createTime = System.currentTimeMillis();
		this.lastAccesTime = createTime;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public SESSION_STATUS getStatus() {
		return status;
	}

	public void setStatus(SESSION_STATUS status) {
		this.status = status;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public long getLastAccesTime() {
		return lastAccesTime;
	}

	protected void setLastAccesTime(long lastAccesTime) {
		this.lastAccesTime = lastAccesTime;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public long getMunicipalityId() {
		return municipalityId;
	}

	public void setMunicipalityId(long municipalityId) {
		this.municipalityId = municipalityId;
	}

	public String getMunicipalityName() {
		return municipalityName;
	}

	public void setMunicipalityName(String municipalityName) {
		this.municipalityName = municipalityName;
	}

	public String getCurrentVersion() {
		return currentVersion;
	}

	public void setCurrentVersion(String currentVersion) {
		this.currentVersion = currentVersion;
	}
	
	

}
