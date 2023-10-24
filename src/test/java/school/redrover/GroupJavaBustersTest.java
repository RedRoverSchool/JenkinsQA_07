package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;


public class GroupJavaBustersTest extends BaseTest {


    private void createFreestyleJob(String jobName) {
        getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@id = 'name']")).sendKeys(jobName);
        getDriver().findElement(By.xpath("//li[contains (@class, 'FreeStyleProject')]")).click();
        getDriver().findElement(By.xpath("//button[@id = 'ok-button']")).click();
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();

    }

    @Test
    public void testCreateFreestyleJobCorrectName() {
        final String jobName = "JobTest";

        createFreestyleJob(jobName);
        getDriver().findElement(By.xpath("//a[@id = 'jenkins-home-link']")).click();
        Assert.assertEquals(getDriver().findElement(By.xpath
                ("//a[contains(@href, 'JobTest')]/span[text() = 'JobTest']")).getText(), jobName);

    }

    @Ignore
    @Test
    public void testFillUserName() {

        getDriver().get("https://testpages.eviltester.com/styled/basic-html-form-test.html");

        WebElement fieldUsername = getDriver().findElement(By.name("username"));
        WebElement submitButton = getDriver().findElement(By.xpath("//*[@value='submit']"));

        fieldUsername.sendKeys("Evgeniia");
        submitButton.click();

        WebElement message = getDriver().findElement(By.id("_valueusername"));
        String value = message.getText();
        assertEquals(value, "Evgeniia");

    }

    @Ignore
    @Test
    public void testCancelFillingUserName() {

        getDriver().get("https://testpages.eviltester.com/styled/basic-html-form-test.html");

        WebElement fieldUsername = getDriver().findElement(By.name("username"));
        WebElement cancelButton = getDriver().findElement(By.xpath("//*[@value='cancel']"));

        fieldUsername.sendKeys("Evgeniia");
        cancelButton.click();

        String value = fieldUsername.getText();
        assertEquals(value, "");

    }

    @Ignore
    @Test
    public void testAllFields() throws InterruptedException {
        String mainLink = "https://www.selenium.dev/selenium/web/web-form.html";
        String indexLink = "https://www.selenium.dev/selenium/web/index.html";

        getDriver().get(mainLink);
        WebElement textInput = getDriver().findElement(By.xpath("//input[@id='my-text-id']"));
        WebElement passwordInput = getDriver().findElement(By.xpath("//label[2]/input[1]"));
        WebElement textArea = getDriver().findElement(By.xpath("//label[3]/textarea[1]"));
        WebElement disableInput = getDriver().findElement(By.xpath("//label[4]/input[1]"));
        WebElement readOnlyField = getDriver().findElement(By.xpath("//label[5]/input[1]"));
        WebElement linkReturnToIndex = getDriver().findElement(By.xpath("//a[contains(text(),'Return to index')]"));

        textInput.sendKeys("test");
        passwordInput.sendKeys("12345678");
        textArea.sendKeys("This is a test text for text area");
        Assert.assertFalse(disableInput.isEnabled());
        Assert.assertTrue(true, readOnlyField.getAttribute("true"));

        linkReturnToIndex.click();
        Thread.sleep(2000);
        String currentLink = getDriver().getCurrentUrl();
        Assert.assertEquals(currentLink, indexLink);
        getDriver().get(mainLink);

        WebElement dropdownSelect = getDriver().findElement(By.xpath("//*[@name='my-select']"));
        Select dropDownSelectedValue = new Select(dropdownSelect);
        dropDownSelectedValue.selectByValue("1");
        dropDownSelectedValue.selectByValue("2");
        dropDownSelectedValue.selectByValue("3");
    }

    @Ignore
    @Test
    public void testFillInForm() {
        getDriver().get("https://automationintesting.online/");

        WebElement nameField = getDriver().findElement(By.xpath("//input[@id='name']"));
        WebElement emailField = getDriver().findElement(By.xpath("//input[@id='email']"));
        WebElement phoneField = getDriver().findElement(By.xpath("//input[@id='phone']"));
        WebElement subjectField = getDriver().findElement(By.xpath("//input[@id='subject']"));
        WebElement messageField = getDriver().findElement(By.xpath("//textarea[@id='description']"));

        nameField.sendKeys("Marta");
        emailField.sendKeys("fake@fakeemail.com");
        phoneField.sendKeys(" 012345678901");
        subjectField.sendKeys("Subject must be between 5 and 100 characters.");
        messageField.sendKeys("The Old Farmhouse, Shady Street, Newfordburyshire, NE1 410S");

        WebElement submitButton = getDriver().findElement(By.xpath("//button[@id='submitContact']"));
        submitButton.click();

        WebElement message = getDriver().findElement(By.xpath("//h2[normalize-space()='Thanks for getting in touch Marta!']"));
        String value = message.getText();
        assertEquals(value, "Thanks for getting in touch Marta!");
    }

    @Ignore
    @Test
    public void testSearch() throws InterruptedException {

        getDriver().get("https://www.euronics.lv/");

        WebElement cookieButton = getDriver().findElement(By.id("cookie-accept-all-button"));
        cookieButton.click();

        WebElement textBox = getDriver().findElement(By.className("autocomplete__input"));
        textBox.sendKeys("macbook");


        WebElement searchButton = getDriver().findElement(By.className("autocomplete__search-button"));
        searchButton.click();

        WebElement message = getDriver().findElement(By.xpath("//h1[@class = 'category__header']"));
        String value = message.getText();
        Assert.assertEquals(value, "macbook");
    }

    @Ignore
    @Test
    public void testSearchCorrectProduct() {
        getDriver().get("https://shop.studiob3.pl/");

        getDriver().findElement(By.className("search-open")).click();

        WebElement typeSearch = getDriver().findElement(By.className("search-field"));
        typeSearch.sendKeys("dress");
        typeSearch.submit();

        String linkToProduct = getDriver()
                .findElement(By.xpath("//*[@id='search-shop-grid']/div[1]/div/div[3]/h5/a"))
                .getAttribute("href");

        assertTrue(linkToProduct.contains("dress"));
    }

    @Ignore
    @Test
    public void testDeleteFromBim() {
        getDriver().get("https://shop.studiob3.pl/product/iola-beanie/");

        // Add product's color.
        getDriver().findElement(By.id("pa_color")).submit();
        getDriver().findElement(By.xpath("//*[@id=\"pa_color\"]/option[4]")).click();

        // Add product's size.
        getDriver().findElement(By.id("pa_size")).submit();
        getDriver().findElement(By.xpath("//*[@id=\"pa_size\"]/option[2]")).click();

        // Check if product with given color and size exists.
        var unused = getDriver().findElement(By.className("stock"));

        getDriver().findElement(By.className("pseudo-add-to-cart")).click();
        getDriver().findElement(By.xpath("//*[@id=\"mini-cart\"]/div/div[1]/form/table/tbody/tr/td[2" +
                "]/div/span[2]")).click();

        WebElement foundElement = getDriver().findElement(By.xpath("//*[@id=\"mini-cart\"]/div/div" +
                "/div[2]/strong"));

        assertTrue(foundElement.getText().contains("Your cart is empty"));
    }

    @Ignore
    @Test
    public void testNavigateToExpectedUrl() {

        getDriver().get("https://shop.studiob3.pl/");

        getDriver().findElement(By.linkText("End of Series")).click();

        assertEquals(getDriver().getCurrentUrl(), "https://shop.studiob3" +
                ".pl/product-category/end-of-series/");
    }

    @Ignore
    @Test
    public void testSomeJenkins() throws InterruptedException {


    }

    @Test
    public void testSignInJenkins() {

        Assert.assertEquals(getDriver().findElement(
                        By.cssSelector("#main-panel > div:nth-child(3) > div > h1")).getText(),
                "Welcome to Jenkins!");
    }

    @Test
    public void testCheckVersion() {

        Assert.assertEquals(getDriver().findElement(
                        By.xpath("//*[@id=\"jenkins\"]/footer/div/div[2]/button")).getText(),
                "Jenkins 2.414.2");
    }

    @Test
    public void testCreateNewItemWithValidName() {

        getDriver().findElement(By.xpath("//*[@id=\"tasks\"]/div[1]/span/a"))
                .click();
        getDriver().findElement(By.className("jenkins-input"))
                .sendKeys("3451643561");
        getDriver().findElement(By.className("hudson_model_FreeStyleProject"))
                .click();
        getDriver().findElement(By.id("ok-button"))
                .click();
        assertEquals(getDriver().findElement(By.cssSelector("#breadcrumbs > li:nth-child(3) > a"))
                .getText(), "3451643561");
    }

    @Test //negative test
    public void testCreateNewItemWithNameFromSpecialCharacters() {

        getDriver().findElement(By.xpath("//*[@id=\"tasks\"]/div[1]/span/a"))
                .click();
        getDriver().findElement(By.className("jenkins-input"))
                .sendKeys("@");
        assertEquals(getDriver().findElement(By.xpath("//*[@id=\"itemname-invalid\"]"))
                .getText(), "» ‘@’ is an unsafe character");
    }

    @Test
    public void testOutOfBoundNameLength() {

        getDriver().findElement(By.xpath("//*[@id=\"tasks\"]/div[1]/span/a"))
                .click();
        getDriver().findElement(By.className("jenkins-input"))
                //252 symbols
                .sendKeys("Далеко-далеко за словесными горами в стрчане " +
                        "гласнцых и согласвных живут рыбные тексты. Вдали от всех живут они в " +
                        "буквенных домах на берегу Семантика большого языкового океана. " +
                        "Маленький ручеек Даль журчит по всей стране и обеспечивает ее всеми необходим");
        getDriver().findElement(By.className("hudson_model_FreeStyleProject"))
                .click();
        getDriver().findElement(By.id("ok-button"))
                .click();
        assertEquals(getDriver().findElement(By.xpath("//*[@id=\"error-description\"]/h2"))
                .getText(), "A problem occurred while processing the request.");

    }
}
