package school.redrover.model;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

import java.util.List;

public class PipelineConfigurationPage extends BasePage {

    @FindBy(xpath = "//label[text()='This project is parameterized']")
    private WebElement projectIsParameterizedCheckbox;

    @FindBy(id = "yui-gen1-button")
    private WebElement addParameterButton;

    @FindBy(id = "yui-gen4")
    private WebElement choiceParameterOption;

    @FindBy(name = "parameter.name")
    private WebElement parameterNameField;

    @FindBy(name = "parameter.choices")
    private WebElement parameterChoicesField;

    @FindBy(xpath = "//button[@name = 'Submit']")
    private WebElement saveButton;

    public PipelineConfigurationPage(WebDriver driver) {
        super(driver);
    }

    public PipelineConfigurationPage clickProjectIsParameterized() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].click()", projectIsParameterizedCheckbox);

        return this;
    }

    public PipelineConfigurationPage clickAddParameter() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].scrollIntoView(true)", projectIsParameterizedCheckbox);
        addParameterButton.click();

        return this;
    }

    public PipelineConfigurationPage selectChoiceParameter() {
        choiceParameterOption.click();

        return this;
    }

    public PipelineConfigurationPage setParameterName(String name) {
        parameterNameField.sendKeys(name);

        return this;
    }

    public PipelineConfigurationPage setParameterChoices(List<String> parameterChoices) {
        for (int i = 0; i < parameterChoices.size(); i++) {
            if (i != parameterChoices.size() - 1) {
                parameterChoicesField.sendKeys(parameterChoices.get(i) + "\n");
            } else {
                parameterChoicesField.sendKeys(parameterChoices.get(i));
            }
        }

        return this;
    }

    public PipelinePage clickSaveButton() {
        saveButton.click();

        return new PipelinePage(getDriver());
    }
}
