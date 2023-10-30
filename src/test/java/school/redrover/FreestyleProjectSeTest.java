package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;
import java.util.List;

public class FreestyleProjectSeTest extends BaseTest {
    private boolean isItemTitleExists(String itemName){
        List<WebElement> itemsList = getDriver().findElements(By.cssSelector(".jenkins-table__link.model-link.inside span"));
        boolean res = false;
        if(itemsList.isEmpty()){
            return res;
        }else {
            for (WebElement e : itemsList) {
                if (e.getText().equals(itemName)) {
                    res = true;
                    break;
                }
            }
        }

        return res;
    }

    private void createAnItem(String itemName) {
        Wait<WebDriver> wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        String createdItemName = "New " + itemName;

        if(isItemTitleExists(createdItemName)){
            int randInt =((int)(Math.random()*100));
            createdItemName = createdItemName +randInt;

        }else{
            createdItemName = createdItemName;
        }

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(createdItemName);
        try {
            List<WebElement> items = getDriver().findElements(By.cssSelector(".label"));
            for (WebElement el : items){
                if (itemName.equals(el.getText())){
                    el.click();
                    break;
                }
            }
            wait.until(ExpectedConditions.elementToBeClickable(By.id("ok-button"))).click();
//            getDriver().findElement(By.id("jenkins-name-icon")).click();

        } catch (Exception timeoutException){
            System.out.println("Error: Wrong Item name");
        }
    }

    @Test
    public void testHelpDescriptionOfDiscardOldBuildsIsClosed() {
        createAnItem("Freestyle project");
        WebElement checkbox = getDriver().findElement(By.cssSelector(" #cb4[type='checkbox']"));
        new Actions(getDriver())
                .click(checkbox)
                .perform();

        Assert.assertEquals(getDriver().findElement(By.cssSelector("[nameref='rowSetStart26'] .form-container.tr"))
                .getAttribute("style"), "");
    }
}
