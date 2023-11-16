package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import school.redrover.model.base.BasePage;

public class ManageJenkinsPage extends BasePage {
    public ManageJenkinsPage(WebDriver driver) {
        super(driver);
    }

    public UserDatabasePage goUserDatabasePage() {
        getDriver().findElement(By.xpath("//a[@href = 'securityRealm/']")).click();

        return new UserDatabasePage(getDriver());
    }
}
