
package net.specialattack.settling.launcher;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.UIManager;

import org.lwjgl.LWJGLUtil;

public class LauncherFrame extends Frame {

    private static final long serialVersionUID = 5344282572136619990L;
    public static LauncherFrame instance;
    private LauncherStub launcher;
    private Applet applet;

    public LauncherFrame(LauncherStub launcher) {
        super("Settling Launcher Window");

        this.launcher = launcher;
        LauncherFrame.instance = this;

        this.setBackground(Color.black);

        JPanel p = new JPanel();
        p.setLayout(new BorderLayout());
        JButton button = new JButton("Start game");

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                Applet applet = LauncherFrame.this.launcher.launchGame();

                LauncherFrame.this.applet = LauncherFrame.this.launcher;
                LauncherFrame.this.launcher.setApplet(applet);
                LauncherFrame.this.initiateApplet();
                LauncherFrame.this.setTitle("Settling");
            }
        });

        p.add(button);

        p.setPreferredSize(new Dimension(854, 480));

        this.setLayout(new BorderLayout());
        this.add(p, "Center");

        this.pack();
        this.setLocationRelativeTo(null);

        if (LWJGLUtil.getPlatform() == LWJGLUtil.PLATFORM_WINDOWS) {
            this.addWindowFocusListener(new WindowAdapter() {

                @Override
                public void windowGainedFocus(WindowEvent arg0) {
                    if (LauncherFrame.this.applet != null) {
                        LauncherFrame.this.applet.requestFocusInWindow();
                    }
                }
            });
        }

        this.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent arg0) {
                new Thread() {

                    @Override
                    public void run() {
                        try {
                            Thread.sleep(30000L);
                        }
                        catch (InterruptedException e) {}
                        System.out.println("FORCING EXIT!");
                        System.exit(0);
                    }
                }.start();

                System.out.println("Closing window...");

                if (LauncherFrame.this.applet != null) {
                    LauncherFrame.this.applet.stop();
                    System.exit(0);
                }
                else {
                    System.exit(0);
                }
            }
        });
    }

    private void initiateApplet() {
        Component component = this.getComponent(0);

        this.removeAll();

        this.applet.setPreferredSize(new Dimension(component.getWidth(), component.getHeight()));

        this.setLayout(new BorderLayout());
        this.add(this.applet, "Center");

        this.pack();

        this.applet.init();
        this.applet.start();
    }

    public static void main(String[] args, LauncherStub launcher) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception localException) {}

        LauncherFrame frame = new LauncherFrame(launcher);
        frame.setVisible(true);
    }

}
