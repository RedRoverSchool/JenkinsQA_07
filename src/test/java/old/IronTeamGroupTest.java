package old;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import static org.openqa.selenium.By.className;

@Ignore
public class IronTeamGroupTest extends BaseTest {
    @Ignore
    @Test
    public void w3schoolTest() throws InterruptedException {
        // Check Title of site
        WebDriverManager.chromedriver().setup();

        WebDriver driver = new ChromeDriver();
        try {
            driver.get("https://www.w3schools.com/");
            WebElement title = driver.findElement(By.cssSelector("h1.learntocodeh1"));
            String value = title.getText();
            Assert.assertEquals(value, "Learn to Code");
        } finally {
            driver.quit();
        }
    }
    @Ignore
    @Test
    public void javaPageTest() throws InterruptedException {
        // Check Java page of site
        WebDriverManager.chromedriver().setup();

        WebDriver driver = new ChromeDriver();
        try {
            driver.get("https://www.w3schools.com/");
            WebElement textBox = driver.findElement(By.id("search2"));
            textBox.sendKeys("Java Tutorial");
            WebElement searchButton = driver.findElement(By.id("learntocode_searchbtn"));
            Thread.sleep(500);
            searchButton.click();
           WebElement title = driver.findElement(By.cssSelector("h1"));
            String value = title.getText();
           Assert.assertEquals(value, "Java Tutorial");
        } finally {
            driver.quit();
        }
    }
    @Ignore
    @Test
    public void testCustomerLogin() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        try {
            driver.get("https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login");
            Thread.sleep(1000);
            WebElement loginButton = driver.findElement(By.cssSelector("[ng-click='customer()']"));
            loginButton.click();
            Thread.sleep(1000);
            driver.findElement(By.id("userSelect")).click();
            driver.findElement(By.cssSelector("[value='2']")).click();
            driver.findElement(By.cssSelector("[type='submit']")).click();
            Thread.sleep(1000);

            WebElement result = driver.findElement(By.xpath("//span[@class='fontBig ng-binding']/parent::strong"));
            Assert.assertEquals(result.getText(), "Welcome Harry Potter !!");
        } finally {
            driver.quit();
        }
    }
    @Ignore
    @Test
    public void testFerosorSearch() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        try {
            driver.get("https://ferosor.cl");
            Thread.sleep(1000);
            WebElement textBox = driver.findElement(By.name("s"));
            textBox.sendKeys("alimento");
            driver.findElement(By.cssSelector("[type='submit']")).click();
            Thread.sleep(1000);

        } finally {
            driver.quit();
        }
    }
    @Ignore
    @Test
    public void testFerosorLogin() throws InterruptedException{
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        try{
            driver.get("https://ferosor.cl");
            driver.findElement(className("login")).click();
            WebElement email = driver.findElement(className("form-control"));
            email.sendKeys("test@test.com");
            WebElement password = driver.findElement(className("js-child-focus"));
            password.sendKeys("12345");
            driver.findElement(By.id("submit-login")).click();
        }finally{
            driver.quit();
        }
    }

    @Test
    public void testJenkinsNewFolder() {
        String folderName= "NewNew";
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@name='name']")).sendKeys(folderName);
        getDriver().findElement(By.xpath("//*[@id='j-add-item-type-nested-projects']/ul/li[1]")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();
        getDriver().findElement(By.xpath("//input[@name='_.displayNameOrNull']")).sendKeys(folderName);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();


        getDriver().findElement(By.linkText("Dashboard")).click();

        Assert.assertTrue(getDriver().findElement(By.xpath("//tr[@id='job_" + folderName + "']")).isDisplayed());
    }
}
