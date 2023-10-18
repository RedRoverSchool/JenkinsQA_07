package school.redrover;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

@Ignore
public class ArSaFirstTest extends BaseTest {


    String initialURL = "https://www.selenium.dev/selenium/web/web-form.html";
    String redirectedURL = "https://www.selenium.dev/selenium/web/submitted-form.html?my-text=&my-password=&my-textarea=&my-readonly=Readonly+input&my-select=Open+this+select+menu&my-datalist=&my-file=&my-check=on&my-radio=on&my-colors=%23563d7c&my-date=&my-range=5&my-hidden=";

    //Check if Title of the Website is correct..
    @Test
    public void testTitleCheck()
    {
        getDriver().get(initialURL);

        String title = getDriver().getTitle();
        Assert.assertEquals(title, "Web form");
    }

    //Check if the form "Text Input" is working, try to input some text, check the input text.
    @Test
    public void testTextInput()
    {

        getDriver().get(initialURL);
        WebElement nameInput = getDriver().findElement(By.id("my-text-id"));
        nameInput.sendKeys("Artur Sabanadze");
        String enteredText = nameInput.getAttribute("value");
        Assert.assertEquals(enteredText, "Artur Sabanadze");
    }

    //Check "my-disabled" element for the following functions: is disabled, is visible and the placeholder text is correct.
    @Test
    public void testDisabledTextInput()
    {

        getDriver().get(initialURL);
        WebElement disabledInput = getDriver().findElement(By.name("my-disabled"));
        Assert.assertEquals(disabledInput.getAttribute("disabled"), "true");
        Assert.assertTrue(disabledInput.isDisplayed());
        String placeholderText = disabledInput.getAttribute("placeholder");
        Assert.assertEquals(placeholderText, "Disabled input");
    }

    //Check the color of "my-colors" element
    @Test
    public void testColorCheck()
    {

        getDriver().get(initialURL);
        WebElement colorInputElement = getDriver().findElement(By.name("my-colors"));
        String actualColor = colorInputElement.getAttribute("value");
        String expectedColor = "#563d7c";
        Assert.assertEquals(actualColor, expectedColor, "Element color is not as expected");
    }


    //Check the functionality of submit button and some elements on redirected url.
    @Test
    public void testSubmitButton() {
        getDriver().get(initialURL);

        WebElement submitButton = getDriver().findElement(By.className("btn-outline-primary"));
        submitButton.click();

        String currentUrl = getDriver().getCurrentUrl();
        Assert.assertEquals(currentUrl, redirectedURL, "Incorrect URL after submission");

        WebElement headerElement = getDriver().findElement(By.className("display-6"));
        WebElement messageElement = getDriver().findElement(By.id("message"));

        Assert.assertEquals(headerElement.getText(), "Form submitted", "Incorrect header text");
        Assert.assertEquals(messageElement.getText(), "Received!", "Incorrect message text");
    }
}