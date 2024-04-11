package cosc202.andie;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple wall-clock timer designed for collecting data on experiments in
 * COSC201
 * 
 * @author Michael Albert
 */
public class Timer {

  private static final long MILLION = 1000000;
  private long startTime = -1;
  private ArrayList<Long> times = new ArrayList<>();
  private boolean running = false;

  /**
   * Constructs a timer (no initialisation required)
   */
  public Timer() {
  }

  /**
   * Start the timer. Note that this throws a runtime exception if the timer
   * is already running.
   * 
   */
  public void start() {
    if (running) {
      throw new RuntimeException("Attempted to start a running timer");
    }

    running = true;
    startTime = System.nanoTime();
  }

  /**
   * Stop the timer. The time taken is returned and also added to the internal
   * list of times. Note that this throws a runtime exception if the timer is
   * not running.
   * 
   * @return The time taken (in nanoseconds) since the last start().
   */
  public long stop() {
    if (!running) {
      throw new RuntimeException("Attempted to stop a stopped timer");
    }
    running = false;
    long time = System.nanoTime() - startTime;
    times.add(time);
    return time;
  }

  /**
   * Return the time taken for the last run of this timer.
   * 
   * @return Time (in nanoseconds) for the last run of this timer.
   */
  public long getTime() {
    if (times.isEmpty()) {
      throw new RuntimeException("No times recorded in this timer");
    }
    return times.get(times.size() - 1);
  }

  /**
   * Return the times recorded for all runs of this timer.
   * 
   * @return A list view of the times recorded by this timer.
   */
  public List<Long> getTimes() {
    return new ArrayList<>(times);
  }

  /**
   * Compute and return the total time recorded for all runs of this timer.
   * 
   * @return The total time recorded by this time (in nanoseconds).
   */
  public long getTotalTime() {
    long result = 0;
    for (long time : times)
      result += time;
    return result;
  }

  /**
   * Reset the timer. That is, stop it and clear the list of times recorded.
   * 
   */
  public void reset() {
    times.clear();
    running = false;
  }

  /**
   * Return the time taken for the last run of this timer in milliseconds.
   * 
   * @return Time (in milliseconds) for the last run of this timer.
   */
  public long getTimeInMs() {
    return getTime()/MILLION;
  }

}
