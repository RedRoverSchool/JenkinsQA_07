package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class MultibranchPipelineRenamePage extends BasePage {

    @FindBy (name = "newName")
    private WebElement inputName;

    public MultibranchPipelineRenamePage(WebDriver driver) {
        super(driver);
    }

    public MultibranchPipelineRenamePage typeNewName(String name) {
        inputName.clear();
        inputName.sendKeys(name);

        return this;
    }

    public MultibranchPipelineDetailsPage clickSubmit() {
        getDriver().findElement(By.name("Submit")).click();

        return new MultibranchPipelineDetailsPage(getDriver());
    }
}
