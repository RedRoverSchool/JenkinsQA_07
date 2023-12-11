package school.redrover.model.views;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

import java.util.List;

public class NewViewConfigurePage extends BasePage<NewViewConfigurePage> {

    @FindBy(xpath = "//div[@class = 'listview-jobs']/span/span[1]")
    private WebElement firstJobCheckbox;

    @FindBy(xpath = "//button[@name = 'Submit']")
    private WebElement configurationOKButton;

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

    public NewViewConfigurePage checkSelectedJobCheckbox(String jobName) {
        getDriver().findElement(By.xpath("//label[@title = '" + jobName + "']")).click();

        return this;
    }

    public NewViewConfigurePage checkFirstJobCheckbox() {
        firstJobCheckbox.click();

        return this;
    }

    public <T> T clickOKButton(T page) {
        configurationOKButton.click();

        return page;
    }
}
