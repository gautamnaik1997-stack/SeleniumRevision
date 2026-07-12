package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseClass {

private static ThreadLocal <WebDriver> driver = new ThreadLocal<>();
public Logger logger;
public Properties p;
	
public static WebDriver getDriver() {
	return driver.get();
}
	@Parameters({"OS", "Browser"})
	@BeforeClass(alwaysRun=true)
	public void setup(String os, String br) throws IOException {
		logger = LogManager.getLogger(this.getClass());
		String environment = System.getProperty("environment", "QA");
		String configFile  = System.getProperty("user.dir") + "\\src\\test\\resources\\config\\" + environment.toLowerCase() + ".properties";
		p=new Properties();
		FileReader file = new FileReader(configFile);
		p.load(file);
		String browser = System.getProperty("browser", br);
		String executionType = System.getProperty("executionType",p.getProperty("executiontype"));
		boolean headless = Boolean.parseBoolean(System.getProperty("headless", "false"));
		
		ChromeOptions chromeOptions = new ChromeOptions();
		EdgeOptions edgeOptions = new EdgeOptions();
		FirefoxOptions firefoxOptions = new FirefoxOptions();
		
		if (headless==true) {
		    chromeOptions.addArguments("--headless=new");
		    chromeOptions.addArguments("--window-size=1920,1080");
		    chromeOptions.addArguments("--disable-dev-shm-usage");
		    chromeOptions.addArguments("--no-sandbox");
		    
		    edgeOptions.addArguments("--headless=new");
		    edgeOptions.addArguments("--window-size=1920,1080");
		    edgeOptions.addArguments("--disable-dev-shm-usage");
		    edgeOptions.addArguments("--no-sandbox");
		    
		    firefoxOptions.addArguments("--headless");
		    firefoxOptions.addArguments("--width=1920");
		    firefoxOptions.addArguments("--height=1080");
		}
		
		if(browser == null || browser.trim().isEmpty()) {
			browser = "Edge";
		}
		
		if (executionType.equalsIgnoreCase("remote")) {
		    String hubUrl = "http://localhost:4444";
		    switch (browser) {
		    case "Chrome": chromeOptions.setPlatformName(os);
		    driver.set(new RemoteWebDriver(new URL(hubUrl),chromeOptions));
		    break;
		    
		    case "Edge": edgeOptions.setPlatformName(os);
            driver.set(new RemoteWebDriver(new URL(hubUrl),edgeOptions));
            break;
            
		    case "Firefox": firefoxOptions.setPlatformName(os);
            driver.set(new RemoteWebDriver(new URL(hubUrl),firefoxOptions));
		    break;

		    default: throw new IllegalArgumentException("Invalid Browser : " + browser);
		    }
		}
		
		if(executionType.equalsIgnoreCase("local")) {
		switch(browser) {
		case "Chrome": driver.set(new ChromeDriver(chromeOptions));break;
		case "Edge" : driver.set(new EdgeDriver(edgeOptions));break;
		case "Firefox" : driver.set(new FirefoxDriver(firefoxOptions));break;
		default: System.out.println("Invaid browser name");return;
		}}
		
		if (getDriver() == null) {
		    throw new RuntimeException(
		        "WebDriver was not initialized. Browser = " + browser +
		        ", ExecutionType = " + executionType
		    );
		}
		
		getDriver().manage().deleteAllCookies();
		if (headless==false) {
		    getDriver().manage().window().maximize();
		}
		getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		getDriver().get(p.getProperty("appUrl"));
	}
	
	@AfterClass(alwaysRun=true)
	public void teardown() {
		if(getDriver()!=null){
		getDriver().quit();
		driver.remove();
		}
	}
	
	public static String captureScreen(String tname) throws IOException {
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.hh.mm.ss").format(new Date());
		String imagePath = System.getProperty("user.dir") + "\\screenshots\\" + tname + "_" + timeStamp + ".png";
		TakesScreenshot ts = (TakesScreenshot) getDriver();
		File source = ts.getScreenshotAs(OutputType.FILE);
		File Target = new File(imagePath);
		FileUtils.copyFile(source, Target);
		return imagePath;
	}
	
	@SuppressWarnings("deprecation")
	public String RandomString() {
		String generatedstring = RandomStringUtils.randomAlphabetic(6);
		return generatedstring;	
	}
	
	@SuppressWarnings("deprecation")
	public String RandomEmailString() {
		String generatedstring = RandomStringUtils.randomAlphanumeric(5);
		String domain = "gmail.com";
		String email = generatedstring+"@"+domain;
		return email;
	}
	
	@SuppressWarnings("deprecation")
	public String randomTelephoneNumber() {
		String generatedstring = RandomStringUtils.randomNumeric(10);
		return generatedstring;
	}
}
