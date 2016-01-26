package update.center.init;


import java.io.InputStream;
import java.net.URL;

public class FindResource {

	
	public InputStream getResourceStream(String name){
		return getClass().getClassLoader().getResourceAsStream(name);
	}
	
	
	public URL getResourceUrl(String name){
		return getClass().getClassLoader().getResource(name);
	}
}
