package grensesnitt;

public class NamedProgram {
	
	private Named named1, named2;
	
	public void init() {
		named1 = new NamedImpl1("Ola", "Nordmann");
		named2 = new NamedImpl2("Ola", "Nordmann");
	}
	
	public void run() {
		System.out.println(named1);
		System.out.println(named2);
		named1.setGivenName("Espen");
		named2.setGivenName("Espen");
		System.out.println(named1);
		System.out.println(named2);
		named1.setFamilyName("Askeladd");
		named2.setFamilyName("Askeladd");
		System.out.println(named1);
		System.out.println(named2);
	}
	
	public static void main(String[] args) {
		NamedProgram namedProgram = new NamedProgram();
		namedProgram.init();
		namedProgram.run();
	}
}
