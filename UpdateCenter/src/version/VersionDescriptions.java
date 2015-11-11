package version;

import java.io.IOException;




/**
 * Description of all versions updates supported by Server!!!!
 * @author lubo
 *
 */
public class VersionDescriptions {
	
	

	//ltf version description
	private static  CommonVersionDescription ltfVersionDescription;
	
	public static CommonVersionDescription getLtfDescription() {
		if(ltfVersionDescription == null )
			try {
				ltfVersionDescription = new  LtfVersionDescription();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		return ltfVersionDescription;
	}
	
	
	
	
	
	
}
