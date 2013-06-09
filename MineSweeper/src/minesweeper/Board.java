/*commitoltam a legfrissebb verziót
megírhatnád a ComputerPlayer.pickField-et
a kezelése annak, hogy mit ad vissza már megy, csak most mindig a balfelső sarkot választja h legyen vmi
a pickField-nél kap egy 2dimenziós tömböt, amiben benne van az összes mező, amikből ki kéne választani egy random elemet
lecsekkolni hogy isClickable() (azaz felnyitották-e már, ha igen, akkor nem tudja választani) és returnolni azt az elemet a tömbből
lehet azt is kérni, hogy hasMine(), azaz h van-e ott akna
ha ügyes vagy, megcsinálod, hogy amikor már nagyon elhúzott a játékos, akkor a gép elkezdjen csalni 
de csak akkor, ha vmi nehéz szintre van állítva a computerplayer
(van egy level változója, mondjuk legyen 1-5-ig)
thx
megcsináltam azt is h végetér a játék ha vki megszerzi a pontok 51%-át
úgyh lehet tesztelni is
holnap nem nyúlok hozzá me nagyon dolgoznom kell
szarul csináltam meg a Sound osztályt is, mert sztem mindig újra és újra beolvassa a fájlokat, ez nem jó!

02:53
Gutai György
5-ös szintnél már csalhat az elejétől, de azért legyen itt is esély nyerni
1-es szinten egyáltalán ne csaljon, de azt láthatja, hogy volt-e már kattintva az adott mező, azt a humán is látja
mondjuk 5-ös szinten is azért megszopathatja magát, de annyira nem, hogy rákattint egy mezőre, ami mellett 0 van és jön a floodfill
de azért átadhatja a stafétát a humánnak
majd belerakjuk hogy indításnál lehessen nevet, pályaméretet és nehézséget választani, de ez nagyon ráér a végén
keddig foglalkozzunk csak ezzel az egésszel, max szerdáig, nem akarok sok időt elbaszni erre
majd belebaszok vmi xml-es config fájlt is
ha klónoztad a repot githubról, vhova a My Documents/GitHubba fogja baszni, onnan csinálhatsz netbeansprojectet
azt fasza
na abbahagytam
legyen csak 1-3-ig a computerplayer szintjei
kevesebb meló
1 - buta
2 - normál
3 - unda presha
csak egyet kérek
holnap pls ne fappelj*/

package minesweeper;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Board extends JFrame /*implements ActionListener, MouseListener, 
 ItemListener*/ {

    private static Board instance = null;
    private BoardSize boardSize;
    public Field[][] fields;
    private JPanel container, fieldContainer, playerContainer;
    private Vector<JLabel> playerLabels, activeLabels, pointLabels;

    /**
     * Board is a singleton.
     *
     * @return Board
     */
    public static Board instance(BoardSize size) {
        if (!(instance instanceof Board) || instance == null) {
            instance = new Board(size);
        }

        return instance;
    }

    private Board(BoardSize size) {
        // create panels
        this.container = new JPanel();
        this.container.setBackground(new Color(27, 108, 150));
        this.setBoardSize(size);

        // fields
        this.fieldContainer = new JPanel(new GridLayout(this.boardSize.x, this.boardSize.y));
        this.fieldContainer.setPreferredSize(new Dimension(640, 640));
        this.fields = new Field[this.boardSize.x + 1][this.boardSize.y + 1];

        // players
        this.playerContainer = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        this.playerContainer.setPreferredSize(new Dimension(650, 51));
        this.playerContainer.setBackground(new Color(27, 108, 150));
        this.playerLabels = new Vector();
        this.activeLabels = new Vector();
        this.pointLabels = new Vector();

        // add panels
        this.container.add(this.playerContainer);
        this.container.add(this.fieldContainer);

        add(this.container);
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void addPlayer(String name, boolean active) {
        this.playerLabels.add(new JLabel(name));
        this.activeLabels.add(new JLabel(active ? "(aktív)" : "(nem aktív)"));
        this.pointLabels.add(new JLabel("0 pont"));

        this.playerContainer.add(this.playerLabels.lastElement());
        this.playerContainer.add(this.activeLabels.lastElement());
        this.playerContainer.add(this.pointLabels.lastElement());
    }

    public void refreshField(int x, int y, ImageIcon flag) {
        new Sound("flag").play();
        Field field = this.fields[x][y];
        field.setText("");
        field.setIcon(flag);
    }

    public void refreshField(int x, int y, int surroundingMines) {
        Field field = this.fields[x][y];
        field.setText(surroundingMines + "");
    }
    
    public void refreshPlayer(int index, Player player) {
        JLabel activeLabel = this.activeLabels.get(index);
        activeLabel.setText(player.isActive() ? "(aktív)" : "(nem aktív)");
        
        JLabel pointLabel = this.pointLabels.get(index);
        pointLabel.setText(player.getPoints() + " pont");
    }

    private void setBoardSize(int x, int y) {
        this.boardSize = new BoardSize(x, y);
    }

    private void setBoardSize(BoardSize size) {
        this.boardSize = size;
    }

    /**
     * Creates fields based on the size of the board, then adds them to the
     * container.
     *
     * @param boolean[][] generatedSpots
     */
    public void addFields(boolean[][] generatedSpots) {
        for (int i = 1; i <= this.boardSize.x; i++) {
            for (int j = 1; j <= this.boardSize.y; j++) {
                // create a Field
                if (!(this.fields[i][j] instanceof Field) || this.fields[i][j] == null) {
                    this.fields[i][j] = new Field();
                    this.fields[i][j].setPosition(i, j);
                }
                // add a mine
                if (generatedSpots[i][j]) {
                    this.fields[i][j].addMine();
                    this.fields[i][j].setText("x");
                }

                // set some gui options
                this.fields[i][j].setIcon(new ImageIcon("./media/img/akna.png"));
                this.fields[i][j].setBorder(BorderFactory.createLineBorder(new Color(59, 153, 203), 2));
                this.fields[i][j].setVerticalTextPosition(JButton.CENTER);
                this.fields[i][j].setHorizontalTextPosition(JButton.CENTER);
                this.fieldContainer.add(this.fields[i][j]);

            }
        }
    }
    
    public void resetFields() {
        for (int i = 1; i <= this.boardSize.x; i++) {
            for (int j = 1; j <= this.boardSize.y; j++) {
                this.fields[i][j].setClickable(true);
                this.fields[i][j].setMine(false);
                this.fields[i][j].setText("");
            }
        }
    }
}
