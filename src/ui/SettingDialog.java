package ui;

import com.intellij.openapi.ui.InputValidator;
import com.intellij.openapi.ui.Messages;
import compontent.StorageComponent;
import constant.Constant;

/**
 * 设置窗口
 *
 * @author 郑龙
 * @date 2019/12/17 15:20
 */
public class SettingDialog {
    /**
     * 窗口标题
     */
    private String title;

    /**
     * 窗内文字
     */
    private String text;

    /**
     * 备选值
     */
    private String[] selectedValues;

    /**
     * 默认选中的值
     */
    private String defaultValue;

    /**
     * 参数校验器
     */
    private InputValidator inputValidator;

    public SettingDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public SettingDialog setText(String text) {
        this.text = text;
        return this;
    }

    public SettingDialog setSelectedValues(String[] selectedValues) {
        this.selectedValues = isEmpty(selectedValues) ? initSelectValues() : selectedValues;
        return this;
    }

    public SettingDialog setInputValidator(InputValidator inputValidator) {
        this.inputValidator = inputValidator;
        return this;
    }

    /**
     * 创建设置窗口
     *
     * @return 点击 “ok” 按钮后返回所选中的值
     */
    public String createSettingDialog() {
        defaultValue = readFromStorage();

        String result = Messages.showEditableChooseDialog(this.text, this.title
                , Messages.getQuestionIcon(), selectedValues, defaultValue, inputValidator);
        saveToStorage(result);

        return result;
    }

    /**
     * 从存储中读取
     *
     * @return 存储的值或默认生成备选的第一项的值
     */
    private String readFromStorage() {
        return StorageComponent.getValue("lastSelectedMinutes") == null
                ? isEmpty(selectedValues) ? "" : selectedValues[0]
                : StorageComponent.getValue("lastSelectedMinutes");
    }

    /**
     * 存储
     *
     * @param result 选取的值
     */
    private void saveToStorage(String result) {
        // 存储新值
        StorageComponent.save("lastSelectedMinutes", result);
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
     * 初始化下拉框选择
     *
     * @return initValues
     */
    private String[] initSelectValues() {
        return Constant.Settings.SELECT_ARRAY;
    }

}
