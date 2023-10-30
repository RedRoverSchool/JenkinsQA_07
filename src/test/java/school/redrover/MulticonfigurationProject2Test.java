package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class MulticonfigurationProject2Test extends BaseTest {
    @Test
    public void testCreate () {
        final String ProjectName = "MulticonfigurationProject";

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@id='name']")).sendKeys("MulticonfigurationProject");
        getDriver().findElement(By.xpath("//li[@class='hudson_matrix_MatrixProject']")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();

        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
        getDriver().findElement(By.xpath("//a[@id='jenkins-home-link']")).click();

        getDriver().findElement(By.xpath("//td/a[@href='job/" + ProjectName + "/']")).click();
        Assert.assertEquals(
                getDriver().findElement(By.cssSelector(".matrix-project-headline.page-headline")).getText(),
                "Project " + ProjectName);

    }
}
