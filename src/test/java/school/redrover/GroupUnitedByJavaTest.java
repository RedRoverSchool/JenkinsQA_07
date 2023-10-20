package school.redrover;

import jdk.jfr.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.Ignore;
import school.redrover.runner.BaseTest;
import java.time.Duration;
import static org.testng.Assert.assertEquals;


public class GroupUnitedByJavaTest extends BaseTest {

    @Ignore
    @Test
    public void testDemoqa() throws InterruptedException {

        getDriver().get("https://demoqa.com/");

        String title = getDriver().getTitle();
        Assert.assertEquals(title, "DEMOQA");

        WebElement testBloc = getDriver().findElement(By.cssSelector(".top-card:nth-child(6)"));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", testBloc);
        Thread.sleep(500);

        testBloc.click();

        WebElement message = getDriver().findElement(By.className("main-header"));
        String value = message.getText();
        Assert.assertEquals(value, "Book Store");
    }

    @Ignore
    @Test
    public void testDemoqaEdgeBookFlow() {
        WebDriver driver = new EdgeDriver();

        driver.get("https://demoqa.com/");

        String title = driver.getTitle();
        Assert.assertEquals(title, "DEMOQA");

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
    public void testSearch() throws InterruptedException {
        getDriver().get("https://demoqa.com/");

        String title = getDriver().getTitle();
        Assert.assertEquals(title, "DEMOQA");

        WebElement widgets = getDriver().findElement(By.xpath("//*[@id='app']/div/div/div[2]/div/div[4]"));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", widgets);
        Thread.sleep(500);

        widgets.click();
        Thread.sleep(2000);

        String url = getDriver().getCurrentUrl();
        String url1 = "https://demoqa.com/widgets";
        Assert.assertEquals(url, url1);

        WebElement appellation = getDriver().findElement(By.xpath("//*[@id='app']/div/div/div[1]/div"));
        String value = appellation.getText();
        Assert.assertEquals(value, "Widgets");
        Thread.sleep(2000);

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
    @Description("Testing a site with non-working search")
    public void testSomething() {

        getDriver().get("https://www.mybirds.ru/");

        // Test title
        WebElement textBox = getDriver().findElement(By.className("slogan"));
        String text = textBox.getText();
        Assert.assertEquals(text, "Энциклопедия владельца птицы");

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
        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        executor.executeScript("arguments[0].click();", linkButton);

    }

    @Ignore
    @Test
    @Description("testing a book search on a store website")
    public void testBookSearch() {
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
    public void testDemoqaEdgeExperiment() {
        WebDriver driver = new EdgeDriver();

        driver.get("http://restful-booker.herokuapp.com/");

        String title = driver.getTitle();
        Assert.assertEquals(title, "Welcome to Restful-Booker");

        WebElement cardBookStore = driver.findElement(By.xpath("//img[@src='/images/motpro.png']"));
        cardBookStore.click();

        driver.getWindowHandles().forEach(tab -> driver.switchTo().window(tab));

        String title2 = driver.getTitle();
        Assert.assertEquals(title2, "Ninja training for software testers | Ministry of Testing");

        driver.quit();
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

    @Ignore
    @Test
    public void testWeatherSearch() throws InterruptedException {
        getDriver().get("https://weather.rambler.ru/");

        WebElement textBox = getDriver().findElement(By.xpath("//input[@placeholder='Поиск по интернету']"));
        textBox.sendKeys("Тбилиси");

        WebElement searchButton = getDriver().findElement(By.xpath("//button[@aria-label='Найти']"));
        searchButton.click();

        Thread.sleep(500);

        WebElement title = getDriver().findElement(By.xpath("//h2[text()='Тбилиси']"));
        String value = title.getText();
        Assert.assertEquals(value, "Тбилиси");
    }


    @Test
    public void firstJenkinsTest() throws InterruptedException {

        WebElement newItem = getDriver().findElement(By.xpath("//*[@id='tasks']/div[1]/span/a"));
        newItem.click();

        String url = getDriver().getCurrentUrl();
        String urlExp = "http://localhost:8080/view/all/newJob";
        Assert.assertEquals(url, urlExp);

        WebElement message = getDriver().findElement(By.xpath("//*[@id='j-add-item-type-standalone-projects']//li[1]//span"));
        String value = message.getText();
        Assert.assertEquals(value, "Freestyle project");
        Thread.sleep(2000);

    }

    @Test
    public void testJenkinsDescriptionPreview() {

        WebElement description = getDriver().findElement(By.id("description-link"));
        description.click();

        String descriptionText = "We're getting started";

        WebElement descriptionArea = getDriver().findElement(By.name("description"));
        descriptionArea.sendKeys(descriptionText);

        WebElement previewButton = getDriver().findElement(By.className("textarea-show-preview"));
        previewButton.click();

        WebElement textPreview = getDriver().findElement(By.className("textarea-preview"));

        Assert.assertEquals(textPreview.getText(), descriptionText,
                textPreview + " differs from " + descriptionText);
    }

    @Test
    public void testJenkinsAdminButton() throws InterruptedException {

        WebElement adminButton = getDriver().findElement(By.xpath("//span[text()='admin']"));
        adminButton.click();

        WebElement title = getDriver().findElement(By.xpath("//div[text()='Jenkins User ID: admin']"));
        String value = title.getText();
        Assert.assertEquals(value, "Jenkins User ID: admin");
        Thread.sleep(1000);
    }

    @Test
    public void testJenkinsSimple() throws InterruptedException {

        WebElement Manage = getDriver().findElement(By.xpath("//a[contains(.,'Manage Jenkins')]"));
        Manage.click();

        WebElement element = getDriver().findElement(By.xpath("//h2[@class='jenkins-section__title' and text()='System Configuration']"));
        String value = element.getText();
        Assert.assertEquals(value, "System Configuration");
        Thread.sleep(1000);
    }

    private void goToCreateUserPage() {
        WebElement buttonManageJenkins = getDriver().findElement(
                By.xpath("//a[@href = '/manage']"));
        buttonManageJenkins.click();

        WebElement users = getDriver().findElement(By.xpath("//dt[text()='Users']"));
        users.click();

        WebElement buttonCreateUser = getDriver().findElement(By.xpath("//a[@href='addUser']"));
        buttonCreateUser.click();
    }

    private void fillDataFields(String userName, String password1, String password2, String fullname, String email) {
        WebElement usernameField = getDriver().findElement(By.xpath("//input[@name='username']"));
        usernameField.sendKeys(userName);

        WebElement passwordField = getDriver().findElement(By.xpath("//input[@name='password1']"));
        passwordField.sendKeys(password1);

        WebElement confirmPasswordField = getDriver().findElement(By.xpath("//input[@name='password2']"));
        confirmPasswordField.sendKeys(password2);

        WebElement fullnameField = getDriver().findElement(By.xpath("//input[@name='fullname']"));
        fullnameField.sendKeys(fullname);

        WebElement emailField = getDriver().findElement(By.xpath("//input[@name='email']"));
        emailField.sendKeys(email);

        WebElement submitButton = getDriver().findElement(By.name("Submit"));
        submitButton.click();
    }

    @Test
    public void testCreateUserWithValidData() {
        final String userName = "test";
        final String password = "12345";
        final String fullname = "test test";
        final String email = "test@mail.com";

        goToCreateUserPage();
        fillDataFields(userName, password, password, fullname, email);

        String userCreated = (new WebDriverWait(getDriver(), Duration.ofMillis(100)))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format("//a[@href='user/%s/']", userName)))).getText();

        assertEquals(userCreated, userName);
    }

    @Test
    public void testCreateUserWithDuplicateName() {
        final String userName = "test";
        final String password = "12345";
        final String fullname = "test test";
        final String email = "test@mail.com";

        goToCreateUserPage();
        fillDataFields(userName, password, password, fullname, email);

        WebElement buttonCreateUser = getDriver().findElement(By.xpath("//a[@href='addUser']"));
        buttonCreateUser.click();

        fillDataFields(userName, password, password, fullname, email);

        String errorText = getDriver().findElement(By.xpath("//div[text()='User name is already taken']")).getText();

        assertEquals(errorText, "User name is already taken");
    }

    @Test
    public void testCreateUserWithNoData() {
        goToCreateUserPage();
        fillDataFields("", "", "", "", "");

        assertEquals(
                getDriver().findElement(By.xpath("//div[text()='\"\" is prohibited as a username for security reasons.']")).getText(),
                "\"\" is prohibited as a username for security reasons.");
        assertEquals(
                getDriver().findElement(By.xpath("//div[text()='Password is required'][1]")).getText(),
                "Password is required");
        assertEquals(
                getDriver().findElement(By.xpath("//div[text()='Password is required'][2]")).getText(),
                "Password is required");
        assertEquals(
                getDriver().findElement(By.xpath("//div[text()='\"\" is prohibited as a full name for security reasons.']")).getText(),
                "\"\" is prohibited as a full name for security reasons.");
        assertEquals(
                getDriver().findElement(By.xpath("//div[text()='Invalid e-mail address']")).getText(),
                "Invalid e-mail address");
    }

    @Test
    public void testCreateUserWithInvalidData() {
        final String userName = "!@$";
        final String password = "12345";
        final String confirmPassword = "1234";
        final String fullname = "test test";
        final String email = "test@mail.com";

        goToCreateUserPage();
        fillDataFields(userName, password, confirmPassword, fullname, email);

        assertEquals(
                getDriver().findElement(By.xpath("//div[text()='User name must only contain alphanumeric characters, underscore and dash']")).getText(),
                "User name must only contain alphanumeric characters, underscore and dash");
        assertEquals(
                getDriver().findElement(By.xpath("//div[@class='error jenkins-!-margin-bottom-2'][2]")).getText(),
                "Password didn't match");
        assertEquals(
                getDriver().findElement(By.xpath("//div[@class='error jenkins-!-margin-bottom-2'][3]")).getText(),
                "Password didn't match");
    }
}
