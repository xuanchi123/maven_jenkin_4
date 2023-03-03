
package employee;

import commons.BaseTest;
import commons.GlobalConstants;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObject.hrm.employee.*;
import utilities.DataHelper;
import utilities.Environment;

public class Level20_Employee_01_Add_Employee_Multiple_Evn_OwnerLib extends BaseTest{

//	private Object EmergencyContactsPage;

	//Code is incompleted - Environment isn't get successfully yet, thus the getBrowserDriver method in BasePage receive env name instead of env URL
	@Parameters({"browser"})
	@BeforeClass
	public void beforeClass(String browserName) {
		Environment environment = ConfigFactory.create(Environment.class);
		log.info("Pre-Condition_01: Open Browser: '" + browserName + "' with env '" + environment + "'");
		driver = getBrowserDriver(browserName, environment.appUrl());
		loginPage = PageGeneratorManager.getLoginPage(driver);
		
		String adminUser = "Admin";
		String adminPassword = "admin123";
		
		log.info("Pre-Condition_02: Login to the system as user: " + adminUser);
		dashboardPage = loginPage.loginToSystem(adminUser, adminPassword);
		
		for (int i = 0; i < 2; i++) {
			fullName[i] = dataHelper.getNameWithMiddle();
			
			String[] fullNameArr = fullName[i].split(" ");
			firstName[i] = fullNameArr[0];
			middleName[i] = fullNameArr[1];
			lastName[i] = fullNameArr[2];
			
			userName[i] = firstName[i] + lastName[i] + getRandomNumber();
			password[i] = dataHelper.getPassword() + "Ab1@";
		}
	
		employeeStatus = "Enabled";
		
		nickName = "NickNameA";
		driverLicense = "123-345";
		licenseExpiryDate = "2024-02-20";
		ssnNumber = "1234567";
		sinNumber = "89124324";
		nationality = "American";
		otherId = "11111";
		militaryService = "5 years";
		maritalStatus = "Married";
		dataOfBirth = "1970-04-05";
		bloodType = "B+";

		street1 = "123 ABC Dr";
		street2 = "456 DEf Dr";
		
		city = dataHelper.getCityAddress();
		state = dataHelper.getStateAddress();
		zipCode = dataHelper.getZipCodeAddress();
		countryValue = "United States";
//		homePhone = dataHelper.gethomePhone();
//		mobilePhone = dataHelper.getmobilePhone();
//		workPhone = dataHelper.gethomePhone();
		homePhone = "432-3245-4325";
		mobilePhone = "443-434-4235";
		workPhone = "543-2345-643";
		
		workEmail = userName[1] + "@hotmail.com";
		
		avatarFileLocation = GlobalConstants.UPLOAD_FILE + "IMG_0142.png";
	}
	
	@Test
	public void Employee_01_Add_Employee() {
		log.info("Add_Employee_01_Step_01: Click To 'PIM' menu");
		dashboardPage.clickToMenuByText(driver, "PIM");
		employeeListPage = PageGeneratorManager.getEmployeeListPage(driver);
		
		log.info("Add_Employee_01_Step_03: Click on 'Add' button");
		addEmployeePage = employeeListPage.clickOnAddButton();
		
		log.info("Add_Employee_01_Step_04: Input value " + firstName + " to 'First Name' textbox");
		addEmployeePage.inputToFirstNameTextbox(firstName[0]);
		
		log.info("Add_Employee_01_Step_05: Input value " + middleName + " to 'Middle Name' textbox");
		addEmployeePage.inputToMiddleNameTextbox(middleName[0]);
		
		log.info("Add_Employee_01_Step_05: Input value " + lastName + " to 'Last Name' textbox");
		addEmployeePage.inputToLastNameTextbox(lastName[0]);
		
		log.info("Add_Employee_01_Step_06: Get 'Employee Id' value");
		employeeId = addEmployeePage.getEmployeeID();
		
		log.info("Add_Employee_01_Step_07: Enable 'Create Login Details' option");
		addEmployeePage.enableCreateLoginDetails();
		
		log.info("Add_Employee_01_Step_08: Input value " + userName + " to 'Username' textbox");
		addEmployeePage.inputToDynamicTextboxByLabel(userName[0], "Username");
		
		log.info("Add_Employee_01_Step_07: Select Employee Status as '" + employeeStatus +"'");
		addEmployeePage.selectEmployeeStatus(employeeStatus);
		
		log.info("Add_Employee_01_Step_07: Input value to 'Password' textbox");
		addEmployeePage.inputToDynamicTextboxByLabel(password[0], "Password");
		
		log.info("Add_Employee_01_Step_07: Input value to 'Confirm Password' textbox");
		addEmployeePage.inputToDynamicTextboxByLabel(password[0], "Confirm Password");
		
		log.info("Add_Employee_01_Step_08: Click on 'Save' button");
		addEmployeePage.clickOnSaveButton();
		
		log.info("Add_Employee_01_Step_09: Verify that 'Successfully Saved' Message displays");
		Assert.assertTrue(addEmployeePage.isSuccessfullSaveMessageDisplays(driver, "Successfully Saved"));

		
		log.info("Add_Employee_01_Step_09: Click to Menu 'PIM'");
		addEmployeePage.clickToMenuByText(driver, "PIM");
		employeeListPage = PageGeneratorManager.getEmployeeListPage(driver);
		
		log.info("Add_Employee_01_Step_10: Input value " + firstName + " into 'Employee Name' textbox");
		employeeListPage.inputToDynamicTextboxByLabel(firstName[0], "Employee Name");
		
		log.info("Add_Employee_01_Step_11: Input value " + employeeId + "into 'Employee Id' textbox");
		employeeListPage.inputToDynamicTextboxByLabel(employeeId, "Employee Id");
		
		log.info("Add_Employee_01_Step_12: Click on 'Search' button");
		employeeListPage.clickOnSearchButton();
		
		log.info("Add_Employee_01_Step_13: Verify the newly created Employee displays in the table");
		Assert.assertTrue(employeeListPage.isEmployeeWithIDFirstMiddleLastNameDisplay(employeeId, firstName[0], middleName[0], lastName[0]));		
	}
	
//	@Test
	public void Employee_02_Upload_Avatar() {
		log.info("Add_Employee_02_Step_01: Click on Employee to view the Employee Detail");
		personalDetailPage = employeeListPage.clickOnEmployeeWithIdFirstMiddleLastName(employeeId, firstName[0], middleName[0], lastName[0]);
		
		log.info("Add_Employee_02_Step_02: Click on Employee Image");
		personalDetailPage.clickOnEmployeeImage();
		
		log.info("Add_Employee_02_Step_03: Upload a new avatar");
		personalDetailPage.uploadAvatar(avatarFileLocation);
		
		log.info("Add_Employee_02_Step_04: Verify that avatar is uploaded successfully");
		Assert.assertTrue(personalDetailPage.isAvatarUploadSuccessfullyAtChangeProfilePictureContent());
		
		log.info("Add_Employee_02_Step_05: Click to 'Save' button on 'Change Profile Picture' section");
		personalDetailPage.clickToSaveButtonBySection(driver, "Change Profile Picture");
		
		log.info("Add_Employee_02_Step_06: Verify that 'Successfully Updated' Message displays");
		Assert.assertTrue(personalDetailPage.isSuccessfullSaveMessageDisplays(driver, "Successfully Updated"));
		
		
//		log.info("Add_Employee_02_Step_07: Verify that avatar is uploaded successfully");
//		Assert.assertTrue(personalDetailPage.isAvatarUploadSuccessfullyAtEmployeeImage());
	}
	
//	@Test
	public void Employee_03_Edit_PersonalDetailPage() {
		log.info("Edit_Employee_02_ContactDetailPage_Step_01: Click to Menu 'Personal Details'");
		personalDetailPage.clickToTabByText(driver, "Personal Details");
		
		log.info("Edit_Employee_01_PersonalDetailPage_Step_02: input value: " + firstName[1] + "to 'First Name' textbox");
		personalDetailPage.inputValueToTextboxByPlaceHolder(driver, firstName[1], "First Name");
		
		log.info("Edit_Employee_01_PersonalDetailPage_Step_03: input value: " + middleName[1] + "to 'Middle Name' textbox");
		personalDetailPage.inputValueToTextboxByPlaceHolder(driver, middleName[1], "Middle Name");
		
		log.info("Edit_Employee_01_PersonalDetailPage_Step_04: input value: " + lastName[1] + "to 'Last Name' textbox");
		personalDetailPage.inputValueToTextboxByPlaceHolder(driver, lastName[1], "Last Name");
		
		log.info("Edit_Employee_01_PersonalDetailPage_Step_05: input value: " + nickName + "to 'Nickname' textbox");
		personalDetailPage.inputValueToTextboxByLabel(driver, nickName, "Nickname");
		
		log.info("Edit_Employee_01_PersonalDetailPage_Step_06: input value: " + otherId + "to 'Other Id' textbox");
		personalDetailPage.inputValueToTextboxByLabel(driver, otherId, "Other Id");
		
		log.info("Edit_Employee_01_PersonalDetailPage_Step_07: input value: " + driverLicense + "to 'License Number' textbox");
		personalDetailPage.inputValueToTextboxByLabel(driver, driverLicense, "License Number");
		
		log.info("Edit_Employee_01_PersonalDetailPage_Step_08: input value: " + licenseExpiryDate + "to 'License Expiry Date' calendar");
		personalDetailPage.inputValueToTextboxByLabel(driver, licenseExpiryDate, "License Expiry Date");
		
		log.info("Edit_Employee_01_PersonalDetailPage_Step_05: input value: " + ssnNumber + "to 'SSN Number' textbox");
		personalDetailPage.inputValueToTextboxByLabel(driver, ssnNumber, "SSN Number");
		
		log.info("Edit_Employee_01_PersonalDetailPage_Step_06: input value: " + sinNumber + "to 'SIN Number' textbox");
		personalDetailPage.inputValueToTextboxByLabel(driver, sinNumber, "SIN Number");
		
		log.info("Edit_Employee_01_PersonalDetailPage_Step_09: Select value: " + nationality + "Nationality' dropdown");
		personalDetailPage.selectValueForDropDownByDropDownName(driver, nationality, "Nationality");
		
		log.info("Edit_Employee_01_PersonalDetailPage_Step_10: Select value: " + maritalStatus + "Marital Status' dropdown");
		personalDetailPage.selectValueForDropDownByDropDownName(driver, maritalStatus, "Marital Status");
		
		log.info("Edit_Employee_01_PersonalDetailPage_Step_11: input value: " + dataOfBirth + "to 'Date of Birth' calendar");
		personalDetailPage.inputValueToTextboxByLabel(driver, dataOfBirth, "Date of Birth");
		
		log.info("Edit_Employee_01_PersonalDetailPage_Step_12: Select option " + gender.MALE + "for 'Gender' riadio");
		personalDetailPage.clickToGenderRadioOptionByLabel(driver, gender.MALE.toString());
		
		log.info("Edit_Employee_01_PersonalDetailPage_Step_13: input value: " + ssnNumber + "to 'SSN Number' textbox");
		personalDetailPage.inputValueToTextboxByLabel(driver, ssnNumber, "SSN Number");
		
		log.info("Edit_Employee_01_PersonalDetailPage_Step_14: input value: " + sinNumber + "to 'SIN Number' textbox");
		personalDetailPage.inputValueToTextboxByLabel(driver, sinNumber, "SIN Number");
		
		log.info("Edit_Employee_01_PersonalDetailPage_Step_15: input value: " + militaryService + "to 'Military Service' textbox");
		personalDetailPage.inputValueToTextboxByLabel(driver, militaryService, "Military Service");
		
		log.info("Edit_Employee_01_PersonalDetailPage_Step_16: Check to 'Yes' checkbox for 'Smoker'");
		personalDetailPage.clickToSmokerYesCheckBox();
		
		log.info("Edit_Employee_01_PersonalDetailPage_Step_17: Click to 'Save' button on 'Personal Details' section");
		personalDetailPage.clickToSaveButtonBySection(driver, "Personal Details");
		
		log.info("Edit_Employee_01_PersonalDetailPage_Step_18: Verify that 'Successfully Updated' Message displays");
		Assert.assertTrue(personalDetailPage.isSuccessfullSaveMessageDisplays(driver, "Successfully Updated"));
		
		log.info("Edit_Employee_01_PersonalDetailPage_Step_19: Select value: " + bloodType + "Blood Type' dropdown");
		personalDetailPage.selectValueForDropDownByDropDownName(driver, bloodType, "Blood Type");
		
		log.info("Edit_Employee_01_PersonalDetailPage_Step_20: Click to 'Save' button on 'Custom Fields' section");
		personalDetailPage.clickToSaveButtonBySection(driver, "Custom Fields");
		
		log.info("Edit_Employee_01_PersonalDetailPage_Step_21: Verify that 'Successfully Updated' Message displays");
		Assert.assertTrue(personalDetailPage.isSuccessfullSaveMessageDisplays(driver,"Successfully Saved"));
	}
	
//	@Test
	public void Employee_04_Edit_ContactDetailsPage() {
		log.info("Edit_Employee_02_ContactDetailPage_Step_01: Click to Menu 'Contact Details'");
		personalDetailPage.clickToTabByText(driver, "Contact Details");
		contactDetailsPage = PageGeneratorManager.getContactDetailsPage(driver);
		
		log.info("Edit_Employee_02_ContactDetailPage_Step_02: input value: " + street1 + "to 'Street 1' textbox");
		personalDetailPage.inputValueToTextboxByLabel(driver, street1, "Street 1");
		
		log.info("Edit_Employee_02_ContactDetailPage_Step_03: input value: " + street2 + "to 'Street 2' textbox");
		personalDetailPage.inputValueToTextboxByLabel(driver, street2, "Street 2");
		
		log.info("Edit_Employee_02_ContactDetailPage_Step_04: input value: " + city + "to 'City' textbox");
		personalDetailPage.inputValueToTextboxByLabel(driver, city, "City");
		
		log.info("Edit_Employee_02_ContactDetailPage_Step_05: input value: " + state + "to 'State/Province' textbox");
		personalDetailPage.inputValueToTextboxByLabel(driver, state, "State/Province");
	
		log.info("Edit_Employee_02_ContactDetailPage_Step_06: input value: " + zipCode + "to 'Zip/Postal Code' textbox");
		personalDetailPage.inputValueToTextboxByLabel(driver, zipCode, "Zip/Postal Code");
		
		log.info("Edit_Employee_02_ContactDetailPage_Step_07: Select value: " + countryValue + "Country' dropdown");
		personalDetailPage.selectValueForDropDownByDropDownName(driver, countryValue, "Country");
		
		log.info("Edit_Employee_02_ContactDetailPage_Step_08: input value: " + homePhone + "to 'Home' textbox");
		personalDetailPage.inputValueToTextboxByLabel(driver, homePhone, "Home");
		
		log.info("Edit_Employee_02_ContactDetailPage_Step_09: input value: " + mobilePhone + "to 'Mobile' textbox");
		personalDetailPage.inputValueToTextboxByLabel(driver, mobilePhone, "Mobile");
		
		log.info("Edit_Employee_02_ContactDetailPage_Step_10: input value: " + workPhone + "to 'Work' textbox");
		personalDetailPage.inputValueToTextboxByLabel(driver, workPhone, "Work");

		log.info("Edit_Employee_02_ContactDetailPage_Step_11: input value: " + workEmail + "to 'Work Email' textbox");
		personalDetailPage.inputValueToTextboxByLabel(driver, workEmail, "Work Email");
		
		log.info("Edit_Employee_02_ContactDetailPage_Step_12: Click to 'Save' button on 'Contact Details' section");
		personalDetailPage.clickToSaveButtonBySection(driver, "Contact Details");
		
		log.info("Edit_Employee_02_ContactDetailPage_Step_13: Verify that 'Successfully Updated' Message displays");
		Assert.assertTrue(personalDetailPage.isSuccessfullSaveMessageDisplays(driver,"Successfully Updated"));
	}

	WebDriver driver;
	
	LoginPO loginPage;
	DashboardPO dashboardPage;
	EmployeeListPO employeeListPage;
	AddEmployeePO addEmployeePage;
	PersonalDetailPO personalDetailPage;
	ContactDetailsPO contactDetailsPage;
	
	String[] fullName = {"",""};
	String[] firstName = {"",""};
	String[] middleName = {"",""};
	String[] lastName = {"",""};
	String[] userName = {"",""};
	String[] password = {"",""};

	String employeeId, employeeStatus, avatarFileLocation, nickName, otherId, militaryService, driverLicense, licenseExpiryDate, ssnNumber, sinNumber, nationality, maritalStatus, dataOfBirth, bloodType;
	String street1, street2, city, state, zipCode, countryValue, homePhone, mobilePhone, workPhone, workEmail;
	
	enum gender{MALE, FEMALE};
	
	DataHelper dataHelper = new DataHelper();

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeBrowserDriver();
	}
}
