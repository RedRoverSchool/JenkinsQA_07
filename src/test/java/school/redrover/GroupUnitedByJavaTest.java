package school.redrover;

import jdk.jfr.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import school.redrover.runner.BaseTest;

import java.time.Duration;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;


public class GroupUnitedByJavaTest extends BaseTest {

    private static final String SAUCEDEMO_URL = "https://www.saucedemo.com/";
    private static final String USERNAME_LOCATOR = "//input[@placeholder='Username']";
    private static final String PASSWORD_LOCATOR = "//input[@placeholder='Password']";
    private static final String SUBMIT_BUTTON ="//input[@type='submit']";

    @Test
    public void testDemoqaElementsRedirection() throws InterruptedException {

        getDriver().get("https://demoqa.com/");

        String title = getDriver().getTitle();
        Assert.assertEquals(title, "DEMOQA");

        WebElement elementsButton = getDriver().findElement(By.cssSelector(".top-card:nth-child(1)"));
        elementsButton.click();

        String currentUrl = getDriver().getCurrentUrl();
        String elementsUrl = "https://demoqa.com/elements";
        Assert.assertEquals(currentUrl, elementsUrl);
        Thread.sleep(2000);
    }

    public static class DataProviders {

        @DataProvider(name = "validPasswordAndName")
        public static String[][] validPasswordAndName(){
            return new String[][]{
                    {"standard_user","secret_sauce"},
                    {"problem_user","secret_sauce"}
            };
        }

        @DataProvider(name = "inValidPasswordOrName")

        public static String[][] inValidPasswordOrName(){
            return new String[][]{
                    {"user","secret_sauce"},
                    {"standard_user","password"}
            };
        }

        @DataProvider(name = "EmptyPasswordOrName")
        public static String[][] EmptyPasswordOrName(){
            return new String[][]{
                    {"","secret_sauce", "incorrect_username"},
                    {"performance_glitch_user","", "incorrect_password"}
            };
        }
    }

    @Test(dataProvider = "validPasswordAndName", dataProviderClass = GroupUnitedByJavaTest.DataProviders.class)
    @Description("Login with correct username and password")
    public void testLoginWithCorrectData(String username, String password) {

        getDriver().get(SAUCEDEMO_URL);

        getDriver().findElement(By.xpath(USERNAME_LOCATOR)).sendKeys(username);
        getDriver().findElement(By.xpath(PASSWORD_LOCATOR)).sendKeys(password);
        getDriver().findElement(By.xpath(SUBMIT_BUTTON)).click();

        assertEquals(getDriver().getCurrentUrl(), "https://www.saucedemo.com/inventory.html");
    }


    @Test(dataProvider = "inValidPasswordOrName", dataProviderClass = GroupUnitedByJavaTest.DataProviders.class)
    @Description("Login with incorrect username and password")
    public void TestLoginWithIncorrectData(String username, String password) {

        getDriver().get(SAUCEDEMO_URL);

        getDriver().findElement(By.xpath(USERNAME_LOCATOR)).sendKeys(username);
        getDriver().findElement(By.xpath(PASSWORD_LOCATOR)).sendKeys(password);
        getDriver().findElement(By.xpath(SUBMIT_BUTTON)).click();

        String errorMessage = "Epic sadface: Username and password do not match any user in this service";

        assertEquals(errorMessage, getDriver().findElement(By.xpath("//h3[@data-test='error']")).getText());
    }


    @Test(dataProvider = "EmptyPasswordOrName", dataProviderClass = GroupUnitedByJavaTest.DataProviders.class)
    @Description("Login with empty username or password")
    public void testLoginWithEmptyFields(String username, String password, String flag) {

        getDriver().get(SAUCEDEMO_URL);

        getDriver().findElement(By.xpath(USERNAME_LOCATOR)).sendKeys(username);
        getDriver().findElement(By.xpath(PASSWORD_LOCATOR)).sendKeys(password);
        getDriver().findElement(By.xpath(SUBMIT_BUTTON)).click();

        if(flag.equals("incorrect_username")) {
            assertEquals(getDriver().findElement(By.xpath("//h3[@data-test='error']")).getText(), "Epic sadface: Username is required");
        }
        else{
            assertEquals(getDriver().findElement(By.xpath("//h3[@data-test='error']")).getText(), "Epic sadface: Password is required");
        }
    }

    @Test
    @Description("Check that the number of items on the home page is correct")
    public void testCountItemsOnHomePage() {

        getDriver().get(SAUCEDEMO_URL);

        getDriver().findElement(By.xpath(USERNAME_LOCATOR)).sendKeys("standard_user");
        getDriver().findElement(By.xpath(PASSWORD_LOCATOR)).sendKeys("secret_sauce");
        getDriver().findElement(By.xpath(SUBMIT_BUTTON)).click();

        int numberOfItems = getDriver().findElements(By.xpath("//div[@class='inventory_item_name']")).size();

        assertEquals(numberOfItems, 6);
    }

    @Test
    @Description("Check that sotring by price is working properly")
    public void testSortByPriceDesc() {

        getDriver().get(SAUCEDEMO_URL);

        getDriver().findElement(By.xpath(USERNAME_LOCATOR)).sendKeys("standard_user");
        getDriver().findElement(By.xpath(PASSWORD_LOCATOR)).sendKeys("secret_sauce");
        getDriver().findElement(By.xpath(SUBMIT_BUTTON)).click();

        List<WebElement> listOfItems = getDriver().findElements(By.xpath("//div[@class='inventory_item']"));

        double priceMax = Double.parseDouble(listOfItems.get(0).findElement(By.xpath(".//div[@class='inventory_item_price']")).getText().replace("$", ""));
        double priceMin = Double.parseDouble(listOfItems.get(5).findElement(By.xpath(".//div[@class='inventory_item_price']")).getText().replace("$", ""));

        assertTrue(priceMax > priceMin);
    }

    @Test
    public void testDemoqaFormsRedirection() {

        getDriver().get("https://demoqa.com/");

        String title = getDriver().getTitle();
        Assert.assertEquals(title, "DEMOQA");

        WebElement elementsButton = getDriver().findElement(By.cssSelector(".top-card:nth-child(2)"));
        elementsButton.click();

        String currentUrl = getDriver().getCurrentUrl();
        String elementsUrl = "https://demoqa.com/forms";

        Assert.assertEquals(currentUrl, elementsUrl, "The actual URL: " + currentUrl +
                " differs from the expected one: " + elementsUrl);
    }

    @Ignore
    @Test
    public void testDemoqa(){
        WebDriver driver = new ChromeDriver();
        driver.get("https://demoqa.com/");

        String title = driver.getTitle();
        assertEquals (title, "DEMOQA");

        WebElement testBloc = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div[2]/div/div[6]"));
        testBloc.click();

        WebElement message = driver.findElement(By.className("main-header"));
        String value = message.getText();
        assertEquals( value, "Book Store");

        driver.quit();
    }

    @Ignore
    @Test
    public void testDemoqaEdgeBookFlow(){
        WebDriver driver = new EdgeDriver();

        driver.get("https://demoqa.com/");

        String title = driver.getTitle();
        Assert.assertEquals (title, "DEMOQA");

        WebElement cardBookStore = driver.findElement(By.xpath("(//div[contains(@class, 'card mt-4 top-card')])[last()]"));
        cardBookStore.click();

        Assert.assertEquals(driver.getCurrentUrl(), "https://demoqa.com/books");

        WebElement cardBook = driver.findElement(By.xpath("//*[@id='see-book-Git Pocket Guide']/a"));
        cardBook.click();

        Assert.assertEquals(driver.getCurrentUrl(), "https://demoqa.com/books?book=9781449325862");

        driver.quit();
    }

    @Ignore
    @Test
    public void testSearch () throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://demoqa.com/");

        String title = driver.getTitle();
        Assert.assertEquals(title, "DEMOQA");
        WebElement widgets = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div[2]/div/div[4]"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", widgets);
        Thread.sleep(500);
        widgets.click();
        Thread.sleep(2000);
        String url = driver.getCurrentUrl();
        String url1 = "https://demoqa.com/widgets";
        Assert.assertEquals(url, url1);
        WebElement appellation = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div[1]/div"));
        String value = appellation.getText();
        Assert.assertEquals(value, "Widgets");
        Thread.sleep(2000);


        driver.quit();
    }

    @Ignore
    @Test
    @Description("Check some elements")
    public void testCheckSomeElements() throws InterruptedException {
        getDriver().get("https://redrover.school/");
        String title = getDriver().getTitle();
        Assert.assertEquals(title, "RedRover | Non-commercial it-school");
        Thread.sleep(2000);
        WebElement submitButton = getDriver().findElement(By.xpath("//div[@data-elem-id='1674179354982']"));
        submitButton.click();
        WebElement emailField = getDriver().findElement(By.xpath("//input[@placeholder='Email']"));
        emailField.sendKeys("testSeleniumFirstCommit@test.ru");
        WebElement nameField = getDriver().findElement(By.xpath("//input[@placeholder='Name']"));
        nameField.sendKeys("testUser");
        Thread.sleep(3000);
        WebElement checkbox = getDriver().findElement(By.className("t-checkbox__indicator"));
        boolean isSelected = checkbox.isSelected();
        if (!isSelected) {
            checkbox.click();
        }
        WebElement teachers = getDriver().findElement(By.xpath("//h2[@field=\"tn_text_1674776847053\"]"));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", teachers);
        String expectedHeading = "Teachers";
        String heading = getDriver().findElement(By.xpath("//h2[contains(text(), \"Teachers\")]")).getText();
        Assert.assertEquals(expectedHeading, heading);
    }

    @Ignore
    @Test
    @Description("WebTables: Test open the window Registration form")
    public void testDemoqaAddNewRecordButton() {
            getDriver().get("https://demoqa.com/webtables");
            getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(40));

            Assert.assertEquals(getDriver().getTitle(), "DEMOQA");

            WebElement main_header = getDriver().findElement(By.className("main-header"));
            Assert.assertEquals(main_header.getText(), "Web Tables");

            WebElement button_add = getDriver().findElement(By.xpath("//button[@id='addNewRecordButton']"));
            button_add.click();
            WebElement window_add = getDriver().findElement(By.xpath("//*[@id='registration-form-modal']"));
            Assert.assertEquals(window_add.getText(), "Registration Form");
    }

    @Test
    @Description("Testing a site with non-working search")
    public void testSomething () {

        getDriver().get("https://www.mybirds.ru/");

        // Test title
        WebElement textBox = getDriver().findElement(By.className("slogan"));
        String text = textBox.getText();
        Assert.assertEquals(text,"Энциклопедия владельца птицы");

        // Test search
        WebElement inputTxt = getDriver().findElement(By.className("input_txt"));
        inputTxt.sendKeys("Parrots");

        WebElement searchButton = getDriver().findElement(By.name("submit"));
        searchButton.click();

        WebElement noText = getDriver().findElement(By.className("notetext"));
        String value = noText.getText();
        Assert.assertEquals(value, "К сожалению, на ваш поисковый запрос ничего не найдено.");

        // Test link
        WebElement linkButton = getDriver().findElement(By.xpath("//a[@href='/nature/' and text()='Птицы в природе']"));
        JavascriptExecutor executor = (JavascriptExecutor)getDriver();
        executor.executeScript("arguments[0].click();", linkButton);

    }

    @Ignore
    @Test
    @Description("testing a book search on a store website")
    public void testBookSearch(){
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.belavrana.com/");

        String title = driver.getTitle();
        assertEquals(title, "Купить книги на русском в Сербии - Bela Vrana (Белая Ворона)");

        WebElement button = driver.findElement(By.name("s"));
        button.click();

        WebElement search = driver.findElement(By.name("q"));
        search.sendKeys("Толстой");

        WebElement button2 = driver.findElement(By.name("s"));
        button2.click();

    }

    @Ignore
    @Test
    public void testDemoqaEdgeExperiment(){
        WebDriver driver = new EdgeDriver();

        driver.get("http://restful-booker.herokuapp.com/");

        String title = driver.getTitle();
        Assert.assertEquals (title, "Welcome to Restful-Booker");

        WebElement cardBookStore = driver.findElement(By.xpath("//img[@src='/images/motpro.png']"));
        cardBookStore.click();

        driver.getWindowHandles().forEach(tab->driver.switchTo().window(tab));

        String title2 = driver.getTitle();
        Assert.assertEquals (title2, "Ninja training for software testers | Ministry of Testing");

        driver.quit();
    }

    @Test
    public void testAddItemFromCatalogueToCart() throws InterruptedException {
        getDriver().get(SAUCEDEMO_URL);

        WebElement usernameField = getDriver().findElement(By.id("user-name"));
        usernameField.sendKeys("standard_user");

        WebElement passwordField = getDriver().findElement(By.id("password"));
        passwordField.sendKeys("secret_sauce");

        WebElement login_button = getDriver().findElement(By.className("submit-button"));
        login_button.click();

        String item_name = "Sauce Labs Fleece Jacket";
        String quantity = "1";

        WebElement fleece_jacket_to_cart_button = getDriver().findElement(By.id(
                "add-to-cart-sauce-labs-fleece-jacket"));
        fleece_jacket_to_cart_button.click();

        WebElement shopping_cart_button = getDriver().findElement(By.className("shopping_cart_container"));
        shopping_cart_button.click();
        Thread.sleep(2000);

        String cart_item_name = getDriver().findElement(By.cssSelector(".cart_item_label .inventory_item_name"))
                .getText();
        String cart_item_quantity = getDriver().findElement(By.className("cart_quantity")).getText();
        Assert.assertEquals(cart_item_name, item_name, "The cart does not have " + item_name);
        Assert.assertEquals(cart_item_quantity, quantity, "The cart quantity is not " + quantity);
    }

    @Ignore
    @Test
    public void testClickElementsLinkText() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://demoqa.com/elements");

        WebElement elementsButton = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div[2]/div[1]/div/div/div[1]/span/div/div[1]"));
        elementsButton.click();

        String url = driver.getCurrentUrl();
        String url1 = "https://demoqa.com/elements";
        Assert.assertEquals(url, url1);

        driver.get("https://demoqa.com/links");

        WebElement search_Text = driver.findElement(By.xpath("//*[@id='linkWrapper']/h5[2]/strong"));
        String searchTextExpected = search_Text.getText();
        Assert.assertEquals(searchTextExpected, "Following links will send an api call");

        WebElement createdButtonClick = driver.findElement(By.xpath("//*[@id='created']"));
        createdButtonClick.click();
        Thread.sleep(2000);

        WebElement searchStatus = driver.findElement(By.xpath("//*[@id='linkResponse']/b[1]"));
        String searchStatusExpected = searchStatus.getText();

        WebElement searchStatusText = driver.findElement(By.xpath("//*[@id='linkResponse']/b[2]"));
        String searchStatusTextExpected = searchStatusText.getText();

        Assert.assertEquals(searchStatusExpected, "201");
        Assert.assertEquals(searchStatusTextExpected, "Created");

        driver.quit();

    }
}
