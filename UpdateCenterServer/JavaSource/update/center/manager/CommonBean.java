package update.center.manager;

import java.io.Serializable;

public class CommonBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected FooterBean footerBean = new  FooterBean();
	protected HeaderBean headerBan = new HeaderBean();

	public FooterBean getFooterBean() {
		return footerBean;
	}

	public void setFooterBean(FooterBean footerBean) {
		this.footerBean = footerBean;
	}

	public HeaderBean getHeaderBan() {
		return headerBan;
	}
	
	

}
