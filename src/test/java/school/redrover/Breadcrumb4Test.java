package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.List;

public class Breadcrumb4Test extends BaseTest {
    @Test
    public void testReturnHomepageClickOnDashboardBreadcrumb(){
        getDriver().findElement(By.xpath("//span/a[@href='/asynchPeople/']")).click();
        Assert.assertEquals(getDriver().findElement(By.xpath(
                "//div[@class ='jenkins-app-bar__content']/h1")).getText()
                , "People");

        getDriver().findElement(By.xpath(
                "//li[@class ='jenkins-breadcrumbs__list-item']/a")).click();
        Assert.assertEquals(getDriver().findElement(By.xpath(
                "//div[@class='empty-state-block']/h1")).getText()
                , "Welcome to Jenkins!");
    }

    @Test
    public void testReturnHomepageFromAnyPageClickingDashboard() {

        List<By> sidebarItems = List.of(
                By.xpath("//a[@href='/view/all/newJob']"),
                By.xpath("//a[@href='/asynchPeople/']"),
                By.xpath("//a[@href='/view/all/builds']"),
                By.xpath("//a[@href='/manage']"),
                By.xpath("//a[@href='/me/my-views']"));

        for (By locator : sidebarItems){
            getDriver().findElement(locator).click();
            getDriver().findElement(By.xpath("//li/a[@class='model-link']")).click();

            Assert.assertEquals(getDriver().findElement(By.xpath(
                    "//div[@class='empty-state-block']/h1")).getText()
                    , "Welcome to Jenkins!");
        }
    }
}