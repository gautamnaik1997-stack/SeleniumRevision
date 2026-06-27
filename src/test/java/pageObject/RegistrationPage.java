package pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RegistrationPage extends BasePage{

	public RegistrationPage(WebDriver driver) {
		super(driver);
	}
	
	@FindBy(xpath="//input[@id='input-firstname']") WebElement txtFirstName;
	@FindBy(xpath="//input[@id='input-lastname']") WebElement txtLastName;
	@FindBy(xpath="//input[@id='input-email']") WebElement txtEmail;
	@FindBy(xpath="//input[@id='input-telephone']") WebElement txtTelephone;
	@FindBy(xpath="//input[@id='input-password']") WebElement txtPassword;
	@FindBy(xpath="//input[@id='input-confirm']") WebElement txtConfirmPassword;
	@FindBy(xpath="//input[@name='agree']") WebElement chkedPrivacyPolicy;
	@FindBy(xpath="//input[@value='Continue']") WebElement btnContinue;

public void setFirstName(String fname) {
	txtFirstName.sendKeys(fname);
}

public void setLastName(String lname) {
	txtLastName.sendKeys(lname);
}

public void setEmail(String email) {
	txtEmail.sendKeys(email);
}

public void setTelephone(String telephone) {
	txtTelephone.sendKeys(telephone);
}

public void setPassword(String password) {
	txtPassword.sendKeys(password);
}

public void setConfirmPassword(String confpwd) {
	txtConfirmPassword.sendKeys(confpwd);
}

public void chkPrivacyPolicy() {
	chkedPrivacyPolicy.click();
}

public void clickContinue() {
	btnContinue.click();
}

}
