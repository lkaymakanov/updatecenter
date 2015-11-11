package net.is_bg.init;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;




import version.VersionDescriptions;
import net.is_bg.controller.AppConstants.CONTEXTPARAMS;


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
		
		VersionDescriptions.getLtfDescription();
		
		System.out.println("======================= CONTEXT INITIALIZED =======================");
	}

}
