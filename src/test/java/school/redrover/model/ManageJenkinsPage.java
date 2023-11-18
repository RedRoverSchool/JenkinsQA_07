package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class ManageJenkinsPage extends BasePage {

    @FindBy(xpath = "//a[@href='computer']")
    private WebElement nodeSection;

    public ManageJenkinsPage(WebDriver driver) {
        super(driver);
    }

    public UserDatabasePage goUserDatabasePage() {
        getDriver().findElement(By.xpath("//a[@href = 'securityRealm/']")).click();

        return new UserDatabasePage(getDriver());
    }

    public NodesListPage goNodesListPage() {
        nodeSection.click();

        return new NodesListPage(getDriver());
    }
}
