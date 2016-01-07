package version;

import java.nio.file.Path;
import java.nio.file.WatchEvent;

public class EventInfo {

	private  WatchEvent<Path> event;
	
	public EventInfo(WatchEvent<Path> event){
		this.event = event;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Path:" + event.context() + " KIND " + event.kind() + " count:" + event.count(); 
	}

	public WatchEvent<Path> getEvent() {
		return event;
	}
	
	
	
}
