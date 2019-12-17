package service.impl;

import service.ScheduledService;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * 定时任务实现类
 *
 * @author 郑龙
 * @date 2019/12/17 19:03
 */
public class ScheduledServiceImpl implements ScheduledService {
    /**
     * 唯一timer
     */
    private Timer timer;

    private Timer getTimer() {
        return timer;
    }

    private void setTimer(Timer timer) {
        this.timer = timer;
    }

    private ScheduledServiceImpl() {
        this.timer = createEmptyTask();
    }

    private static class ScheduledServiceHolder {
        private static final ScheduledServiceImpl INSTANCE = new ScheduledServiceImpl();
    }

    /**
     * instance
     *
     * @return {@link ScheduledServiceImpl}
     */
    public static ScheduledServiceImpl getInstance() {
        return ScheduledServiceHolder.INSTANCE;
    }

    /**
     * 创建一个空任务
     *
     * @return empty task timer
     */
    private Timer createEmptyTask() {
        return new Timer(0, null);
    }

    /**
     * 添加任务
     *
     * @param delay    延迟以及周期
     * @param listener 事件
     */
    @Override
    public void addTask(int delay, ActionListener listener) {
        synchronized (getInstance().getTimer()) {
            if (isTaskRunning()) {
                removeTask();
            }
            getInstance().setTimer(new Timer(delay, listener));
        }
    }

    /**
     * 移除任务
     */
    @Override
    public void removeTask() {
        synchronized (getInstance().getTimer()) {
            //停止任务
            stop();
            //置空
            getInstance().setTimer(createEmptyTask());
        }
    }

    /**
     * 是否有定时任务正在执行
     *
     * @return 是/否
     */
    @Override
    public boolean isTaskRunning() {
        return !isEmptyTaskTimer() && getInstance().getTimer().isRunning();
    }

    /**
     * 判断是否是空任务定时器
     *
     * @return 是/否
     */
    @Override
    public boolean isEmptyTaskTimer() {
        return getInstance().getTimer().getDelay() == 0
                && (getInstance().getTimer().getActionListeners() == null
                || getInstance().getTimer().getActionListeners().length == 0);
    }

    /**
     * 开始执行任务
     */
    @Override
    public void start() {
        getInstance().getTimer().start();
    }

    /**
     * 停止任务
     */
    @Override
    public void stop() {
        getInstance().getTimer().stop();
    }
}
