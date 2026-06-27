package pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DashboardPage extends BasePage{

	public DashboardPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath="//a[@title='My Account']") WebElement btnMyAccount;
	@FindBy(xpath="//a[normalize-space()='Login']") WebElement btnLogin;
	@FindBy(xpath="//a[normalize-space()='Register']") WebElement btnRegister;
	
	public void clickMyAccount() {
		btnMyAccount.click();
	}
	
	public void clickLogin() {
		btnLogin.click();
	}
	
	public void clickRegister() {
		btnRegister.click();
	}
}
