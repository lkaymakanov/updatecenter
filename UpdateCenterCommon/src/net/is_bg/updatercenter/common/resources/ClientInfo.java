package net.is_bg.updatercenter.common.resources;

public class ClientInfo {
	private long municipalityId;
	private String currentVersion;

	public ClientInfo() {

	}

	public ClientInfo(long municipalityId, String currentVersion) {
		super();
		this.municipalityId = municipalityId;
		this.currentVersion = currentVersion;
	}

	public long getMunicipalityId() {
		return municipalityId;
	}

	public void setMunicipalityId(long municipalityId) {
		this.municipalityId = municipalityId;
	}

	public String getCurrentVersion() {
		return currentVersion;
	}

	public void setCurrentVersion(String currentVersion) {
		this.currentVersion = currentVersion;
	}

}
