package update.center.manager;

import javax.faces.event.ActionEvent;

import org.richfaces.event.UploadEvent;
import org.richfaces.model.UploadItem;


public class FileUploadBean {
	 	
	 private FileUploadCallBack callback;
	 
	 public FileUploadBean(FileUploadCallBack callback){
		 this.callback = callback;
	 }
	 
	 
	 public void listener(UploadEvent event) throws Exception {
        UploadItem item = event.getUploadItem();
        System.out.println( "Temp file is.. " + item.isTempFile());
        if(item.isTempFile()){
        	if(callback!=null) callback.callback(item.getFile(), event);
        }else{
        	if(callback!=null) callback.callback(item.getData(), event);
        }
	 }
	 
	 
	 public void clearFile(ActionEvent actionEvent){
		 
	 }
}
