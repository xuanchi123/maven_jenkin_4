package commons;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeSuite;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {
	private WebDriver driver;
	
	protected final Log log;	

	@BeforeSuite
	public void deleteAllAllureReportFiles() {
		System.out.println("---------- START delete file in folder ----------");
		deleteAllFileInFolder(GlobalConstants.ALLURE_JSON);
		System.out.println("---------- END delete file in folder ----------");
	}

	public void deleteAllFileInFolder(String pathFolder) {
		try {
			File file = new File(pathFolder);
			File[] listOfFiles = file.listFiles();
			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].isFile()) {
					System.out.println(listOfFiles[i].getName());
					new File(listOfFiles[i].toString()).delete();
				}
			}
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
	}
	
	protected BaseTest() {
		log = LogFactory.getLog(getClass());
	}
	
	public WebDriver getDriver() {
		return this.driver;
	}

	protected WebDriver getBrowserDriver(String browserName, String serverName) {
		switch (browserName) {
			case "firefox":
				driver = WebDriverManager.firefoxdriver().create();
				break;
			case "firefoxheadless":
				FirefoxOptions ffOption = new FirefoxOptions();
				ffOption.addArguments("headless");
				ffOption.addArguments("window-size=1920x1080");
				driver = WebDriverManager.firefoxdriver().capabilities(ffOption).create();
				break;
			case "chrome":
				driver = WebDriverManager.chromedriver().create();
				break;
			case "chromeheadless":
				ChromeOptions chromeOption = new ChromeOptions();
				chromeOption.addArguments("headless");
				chromeOption.addArguments("window-size=1920x1080");
				driver = WebDriverManager.chromedriver().capabilities(chromeOption).create();
				break;
			case "edge":
				driver = WebDriverManager.edgedriver().create();
				break;
			default:
				throw new RuntimeException("Invalid driver");
		}
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(getBrowserURL(serverName));
		driver.manage().window().fullscreen();
		return driver;
	}


	protected WebDriver getBrowserDriverSauceLab(String browserName, String serverName, String envName, String osName) {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("browserName", browserName);
		capabilities.setCapability("browserVersion", "latest");

		capabilities.setCapability("platformName", osName);

		Map<String, Object> sauceOptions = new HashMap<>();
		if(osName.contains("Windows")){
			sauceOptions.put("screenResolution", "1920x1080");
		} else {
			sauceOptions.put("screenResolution", "1920x1440");
		}
		sauceOptions.put("build", "selenium-build-5ZQ96");
		capabilities.setCapability("sauce:options", sauceOptions);

		try{
			driver = new RemoteWebDriver(new URL(GlobalConstants.SAUCE_URL), capabilities);
		} catch (MalformedURLException e){
			e.printStackTrace();
		}

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(getBrowserURL(serverName));
		driver.manage().window().fullscreen();
		return driver;
	}

	String getBrowserURL(String serverName){
		String envURL = null;
		EnvironmentList environment = EnvironmentList.valueOf(serverName.toUpperCase());
		switch (environment){
			case DEV: envURL = "https://opensource-demo.orangehrmlive.com";
				System.out.println("Get URL for environment " + environment.toString());
				break;
			case QA: envURL = "https://opensource-demo.orangehrmlive.com";
				break;
			case STAGING: envURL = "https://opensource-demo.orangehrmlive.com";
				break;
			case PRODUCTION: envURL = "https://opensource-demo.orangehrmlive.com";
				break;
			default:
				System.out.println("Cannot get URL");
				break;
		}
		return envURL;
	}

	private boolean checkTrue(boolean condition) {
		boolean pass = true;
		try {
			Assert.assertTrue(condition);
			log.info(" -------------------------- PASSED -------------------------- ");	
		} catch (Throwable e) {
			log.info(" -------------------------- FAILED -------------------------- ");
			pass = false;

			// Add lỗi vào ReportNG
			VerificationFailures.getFailures().addFailureForTest(Reporter.getCurrentTestResult(), e);
			Reporter.getCurrentTestResult().setThrowable(e);
		}
		return pass;
	}

	protected boolean verifyTrue(boolean condition) {
		return checkTrue(condition);
	}

	private boolean checkFailed(boolean condition) {
		boolean pass = true;
		try {		
			Assert.assertFalse(condition);
			log.info(" -------------------------- PASSED -------------------------- ");	
		} catch (Throwable e) {
			log.info(" -------------------------- FAILED -------------------------- ");
			pass = false;
			VerificationFailures.getFailures().addFailureForTest(Reporter.getCurrentTestResult(), e);
			Reporter.getCurrentTestResult().setThrowable(e);
		}
		return pass;
	}

	protected boolean verifyFalse(boolean condition) {
		return checkFailed(condition);
	}

	private boolean checkEquals(Object actual, Object expected) {
		boolean pass = true;
		try {
			Assert.assertEquals(actual, expected);
			log.info(" -------------------------- PASSED -------------------------- ");
		} catch (Throwable e) {
			pass = false;
			log.info(" -------------------------- FAILED -------------------------- ");
			VerificationFailures.getFailures().addFailureForTest(Reporter.getCurrentTestResult(), e);
			Reporter.getCurrentTestResult().setThrowable(e);
		}
		return pass;
	}

	protected boolean verifyEquals(Object actual, Object expected) {
		return checkEquals(actual, expected);
	}

	protected void closeBrowserDriver() {
		String cmd = null;
		try {
			String osName = GlobalConstants.OS_NAME;
			log.info("OS name = " + osName);

			String driverInstanceName = driver.toString().toLowerCase();
			log.info("Driver instance name = " + driverInstanceName);

			String browserDriverName = null;

			if (driverInstanceName.contains("chrome")) {
				browserDriverName = "chromedriver";
			} else if (driverInstanceName.contains("internetexplorer")) {
				browserDriverName = "IEDriverServer";
			} else if (driverInstanceName.contains("firefox")) {
				browserDriverName = "geckodriver";
			} else if (driverInstanceName.contains("edge")) {
				browserDriverName = "msedgedriver";
			} else if (driverInstanceName.contains("opera")) {
				browserDriverName = "operadriver";
			} else {
				browserDriverName = "safaridriver";
			}

			if (osName.contains("window")) {
				cmd = "taskkill /F /FI \"IMAGENAME eq " + browserDriverName + "*\"";
			} else {
				cmd = "pkill " + browserDriverName;
			}

			if (driver != null) {
				driver.manage().deleteAllCookies();
				driver.quit();
			}
		} catch (Exception e) {
			log.info(e.getMessage());
		} finally {
			try {
				Process process = Runtime.getRuntime().exec(cmd);
				process.waitFor();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public int getRandomNumber() {
		Random random = new Random();
		return random.nextInt(99999);		
	}	
}
