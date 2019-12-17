import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;
import constant.Constant;
import service.ScheduledService;

/**
 * @author 郑龙
 * @date 2019/12/17 19:31
 */
public class StopAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        boolean flag = false;
        if (!ScheduledService.getInstance().isEmptyTaskTimer()) {
            ScheduledService.getInstance().removeTask();
            flag = true;
        }

        Messages.showDialog(flag ? Constant.Stop.SUCCESS_TEXT : Constant.Stop.FAIL_TEXT, Constant.Stop.TITLE,
                new String[]{"确定"}, 0,
                Messages.getInformationIcon());
    }
}
