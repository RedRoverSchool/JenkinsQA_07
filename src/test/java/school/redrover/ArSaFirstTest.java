package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class ArSaFirstTest extends BaseTest {

    //Check if Title of the Website is correct..
    @Test
    public void testTitleCheck()
    {
            getDriver().get("https://www.selenium.dev/selenium/web/web-form.html");

            String title = getDriver().getTitle();
            Assert.assertEquals(title, "Web form");
    }
    //Check if the form "Text Input" is working, try to input some text, check the input text.
    @Test
    public void testTextInput() {

            getDriver().get("https://www.selenium.dev/selenium/web/web-form.html");

            WebElement nameInput = getDriver().findElement(By.id("my-text-id"));
            nameInput.sendKeys("Artur Sabanadze");

            String enteredText = nameInput.getAttribute("value");
            Assert.assertEquals(enteredText, "Artur Sabanadze");
        }

    //Check "my-disabled" element for the following functions: is disabled, is visible and the placeholder text is correct.
    @Test
    public void testDisabledTextInput() {

        getDriver().get("https://www.selenium.dev/selenium/web/web-form.html");

        WebElement disabledInput = getDriver().findElement(By.name("my-disabled"));

        // Check if the input is disabled
        Assert.assertEquals(disabledInput.getAttribute("disabled"), "true");

        //Check if the element is visible
        Assert.assertTrue(disabledInput.isDisplayed());

        // Check the placeholder text in the disabled input
        String placeholderText = disabledInput.getAttribute("placeholder");
        Assert.assertEquals(placeholderText, "Disabled input");


    }

    //Check the color of "my-colors" element
    @Test
    public void testColorCheck() {

        getDriver().get("https://www.selenium.dev/selenium/web/web-form.html");


        WebElement colorInputElement = getDriver().findElement(By.name("my-colors"));


        String actualColor = colorInputElement.getAttribute("value");


        String expectedColor = "#563d7c";


        Assert.assertEquals(actualColor, expectedColor, "Element color is not as expected");

    }
}
