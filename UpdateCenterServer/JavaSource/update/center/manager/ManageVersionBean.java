package update.center.manager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import net.is_bg.controller.AppConstants.CONTEXTPARAMS;
import net.is_bg.controller.AppConstants.VERSION_VALIDATION_PATTERNS;
import net.is_bg.ltf.Message;
import net.is_bg.ltf.SimpleLock;
import net.is_bg.updatercenter.common.FileUtil;
import net.is_bg.updatercenter.common.resources.Session;

import org.richfaces.event.UploadEvent;

import update.center.controllers.AppUtil;
import update.center.controllers.IUpdateCenterController;
import update.center.controllers.UpdateCenterController;
import update.center.init.ApplicationInitListener;
import version.ModalDialog;
import version.VersionDescriptions;
import version.WarVersionDescription;
import version.WarVersionLocks;



public class ManageVersionBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8426369952000322764L;

	/**
	 * Controller
	 */
	private IUpdateCenterController c = UpdateCenterController.getIUpdateCenterController();
    
	private String pattern;
	
	
	
	
	
	/**
	 * File Upload Bean
	 */
	private FileUploadBean fileBean = new  FileUploadBean(new  FileUploadCallBack() {
		@Override
		public void callback(byte[] file, UploadEvent event) {
			// TODO Auto-generated method stub
			String fName = event.getUploadItem().getFileName();
			
			//save the file to version directory
			ByteArrayOutputStream os  = new ByteArrayOutputStream(file.length);
			FileOutputStream fos = null;
			String fileName = ((String)CONTEXTPARAMS.UPDATE_CENTER_VERSIONS_DIR.getValue()) + File.separator + fName;
			String lockName = FileUtil.removeFileExtension(fileName);
			WarVersionLocks.addLockifNotExist(lockName);
			SimpleLock lock = WarVersionLocks.getLock(lockName);
			try {
				if(!lock.lock(new Message("Uploading  file " + fileName))){
					fos = new FileOutputStream(new File(fileName));
					os.write(file);
					
					//add to version descriptions
					VersionDescriptions.initDescription(fileName, AppUtil.getProvider());
				}else{
					throw new RuntimeException(lock.getMessage().getValue());
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				AppUtil.getProvider().getModalDialog().setErrMsg(e.getMessage());
			}finally{
				if(fos!=null){
					try {
						os.flush();
						fos.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		
		@Override
		public void callback(File file, UploadEvent event) {
			// TODO Auto-generated method stub
			String fName = event.getUploadItem().getFileName();
			FileOutputStream fos = null;
			FileInputStream ins = null;
			String fileName = ((String)CONTEXTPARAMS.UPDATE_CENTER_VERSIONS_DIR.getValue()) + File.separator + fName;
			byte [] buffer = new byte [100*1024];
			String lockName = FileUtil.removeFileExtension(fileName);
			WarVersionLocks.addLockifNotExist(lockName);
			SimpleLock lock = WarVersionLocks.getLock(lockName);
			try {
				if(!lock.lock(new Message("Uploading  file " + fileName))){
					fos = new FileOutputStream(fileName);
					ins = new FileInputStream(file);
					
					int bytes = 0;
					bytes = ins.read(buffer);
					while(bytes !=-1){
						fos.write(buffer);
						bytes = ins.read(buffer);
					}
					//add to version descriptions
					VersionDescriptions.initDescription(fileName, AppUtil.getProvider());
				}else{
					throw new RuntimeException(lock.getMessage().getValue());
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				AppUtil.getProvider().getModalDialog().setErrMsg(e.getMessage());
			}
			finally{
				if(fos!=null){
					try {
						fos.flush();
						fos.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if(ins != null){
					try {
						ins.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		}
	});

	
	public String logOut(){
		HttpSession session = (HttpSession) AppUtil.getFacesContext().getExternalContext().getSession(false);
		//session.removeAttribute(AppConstants.VISIT_KEY_SCOPE + AppConstants.VISIT_KEY);
		if (session != null)  session.invalidate();
		return "toLogin";
	}
	
	
	public List<String> getContextParams(){
		List<String> s=  new ArrayList<>();
		CONTEXTPARAMS [] a = CONTEXTPARAMS.values();
		for(CONTEXTPARAMS p : a){
			s.add(p.getName()+ "=" + p.getValue());
		}
		return s;
	}
	
	public ModalDialog getModalDialog(){
		return AppUtil.getProvider().getModalDialog();
	}
	
	
	public Set<String> getLibs(){
		return c.getLibraries();
	}
	
	
	public Collection<WarVersionDescription> getVersionDescriptions(){
		return VersionDescriptions.getDescriptions();
	}
	
	public List<Session> getSessionInfo() {
		return c.getSessionsInfo();
	}


	public FileUploadBean getFileBean() {
		return fileBean;
	}
	
	public void savePattern(){
		VERSION_VALIDATION_PATTERNS.savePattern(pattern, CONTEXTPARAMS.UPDATE_CENTER_VALIDATION_PATTERN_PROPERTY_FILE.getValue().toString());
	}
	
	public List<ValidationPatternModel> getPatterns(){
		return ValidationPatternModel.propToValidationPatterns();
	}
	

	public String getPattern() {
		return pattern;
	}


	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
	
	
	
	
	/**
     * Modal dialog clear.
     */
    public void modalDialogClear() {
    	getModalDialog().clear();
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
	
	
	
}
