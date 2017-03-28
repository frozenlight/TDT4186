package p3.round_robin;

import java.util.LinkedList;

/**
 * This class implements functionality associated with
 * the I/O device of the simulated system.
 */
public class Io {
    private Process activeProcess = null;

    /**
     * Creates a new I/O device with the given parameters.
     * @param ioQueue		The I/O queue to be used.
     * @param avgIoTime		The average duration of an I/O operation.
     * @param statistics	A reference to the statistics collector.
     */

    private LinkedList<Process> ioQueue;
    private long avgIoTime;
    private Statistics statistics;

    public Io(LinkedList<Process> ioQueue, long avgIoTime, Statistics statistics) {
        this.ioQueue = ioQueue;
        this.avgIoTime = avgIoTime;
        this.statistics = statistics;

        // Incomplete
    }

    /**
     * Adds a process to the I/O queue, and initiates an I/O operation
     * if the device is free.
     * @param requestingProcess	The process to be added to the I/O queue.
     * @param clock				The time of the request.
     * @return					The event ending the I/O operation, or null
     *							if no operation was initiated.
     */
    public Event addIoRequest(Process requestingProcess, long clock) {
        // Incomplete
        ioQueue.addLast(requestingProcess);
        //startIoOperation initiates IO if the device is free, returns null if not
        return startIoOperation(clock);
    }

    /**
     * Starts a new I/O operation if the I/O device is free and there are
     * processes waiting to perform I/O.
     * @param clock		The global time.
     * @return			An event describing the end of the I/O operation that was started,
     *					or null	if no operation was initiated.
     */
    public Event startIoOperation(long clock) {
        // Incomplete
        if(activeProcess == null && !ioQueue.isEmpty()){
            activeProcess = ioQueue.removeFirst();
            activeProcess.resetIoTime();
            System.out.println("Process " + activeProcess.getProcessId() + " is in IO");
            //activeProcess.updateStatistics(statistics);
            //return new event using random time within avgIoTime
            return new Event(Event.END_IO, (long)(clock + Math.random()*avgIoTime));
        }
        return null;
    }

    /**
     * This method is called when a discrete amount of time has passed.
     * @param timePassed	The amount of time that has passed since the last call to this method.
     */
    public void timePassed(long timePassed) {
        statistics.ioQueueLengthTime += ioQueue.size()*timePassed;
        if (ioQueue.size() > statistics.ioQueueLargestLength) {
            statistics.ioQueueLargestLength = ioQueue.size();
        }
    }

    /**
     * Removes the process currently doing I/O from the I/O device.
     * @return	The process that was doing I/O, or null if no process was doing I/O.
     */
    public Process removeActiveProcess() {
        Process active = activeProcess;
        activeProcess = null;
        return active;
    }

    public Process getActiveProcess() {
        return activeProcess;
    }
}
