import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.InputValidator;
import constant.Constant;
import service.AlertService;
import service.ScheduledService;
import ui.SettingDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * 设置窗口
 *
 * @author 郑龙
 * @date 2019/12/16 18:20
 */
public class SettingAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        //拿到项目上下文
        final Project p = e.getProject();

        //创建设置窗口
        String result = new SettingDialog().setText(Constant.Settings.SETTING_ALERT_TEXT)
                .setTitle(Constant.Settings.SETTING_WINDOW_TITLE)
                .setSelectedValues(Constant.Settings.SELECT_ARRAY).setInputValidator(new InputValidator() {
                    @Override
                    public boolean checkInput(String s) {
                        try {
                            long minute = Long.parseLong(s);
                            //最大值为480分钟
                            return minute > 0 && minute <= 480;
                        } catch (Exception e) {
                            //输入异常
                        }
                        return false;
                    }

                    @Override
                    public boolean canClose(String s) {
                        return false;
                    }
                }).createSettingDialog();


        try {
            int period = Integer.parseInt(result);
            ScheduledService.getInstance().addTask(period * 60 * 1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ProgressManager.getInstance()
                            .executeNonCancelableSection(
                                    () -> AlertService.getInstance().showAlertDialog(p, Constant.AlertDialog.CXK, period));
                }
            });
            //开始运行
            ScheduledService.getInstance().start();
        } catch (Exception ex) {
            //exception
            ex.printStackTrace();
        }


    }
}
