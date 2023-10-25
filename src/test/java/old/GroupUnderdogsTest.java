package old;

import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.collections.Sets;
import school.redrover.runner.BaseTest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.testng.Assert.assertEquals;

public class GroupUnderdogsTest extends BaseTest {

    private static final String STR_TEST = "test";

    @Test
    public void testNewProjectCreatedOlena() {
        String randomName = UUID.randomUUID()
                .toString()
                .substring(0, 5);
        WebElement newItem = getDriver().findElement(By.linkText("New Item"));
        newItem.click();

        WebElement projectNameField = getDriver().findElement(By.id("name"));
        projectNameField.click();
        projectNameField.sendKeys(randomName);

        WebElement selectProjectType = getDriver().findElement(By.xpath("//span[text()='Freestyle project']"));
        selectProjectType.click();

        WebElement okButton = getDriver().findElement(By.id("ok-button"));
        okButton.click();

        getDriver().findElement(By.linkText("Dashboard")).click();
        WebElement projectName = getDriver().findElement(By.xpath("//td[3]/a"));
        String actualProjectName = projectName.getText();
        assertEquals(actualProjectName, randomName);
    }

    @Test
    public void testJenkinsVersionInFooter_tereshenkov29() {

        WebElement JenkinsVersionInFooter = getDriver().findElement(By.xpath("//*[@class='jenkins-button jenkins-button--tertiary jenkins_ver']"));

        String JenkinsVersionInFooterValue = JenkinsVersionInFooter.getText();
        assertEquals(JenkinsVersionInFooterValue, "Jenkins 2.414.2");
    }

    @Test
    public void testJenkinsLogOut() {

        getDriver().findElement(By.xpath("//span[normalize-space()='log out']")).click();
        Assert.assertEquals(getDriver().findElement(By.xpath
                        ("//h1[normalize-space()='Sign in to Jenkins']")).getText(),
                "Sign in to Jenkins");
    }

    private void createNewFolder() {

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();

        getDriver().findElement(By.xpath("(//input[@id='name'])[1]")).sendKeys(STR_TEST);

        getDriver().findElement(By.xpath("//span[normalize-space()='Folder']")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
    }

    @Test
    public void testVerifyIconSize() {

        final String background = "rgba(175, 175, 207, 0.176)";

        createNewFolder();

        getDriver().findElement(By.xpath("//a[normalize-space()='Dashboard']")).click();

        Assert.assertEquals(getDriver().findElement(By.cssSelector(".jenkins-table__link > span")).getText(), STR_TEST);

        getDriver().findElement(By.xpath("//li[@class='jenkins-icon-size__items-item']")).click(); // M

        getDriver().findElement(By.linkText("People")).click();
        Assert.assertEquals(getDriver().findElement(By.xpath("//li[@class='jenkins-icon-size__items-item']"))
                .getCssValue("background-color"), background);

        getDriver().findElement(By.linkText("Build History")).click();
        Assert.assertEquals(getDriver().findElement(By.xpath("//li[@class='jenkins-icon-size__items-item']"))
                .getCssValue("background-color"), background);

        getDriver().findElement(By.linkText("My Views")).click();
        Assert.assertEquals(getDriver().findElement(By.xpath("//li[@class='jenkins-icon-size__items-item']"))
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

    @Test
    public void testRestApiPageOpensAndHas3ApiOptions() {
        getDriver().findElement(
                By.xpath(
                        "//*[@id='jenkins']/footer/div/div[contains(@class, 'page-footer__links')]/a[contains(@class, 'rest-api')]"
                )
        ).click();

        List<WebElement> apiTypes = getDriver().findElements(By.xpath("//div[@id='main-panel']/dl/dt/a"));
        Assert.assertEquals(apiTypes.size(), 3, "REST API page should always have 3 API types");

        Set<String> apiTypeText = new HashSet<>();
        for (WebElement el : apiTypes) {
            apiTypeText.add(el.getText());
        }
        Set<String> expected = Sets.newHashSet("XML API", "JSON API", "Python API");
        Assert.assertEquals(apiTypeText, expected);
    }

    @Test
    public void testKristinaSearchID() {

        getDriver().findElement(By.xpath("//div[@id='tasks']//a[@href='/asynchPeople/']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//table[@id='people']/thead/tr/th[2]/a")).getText(), "User ID");
    }

    @Test
    public void testCreateNewItemKristina() {
        getDriver().findElement(By.xpath("//div[@id='tasks']//a[@href='/view/all/newJob']")).click();

        getDriver().findElement(By.xpath("//input[@id='name']")).sendKeys("My project");
        getDriver().findElement(By.xpath("//span[text()='Folder']")).click();
        getDriver().findElement(By.xpath("//div[@class='footer']//button")).click();

        String name = "First project";
        getDriver().findElement(By.xpath("//div[@id='main-panel']//div[@class='setting-main']/input")).sendKeys(name);
        getDriver().findElement(By.xpath("//div[@id='bottom-sticker']//button[@name='Submit']")).click();

        WebElement findObject = getDriver().findElement(By.xpath("//div[@id='main-panel']//h1"));
        String actualResult = findObject.getText();
        Assert.assertEquals(actualResult, name);

        getDriver().findElement(By.xpath("//div[@id='breadcrumbBar']//a[text()='Dashboard']")).click();

        String table = getDriver().findElement(By.xpath("//table[@id='projectstatus']/tbody")).getText();
        String[] wordTable = table.split(" ");

        for (int i = 0; i < wordTable.length; i++) {
            String result = " ";
            if (result == name) {
                result = name;
                assertEquals(result, name);
            }
        }
    }

    @Test
    public void testOlgaBrest() throws InterruptedException {
        WebElement newItem = getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']"));
        newItem.click();
        WebElement itemName = getDriver().findElement(By.id("name"));
        itemName.sendKeys("MyProject");
        WebElement pipeLine = getDriver().findElement(By.xpath("//span[normalize-space()='Pipeline']"));
        pipeLine.click();
        WebElement button = getDriver().findElement(By.id("ok-button"));
        button.click();
        Assert.assertEquals(
                getDriver().findElement(By.xpath("//h1[normalize-space()='Configure']")).getText(),
                "Configure");
    }
}