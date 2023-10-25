package old;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;
import java.util.*;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import java.awt.Dimension;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
@Ignore
public class GroupUnicornsTest extends BaseTest {

    private void goToDashboard() {
        getDriver().findElement(By.xpath("//a[@href='/manage']")).click();
    }

    private void sendKeysToSearchBar(String search) {
        getDriver().findElement(By.id("settings-search-bar")).sendKeys(search);
    }

    @Test
    public void testSuccessfulSearchResult() {

        goToDashboard();
        sendKeysToSearchBar("users");
        WebElement searchResult = getDriver().findElement(By.xpath("//a[contains(text(),'Users')]"));
        Assert.assertEquals(searchResult.getAttribute("text"), "Users");
        searchResult.click();

        Assert.assertEquals(getDriver().getCurrentUrl(), "http://localhost:8080/manage/securityRealm/");
    }

    @Test
    public void testTableSizes() {

        //creating a new job
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys("job");
        getDriver().findElement(By.xpath("//li[@class='hudson_model_FreeStyleProject']")).click();
        getDriver().findElement(By.xpath("//button[.='OK']")).click();
        getDriver().findElement(By.xpath("//li[contains(.,'Dashboard')]")).click();

        //check size when Small is clicked
        getDriver().findElement(By.xpath("//a[@tooltip='Small']")).click();
        Dimension actualTableSizeS = getTableDimension();
        Assert.assertEquals(actualTableSizeS, new Dimension(1524, 71));

        //check size when Medium is clicked
        getDriver().findElement(By.xpath("//a[@tooltip='Medium']")).click();
        Dimension actualTableSizeM = getTableDimension();
        Assert.assertEquals(actualTableSizeM, new Dimension(1524, 86));

        //check size when Large is clicked
        getDriver().findElement(By.xpath("//a[@tooltip='Large']")).click();
        Dimension actualTableSizeL = getTableDimension();
        Assert.assertEquals(actualTableSizeL, new Dimension(1524, 102));
    }

    private Dimension getTableDimension() {
        WebElement table = getDriver().findElement(By.xpath("//table[@id='projectstatus']"));
        return new Dimension(table.getSize().width, table.getSize().height);
    }

    @Test
    public void testVerifyRemoteDirectoryIsMandatoryForSetUpAnAgent() throws InterruptedException {
        getDriver().findElement(By.xpath("//span[normalize-space()='Set up an agent']")).click();
        getDriver().findElement(By.xpath("//input[@id='name']")).sendKeys("PKTest");
        getDriver().findElement(By.xpath("//label[@for='hudson.slaves.DumbSlave']")).click();
        getDriver().findElement(By.xpath("//button[@id='ok']")).click();

        // added  this wait until we implement implicit wait in our Base test
        Thread.sleep(1000);
        Assert.assertTrue(getDriver().
                findElement(By.xpath("//div[contains(text(),'Remote directory is mandatory')]")).isDisplayed());
    }

    @Test
    public void testVerifyBuildHistoryTabOpens() {
        getDriver().findElement(By.xpath("//a[@href='/view/all/builds']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1[normalize-space()='Build History of Jenkins']"))
                .getText(), "Build History of Jenkins");
    }

    @Ignore
    @Test
    public void testSuccessfulLogin() {
        WebDriver driver = getDriver();
        driver.get("https://the-internet.herokuapp.com/login");
        String username = "tomsmith";
        String password = "SuperSecretPassword!";
        driver.findElement(By.id("username")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.className("radius")).click();
        String actual = driver.findElement(By.id("flash")).getText();
        assertTrue(actual.contains("You logged into a secure area!"));
    }

    @Ignore
    @Test
    public void testLoginAttemptWithInvalidUsername() {
        WebDriver driver = getDriver();
        driver.get("https://the-internet.herokuapp.com/login");
        String username = "tomsmith123";
        String password = "SuperSecretPassword!";
        driver.findElement(By.id("username")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.className("radius")).click();
        String actual = driver.findElement(By.id("flash-messages")).getText();
        assertTrue(actual.contains("Your username is invalid!"));
    }

    @Test
    public void TestCreateNewFolderAndCheckDashboard() {

        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.id("name")).sendKeys("FolderTest");
        getDriver().findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.className("jenkins-input")).sendKeys("Testing folder");
        getDriver().findElement(By.name("Submit")).click();

        List<String> listOfExpectedItems = Arrays.asList("Status", "Configure", "New Item", "Delete Folder", "People", "Build History", "Rename", "Credentials");
        List<WebElement> listOfDashboardItems = getDriver().findElements(By.xpath("//span[@class='task-link-text' and contains(., '')]"));
        List<String> extractedTexts = listOfDashboardItems.stream().map(WebElement::getText).collect(Collectors.toList());

        assertEquals(extractedTexts, listOfExpectedItems);
        assertEquals(getDriver().findElement(By.xpath("//*[@id='main-panel']/h1")).getText(), "FolderTest");
        assertEquals(getDriver().findElement(By.xpath("//*[@id='description']/div[1]")).getText(), "Testing folder");
    }

    @Test
    public void TestJenkins() {

        //Check the button REST API

        getDriver().findElement(By.linkText("REST API")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//*[@id='main-panel']/h1")).getText(),
                "REST API");
    }

    @Ignore
    @Test
    public void testDemoWebShop() {

        String pageTitlePath = "//div[@class='page-title']//h1";
        String basePath = "//ul[@class='top-menu']//a[@href='/";

        HashMap<String, String> pages = new HashMap<>();
        pages.put("Books", basePath + "books']");
        pages.put("Computers", basePath + "computers']");
        pages.put("Electronics", basePath + "electronics']");
        pages.put("Apparel & Shoes", basePath + "apparel-shoes']");
        pages.put("Digital downloads", basePath + "digital-downloads']");
        pages.put("Jewelry", basePath + "jewelry']");
        pages.put("Gift Cards", basePath + "gift-cards']");

        String pageTitle;
        getDriver().get("https://demowebshop.tricentis.com/");

        for (String key : pages.keySet()) {
            getDriver().findElement(By.xpath(pages.get(key))).click();
            pageTitle = getDriver().findElement(By.xpath(pageTitlePath)).getText();
            Assert.assertEquals(pageTitle, key);
        }
    }

    @Test
    public void testCheckPeopleAdmin() {
        getDriver().findElement(By.xpath("//a[@href='/asynchPeople/']")).click();
        Assert.assertEquals(getDriver().findElement(By.xpath("//a[@href='/user/admin/']")).getText(), "admin");
        Assert.assertEquals(getDriver().findElement(By.xpath("//tr[@id = 'person-admin']/td[3]")).getText(), "admin");
    }

    @Test
    public void testAdminAddDescription() {
        getDriver().findElement(By.xpath("//a[@href='/user/admin']")).click();
        getDriver().findElement(By.xpath("//a[@id='description-link']")).click();
        getDriver().findElement(By.xpath("//textarea[@name='description']")).sendKeys("test admin");
        getDriver().findElement(By.xpath("//button[@formnovalidate='formNoValidate']")).submit();
        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id='description']/div[1]")).getText(), "test admin");

        getDriver().findElement(By.xpath("//a[@id='description-link']")).click();
        getDriver().findElement(By.xpath("//textarea[@name='description']")).clear();
        getDriver().findElement(By.xpath("//button[@formnovalidate='formNoValidate']")).submit();
        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id='description']/div[1]")).getText(), "");
    }

    @Ignore
    @Test
    public void testComputersMenu() {

        String[] computers = new String[]{"Desktops", "Notebooks", "Accessories"};

        getDriver().get("https://demowebshop.tricentis.com/");
        getDriver().findElement(By.xpath("//ul[@class='top-menu']//a[@href='/computers']")).click();

        List<WebElement> elements = getDriver().findElements(By.className("sub-category-item"));
        boolean actual = true;
        for (int i = 0; i < elements.size(); i++) {
            if (!computers[i].equals(elements.get(i).getText())) {
                actual = false;
                break;
            }
        }
        assertTrue(actual);
    }

    final String jobName = "Unicorns22";

    public void createNewJob() {

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//a[@href='/view/all/']")).isDisplayed();
        getDriver().findElement(By.name("name")).sendKeys(jobName);
        getDriver().findElement(By.xpath("//img[@class = 'icon-freestyle-project icon-xlg']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
    }

    public void deleteJob() {

        WebElement jobNameJenkins = getDriver().findElement(By.xpath("//h1[text() = contains(.,'Project')]"));
        if (jobNameJenkins.getText().equals(String.format("Project %s", jobName))) {
            jobNameJenkins.click();
            getDriver().findElement(By.xpath("//span[text() = 'Delete Project']")).click();
            Alert alert = getDriver().switchTo().alert();
            //String alertText = alert.getText();
            alert.accept();
        }
    }

    @Test
    public void testVerifyHomePageJenkins() {

        Assert.assertTrue(getDriver().findElement(By.xpath("//li/a[@class = 'model-link']")).isDisplayed());
    }

    @Test
    public void testCreateNewProjectJenkins() {

        createNewJob();

        Assert.assertEquals(getDriver().findElement(By.xpath("//li[@class = 'jenkins-breadcrumbs__list-item'][2]")).getText(), jobName);
        Assert.assertEquals(getDriver().findElement(By.xpath("//h1[text() = contains(.,'Project')]")).getText(), String.format("Project %s", jobName));
    }

    @Test
    public void testDeleteNewJobJenkins() {

        createNewJob();
        deleteJob();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1[text() = 'Welcome to Jenkins!']")).getText(), "Welcome to Jenkins!");
    }

    @Test
    public void testJenkinsVersion() {

        Assert.assertEquals(getDriver().findElement(By.xpath("//button[@class='jenkins-button jenkins-button--tertiary jenkins_ver']")).getText(), "Jenkins 2.414.2");
    }

    @Ignore
    @Test
    public void testAddDescriptionFeature() {
        String expected = "Testing description feature on Jenkins Home Page";
        WebDriver driver = getDriver();

        By descriptionButton = By.id("description-link");
        By textDescriptionArea = By.xpath("//textarea[@name='description']");
        By descriptionArea = By.cssSelector("#description > div:nth-child(1)");
        By saveDescriptionButton = By.name("Submit");

        //adding text to "Add description area" on Home page

        driver.findElement(descriptionButton).click();
        driver.findElement(textDescriptionArea).sendKeys(expected);
        driver.findElement(saveDescriptionButton).click();
        String actualResult = driver.findElement(descriptionArea).getText();
        assertEquals(actualResult, expected);

        //removing text

        driver.findElement(descriptionButton).click();
        driver.findElement(textDescriptionArea).sendKeys(Keys.chord(Keys.CONTROL, "a"));
        driver.findElement(textDescriptionArea).sendKeys(Keys.BACK_SPACE);
        driver.findElement(saveDescriptionButton).click();
        actualResult = driver.findElement(descriptionArea).getText();
        assertTrue(actualResult.isEmpty());
    }

    @Ignore //putting ignore, it's failing during CI check
    @Test
    public void testSubmit() {

        getDriver().get("https://chadd.org/for-adults/overview/");

        WebElement textBox = getDriver().findElement(By.name("EMAIL"));
        WebElement submitButton = getDriver().findElement(By.className("button"));

        textBox.sendKeys("ximotof590@ibtrades.com");
        submitButton.click();
    }

    @Ignore
    @Test
    public void testJenkinsAddDescr() {

        getDriver().findElement(By.id("description-link")).click();
        WebElement descriptionTextArea = getDriver().findElement(By.name("description"));
        boolean visible = descriptionTextArea.isDisplayed();
        assertTrue(visible);

        String descText = "Testing";

        descriptionTextArea.sendKeys(descText);

        getDriver().findElement(By.className("textarea-show-preview")).click();
        String actualText = getDriver().findElement(By.className("textarea-preview")).getText();
        Assert.assertEquals(descText, actualText);
    }

    @Ignore
    @Test
    public void testRaiffeisenBank() {
        final List<String> currnecyExpected = List.of("USD", "EUR", "GBP", "CHF", "JPY", "CNY");

        getDriver().get("https://www.raiffeisen.ru/currency_rates/");
        for (int i = 1; i < 7; i++) {
            WebElement currencyActual = getDriver().findElement(By.xpath("(//p[@data-marker='CurrencyRateTable.P'])[" + i + "]"));
            Assert.assertEquals(currencyActual.getText(), currnecyExpected.get(i - 1));
        }
    }

    @Ignore
    @Test
    public void testPearson() {
        getDriver().get("https://www.pearson.com/");

        getDriver().findElement(By.id("onetrust-accept-btn-handler")).click();

        getDriver().findElement(By.className("usernav-signin-button")).click();
        getDriver().findElement(By.className("side-banner__heading"));
        getDriver().findElement(By.className("ies-input")).sendKeys("tester@gmail.com");
        getDriver().findElement(By.id("password")).sendKeys("Test1234");
        getDriver().findElement(By.id("submitBttn")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("(//div[@class='alert'])[1]")).getText(), "We can't find an account with this email and password. Please try again.");
    }

    @Test
    public void testTasksInSideNavigation() {
        WebElement newItem = getDriver().findElement(By.xpath("//a[contains(@href, 'view/all/newJob')]"));
        Assert.assertEquals(newItem.getText(), "New Item");

        newItem.click();
        Assert.assertEquals(getDriver().getCurrentUrl(), "http://localhost:8080/view/all/newJob");

        Assert.assertEquals(getDriver().findElement(By.xpath("//label[@for='name']")).getText(), "Enter an item name");

        getDriver().findElement(By.xpath("//label[@for='name']")).click();
        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id='itemname-required']")).getText(), "» This field cannot be empty, please enter a valid name");

    }

    @Test
    public void testDashboardItems() {
        List<String> listOfExpectedItems = Arrays.asList("New Item", "People", "Build History", "Manage Jenkins", "My Views");
        List<WebElement> listOfDashboardItems = getDriver().findElements(By.xpath("//span[@class='task-link-text' and contains(., '')]"));

        List<String> extractedTexts = listOfDashboardItems.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());

        Assert.assertEquals(listOfExpectedItems, extractedTexts);
    }

    @Test
    public void testJenkinsCreateProject() {

        getDriver().findElement(By.xpath("(//*[@href = 'newJob'])")).click();
        getDriver().findElement(By.id("name")).sendKeys("MyFirstTestProject");
        getDriver().findElement(By.xpath("//span[text()='Freestyle project']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//textarea[@class='jenkins-input   ']")).sendKeys("This is my first test project");
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[text()='This is my first test project']")).getText(), "This is my first test project");

    }

    @Test
    public void testJenkinsDeleteProject() {

        testJenkinsCreateProject();

        getDriver().findElement(By.xpath("//span[text()='Delete Project']")).click();
        getDriver().switchTo().alert().accept();

        Assert.assertTrue(getDriver().findElements(By.xpath("//a[text()='MyFirstTestProject']")).isEmpty());

    }


    @Test
    public void testSearchFieldWithoutResultsExpected() {
        final String searchRequest = "Incorrect search request";
        final String expectedErrorMessage = "Nothing seems to match.";
        WebDriver driver = getDriver();

        driver.findElement(By.id("search-box")).sendKeys(searchRequest + Keys.ENTER);
        WebElement errorMessageField = null;
        try {
            errorMessageField = driver.findElement(By.className("error"));
        } catch (NoSuchElementException e) {
            Assert.fail();
        }
        String errorMessage = errorMessageField.getText();
        assertEquals(errorMessage, expectedErrorMessage);
    }

    @Test
    public void testCreateNewJob() {

        final String JOB_NAME = "UnicornProject";

        getDriver().findElement(By.className("content-block__link")).click();
        getDriver().findElement(By.id("name")).sendKeys("UnicornProject");
        getDriver().findElement(By.xpath("//span[text()='Freestyle project']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//textarea[@name='description']")).sendKeys("First Project");
        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.xpath("//li/a[@href='/']")).click();
        getDriver().findElement(By.className("inside")).click();

        String createdJobName = getDriver().findElement(By.xpath("//div[@id='main-panel']/h1")).getText();

        Assert.assertEquals(createdJobName, String.format("Project %s", JOB_NAME));
    }

    final String PROJECTNAME = "Project 07";

    private void createNewProject() {
        getDriver().findElement(By.xpath("(//a[@href = '/view/all/newJob'])")).click();

        getDriver().findElement(By.xpath("//input[@name = 'name']")).sendKeys(PROJECTNAME);
        getDriver().findElement(By.xpath("//span[contains(text(), 'Freestyle project')]")).click();
        getDriver().findElement(By.id("ok-button")).click();
    }

    @Test
    public void testCheckJenkinsVersion() {
        final String VERSION = "Jenkins 2.414.2";
        String version = getDriver().findElement(By.cssSelector("button[type = 'button']")).getText();
        Assert.assertEquals(version, VERSION);
    }

    @Test
    public void testNewFreestyleProjectIsCreated() throws InterruptedException {
        createNewProject();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
        getDriver().findElement(By.xpath("//div[@id='breadcrumbBar']//a[@class='model-link']")).click();
        WebElement projectsList = getDriver().findElement(By.xpath("//table[@id='projectstatus']"));
        String[] array = projectsList.getText().split("\n");

        boolean isCreated = Arrays.asList(array).contains(PROJECTNAME);
        Assert.assertTrue(isCreated);
    }

    @Test
    public void testDescriptionPreviewHidePreview() throws InterruptedException {
        createNewProject();
        String projectDescription = "Project Description of " + PROJECTNAME;
        getDriver().findElement(By.xpath("//textarea[@name = 'description']")).sendKeys(projectDescription);
        getDriver().findElement(By.xpath("//a[@class = 'textarea-show-preview']")).click();
        String previewProjectDescription = getDriver().findElement(By.className("textarea-preview")).getText();

        Assert.assertEquals(projectDescription, previewProjectDescription);
    }

    @Test
    public void testDiscardOldBuildsCheckStrategyVisible() throws InterruptedException {
        createNewProject();
        getDriver().findElement(By.xpath("//input[@id='cb4']/parent::span")).click();

        boolean strategyIsVisible = getDriver().findElement(By.xpath("//div[@class='optionalBlock-container jenkins-form-item jenkins-form-item--tight']//div[@class='form-container tr']")).isDisplayed();
        assertTrue(strategyIsVisible);
    }

    @Test
    public void testDiscardOldBuildsCheckDaysToKeepBuildsClickableAndSaves() throws InterruptedException {

        String sendKeys = "120";

        createNewProject();
        getDriver().findElement(By.xpath("//input[@id='cb4']/parent::span")).click();
        getDriver().findElement(By.xpath("//div[@class='setting-main']/input[@name= '_.daysToKeepStr']")).sendKeys(sendKeys);
        getDriver().findElement(By.xpath("//div[@class='setting-main']/input[@name= '_.numToKeepStr']")).sendKeys(sendKeys);

        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        getDriver().findElement(By.xpath("//div[@id='tasks']/div[5]")).click();

        String resultAfterSaving1 = getDriver().findElement(By.xpath("//div[@class='setting-main']/input[@name= '_.daysToKeepStr']")).getAttribute("value");
        String resultAfterSaving2 = getDriver().findElement(By.xpath("//div[@class='setting-main']/input[@name= '_.numToKeepStr']")).getAttribute("value");

        Assert.assertEquals(resultAfterSaving1, sendKeys);
        Assert.assertEquals(resultAfterSaving2, sendKeys);
    }

    @Test
    public void testJenkinsNewItemTitle() {

        getDriver().findElement(By.xpath("(//*[@class = 'task-icon-link']) [1]")).click();
        String expectedTitle = "New Item [Jenkins]";

        Assert.assertEquals(getDriver().getTitle(), expectedTitle);
    }

    @Test
    public void testJenkinsSearchButton() {

        getDriver().findElement(By.className("main-search__icon-trailing")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//*[@id='search-box']")).getText(), "Search Box");
    }
}