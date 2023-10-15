package school.redrover;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.JenkinsUtils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertEquals;


public class GroupUnderdogsTest extends BaseTest {

    // the next line will be deleted as soon as all methods within the class are refactored and driver is changed to getDriver()
    WebDriver driver;

    private static final String MAIN_PAGE_URL_99BOTTLES = "http://www.99-bottles-of-beer.net/";
    String userName = "academic198405@gmail.com";
    String password = "BikeTrekMarlyn4!";
    String wrongPassword = "Sbbhbhbln2";
    String baseUrl = "https://www.trekbikes.com/us/en_US/";
    String baseUrlArt = "https://www.maytag.ca/";

    @Test
    public void test99BottlesTitleTest_tereshenkov29() {
        getDriver().get(MAIN_PAGE_URL_99BOTTLES);
        WebElement title = getDriver().findElement(By.xpath("//*[@id=\"header\"]/h1"));
        String titleValue = title.getText();
        assertEquals(titleValue, "99 Bottles of Beer");
    }

    @Test
    public void test99BottlesLastMenuLinkGetAttribute_tereshenkov29() {
        getDriver().get(MAIN_PAGE_URL_99BOTTLES);
        WebElement lastMenuLink = getDriver().findElement(By.xpath("//*[@id=\"menu\"]/li[6]/a"));
        String lastMenuLinkValue = lastMenuLink.getAttribute("textContent");
        assertEquals(lastMenuLinkValue, "Submit new Language");
    }

    @Test
    public void test99BottlesLastMenuLinkGetText_tereshenkov29() {
        getDriver().get(MAIN_PAGE_URL_99BOTTLES);
        WebElement lastMenuLink = getDriver().findElement(By.xpath("//*[@id=\"menu\"]/li[6]/a"));
        String lastMenuLinkValue = lastMenuLink.getText();
        assertEquals(lastMenuLinkValue, "SUBMIT NEW LANGUAGE");
    }

    @Test
    public void testJenkinsVersionInFooter_tereshenkov29() {
        JenkinsUtils.login(getDriver());

        WebElement JenkinsVersionInFooter = getDriver().findElement(By.xpath("//*[@class='jenkins-button jenkins-button--tertiary jenkins_ver']"));

        String JenkinsVersionInFooterValue = JenkinsVersionInFooter.getText();
        assertEquals(JenkinsVersionInFooterValue, "Jenkins 2.414.2");
    }

    @Test
    public void test99BottlesMainPageTitle_Olgla() {
        getDriver().get(MAIN_PAGE_URL_99BOTTLES);
        String title = getDriver().getTitle();
        assertEquals(title, "99 Bottles of Beer | Start");
    }

    @Test
    public void testFirstMenuTabText_Olgla() {
        getDriver().get("http://www.99-bottles-of-beer.net/abc.html");
        String elementName = getDriver().findElement(By.xpath("//ul[@id='submenu']/li[1]")).getText();
        assertEquals(elementName, "0-9");
    }

    @Test
    public void testAuthorNames_Olgla() {
        List<String> expectedAuthorNames = Arrays.asList("Oliver Schade", "Gregor Scheithauer", "Stefan Scheler");
        getDriver().get(MAIN_PAGE_URL_99BOTTLES);
        getDriver().findElement(By.xpath("//a[@href='team.html']")).click();
        List<WebElement> elements = getDriver().findElements(By.xpath("//h3"));
        List<String> authorNames = new ArrayList<>();
        for (WebElement i : elements) {
            authorNames.add(i.getText());
        }
        assertEquals(authorNames, expectedAuthorNames);
    }

    @Test
    public void testInactiveMember_maksin() {

        getDriver().get("http://www.99-bottles-of-beer.net/team.html");
        WebElement text = getDriver().findElement(By.xpath
                ("/html/body/div/div[3]/p[7]/font/b"));
        Assert.assertTrue(text.getText().toLowerCase().equals(text.getText()));
        assertEquals(text.getCssValue("color"), "rgba(255, 0, 0, 1)");
    }

    @Test
    public void testJenkinsLogOut_maksin() throws InterruptedException {
        JenkinsUtils.login(getDriver());
        getDriver().findElement(By.xpath("//*[@id='page-header']/div[3]/a[2]")).click();
        Assert.assertEquals(getDriver().findElement(By.xpath
                        ("//*[@id='main-panel']/div/h1")).getText(),
                "Sign in to Jenkins");
    }

    @Ignore
    @Test
    public void artuomTrack_correct_CredentialTest() throws InterruptedException {
        driver = new ChromeDriver();
        driver.get(baseUrl);
        WebElement enterButton = driver.findElement(By.xpath("(//*[@class='pdl-icon pdl-icon--size-24'])[1]"));
        Thread.sleep(2000);
        enterButton.click();
        Thread.sleep(3000);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement login = driver.findElement(By.xpath("//*[@class='mr-1 material-icons md-24']"));
        js.executeScript("arguments[0].scrollIntoView();", login);
        login.click();

        WebElement emailField = driver.findElement(By.xpath("//*[@id=\"j_username\"]"));
        emailField.click();
        emailField.sendKeys(userName);
        Thread.sleep(2000);

        WebElement passwordField = driver.findElement(By.xpath("//*[@id=\"j_password\"]"));
        passwordField.click();
        passwordField.sendKeys(password);
        Thread.sleep(1000);

        WebElement button = driver.findElement(By.xpath("(//*[text()='Log in'])[3]"));
        button.click();
        Thread.sleep(1000);

        WebElement isAllNewArrivals = driver.findElement(By.xpath("(//*[@class=\"pdl-heading pdl-heading--xl \"])[1]"));

        String text = isAllNewArrivals.getText();
        assertEquals(text, "All-new arrivals");
    }

    @Ignore
    @Test
    public void artuomTrack_Incorrect_CredentialTest() throws InterruptedException {
        driver = new ChromeDriver();
        driver.get(baseUrl);
        WebElement enterButton = driver.findElement(By.xpath("(//*[@class='pdl-icon pdl-icon--size-24'])[1]"));
        Thread.sleep(2000);
        enterButton.click();
        Thread.sleep(3000);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement login = driver.findElement(By.xpath("//*[@class='mr-1 material-icons md-24']"));
        js.executeScript("arguments[0].scrollIntoView();", login);
        login.click();

        WebElement emailField = driver.findElement(By.xpath("//*[@id=\"j_username\"]"));
        emailField.click();
        emailField.sendKeys(userName);
        Thread.sleep(2000);

        WebElement passwordField = driver.findElement(By.xpath("//*[@id=\"j_password\"]"));
        passwordField.click();
        passwordField.sendKeys(wrongPassword);

        WebElement button = driver.findElement(By.xpath("(//*[text()='Log in'])[3]"));
        button.click();
        Thread.sleep(1000);

        WebElement incorrectUser = driver.findElement(By.xpath("//*[text()='Incorrect username or password']"));

        String text = incorrectUser.getText();
        assertEquals(text, "Incorrect username or password");
    }

    @Ignore
    @Test
    public void artuomMarlin4Test() throws InterruptedException {
        driver = new ChromeDriver();
        driver.get(baseUrl);
        WebElement enterButton = driver.findElement(By.xpath("(//*[@class='pdl-icon pdl-icon--size-24'])[1]"));
        Thread.sleep(2000);
        enterButton.click();
        Thread.sleep(3000);

        WebElement mount = driver.findElement(By.xpath("//*[@id=\"expandMountainBikesMainMenu-compact\"]"));
        mount.click();
        Thread.sleep(1000);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement marlin = driver.findElement(By.xpath("(//*[text()='Marlin'])[1]"));
        js.executeScript("arguments[0].scrollIntoView();", marlin);
        marlin.click();
        Thread.sleep(1000);

        WebElement seeTheBikes = driver.findElement(By.xpath("//*[@title=\"SEE THE BIKES\"]"));
        seeTheBikes.click();
        Thread.sleep(2000);

        JavascriptExecutor js1 = (JavascriptExecutor) driver;
        WebElement marlin4 = driver.findElement(By.xpath("//*[text()='Marlin 4 Gen 2']"));
        js.executeScript("arguments[0].scrollIntoView();", marlin4);
        Thread.sleep(2000);

        String bikeName = marlin4.getText();
        assertEquals(bikeName, "Marlin 4 Gen 2");
    }

    @Ignore
    @Test
    public void artuomEnd_to_EndTest() throws InterruptedException {
        driver.manage().window().maximize();
        driver.get(baseUrlArt);

        WebElement modWindOffersAvailable = driver.findElement(By.xpath("//*[@viewBox='0 0 22 13']"));
        Thread.sleep(1000);
        modWindOffersAvailable.click();
        Thread.sleep(1000);

        WebElement fRlocal = driver.findElement(By.xpath("(//*[@class='utility-nav__link'])[5]"));
        fRlocal.click();
        Thread.sleep(1000);

        WebElement modWindOfferEnCours = driver.findElement(By.xpath("//*[@class='promo-drawer__heading']"));
        modWindOfferEnCours.click();

        WebElement addresseZipCode = driver.findElement(By.xpath("//*[@class='location-data']"));
        addresseZipCode.click();
        Thread.sleep(1000);

        WebElement fieldSearch = driver.findElement(By.xpath("//input[@placeholder='Tout rechercher']"));
        fieldSearch.clear();
        fieldSearch.sendKeys("FILTRE À EAU");
        fieldSearch.sendKeys(Keys.ENTER);

        WebElement sortPricePertinceLtoH = driver.findElement(By.xpath("//*[@aria-label='sort by']/option[2]"));
        sortPricePertinceLtoH.click();
        Thread.sleep(1000);

        WebElement FILTRE_À_AIR_FRESHFLOWTM_AIR1_POUR_RÉFRIGÉRATEUR = driver.findElement(By.xpath("(//*[@href='/fr_ca/accessories/kitchen-accessories/refrigerator/p.freshflow-refrigerator-air-filter-air1.w10311524.html?originVariantsOrder=NC'])[1]"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        int yOffset = FILTRE_À_AIR_FRESHFLOWTM_AIR1_POUR_RÉFRIGÉRATEUR.getLocation().getY();
        for (int i = 0; i < yOffset; i += 20) {
            js.executeScript("window.scrollTo(0, " + i + ")");
            Thread.sleep(50);
        }
        FILTRE_À_AIR_FRESHFLOWTM_AIR1_POUR_RÉFRIGÉRATEUR.click();
        Thread.sleep(1000);

        JavascriptExecutor js1 = (JavascriptExecutor) driver;
        WebElement Disponibilte_Available = driver.findElement(By.xpath("(//*[@class='button checking-availability-btn trigger-modal'])[2]"));
        js1.executeScript("arguments[0].scrollIntoView();", Disponibilte_Available);
        Thread.sleep(1000);
        Disponibilte_Available.click();

        WebElement zipCode = driver.findElement(By.xpath("//*[@class='signin-account-field form-input mm-zipcode-location-v2']"));
        zipCode.click();

        WebElement fieldZip = driver.findElement(By.xpath("//*[@placeholder='Tapez le code postal ici...']"));
        fieldZip.click();
        fieldZip.sendKeys("A1A 1A1");

        WebElement submit = driver.findElement(By.xpath("//*[@id='update-location-btn']"));
        submit.click();
        Thread.sleep(2000);

        JavascriptExecutor js2 = (JavascriptExecutor) driver;
        WebElement AJOUTER_AU_PANIER_AddToBin = driver.findElement(By.xpath("(//span[@class='button__text'])[2]"));
        js2.executeScript("arguments[0].scrollIntoView();", AJOUTER_AU_PANIER_AddToBin);

        Thread.sleep(1000);
        AJOUTER_AU_PANIER_AddToBin.click();

        Thread.sleep(1000);
        WebElement passer_Au_Panier = driver.findElement(By.xpath("//span[@data-backdrop='static']"));
        passer_Au_Panier.click();
        Thread.sleep(1000);

        WebElement totalEst = driver.findElement(By.xpath("(//*[@class='row--value '])[4]"));
        String actRes = totalEst.getText();
        String expRes = "15,56 $";

        assertEquals(actRes, expRes);
    }

    @Ignore
    @Test
    public void testKristinaNameAuthorSite() {
        getDriver().get("http://www.99-bottles-of-beer.net/");
        WebElement button = driver.findElement(By.xpath("//*[@id=\"main\"]/p[4]/a[2]"));
        button.click();

        WebElement nameOliver = driver.findElement(By.xpath("//div[@id=\"main\"]/h3[1]"));
        String name1 = nameOliver.getText();
        assertEquals(name1, "Oliver Schade");

        WebElement nameGregor = driver.findElement(By.xpath("//div[@id=\"main\"]/h3[2]"));
        String name2 = nameGregor.getText();
        assertEquals(name2, "Gregor Scheithauer");

        WebElement nameStefan = driver.findElement(By.xpath("//div[@id=\"main\"]/h3[3]"));
        String name3 = nameStefan.getText();
        assertEquals(name3, "Stefan Scheler");
    }
    
    @Ignore
    @Test
    public void testKristinaNameMenu(){
        getDriver().get("http://www.99-bottles-of-beer.net/abc.html");
        WebElement title = driver.findElement(By.xpath("//*[@id=\"submenu\"]/li[1]/a"));
        String value = title.getText();
        Assert.assertEquals(value, "0-9");
    }

    @Ignore
    @Test
    public void testKristinaTopLists() {
        getDriver().get("http://www.99-bottles-of-beer.net/");
        WebElement title = driver.findElement(By.xpath("//*[@id=\"menu\"]/li[4]/a[@href=\"/toplist.html\"]"));
        title.click();
        WebElement language = driver.findElement(By.xpath("//*[@id=\"category\"]/tbody/tr[2]/td[2]/a"));
        String title1 = language.getText();
        assertEquals(title1, "Malbolge (real loop version)");
    }

    @Ignore
    @Test
    public void testKristinaSubmitLanguage(){
        getDriver().get("http://www.99-bottles-of-beer.net/submitnewlanguage.html");

        WebElement button = driver.findElement(By.xpath("//*[@id=\"addlanguage\"]/p/input[8]"));
        button.click();

        WebElement alert = driver.findElement(By.xpath("//*[@id=\"main\"]/p"));
        String title = alert.getText();
        Assert.assertEquals(title, "Error: Precondition failed - Incomplete Input.");
    }

    @Ignore
    @Test
    public void testBrowseLanguagesKotlin() {
        driver = new ChromeDriver();
        driver.get(MAIN_PAGE_URL_99BOTTLES);

        WebElement browseLanguagesBtn = driver.findElement(By.xpath("//li/a[text()='Browse Languages']"));
        browseLanguagesBtn.click();

        WebElement letterLink = driver.findElement(By.xpath("//li/a[text()='K']"));
        letterLink.click();

        WebElement languageLink = driver.findElement(By.xpath("//a[contains(@href, 2901)]"));
        languageLink.click();

        WebElement languagePageHeader = driver.findElement(By.xpath("//div[@id='main']/h2"));
        String pageHeader = languagePageHeader.getText();

        assertEquals(pageHeader, "Language Kotlin");
    }

    @Ignore
    @Test
    public void testSearchLanguages() {
        final String partOfWordToSearch = "kot";
        driver = new ChromeDriver();
        driver.get(MAIN_PAGE_URL_99BOTTLES);

        WebElement searchLanguagesBtn = driver.findElement(By.xpath("//li/a[text()='Search Languages']"));
        searchLanguagesBtn.click();

        WebElement searchField = driver.findElement(By.xpath("//input[@name='search']"));
        searchField.sendKeys(partOfWordToSearch);

        WebElement goBtn = driver.findElement(By.xpath("//input[@name='submitsearch']"));
        goBtn.click();

        List<WebElement> searchResult = driver.findElements(By.xpath("//td/a[contains(@href,'language')]"));
        for (WebElement element : searchResult) {
            Assert.assertTrue(element.getText().toLowerCase().contains(partOfWordToSearch));
            assertEquals(element.getTagName(), "a");
        }
    }

    @Ignore
    @Test
    public void testRailiaImportantNoticeMarkup() {
        driver = new ChromeDriver();
        getDriver().get(MAIN_PAGE_URL_99BOTTLES);
        driver.findElement(By.linkText("SUBMIT NEW LANGUAGE")).click();

        List<WebElement> listItems = driver.findElements(By.xpath("//*[@id=\"main\"]/ul/li/span"));
        Assert.assertFalse(listItems.isEmpty(), "We should have at least one list item with bold text");

        for (WebElement el : listItems) {
            String notificationText = el.getText();
            if (notificationText.equalsIgnoreCase("important:")) {
                String backgroundColor = el.getCssValue("background-color");
                String textColor = el.getCssValue("color");
                assertEquals(backgroundColor, "rgba(255, 0, 0, 1)");
                assertEquals(textColor, "rgba(255, 255, 255, 1)");
                assertEquals(notificationText, notificationText.toUpperCase());
            }

        }
    }

    public void testNamesOfCreatorsOfSite() {
        List<String> teamMembers = Arrays.asList("Oliver Schade", "Gregor Scheithauer", "Stefan Scheler");
        driver = new ChromeDriver();
        driver.get(MAIN_PAGE_URL_99BOTTLES);

        WebElement teamLink = driver.findElement(By.xpath("//a[text()='Team']"));
        teamLink.click();

        List<WebElement> creators = driver.findElements(By.xpath("//h3"));
        List<String> namesOfCreators = new ArrayList<>();
        for (WebElement element : creators) {
            namesOfCreators.add(element.getText());
        }
        assertEquals(namesOfCreators, teamMembers);
    }

    @Test
    public void testSubmitLanguage() {
        getDriver().get(MAIN_PAGE_URL_99BOTTLES);

        WebElement clickSub = getDriver().findElement(By.xpath("//*[@id=\"menu\"]/li[6]/a"));
        clickSub.click();

        WebElement header = getDriver().findElement(By.xpath("//*[@id=\"submenu\"]/li/a"));
        String actualHeader = header.getText();

        assertEquals(actualHeader, "Submit New Language");
    }


    @Test
    public void testTitle() {
        getDriver().get(MAIN_PAGE_URL_99BOTTLES);

        WebElement title = getDriver().findElement(By.xpath("//*[@id=\"header\"]/h1"));
        String actualTitle = title.getText();

        assertEquals(actualTitle, "99 Bottles of Beer");
    }

    @Test
    public void testYuliafaReddit() {
        JenkinsUtils.login(getDriver());
        getDriver().get("https://www.reddit.com/?feed=home");

        String title = getDriver().getTitle();
        Assert.assertEquals( title, "Reddit - Dive into anything");

        getDriver().findElement(By.id("login-button")).click();
        getDriver().findElement(By.id("login-username")).sendKeys("test@mail.ru");
        getDriver().findElement(By.id("login-password")).sendKeys("12Qwerty");

    }
}