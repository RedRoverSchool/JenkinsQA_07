package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

import java.util.List;

public class ListViewConfigurationPage extends BasePage {

    @FindBy(xpath = "//button[@name = 'Submit']")
    private WebElement configurationOKButton;

    @FindBy(xpath = "//div[@class = 'listview-jobs']/span/span[1]")
    private WebElement firstJobCheckbox;

    @FindBy(xpath = "//input[@name='name']")
    private WebElement inputName;

    @FindBy(xpath = "//div[@class='listview-jobs']/span/span/label")
    private List<WebElement> allJobsCheckboxes;

    public ListViewConfigurationPage(WebDriver driver) {
        super(driver);
    }

    public ListViewConfigurationPage checkSelectedJobCheckbox(String jobName) {
        getDriver().findElement(By.xpath("//label[@title = '" + jobName + "']")).click();

        return this;
    }

    public ListViewPage clickOKButton() {
        configurationOKButton.click();

        return new ListViewPage(getDriver());
    }

    public ListViewConfigurationPage checkFirstJobCheckboxWithJavaExecutor() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollBy(0,600)");
        firstJobCheckbox.click();

        return this;
    }

    public ListViewConfigurationPage checkJobsCheckboxesWithJavaExecutor() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollBy(0,600)");
        for (WebElement job : allJobsCheckboxes) {
            if (!job.isSelected()) {
                job.click();
            }
        }

        return this;
    }

    public ListViewConfigurationPage typeNewName(String name) {
        inputName.clear();
        inputName.sendKeys(name);

        return this;
    }
}
