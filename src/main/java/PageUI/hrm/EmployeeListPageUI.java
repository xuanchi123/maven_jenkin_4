package PageUI.hrm;

public class EmployeeListPageUI {
	public static final String 	ADD_BUTTON = "xpath=//button[text()=' Add ']";
	public static final String 	SEARCH_BUTTON = "css=button[type='submit']";
	public static final String 	DYNAMIC_TEXTBOX_BY_LABEL = "xpath=//label[text()='%s']/parent::div/following-sibling::div//input";
	public static final String 	DYNAMIC_ROW_BY_ID_FIRST_MIDDLE_LASTNAME = "xpath=//div[text()='%s']/parent::div/following-sibling::div/div[text()='%s']/parent::div/following-sibling::div/div[text()='%s']";
}
