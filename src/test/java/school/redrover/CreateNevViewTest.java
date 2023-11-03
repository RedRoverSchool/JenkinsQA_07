package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;


public class CreateNevViewTest extends BaseTest {

    private void createFreestPro() {
        getDriver().findElement(By.xpath("//a [@href = '/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys("MyFirstFreestyleProject");
        getDriver().findElement(By.xpath("//img[@class='icon-freestyle-project icon-xlg']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//button[@formnovalidate = 'formNoValidate']")).click();
        getDriver().findElement(By.id("jenkins-home-link")).click();
    }

    @Test
    public void testCreateView() {
        createFreestPro();

        getDriver().findElement(By.xpath("//a[@aria-label='New View']")).click();
        getDriver().findElement(By.xpath("//input[@id='name']")).sendKeys("My view");
        getDriver().findElement(By.xpath("//label[@for='hudson.model.MyView']")).click();
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();

        String nameView = getDriver().findElement(By.xpath("//a[contains(text(), 'My view')]")).getText();

        Assert.assertEquals(nameView, "My view");

    }
}
