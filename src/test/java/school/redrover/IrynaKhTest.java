package school.redrover;

import org.openqa.selenium.By;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import java.util.List;

public class IrynaKhTest extends BaseTest {
    @Test
    public void testInventoryPage() {
        getDriver().get("https://www.saucedemo.com/inventory.html");
        WebElement usernameInput = getDriver().findElement(By.id("user-name"));
        WebElement passwordInput = getDriver().findElement(By.id("password"));
        WebElement loginButton = getDriver().findElement(By.id("login-button"));

        usernameInput.sendKeys("standard_user");
        passwordInput.sendKeys("secret_sauce");
        loginButton.click();

        WebElement productsPageTitle = getDriver().findElement(By.className("title"));
        Assert.assertTrue(productsPageTitle.isDisplayed(), "Not on product page");

        List<WebElement> inventoryItemDescriptions = getDriver().findElements(By.className("inventory_item_desc"));
        List<WebElement> inventoryItemTitles = getDriver().findElements(By.className("inventory_item_name"));

        for (int i = 0; i < inventoryItemDescriptions.size(); i++) {
            inventoryItemTitles.get(i).getText();
            inventoryItemDescriptions.get(i).getText();

        }

    }
}
