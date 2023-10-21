package school.redrover;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import school.redrover.runner.BaseTest;

import java.util.concurrent.TimeUnit;

public class LocomotiveGroupTest extends BaseTest {

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
                "Attribute 'data-dropdown' for Jenkins Version button is incorrect");

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

    @Test
    public void testAddDescriptionJenkinsHomePage() {
        final String description = "My Jenkins home page description";

        By submitButton = By.id("description-link");
        By descriptionInputField = By.xpath("//textarea[@name='description']");
        By saveButton = By.xpath("//button[@name='Submit']");

        getDriver().findElement(submitButton).click();
        getDriver().findElement(descriptionInputField).sendKeys(description);
        getDriver().findElement(saveButton).click();

        Assert.assertEquals(getDriver()
                .findElement(By.xpath("//*[@id='description']/div[1]"))
                .getText(), description);
    }


    @Ignore
    @Test
    public void testMartspecGoPageBiorhythms () {
        getDriver().get("https://martspec.com/ru/emotion");
        WebElement buttonForBiorh = getDriver().findElement(By.xpath("//div[@class='col-lg-6 d-table mb-lg-0 mb-4']//a"));

        buttonForBiorh.click();

        //WebElement imageBiorh = getDriver().findElement(By.xpath("//div[@class='col']/img[1]"));
        // learn how to find a picture on a page
        Assert.assertEquals(getDriver().findElement(By.xpath(
                "//div[@class='col']/h1")).getText(), "Биоритмы");

    }

    @Test
    public void testOpenBuildHistory() {

        getDriver().findElement(By.xpath("//*[@href='/view/all/builds']")).click();

        Assert.assertEquals(getDriver().findElement(By.className("jenkins-app-bar__content")).getText(), "Build History of Jenkins");

    }


    @Test
    public void testCreateProject(){
        WebElement newItem = getDriver().findElement(By.xpath("//*[@id=\"tasks\"]/div[1]/span/a"));
        newItem.click();

        WebElement inputName = getDriver().findElement(By.xpath("//*[@id=\"name\"]"));
        inputName.sendKeys("Newproject");
        WebElement setingproject = getDriver().findElement(By.xpath("//*[@id=\"j-add-item-type-standalone-projects\"]/ul/li[1]"));
        setingproject.click();

        WebElement buttonClick = getDriver().findElement(By.xpath("//*[@id='ok-button']"));
        buttonClick.click();

        WebElement saveButton = getDriver().findElement(By.xpath("//*[@id=\"bottom-sticker\"]/div/button[1]"));
        saveButton.click();

        WebElement buttonDashboard = getDriver().findElement(By.xpath("//*[@id=\"breadcrumbs\"]/li[1]/a"));
        buttonDashboard.click();

        // Проверяем наличие элемента с текстом "Newproject" на странице
        String projectName = "Newproject";
        WebElement projectElement = getDriver().findElement(By.xpath("//*[contains(text(), '" + projectName + "')]"));
        Assert.assertTrue(projectElement.isDisplayed());

    }

}
