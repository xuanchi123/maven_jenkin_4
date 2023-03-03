package pageObject.hrm.employee;

import org.openqa.selenium.WebDriver;

import PageUI.hrm.PersonalDetailPageUI;
import commons.BasePage;

public class PersonalDetailPO extends BasePage {
	WebDriver driver;
	
	public PersonalDetailPO(WebDriver driver) {
		this.driver = driver;
	}

	public void clickOnEmployeeImage() {
		clickToElement(driver, PersonalDetailPageUI.EMPLOYEE_AVATAR);
	}

	public void uploadAvatar(String avatarFileLocation) {
		sendKeyToElement(driver, PersonalDetailPageUI.UPLOAD_IMAGE_LINK, avatarFileLocation);
	}
	
	public boolean isAvatarUploadSuccessfullyAtChangeProfilePictureContent() {
		sleepInSecond(3);
		return isImageLoaded(driver, PersonalDetailPageUI.EMPLOYEE_IMAGE_AT_CHANGE_PROFILE_PICTURE);
	}

	public boolean isAvatarUploadSuccessfullyAtEmployeeImage() {
		sleepInSecond(3);
		return isImageLoaded(driver, PersonalDetailPageUI.EMPLOYEE_AVATAR);
	}

	public void clickToSmokerYesCheckBox() {
		clickToElementByJS(driver, PersonalDetailPageUI.SMOKER_YES_CHECKBOX);
	}
}
