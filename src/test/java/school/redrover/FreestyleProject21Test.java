package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FreestyleProject21Test extends BaseTest {

    private final static String PROJECT_NAME = "NewFreestyleProject";

    private void createFreestyleProject(String nameOfProject, boolean goToHomePage) {
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.xpath("//input[@id='name']")).sendKeys(nameOfProject);
        getDriver().findElement(By.xpath("//span[text()='Freestyle project']")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();

        if (goToHomePage) {
            getDriver().findElement(By.xpath("//img[@id='jenkins-name-icon']")).click();
        }
    }

    @Test
    public void testVisibilityHelDescriptionQuietPeriod(){

        createFreestyleProject(PROJECT_NAME, true);

        getDriver().findElement(By.xpath("//span[normalize-space()='" + PROJECT_NAME + "']")).click();
        getDriver().findElement(By.xpath("//span/a[@href='/job/" + PROJECT_NAME + "/configure']")).click();
        getDriver().findElement(By.cssSelector(".config-table > .jenkins-form-item .jenkins-button")).click();
        getDriver().findElement(By.xpath("//a[@title='Help for feature: Quiet period']")).click();

        Assert.assertTrue(getDriver().findElement(By.
                xpath("//div[@class='tbody dropdownList-container']//div[@class='help']//div")).isDisplayed(),
                "Help description of Quiet Period is not displayed!");
    }
}