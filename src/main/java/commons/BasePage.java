package commons;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import PageUI.hrm.NavigatorAndCommonElementPageUI;
//import pageObject.hrm.employee.EmployeeListPO;
//import pageObject.hrm.employee.PageGeneratorManager;

public class BasePage {
	private long shortTimeout = GlobalConstants.SHORT_TIMEOUT;
	private long longTimeout = GlobalConstants.LONG_TIMEOUT;
	private JavascriptExecutor jsExecutor;
	
	public static BasePage getBasePageObject() {
		return new BasePage();
	}

	public void openPageURL(WebDriver driver, String url) {
		driver.get(url);
	}

	protected String getPageTitle(WebDriver driver) {
		return driver.getTitle();
	}

	protected String getPageURL(WebDriver driver) {
		return driver.getCurrentUrl();
	}

	protected String getPageSource(WebDriver driver) {
		return driver.getPageSource();
	}

	protected void backToPage(WebDriver driver) {
		driver.navigate().back();
	}

	protected void forwardToPage(WebDriver driver) {
		driver.navigate().forward();
	}

	public void refreshCurrentPage(WebDriver driver) {
		driver.navigate().refresh();
	}

	protected Alert waitForAlertPresence(WebDriver driver) {
		WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
		return explicitWait.until(ExpectedConditions.alertIsPresent());
	}

	protected void acceptAlert(WebDriver driver) {
		Alert alert = waitForAlertPresence(driver);
		alert.accept();
	}

	protected void cancelAlert(WebDriver driver) {
		Alert alert = waitForAlertPresence(driver);
		alert.dismiss();
	}

	protected String getTextAlert(WebDriver driver) {
		Alert alert = waitForAlertPresence(driver);
		return alert.getText();
	}

	protected void sendKeyToAlert(WebDriver driver, String textValue) {
		Alert alert = waitForAlertPresence(driver);
		alert.sendKeys(textValue);
	}

	protected void switchWindowByID(WebDriver driver, String parentWindowID) {
		Set<String> allWindowIDs = driver.getWindowHandles();
		for (String id : allWindowIDs) {
			if (!id.equals(parentWindowID)) {
				driver.switchTo().window(id);
				break;
			}
		}
	}

	protected void switchWindowByTitle(WebDriver driver, String title) {
		Set<String> allWindowIDs = driver.getWindowHandles();
		for (String id : allWindowIDs) {
			driver.switchTo().window(id);
			if (driver.getTitle().equals(title)) {
				break;
			}
		}
	}

	protected void closeAllTabWithoutParent(WebDriver driver, String parentWindowID) {
		Set<String> allWindowIDs = driver.getWindowHandles();
		for (String id : allWindowIDs) {
			if (!id.equals(parentWindowID)) {
				driver.switchTo().window(id);
				driver.close();
			}
			driver.switchTo().window(parentWindowID);
		}
	}

	protected By getByLocator(String locatorType) {
		By by = null;
		String locatorTypeLowercase = locatorType.toLowerCase();

		if (locatorTypeLowercase.startsWith("xpath=")) {
			by = By.xpath(locatorType.substring(6));
		} else if (locatorTypeLowercase.startsWith("id=")) {
			by = By.id(locatorType.substring(3));
		} else if (locatorTypeLowercase.startsWith("class=")) {
			by = By.className(locatorType.substring(6));
		} else if (locatorTypeLowercase.startsWith("name=")) {
			by = By.name(locatorType.substring(5));
		} else if (locatorTypeLowercase.startsWith("css=")) {
			by = By.cssSelector(locatorType.substring(4));
		} else {
			throw new RuntimeException("Locator type is not supported!");
		}
		return by;
	}

	protected WebElement getWebElement(WebDriver driver, String locatorType) {
		return driver.findElement(getByLocator(locatorType));
	}

	private String getDynamicXpath(String locatorType, String... dynamicValues) {
		String locatorTypeLowserCase = locatorType.toLowerCase();
		if (locatorTypeLowserCase.startsWith("xpath")) {
			locatorType = String.format(locatorType, (Object[]) dynamicValues);
		}
		return locatorType;
	}

	protected List<WebElement> getListWebElements(WebDriver driver, String locatorType) {
		return driver.findElements(getByLocator(locatorType));
	}

	protected void clickToElement(WebDriver driver, String locatorType, String... dynamicValues) {
		waitForElementClickable(driver, getDynamicXpath(locatorType, dynamicValues));
		getWebElement(driver, getDynamicXpath(locatorType, dynamicValues)).click();
	}
	
	protected void sendKeyToElement(WebDriver driver, String locatorType, String textValue, String... dynamicValues) {
		waitForElementVisible(driver, getDynamicXpath(locatorType, dynamicValues));
		WebElement element = getWebElement(driver, getDynamicXpath(locatorType, dynamicValues));
		try {
			element.clear();
		} catch (Exception e) {
			e.printStackTrace();
		}
		element.sendKeys(textValue);
	}

	protected void selectItemInDefaultDropdown(WebDriver driver, String locatorType, String textValue,
			String... dynamicValues) {
		waitForElementClickable(driver, getDynamicXpath(locatorType, dynamicValues));
		Select select = new Select(getWebElement(driver, getDynamicXpath(locatorType, dynamicValues)));
		select.selectByVisibleText(textValue);
	}

	protected String getSelectedItemInDefaultDropdown(WebDriver driver, String locatorType, String... dynamicValues) {
		waitForElementVisible(driver, getDynamicXpath(locatorType, dynamicValues));
		Select select = new Select(getWebElement(driver, getDynamicXpath(locatorType, dynamicValues)));
		return select.getFirstSelectedOption().getText();
	}

	protected boolean isDropdownMultiple(WebDriver driver, String locatorType, String... dynamicValues) {
		Select select = new Select(getWebElement(driver, getDynamicXpath(locatorType, dynamicValues)));
		return select.isMultiple();
	}

	protected void selectItemInCustomDropdown(WebDriver driver, String parentLocatorType, String childItemLocatorType,
			String expectedItem, String ...dynamicParentLocatorValues) {
		getWebElement(driver, getDynamicXpath(parentLocatorType, dynamicParentLocatorValues)).click();
		sleepInSecond(1);

		WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
		List<WebElement> allItems = explicitWait
				.until(ExpectedConditions.presenceOfAllElementsLocatedBy(getByLocator(childItemLocatorType)));

		for (WebElement item : allItems) {
			if (item.getText().trim().equals(expectedItem)) {
				jsExecutor = (JavascriptExecutor) driver;
				jsExecutor.executeScript("arguments[0].scrollIntoView(false);", item);
				sleepInSecond(1);
				item.click();
				break;
			}
		}
	}

	protected String getElementAttribute(WebDriver driver, String locatorType, String attributeName,
			String... dynamicValues) {
		return getWebElement(driver, getDynamicXpath(locatorType, dynamicValues)).getAttribute(attributeName);
	}

	protected String getTextInElement(WebDriver driver, String locatorType, String... dynamicValues) {
		waitForElementVisible(driver, locatorType, dynamicValues);
		return getWebElement(driver, getDynamicXpath(locatorType, dynamicValues)).getText();
	}

	protected String getElementCssValue(WebDriver driver, String locatorType, String propertyName,
			String... dynamicValues) {
		return getWebElement(driver, getDynamicXpath(locatorType, dynamicValues)).getCssValue(propertyName);
	}

	protected String getHexaColorFromRGBA(String rgbaValue) {
		return Color.fromString(rgbaValue).asHex();
	}

	protected int getElementsSize(WebDriver driver, String locatorType, String... dynamicValues) {
		return getListWebElements(driver, getDynamicXpath(locatorType, dynamicValues)).size();
	}

	protected void checkToDefaultCheckboxRadio(WebDriver driver, String locatorType, String... dynamicValues) {
		waitForElementClickable(driver, locatorType, dynamicValues);
		WebElement element = getWebElement(driver, getDynamicXpath(locatorType, dynamicValues));
		if (!element.isSelected()) {
			element.click();
		}
	}

	protected void uncheckToDefaultCheckbox(WebDriver driver, String locatorType, String... dynamicValues) {
		waitForElementClickable(driver, getDynamicXpath(locatorType, dynamicValues));
		WebElement element = getWebElement(driver, getDynamicXpath(locatorType, dynamicValues));
		if (element.isSelected()) {
			element.click();
		}
	}

	protected boolean isElementDisplay(WebDriver driver, String locatorType, String... dynamicValues) {
		waitForElementVisible(driver, getDynamicXpath(locatorType, dynamicValues));
		boolean status = true;
		
		try {
			WebElement element = getWebElement(driver, getDynamicXpath(locatorType, dynamicValues));
			if (element.isDisplayed()) {
				return status;				
			}
		} catch (Exception e) {
			status = false;
		}
		
		return status;
	}
	
	protected boolean isElementUndisplay(WebDriver driver, String locatorType, String... dynamicValues) {
		
		overrideImplicitTimeout(driver, shortTimeout);
		List<WebElement> elements = getListWebElements(driver, getDynamicXpath(locatorType, dynamicValues));
		overrideImplicitTimeout(driver, longTimeout);
		if(elements.size()==0) {
			return true;
		} else if(elements.size()>0 && !elements.get(0).isDisplayed()) {
			return true;
		} else return false;
	}

	private void overrideImplicitTimeout(WebDriver driver, long timeout) {
		driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
	}

	protected boolean isElementSelected(WebDriver driver, String locatorType, String... dynamicValues) {
		waitForElementVisible(driver, getDynamicXpath(locatorType, dynamicValues));
		return getWebElement(driver, getDynamicXpath(locatorType, dynamicValues)).isSelected();
	}

	protected boolean isElementEnabled(WebDriver driver, String locatorType, String... dynamicValues) {
		return getWebElement(driver, getDynamicXpath(locatorType, dynamicValues)).isEnabled();
	}

	protected void switchToFrameIframe(WebDriver driver, String locatorType) {
		driver.switchTo().frame(getWebElement(driver, locatorType));
	}

	protected void switchToDefaultContent(WebDriver driver) {
		driver.switchTo().defaultContent();
	}

	protected void hoverMouseToElement(WebDriver driver, String locatorType) {
		Actions action = new Actions(driver);
		action.moveToElement(getWebElement(driver, locatorType)).perform();
	}
	  
	  public void sendKeyboardToElement(WebDriver driver, String locatorType, Keys key, String... dynamicValues) { 
		  Actions action = new Actions(driver);
		  action.sendKeys(getWebElement(driver, getDynamicXpath(locatorType, dynamicValues)), key).perform(); }

	protected void scrollToBottomPage(WebDriver driver) {
		jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("window.scrollBy(0,document.body.scrollHeight)");
	}

	protected void highlightElement(WebDriver driver, String locatorType, String... dynamicValues) {
		jsExecutor = (JavascriptExecutor) driver;
		WebElement element = getWebElement(driver, getDynamicXpath(locatorType, dynamicValues));
		String originalStyle = element.getAttribute("style");
		jsExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style",
				"border: 2px solid red; border-style: dashed;");
		sleepInSecond(1);
		jsExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style",
				originalStyle);
	}

	protected void clickToElementByJS(WebDriver driver, String locatorType, String... dynamicValues) {
		jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].click();",
				getWebElement(driver, getDynamicXpath(locatorType, dynamicValues)));
	}

	protected void scrollToElement(WebDriver driver, String locatorType) {
		jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].scrollIntoView(true);", getWebElement(driver, locatorType));
	}

	protected void removeAttributeInDOM(WebDriver driver, String locatorType, String attributeRemove) {
		jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].removeAttribute('" + attributeRemove + "');",
				getWebElement(driver, locatorType));
	}

	protected boolean areJQueryAndJSLoadedSuccess(WebDriver driver) {
		WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
		jsExecutor = (JavascriptExecutor) driver;

		ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				try {
					return ((Long) jsExecutor.executeScript("return jQuery.active") == 0);
				} catch (Exception e) {
					return true;
				}
			}
		};

		ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return jsExecutor.executeScript("return document.readyState").toString().equals("complete");
			}
		};

		return explicitWait.until(jQueryLoad) && explicitWait.until(jsLoad);
	}
	
	protected String  getTextboxValueByJS(WebDriver driver, String locatorType) {
		jsExecutor = (JavascriptExecutor) driver;
		return (String) jsExecutor.executeScript("return arguments[0]._value;", getWebElement(driver, locatorType));
	}

	protected String getElementValidationMessage(WebDriver driver, String locatorType) {
		jsExecutor = (JavascriptExecutor) driver;
		return (String) jsExecutor.executeScript("return arguments[0].validationMessage;",
				getWebElement(driver, locatorType));
	}

	public String getElementValueByJSXPath(WebDriver driver, String XpathLocator) {
		jsExecutor = (JavascriptExecutor) driver;
		return (String) jsExecutor.executeScript("$(document.evaluate(\"" + XpathLocator + "\", document, null, XpathResult.FIRST_ORDERED_NOTE_TYPE, null).singleNodeValue).val()");
	}
	
	protected boolean isImageLoaded(WebDriver driver, String locatorType) {
		jsExecutor = (JavascriptExecutor) driver;
		boolean status = (boolean) jsExecutor.executeScript(
				"return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0",
				getWebElement(driver, locatorType));
		if (status) {
			return true;
		} else {
			return false;
		}
	}
	
	protected boolean isImageLoaded(WebDriver driver, String locatorType, String ...dynamicValues) {
		jsExecutor = (JavascriptExecutor) driver;
		boolean status = false; 
		
		status = (boolean) jsExecutor.executeScript(
				"return arguments[0].complete && typeof arguments[0].naturalWidth != 'undefined' && arguments[0].naturalWidth > 0",
				getWebElement(driver, getDynamicXpath(locatorType, dynamicValues)));
		return status;
	}

	protected void waitForElementVisible(WebDriver driver, String locatorType, String... dynamicValues) {
		WebDriverWait wait = new WebDriverWait(driver, longTimeout);
		try {
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(getByLocator(getDynamicXpath(locatorType, dynamicValues))));
		} catch (Exception e) {
			System.out.println("Element doesn't exist");
		} 
	}

	protected List<WebElement> waitForAllElementsVisible(WebDriver driver, String locatorType) {
		WebDriverWait wait = new WebDriverWait(driver, longTimeout);
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(getByLocator(locatorType)));
	}

	protected WebElement waitForElementClickable(WebDriver driver, String locatorType, String... dynamicValues) {
		WebDriverWait wait = new WebDriverWait(driver, longTimeout);
		return wait.until(
				ExpectedConditions.elementToBeClickable(getByLocator(getDynamicXpath(locatorType, dynamicValues))));
	}

	protected boolean waitForElementInvisible(WebDriver driver, String locatorType, String... dynamicValues) {
		WebDriverWait wait = new WebDriverWait(driver, longTimeout);
		return wait.until(ExpectedConditions
				.invisibilityOfElementLocated(getByLocator(getDynamicXpath(locatorType, dynamicValues))));
	}

	protected boolean waitForAllElementsInvisible(WebDriver driver, String locatorType) {
		WebDriverWait wait = new WebDriverWait(driver, longTimeout);
		return wait.until(ExpectedConditions.invisibilityOfAllElements(getListWebElements(driver, locatorType)));
	}

	public void sleepInSecond(long second) {
		try {
			Thread.sleep(second*1000);;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Set<Cookie> getAllCookies(WebDriver driver) {
		return driver.manage().getCookies();
	}
	public void setAllCookies(WebDriver driver, Set<Cookie> cookies) {
		for (Cookie cookie : cookies) {
			System.out.println("Current cookie is: " + cookie);
			driver.manage().addCookie(cookie);
		}
	}
	
	//Method for HRM project
	public void clickToMenuByText(WebDriver driver, String menuName) {
		clickToElement(driver, NavigatorAndCommonElementPageUI.DYNAMIC_LEFT_HAND_MENU_BY_TEXT, menuName);
	}
	
	public void clickToTabByText(WebDriver driver, String tabName) {
		clickToElement(driver, NavigatorAndCommonElementPageUI.DYNAMIC_TAB_BY_TEXT, tabName);
		sleepInSecond(1);
	}
	
	public boolean isElementChecked(WebDriver driver, String locatorType) {
		jsExecutor = (JavascriptExecutor) driver;
		return (boolean) jsExecutor.executeScript("return arguments[0].checked;",
				getWebElement(driver, locatorType));
	}
	
	public void inputValueToTextboxByPlaceHolder(WebDriver driver, String value, String placeHolder) {
		sendKeyToElement(driver, NavigatorAndCommonElementPageUI.DYNAMIC_TEXTBOX_BY_PLACEHOLDER, value, placeHolder);
		
	}

	public void inputValueToTextboxByLabel(WebDriver driver, String value, String label) {
		sendKeyToElement(driver, NavigatorAndCommonElementPageUI.DYNAMIC_TEXTBOX_BY_LABEL, value, label);
		
	}

	public void selectValueForDropDownByDropDownName(WebDriver driver, String dropdownItem, String dropdownName) {
		selectItemInCustomDropdown(driver, NavigatorAndCommonElementPageUI.DYNAMIC_DROPDOWN_ARROW_BY_LABEL, NavigatorAndCommonElementPageUI.DROPDOWN_OPTIONS_VALUE, dropdownItem, dropdownName);
	}

	public void clickToGenderRadioOptionByLabel(WebDriver driver, String gender) {
		if(gender.toLowerCase().equals("male")) {
			clickToElementByJS(driver, NavigatorAndCommonElementPageUI.DYNAMIC_RADIO_OPTION_BY_INDEX, "1");
		}
		else clickToElementByJS(driver, NavigatorAndCommonElementPageUI.DYNAMIC_RADIO_OPTION_BY_INDEX, "2");
	}

	
	public void clickToSaveButtonBySection(WebDriver driver, String sectionName) {
		sleepInSecond(1);
		clickToElement(driver, NavigatorAndCommonElementPageUI.DYNAMIC_SAVE_BUTTON_BY_SECTION, sectionName);
	}

	public boolean isSuccessfullSaveMessageDisplays(WebDriver driver, String successfullyMessage) {
		return isElementDisplay(driver, NavigatorAndCommonElementPageUI.SUCCESSFULL_SAVE_MESSAGE, successfullyMessage);
	}
}
