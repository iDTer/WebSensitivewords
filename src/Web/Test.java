package Web;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;

/**
 * @version 1.0
 * @author 刘胜军
 */
public class Test extends JFrame {
    private static final long serialVersionUID = 1L;

    /** 定义一个历史面板，用于显示已经发送的文字 */
    private JTextPane _old = new JTextPane();

    /** 定义一个输入面板，用于显示正在输入的内容 */
    private JTextPane _new = new JTextPane();

    /** 声明三组样式，具体的在方法public static void styleInit() {}中定义 */
    private Style style1 = null;
    private Style style2 = null;
    private Style style3 = null;

    /** 下拉列表，用于选择样式 */
    private JComboBox<String> box = new JComboBox<String>();

    /** 发送按钮，用于将消息提交到历史面板 */
    private JButton send = new JButton("提交");

    public static void main(String[] args) {
        new Test();
    }

    /**
     * 构造方法，需要完成所以初始化操作 鼠标放在方法名上，可以显示其内容
     */
    public Test() {
        styleInit();
        init();
    }

    /** 样式初始化 */
    public void styleInit() {

        Style style = _new.getStyledDocument().addStyle(null, null);// 获取组件空样式，addStyle(null,
        // null)会返回一个空样式

        StyleConstants.setFontFamily(style, "楷体");// 为style样式设置字体属性
        StyleConstants.setFontSize(style, 18);// 为style样式设置字体大小

        Style normal = _new.addStyle("normal", style);// 将style样式添加到组件，并命名为normal，返回一个样式由Style
        // normal变量接收
        /** 这个时候，组件编辑器关联的模型中就添加了一个样式normal，这个样式是最基本的一个样式，其他样式可以根据他进行修改 */

        style1 = _new.addStyle("style1", normal);// 基于normal样式，在添加三次，分别命名为style1，style2，style3
        style2 = _new.addStyle("style2", normal);// 此时，style1，style2，style3三个样式和normal样式是一模一样的
        style3 = _new.addStyle("style3", normal);// 如果修改，可以对每个变量单独修改，具体修改方式如下

        StyleConstants.setForeground(style1, Color.GREEN);// 将style1的颜色设置为绿色

        StyleConstants.setForeground(style2, Color.RED);// 将style2的颜色设置为红色

        StyleConstants.setForeground(style3, Color.BLACK);// 将style3的颜色设置为黑色
        StyleConstants.setFontSize(style3, 14);// 将style3的大小设置为14
    }

    /** 初始化布局 */
    public void init() {
        this.setBounds(200, 100, 420, 520);
        this.setLayout(null);

        this._old.setEditable(false);
        // 定义滚动面板，放历史面板，以实现滚动条（有需要的时候显示）和换行
        JScrollPane js_old = new JScrollPane(_old,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        // 设置位置大小
        js_old.setBounds(0, 0, 400, 300);
        // 添加到窗体
        this.add(js_old);

        // 定义滚动面板，放输入面板，以实现滚动条（有需要的时候显示）和换行
        JScrollPane js_new = new JScrollPane(_new,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        // 设置位置大小
        js_new.setBounds(0, 350, 400, 150);
        // 添加到窗体
        this.add(js_new);

        this.box.addItem("style1");
        this.box.addItem("style2");
        this.box.addItem("style3");
        this.box.setBounds(50, 315, 100, 20);
        this.add(this.box);

        this.send.setBounds(200, 315, 100, 20);
        this.add(this.send);

        this.send.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                inserMessage();
            }
        });

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    /** 将文字插入到历史面板，并清空输入面板 */
    public void inserMessage() {
        try {
            /** 判断下拉列表内容，确定使用哪种样式 */
            Style style = (box.getSelectedItem().equals("style1")) ? style1
                    : (box.getSelectedItem().equals("style2")) ? style2
                    : style3;
            this._old.getStyledDocument().insertString(
                    this._old.getStyledDocument().getLength(),
                    this._new.getText() + "\n", style);
            /** 将输入面板置空 */
            this._new.setText(null);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

}