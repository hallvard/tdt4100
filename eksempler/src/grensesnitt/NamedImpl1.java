package grensesnitt;

public class NamedImpl1 implements Named {

	private String givenName;
	private String familyName;
	
	public NamedImpl1(String givenName, String familyName) {
		setGivenName(givenName);
		setFamilyName(familyName);
	}
	
	public String toString() {
		return "[NamedImpl1 " + getGivenName() + " + " + getFamilyName() + " = " + getFullName() + "]";
	}
	
	// from Named interface

	public String getGivenName() {
		return givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public String getFullName() {
		return givenName + " " + familyName;
	}
}
