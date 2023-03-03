package pageObject.hrm.employee;

import org.openqa.selenium.WebDriver;

import PageUI.hrm.AddEmployeePageUI;
import commons.BasePage;

public class AddEmployeePO extends BasePage {
	WebDriver driver;
	
	public AddEmployeePO(WebDriver driver) {
		this.driver = driver;
	}

	public void inputToFirstNameTextbox(String firstName) {
		sendKeyToElement(driver, AddEmployeePageUI.FIRSTNAME_TEXTBOX, firstName);		
	}
	
	public void inputToMiddleNameTextbox(String lastName) {
		sendKeyToElement(driver, AddEmployeePageUI.MIDDLENAME_TEXTBOX, lastName);
	}

	public void inputToLastNameTextbox(String lastName) {
		sendKeyToElement(driver, AddEmployeePageUI.LASTNAME_TEXTBOX, lastName);
	}

	public String getEmployeeID() {
		return getTextboxValueByJS(driver, AddEmployeePageUI.EMPLOYEE_ID_TEXTBOX);
	}

	public void enableCreateLoginDetails() {
		if (!isElementChecked(driver, AddEmployeePageUI.CREATE_LOGIN_DETAILS_CHECKBOX)) {
			clickToElementByJS(driver, AddEmployeePageUI.CREATE_LOGIN_DETAILS_CHECKBOX);
		}
	}

	public void inputToDynamicTextboxByLabel(String userNameValue, String textboxLabel) {
		sendKeyToElement(driver, AddEmployeePageUI.DYNAMIC_TEXTBOX_BY_LABEL, userNameValue, textboxLabel);
	}

	public void selectEmployeeStatus(String employeeStatus) {
		// TODO Auto-generated method stub
		
	}

	public void clickOnSaveButton() {
		clickToElement(driver, AddEmployeePageUI.SAVE_BUTTON);
	}
}
