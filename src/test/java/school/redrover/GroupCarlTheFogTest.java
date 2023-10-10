package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.testng.AssertJUnit.assertEquals;

public class GroupCarlTheFogTest {
    @Test
    public void hireRightTest() {


        WebDriver driver = new ChromeDriver();
        driver.get("https://www.hireright.com");

        String title = driver.getTitle();
        Assert.assertEquals(title, "Employment Background Checks, Background Screening | HireRight");

        WebElement cacheButton = driver.findElement(By.xpath("//div[@class='CookieConsent']//button[contains(text(), 'Continue')]"));

        cacheButton.click();
        driver.quit();

    }

    @Test
    public void registerNowDisplayTest() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.hireright.com");
        String expectedText = "Register Now";
        String registerNow= "//a[@class = 'btn btn--primary btn--hover-red-dark btn-active-red-darker'][contains(text(),'Register Now')]";
        WebElement registerNowBTN = driver.findElement(By.xpath(registerNow));
        registerNowBTN.getText();

        Assert.assertEquals(registerNowBTN.getText(), expectedText);

        driver.quit();

    }

    @Test
    public void testGoogleFinance() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.google.com/finance/");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement searchTickerGoogl = driver.findElement(By.xpath("//div[@class = 'L6J0Pc ZB3Ebc nz7KN']/div/input[2]"));
        searchTickerGoogl.sendKeys("GOOGL");
        searchTickerGoogl.sendKeys(Keys.RETURN);
        WebElement previousClosingPriceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class = 'AHmHk']/span/div/div")));
        String previousClosingPrice = previousClosingPriceElement.getText();

        Assert.assertNotNull(previousClosingPrice);
        driver.quit();
    }

    @Test
    public void testDeadlinkPrinter() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        String pageToCheck = "https://stackoverflow.com/";
        driver.get(pageToCheck);
        Thread.sleep(5000);

        List<String> deadlinkList = new ArrayList<>();
        List<WebElement> deadlinks = driver.findElements(By.tagName("a"));

        for (int i = 0; i < deadlinks.size(); i++) {
            String link = deadlinks.get(i).getAttribute("href");
            if (link != null && link.startsWith(pageToCheck)) {
                String result = testDeadLink(link);
                if (result != null) {
                    deadlinkList.add(result);
                }
            }
        }

        deadlinkList.forEach(link -> System.out.println(link));
        driver.quit();
    }

    private String testDeadLink(String link) {
        try {
            URL url = new URL(link);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setRequestMethod("HEAD");
            httpURLConnection.connect();

            if (httpURLConnection.getResponseCode() >= 400) {
                return String.format("%s, response code - %d", link, httpURLConnection.getResponseCode());
            }
        } catch (Exception ignore) {}
        return null;
    }

    @Test
    public void testRadyShellCalendar()  {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.theshell.org/");

        String title = driver.getTitle();
        assertEquals("Home | Rady Shell at Jacobs Park", title);

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        driver.findElement(By.xpath("//button[@class='navtoggle']")).click();

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        driver.findElement(By.xpath("//*[@id='site-menu']/li[3]/a")).click();

        String performancesPage = driver.getTitle();
        assertEquals("Performances | Rady Shell at Jacobs Park", performancesPage);

        driver.quit();
    }

    @Test
    public void menuItemsTest1() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.hireright.com");
        List<String> menuItems = Arrays.asList("Services", "Industries", "Partners", "Resources", "Company", "Contact Us");

        List<WebElement> foundMenuItems = driver.findElements(By.cssSelector("ul.hidden > li > button, ul.hidden > li > a"));

        List<String> foundTexts = new ArrayList<>();
        for (WebElement menuItem : foundMenuItems) {
            foundTexts.add(menuItem.getText());
        }

        Assert.assertEquals(foundTexts, menuItems);

        driver.quit();
    }

    @Test
    public void menuItemsTest2() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.hireright.com");
        List<String> expectedMenuItems = Arrays.asList("Services", "Industries", "Partners", "Resources", "Company", "Contact Us");
        List<WebElement> foundMenuItems = driver.findElements(By.xpath("//ul[contains(@class, 'lg:flex')]//button/span | //ul[contains(@class, 'lg:flex')]//a"));

        List<String> foundMenuTexts = foundMenuItems.stream().map(WebElement::getText).collect(Collectors.toList());

        for (String expectedItem : expectedMenuItems) {
            Assert.assertTrue(foundMenuTexts.contains(expectedItem), "Expected menu item '" + expectedItem + "' not found!");

        }

        driver.quit();

    }
}
