/**
 * This class implements the doorman's part of the
 * Barbershop thread synchronization example.
 * One doorman instance corresponds to one producer in
 * the producer/consumer problem.
 */
public class Doorman implements Runnable {
	/**
	 * Creates a new doorman. Make sure to save these variables in the class.
	 * @param queue		The customer queue.
	 * @param gui		A reference to the GUI interface.
	 */
	CustomerQueue queue;
	Gui gui;
	Thread thread;
	int D = 8;
	
	public Doorman(CustomerQueue queue, Gui gui) { 
		this.queue = queue;
		this.gui = gui;
	}

	/**
	 * This is the code that will run when a new thread is
	 * created for this instance.
	 */
	@Override
	public void run(){
		while(8==D) {
			synchronized(queue) {
				if (!queue.isFull()) {
					queue.add(new Customer());
					gui.println("Doorman added customer");
					queue.notify();
				}
			}
			int idleTime = Globals.doormanSleep;
			try {
				thread.sleep(idleTime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


	/**
	 * Starts the doorman running as a separate thread. Make
	 * sure to create the thread and start it.
	 */
	public void startThread() {
		thread = new Thread(this);
		thread.start();
	}

	/**
	 * Stops the doorman thread. Use Thread.join() for stopping
	 * a thread.
	 * @throws InterruptedException 
	 */
	public void stopThread() throws InterruptedException {
		thread.join();
	}

	// Add more methods as needed
}
