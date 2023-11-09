package school.redrover;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;

public class FreestyleProjectIldanaTest extends BaseTest {

    private final static String PROJECT_NAME = "Ildana Frolova";
    private final static String DESCRIPTION_TEXT = "Test add description to project";

    private void createFreeStyleProject2(String PROJECT_NAME) {
        getDriver().findElement(By.xpath("//span[@ class='task-link-wrapper ']//a[@ href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//div[@class='add-item-name']//input[@name='name']")).sendKeys(PROJECT_NAME);
        getDriver().findElement(By.xpath("//ul[@class='j-item-options']/li[@class='hudson_model_FreeStyleProject']")).click();

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

        getDriver().findElement(By.xpath("//button[@type='submit']")).click();
        getDriver().findElement(By.xpath("//div[@id='bottom-sticker']//button[@name='Submit']")).click();

    }

    private void addDescription(String DESCRIPTION_TEXT) {
        getDriver().findElement(By.xpath("//a[@id='description-link']")).click();
        getDriver().findElement(By.xpath("//textarea[@name='description']")).sendKeys(DESCRIPTION_TEXT);
        getDriver().findElement(By.xpath("//form[@action='submitDescription']//button[@name='Submit']")).click();
    }

    private void goHomePage() {
        getDriver().findElement(By.xpath("//ol[@id=\"breadcrumbs\"]/li[1]"));
    }

    @Test
    public void testAddDescription2FreestyleProject() {

        createFreeStyleProject2(PROJECT_NAME);

        getDriver().findElement(By.xpath("//a[@id='description-link']")).click();
        getDriver().findElement(By.xpath("//textarea[@name='description']")).sendKeys(DESCRIPTION_TEXT);
        getDriver().findElement(By.xpath("//form[@action='submitDescription']//button[@name='Submit']")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//div[@id='description']/div[1]")).getText(), DESCRIPTION_TEXT
        );

    }

    @Test
    public void testEditDescription2() {

        final String descriptionChangeText = "Test change description to project";

        createFreeStyleProject2(PROJECT_NAME);
        addDescription(DESCRIPTION_TEXT);

        getDriver().findElement(By.xpath("//a[@href='editDescription']")).click();

        WebElement textArea = getDriver().findElement(By.xpath("//textarea[@name='description']"));
        textArea.clear();
        textArea.sendKeys(descriptionChangeText);
        getDriver().findElement(By.xpath("//form[@action='submitDescription']//button[@name='Submit']")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//div[@id='description']/div[1]")).getText(), descriptionChangeText
        );

    }

    @Test
    public void testDeleteDescription2() {

        final String descriptionText = "Test add description to project";

        createFreeStyleProject2(PROJECT_NAME);
        addDescription(DESCRIPTION_TEXT);

        getDriver().findElement(By.xpath("//a[@href='editDescription']")).click();

        getDriver().findElement(By.xpath("//textarea[@name='description']")).clear();
        getDriver().findElement(By.xpath("//form[@action='submitDescription']//button[@name='Submit']")).click();

        WebElement afterClear = getDriver().findElement(By.xpath("//div[@id='description']/div[1]"));

        String textAfterClear = afterClear.getText();

        Assert.assertTrue(textAfterClear.isEmpty(), " ");

    }

    @Test
    public void testConfigureAdvancedSettings() {

        createFreeStyleProject2(PROJECT_NAME);
        //goHomePage();

        //getDriver().findElement(By.xpath("//table[@id=\'projectstatus\']//td[3]")).click();

//        getDriver().findElement(By.xpath("//span[text()='" + PROJECT_NAME + "']/..")).click();
          getDriver().findElement(By.xpath("//span[text()='Configure']/..")).click();
          //getDriver().findElement(By.xpath("//div[@id='tasks']//div[5]")).click();
          getDriver().findElement(By.xpath("(//button[@class=\'jenkins-button advanced-button advancedButton\'][contains(text(),\'Advanced\')])[1]"))
                  .click();
          WebElement helpTextIcon = getDriver().findElement(By.xpath("//a[@title='Help for feature: Quiet period']"));
          Assert.assertTrue(helpTextIcon.isDisplayed());

//        getDriver().findElement(By.xpath("//div[@class='tbody dropdownList-container']//div[@ref='cb9']")).click();
//
//        //WebElement advancedArea = getDriver().findElement(By.xpath("//div[@class='setting-main']//input[@name='quiet_period']"));
//
//        //advancedArea.clear();
//        //advancedArea.sendKeys("10");
//
//
//
//        Assert.assertTrue(helpTextIcon.isDisplayed());
//
//        helpTextIcon.click();
//
//
//        // Получение значения атрибута placeholder
//        String helpText = helpTextIcon.getText();
//
//        // Проверка наличия текста подсказки
//        Assert.assertNotNull(helpText);
//    }
//
//
//
//        //getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
//
//
//
//    }
//

    }
}
