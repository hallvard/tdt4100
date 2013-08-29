package grensesnitt;

public class NamedImpl2 implements Named {

	private String fullName;
	
	public NamedImpl2(String givenName, String familyName) {
		setFullName(givenName, familyName);
	}
	
	public String toString() {
		return "[NamedImpl2 " + getGivenName() + " + " + getFamilyName() + " = " + getFullName() + "]";
	}
	
	// helper methods
	
	private void setFullName(String givenName, String familyName) {
		this.fullName = givenName + " " + familyName;
	}
	private String[] splitFullName() {
		return fullName.split(" ");
	}
	
	// from Named interface
	
	public String getGivenName() {
		return splitFullName()[0];
	}

	public void setGivenName(String givenName) {
		setFullName(givenName, getFamilyName());
	}

	public String getFamilyName() {
		return splitFullName()[1];
	}

	public void setFamilyName(String familyName) {
		setFullName(getGivenName(), familyName);
	}

	public String getFullName() {
		return fullName;
	}
}
