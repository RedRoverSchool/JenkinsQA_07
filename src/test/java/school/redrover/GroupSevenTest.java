package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;
import java.util.ArrayList;


public class GroupSevenTest extends BaseTest {

    @Test
    public void testJobCreation() {

        WebElement createJobButton = getDriver().findElement(By.xpath("//a[@href='newJob']"));
        createJobButton.click();

        WebElement itemName = getDriver().findElement(By.xpath("//input[@class='jenkins-input']"));
        itemName.sendKeys("First test");

        WebElement freestyleProject = getDriver().findElement(By.xpath("//li[@class='hudson_model_FreeStyleProject']//label"));
        freestyleProject.click();

        WebElement submitButton = getDriver().findElement(By.xpath("//button[@id='ok-button']"));
        submitButton.click();

        WebElement saveButton = getDriver().findElement(By.xpath("//button[@name='Submit']"));
        saveButton.click();

        WebElement projectTitle = getDriver().findElement(By.xpath("//h1[@class='job-index-headline page-headline']"));
        Assert.assertTrue(projectTitle.isDisplayed());
    }

    @Ignore
    @Test
    public void TestBddSearch() {
        WebDriver driver = getDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));
        driver.get("https://duckduckgo.com/");
        WebElement searchBox = driver.findElement(By.xpath("//input[@class = 'searchbox_input__bEGm3']"));
        searchBox.sendKeys("bdd");
        WebElement searchButton = driver.findElement(By.xpath("//button[@class = 'searchbox_searchButton__F5Bwq iconButton_button__6x_9C']"));
        searchButton.click();
        WebElement searchResult = driver.findElement(By.xpath("//h2[@class = 'Ee2e63EzQ9F3xq9wsGDY']"));
        String resultText = searchResult.getText();
        Assert.assertTrue(resultText.contains("Behavior-driven development"));
    }

    @Ignore
    @Test
    public void testSearch() {

        getDriver().get("https://elitetransit.com/");
        WebElement buttonContact = getDriver().findElement(By.xpath("//ul[@id='top-menu']//a[normalize-space()='Contact']"));
        buttonContact.click();

        String title = getDriver().getTitle();

        Assert.assertEquals(title, "Contact | ELITE Transit Solutions");
    }

    @Ignore
    @Test
    public void testTextInput() {

        getDriver().get("https://www.selenium.dev/selenium/web/web-form.html");
        WebElement input = getDriver().findElement(By.id("my-text-id"));
        input.click();
        input.sendKeys("Selenium");

        WebElement submit = getDriver().findElement(By.tagName("button")); ////button[@type='submit']
        submit.submit();
        WebElement message = getDriver().findElement(By.id("message"));
        Assert.assertEquals(message.getText(), "Received!");
    }

    @Ignore
    @Test
    public void testSearchAB() {

        WebDriver driver = new ChromeDriver();
        driver.get("https://duckduckgo.com/");

        String title = driver.getTitle();
        Assert.assertEquals(title, "DuckDuckGo — Privacy, simplified.");

        WebElement textBox = driver.findElement(By.name("q"));
        WebElement submitButton = driver.findElement(By.xpath("//*[@aria-label='Search']"));

        textBox.sendKeys("Wikipedia");
        submitButton.click();

        WebElement wikiName = driver.findElement(By.xpath("(//*[@data-testid='result-title-a'])[1]"));
        String wikiName2 = wikiName.getText();
        Assert.assertEquals(wikiName2, "Wikipedia");

        driver.quit();
    }

    @Ignore
    @Test
    public void testLinks() throws InterruptedException {

        WebDriver driver = new ChromeDriver();
        driver.get("https://duckduckgo.com/");

        WebElement textBox = driver.findElement(By.name("q"));
        WebElement submitButton = driver.findElement(By.xpath("//*[@aria-label='Search']"));

        textBox.sendKeys("Wikipedia");
        submitButton.click();

        Thread.sleep(5000);

        int linkCount = driver.findElements(By.xpath("//*[@data-testid='result-extras-url-link']")).size();

        Assert.assertEquals(linkCount, 10);

        driver.quit();
    }

    @Ignore
    @Test
    public void testHPSearch() {
        getDriver().get("https://www.wizardingworld.com/");

        WebElement searchActivation = getDriver().findElement(By.xpath("//button[@name='search']"));
        searchActivation.click();

        WebElement searchField = getDriver().findElement(By.xpath("//input[@placeholder='Search']"));
        searchField.sendKeys("Harry Potter", Keys.RETURN);

        WebElement searchResults = getDriver().findElement(By.xpath("//h3[text()='Harry Potter']"));
        searchResults.click();

        ArrayList<String> wid = new ArrayList<>(getDriver().getWindowHandles());
        getDriver().switchTo().window(wid.get(1));

        WebElement resultHeader = getDriver().findElement(By.xpath("//h1"));
        Assert.assertEquals(resultHeader.getText(), "Harry Potter");
    }

    @Test
    public void testJenkinsAbout() {

        WebElement bottomRightButtonExpand = getDriver().findElement(By.xpath("//button[normalize-space()='Jenkins 2.414.2']"));
        bottomRightButtonExpand.click();

        WebElement aboutJenkins = getDriver().findElement(By.xpath("//a[normalize-space()='About Jenkins']"));
        aboutJenkins.click();

        WebElement resultHeader = getDriver().findElement(By.xpath("//h1[normalize-space()='Jenkins']"));
        Assert.assertEquals(resultHeader.getText(), "Jenkins");

        WebElement version = getDriver().findElement(By.xpath("//p[@class='app-about-version']"));
        Assert.assertEquals(version.getText(), "Version 2.414.2");

        WebElement checkOnWhatPage = getDriver().findElement(By.xpath("(//li[@class='jenkins-breadcrumbs__list-item'])[3]"));
        Assert.assertEquals(checkOnWhatPage.getText(), "About Jenkins");

    }



    @Ignore
    @Test
    public void TestYMCA() {

        getDriver().get("https://ymcacapecod.org/");

        WebElement textBox = getDriver().findElement(By.className("field"));
        WebElement SearchButton = getDriver().findElement(By.className("submit"));

        textBox.sendKeys("pool");
        SearchButton.click();

        WebElement findelement = getDriver().findElement(By.xpath("//*[@id=\"folio\"]/nav/ul/li[2]/a"));
        findelement.click();

        WebElement text = getDriver().findElement(By.xpath("//*[@id=\"content\"]/article/p[4]/strong/a"));
        text.click();

        String value = text.getText();
        Assert.assertEquals(value, "CLICK HERE TO REGISTER ONLINE!");
    }



    @Ignore
    @Test
    public void testTitle() {

        getDriver().get("https://www.psafe.com/");
        String title = getDriver().getTitle();
        Assert.assertEquals(title, "PSafe | Leading provider of mobile privacy, security, and performance apps");

        WebElement enterHomeButton = getDriver().findElement(By.linkText("Home"));
        enterHomeButton.click();

        String footer = getDriver().findElement(By.xpath("//a[@href = 'https://www.psafe.com/dfndr/']")).getText();
        String expectedText = "Home";
        Assert.assertEquals(footer, expectedText);
    }

    @Ignore
    @Test
    public void testDatalist() {

        getDriver().get("https://www.selenium.dev/selenium/web/web-form.html");

        WebElement readonly = getDriver().findElement(By.name("my-readonly"));
        readonly.click();
        String text = readonly.getAccessibleName();
        Assert.assertEquals(text, "Readonly input");

    }

    @Ignore
    @Test
    public void testDatePicker() {

        getDriver().get("https://www.selenium.dev/selenium/web/web-form.html");

        WebElement myDate = getDriver().findElement(By.name("my-date"));
        myDate.click();

        WebElement weekDay = getDriver().findElement(By.xpath("//thead/tr[3]/th[1]"));
        String text = weekDay.getText();
        Assert.assertEquals(text, "Su");

    }

    @Test
    public void testToolsSearch() {
        String title = getDriver().getTitle();
        Assert.assertEquals(title, "Dashboard [Jenkins]");

        getDriver().findElement(By.xpath("//a[@href='/manage']")).click();

        WebElement searchBox = getDriver().findElement(By.id("settings-search-bar"));

        searchBox.sendKeys("tools");
        getDriver().findElement(By.xpath("//a[@class='jenkins-search__results-item--selected']")).click();

        WebElement message = getDriver().findElement(By.xpath("//h1[text()='Tools']"));
        String value = message.getText();
        Assert.assertEquals(value, "Tools");
    }

    @Ignore
    @Test
    public void testInventoryPage() {
        getDriver().get("https://www.saucedemo.com/inventory.html");
        WebElement usernameInput = getDriver().findElement(By.id("user-name"));
        WebElement passwordInput = getDriver().findElement(By.id("password"));
        WebElement loginButton = getDriver().findElement(By.id("login-button"));

        usernameInput.sendKeys("standard_user");
        passwordInput.sendKeys("secret_sauce");
        loginButton.click();

        WebElement productsPageTitle = getDriver().findElement(By.className("title"));
        Assert.assertTrue(productsPageTitle.isDisplayed(), "Not on product page");


    }

    @Test
    public void testUserPage() {

        WebElement userIcon = getDriver().findElement(By.xpath("//a[@href='/user/admin']"));
        userIcon.click();

        WebElement nameTitle = getDriver().findElement(By.xpath("//h1[normalize-space()='admin']"));
        String value = nameTitle.getText();
        Assert.assertEquals(value, "admin");
    }

    @Ignore
    @Test
    public void testSY() throws InterruptedException {
        getDriver().get("https://animevost.org/");
        WebElement textBox = getDriver().findElement(By.id("story"));
        textBox.sendKeys("Токийский Гуль");
        WebElement searchButton = getDriver().findElement(By.className("searchButton"));
        searchButton.click();
        Thread.sleep(5000);
        WebElement searchText = getDriver().findElement(By.id("dosearch"));
        String title = searchText.getAttribute("value");
        Assert.assertEquals(title, "Начать поиск");
    }

    @Test
    public void testSearchBar() {

        WebElement searchBar = getDriver().findElement(By.xpath("//input[@id='search-box']"));
        searchBar.click();
        searchBar.sendKeys("configure");
        searchBar.sendKeys(Keys.ENTER);

        WebElement configureTitle = getDriver().findElement(By.xpath("//h1[normalize-space()='System']"));
        String value = configureTitle.getText();
        Assert.assertEquals(value, "System");
    }

    @Test
    public void testCreateNewProject() {
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();

        WebElement itemName = getDriver().findElement(By.xpath("//input[@class='jenkins-input']"));

        itemName.sendKeys("1st project");
        getDriver().findElement(By.xpath("//span[contains(text(),'Freestyle project')]")).click();
        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        WebElement message = getDriver().findElement(By.xpath("//h1[@class='job-index-headline page-headline']"));
        String value = message.getText();
        Assert.assertEquals(value, "Project 1st project");
    }

    @Test
    public void testCreatePipeline() {
        final String PIPELINE_NAME = "New_Pipeline";
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();

        getDriver().findElement(By.id("name")).sendKeys(PIPELINE_NAME);
        getDriver().findElement(By.xpath("//span[text() = 'Pipeline']")).click();
        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.name("Submit")).click();

        getDriver().findElement(By.xpath("//li[@class = 'jenkins-breadcrumbs__list-item']/a[@href='/']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//a[@class='jenkins-table__link model-link inside']")).getText(), PIPELINE_NAME);
    }

    @Test
    public void testDeletePipeline() {
        final String PIPELINE_NAME = "New_Pipeline";

        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();

        getDriver().findElement(By.id("name")).sendKeys(PIPELINE_NAME);
        getDriver().findElement(By.xpath("//span[text() = 'Pipeline']")).click();
        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        getDriver().findElement(By.xpath("//li[@class = 'jenkins-breadcrumbs__list-item']/a[@href='/']")).click();

        getDriver().findElement(By.xpath("//td/a[@href='job/" + PIPELINE_NAME + "/']")).click();
        getDriver().findElement(By.xpath("//span[contains(text(),'Delete')]")).click();

        getDriver().switchTo().alert().accept();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(), "Welcome to Jenkins!");
    }

    @Test
    public void testCreatePipelineWithDisplayName() {
        final String PIPELINE_NAME = "New_Pipeline";
        final String NEW_NAME = "NewNamePipeline";

        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();

        getDriver().findElement(By.id("name")).sendKeys(PIPELINE_NAME);
        getDriver().findElement(By.xpath("//span[text() = 'Pipeline']")).click();
        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(
                By.xpath("//div[@id='advanced-project-options']/following-sibling::div//button[@class='jenkins-button advanced-button advancedButton']")).sendKeys(Keys.RETURN);
        getDriver().findElement(By.xpath("//input[@name='_.displayNameOrNull']")).sendKeys(NEW_NAME);
        getDriver().findElement(By.name("Submit")).click();

        String pipelineHeader = getDriver().findElement(By.xpath("//h1")).getText();

        getDriver().findElement(By.xpath("//li[@class = 'jenkins-breadcrumbs__list-item']/a[@href='/']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//a[@class='jenkins-table__link model-link inside']")).getText(), NEW_NAME);
        Assert.assertTrue(pipelineHeader.contains(NEW_NAME));
    }
}
