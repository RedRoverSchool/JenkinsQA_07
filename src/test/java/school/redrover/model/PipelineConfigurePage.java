package school.redrover.model;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import school.redrover.model.base.BaseConfigurationPage;
import school.redrover.model.base.BaseProjectPage;

import java.util.List;

public class PipelineConfigurePage extends BaseConfigurationPage<PipelineDetailsPage, PipelineConfigurePage> {

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

    @FindBy(xpath = "//label[text()='Do not allow concurrent builds']")
    private WebElement concurrentBuildsCheckboxLabel;

    @FindBy(id = "cb3")
    private WebElement concurrentBuildsCheckbox;

    @FindBy(xpath = "//div[@hashelp = 'true']//a[contains(@tooltip, '')]")
    private List<WebElement> tooltipsList;

    public PipelineConfigurePage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected PipelineDetailsPage createProjectPage() {
        return new PipelineDetailsPage(getDriver());
    }

    public PipelineConfigurePage clickProjectIsParameterized() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].click()", projectIsParameterizedCheckbox);

        return this;
    }

    public PipelineConfigurePage clickAddParameter() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].scrollIntoView(true)", projectIsParameterizedCheckbox);
        addParameterButton.click();

        return this;
    }

    public PipelineConfigurePage selectChoiceParameter() {
        choiceParameterOption.click();

        return this;
    }

    public PipelineConfigurePage setParameterName(String name) {
        parameterNameField.sendKeys(name);

        return this;
    }

    public PipelineConfigurePage setParameterChoices(List<String> parameterChoices) {
        for (int i = 0; i < parameterChoices.size(); i++) {
            if (i != parameterChoices.size() - 1) {
                parameterChoicesField.sendKeys(parameterChoices.get(i) + "\n");
            } else {
                parameterChoicesField.sendKeys(parameterChoices.get(i));
            }
        }

        return this;
    }

    public PipelineConfigurePage selectPipelineScriptSampleByValue(String value) {
        new Select(pipelineScriptSamplesDropdown).selectByValue(value);

        return this;
    }

    public PipelineConfigurePage setBuildAfterOtherProjectsCheckbox() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].click();", buildAfterOtherProjectsCheckbox);

        return this;
    }

    public PipelineConfigurePage setProjectsToWatch(String projectName) {
        projectsToWatchField.sendKeys(projectName);

        return this;
    }

    public PipelineConfigurePage clickAlwaysTriggerRadio() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].click();", alwaysTriggerRadio);

        return this;
    }

    public PipelineConfigurePage setPipelineScript(String pipelineScript) {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].scrollIntoView(true)", pipelineScriptTextAreaLine);
        pipelineScriptTextArea.sendKeys(pipelineScript);

        return this;
    }

    public PipelineConfigurePage selectStringParameter() {
        stringParameterOption.click();

        return this;
    }

    public PipelineConfigurePage clickDoNotAllowConcurrentBuilds() {
        getWait5().until(ExpectedConditions.elementToBeClickable(concurrentBuildsCheckboxLabel)).click();

        return this;
    }

    public boolean isDoNotAllowConcurrentBuildsSelected() {

        return concurrentBuildsCheckbox.isSelected();
    }

    public int getNumOfTooltips() {

        return tooltipsList.size();
    }

    public List<String> getTooltipsTitlesList() {

        return tooltipsList
                .stream()
                .map(element -> element.getAttribute("title"))
                .toList();
    }
}
