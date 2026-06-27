package testCase;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObject.AccountPage;
import pageObject.DashboardPage;
import pageObject.RegistrationPage;
import testBase.BaseClass;

public class TC002_Register extends BaseClass {
	
	@Test(groups= {"Regression"})
	public void verifyRegistration() {
		logger.info("***** Starting TC002_Register *****");
		try {
			DashboardPage dp = new DashboardPage(getDriver());
			dp.clickMyAccount();
			dp.clickRegister();
			
			RegistrationPage rp = new RegistrationPage(getDriver());
			rp.setFirstName(RandomString());
			rp.setLastName(RandomString());
			rp.setEmail(RandomEmailString());
			rp.setTelephone(randomTelephoneNumber());
			String password = RandomString();
			rp.setPassword(password);
			rp.setConfirmPassword(password);
			rp.chkPrivacyPolicy();
			rp.clickContinue();
			
			AccountPage ap = new AccountPage(getDriver());
			boolean actualStatus = ap.confmsgStatus();
			boolean expectedStatus = true;
			Assert.assertEquals(actualStatus, expectedStatus, "Status Mismatch");
		}catch(Exception e) {
			logger.debug("TestCase Execution Failed", e);
			Assert.fail(e.getMessage());
		}finally {
			logger.info("***** Ending TC002_Register *****");
		}
	}
	
}
