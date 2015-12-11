package net.is_bg.controller;

import com.cc.rest.client.Requester.MEDIA_TYPE;
import com.cc.rest.jersey.CCRestResources;
import com.cc.rest.jersey.ObjectMapperProvider;

/**
 * Contains configuration for the controllers packages!!!
 * @author lubo
 *
 */
public class ApplicationResources extends CCRestResources {
    public ApplicationResources() {
		packages("net.is_bg.controller");
		// Override date format
		ObjectMapperProvider.getObjectMapper(MEDIA_TYPE.JSON).setDateFormat(ObjectMapperProvider.DATE_FORMAT_ISO8601_WITHOUT_TIME);
		ObjectMapperProvider.getObjectMapper(MEDIA_TYPE.XML).setDateFormat(ObjectMapperProvider.DATE_FORMAT_ISO8601_WITHOUT_TIME);
    }
}
