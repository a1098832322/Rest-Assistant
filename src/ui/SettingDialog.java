package ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.*;
import compontent.StorageComponent;
import constant.Constant;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

/**
 * 设置窗口
 *
 * @author 郑龙
 * @date 2019/12/17 15:20
 */
public class SettingDialog extends DialogWrapper {
    /**
     * 项目
     */
    private Project project;

    /**
     * 窗内文字
     */
    private String modelText, timeText;

    /**
     * 备选值
     */
    private String[] modelSelectedValues;

    /**
     * 时间备选值
     */
    private String[] timeSelectedValues;

    /**
     * 默认选中模型
     */
    private String defaultModelValue;

    /**
     * 默认选中时间
     */
    private String defaultTimeValue;

    /**
     * 参数校验器
     */
    private InputValidator inputValidator;

    /**
     * 模型和时间选择框
     */
    private ComboBox<String> timeSelector, modelSelector;

    public SettingDialog(@Nullable Project project, String title, String modelText, String timeText
            , String[] timeSelectedValues
            , InputValidator inputValidator) {
        super(project);
        setTitle(title);
        this.modelText = modelText;
        this.timeText = timeText;
        this.modelSelectedValues = initModelSelectValues();
        this.timeSelectedValues = isEmpty(timeSelectedValues) ? initTimeSelectValues() : timeSelectedValues;
        this.defaultModelValue = readFromStorage(true);
        this.defaultTimeValue = readFromStorage(false);
        this.inputValidator = inputValidator;
        this.init();
    }

    public SettingDialog setProject(Project project) {
        this.project = project;
        return this;
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        JPanel panel = this.createIconPanel();
        //模型选择panel
        JPanel modelSelectorPanel = this.createMessagePanel(this.modelText);
        //为了后续自定义扩展，这里选择可编辑的comboBox
        modelSelector = new ComboBox<>(220);
        modelSelector.setModel(new DefaultComboBoxModel<>(Constant.Settings.MODEL_SELECT_ARRAY));
        modelSelector.getEditor().setItem(this.defaultModelValue);
        modelSelector.setSelectedItem(this.defaultModelValue);
        modelSelectorPanel.add(modelSelector, "Center");

        //时间选择panel
        JPanel timeSelectorPanel = this.createMessagePanel(this.timeText);
        timeSelector = new ComboBox<>(220);
        timeSelector.setEditable(true);
        timeSelector.setModel(new DefaultComboBoxModel<>(timeSelectedValues));
        timeSelector.getEditor().setItem(this.defaultTimeValue);
        timeSelector.setSelectedItem(this.defaultTimeValue);
        timeSelectorPanel.add(timeSelector, "Center");

        //添加到主panel
        panel.add(modelSelectorPanel, "Center");
        panel.add(timeSelectorPanel, "South");
        return panel;
    }

    /**
     * 创建一个带有图标的panel
     *
     * @return 图标panel
     */
    @NotNull
    protected JPanel createIconPanel() {
        JPanel panel = new JPanel(new BorderLayout(15, 0));
        JLabel iconLabel = new JLabel(Messages.getQuestionIcon());
        Container container = new Container();
        container.setLayout(new BorderLayout());
        container.add(iconLabel, "North");
        panel.add(container, "West");

        return panel;
    }

    @NotNull
    protected JPanel createMessagePanel(String text) {
        JPanel messagePanel = new JPanel(new BorderLayout());
        JLabel textLabel = new JLabel(text);
        textLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        textLabel.setUI(new MultiLineLabelUI());
        messagePanel.add(textLabel, "North");
        return messagePanel;
    }

    /**
     * 获得选择的时间
     *
     * @return 提醒时间
     */
    private String getTimeSelectorValue() {
        Object o = this.timeSelector.getSelectedItem();
        return o != null ? o.toString() : "";
    }

    /**
     * 获得选择的时间
     *
     * @return 选择模型
     */
    private String getModelSelectorValue() {
        switch (this.modelSelector.getSelectedIndex()) {
            case 1:
                return Constant.AlertDialog.YCY.name();
            case 2:
                return Constant.AlertDialog.OLG1.name();
            case 3:
                return Constant.AlertDialog.OLG2.name();
            case 4:
                return Constant.AlertDialog.DRINK_TEA.name();
            case 5:
                return Constant.AlertDialog.TOUCH_FISH.name();
            case 6:
                return Constant.AlertDialog.WORK_HARD.name();
            case 7:
                return Constant.AlertDialog.DRINK_WATER.name();
            default:
                return Constant.AlertDialog.CXK.name();
        }
    }

    /**
     * 创建设置窗口
     *
     * @return 点击 “ok” 按钮后返回所选中的值
     */
    public String createSettingDialog() {
        this.show();
        String model = getModelSelectorValue();
        String time = getTimeSelectorValue();

        saveToStorage(model, time);
        return time;
    }

    /**
     * 从存储中读取
     *
     * @param isModel 是否是读取模型
     * @return 存储的值或默认生成备选的第一项的值
     */
    private String readFromStorage(boolean isModel) {
        if (isModel) {
            return StorageComponent.getValue("model") == null
                    ? isEmpty(modelSelectedValues) ? "" : modelSelectedValues[0]
                    : StorageComponent.getValue("model");
        } else {
            return StorageComponent.getValue("lastSelectedMinutes") == null
                    ? isEmpty(timeSelectedValues) ? "" : timeSelectedValues[0]
                    : StorageComponent.getValue("lastSelectedMinutes");
        }
    }

    /**
     * 存储
     *
     * @param model 选取的模型
     * @param time  选取的时间值
     */
    private void saveToStorage(String model, String time) {
        // 存储新值
        StorageComponent.save("model", model);
        StorageComponent.save("lastSelectedMinutes", time);
    }

    /**
     * 判断数组是否为空
     *
     * @param array 数组
     * @return 是/否
     */
    private boolean isEmpty(String[] array) {
        return array == null || array.length == 0;
    }

    /**
     * 初始化模型下拉框选择
     *
     * @return initValues
     */
    private String[] initModelSelectValues() {
        return Constant.Settings.MODEL_SELECT_ARRAY;
    }

    /**
     * 初始化时间下拉框选择
     *
     * @return initValues
     */
    private String[] initTimeSelectValues() {
        return Constant.Settings.TIME_SELECT_ARRAY;
    }

}
