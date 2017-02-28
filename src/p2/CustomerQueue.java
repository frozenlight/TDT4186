import java.util.*;
/**
 * This class implements a queue of customers as a circular buffer.
 */
public class CustomerQueue {
	/**
	 * Creates a new customer queue. Make sure to save these variables in the class.
	 * @param queueLength	The maximum length of the queue.
	 * @param gui			A reference to the GUI interface.
	 */
	private int queueLength;
	private Gui gui;
	private Queue<Integer> indexQueue = new LinkedList();
	private Customer[] customerArray;
	
    public CustomerQueue(int queueLength, Gui gui) {
		// Incomplete
    	this.queueLength = queueLength;
    	this.gui = gui;
    	this.customerArray = new Customer[queueLength];
	}

	public synchronized void add(Customer customer) {
		int index = getFirstAvaiableSeat();
		customerArray[index] = customer;
		indexQueue.add(index);
		gui.fillLoungeChair(index, customer);
	}

	public synchronized Customer next() {
		int index = indexQueue.poll();
		Customer customer = customerArray[index];
		customerArray[index] = null;
		gui.emptyLoungeChair(index);
		return customer;
	}
	
	public boolean isEmpty() {
		return getSize() == 0;
	}
	
	public boolean isFull() {
		return getSize() == this.queueLength;
	}

	public int getSize() {
		return (int) Arrays.stream(customerArray).filter((a) -> a != null).count();
	}
	
	private int getFirstAvaiableSeat() {
		for (int i = 0; i < customerArray.length; i++) {
			if (customerArray[i] == null) {
				return i;
			}
		}
		return queueLength + 1;
	}
	// Add more methods as needed
}
