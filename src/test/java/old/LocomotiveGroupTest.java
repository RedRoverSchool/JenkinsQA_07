package old;

import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import school.redrover.runner.BaseTest;
@Ignore
public class LocomotiveGroupTest extends BaseTest {

    @Test
    public void testVerifyJenkinsVersion() {
        WebDriver driver = getDriver();
        By locatorButtonJenkinsVersion = By.cssSelector("button.jenkins_ver");
        By locatorButtonAbout = By.cssSelector(".jenkins-dropdown__item:first-of-type");
        By locatorTextJenkinsVersion = By.cssSelector("p.app-about-version");
        final String expectedJenkinsVersionText = "Version 2.414.2";

        WebElement buttonJenkinsVersion = driver.findElement(locatorButtonJenkinsVersion);
        buttonJenkinsVersion.click();

        Assert.assertEquals(buttonJenkinsVersion.getAttribute("data-dropdown"),
                "true",
                "Attribute 'data-dropdown' for Jenkins Version button is incorrect");

        driver.findElement(locatorButtonAbout).click();
        Assert.assertEquals(driver.findElement(locatorTextJenkinsVersion).getText(),
                expectedJenkinsVersionText,
                "Jenkins Version is incorrect");
    }

    @Test
    public void testAddDescriptionJenkinsHomePage() {
        final String description = "My Jenkins home page description";

        By submitButton = By.id("description-link");
        By descriptionInputField = By.xpath("//textarea[@name='description']");
        By saveButton = By.xpath("//button[@name='Submit']");

        getDriver().findElement(submitButton).click();
        getDriver().findElement(descriptionInputField).sendKeys(description);
        getDriver().findElement(saveButton).click();

        Assert.assertEquals(getDriver()
                .findElement(By.xpath("//*[@id='description']/div[1]"))
                .getText(), description);
    }

    @Test
    public void testOpenBuildHistory() {
        getDriver().findElement(By.xpath("//*[@href='/view/all/builds']")).click();

        Assert.assertEquals(getDriver().findElement(By.className("jenkins-app-bar__content")).getText(), "Build History of Jenkins");
    }

    @Test
    public void testJenkinsCreateNewJob() {

        getDriver().findElement(By.xpath("//*[@id='main-panel']/div[2]/div/section[1]/ul/li/a/span[1]")).click();

        getDriver().findElement(By.xpath("//*[@id='name']")).sendKeys("Locomotive Project");

        getDriver().findElement(By.xpath("//*[@id='j-add-item-type-standalone-projects']/ul/li[1]")).click();

        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.xpath("//*[@id='main-panel']/form/div[1]/div[2]/div/div[2]/textarea"))
                .sendKeys("This is first automation QAA project by Michael, from Locomotive group");

        getDriver().findElement(By.xpath("//*[@id='bottom-sticker']/div/button[1]")).click();

        WebElement title = getDriver().findElement(By.xpath("//*[@id='main-panel']/h1"));
        String value = title.getText();
        Assert.assertEquals(value, "Project Locomotive Project");
    }

    @Test
    public void testCreateOrganizationFolder() {

//        Actions action = new Actions(getDriver());

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@class='jenkins-input']")).sendKeys("Folder1");
        getDriver().findElement(By.className("jenkins_branch_OrganizationFolder")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//a[@href='/job/Folder1/' and @class='model-link']")).getText(),"Folder1");
    }

    @Test
    public void testSearchDocumentationAboutJenkins () {
        getDriver().findElement(By.xpath
                ("//div[@class='page-footer__links']/a[@href='api/']")).click();
        getDriver().findElement(By.xpath
                ("//a[@href='https://www.jenkins.io/redirect/remote-api']")).click();
        WebElement searchText = getDriver().findElement(By.xpath("//a[contains(text(),'> User Documentation Home')]"));
        String ExpectedDocument = searchText.getText();

        Assert.assertEquals(ExpectedDocument, "> User Documentation Home");
    }

    @Test
    public void testClickNewItemJenkins() {
          getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
          String pageCurrentUrl = getDriver().getCurrentUrl();
          String pageNewJobUrl = "http://localhost:8080/view/all/newJob";

          Assert.assertEquals(pageCurrentUrl, pageNewJobUrl);
    }
}
