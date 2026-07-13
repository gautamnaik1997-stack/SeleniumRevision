package utils;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;

public class ExtentReportsManager implements ITestListener {
	
	public ExtentSparkReporter sparkReporter;
	public ExtentReports extent;
	public ExtentTest extents;
	public static ThreadLocal<ExtentTest> tests = new ThreadLocal<>();
	public String repName;
	
	public void onStart(ITestContext testcontext) {
		repName = "report_" + new SimpleDateFormat("yyyy.MM.dd.hh.mm.ss").format(new Date()) + ".html";
		
		sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir") + "\\reports\\" + repName);
		sparkReporter.config().setDocumentTitle("Selenium Revision");
		sparkReporter.config().setReportName("Functional Testing");
		sparkReporter.config().setTheme(Theme.DARK);
		
		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);
		extent.setSystemInfo("Project", "Selenium Revision");
		extent.setSystemInfo("Module", "Admin");
		extent.setSystemInfo("sub module", "Customer");
		
		String className = testcontext.getName();
		extent.setSystemInfo("TestCase", className);
		
		String operatingsystem = testcontext.getCurrentXmlTest().getParameter("OS");
		extent.setSystemInfo("Operating System", operatingsystem);
		
		String browser = testcontext.getCurrentXmlTest().getParameter("Browser");
		extent.setSystemInfo("Browser", browser);
		
		List<String> groups = testcontext.getCurrentXmlTest().getIncludedGroups();
		if(!groups.isEmpty()) {
			extent.setSystemInfo("Groups", groups.toString());
		}	
	}
	
	public void onTestSuccess(ITestResult result) {
		extents= extent.createTest(result.getName());
		tests.set(extents);
		tests.get().assignCategory(result.getMethod().getGroups());
		tests.get().log(Status.PASS, result.getName() + " got successfully exected");
	}
	
	public void onTestFailure(ITestResult result) {
		extents= extent.createTest(result.getName());
		tests.set(extents);
		tests.get().assignCategory(result.getMethod().getGroups());
		tests.get().log(Status.FAIL, result.getThrowable() + " got failed");
		try {
			String filepath = BaseClass.captureScreen(result.getName());
			tests.get().addScreenCaptureFromPath(filepath);
		} catch (IOException e1) {
			e1.printStackTrace();
		}	
	}
	
	public void onTestSkipped(ITestResult result) {
		extents= extent.createTest(result.getName());
		tests.set(extents);
		tests.get().assignCategory(result.getMethod().getGroups());
		tests.get().log(Status.SKIP, result.getName() + " got skipped");
	}
	
	public void onFinish(ITestContext context) {
		extent.flush();
		
		String filepath = System.getProperty("user.dir") + "\\reports\\" + repName;
		File repfile = new File(filepath);
		
		/* try {
			Desktop.getDesktop().browse(repfile.toURI());
		} catch (IOException e) {
			e.printStackTrace();
		} */
		
		
	}
}
