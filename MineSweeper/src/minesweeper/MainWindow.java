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
 holnap pls ne fappelj

 az a lényeg hogy az előtt értékelte ki h játék vége van-e, mielőtt a newTurn-ben elosztotta volna a pontokat
 de csak akkor, amikor a gép rakott, mert olyankor máshonnan hívja a a handlefieldclicket
 csináld a felső playerbart is pls
 régi kódból át tudod hozni
 szerdáig befejezzük

 lehetne a Game-nek egy Turn objektuma amúgy
 ami tárolja h hányadik turn van, stb
 ezt majd átgondolom

 amúgy kipróbálhatnál vmit
 hogy ha a 2. player típusát megváltoztatod humanplayerre ugyanúgy
 akkor lehet-e úgy játszani 2-en egy gépen mint a régiben
 mert akkor ez is lehetne egy option a startnál
 1 vs 1, 1 vs CPU
 mert elvileg csak akkor rak magától a humán pléjer után, ha a 2. player típusa ComputerPlayer

 van egy olyna h player instanceof ComputerPlayer
 tehát ha humán a másik is, akkor nincs semmiféle automatikus rakás

 csalási feltétel
 ha a humánpléjer mondjuk 3 pontra van a győzelemtől, akkor gyorsan találjon el annyit a gép, 
 hogy feljöjjön -1 pontra, mindezt 3-as difficultyn

 amúgy nagyon egyszerű ha a gép kezd akkor meg kell hívni a newturn-t
 this.getActivaplayer() instanceof...

 azt, ahol a newturn-ben megnézi hogy gépi játékos jön-e
 azt ami az if-en belül van tedd át egy külön void-ba
 és ezt hívd meg ott, meg akkor is ha az elején gép kezd

 a newturn-t ne hívd mert akkor csak át fogja adni a stafétát a humánnak
 és ugyanúgy marad minden csak a humán kezd...
 szal felesleges sorsolni

 játék végén elvileg nem cserélődik az active player tehát a győztes kezd
 bár sztem szopásabb kezdeni, úgyh a vesztes kezdjen
 végigloopolsz a pléjereken és player.toggleActive()

 Amúgy újabb bővítési lehetőség
 3 plejer nagyobb pálya
 2 cpu
 Amúgy logikusan kene raknia a cpunak
 Ha felnyitottam egyet amire 4 van írva akkor arrafelé rakjon

 */
package minesweeper;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainWindow extends JFrame {

    private static MainWindow instance = null;
    private JPanel container;

    public static MainWindow instance() {
        if (!(instance instanceof MainWindow) || instance == null) {
            instance = new MainWindow();
        }

        return instance;
    }

    private MainWindow() {
        // create panels
        this.container = new JPanel();
        this.container.setBackground(new Color(27, 108, 150));

        // add panels
        this.container.add(StatusBar.instance());
        this.container.add(MineField.instance());
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.add(this.container);
    }

}
