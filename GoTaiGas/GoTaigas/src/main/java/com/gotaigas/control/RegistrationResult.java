package com.gotaigas.control;

public class RegistrationResult {

    public final static String EMAIL_MISSING = "email missing";
	public final static String PASSWORD_MISSING = "password missing"; 
	public final static String REGISTRATION_SUCCESSFULL = "ok";
    public final static String FIRSTNAME_MISSING = "fistname missing";
    public final static String LASTNAME_MISSING = "lastname missing";
    public final static String EMAIL_INVALID = "email invalid";
    public final static String PASSWORD_TOO_SHORT = "password too short";
	public final static String USER_EXISTS_ALREADY = "user exists already";
	public final static String SHOULD_BE_A_NUMBER = "should be a number";
    public final static String EMAIL_ALREADY_EXISTS = "mail";
	
	private boolean result;
	
	private String reason;

	public boolean getResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public String getReason() {
		return reason;
	}

	/**
	 * Setzen eines Grunds für die fehlerhafte Registrierung.
	 * Wie könnte man diese Methode sinnvoll erweitern? ToDo
	 *
	 * @param reason
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	

}
