package update.center.manager;

import java.io.Serializable;

import update.center.controllers.Bundles;

public class FooterBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3599402653397017343L;
	private String me;
	private String io;
	
	public FooterBean() {
		// TODO Auto-generated constructor stub
		me = Bundles.footerBundle.getString("batlubo");
		io = Bundles.footerBundle.getString("io");
	}

	public String getMe() {
		return me;
	}

	public void setMe(String me) {
		this.me = me;
	}

	public String getIo() {
		return io;
	}

	public void setIo(String io) {
		this.io = io;
	}
	
}
