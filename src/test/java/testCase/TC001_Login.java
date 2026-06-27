package testCase;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObject.AccountPage;
import pageObject.DashboardPage;
import pageObject.LoginPage;
import testBase.BaseClass;

public class TC001_Login extends BaseClass {
	
	//@Test(priority=1, dataProvider = "login", dataProviderClass = DataProviders.class)
	@Test(groups= {"Regression", "Sanity", "Master"})
	public void verifyLogin() {
		logger.info("***** Starting verifyLogin *****");
		try{
		DashboardPage dp = new DashboardPage(getDriver());
		dp.clickMyAccount();
		dp.clickLogin();
		
		LoginPage lp = new LoginPage(getDriver());
			lp.setUserName(p.getProperty("username"));
			lp.setPassword(p.getProperty("password"));
			lp.clickLogin();
			
		AccountPage ap = new AccountPage(getDriver());
		boolean actualStatus = ap.getAccountLabelStatus();
		boolean expectedStatus = true;
		ap.clickLogout();
		Assert.assertEquals(actualStatus, expectedStatus, "Status Mismatch");
		/*	if(actualStatus==expectedStatus) {
				if(ExpectedResult.equalsIgnoreCase("valid")) {
					Assert.assertTrue(true);
				}
				else {
					Assert.assertTrue(false);
				}
			}
			if(!actualStatus==expectedStatus) {
				if(ExpectedResult.equalsIgnoreCase("invalid")) {
					Assert.assertTrue(true);
				}
				else {
					Assert.assertTrue(false);
				}
			}*/
			
		}catch(Exception e) {
			logger.debug("TestCase Execution Failed", e);
			Assert.fail(e.getMessage());
		}finally {
			logger.info("***** Ending verifyLogin *****");
		}
		
	}
}
