package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import school.redrover.model.base.BasePage;

import java.util.List;

public class NewViewConfigurePage extends BasePage {
    public NewViewConfigurePage(WebDriver driver) {
        super(driver);
    }

    public List<String> getAllCheckboxes() {
        List<WebElement> elementList = getDriver().findElements(By.xpath("//span[@class='jenkins-checkbox']/input[@type='checkbox']"));
        List<String> resultList = elementList.stream().map(WebElement::getText).toList();

        return resultList;
    }

    public NewViewConfigurePage clickCheckboxByTitle(String title) {
        String xpathCheckboxTitle = String.format("//label[@title='%s']", title);

        List<WebElement> checkboxes = getDriver().findElements(By.xpath(xpathCheckboxTitle));
        for (WebElement checkbox : checkboxes) {
            if (title.equals(checkbox.getAttribute("title")) && !checkbox.isSelected()) {
                checkbox.click();
                break;
            }
        }

        return this;
    }

    public MyViewPage clickOKButton() {
        getDriver().findElement(By.name("Submit")).click();

        return new MyViewPage(getDriver());
    }
}
