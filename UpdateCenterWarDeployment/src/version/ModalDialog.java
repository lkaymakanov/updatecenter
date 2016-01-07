package version;
import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Class ModalDialog.
 */
public class ModalDialog implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8611410404823167197L;
	
	/** The Constant msgModalMsg. */
	
	/** The err msg. */
	private String errMsg;
	
	/** The report msg. */
	private String reportMsg;
	
	/** The recipients lis. */
	private String recipientsLis;
	
	/** The info msg. */
	private String infoMsg;
	
	/** The yes no msg. */
	private String yesNoMsg;
	
	
	/** The caption. */
	private String caption;
	
	/** The modal height. */
	private Integer modalHeight;
	
	/** The icon path. */
	private String iconPath;
	
	/** The close icon path. */
	private String closeIconPath = "/images/icons/delete.gif";
	
	
	

	/**
	 * Instantiates a new modal dialog.
	 */
	public ModalDialog() {
		errMsg = null;
		infoMsg = null;
		yesNoMsg = null;
	}
	
	
/*	private Integer mpHeight(String outputString){
		return (((outputString.length() / 60) + 1) * 14) + 100;
	}*/

	/**
 * Clear.
 */
public void clear(){
		errMsg = null;
		infoMsg = null;
		yesNoMsg = null;
	}
	
	/**
	 * Gets the err msg.
	 *
	 * @return the err msg
	 */
	public String getErrMsg() {
		return errMsg;
	}

	/**
	 * Sets the err msg.
	 *
	 * @param errMsg the new err msg
	 */
	public void setErrMsg(String errMsg) {
		infoMsg = null;
		yesNoMsg = null;
		caption = "error";
		//modalHeight = mpHeight(errMsg);
		iconPath = "/images/icons/error.gif"; 
		closeIconPath = "/images/icons/delete.gif";
		this.errMsg = errMsg;
	}
	
	
	/**
	 * Gets the info msg.
	 *
	 * @return the info msg
	 */
	public String getInfoMsg() {
		return infoMsg;
	}

	/**
	 * Sets the info msg.
	 *
	 * @param infoMsg the new info msg
	 */
	public void setInfoMsg(String infoMsg) {
		errMsg = null;
		yesNoMsg = null;
		caption = "info";
		//modalHeight = mpHeight(infoMsg);
		iconPath = "/images/icons/info.gif"; //da se promeni
		this.infoMsg = infoMsg;
	}

	/**
	 * Gets the yes no msg.
	 *
	 * @return the yes no msg
	 */
	public String getYesNoMsg() {
		return yesNoMsg;
	}

	/**
	 * Sets the yes no msg.
	 *
	 * @param yesNoMsg the new yes no msg
	 */
	public void setYesNoMsg(String yesNoMsg) {
		errMsg = null;
		infoMsg = null;
		caption = "YesNo";
		//modalHeight = mpHeight(yesNoMsg);
		iconPath = "/images/icons/question.gif"; 
		this.yesNoMsg = yesNoMsg;
	}

	/**
	 * Gets the caption.
	 *
	 * @return the caption
	 */
	public String getCaption() {
		return caption;
	}

	/**
	 * Sets the caption.
	 *
	 * @param caption the new caption
	 */
	public void setCaption(String caption) {
		this.caption = caption;
	}

	/**
	 * Gets the modal height.
	 *
	 * @return the modal height
	 */
	public Integer getModalHeight() {
		return modalHeight;
	}

	/**
	 * Sets the modal height.
	 *
	 * @param modalHeight the new modal height
	 */
	public void setModalHeight(Integer modalHeight) {
		this.modalHeight = modalHeight;
	}

	/**
	 * Gets the icon path.
	 *
	 * @return the icon path
	 */
	public String getIconPath() {
		return iconPath;
	}

	/**
	 * Sets the icon path.
	 *
	 * @param iconPath the new icon path
	 */
	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}
	//проверка да ли е сетнато съобщението за грешка
	/**
	 * Checks if is error msg.
	 *
	 * @return true, if is error msg
	 */
	public boolean isErrorMsg(){
		return (getErrMsg() != null && !getErrMsg().isEmpty());
	}
	

	
	/**
	 * Gets the close icon path.
	 *
	 * @return the close icon path
	 */
	public String getCloseIconPath() {
		return closeIconPath;
	}
	
	//set Report MSG 2 overloads
	//set the report message to be sent on mail
	/**
	 * Sets the report msg.
	 *
	 * @param repMsg the new report msg
	 */
	public void setReportMsg(String repMsg){
		reportMsg = repMsg;
		setErrMsg(repMsg);
	}
	
	/**
	 * Sets the report msg.
	 *
	 * @param recipients the recipients
	 * @param repMsg the rep msg
	 */
	public void setReportMsg(String recipients, String repMsg){
		reportMsg = repMsg;
		recipientsLis = recipients;
		setErrMsg(repMsg);
	}
	
	/**
	 * Gets the report msg.
	 *
	 * @return the report msg
	 */
	public String getReportMsg() {
		return reportMsg;
	}

	/**
	 * Gets the recipients lis.
	 *
	 * @return the recipients lis
	 */
	public String getRecipientsLis() {
		return recipientsLis;
	}

}
