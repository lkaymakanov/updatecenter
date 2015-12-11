package version;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.is_bg.controller.AppConstants.CONTEXTPARAMS;




/**
 * Description of all versions updates supported by Server!!!!
 * @author lubo
 *
 */
public class VersionDescriptions {
	
	

	//ltf version description
	private static  WarVersionDescription ltfVersionDescription;
	private static  WarVersionDescription onlineReportVersionDescription;
	private static  List<CommonVersionDescription> descriptions = new  ArrayList<CommonVersionDescription>();
	
	
	public static WarVersionDescription getLtfDescription() {
		if(ltfVersionDescription == null )
			try {
				ltfVersionDescription = new  WarVersionDescriptionEx((String)CONTEXTPARAMS.LTF_UPDATE_ROOT_DIR.getValue(),
						 (String)CONTEXTPARAMS.LTF_WAR_FILE.getValue(), "[lL][tT][fF]-1.2-(\\d)+.[wW][aA][rR]", 5*1024*1024, 8, true);
				descriptions.add(ltfVersionDescription);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		return ltfVersionDescription;
	}
	
	public static WarVersionDescription getOnlineReportVersionDescription(){
		if(onlineReportVersionDescription == null ){
			try {
				onlineReportVersionDescription = new WarVersionDescriptionEx((String)CONTEXTPARAMS.ONLINE_REPORT_ROOT_DIR.getValue(),
						 (String)CONTEXTPARAMS.ONLINE_REPORT_WAR_FILE.getValue(), "[Oo][nN][Ll][Ii][Nn][Ee][Rr][Ee][Pp][Oo][Rr][Tt][Ww][Ss][Cc][Ll][Ii][Ee][Nn][Tt]-1.2-(\\d)+.[wW][aA][rR]", 5*1024*1024, 25, true);
				descriptions.add(onlineReportVersionDescription);
			}catch(Exception ex){
				ex.printStackTrace();
				throw new RuntimeException(ex);
			}
		}
		return onlineReportVersionDescription;
	}
	
	public static void initDescriptions(){
		getLtfDescription();
		getOnlineReportVersionDescription();
	}
	
	public static List<CommonVersionDescription> getDescriptions(){
		return descriptions;
	}
}
