package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

@Ignore
public class GroupForwardTest {

    private final String PAGE_URL = "https://www.ldoceonline.com/";

    @Test
    public void testSearch() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        try {
            driver.get(PAGE_URL);

            WebElement textBox = driver.findElement(By.className("search_input"));
            WebElement searchButton = driver.findElement(By.xpath("//*[@type='submit']"));

            textBox.sendKeys("readable");
            searchButton.click();
            Thread.sleep(600);
            WebElement titleElement = driver.findElement(By.className("HYPHENATION"));
            String value = titleElement.getText();
            Assert.assertEquals(value, "read‧a‧ble");

        } finally {
            driver.quit();
        }

    }

    @Test
    public void testToSpanish() {
        WebDriver driver = new ChromeDriver();
        try {
            driver.get(PAGE_URL);

            WebElement languageButton = driver.findElement(By.xpath("//span[@class='text']"));
            languageButton.click();
            WebElement spanishButton = driver.findElement(
                By.xpath("//a[@class='item' and text()='Español latino']"));
            spanishButton.click();

            WebElement title = driver.findElement(By.xpath("//h1[contains(text(),'Bienvenido')]"));
            String value = title.getText();

            Assert.assertEquals(value,
                "Bienvenido al Longman Dictionary of Contemporary English Online");

        } finally {
            driver.quit();
        }

    }

    @Test
    public void logoIsDisplayed() {
        WebDriver driver = new ChromeDriver();
        try {
            driver.get(PAGE_URL);
            WebElement logo = driver.findElement(By.xpath("//img[@class = 'logo responsive_hide_on_smartphone']"));
            Assert.assertTrue(logo.isDisplayed());
        } finally {
            driver.quit();
        }
    }

    @Test
    public void getDictionaryOfWordOfTheDayTest(){
        String urlOfDictionaryOfWordOfDay = "https://www.ldoceonline.com/dictionary/";
        WebDriver driver = new ChromeDriver();
        try {
            driver.get(PAGE_URL);
            WebElement closeCookieWindow = driver.findElement(By.xpath("//button[@aria-label = 'Close']"));
            closeCookieWindow.click();

            WebElement wordOfTheDay = driver.findElement(By.xpath("//span[@class = 'title_entry']/a"));
            String wordOfDay = wordOfTheDay.getText();
            wordOfTheDay.click();

            Assert.assertEquals(driver.getCurrentUrl(), (urlOfDictionaryOfWordOfDay + wordOfDay));
        } finally {
            driver.quit();
        }
    }

    @Test
    public void testStoreSearch() throws InterruptedException {

        WebDriver driver = new ChromeDriver();

        try {
            driver.get("https://www.nobullproject.com/");

            WebElement closeCookies = driver.findElement(By.id("onetrust-close-btn-container"));
            closeCookies.click();

            WebElement searchButton = driver.findElement(By.xpath("//*[@data-target = 'search-button']"));
            searchButton.click();

            WebElement searchField = driver.findElement(By.xpath("//input[@name = 'q']"));
            searchField.sendKeys("Tank");

            WebElement searchButtonOnBar = driver.findElement(By.xpath("//button[@class = 'text-black'][1]"));
            searchButtonOnBar.click();

            Thread.sleep(8000);

            driver.switchTo().frame("attentive_creative");
            WebElement discountPopUp = driver.findElement(By.id("closeIconContainer"));
            discountPopUp.click();

            driver.switchTo().defaultContent();

            WebElement searchResult = driver.findElement(By.xpath("//span[@class = 'ss__query']"));
            String value = searchResult.getText();
            Assert.assertEquals(value, "TANK");
        } finally {
            driver.quit();
        }
    }

    @Test
    public void titleTest() {
        WebDriver driver = new ChromeDriver();
        try{
            driver.get(PAGE_URL);
            String title = driver.getTitle();

            Assert.assertEquals(title, "Longman Dictionary of Contemporary English | LDOCE");
        } finally {
            driver.quit();
        }
    }
}

