package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import java.util.List;

public class FreestyleProject5Test extends BaseTest {
    private void createProject() {
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.id("name")).sendKeys("Dead project");
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();
    }
    @Test
    public void deleteProject() {
        final String projectName1 = "Dead project";
        createProject();

        getDriver().findElement(By.linkText("Dashboard")).click();
        getDriver().findElement(By.xpath("//span[text()='" + projectName1 + "']")).click();
        getDriver().findElement(By.linkText("Delete Project")).click();
        getDriver().switchTo().alert().accept();
        getDriver().findElement(By.linkText("Dashboard")).click();

        boolean deleted = isElementDeleted(By.xpath("//span[text()='" + projectName1 + "']"));
        assert (deleted);
    }
    private boolean isElementDeleted(By locator) {
        List<WebElement> elements = getDriver().findElements(locator);
        return elements.isEmpty() || !elements.get(0).isDisplayed();
    }
}

