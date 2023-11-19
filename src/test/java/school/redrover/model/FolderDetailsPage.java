package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class FolderDetailsPage extends BasePage {

    @FindBy(linkText = "Configure")
    private WebElement configure;

    public FolderDetailsPage(WebDriver driver) {
        super(driver);
    }

    public FolderRenamePage clickRename() {
        getDriver().findElement(By.xpath("//a[contains(@href, '/confirm-rename')]")).click();

        return new FolderRenamePage(getDriver());
    }

    public ConfigurationPage clickConfigure() {
        configure.click();

        return new ConfigurationPage(getDriver());
    }

}
