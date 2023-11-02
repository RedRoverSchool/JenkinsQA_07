package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.List;

public class FreestyleProject19Test extends BaseTest {
    @Test
    public void testCreateProject() {
        final String projectName = "FreestyleProject19Test";

        getDriver().findElement(By.xpath("//div[@id='tasks']/div[1]/span/a")).click();
        getDriver().findElement(By.xpath("//input [@id='name']")).sendKeys(projectName);
        getDriver().findElement(By
                .xpath("//li[@class='hudson_model_FreeStyleProject']"))
                .click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();
        getDriver().findElement(By
                        .xpath("//textarea[@name = 'description']"))
                .sendKeys(projectName + " Description");
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        getDriver().findElement(By.xpath("//a[@id='jenkins-home-link']")).click();
        List<WebElement> listItems = getDriver()
                .findElements(By
                        .xpath("//a[@class='jenkins-table__link model-link inside']"));

        Assert.assertTrue(listItems.stream().anyMatch(item -> item.getText().compareTo(projectName) == 0));
    }
}
