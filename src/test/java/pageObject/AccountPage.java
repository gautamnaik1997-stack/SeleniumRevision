package pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AccountPage extends BasePage {

	public AccountPage(WebDriver driver) {
		super(driver);
	}
	
	@FindBy(xpath="//h2[normalize-space()='My Account']") WebElement labelMyAccont;
	@FindBy(xpath="//h1[normalize-space()='Your Account Has Been Created!']") WebElement successMSG;
	@FindBy(xpath="//a[@class='list-group-item'][normalize-space()='Logout']") WebElement lnkLogout;
	
	public boolean getAccountLabelStatus() {
		boolean labelStatus = labelMyAccont.isDisplayed();
		return labelStatus;
	}
	
	public void clickLogout() {
		lnkLogout.click();
	}
	
	public boolean confmsgStatus() {
	boolean status=successMSG.isDisplayed();
	return status;
	}
}
