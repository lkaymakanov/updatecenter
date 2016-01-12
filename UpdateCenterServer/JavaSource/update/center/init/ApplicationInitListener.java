package update.center.init;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import update.center.controllers.AppUtil;
import version.EventInfo;
import version.IElementProcessor;
import version.VersionDescriptions;
import version.VersionFolderManager;
import net.is_bg.controller.AppConstants.CONTEXTPARAMS;
import net.is_bg.controller.AppConstants.VERSION_VALIDATION_PATTERNS;
import net.is_bg.controller.ApplicationLibFiles;
import net.is_bg.controller.FileChangeObserver;
import net.is_bg.controller.FileChangeWatcher;
import net.is_bg.updatercenter.common.FileUtil;

public class ApplicationInitListener implements ServletContextListener{

	static VersionFolderManager<EventInfo> foldermanager;
	
	public static  Properties users;
	
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
		VersionDescriptions.initDescriptions(AppUtil.getProvider());
		
		//load users
		users = FileUtil.loadProperties((String)CONTEXTPARAMS.UPDATE_CENTER_USERS_PROPERTY_FILE.getValue());
		
		
		foldermanager = new VersionFolderManager<EventInfo>(new IElementProcessor<EventInfo>(){
			@Override
			public void process(EventInfo element) {
				// TODO Auto-generated method stub
				System.out.println(new File(CONTEXTPARAMS.UPDATE_CENTER_VERSIONS_DIR.getValue() + File.separator + element.getEvent().context()).lastModified());
				System.out.println("Entered in process EventInfo  " + element);
			}
			
		});
		
		//start file update server version directory listener!!! 
		FileChangeWatcher fc;
		try {
			fc = FileChangeWatcher.getInstance();
			fc.registerListener((String)CONTEXTPARAMS.UPDATE_CENTER_VERSIONS_DIR.getValue());
			fc.addObserver(new FileChangeObserver<WatchEvent<Path>>() {
				@Override
				public void update(WatchEvent<Path> event) {
					// TODO Auto-generated method stub
					ApplicationInitListener.foldermanager.addElement(new EventInfo(event));
				}
			});
			fc.startMonitoringLoop();
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("===================================== UpdateCenterServer context initialized =========================================");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		System.out.println("===================================== UpdateCenterServer context destroyed =========================================");
	}
}
