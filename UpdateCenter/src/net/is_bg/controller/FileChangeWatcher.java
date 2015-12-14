package net.is_bg.controller;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Observable;

import static java.nio.file.StandardWatchEventKinds.*;

public final class FileChangeWatcher extends  Observable {
	private static FileChangeWatcher watcher;
	private WatchService service;
	private boolean monitoring = false;
	private static Object lock = new  Object();
	
	private FileChangeWatcher() throws IOException{
		createFileChangeWatcher();
	}
	
	private void createFileChangeWatcher() throws IOException{
		service = FileSystems.getDefault().newWatchService();
	}
	
	public void registerListener(Path p) throws IOException{
		p.register(service, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
	}
	
	
	public void registerListener(String folder) throws IOException{
		registerListener(FileSystems.getDefault().getPath(folder));
	}
	
	
	public synchronized void addObserver(FileChangeObserver<WatchEvent<Path>> o) {
		// TODO Auto-generated method stub
		super.addObserver(o);
	}
	
	
	public static FileChangeWatcher getInstance() throws IOException{
		if(watcher == null){
			synchronized (lock) {
				if(watcher == null) watcher = new  FileChangeWatcher();
			}
		}
		return watcher;
	}
	
	public boolean isMonitoring(){
		return monitoring;
	}
	
	public void stopMonitoring(){
		synchronized (this) {
			if(!monitoring) return;
			else monitoring = false;
		}
	}
	
	public void startMonitoringLoop(){
			synchronized (this) {
				if(monitoring) return;
				else monitoring = true;
			}
			for (;;) {
				if(!monitoring) break;
			    // wait for key to be signaled
			    WatchKey key;
			    try {
			        key = service.take();
			    } catch (InterruptedException x) {
			    	monitoring = false;
			        return;
			    }
	
			    for (WatchEvent<?> event: key.pollEvents()) {
			        WatchEvent.Kind<?> kind = event.kind();
	
			        // This key is registered only
			        // for ENTRY_CREATE events,
			        // but an OVERFLOW event can
			        // occur regardless if events
			        // are lost or discarded.
			        if (kind == OVERFLOW) {
			            continue;
			        }
	
			        // The filename is the
			        // context of the event.
			       /* WatchEvent<Path> ev = (WatchEvent<Path>)event;*/
			       // Path filename = ev.context();
			        
			       //notify observers
			       setChanged();
			       notifyObservers(event);
			        
			        
			        //System.out.println(filename  + " kind event: " + kind);
	
			        /*// Verify that the new
			        //  file is a text file.
			        try {
			            // Resolve the filename against the directory.
			            // If the filename is "test" and the directory is "foo",
			            // the resolved name is "test/foo".
			            Path child = dir.resolve(filename);
			            if (!Files.probeContentType(child).equals("text/plain")) {
			                System.err.format("New file '%s'" +
			                    " is not a plain text file.%n", filename);
			                continue;
			            }
			        } catch (IOException x) {
			            System.err.println(x);
			            continue;
			        }
	
			        // Email the file to the
			        //  specified email alias.
			        System.out.format("Emailing file %s%n", filename);
			        //Details left to reader....*/
			    }
	
			    // Reset the key -- this step is critical if you want to
			    // receive further watch events.  If the key is no longer valid,
			    // the directory is inaccessible so exit the loop.
			    boolean valid = key.reset();
			    if (!valid) {
			    	monitoring = false;
			        break;
			    }
			}
		
	}
	
	public static void main(String a[]) throws IOException{
		FileChangeWatcher fc = FileChangeWatcher.getInstance();
		fc.addObserver(new FileChangeObserver<WatchEvent<Path>>() {
			@Override
			public void update(WatchEvent<Path> event) {
				// TODO Auto-generated method stub
				System.out.println(event.context()  + " kind event: " + event.kind());
			}
		});
		fc.registerListener("D:\\updatecenterroot");
		fc.startMonitoringLoop();
	}

	
}
