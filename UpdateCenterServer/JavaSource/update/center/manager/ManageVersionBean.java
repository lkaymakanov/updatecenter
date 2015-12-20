package update.center.manager;

import java.util.Collection;
import java.util.Set;

import update.center.controllers.IUpdateCenterController;
import update.center.controllers.UpdateCenterController;
import version.VersionDescriptions;
import version.WarVersionDescription;



public class ManageVersionBean {

	private IUpdateCenterController c = UpdateCenterController.getIUpdateCenterController();
	
	public String getLibs(){
		return c.getLibraries().toString();
	}
	
	
	public Collection<WarVersionDescription> getVersionDescriptions(){
		return VersionDescriptions.getDescriptions();
	}
	
	public Set<String> getSessions() {
		return c.getSessions();
	}
}
