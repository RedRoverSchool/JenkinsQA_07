package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class MulticonfigurationProject7Test extends BaseTest {
    private final String PROJECT_NAME = "hero builds";
    @Test
    public void testCreateMulticonfiguratiobProject() {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//*[@class='jenkins-input']")).sendKeys(PROJECT_NAME);
        getDriver().findElement(By.xpath("//li[@class='hudson_matrix_MatrixProject']")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();
        getDriver().findElement(By.xpath("/html/body/div[2]/ol/li[1]/a")).click();

        //first assert — To see the created project on the Dashboard;
        Assert.assertEquals(getDriver().findElement
                (By.cssSelector("#job_hero\\ builds > td:nth-child(3) > a > span")).getText(), PROJECT_NAME);

        getDriver().findElement(By.xpath("//a[@class='jenkins-table__link model-link inside']")).click();

        //The last one assert — Navigate to the configuration page of the created project Multiconfiguration;
        Assert.assertEquals(getDriver().findElement
                (By.xpath("//h1")).getText(),"Project " + PROJECT_NAME);
    }

}
