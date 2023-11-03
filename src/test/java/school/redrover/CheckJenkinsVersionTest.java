package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;


public class CheckJenkinsVersionTest extends BaseTest {

    @Test
    public void testVersionCheck() {

        getDriver().findElement(By.xpath("//div/button")).click();
        Assert.assertEquals(
                getDriver().findElement(By.xpath("//div/button")).getText(),
                "Jenkins 2.414.2");

        getDriver().findElement(By.xpath("//a[@href = '/manage/about']")).click();

        Assert.assertEquals(
                getDriver().findElement(By.className("page-footer__links")).getText(),
                "Jenkins 2.414.2");
    }

    @Test
    public void testJenkinsVersion() {

        Assert.assertEquals(getDriver().findElement
                        (By.xpath("//button[@class='jenkins-button jenkins-button--tertiary jenkins_ver']")).getText(),
                "Jenkins 2.414.2");
    }


    @Test

    public void testVersion() {

        getDriver().findElement(By.xpath("//button[@class='jenkins-button jenkins-button--tertiary jenkins_ver']")).click();
        getDriver().findElement(By.xpath("//a[@href='/manage/about']")).click();
        Assert.assertEquals(getDriver().findElement(By.cssSelector(".app-about-version")).getText(),"Version 2.414.2");
    }

    @Test

    public void testJenkinsVersionCheck() {

        Assert.assertEquals(
        getDriver().findElement(By.xpath("//button[contains(text(),'Jenkins 2.414.2')]")).
                getAttribute("innerText").trim(),
                "Jenkins 2.414.2");

        getDriver().findElement((By.className("page-footer__links"))).click();

        getDriver().findElement((By.className("jenkins-dropdown__item"))).click();

        Assert.assertEquals(
                getDriver().findElement(By.className("app-about-version")).getText(),
                "Version 2.414.2");

        Assert.assertEquals(
                getDriver().findElement(By.className("page-footer__links")).getText(),
                "Jenkins 2.414.2");
    }
}

