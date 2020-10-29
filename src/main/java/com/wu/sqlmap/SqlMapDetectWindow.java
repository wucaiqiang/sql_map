package com.wu.sqlmap;

import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;


/**
 * 代码生成工具入口
 *
 * @author
 */
public class SqlMapDetectWindow extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;

    JTextField filePathField = new JTextField();

    public static void main(String[] args) {
        new SqlMapDetectWindow();
    }

    public SqlMapDetectWindow() {

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Container container = getContentPane();
        container.setLayout(null);

        Toolkit tk = Toolkit.getDefaultToolkit();//得到Toolkit对象
        Dimension screen = tk.getScreenSize();//得到屏幕的大小
        this.setBounds(((int) (screen.getWidth() - 400) / 2), ((int) (screen.getHeight() - 500) / 2), 400, 300);

        JLabel l1 = new JLabel("选择文件:");
        l1.setBounds(20, 20, 80, 30);
        container.add(l1);

        JButton selectButton = new JButton("...");
        selectButton.setBounds(100, 20, 100, 30);
        selectButton.addActionListener(this);
        container.add(selectButton);

        filePathField.setBounds(200, 20, 180, 30);
        container.add(filePathField);

        JButton beginButton = new JButton("开始执行");
        beginButton.setBounds(150, 120, 120, 30);
        this.add(beginButton);
        beginButton.addActionListener(this);
        container.add(beginButton);

        this.setTitle("漏洞检测");
        this.setResizable(false);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if ("开始执行".equals(cmd)) {
            String filePath = filePathField.getText().replace(" ", "").replace("　", "");

            if (StringUtils.isBlank(filePath)) {
                JOptionPane.showMessageDialog(this, "请选择文件");
                return;
            }
            try {
                new SqlMapDetectHandle().execute(filePath);
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                JOptionPane.showMessageDialog(this, "检测完成!");
            }

        } else if ("...".equals(cmd)) {
            JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            int r = j.showOpenDialog(null);

            if (r == JFileChooser.APPROVE_OPTION) {
                filePathField.setText(j.getSelectedFile().getAbsolutePath());
            }

        }

    }


}
