package pageObject.hrm.employee;

import org.openqa.selenium.WebDriver;

import PageUI.hrm.LoginPageUI;
import commons.BasePage;

public class LoginPO extends BasePage {
	WebDriver driver;
	
	public LoginPO(WebDriver driver) {
		this.driver = driver;
	}

	public DashboardPO loginToSystem(String userName, String password) {
		sendKeyToElement(driver, LoginPageUI.USERNAME_TEXTBOX, userName);
		sendKeyToElement(driver, LoginPageUI.PASSWORD_TEXTBOX, password);
		clickToElement(driver, LoginPageUI.LOGIN_BUTTON);
		
		return PageGeneratorManager.getDashboardPage(driver);
	}
}
