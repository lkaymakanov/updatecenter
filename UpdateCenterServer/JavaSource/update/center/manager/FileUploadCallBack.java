package update.center.manager;

import java.io.File;

import org.richfaces.event.UploadEvent;

public interface FileUploadCallBack {

	public void callback(File file, UploadEvent event);
	public void callback(byte [] file, UploadEvent event);
}
