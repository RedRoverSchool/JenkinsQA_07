package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FreestyleProject5Test extends BaseTest {
    private void createProject(String projectName) {
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.xpath("//input[@id='name']")).sendKeys(projectName);
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();
    }

    @Test
    public void deleteProject() {
        final String projectName1 = "Dead project";
        createProject(projectName1);
        getDriver().findElement(By.xpath("//*[@id=\"breadcrumbs\"]/li[1]/a")).click();
        getDriver().findElement(By.xpath("//span[text()='" + projectName1 + "']")).click();
        getDriver().findElement(By.linkText("Delete Project")).click();
        getDriver().switchTo().alert().accept();
        getDriver().findElement(By.xpath("//*[@id=\"breadcrumbs\"]/li[1]/a")).click();

        boolean deleted = isElementDeleted(By.xpath("//span[text()='" + projectName1 + "']"));
        assert (deleted);
    }

    private boolean isElementDeleted(By locator) {
        try {
            WebElement element = getDriver().findElement(locator);
            return !element.isDisplayed();
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return true;
        }
    }
}

