package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.JenkinsUtils;


public class GroupForwardTest extends BaseTest {

  private static final String PAGE_URL = "https://www.ldoceonline.com/";


  @Test
  public void testSearch() throws InterruptedException {

    getDriver().get(PAGE_URL);

    WebElement textBox = getDriver().findElement(By.className("search_input"));
    WebElement searchButton = getDriver().findElement(By.xpath("//*[@type='submit']"));

    textBox.sendKeys("readable");
    searchButton.click();
    Thread.sleep(600);
    WebElement titleElement = getDriver().findElement(By.className("HYPHENATION"));
    String value = titleElement.getText();
    Assert.assertEquals(value, "read‧a‧ble");


    getDriver().quit();


  }


  @Test
  public void testToSpanish() throws InterruptedException {

    getDriver().get(PAGE_URL);

    WebElement languageButton = getDriver().findElement(By.xpath("//span[@class='text']"));
    languageButton.click();
    WebElement spanishButton = getDriver().findElement(
        By.xpath("//a[@class='item' and text()='Español latino']"));
    spanishButton.click();

    WebElement title = getDriver().findElement(By.xpath("//h1[contains(text(),'Bienvenido')]"));
    Thread.sleep(8000);
    String value = title.getText();

    Assert.assertEquals(value,
        "Bienvenido al Longman Dictionary of Contemporary English Online");

  }

  @Test
  public void testLogoIsDisplayed() {
    getDriver().get(PAGE_URL);
    WebElement logo = getDriver().findElement(
        By.xpath("//img[@class = 'logo responsive_hide_on_smartphone']"));

    Assert.assertTrue(logo.isDisplayed());
  }

  @Ignore
  @Test
  public void getDictionaryOfWordOfTheDayTest() {
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

    getDriver().get("https://www.nobullproject.com/");

    WebElement closeCookies = getDriver().findElement(By.id("onetrust-close-btn-container"));
    closeCookies.click();

    WebElement searchButton = getDriver().findElement(By.xpath("//*[@data-target = 'search-button']"));
    searchButton.click();

    WebElement searchField = getDriver().findElement(By.xpath("//input[@name = 'q']"));
    searchField.sendKeys("Tank");

    WebElement searchButtonOnBar = getDriver().findElement(By.xpath("//button[@class = 'text-black'][1]"));
    searchButtonOnBar.click();

    Thread.sleep(8000);

    getDriver().switchTo().frame("attentive_creative");
    WebElement discountPopUp = getDriver().findElement(By.id("closeIconContainer"));
    discountPopUp.click();

    getDriver().switchTo().defaultContent();

    WebElement searchResult = getDriver().findElement(By.xpath("//span[@class = 'ss__query']"));
    String value = searchResult.getText();

    Assert.assertEquals(value, "TANK");
  }

  @Test
  public void titleTest() {
    getDriver().get(PAGE_URL);
    String title = getDriver().getTitle();
    Assert.assertEquals(title, "Longman Dictionary of Contemporary English | LDOCE");
  }

  @Test
  public void test_SpanText_WhenChangingLanguage() throws InterruptedException {

    String expectedResult = "Japanese - English";

    getDriver().get(PAGE_URL);

    WebElement languageButtonValue = getDriver().findElement(By.xpath(
        "//span[@class= 'text res_hos']"));

    languageButtonValue.click();

    Thread.sleep(5000);

    WebElement japaneseToEnglish = getDriver().findElement(By.xpath(
        "//a[@data-value = 'japanese-english']"));

    japaneseToEnglish.click();

    Thread.sleep(5000);

    String actualResult = languageButtonValue.getText();

    Assert.assertEquals(actualResult, expectedResult);
  }

  @Test
  public void test_InputFieldText_WhenChangingLanguage() throws InterruptedException {

    String expectedResult = "Japanese - English";

    getDriver().get(PAGE_URL);

    WebElement changeLanguageButton = getDriver().findElement(By.xpath(
        "//span[@class= 'text res_hos']"));

    changeLanguageButton.click();

    Thread.sleep(5000);

    WebElement japaneseToEnglish = getDriver().findElement(By.xpath(
        "//a[@data-value = 'japanese-english']"));

    japaneseToEnglish.click();

    Thread.sleep(5000);


    WebElement searchField = getDriver().findElement(By.xpath(
        "//div[contains(@class,'search-input-container')]/input[@class='search_input']"));

    String actualResult = searchField.getAttribute("placeholder");

//    Thread.sleep(5000);

    Assert.assertEquals(actualResult, expectedResult);
  }

  @Test
  public void testLongmanSearch() throws InterruptedException {
    getDriver().get(PAGE_URL);

    Thread.sleep(5000);

    WebElement inputSearch = getDriver().findElement(By.xpath("//input[@name='q']"));
    inputSearch.sendKeys("Selenium");

    WebElement searchButton = getDriver().findElement(By.xpath("//button[@type='submit']"));
    searchButton.click();

    WebElement pagetitle = getDriver().findElement(By.xpath("//h1[@class='pagetitle']"));
    String value = pagetitle.getText();
    Assert.assertEquals(value, "selenium");
  }

  @Test
  public void test_URL_WhenClickingOnMyViewsButton() {

    String expectedResult = "http://localhost:8080/me/my-views/view/all/";

    JenkinsUtils.login(getDriver());

    WebElement myViewsButton = getDriver().findElement(By.xpath(
        "//a[@href='/me/my-views']"));

    myViewsButton.click();

    String actualResult = getDriver().getCurrentUrl();

    Assert.assertEquals(actualResult,expectedResult);


  }
@Ignore
  @Test
  public void testClickLogoToMainPage() {

    JenkinsUtils.login(getDriver());
    WebElement myViewsButton = getDriver().findElement(By.xpath(
            "//a[@href='/me/my-views']"));
    myViewsButton.click();

    WebElement logoJenkins = getDriver().findElement(By.id("jenkins-head-icon"));
    logoJenkins.click();

    Assert.assertEquals(
            getDriver().findElement(By.cssSelector(".empty-state-block > h1")).getText(),
            "Welcome to Jenkins!");
  }


  @Test
  public void aboutLDOCETest() {
    getDriver().get(PAGE_URL);


    WebElement closeCookieWindow = getDriver().findElement(By.xpath("//button[@aria-label = 'Close']"));
    closeCookieWindow.click();
    WebElement aboutUsLink = getDriver().findElement(By.xpath("//a[text()='About LDOCE']"));

    aboutUsLink.click();

    Assert.assertEquals(getDriver().getCurrentUrl(), "https://www.ldoceonline.com/about.html");
    WebElement title = getDriver().findElement(By.xpath("//h1[@class='about_title']"));
    String value = title.getText();

    Assert.assertEquals(value, "About LDOCE Online");
  }

  @Test
  public void testAddDescription() {

    JenkinsUtils.login(getDriver());
    String addDescription = "GroupForward #1";

    WebElement description = getDriver().findElement(By.id("description-link"));
    description.click();
    WebElement textField = getDriver().findElement(By.name("description"));
    textField.clear();
    textField.sendKeys(addDescription);
    getDriver().findElement(By.name("Submit")).click();
    String newDescriprtion = getDriver().findElement(By.id("description")).getText();

    Assert.assertTrue(newDescriprtion.contains(addDescription));

  }

}



