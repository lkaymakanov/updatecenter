package update.center.security;

import java.util.Properties;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import update.center.controllers.AppUtil;
import update.center.controllers.Bundles;
import update.center.init.ApplicationInitListener;
import update.center.init.ApplicationSessionManager;
import update.center.init.HttpSessionEx;
import update.center.manager.CommonBean;
import version.ModalDialog;

public class LoginBean extends CommonBean{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6525690883317581178L;
	private Properties users = ApplicationInitListener.users;
	private User user = new  User();
	public final static String USER_KEY = "user";
	private final static String SUCCESS_FULL_LOGIN  = "toServerInfo";
	
	
	public String login(){
		HttpServletRequest request = (HttpServletRequest) AppUtil.getFacesContext().getExternalContext().getRequest();
		
		try{
			Object  p = users.get(user.getUserName());
			String userpass = (p == null) ? null : p.toString();
			if(userpass == null) throw new RuntimeException("User not exists!!!");
			else{
				if(!userpass.equals(new Sha512().digest(user.getUserName() +  user.getUserPass()))) throw new RuntimeException("Pass is not correct!!!");
			}
			
			
			//add user to session
			System.out.println("user logged in successfully...");
			
			//put user to session map
			AppUtil.getFacesContext().getExternalContext().getSessionMap().put(USER_KEY, user);
			HttpSessionEx session = new HttpSessionEx((HttpSession)AppUtil.getFacesContext().getExternalContext().getSession(false));
			session.setUser(user);
			session.setIpAddress(request.getRemoteAddr());
			ApplicationSessionManager.addSession(session);
			return SUCCESS_FULL_LOGIN;
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			getModalDialog().setErrMsg(e.getMessage());
		}
		return null;
	}


	public User getUser() {
		return user;
	}
	
	public ModalDialog getModalDialog(){
		return AppUtil.getProvider().getModalDialog();
	}
	/**
     * Modal dialog clear.
     */
    public void modalDialogClear() {
    	AppUtil.getProvider().getModalDialog().clear();
    }

    /**
     * Modal yes.
     *
     * @return the string
     */
    public String modalYes() {
	modalDialogClear();
	return null;
    }

    /**
     * Modal no.
     *
     * @return the string
     */
    public String modalNo() {
	modalDialogClear();
	return null;
    }

    /**
     * Modal ok.
     *
     * @return the string
     */
    public String modalOk() {
	modalDialogClear();
	return null;
    }

    // send report button event handler
    /**
     * Modal report.
     *
     * @return the string
     */
    public String modalReport() {

	modalDialogClear();
	return null;
    }
    
    
	public String getBuild(){
		return Bundles.footerBundle.getString("finalName");
	}
  
	
}
