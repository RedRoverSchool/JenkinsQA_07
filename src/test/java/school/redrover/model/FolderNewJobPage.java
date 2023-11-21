package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import school.redrover.model.base.BasePage;

public class FolderNewJobPage extends BasePage {
    public FolderNewJobPage(WebDriver driver) {
        super(driver);
    }

    public FolderNewJobPage typeJobName(String name) {
        getDriver().findElement(By.xpath("//input[@name='name']")).sendKeys(name);

        return this;
    }

    public FolderNewJobPage clickFreestyleProject() {
        getDriver().findElement(By.xpath("//li[@class='hudson_model_FreeStyleProject']")).click();

        return this;
    }

    public FolderConfigurationPage clickSubmit() {
        getDriver().findElement(By.id("ok-button")).click();

        return new FolderConfigurationPage(getDriver());
    }
}