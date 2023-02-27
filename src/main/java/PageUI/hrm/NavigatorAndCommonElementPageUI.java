package PageUI.hrm;

public class NavigatorAndCommonElementPageUI {
	public static final String DYNAMIC_LEFT_HAND_MENU_BY_TEXT = "xpath=//span[text()='%s']";
	public static final String DYNAMIC_TAB_BY_TEXT = "xpath=//a[text()='%s']";
	public static final String DYNAMIC_SAVE_BUTTON_BY_SECTION = "xpath=//h6[text()='%s']/parent::div//button[text()=' Save ']";
	
	public static final String SUCCESSFULL_SAVE_MESSAGE = "xpath=//div[@class='oxd-toast-content oxd-toast-content--success']//p[text()='%s']";
	
	public static final String DYNAMIC_TEXTBOX_BY_LABEL = "xpath=//label[contains(text(),'%s')]/parent::div/following-sibling::div//input";
	public static final String DYNAMIC_TEXTBOX_BY_PLACEHOLDER = "xpath=//input[@placeholder='%s']";
	public static final String DYNAMIC_DROPDOWN_ARROW_BY_LABEL = "xpath=//label[contains(text(),'%s')]/parent::div/following-sibling::div//div[@class='oxd-select-text--after']/i";
//	public static final String DROPDOWN_OPTIONS = "xpath=//div[@role='option']";
	public static final String DROPDOWN_OPTIONS_VALUE = "xpath=//div[@role='option']/span";
	public static final String DYNAMIC_RADIO_OPTION_BY_INDEX = "xpath=//input[@type='radio' and @value='%s']";

}
