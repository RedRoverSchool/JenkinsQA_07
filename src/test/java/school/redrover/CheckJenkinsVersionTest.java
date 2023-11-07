package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.ArrayList;
import java.util.List;


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
        Assert.assertEquals(getDriver().findElement(By.cssSelector(".app-about-version")).getText(), "Version 2.414.2");
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

    @Test
    public void testJenkinsVersionCheck1() {
        getDriver().findElement(By.xpath("//a[@class]//span[@class='hidden-xs hidden-sm']")).click();
        getDriver().findElement(By.xpath("//button[@class='jenkins-button jenkins-button--tertiary jenkins_ver']")).click();
        getDriver().findElement(By.xpath("//a[@href='/manage/about']")).click();

        Assert.assertEquals(getDriver().getCurrentUrl(),"http://localhost:8080/manage/about/");
        Assert.assertTrue(getDriver()
                .findElement(By.xpath("//p[@class='app-about-version']"))
                .getText().contains("Version 2.414.2"));
    }
    @Test
    public void testJenkinsVersionButtonVisibilityCLikabilityFunctionality() {

        getDriver().findElement(By.xpath("//a[@class]//span[@class='hidden-xs hidden-sm']")).click();
        getDriver().findElement(By.xpath("//button[@class='jenkins-button jenkins-button--tertiary jenkins_ver']")).click();

        List<WebElement> listJenkinsDropdownItem = getDriver().findElements(By.xpath("//a[@class='jenkins-dropdown__item']"));

        Assert.assertEquals(listJenkinsDropdownItem.size(), 3);

        for (WebElement e : listJenkinsDropdownItem) {
            Assert.assertTrue(e.isDisplayed());
        }
        listJenkinsDropdownItem.get(0).click();

        Assert.assertEquals(getDriver().getCurrentUrl(), "http://localhost:8080/manage/about/");

        List<WebElement> actualListTabBar = getDriver()
                .findElements(By.xpath("//div[@class='tabBar']//div[contains(@class,'tab')]"));
        List<String> expectedListTabBar = List.of("Mavenized dependencies",
                "Static resources", "License and dependency information for plugins");
        List<String> actualListTabBarGetText = new ArrayList<>();

        for (WebElement e : actualListTabBar) {
            actualListTabBarGetText.add(e.getText());
        }
        Assert.assertTrue(actualListTabBarGetText.containsAll(expectedListTabBar));
    }
}

