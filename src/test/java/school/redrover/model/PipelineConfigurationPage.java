package school.redrover.model;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
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

    @FindBy(xpath = "//div[@class='samples']/select")
    private WebElement pipelineScriptSamplesDropdown;

    @FindBy(xpath = "//label[text()='Build after other projects are built']")
    private WebElement buildAfterOtherProjectsCheckbox;

    @FindBy(name = "_.upstreamProjects")
    private WebElement projectsToWatchField;

    @FindBy(xpath = "//label[text()='Always trigger, even if the build is aborted']")
    private WebElement alwaysTriggerRadio;

    @FindBy(xpath = "//div[@class='ace_line']")
    private WebElement pipelineScriptTextAreaLine;

    @FindBy(className = "ace_text-input")
    private WebElement pipelineScriptTextArea;

    @FindBy(id = "yui-gen10")
    private WebElement stringParameterOption;

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

    public PipelineDetailsPage clickSaveButton() {
        saveButton.click();

        return new PipelineDetailsPage(getDriver());
    }

    public PipelineConfigurationPage selectPipelineScriptSampleByValue(String value) {
        new Select(pipelineScriptSamplesDropdown).selectByValue(value);

        return this;
    }

    public PipelineConfigurationPage setBuildAfterOtherProjectsCheckbox() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].click();", buildAfterOtherProjectsCheckbox);

        return this;
    }

    public PipelineConfigurationPage setProjectsToWatch(String projectName) {
        projectsToWatchField.sendKeys(projectName);

        return this;
    }

    public PipelineConfigurationPage clickAlwaysTriggerRadio() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].click();", alwaysTriggerRadio);

        return this;
    }

    public PipelineConfigurationPage setPipelineScript(String pipelineScript) {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].scrollIntoView(true)", pipelineScriptTextAreaLine);
        pipelineScriptTextArea.sendKeys(pipelineScript);

        return this;
    }

    public PipelineConfigurationPage selectStringParameter() {
        stringParameterOption.click();

        return this;
    }
}
