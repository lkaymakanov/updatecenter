package update.center.init;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import version.VersionDescriptions;
import net.is_bg.controller.AppConstants.CONTEXTPARAMS;
import net.is_bg.controller.AppConstants.VERSION_VALIDATION_PATTERNS;
import net.is_bg.controller.ApplicationLibFiles;

public class ApplicationInitListener implements ServletContextListener{

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
		//SessionRegister.init();
		CONTEXTPARAMS.printParams();
				
		//init application lib files
		ApplicationLibFiles.initApplicationLibFiles((String)CONTEXTPARAMS.UPDATE_CENTER_LIB_DIR.getValue());
		
		//read validation patterns property files
		VERSION_VALIDATION_PATTERNS.initPropertiesByPropertyFile(CONTEXTPARAMS.UPDATE_CENTER_VALIDATION_PATTERN_PROPERTY_FILE.getValue().toString());
		
		//init version descriptions
		VersionDescriptions.initDescriptions();
				
		
		System.out.println("===================================== UpdateCenterServer context initialized =========================================");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		System.out.println("===================================== UpdateCenterServer context destroyed =========================================");
	}
}
