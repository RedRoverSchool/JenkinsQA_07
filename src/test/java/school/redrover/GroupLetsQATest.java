package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;
import school.redrover.runner.BaseTest;

import java.time.Duration;
import java.util.List;
import java.util.stream.Stream;

public class GroupLetsQATest extends BaseTest {

    @Test
    public void testDescriptionTextAreaAppearsJenkinsProject() {
        Wait<WebDriver> wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));

        WebElement addDescriptionButton = getDriver().findElement(By.id("description-link"));
        addDescriptionButton.click();
        try {
            WebElement descriptionInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("jenkins-input")));
            Assert.assertTrue(true);
        } catch (Exception TimeoutException) {
            Assert.assertTrue(false);
        }

    }

    @Test
    public void testSaveDescriptionButtonAppearsJenkinsProject() {
        Wait<WebDriver> wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));

        WebElement addDescriptionButton = getDriver().findElement(By.id("description-link"));
        addDescriptionButton.click();

        try {
            WebElement descriptionInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".jenkins-button--primary")));
            Assert.assertTrue(true);
        } catch (Exception TimeoutException) {
            Assert.assertTrue(false);
        }

    }

    @Test
    public void searchBoxJenkinsTest() {
        WebElement searchBox = getDriver().findElement(By.name("q"));
        searchBox.sendKeys("admin");
        searchBox.sendKeys(Keys.ENTER);

        try {
            WebElement searchResult = getDriver().findElement(By.xpath("//*[@id='main-panel']/div[2]"));
            Assert.assertTrue(searchResult.getText().contains("admin"));
        } catch (Exception e) {
            System.out.println("You have no admin user");
        }
    }

    @Test
    public void versionJenkinsTest() {
        WebElement versionBox = getDriver().findElement(By.xpath("//*[@id='jenkins']/footer/div/div[2]/button"));

        Assert.assertEquals(versionBox.getText(), "Jenkins 2.414.2");
    }

    @Ignore
    @Test
    public void newItemButtonTest() {
        WebElement newItemButton = getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']"));
        newItemButton.click();

        WebElement newItemSpan = getDriver().findElement(By.cssSelector(".h3"));

        Assert.assertEquals(newItemSpan.getText(), "Enter an item name");
    }

    @Test
    public void testNewFolderCreation() {

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();

        getDriver().findElement(By.id("name")).sendKeys("123");

        getDriver().findElement(By.xpath("//span[text()='Folder']")).click();

        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.id("jenkins-name-icon")).click();

        List<WebElement> itemsList = getDriver().findElements(By.cssSelector(".jenkins-table__link.model-link.inside span"));
        boolean result = false;
        for (WebElement e : itemsList) {
            if (e.getText().equals("123")) {
                result = true;
                break;
            }
        }

        Assert.assertTrue(result);
    }

    @Test
    public void testProceedAboutJenkins() {
        getDriver().findElement(By.xpath("//a[@href='/manage']")).click();

        getDriver().findElement(By.xpath("//a[@href='about']")).click();

        Assert.assertTrue(getDriver()
                .findElement(By.className("app-about-paragraph"))
                .getText().contains("The leading open source automation server"));
    }
}