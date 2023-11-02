package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.ArrayList;
import java.util.List;

public class MultibranchPipeline4Test extends BaseTest {
    private static final String NAME = "Multibranch Pipeline";
    private static final String RENAMED = "Renamed Multibranch Pipeline";

    private void createMultibranchPipelin(String str) {
        getDriver().findElement(By.linkText("Create a job")).click();
        getDriver().findElement(By.id("name")).sendKeys(NAME);
        getDriver().findElement(By.xpath("//span[@class='label' and text()='Multibranch Pipeline']"))
                .click();
        getDriver().findElement(By.id("ok-button")).click();
    }

    private void getDashboardLink() {
        getDriver().findElement(By.xpath("//a[normalize-space()='Dashboard']")).click();
    }

    private void goMultibranchPipelinePage() {
        getDriver().findElement(By.xpath("//span[normalize-space()='" + NAME + "']")).click();
    }


    @Ignore
    @Test
    public void testRenameUsingBreadcrumb() {
        createMultibranchPipelin(NAME);
        getDashboardLink();
        goMultibranchPipelinePage();

        WebElement hoverable = getDriver().findElement(By.xpath("//li[3]/a/button"));
        new Actions(getDriver()).moveToElement(hoverable)
                .perform();

        getDriver().findElement(By.xpath("//li[3]/a/button")).click();
        getDriver().findElement(By.xpath("//div/a[6]")).click();

        getDriver().findElement(By.xpath("//input[@class='jenkins-input validated  ']")).clear();
        getDriver().findElement(By.xpath("//input[@class='jenkins-input validated  ']")).sendKeys(RENAMED);
        getDriver().findElement(By.xpath("//button[@class='jenkins-button jenkins-button--primary ']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(), RENAMED);
    }

    @Test
    public void testRenameUsingSidebar() {
        createMultibranchPipelin(NAME);
        getDashboardLink();
        goMultibranchPipelinePage();

        getDriver().findElement(By.xpath("//div[8]/span/a")).click();

        getDriver().findElement(By.xpath("//input[@class='jenkins-input validated  ']")).clear();
        getDriver().findElement(By.xpath("//input[@class='jenkins-input validated  ']")).sendKeys(RENAMED);
        getDriver().findElement(By.xpath("//button[@class='jenkins-button jenkins-button--primary ']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(), RENAMED);
    }
}
