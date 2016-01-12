package update.center.controllers;



import com.cc.rest.jersey.CCRestResources;

/**
 * Contains configuration for the controllers packages!!!
 * @author lubo
 *
 */
public class ApplicationResources extends CCRestResources {
	public static final String CONTROLLER_PACKAGE = "update.center.controllers";           //the package that contains controllers
    public ApplicationResources() {
		packages(CONTROLLER_PACKAGE);
		// Override date format
		//ObjectMapperProvider.getObjectMapper(MEDIA_TYPE.JSON).setDateFormat(ObjectMapperProvider.DATE_FORMAT_ISO8601_WITHOUT_TIME);
		//ObjectMapperProvider.getObjectMapper(MEDIA_TYPE.XML).setDateFormat(ObjectMapperProvider.DATE_FORMAT_ISO8601_WITHOUT_TIME);
    }
}
