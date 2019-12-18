package service;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ServiceManager;
import service.impl.ScheduledServiceImpl;

import java.awt.event.ActionListener;

/**
 * 定时任务服务
 *
 * @author 郑龙
 * @date 2019/12/17 19:02
 */
public interface ScheduledService {
    /**
     * 添加任务
     *
     * @param delay    延迟以及周期
     * @param listener 事件
     */
    void addTask(int delay, ActionListener listener);

    /**
     * 是否有定时任务正在执行
     *
     * @return 是/否
     */
    boolean isTaskRunning();

    /**
     * 判断是否是空任务定时器
     *
     * @return 是/否
     */
    boolean isEmptyTaskTimer();

    /**
     * 移除任务
     */
    void removeTask();

    /**
     * 开始执行任务
     */
    void start();

    /**
     * 停止任务
     */
    void stop();

    /**
     * getInstance
     *
     * @return {@link ScheduledServiceImpl}
     */
    static ScheduledService getInstance() {
        if (ApplicationManager.getApplication() != null) {
            return ServiceManager.getService(ScheduledService.class);
        } else {
            return ScheduledServiceImpl.getInstance();
        }
    }
}
