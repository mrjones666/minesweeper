package minesweeper;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StatusBar extends JPanel {

    private static StatusBar instance = null;
    private JLabel[][] playerInfo;
    private JButton bomb, giveUp;

    public static StatusBar instance() {
        if (instance == null) {
            instance = new StatusBar();
        }

        return instance;
    }

    private StatusBar() {
        super(new FlowLayout(FlowLayout.LEFT, 0, 0));
        this.init();
    }

    private void init() {
        this.setPreferredSize(new Dimension(650, 51));
        this.setBackground(new Color(27, 108, 150));
        this.playerInfo = new JLabel[2][3];

        ImageIcon iconLeft;
        ImageIcon iconScore;
        ImageIcon iconNev;
        ImageIcon iconRight;
        for (int i = 0; i < 2; i++) {
            iconLeft = new ImageIcon("./media/img/bar" + i + "left.png");
            iconScore = new ImageIcon("./media/img/bar" + i + "score.png");
            iconNev = new ImageIcon("./media/img/bar" + i + "bg.png");
            iconRight = new ImageIcon("./media/img/bar" + i + "right.png");

            playerInfo[i][0] = new JLabel(0 + "", iconScore, JLabel.LEFT);
            playerInfo[i][1] = new JLabel(iconNev, JLabel.CENTER);
            playerInfo[i][0].setHorizontalTextPosition(JLabel.CENTER);
            playerInfo[i][1].setHorizontalTextPosition(JLabel.CENTER);
            playerInfo[i][0].setVerticalTextPosition(JLabel.CENTER);
            playerInfo[i][1].setVerticalTextPosition(JLabel.CENTER);
            playerInfo[i][0].setForeground(Color.white);

            // bomba gomb
            this.bomb = new JButton();
            this.bomb.setIcon(new ImageIcon("./media/img/bomba" + i + ".png"));
            this.bomb.setRolloverIcon(new ImageIcon("./media/img/bomba" + i + "roll.png"));
            this.bomb.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
            this.bomb.setVerticalTextPosition(JButton.CENTER);
            this.bomb.setBackground(null);
            this.bomb.setHorizontalTextPosition(JButton.CENTER);

            // feladás gomb
            this.giveUp = new JButton();
            this.giveUp.setIcon(new ImageIcon("./media/img/feherzaszlo" + i + ".png"));
            this.giveUp.setRolloverIcon(new ImageIcon("./media/img/feherzaszlo" + i + "roll.png"));
            this.giveUp.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
            this.giveUp.setBackground(null);
            this.giveUp.setVerticalTextPosition(JButton.CENTER);
            this.giveUp.setHorizontalTextPosition(JButton.CENTER);

            this.add(new JLabel(iconLeft));
            this.add(playerInfo[i][0]);
            this.add(playerInfo[i][1]);
            this.add(new JLabel(new ImageIcon("./media/img/elvalaszto" + i + ".png")));
            this.add(this.bomb);
            this.add(new JLabel(new ImageIcon("./media/img/elvalaszto" + i + ".png")));
            this.add(this.giveUp);
            this.add(new JLabel(iconRight));

        }
    }

    public void addPlayer(Player player) {
        int index = 0;
        if (!(player instanceof HumanPlayer)) {
            index = 1;
            this.bomb.setVisible(false);
            this.giveUp.setVisible(false);
        }

        this.playerInfo[index][0].setText(player.getPoints() + "");
        String active = player.isActive() ? "(aktív)" : "(nem aktív)";
        this.playerInfo[index][1].setText(player.getName() + " " + active);
    }

    public void addPlayer(Player[] players) {
        for (Player player : players) {
            this.addPlayer(player);
            System.out.println(player);
        }
    }

    public void refreshPlayer(Player player) {
        int index = 0;
        if (!(player instanceof HumanPlayer)) {
            index = 1;
        }

        String active = player.isActive() ? "(aktív)" : "(nem aktív)";
        this.playerInfo[index][1].setText(player.getName() + " " + active);
        this.playerInfo[index][0].setText(player.getPoints() + "");
    }
}
