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
        final WebDriver driver = getDriver();

        driver.findElement(By.xpath("//*[@id=\"tasks\"]/div[1]/span/a")).click();
        driver.findElement(By.xpath("//*[@id=\"name\"]")).sendKeys(projectName);
        driver.findElement(By
                .xpath("//*[@id=\"j-add-item-type-standalone-projects\"]/ul/li[1]"))
                .click();
        driver.findElement(By.xpath("//*[@id=\"ok-button\"]")).click();
        driver.findElement(By
                        .xpath("//*[@id=\"main-panel\"]/form/div[1]/div[2]/div/div[2]/textarea"))
                .sendKeys(projectName + " Description");
        driver.findElement(By.xpath("//*[@id=\"bottom-sticker\"]/div/button[1]")).click();

        driver.findElement(By.xpath("//*[@id=\"jenkins-name-icon\"]")).click();
        List<WebElement> listItems = driver
                .findElements(By
                        .xpath("//*[@class=\"jenkins-table__link model-link inside\"]"));

        Assert.assertTrue(listItems.stream().anyMatch(item -> item.getText().compareTo(projectName) == 0));
    }
}
