package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import static org.testng.AssertJUnit.assertTrue;

public class Breadcrumb2Test extends BaseTest {
    private boolean isBreadcrumbPresent() {
        try {
            WebElement breadcrumb = getDriver().findElement(By.xpath("//div[@id=\"breadcrumbBar\"]"));
            return breadcrumb.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }
    private boolean thisIsDashboardPage() {
        try {
            WebElement dashboard = getDriver().findElement(By.xpath("//div[@class=\"dashboard\"]"));
            return dashboard.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }
    private void createTask() {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//*[@id=\"name\"]")).sendKeys("Test");
        getDriver().findElement(By.xpath("//li[@class='hudson_model_FreeStyleProject']//label")).click();
        getDriver().findElement(By.xpath("//button[@id=\"ok-button\"]")).click();
    }
@Test
    public void testBreadcrumbOnDifferentPages() {


        createTask();
        assertTrue(isBreadcrumbPresent());
        getDriver().findElement(By.xpath("//button[@name=\"Submit\"]")).click();
        assertTrue(isBreadcrumbPresent());

        getDriver().findElement(By.xpath("//*[@id=\"tasks\"]/div[2]/span/a")).click();
        assertTrue(isBreadcrumbPresent());

        getDriver().findElement(By.xpath("//*[@id=\"tasks\"]/div[3]/span/a")).click();
        assertTrue(isBreadcrumbPresent());

        getDriver().findElement(By.xpath("//*[@id=\"tasks\"]/div[4]/span/a")).click();
        assertTrue(isBreadcrumbPresent());

        getDriver().findElement(By.xpath("//*[@id=\"page-header\"]/div[3]/a[1]")).click();
        assertTrue(isBreadcrumbPresent());

    }
    @Test
    public void testReturnToDashboardOnDifferentPages() {
        createTask();
        getDriver().findElement(By.xpath("//*[@id=\"breadcrumbs\"]/li[1]/a")).click();
        assertTrue(thisIsDashboardPage());

        getDriver().findElement(By.xpath("//*[@id=\"tasks\"]/div[2]/span/a")).click();
        getDriver().findElement(By.xpath("//*[@id=\"breadcrumbs\"]/li[1]/a")).click();
        assertTrue(thisIsDashboardPage());

        getDriver().findElement(By.xpath("//*[@id=\"tasks\"]/div[3]/span/a")).click();
        getDriver().findElement(By.xpath("//*[@id=\"breadcrumbs\"]/li[1]/a")).click();
        assertTrue(thisIsDashboardPage());

        getDriver().findElement(By.xpath("//*[@id=\"tasks\"]/div[4]/span/a")).click();
        getDriver().findElement(By.xpath("//*[@id=\"breadcrumbs\"]/li[1]/a")).click();
        assertTrue(thisIsDashboardPage());

        getDriver().findElement(By.xpath("//*[@id=\"page-header\"]/div[3]/a[1]")).click();
        getDriver().findElement(By.xpath("//*[@id=\"breadcrumbs\"]/li[1]/a")).click();
        assertTrue(thisIsDashboardPage());




    }
}