package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;
public class UserConfigurationPage extends BasePage {
    @FindBy(xpath ="//*[@id='people']/tbody/tr[2]/td[2]/a")
    private WebElement username;

    @FindBy(xpath = "//*[@id='tasks']/div[4]/span/a")
    private WebElement configPage;
    @FindBy(xpath = "//*[@id='main-panel']/form/div[1]/div[1]/div[2]/input")
    private WebElement userFull;
    @FindBy(xpath = "//*[@id='main-panel']/form/div[1]/div[1]/div[2]/input")
    private WebElement fullUser;
    @FindBy(xpath = "//*[@id='bottom-sticker']/div/button[1]")
    private WebElement saveButton;
    final String fullname = "User User";
    public UserConfigurationPage(WebDriver driver) {
        super(driver);
    }


    public UserConfigurationPage username(){
        username.click();
        return this;
    }
    public UserConfigurationPage configurationPage(){
       configPage.click();
       return this;
    }
    public UserConfigurationPage userFull(){
        userFull.clear();
        return this;
    }
    public UserConfigurationPage fullUser() {
        fullUser.sendKeys(fullname);
        return this;
    }
    public UserConfigurationPage saveButton(){
        saveButton.click();
        return new UserConfigurationPage(getDriver());
    }

}
