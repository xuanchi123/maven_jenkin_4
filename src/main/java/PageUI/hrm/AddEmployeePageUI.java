package PageUI.hrm;

public class AddEmployeePageUI {
	public static final String 	FIRSTNAME_TEXTBOX = "css=input[name='firstName']";
	public static final String 	MIDDLENAME_TEXTBOX = "css=input[name='middleName']";
	public static final String 	LASTNAME_TEXTBOX = "css=input[name='lastName']";
	public static final String 	EMPLOYEE_ID_TEXTBOX = "xpath=//label[text()='Employee Id']/parent::div/following-sibling::div/input";
	public static final String 	DYNAMIC_TEXTBOX_BY_LABEL = "xpath=//label[text()='%s']/parent::div/following-sibling::div/input"; //EmployeeID, Username, Password, Confirm Password 	
	public static final String 	CREATE_LOGIN_DETAILS_CHECKBOX = "xpath=//p[text()='Create Login Details']/parent::div//input";
	public static final String 	DYNAMIC_STATUS_RADIO_BY_STATUS = "xpath=//label[text()='%s']/input";
	public static final String 	SAVE_BUTTON = "css=button[type='submit']";
}
