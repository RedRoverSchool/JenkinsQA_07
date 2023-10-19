package school.redrover;

import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import static org.testng.Assert.assertEquals;

public class GroupUnderdogsTest extends BaseTest {

    @Test
    public void testJenkinsVersionInFooter_tereshenkov29() {

        WebElement JenkinsVersionInFooter = getDriver().findElement(By.xpath("//*[@class='jenkins-button jenkins-button--tertiary jenkins_ver']"));

        String JenkinsVersionInFooterValue = JenkinsVersionInFooter.getText();
        assertEquals(JenkinsVersionInFooterValue, "Jenkins 2.414.2");
    }

    @Test
    public void testJenkinsLogOut_maksin() {

        getDriver().findElement(By.xpath("//*[@id='page-header']/div[3]/a[2]")).click();
        Assert.assertEquals(getDriver().findElement(By.xpath
                        ("//*[@id='main-panel']/div/h1")).getText(),
                "Sign in to Jenkins");
    }

    @Test
    public void testVerifyIconSize() {

        final String projectName = "test";
        final String background = "rgba(175, 175, 207, 0.176)";

        getDriver().findElement(By.xpath("(//a[@href='/view/all/newJob'])[1]")).click();
        getDriver().findElement(By.xpath("(//input[@id='name'])[1]")).sendKeys(projectName);
        getDriver().findElement(By.xpath("(//span[normalize-space()='Folder'])[1]")).click();
        getDriver().findElement(By.xpath("//*[@id='ok-button']")).click();
        getDriver().findElement(By.xpath("//*[@id='bottom-sticker']/div/button[1]")).click();
        getDriver().findElement(By.xpath("(//a[normalize-space()='Dashboard'])[1]")).click();

        Assert.assertEquals(getDriver().findElement(By.cssSelector(".jenkins-table__link > span")).getText(), projectName);

        getDriver().findElement(By.xpath("//*[@id='main-panel']/div[2]/div[2]/div/div[1]/ol/li[2]")).click(); // M

        getDriver().findElement(By.linkText("People")).click();
        Assert.assertEquals(getDriver().findElement(By.xpath("/html[1]/body[1]/div[2]/div[2]/div[3]/div[1]/ol[1]/li[2]"))
                .getCssValue("background-color"), background);

        getDriver().findElement(By.linkText("Build History")).click();
        Assert.assertEquals(getDriver().findElement(By.xpath("/html[1]/body[1]/div[2]/div[2]/div[6]/div[1]/ol[1]/li[2]"))
                .getCssValue("background-color"), background);

        getDriver().findElement(By.linkText("My Views")).click();
        Assert.assertEquals(getDriver().findElement(By.xpath("/html[1]/body[1]/div[2]/div[2]/div[2]/div[2]/div[1]/div[1]/ol[1]/li[2]"))
                .getCssValue("background-color"), background);
    }

    @Test
    public void testIdAdminArtuom() throws InterruptedException {
        getDriver().findElement(By.xpath("//a[@href='/user/admin']")).click();
        getDriver().findElement(By.xpath("(//span[@class='task-link-wrapper '])[4]")).click();

        WebElement button = getDriver().findElement(By.xpath("//button[@formnovalidate='formNoValidate']"));
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].scrollIntoView(true);", button);
        button.click();

        WebElement UserIdAdm = getDriver().findElement(By.xpath("//div[@id='description']/following-sibling::div"));
        String actNameOfUser = UserIdAdm.getText();
        String expRes = "Jenkins User ID: admin";
        assertEquals(actNameOfUser, expRes);

    }

    @Test
    public void testCreateNewJob() throws InterruptedException {

        WebElement createJobButton = getDriver().findElement(By.xpath("//a [@href='newJob']"));
        createJobButton.click();

        WebElement inputField = getDriver().findElement(By.xpath("//input[@name = 'name']"));
        inputField.click();
        inputField.sendKeys("My Job");

        WebElement projectType = getDriver().findElement(By.xpath("//div[contains(text(),'This is the central feature of Jenkins. Jenkins wi')]"));
        projectType.click();

        WebElement okButton = getDriver().findElement(By.xpath("//button[@id='ok-button']"));
        okButton.click();

        WebElement saveButton = getDriver().findElement(By.xpath("//button[contains(text(),'Save')]"));
        saveButton.click();

        WebElement title = getDriver().findElement(By.xpath("//*[@class = 'job-index-headline page-headline']"));
        String value = title.getText();
        Assert.assertEquals(value, "Project My Job");

    }

    @Test
    public void testDescription() {

        WebElement addDescriptionButton = getDriver().findElement(By.xpath("//a[@id='description-link']"));
        addDescriptionButton.click();

        WebElement descriptionTextField = getDriver().findElement(By.xpath("//*[@name = 'description']"));
        descriptionTextField.click();
        descriptionTextField.sendKeys("Test Description");

        WebElement saveButton = getDriver().findElement(By.xpath("//*[@name = 'Submit']"));
        saveButton.click();

        WebElement title = getDriver().findElement(By.xpath("//div[contains(text(),'Test Description')]"));
        String value = title.getText();
        Assert.assertEquals(value, "Test Description");

    }
}
