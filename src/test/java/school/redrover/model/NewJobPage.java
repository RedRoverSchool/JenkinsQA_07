package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BasePage;

import java.util.List;

public class NewJobPage extends BasePage {
    public NewJobPage(WebDriver driver) {
        super(driver);
    }
    public FreestyleProjectConfigurePage createFreestyleProject(String projectName) {

        getDriver().findElement(By.id("name")).sendKeys(projectName);
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();

        return new FreestyleProjectConfigurePage(getDriver());
    }

}
