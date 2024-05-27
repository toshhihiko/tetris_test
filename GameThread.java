package app_TETRIS;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GameThread extends Thread {
    private Mino mino;
    private Mino nextMino;
    private GameArea ga;

    public GameThread() {
        this.mino = new Mino();
        this.nextMino = new Mino();
        this.ga = new GameArea();
    }
    public Mino getMino(){
        return this.mino;
    }
    public Mino getNextMino(){
        return this.nextMino;
    }
    public GameArea getGameArea(){
        return this.ga;
    }

    public void run() {
        while (true) {
            ga.moveDown(mino);

            if (ga.isCollison(mino)) {
                if(mino.getMinoY() <= 1){ 
                    System.out.println("GameOver");
                    System.out.println(ga.getName() + "  あなたのスコア:" + ga.getScore());
                    System.exit(0);
                }
                this.mino = nextMino;
                this.nextMino = new Mino();
            }
            ga.drawFieldAndMino(mino, nextMino);
            ga.eraseLine();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(GameThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
