package enkleobjekter;

// program class used for testing
public class CounterProgram {
	
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
