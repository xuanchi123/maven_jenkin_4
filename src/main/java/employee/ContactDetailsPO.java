package pageObject.hrm.employee;

import org.openqa.selenium.WebDriver;

import PageUI.hrm.EmployeeListPageUI;
import commons.BasePage;

public class ContactDetailsPO extends BasePage {
	WebDriver driver;
	
	public ContactDetailsPO(WebDriver driver) {
		this.driver = driver;
	}
}
