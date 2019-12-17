package ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * 主提示框
 *
 * @author 郑龙
 * @date 2019/12/17 13:38
 */
public class AlertDialog extends DialogWrapper {
    /**
     * 窗体文本
     */
    private String text;

    /**
     * 图片路径
     */
    private String imagePath;

    public AlertDialog(@Nullable Project project, String imagePath, String title, String text) {
        super(project, false, true);
        setTitle(title);
        this.imagePath = imagePath;
        this.text = text;
        this.init();
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return createFrame();
    }

    @NotNull
    @Override
    protected Action[] createActions() {
        //只有一个ok按钮
        return new Action[]{getOKAction()};
    }

    @Override
    public void show() {
        if (Messages.isMacSheetEmulation()) {
            this.setInitialLocationCallback(() -> {
                JRootPane rootPane = SwingUtilities.getRootPane(this.getWindow().getParent());
                if (rootPane == null) {
                    rootPane = SwingUtilities.getRootPane(this.getWindow().getOwner());
                }

                Point p = rootPane.getLocationOnScreen();
                p.x += (rootPane.getWidth() - this.getWindow().getWidth()) / 2;
                return p;
            });
            this.getPeer().getWindow().setOpacity(0.8F);
            this.setAutoAdjustable(false);
            this.setSize(this.getPreferredSize().width, 0);
        }

        super.show();
    }

    /**
     * 创建容器
     *
     * @return {@link JComponent}
     */
    private JComponent createFrame() {
        JPanel panel = new JPanel(new BorderLayout());
        //image
        URL iconPath = this.getClass().getResource(this.imagePath);
        JLabel label = new JLabel(new ImageIcon(iconPath));
        //label
        JLabel text = new JLabel(this.text);
        text.setFont(new Font("微软雅黑", Font.BOLD, 18));

        //添加进容器
        panel.add(text, BorderLayout.SOUTH);
        panel.add(label, BorderLayout.CENTER);
        panel.setSize(300, 600);

        return panel;
    }
}
