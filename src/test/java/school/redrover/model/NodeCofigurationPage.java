package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BasePage;

public class NodeCofigurationPage extends BasePage {

    @FindBy(xpath = "//button[@formnovalidate='formNoValidate']")
    private WebElement saveButton;

    @FindBy(name = "_.name")
    private WebElement inputName;

    @FindBy(xpath = "//input[contains(@name, 'numExecutors')]")
    private WebElement numberOfExecutorsField;

    @FindBy(xpath = "//input[@name = '_.labelString']")
    private WebElement labelsField;

    @FindBy(xpath = "//input[@name = '_.remoteFS']")
    private WebElement remoteRootDirectoryField;

    @FindBy(xpath = "//div[@class = 'warning']")
    private WebElement warning;

    public NodeCofigurationPage(WebDriver driver) {
        super(driver);
    }

    public <T> T saveButtonClick(T page) {
        saveButton.click();
        return page;
    }

    public NodeCofigurationPage clearAndInputNewName(String newName) {
        inputName.clear();
        inputName.sendKeys(newName);

        return this;
    }

    public ErrorPage inputInvalidNumberOfExecutors(int number) {
        numberOfExecutorsField.sendKeys(String.valueOf(number));
        saveButton.click();

        return new ErrorPage(getDriver());
    }

    public AngryErrorPage inputEnormousNumberOfExecutors(int number) {
        numberOfExecutorsField.sendKeys(String.valueOf(number));
        saveButton.click();

        return new AngryErrorPage(getDriver());
    }

    public NodeDetailsPage inputLabelName(String name) {
        labelsField.clear();
        labelsField.sendKeys(name);
        saveButton.click();

        return new NodeDetailsPage(getDriver());
    }

    public NodeDetailsPage inputRemoteRootDirectory(String path) {
        remoteRootDirectoryField.sendKeys(path);
        saveButton.click();

        return new NodeDetailsPage(getDriver());
    }

    public String getWarningText() {
        return getWait2().until(ExpectedConditions.visibilityOf(warning)).getText();
    }
}
