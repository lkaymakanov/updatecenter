package net.is_bg.session;

import java.util.Set;
import java.util.concurrent.TimeUnit;


/***
 * Intended to refresh sessions periodically!!!
 * @author lubo
 *
 */
class SessionRefresher implements Runnable{
	
	private SessionRegister sessionRegister;
	private long delayMIllis;
	private long cycles = 1;
	
	public SessionRefresher(SessionRegister sessionRegister, long delay, TimeUnit unit){
		this.sessionRegister = sessionRegister;
		this.delayMIllis = (unit.toMillis(delay));
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			//refresh the sessions
			try{
				System.out.println("SessionRefresher cycles =  " + cycles++);
				Set<String> keys = sessionRegister.getSessionIds();
				for(String k : keys){ sessionRegister.invalidate(k); }
				//pause 
				Thread.sleep(delayMIllis);
			}catch(Exception e){
				System.out.println(e.getCause());
				e.printStackTrace();
			}finally{
				
			}
		}
	}
	
	/**
	 * Starts the refresher of the register!!!!
	 * @param sessionRegister
	 * @param delay
	 * @param unit
	 */
	static void startSessionRefresher(SessionRegister sessionRegister,  long delay, TimeUnit unit){
		Thread t = new  Thread(new SessionRefresher(sessionRegister, delay, unit));
		t.setDaemon(true);
		t.start();
	}
	
}
