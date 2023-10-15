package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.JenkinsUtils;

import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class GroupCarlTheFogTest extends BaseTest {

    private WebDriverWait wait;

    @Test
    public void testHireRight() {

        getDriver().get("https://www.hireright.com");

        String title = getDriver().getTitle();
        Assert.assertEquals(title, "Employment Background Checks, Background Screening | HireRight");

        WebElement cacheButton = getDriver().findElement(By.xpath("//div[@class='CookieConsent']//button[contains(text(), 'Continue')]"));

        cacheButton.click();
    }

    @Test
    public void testSearchIndustry() {
        getDriver().get("https://www.hireright.com/");

        WebElement industriesDropDown = getDriver().findElement(By.xpath("//span[contains(text(), 'Industries')]"));
        industriesDropDown.click();

        String Industries = getDriver().findElement(By.xpath("//h4[contains(text(), 'Industries')]")).getText();
        Assert.assertEquals(Industries,"Industries");

        WebElement healthCareAndLifeSciensces = getDriver().findElement(By.xpath("//p[contains(text(), 'Healthcare & Life Sciences')]"));
        Assert.assertTrue(healthCareAndLifeSciensces.isDisplayed());
    }

    @Test
    public void testSlowCalculator(){
        String url = "https://bonigarcia.dev/selenium-webdriver-java/slow-calculator.html";
        int calculatorDelay = 0;
        int firstValue = 4;
        int secondValue = 7;
        int result = firstValue + secondValue;
        getDriver().get(url);

        String firstOperand = String.format("//span[contains(@class, 'btn-outline-primary') and text() = %d]", firstValue);
        String secondOperand = String.format("//span[contains(@class, 'btn-outline-primary') and text() = %d]", secondValue);
        String operation = "//span[contains(@class, 'operator') and text() = '+']";
        String equalSign = "//span[contains(@class, 'btn-outline-warning') and text() = '=']";
        String justScreen = "//div[@class='screen']";
        String screenWithResult = String.format("//div[@class='screen' and text() = %d]", result);

        getDriver().findElement(By.id("delay")).clear();
        getDriver().findElement(By.id("delay")).sendKeys("" + calculatorDelay);
        getDriver().findElement(By.xpath(firstOperand)).click();
        getDriver().findElement(By.xpath(operation)).click();
        getDriver().findElement(By.xpath(secondOperand)).click();
        getDriver().findElement(By.xpath(equalSign)).click();

        WebDriverWait wait = new WebDriverWait(getDriver(),Duration.ofSeconds(calculatorDelay));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(screenWithResult)));

        Assert.assertEquals(getDriver().findElement(By.xpath(justScreen)).getText(), "" + result);

    }

    @Test
    public void testRegisterNowDisplay() {
        String hrUrl = "https://www.hireright.com";
        getDriver().get(hrUrl);
        String expectedText = "Register Now";
        String registerNow= "//a[@class = 'btn btn--primary btn--hover-red-dark btn-active-red-darker'][contains(text(),'Register Now')]";
        WebElement registerNowBTN = getDriver().findElement(By.xpath(registerNow));
        registerNowBTN.getText();

        Assert.assertEquals(registerNowBTN.getText(), expectedText);
    }

    @Test
    public void testGoogleFinance() {
        String googleFinancePage = "https://www.google.com/finance/";
        getDriver().get(googleFinancePage);

        wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));

        WebElement searchTickerGoogl = getDriver().findElement(By.xpath("//div[@class = 'L6J0Pc ZB3Ebc nz7KN']/div/input[2]"));
        searchTickerGoogl.sendKeys("GOOGL");
        searchTickerGoogl.sendKeys(Keys.RETURN);
        WebElement previousClosingPriceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class = 'AHmHk']/span/div/div")));
        String previousClosingPrice = previousClosingPriceElement.getText();

        Assert.assertNotNull(previousClosingPrice);
    }

    @Test
    public void testDeadlinkPrinter() {
        wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));

        String pageToCheck = "https://stackoverflow.com/";
        getDriver().get(pageToCheck);

        List<String> deadlinkList = new ArrayList<>();
        List<WebElement> deadlinks = getDriver().findElements(By.tagName("a"));

        for (int i = 0; i < deadlinks.size(); i++) {
            String link = deadlinks.get(i).getAttribute("href");
            if (link != null && link.startsWith(pageToCheck)) {
                String result = testDeadLink(link);
                if (result != null) {
                    deadlinkList.add(result);
                }
            }
        }

        if (deadlinkList.isEmpty()) {
            System.out.println("Broken links not found.");
        } else {
            deadlinkList.forEach(link -> System.out.println(link));
        }
        getDriver().quit();
    }

    private String testDeadLink(String link) {
        try {
            URL url = new URL(link);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.connect();

            if (httpURLConnection.getResponseCode() >= 400) {
                return String.format("%s, response code - %d", link, httpURLConnection.getResponseCode());
            }
        } catch (Exception ignore) {}
        return null;
    }

    @Test
    public void testRadyShellCalendar()  {
        getDriver().get("https://www.theshell.org/");

        String title = getDriver().getTitle();
        Assert.assertEquals("Home | Rady Shell at Jacobs Park", title);

        getDriver().findElement(By.xpath("//button[@class='navtoggle']")).click();

        getDriver().findElement(By.xpath("//*[@id='site-menu']/li[3]/a")).click();

        String performancesPage = getDriver().getTitle();
        Assert.assertEquals("Performances | Rady Shell at Jacobs Park", performancesPage);
    }

    @Test
    public void testMenuItems() {
        String hrUrl = "https://www.hireright.com";
        getDriver().get(hrUrl);
        List<String> menuItems = Arrays.asList("Services", "Industries", "Partners", "Resources", "Company", "Contact Us");

        List<WebElement> foundMenuItems = getDriver().findElements(By.cssSelector("ul.hidden > li > button, ul.hidden > li > a"));

        List<String> foundTexts = new ArrayList<>();
        for (WebElement menuItem : foundMenuItems) {
            foundTexts.add(menuItem.getText());
        }

        Assert.assertEquals(foundTexts, menuItems);
    }

    @Test
    public void testMainMenuItems() {
        String hrUrl = "https://www.hireright.com";
        getDriver().get(hrUrl);
        List<String> expectedMenuItems = Arrays.asList("Services", "Industries", "Partners", "Resources", "Company", "Contact Us");
        List<WebElement> foundMenuItems = getDriver().findElements(By.xpath("//ul[contains(@class, 'lg:flex')]//button/span | //ul[contains(@class, 'lg:flex')]//a"));

        List<String> foundMenuTexts = foundMenuItems.stream().map(WebElement::getText).collect(Collectors.toList());

        for (String expectedItem : expectedMenuItems) {

            Assert.assertTrue(foundMenuTexts.contains(expectedItem), "Expected menu item '" + expectedItem + "' not found!");
        }
    }

    @Test
    public void testJenkinsGreetings() {
        JenkinsUtils.login(getDriver());
        String JenkinsGreetings = getDriver().findElement(By.tagName("h1")).getText();

        Assert.assertEquals("Welcome to Jenkins!", JenkinsGreetings);
    }
}
