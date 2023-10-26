package old;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

@Ignore
public class GroupForwardTest extends BaseTest {


    @Test
    public void testH1BeforeClickingOnLogOutButton() {

        String expectedResult = "Sign in to Jenkins";

        WebElement logOutButton = getDriver().findElement(By.xpath(
                "//a[ @href = '/logout']"));
        logOutButton.click();

        WebElement h1OnStartPage = getDriver().findElement(By.xpath("//h1"));

        String actualResult = h1OnStartPage.getText();

        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void testClickLogoToMainPage() {

        WebElement myViewsButton = getDriver().findElement(By.xpath(
                "//a[@href='/me/my-views']"));
        myViewsButton.click();

        WebElement logoJenkins = getDriver().findElement(By.id("jenkins-head-icon"));
        logoJenkins.click();

        Assert.assertEquals(
                getDriver().findElement(By.cssSelector(".empty-state-block > h1")).getText(),
                "Welcome to Jenkins!");
    }

    @Test
    public void testAddDescription() {

        String addDescription = "GroupForward #1";

        WebElement description = getDriver().findElement(By.id("description-link"));
        description.click();
        WebElement textField = getDriver().findElement(By.name("description"));
        textField.clear();
        textField.sendKeys(addDescription);
        getDriver().findElement(By.name("Submit")).click();
        String newDescriprtion = getDriver().findElement(By.id("description")).getText();

        Assert.assertTrue(newDescriprtion.contains(addDescription));

    }

    @Test
    public void testHomeDirectory() {
        getDriver().findElement(By.xpath("//a[@href='/manage']")).click();
        getDriver().findElement(By.xpath("//a[@href='configure']")).click();

        WebElement directory = getDriver().findElement(By.xpath("//div[@class= 'jenkins-quote jenkins-quote--monospace']"));
        String innerText = directory.getAttribute("innerText");
        WebElement copyText = getDriver().findElement(By.xpath("//button[@tooltip = 'Copy home directory']"));
        String copyInnerText = copyText.getAttribute("text");

        Assert.assertEquals(innerText, copyInnerText);

    }

    @Test
    public void testJenkinsVersion() {
        By locator = By.xpath("//button[contains(@class, 'jenkins-button--tertiary') and contains(text(), 'Jenkins 2.414.2')]");
        WebElement element = getDriver().findElement(locator);

        Assert.assertEquals(element.getText().trim(), "Jenkins 2.414.2");
    }

    @Test
    public void testJenkins1Version() {

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@class = 'page-footer__links']/button"))
                .getText().trim(), "Jenkins 2.414.2");
    }

    @Test
    public void testDropDownMenuTimespan() {
        getDriver().findElement(By.xpath("//a[@href='/computer/']")).click();
        getDriver().findElement(By.xpath("//*[@id='node_']/td[2]/a")).click();
        getDriver().findElement(By.xpath("//*[@id='tasks']/div[4]/span/a")).click();
        Assert.assertEquals(getDriver().findElement(By.xpath("//option[@value='min']"))
                        .getText(),"Medium");
        Assert.assertEquals(getDriver().findElement(By.xpath("//option[@value='hour']"))
                        .getText(),"Long");
        Assert.assertEquals(getDriver().findElement(By.xpath("//option[@value='sec10']"))
                        .getText(),"Short");

    }
}



