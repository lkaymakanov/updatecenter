package update.center.manager;

import java.io.Serializable;

import update.center.controllers.Bundles;

public class HeaderBean implements Serializable {

	/**
	 */
	private static final long serialVersionUID = -7988042379666460680L;
	private static final String finalName = Bundles.appBundle.getString("finalName");
	private static final String version = Bundles.appBundle.getString("version");
	private static final String buildNumber = Bundles.appBundle.getString("buildNumber");
	private static final String buildDate = Bundles.appBundle.getString("buildDate");
	private static final String buildTime = Bundles.appBundle.getString("buildTime");


	public  String getFinalname() {
		return finalName;
	}

	public  String getVersion() {
		return version;
	}
	public  String getBuildnumber() {
		return buildNumber;
	}
	public String getFinalName() {
		return finalName;
	}

	public String getBuilddate() {
		return buildDate;
	}

	public  String getBuildtime() {
		return buildTime;
	}
	
	
}
