package grensesnitt;

import junit.framework.TestCase;

public class NamedTest extends TestCase {

	private Named named1, named2;
	
	protected void setUp() {
		named1 = new NamedImpl1("Ola", "Nordmann");
		named2 = new NamedImpl2("Ola", "Nordmann");
	}
	
	public void testGetSetGivenName() {
		assertEquals("Ola", named1.getGivenName());
		assertEquals("Ola", named2.getGivenName());
		named1.setGivenName("Espen");
		named2.setGivenName("Espen");
		assertEquals("Espen", named1.getGivenName());
		assertEquals("Espen", named2.getGivenName());
	}
	
	public void testGetSetFamilyName() {
		assertEquals("Nordmann", named1.getFamilyName());
		assertEquals("Nordmann", named2.getFamilyName());
		named1.setFamilyName("Askeladd");
		named2.setFamilyName("Askeladd");
		assertEquals("Askeladd", named1.getFamilyName());
		assertEquals("Askeladd", named2.getFamilyName());
	}
}
