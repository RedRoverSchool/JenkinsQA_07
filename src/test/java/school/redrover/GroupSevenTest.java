package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;

public class GroupSevenTest {
    @Test
    public void kylieTitleTest() {
        WebDriver driver = new ChromeDriver();

        driver.get("https://kyliecosmetics.com/");
        String title = driver.getTitle();
        Assert.assertEquals(title, "Kylie Cosmetics by Kylie Jenner | Kylie Skin | Kylie Baby");

        driver.quit();
    }

    @Test
    public void searchTest() throws InterruptedException {
        WebDriver driver = new FirefoxDriver();
        try {
            driver.get("https://kyliecosmetics.com/collections/kylie-cosmetics");

            Thread.sleep(1000);

            WebElement searchButton = driver.findElement(By.xpath("//a[@title='Search']"));
            searchButton.click();

            WebElement searchInput = driver.findElement(By.xpath("//input[@id='SearchForm-Header-Query']"));
            searchInput.sendKeys("lip kit");

            WebElement searchButtonNext = driver.findElement(By.xpath("//button[@id='SearchForm-Header-Submit']"));
            searchButtonNext.click();

            WebElement title = driver.findElement(By.xpath("//h1[normalize-space()='Search']"));
            String value = title.getText();
            Assert.assertEquals(value, "SEARCH");

        } finally {
            driver.quit();
        }
    }

    @Test
    public void TestBddSearch() {
        WebDriver driver = new ChromeDriver();
        try {
            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));
            driver.get("https://duckduckgo.com/");
            WebElement searchBox = driver.findElement(By.xpath("//input[@class = 'searchbox_input__bEGm3']"));
            searchBox.sendKeys("bdd");
            WebElement searchButton = driver.findElement(By.xpath("//button[@class = 'searchbox_searchButton__F5Bwq iconButton_button__6x_9C']"));
            searchButton.click();
            WebElement searchResult = driver.findElement(By.xpath("//h2[@class = 'Ee2e63EzQ9F3xq9wsGDY']"));
            String resultText = searchResult.getText();
            Assert.assertTrue(resultText.contains("Behavior-driven development"));
        } finally {
            driver.quit();
        }
    }

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
    public void testPricePageHeader() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        try {
            driver.get("https://megagroup.by/");
            driver.manage().window().maximize();
            Thread.sleep(1500);

            WebElement price = driver.findElement(By.xpath("//nav[@class='mp-header__nav']/a[@href='/price']"));
            price.click();

            Thread.sleep(1500);
            WebElement header = driver.findElement(By.xpath("//h1"));
            Assert.assertEquals(header.getText(), "Стоимость сайтов");
        } finally {
            driver.quit();
        }
    }

    @Test
    public void testHPSearch() {
        WebDriver driver = new ChromeDriver();
        try {
            driver.get("https://www.wizardingworld.com/");
            WebElement hamBurgerMenu = driver.findElement(By.xpath("//*[@id='hamBurgerMenu']"));
            hamBurgerMenu.click();
            WebElement searchActivation = driver.findElement(By.xpath("//button[@data-testid='navSearchButton']"));
            searchActivation.click();
            WebElement searchField = driver.findElement(By.xpath("//input[@name='Search']"));
            searchField.sendKeys("Harry Potter");
            WebElement searchButton = driver.findElement(By.xpath(" //button[@name='Search button']"));
            searchButton.click();
            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(8000));
            WebElement searchResults = driver.findElement(By.xpath("//*[@id=\"__next\"]/div[2]/div/div[6]/div/div[3]/div[2]/div[2]/ul/li[4]/article/a/div[2]"));
            searchResults.click();
            ArrayList<String> wid = new ArrayList<>(driver.getWindowHandles());
            driver.switchTo().window(wid.get(1));
            WebElement resultHeader = driver.findElement(By.xpath("//h1"));
            Assert.assertEquals(resultHeader.getText(), "Harry Potter");
        } finally {
            driver.quit();
        }
    }

    @Test
    public void testBestBrainsSearch() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://bestbrains.com/");

        String title = driver.getTitle();
        Assert.assertEquals(title, "Best Brains: Be Your Best!");

        // driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        WebElement textBox = driver.findElement(By.xpath("//input[@placeholder='Enter Your Zip/Postal Code']"));
        WebElement submitButton = driver.findElement(By.xpath("//button[@class = 'btn btn-white']"));
        textBox.sendKeys("29707");
        submitButton.click();

        Thread.sleep(5000);
        WebElement message = driver.findElement(By.xpath("//p[@class = 'address']"));
        String value = message.getText();
        Assert.assertEquals(value, "17206 Lancaster Hwy, STE 504, Charlotte, NC-28277");


        String title1 = driver.getTitle();
        Assert.assertEquals(title1, "Best Brains Center Locations");


        WebElement location = driver.findElement(By.xpath("//h1[@class = 'text-center']"));
        Thread.sleep(1000);
        String value1 = location.getText();
        Assert.assertEquals(value1, "Find your nearest center to schedule a FREE placement test and orientation.");


        WebElement ballantyneLink = driver.findElement(By.xpath("//a[@href = '/ballantyne']"));
        ballantyneLink.click();
        WebElement ballantyneText = driver.findElement(By.xpath("//span[@class = 'd-inline-block']"));
        String valueBallantyneText = ballantyneText.getText();
        Assert.assertEquals(valueBallantyneText, "Ballantyne");


        WebElement registration = driver.findElement(By.xpath("//a[@href = '/new-registration']"));
        Thread.sleep(1000);
        registration.click();


        String titleRegistration = driver.getTitle();
        Assert.assertEquals(titleRegistration, "Student Registration | Best Brains");
        Thread.sleep(1000);

        WebElement zipCode = driver.findElement(By.xpath("//input[@id = 'zipcode' ]"));
        zipCode.sendKeys("29707");


        Select drpCenters = new Select(driver.findElement(By.name("locationId")));


        boolean isMultiple = drpCenters.isMultiple();
        Thread.sleep(5000);

        if (isMultiple) {
            System.out.println("The dropdown allows multiple selections.");
        } else {
            System.out.println("The dropdown allows only single selection.");
        }
        Thread.sleep(1000);

        WebElement lastNameField = driver.findElement(By.name("lastName"));
        Thread.sleep(1000);
        String nameAttributeValue = lastNameField.getAttribute("name");

        if (nameAttributeValue.equals("lastName")) {
            System.out.println("Элемент представляет поле 'last name'.");
        } else {
            System.out.println("Элемент не представляет поле 'last name'.");
        }

        driver.quit();
    }

    @Test
    public void testBookSearch() {
        WebDriver driver = new ChromeDriver();

        driver.get("https://www.doylestownbookshop.com/");

        String title = driver.getTitle();
        Assert.assertEquals(title, "The Doylestown Bookshop |");

        WebElement textBox = driver.findElement(By.id("edit-search-block-form--2"));
        WebElement searchButton = driver.findElement(By.id("edit-submit"));

        textBox.sendKeys("Making it so");
        searchButton.click();

        WebElement message = driver.findElement(By.id("b-9781982167738"));
        String value = message.getText();
        Assert.assertEquals(value, "Making It So: A Memoir");

        driver.quit();
    }

    @Test
    public void YMCATest() {

        WebDriver driver = new FirefoxDriver();
        try {
            driver.get("https://ymcacapecod.org/");

            WebElement textBox = driver.findElement(By.className("field"));
            WebElement SearchButton = driver.findElement(By.className("submit"));

            textBox.sendKeys("pool");
            SearchButton.click();

            WebElement findelement = driver.findElement(By.xpath("//*[@id=\"folio\"]/nav/ul/li[2]/a"));
            findelement.click();

            WebElement text = driver.findElement(By.xpath("//*[@id=\"content\"]/article/p[4]/strong/a"));
            text.click();

            String value = text.getText();
            Assert.assertEquals(value, "CLICK HERE TO REGISTER ONLINE!");
        } finally {
            driver.quit();
        }
    }
}
