package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;


public class GroupSurvivorsTest extends BaseTest {

    @Test
    public void testEvgenyCheckJenkinsVersion() {
        getDriver().findElement(By.cssSelector(".jenkins-button.jenkins-button--tertiary.jenkins_ver")).click();
        getDriver().findElement(By.xpath("//*[@class = 'jenkins-dropdown__item'][1]")).click();

        Assert.assertEquals(getDriver().findElement(By.className("app-about-version")).getText(), "Version 2.414.2");
    }

    @Test
    public void testEvgenyAddDescription() {
        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.xpath("//*[@name = 'description']")).sendKeys("Test description");
        getDriver().findElement(By.xpath("//*[@id = 'description']/form/div[2]/button")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//*[@id = 'description']/div")).getText(), "Test description");
    }

    @Test
    public void testIuliaFindIconLegend() {
        getDriver().findElement(By.xpath("//a[@href='/view/all/builds']")).click();
        getDriver().findElement(By.xpath("//*[@id='button-icon-legend']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1[@class='jenkins-modal__title']")).getText(), "Icon legend");
    }
}


