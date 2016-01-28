package update.center.manager;

import java.io.Serializable;

import update.center.controllers.Bundles;

public class HeaderBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7988042379666460680L;
	private static final String build = Bundles.appBundle.getString("finalName");
	
	public String getBuild() {
		return build;
	}
}
