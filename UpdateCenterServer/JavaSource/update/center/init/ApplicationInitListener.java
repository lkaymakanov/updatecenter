package update.center.init;

import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import net.is_bg.controller.AppConstants.CONTEXTPARAMS;
import net.is_bg.controller.AppConstants.VERSION_VALIDATION_PATTERNS;
import net.is_bg.controller.ApplicationLibFiles;
import net.is_bg.updatercenter.common.FileUtil;
import update.center.controllers.AppUtil;
import version.VersionDescriptions;

public class ApplicationInitListener implements ServletContextListener{

	//static VersionFolderManager<EventInfo> foldermanager;
	/**A hash map that contains the pack zips for each java version*/
	public final static Map<String, Boolean> packZips = new  Hashtable<String, Boolean>();
	
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
		
		new DataSourceConnectionFactory().getConnection("jdbc/mycon");
		
		//copy pack zip for each java version into the server lib directory
		/*URL url = new FindResource().getResourceUrl("pack");
		System.out.println("Pack URL is " + URLDecoder.decode(url.toString()));
		
		String packFolder = URLDecoder.decode(new FindResource().getResourceUrl("pack").getFile());
		System.out.println("Pack folder is " + packFolder);
		File f = new  File(packFolder);
		for(String child : f.list()){
			System.out.println(packFolder + child);
			try {
				FileUtil.copyFile(new File(packFolder + child), new File((String)CONTEXTPARAMS.UPDATE_CENTER_LIB_DIR.getValue() + File.separator + child));
				packZips.put(child, true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}*/

		//AppUtil.createSessionRegister(30, 2, TimeUnit.MINUTES);
		
		//init version descriptions
		VersionDescriptions.initDescriptions(AppUtil.getProvider(), packZips);
		
		//load users
		users = FileUtil.loadProperties((String)CONTEXTPARAMS.UPDATE_CENTER_USERS_PROPERTY_FILE.getValue());
		
		//copy to lib directory
		
		/*
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
		}*/
		
		System.out.println("===================================== UpdateCenterServer context initialized =========================================");
	}
	

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		System.out.println("===================================== UpdateCenterServer context destroyed =========================================");
	}
}
