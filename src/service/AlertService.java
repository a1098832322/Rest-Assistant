package service;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import constant.Constant;

/**
 * 弹窗提醒服务
 *
 * @author 郑龙
 * @date 2019/12/17 13:45
 */
public interface AlertService {
    /**
     * 显示弹窗
     *
     * @param project     当前项目上下文
     * @param dialogModel 弹窗model，选用哪种模式的弹窗
     * @param timeMinutes 时间（分钟）
     */
    void showAlertDialog(Project project, Constant.AlertDialog dialogModel, int timeMinutes);

    /**
     * getInstance
     *
     * @return {@link service.impl.AlertServiceImpl}
     */
    static AlertService getInstance() {
        if (ApplicationManager.getApplication() != null) {
            return ServiceManager.getService(AlertService.class);
        } else {
            try {
                return (AlertService) AlertService.class.getClassLoader().loadClass("service.impl.AlertServiceImpl").newInstance();
            } catch (IllegalAccessException | ClassNotFoundException | InstantiationException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
