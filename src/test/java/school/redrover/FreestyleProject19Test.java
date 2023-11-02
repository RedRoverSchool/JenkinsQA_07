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
    public void createProjectTest() {
        final String projectName = "FreestyleProject19Test";
        final WebDriver driver = getDriver();

        //Click the New Item
        driver.findElement(By.xpath("//*[@id=\"tasks\"]/div[1]/span/a")).click();

        //Enter the name item in the required field
        driver.findElement(By.xpath("//*[@id=\"name\"]")).sendKeys(projectName);

        //Click Freestyle project option
        driver.findElement(By
                .xpath("//*[@id=\"j-add-item-type-standalone-projects\"]/ul/li[1]"))
                .click();

        //Click the Ok button
        driver.findElement(By.xpath("//*[@id=\"ok-button\"]")).click();

        //Enter the description in the same name field
        driver.findElement(By
                        .xpath("//*[@id=\"main-panel\"]/form/div[1]/div[2]/div/div[2]/textarea"))
                .sendKeys(projectName + " Description");

        //Click the save button
        driver.findElement(By.xpath("//*[@id=\"bottom-sticker\"]/div/button[1]")).click();

        //Go to the main page of Jenkins
        driver.findElement(By.xpath("//*[@id=\"jenkins-name-icon\"]")).click();

        List<WebElement> listItems = driver
                .findElements(By
                        .xpath("//*[@class=\"jenkins-table__link model-link inside\"]"));

        //Make sure that the project has been created
        Assert.assertTrue(listItems.stream().anyMatch(item -> item.getText().compareTo(projectName) == 0));

    }
}
