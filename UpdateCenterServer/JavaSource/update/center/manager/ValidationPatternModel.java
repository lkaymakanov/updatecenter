package update.center.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.is_bg.controller.AppConstants.CONTEXTPARAMS;
import net.is_bg.controller.AppConstants.VERSION_VALIDATION_PATTERNS;


public class ValidationPatternModel {

	private String key;
	private String pattern;
     
	public ValidationPatternModel(String key, String pattern){
		this.key = key;
		this.pattern = pattern;
	}
     
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getPattern() {
		return pattern;
	}
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
    
	
	
	public void deletePattern(){
		VERSION_VALIDATION_PATTERNS.deletePattern(key, CONTEXTPARAMS.UPDATE_CENTER_VALIDATION_PATTERN_PROPERTY_FILE.getValue().toString());
	}
	
	public static List<ValidationPatternModel> propToValidationPatterns(){
		List<ValidationPatternModel> l = new  ArrayList<ValidationPatternModel>();
		Map<Object, Object> m = VERSION_VALIDATION_PATTERNS.getProperties();
		
		for(Object key: m.keySet()){
			ValidationPatternModel model = new ValidationPatternModel(key.toString(), m.get(key).toString());
			l.add(model);
		}
			
		return l;
	}
}
