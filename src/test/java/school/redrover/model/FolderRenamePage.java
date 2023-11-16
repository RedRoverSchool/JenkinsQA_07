package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class FolderRenamePage extends BasePage {

    @FindBy(xpath = "//input[@name='newName']")
    private WebElement inputName;

    public FolderRenamePage(WebDriver driver) {
        super(driver);
    }

    public FolderRenamePage typeNewName(String name) {
        inputName.clear();
        inputName.sendKeys(name);

        return this;
    }

    public FolderDetailsPage clickSubmit() {
        getDriver().findElement(By.name("Submit")).click();

        return new FolderDetailsPage(getDriver());
    }
}
