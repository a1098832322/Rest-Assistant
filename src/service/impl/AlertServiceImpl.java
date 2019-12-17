package service.impl;

import com.intellij.openapi.project.Project;
import compontent.MessageBuilder;
import constant.Constant;
import service.AlertService;
import ui.AlertDialog;

/**
 * 提醒服务实现类
 *
 * @author 郑龙
 * @date 2019/12/17 13:45
 */
public class AlertServiceImpl implements AlertService {
    /**
     * 显示弹窗
     *
     * @param project     当前项目上下文
     * @param dialogModel 弹窗model，选用哪种模式的弹窗
     * @param timeMinutes 时间（分钟）
     */
    @Override
    public void showAlertDialog(Project project, Constant.AlertDialog dialogModel, int timeMinutes) {
        AlertDialog alertDialog = new AlertDialog(project,
                dialogModel.getImagePath(),
                dialogModel.getTitle(),
                new MessageBuilder().setBody(dialogModel.getText()).setVariables(timeMinutes).build());
        alertDialog.show();
    }
}
