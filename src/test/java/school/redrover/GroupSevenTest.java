package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.JenkinsUtils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;


public class GroupSevenTest extends BaseTest {
    @Test
    public void testKylieTitle() {
        getDriver().get("https://kyliecosmetics.com/");

        String title = getDriver().getTitle();

        Assert.assertEquals(title, "Kylie Cosmetics by Kylie Jenner | Kylie Skin | Kylie Baby");
    }

    @Test
    public void testSearchField() {

        getDriver().get("https://kyliecosmetics.com/collections/kylie-cosmetics");

        WebElement searchButton = getDriver().findElement(By.xpath("//a[@title='Search']"));
        searchButton.click();

        WebElement searchInput = getDriver().findElement(By.xpath("//input[@id='SearchForm-Header-Query']"));
        searchInput.sendKeys("lip kit");

        WebElement searchButtonNext = getDriver().findElement(By.xpath("//button[@id='SearchForm-Header-Submit']"));
        searchButtonNext.click();

        WebElement title = getDriver().findElement(By.xpath("//h1[normalize-space()='Search']"));
        String value = title.getText();
        Assert.assertEquals(value, "SEARCH");
    }

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
        WebDriver driver = new ChromeDriver();
        try {
            driver.get("https://elitetransit.com/");

            driver.manage().window().maximize();

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1000));
            WebElement buttonContact = driver.findElement(By.xpath("//ul[@id='top-menu']//a[normalize-space()='Contact']"));
            buttonContact.click();
            String title = driver.getTitle();

            Assert.assertEquals(title, "Contact | ELITE Transit Solutions");
        } finally {
            driver.quit();
        }
    }

    @Ignore
    @Test
    public void testTextInput() {

        WebDriver driver = new ChromeDriver();
        try {
            driver.get("https://www.selenium.dev/selenium/web/web-form.html");

            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(800));

            WebElement input = driver.findElement(By.id("my-text-id"));
            input.click();
            input.sendKeys("Selenium");

            WebElement submit = driver.findElement(By.tagName("button")); ////button[@type='submit']
            submit.submit();
            WebElement message = driver.findElement(By.id("message"));
            Assert.assertEquals(message.getText(), "Received!");
        } finally {
            driver.quit();
        }
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


    @Test
    public void testPricePageHeader() {
        getDriver().get("https://megagroup.by/");

        WebElement price = getDriver().findElement(By.xpath("//nav[@class='mp-header__nav']/a[@href='/price']"));
        price.click();

        WebElement header = getDriver().findElement(By.xpath("//h1"));
        Assert.assertEquals(header.getText(), "Стоимость сайтов");
    }


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
        JenkinsUtils.login(getDriver());

        WebElement bottomRightButtonExpand = getDriver().findElement(By.xpath("//button[normalize-space()='Jenkins 2.414.2']"));
        bottomRightButtonExpand.click();

        WebElement aboutJenkins = getDriver().findElement(By.xpath("//a[normalize-space()='About Jenkins']"));
        aboutJenkins.click();

        WebElement resultHeader = getDriver().findElement(By.xpath("//h1[normalize-space()='Jenkins']"));
        Assert.assertEquals(resultHeader.getText(), "Jenkins");

        WebElement version = getDriver().findElement(By.xpath("//p[@class='app-about-version']"));
        Assert.assertEquals(version.getText(),"Version 2.414.2");

        WebElement checkOnWhatPage = getDriver().findElement(By.xpath("(//li[@class='jenkins-breadcrumbs__list-item'])[3]"));
        Assert.assertEquals(checkOnWhatPage.getText(),"About Jenkins");

    }

    @Test
    public void testBestBrainsSearch() throws InterruptedException {

        getDriver().get("https://bestbrains.com/");

        String title = getDriver().getTitle();
        Assert.assertEquals(title, "Best Brains: Be Your Best!");

        // driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        WebElement textBox = getDriver().findElement(By.xpath("//input[@placeholder='Enter Your Zip/Postal Code']"));
        WebElement submitButton = getDriver().findElement(By.xpath("//button[@class = 'btn btn-white']"));
        textBox.sendKeys("29707");
        submitButton.click();

        Thread.sleep(5000);
        WebElement message = getDriver().findElement(By.xpath("//p[@class = 'address']"));
        String value = message.getText();
        Assert.assertEquals(value, "17206 Lancaster Hwy, STE 504, Charlotte, NC-28277");


        String title1 = getDriver().getTitle();
        Assert.assertEquals(title1, "Best Brains Center Locations");


        WebElement location = getDriver().findElement(By.xpath("//h1[@class = 'text-center']"));
        Thread.sleep(1000);
        String value1 = location.getText();
        Assert.assertEquals(value1, "Find your nearest center to schedule a FREE placement test and orientation.");


        WebElement ballantyneLink = getDriver().findElement(By.xpath("//a[@href = '/ballantyne']"));
        ballantyneLink.click();
        WebElement ballantyneText = getDriver().findElement(By.xpath("//span[@class = 'd-inline-block']"));
        String valueBallantyneText = ballantyneText.getText();
        Assert.assertEquals(valueBallantyneText, "Ballantyne");


        WebElement registration = getDriver().findElement(By.xpath("//a[@href = '/new-registration']"));
        Thread.sleep(1000);
        registration.click();


        String titleRegistration = getDriver().getTitle();
        Assert.assertEquals(titleRegistration, "Student Registration | Best Brains");
        Thread.sleep(1000);

        WebElement zipCode = getDriver().findElement(By.xpath("//input[@id = 'zipcode' ]"));
        zipCode.sendKeys("29707");


        Select drpCenters = new Select(getDriver().findElement(By.name("locationId")));


        boolean isMultiple = drpCenters.isMultiple();
        Thread.sleep(5000);

        if (isMultiple) {
            System.out.println("The dropdown allows multiple selections.");
        } else {
            System.out.println("The dropdown allows only single selection.");
        }
        Thread.sleep(1000);

        WebElement lastNameField = getDriver().findElement(By.name("lastName"));
        Thread.sleep(1000);
        String nameAttributeValue = lastNameField.getAttribute("name");

        if (nameAttributeValue.equals("lastName")) {
            System.out.println("Элемент представляет поле 'last name'.");
        } else {
            System.out.println("Элемент не представляет поле 'last name'.");
        }
    }


    @Test
    public void testBooksSearch() {
        getDriver().get("https://www.doylestownbookshop.com/");

        String title = getDriver().getTitle();
        Assert.assertEquals(title, "The Doylestown Bookshop |");

        WebElement textBox = getDriver().findElement(By.id("edit-search-block-form--2"));
        WebElement searchButton = getDriver().findElement(By.id("edit-submit"));

        textBox.sendKeys("Making it so");
        searchButton.click();

        WebElement message = getDriver().findElement(By.id("b-9781982167738"));
        String value = message.getText();
        Assert.assertEquals(value, "Making It So: A Memoir");
    }

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
    public void testKumon() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.kumon.com/");


        WebElement locationButton = driver.findElement(By.xpath("//*[@href = '/locations']"));
        locationButton.click();
        Thread.sleep(1000);

        String titleLocation = driver.getTitle();
        Thread.sleep(1000);
        Assert.assertEquals(titleLocation, "Find Kids' Learning Centers - Kumon Locations");


        WebElement inputButton = driver.findElement(By.xpath("//input[@type = 'text']"));
        inputButton.click();
        Thread.sleep(1000);


        WebElement locationText = driver.findElement(By.xpath("//h4[text()='INDIAN LAND']"));
        String getText = locationText.getText();
        Assert.assertEquals(getText, "INDIAN LAND");

        WebElement schedulerButton = driver.findElement(By.xpath("//a[@href = '/indian-land/scheduler']"));
        Thread.sleep(1000);
        schedulerButton.click();
        Thread.sleep(5000);
        String parentWindowHandle = driver.getWindowHandle(); //это метод в библиотеке Selenium WebDriver,
        // который используется для получения идентификатора (handle) текущего окна или вкладки браузера.
        for (String windowHandle : driver.getWindowHandles()) {
            if (!windowHandle.equals(parentWindowHandle)) {
                driver.switchTo().window(windowHandle);
                return; // Завершить метод и вернуться
            }
        }

        String schedulerTitle = driver.getTitle();

        Assert.assertEquals(schedulerTitle, "Book Appointment | Kumon of  INDIAN LAND");

        driver.quit();

    }

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

    @Test
    public void testDatalist() {

        getDriver().get("https://www.selenium.dev/selenium/web/web-form.html");

        WebElement readonly = getDriver().findElement(By.name("my-readonly"));
        readonly.click();
        String text = readonly.getAccessibleName();
        Assert.assertEquals(text, "Readonly input");

    }

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
        JenkinsUtils.login(getDriver());
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
    }
