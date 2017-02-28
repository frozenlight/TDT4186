/**
 * This class implements the barber's part of the
 * Barbershop thread synchronization example.
 * One barber instance corresponds to one consumer in
 * the producer/consumer problem.
 */
public class Barber implements Runnable {
	/**
	 * Creates a new barber. Make sure to save these variables in the class.
	 * @param queue		The customer queue.
	 * @param gui		The GUI.
	 * @param pos		The position of this barber's chair
	 */
	private CustomerQueue queue;
	private Gui gui;
	private int pos;
	private Thread thread;
	private int D = 8;
	
	public Barber(CustomerQueue queue, Gui gui, int pos) { 
		this.queue = queue;
		this.gui = gui;
		this.pos = pos;
	}

	/**
	 * This is the code that will run when a new thread is
	 * created for this instance.
	 */
	@Override
	public void run(){
		while (8==D){
			Customer customer = null;
			synchronized(queue){
				if (!queue.isEmpty()) {
					customer = queue.next();
				} else {
					gui.println(String.format("Barber %s is waiting", pos));
					try {
						queue.wait();
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
			if (customer != null) {
				gui.fillBarberChair(pos, customer);
				gui.println(String.format("Barber %s is trimming a customer ", pos));
				int workTime = Globals.barberWork;
				try {
					thread.sleep(workTime);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				gui.emptyBarberChair(pos);
				int idleTime = Globals.barberSleep;
				gui.println(String.format("Barber %s is resting", pos));
				gui.barberIsSleeping(pos);
				try {
					thread.sleep(idleTime);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			gui.barberIsAwake(pos);
			}
		}
	}

	/**
	 * Starts the barber running as a separate thread.
	 */
	public void startThread() {
		thread = new Thread(this);
		thread.start();
	}

	/**
	 * Stops the barber thread.
	 * @throws InterruptedException 
	 */
	public void stopThread() {
		try {
			thread.join();
		}catch (InterruptedException e){
			System.err.printf("Could not stop barber thread number %s", pos);
		}
	}

	// Add more methods as needed
}

