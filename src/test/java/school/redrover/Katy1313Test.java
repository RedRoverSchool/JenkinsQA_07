package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.JenkinsUtils;

public class Katy1313Test extends BaseTest {

    @Test
    public void testCreateFreestyleProject() throws InterruptedException {
        final String expectedProjectName = "Project Freestyle Project";

        JenkinsUtils.login(getDriver());

        WebElement newItem = getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']"));
        newItem.click();

        WebElement inputField = getDriver().findElement(By.name("name"));
        inputField.sendKeys("Freestyle Project");

        WebElement freestyleProjectButton = getDriver().findElement(By.xpath("//li//span[text()='Freestyle project']"));
        freestyleProjectButton.click();

        WebElement okButton = getDriver().findElement(By.id("ok-button"));
        okButton.click();

        WebElement saveButton = getDriver().findElement(By.name("Submit"));
        saveButton.click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//*[@id='main-panel']/h1")).getText(), expectedProjectName);

    }
}
