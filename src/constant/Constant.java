package constant;

/**
 * 常量池
 *
 * @author 郑龙
 * @date 2019/12/16 19:09
 */
public class Constant {

    /**
     * 设置窗口相关
     */
    public static class Settings {
        /**
         * 设置窗口标题
         */
        public static final String SETTING_WINDOW_TITLE = "Assistant Setting";

        /**
         * 窗体提示文本
         */
        public static final String SETTING_ALERT_TEXT = "请选择提醒周期（单位：分钟）:";

        /**
         * 初始化的可选数组
         */
        public static final String[] SELECT_ARRAY = new String[]{"30", "60", "90", "120", "240", "360", "480"};
    }

    /**
     * 停止窗口相关
     */
    public static class Stop {
        /**
         * title
         */
        public static final String TITLE = "提示";

        /**
         * 成功正文
         */
        public static final String SUCCESS_TEXT = "停止成功！";

        /**
         * 失败正文
         */
        public static final String FAIL_TEXT = "停止失败！";
    }

    /**
     * 提示窗相关
     */
    public enum AlertDialog {
        /**
         * CXK
         */
        CXK("/icons/cxk_basketball.gif", "CXK提醒您", "工作{0}分钟辛苦了吧？  欣赏一段鸡你太美休息一下吧~~ ！");

        /**
         * 装载图片路径
         */
        String imagePath;

        /**
         * 标题文本
         */
        String title;

        /**
         * 提示语文本
         */
        String text;

        AlertDialog(String imagePath, String title, String text) {
            this.imagePath = imagePath;
            this.title = title;
            this.text = text;
        }

        public String getImagePath() {
            return imagePath;
        }

        public String getTitle() {
            return title;
        }

        public String getText() {
            return text;
        }
    }
}
