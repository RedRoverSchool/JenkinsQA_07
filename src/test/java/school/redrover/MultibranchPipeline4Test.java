package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;
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

    @Test
    public void testErrorForUnsafeChar() {
        createMultibranchPipelin(NAME);
        getDashboardLink();
        goMultibranchPipelinePage();

        getDriver().findElement(By.xpath("//div[8]/span/a")).click();

        getDriver().findElement(By.xpath("//input[@class='jenkins-input validated  ']")).clear();
        getDriver().findElement(By.xpath("//input[@class='jenkins-input validated  ']")).sendKeys(RENAMED + "!");

        Actions actions = new Actions(getDriver());
        WebElement element = getDriver().findElement(By.xpath("//input[@class='jenkins-input validated  ']"));
        actions.moveToElement(element).moveByOffset(100, 50).click().build().perform();

        WebElement error_message = getDriver().findElement(By.xpath("//div[@class='error']"));

        Wait<WebDriver> wait = new WebDriverWait(getDriver(), Duration.ofSeconds(2));
        wait.until(d -> error_message.isDisplayed());

        Assert.assertEquals(error_message.getText(), "‘!’ is an unsafe character");
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

        Assert.assertEquals(getDriver().findElement(By.xpath("//*[contains(text(),'" + RENAMED + "')]")).getText(), RENAMED);
    }

    @Test
    public void testRenameResultInBreadcrumb() {
        createMultibranchPipelin(NAME);
        getDashboardLink();
        goMultibranchPipelinePage();

        getDriver().findElement(By.xpath("//div[8]/span/a")).click();

        getDriver().findElement(By.xpath("//input[@class='jenkins-input validated  ']")).clear();
        getDriver().findElement(By.xpath("//input[@class='jenkins-input validated  ']")).sendKeys(RENAMED);
        getDriver().findElement(By.xpath("//button[@class='jenkins-button jenkins-button--primary ']")).click();

        List<WebElement> elements = getDriver().findElements(By.xpath("//li[@class='jenkins-breadcrumbs__list-item']"));
        List<String> breadcrumb = new ArrayList<>();
        for (WebElement element : elements) {
            breadcrumb.add(element.getText());
        }

        Assert.assertTrue(breadcrumb.contains(RENAMED));
    }

    @Test
    public void testRenameResultOnPageHeading() {
        createMultibranchPipelin(NAME);
        getDashboardLink();
        goMultibranchPipelinePage();

        getDriver().findElement(By.xpath("//div[8]/span/a")).click();

        getDriver().findElement(By.xpath("//input[@class='jenkins-input validated  ']")).clear();
        getDriver().findElement(By.xpath("//input[@class='jenkins-input validated  ']")).sendKeys(RENAMED);
        getDriver().findElement(By.xpath("//button[@class='jenkins-button jenkins-button--primary ']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(), RENAMED);
    }

    @Test
    public void testRenameResultOnDashboard() {
        createMultibranchPipelin(NAME);
        getDashboardLink();
        goMultibranchPipelinePage();

        getDriver().findElement(By.xpath("//div[8]/span/a")).click();

        getDriver().findElement(By.xpath("//input[@class='jenkins-input validated  ']")).clear();
        getDriver().findElement(By.xpath("//input[@class='jenkins-input validated  ']")).sendKeys(RENAMED);
        getDriver().findElement(By.xpath("//button[@class='jenkins-button jenkins-button--primary ']")).click();

        getDashboardLink();

        List<WebElement> elements = getDriver().findElements(By.xpath("//td/a"));
        List<String> jobs = new ArrayList<>();
        for (WebElement element : elements) {
            jobs.add(element.getText());
        }

        Assert.assertTrue(jobs.contains(RENAMED));
    }
}
