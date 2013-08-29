package enkleobjekter;

public class Counter {

	// internal state
	private int counter;
	private int end;

	// public API
	public Counter(int start, int end) {
		this.counter = start;
		this.end = end;
	}

	public String toString() {
		return "[Counter counter=" + counter + " end=" + end + "]";
	}

	public int getCounter() {
		return counter;
	}

	public void count(int increment) {
		if (counter < end) {
			counter += increment;
		}
	}

	public void count() {
		count(1);
	}
}

// program class used for testing
class CounterProgram {
	
	private Counter counter;
	
	public void init() {
		counter = new Counter(1, 3);
	}
	
	public void run() {
		System.out.println(counter);
		counter.count();
		System.out.println(counter);
		counter.count();
		System.out.println(counter);
		counter.count();
		System.out.println(counter);
	}
	
	public static void main(String[] args) {
		CounterProgram counterProgram = new CounterProgram();
		counterProgram.init();
		counterProgram.run();
	}
}
