package net.is_bg.init;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;





import version.VersionDescriptions;
import net.is_bg.controller.AppConstants.CONTEXTPARAMS;
import net.is_bg.controller.ApplicationLibFiles;


@WebListener("Application init listener")
public class ApplicationInitListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("======================= CONTEXT DESTROYED =======================");
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		//SessionRegister.init();
		CONTEXTPARAMS.printParams();
		
		//init application lib files
		ApplicationLibFiles.initApplicationLibFiles((String)CONTEXTPARAMS.SERVER_LIB_DIR.getValue());
		
		//init version descriptions
		VersionDescriptions.initDescriptions();
		
		System.out.println("======================= CONTEXT INITIALIZED =======================");
	}

}
