package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.ArrayList;
import java.util.List;

public class HeaderTest extends BaseTest {

    @Test
    public void testReturnWithLogo() {
        getDriver().findElement(By.xpath("//a[@href='/manage']")).click();

        getDriver().findElement(By.xpath("//img[@id='jenkins-name-icon']")).click();

        Assert.assertTrue(getDriver().getTitle().contains("Dashboard"));
    }

    @Test
    public void testClickLogoToMainPage() {

        getDriver().findElement(By.xpath("//a[@href='/me/my-views']")).click();

        getDriver().findElement(By.id("jenkins-head-icon")).click();

        Assert.assertTrue(
                getDriver().findElement(By.cssSelector(".empty-state-block > h1")).getText().contains("Welcome to Jenkins!"));
    }

    @Test
    public void testReturningBackToMainPageFromMainMenuPages() {

        List<By> mainPageMenuItems = List.of(
            By.xpath("//a[@href='/view/all/newJob']"),
            By.xpath("//a[@href='/asynchPeople/']"),
            By.xpath("//a[@href='/view/all/builds']"),
            By.xpath("//a[@href='/manage']"),
            By.xpath("//a[@href='/me/my-views']"));

        for (By locator : mainPageMenuItems) {
            getDriver().findElement(locator).click();
            getDriver().findElement(By.id("jenkins-home-link")).click();

            Assert.assertEquals(getDriver().getTitle(), "Dashboard [Jenkins]");
        }
    }
}
