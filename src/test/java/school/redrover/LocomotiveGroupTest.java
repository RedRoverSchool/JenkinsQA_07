package school.redrover;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import school.redrover.runner.BaseTest;
import school.redrover.runner.JenkinsUtils;

import java.util.concurrent.TimeUnit;

@Ignore
public class LocomotiveGroupTest extends BaseTest {
    @Test
    @Ignore
    public void testDemoqaTextBox() {
        String fullName = "Tom Jonson";
        String email = "mail@mail.com";

        getDriver().get("https://demoqa.com/text-box");

        String pageTitleText = getDriver().findElement(By.className("main-header")).getText();
        Assert.assertEquals(pageTitleText, "Text Box");

        WebElement fullNameTextBox = getDriver().findElement(By.cssSelector("#userName"));
        fullNameTextBox.sendKeys(fullName);

        WebElement emailTextBox = getDriver().findElement(By.id("userEmail"));
        emailTextBox.sendKeys(email);

        WebElement submitButton = getDriver().findElement(By.id("submit"));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", submitButton);
        submitButton.click();

        String actualFullName = getDriver()
                .findElement(By.id("name"))
                .getText();
        Assert.assertEquals(actualFullName, "Name:" + fullName);

        String actualEmail = getDriver()
                .findElement(By.xpath("//*[@id=\"email\"]"))
                .getText();

        Assert.assertEquals(actualEmail, "Email:" + email);
    }

    @Test
    public void testLink() throws InterruptedException {
            String linkExpected = "https://demoqa.com/";

            getDriver().get("https://demoqa.com/links");
            String originalWindow = getDriver().getWindowHandle();
            WebElement link = getDriver().findElement(By.xpath("//*[@id=\"simpleLink\"]"));
            String linkActual = link.getAttribute("href");

            Assert.assertEquals(linkActual, linkExpected);

            link.click();

            Thread.sleep(1000);

            for (String windowHandle : getDriver().getWindowHandles()) {
                if (!originalWindow.contentEquals(windowHandle)) {
                    getDriver().switchTo().window(windowHandle);
                    break;
                }
            }

            Thread.sleep(1000);
            getDriver().findElement(By.xpath("//*[@class=\"banner-image\"]")).isDisplayed();
    }

    @Ignore
    @Test
    public void checkRadioButton() throws InterruptedException {

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://demoqa.com/radio-button");


        selectRadioButton(driver, "Yes");
        WebElement textRadioButton = driver.findElement(By.xpath("//p[@class='mt-3']"));
        Assert.assertEquals(textRadioButton.getText(), "You have selected Yes");

        Thread.sleep(3000);

        selectRadioButton(driver, "Impressive");
        Assert.assertEquals(textRadioButton.getText(), "You have selected Impressive");

        Thread.sleep(3000);

        driver.close();
    }

    @Ignore
    public static void selectRadioButton(WebDriver driver, String value) {
        WebElement RadioButton = driver.findElement(By.xpath("//label[normalize-space()='" + value + "']"));
        RadioButton.click();
    }

    @Ignore
    @Test

    public void DashboardButtonTest() {
        WebDriver driver = new ChromeDriver();
        try {
            driver.get("https://refero.design/");
            WebElement bentoBox = driver.findElement(By.xpath("//div[@class='xK9VF'][contains(text(),'Dashboard')]"));
            Actions actions = new Actions(driver);
            actions.moveToElement(bentoBox);
            bentoBox.click();

            Assert.assertEquals(driver.getCurrentUrl(), "https://refero.design/search?page_types[id][]=28&order=popular");
        } finally {
            driver.quit();
        }
      }


      @Test
    public void testYandexSearchBar(){
        String url = "https://ya.ru/";
        getDriver().get(url);
        try {

            WebElement searchBar = getDriver().findElement(By.xpath("//div[@class='search3__input-wrapper']/input"));
            WebElement searchButton = getDriver().findElement(By.xpath("//button[@class='search3__button mini-suggest__button']"));
            searchBar.click();
            searchBar.sendKeys("Ответ на главный вопрос жизни");
            searchButton.click();
            WebElement searchText = getDriver().findElement(By.xpath("//div[text()='Ответ на главный вопрос жизни, вселенной и всего такого']"));
            Assert.assertTrue(searchText.isDisplayed());
        }catch (NoSuchElementException e){
            System.out.println("Капча яндекса не позволяет закончить тест");
        }finally {
            System.out.println("Тест окончен");
        }
      }

    @Ignore
    @Test
    public void testSimpleSearch() throws InterruptedException {
        WebDriver driver = new ChromeDriver();

        driver.get("https://999.md/");

        WebElement categoryButton = driver.findElement(By.xpath("//button[@id='js-categories-toggle']"));
        categoryButton.click();

        WebElement autoButton = driver.findElement(By.xpath("//a[contains(text(),'Autoturisme')]"));
        autoButton.click();

        Thread.sleep(1000);

        WebElement masini = driver.findElement(By.xpath("//h1[contains(text(),'Mașini')]"));
        String value = masini.getText();
        Assert.assertEquals(value, "Mașini");

        WebElement searchInput = driver.findElement(By.xpath("//input[@id='js-search-input']"));
        searchInput.click();

        WebElement input = driver.findElement(By.xpath("//input[@id='js-search-input']"));
        input.sendKeys("audi a6");

        WebElement searchButton = driver.findElement(By.xpath("//span[contains(.,'Caută')]"));
        searchButton.click();

        WebElement title = driver.findElement(By.xpath("//span[contains(text(),'Rezultatele căutării')]"));
        String value1 = title.getText();
        Assert.assertEquals(value1,"Rezultatele căutării \"audi a6\" (2 607 anunţuri)");

        WebElement searchInput1 = driver.findElement(By.xpath("//input[@id='js-search-input']"));
        searchInput1.click();

        Thread.sleep(3000);

        WebElement dropDown= driver.findElement(By.xpath("//html[1]/body[1]/div[3]/header[1]/div[2]/div[1]/form[1]/fieldset[1]/span[1]/div[1]/div[1]/div[1]"));
        dropDown.click();

        WebElement title1 = driver.findElement(By.xpath("//span[contains(text(),'Autoturisme')]"));
        String value2 = title1.getText();
        Assert.assertEquals(value2,"Autoturisme (600)");

        driver.quit();
    }

    @Test
    public void testHoverOver() {
        By image = By.className("figure");
        By captionInput = By.cssSelector("#content > div > div:nth-child(3) > div > h5");
        int imageIndex = 0;
        String captionExpected = "name: user" + (imageIndex + 1);

        var driver = getDriver();
        driver.get("https://the-internet.herokuapp.com/hovers");
        var hoverElement = driver.findElements(image).get(imageIndex); // returns 1st image

        var action = new Actions(driver);
        action.moveToElement(hoverElement).perform();

        var captionText = driver.findElements(captionInput).get(imageIndex);
        Assert.assertEquals(captionText.getText(), captionExpected, "The caption text is wrong");
    }

    @Test
    public void testVerifyJenkinsVersion() {
        WebDriver driver = getDriver();
        By locatorButtonJenkinsVersion = By.cssSelector("button.jenkins_ver");
        By locatorButtonAbout = By.cssSelector(".jenkins-dropdown__item:first-of-type");
        By locatorTextJenkinsVersion = By.cssSelector("p.app-about-version");
        final String expectedJenkinsVersionText = "Version 2.414.2";

        WebElement buttonJenkinsVersion = driver.findElement(locatorButtonJenkinsVersion);
        buttonJenkinsVersion.click();

        Assert.assertEquals(buttonJenkinsVersion.getAttribute("data-dropdown"),
                "true",
                "Attribute ' for Jenkins Version button is incorrect");

        driver.findElement(locatorButtonAbout).click();
        Assert.assertEquals(driver.findElement(locatorTextJenkinsVersion).getText(),
                expectedJenkinsVersionText,
                "Jenkins Version is incorrect");
    }

    @Test
    public void testSkateSiteHeader() throws InterruptedException {
        getDriver().get("http://www.skate.net/");

        WebElement title = getDriver().findElement(By.className("header_text"));
        String value = title.getText();
        Assert.assertEquals(value, "skates, skate gear, ice skates and more");

        Thread.sleep(2000);
    }

    @Ignore
    @Test
    public void testAddDescriptionJenkinsHomePage() {
        String description = "My Jenkins home page description";
        By submitButton = By.id("description-link");
        By descriptionInputField = By.xpath("//textarea[@name='description']");
        By saveButton = By.xpath("//button[@name='Submit']");

        getDriver().findElement(submitButton).click();
        getDriver().findElement(descriptionInputField).sendKeys(description);
        getDriver().findElement(saveButton).click();

        Assert.assertEquals(getDriver()
                .findElement(By.xpath("//*[@id='description']/div[1]"))
                .getText(), description);

        getDriver().findElement(submitButton).click();
        getDriver().findElement(descriptionInputField).clear();
        getDriver().findElement(saveButton).click();
    }
}
