package version;

import java.util.concurrent.LinkedBlockingQueue;

public class VersionFolderManager<T> {

	private LinkedBlockingQueue<T> queue = new  LinkedBlockingQueue<T>(); 
	private Object lock = new Object();
	private IElementProcessor<T> processor;
	
	public VersionFolderManager(IElementProcessor<T> processor){
		this.processor = processor;
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				startPeeker();
			}
		}).start();
	}
	
	public void addElement(T element){
		synchronized (lock) {
			queue.add(element);
			lock.notify();
		}
	}
	

	/**A loop that peeks an element from the queue an processes it
	 * 
	 */
	private void startPeeker(){
			T element = null;
			while(true){
				try{
					synchronized (lock) {
						while(queue.isEmpty())
							try {
								lock.wait();   //wait until there is element is available!!!
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							element = queue.poll();   //get an element from the queue
							System.out.println("poll an element from queue...");
							lock.notifyAll();
							
					}
					//process element
					System.out.println("staritng processing element....");
					processor.process(element);
				}catch(Exception e){
					System.out.println(e.getMessage());
				}
			}
	}
	
}
