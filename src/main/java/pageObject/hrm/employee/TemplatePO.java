package pageObject.hrm.employee;

import org.openqa.selenium.WebDriver;

import commons.BasePage;

public class TemplatePO extends BasePage {
	WebDriver driver;
	
	public TemplatePO(WebDriver driver) {
		this.driver = driver;
	}
}
