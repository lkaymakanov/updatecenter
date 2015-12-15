package version;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VersionValidator {
	String pattern;
	String version;
	long versionNumber;
	
	
	public VersionValidator(String version, String pattern){
		this.pattern = pattern;
		this.version = version;
		
	}
	
	public boolean validate(){
		Pattern p = Pattern.compile(pattern);
	    Matcher m = p.matcher(version);
	    boolean b = false;
	    while(m.find()) {
	    	 b = true;
			 //System.out.println("Match: "+m.group());
			 if(m.start() > 0) throw new IllegalArgumentException("Invalid  version FileName...");
			 //System.out.println(m.start());
			 //System.out.println(m.end());
			 //System.out.println(version.length() > m.end());
			 if(version.length() > m.end())  throw new IllegalArgumentException("Invalid  version FileName...");
	    }
	    if(!b) throw new IllegalArgumentException("Invalid  version FileName...");
	    return b;
	}

	public long getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(long versionNumber) {
		this.versionNumber = versionNumber;
	}
	
}
