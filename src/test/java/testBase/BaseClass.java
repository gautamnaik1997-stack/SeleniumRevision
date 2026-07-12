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
import org.openqa.selenium.firefox.FirefoxDriver;
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
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless=new");
		
		String browser = System.getProperty("browser", br);
		if(browser == null || browser.trim().isEmpty()) {
			browser = "Edge";
		}
		
		String executionType = System.getProperty("executionType",p.getProperty("executiontype"));
		String environment = System.getProperty("environment", "QA");
		String configFile  = System.getProperty("user.dir") + "\\src\\test\\resources\\config\\" + environment.toLowerCase() + ".properties";
		p=new Properties();
		FileReader file = new FileReader(configFile);
		p.load(file);
		
		if(executionType.equalsIgnoreCase("remote")) {
			String huburl = "http://localhost:4444";
			DesiredCapabilities capabilities = new DesiredCapabilities();
			switch(os){
			case "Windows": capabilities.setPlatform(Platform.WIN11);break;
			case "Mac": capabilities.setPlatform(Platform.MAC);break;
			case "linux": capabilities.setPlatform(Platform.LINUX);break;
			default: System.out.println("invaid platform");return;
			}
			
			switch(browser) {
			case "Chrome": capabilities.setBrowserName("chrome"); break;
			case "Edge": capabilities.setBrowserName("MicrosoftEdge"); break;
			case "Firefox": capabilities.setBrowserName("firefox"); break;
			default: System.out.println("invalid browser"); return;
			}
			
			driver.set(new RemoteWebDriver(new URL(huburl), capabilities));
		}
		
		if(executionType.equalsIgnoreCase("local")) {
		switch(browser) {
		case "Chrome": driver.set(new ChromeDriver(options));break;
		case "Edge" : driver.set(new EdgeDriver());break;
		case "Firefox" : driver.set(new FirefoxDriver());break;
		default: System.out.println("Invaid browser name");return;
		}}
		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		getDriver().get(p.getProperty("appURL"));
	}
	
	@AfterClass(alwaysRun=true)
	public void teardown() {
		getDriver().quit();
		driver.remove();
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
