package update.center.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import update.center.manager.ModalDialogProviderBean;
import update.center.security.LoginBean;
import update.center.security.User;
import version.IModalDailogProvider;
import version.ModalDialog;

public class AppUtil {

	  public static ValueExpression  createValueExpression(String valueExpression, Class<?> valueType) {
		  	FacesContext fc = FacesContext.getCurrentInstance();
			return fc.getApplication().getExpressionFactory().createValueExpression(fc.getELContext(), valueExpression, valueType);
	  }
	  
	  public static IModalDailogProvider getProvider(){
		  return new IModalDailogProvider() {
			@Override
			public ModalDialog getModalDialog() {
				// TODO Auto-generated method stub
				 FacesContext fc = FacesContext.getCurrentInstance();
				 ValueExpression expression = createValueExpression("#{modalDialogProviderBean}", ModalDialogProviderBean.class);
				 return ((ModalDialogProviderBean) expression.getValue(fc.getELContext())).getModalDialog();
			}
		};
	  }
	  
	  
	  public static FacesContext getFacesContext(){
		return   FacesContext.getCurrentInstance();
	  }
	  
	  
	  
	  public static User getCurrentUser(){
		 Object o =  AppUtil.getFacesContext().getExternalContext().getSessionMap().get(LoginBean.USER_KEY);
		 return o == null ? null : (User)o;
	  }
	  
	  
	  public static void downloadFile(String pathToFile, String fileName){
		  FacesContext fc = FacesContext.getCurrentInstance();
		   HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
		   OutputStream out = null;
		   //String mimeType = getMimeType(fp);
		   response.setContentType("application/x-unknown");
          //response.setContentType("application/octet-stream");
          //response.setContentType("application/x-download");
		   //fileName = System.getProperty("java.io.tmpdir") + "\\" + fileName;
          //fileName = "C:/Temp/" + fileName;
          //fileName = "fooo"; 
		   response.setHeader("Content-disposition", "attachment; filename=\"" + fileName + "\"");
		   File f = new File(pathToFile);   
		   response.setContentLength((int) f.length());
		   //response.setHeader("Cache-Control", "cache, must-revalidate");
		   try {
		    FileInputStream fileIn = new FileInputStream(f);
		    //DataInputStream in = new DataInputStream(fileIn);
		    out = response.getOutputStream();
		    //byte[] bytes = new byte[1024 * 4];
			int read = 0;
		    byte[] outputByte = new byte[1024*1024];
		    while ((read = fileIn.read(outputByte)) != -1)
		    { 
		    	out.write(outputByte, 0, read);
		    }
		    fileIn.close();
	        out.flush();
		    out.close();
		    fc.responseComplete(); //this is important		    
		   } catch (IOException e1) {
			   throw new RuntimeException(e1);
		   }
	  }
	  
	  
	  
}
