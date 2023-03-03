package pageObject.hrm.employee;

import org.openqa.selenium.WebDriver;

import PageUI.hrm.EmployeeListPageUI;
import commons.BasePage;

public class EmployeeListPO extends BasePage {
	WebDriver driver;
	
	public EmployeeListPO(WebDriver driver) {
		this.driver = driver;
	}

	public AddEmployeePO clickOnAddButton() {
		clickToElement(driver, EmployeeListPageUI.ADD_BUTTON);
		return PageGeneratorManager.getAddEmployeePage(driver);
	}
	
	public void inputToDynamicTextboxByLabel(String value, String label) {
		sendKeyToElement(driver, EmployeeListPageUI.DYNAMIC_TEXTBOX_BY_LABEL, value, label);
	}

	public void clickOnSearchButton() {
		clickToElement(driver, EmployeeListPageUI.SEARCH_BUTTON);
	}

	public boolean isEmployeeWithIDFirstMiddleLastNameDisplay(String employeeId, String employeeFirstName, String employeeMiddleName, String employeeLastName) {
		sleepInSecond(1);
		String employeeFirstAndMiddleName = employeeFirstName + " " + employeeMiddleName;
		return isElementDisplay(driver, EmployeeListPageUI.DYNAMIC_ROW_BY_ID_FIRST_MIDDLE_LASTNAME, employeeId, employeeFirstAndMiddleName, employeeLastName);
	}

	public PersonalDetailPO clickOnEmployeeWithIdFirstMiddleLastName(String employeeId, String employeeFirstName,
			String employeeMiddleName, String employeeLastName) {
		String employeeFirstAndMiddleName = employeeFirstName + " " + employeeMiddleName;
		clickToElement(driver, EmployeeListPageUI.DYNAMIC_ROW_BY_ID_FIRST_MIDDLE_LASTNAME, employeeId, employeeFirstAndMiddleName, employeeLastName);
		return PageGeneratorManager.getPersonalDetailPage(driver);
		
	}
}

