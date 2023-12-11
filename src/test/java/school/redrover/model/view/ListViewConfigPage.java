package school.redrover.model.view;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.HomePage;
import school.redrover.model.base.BasePage;
import school.redrover.model.base.BaseViewConfigPage;
import school.redrover.runner.SeleniumUtils;

import java.util.List;

public class ListViewConfigPage extends BaseViewConfigPage {

    @FindBy(xpath = "//button[@name = 'Submit']")
    private WebElement configurationOKButton;

    @FindBy(xpath = "//div[@class = 'listview-jobs']/span/span[1]")
    private WebElement firstJobCheckbox;

    @FindBy(xpath = "//input[@name='name']")
    private WebElement inputName;

    @FindBy(xpath = "//div[@class='listview-jobs']/span/span/label")
    private List<WebElement> allJobsCheckboxes;

    @FindBy(xpath = "//button[@id = 'yui-gen3-button']")
    private WebElement addColumnButton;

    @FindBy(xpath = "//a[@class = 'yuimenuitemlabel']")
    private List<WebElement> extraColumns;

    public ListViewConfigPage(WebDriver driver) {
        super(driver);
    }

    public ListViewConfigPage checkSelectedJobCheckbox(String jobName) {
        getDriver().findElement(By.xpath("//label[@title = '" + jobName + "']")).click();

        return this;
    }

    public ViewPage clickOKButton() {
        configurationOKButton.click();

        return new ViewPage(getDriver());
    }

    public ListViewConfigPage checkFirstJobCheckboxWithJavaExecutor() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollBy(0,600)");
        firstJobCheckbox.click();

        return this;
    }

    public ListViewConfigPage checkJobsCheckboxesWithJavaExecutor() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollBy(0,600)");
        for (WebElement job : allJobsCheckboxes) {
            if (!job.isSelected()) {
                job.click();
            }
        }

        return this;
    }

    public ListViewConfigPage typeNewName(String name) {
        inputName.clear();
        inputName.sendKeys(name);

        return this;
    }

    public ListViewConfigPage addColumn(String columnName) {
        SeleniumUtils.jsScroll(getDriver(), addColumnButton);
        addColumnButton.click();
        extraColumns.stream() .filter(el -> el.getText().contains(columnName))
                .findFirst()
                .ifPresent(WebElement::click);

        return this;
    }

    public ListViewConfigPage deleteColumn(String columnName) {
        WebElement deleteButton = getDriver().findElement(By.xpath("//div[@name='columns']/div/div[contains(text(),'" + columnName + "')]/button"));
        SeleniumUtils.jsScroll(getDriver(), deleteButton);
        deleteButton.click();
        getWait2().until(ExpectedConditions.invisibilityOf(deleteButton));

        return this;
    }

    public ListViewConfigPage moveColumnToFirstPosition(String columnName) {
        WebElement column = getDriver().findElement(By.xpath(
                "//div[contains(text(), '" + columnName + "')]/div[@class = 'dd-handle']"));

        WebElement placeForTheReorderedColumn = getDriver().findElement(By.xpath(
                "//div[@class = 'repeated-chunk__header'][1]"));

        SeleniumUtils.jsScroll(getDriver(), column);

        new Actions(getDriver())
                .clickAndHold(column)
                .moveToElement(placeForTheReorderedColumn)
                .release(placeForTheReorderedColumn)
                .perform();

        return this;
    }

    public ListViewConfigPage checkFirstJobCheckbox() {
        firstJobCheckbox.click();

        return this;
    }

    public ListViewConfigPage clickCheckboxByTitle(String title) {
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
}
