package app_TETRIS;

public class GameArea {
    private int fieldHight = 21;
    private int fieldWidth = 12;
    private int grandHight = 30;
    private int grandWidth = 20;
    private int[][] field;
    private int[][] bufferField;
    private int score = 0;
    private int linecount = 0;
    private String name;

    public GameArea() {
        this.bufferField = new int[grandHight][grandWidth];
        this.field = new int[grandHight][grandWidth];
        initBufferField();
        initField();
    }

    public int getScore() {
        return this.score;
    }

    public int getcount() {
        return this.linecount;
    }

    public int resetCount() {
        this.linecount = 0;
        return this.linecount;
    }

    public int getFieldHight() {
        return this.fieldHight;
    }

    public int getFieldWidth() {
        return this.fieldWidth;
    }

    public int getGrandHight() {
        return this.grandHight;
    }

    public int getGrandWidth() {
        return this.grandWidth;
    }

    public int[][] getBufferField() {
        return this.bufferField;
    }

    public int[][] getField() {
        return this.field;
    }

    public GameArea(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void initField() {
        for (int y = 0; y < getFieldHight(); y++) {
            for (int x = 0; x < getFieldWidth(); x++) {
                field[y][x] = bufferField[y][x];
            }
        }
    }

    public void initBufferField() {
        for (int y = 0; y < getFieldHight(); y++) {
            for (int x = 0; x < getFieldWidth(); x++) {
                bufferField[y][x] = 0;
            }
        }
        for (int y = 0; y < getFieldHight(); y++) {
            bufferField[y][0] = bufferField[y][getFieldWidth() - 1] = 1;
        }
        for (int x = 0; x < getFieldWidth(); x++) {
            bufferField[getFieldHight() - 1][x] = 1;
        }
    }

    public void drawField() {
        for (int y = 0; y < getFieldHight(); y++) {
            for (int x = 0; x < getFieldWidth(); x++) {
                System.out.printf("%s", (field[y][x] == 1 ? "回" : "・"));
            }
            System.out.println();
        }
        System.out.println("消したライン数：" + linecount);
        System.out.print("名前:" + name + "   ");
        System.out.println("スコア：" + score);
    }

    public void drawNextMino(Mino nextMino) {
        int[][][] m = nextMino.getMino();
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                System.out.printf("%s", (m[0][y][x] == 1 ? "回" : "・"));
            }
            System.out.println();
        }
    }

    public void drawFieldAndMino(Mino mino, Mino nextMino) {
        if (isCollison(mino)) {
            bufferFieldAddMino(mino);
            initField();
        }
        else {
            initField();
            fieldAddMino(mino);
        }
        System.out.println("NextMino"); 
        drawNextMino(nextMino);
        System.out.println();
        drawField();
    }

    public void fieldAddMino(Mino mino) {
        for (int y = 0; y < mino.getMinoSize(); y++) {
            for (int x = 0; x < mino.getMinoSize(); x++) {
                this.field[mino.getMinoY() + y][mino.getMinoX() + x] |= mino.getMino()[mino.getMinoAngle()][y][x];
            }
        }
    }

    public void bufferFieldAddMino(Mino mino) {
        for (int y = 0; y < mino.getMinoSize(); y++) {
            for (int x = 0; x < mino.getMinoSize(); x++) {
                this.bufferField[mino.getMinoY() + y][mino.getMinoX() + x] |= mino.getMino()[mino.getMinoAngle()][y][x];
            }
        }
    }

    public boolean isCollison(Mino mino) {
        for (int r = 0; r < mino.getMinoSize(); r++) {
            for (int c = 0; c < mino.getMinoSize(); c++) {
                if (this.bufferField[mino.getMinoY() + r + 1][mino.getMinoX() + c] == 1 && mino.getMino()[mino.getMinoAngle()][r][c] == 1) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isCollison(Mino mino, int _x, int _y, int _angle) {
        for (int r = 0; r < mino.getMinoSize(); r++) {
            for (int c = 0; c < mino.getMinoSize(); c++) {
                if (getBufferField()[_y + r][_x + c] == 1 && mino.getMino()[_angle][r][c] == 1) {
                    return true;
                }
            }
        }
        return false;
    }

    public void eraseLine() {
        boolean isFill = true;
        resetCount();
        for (int y = getFieldHight() - 2; y > 0; y--) {
            for (int x = 1; x < getFieldWidth() - 1; x++) {
                if (bufferField[y][x] == 0) {
                    isFill = false;
                }
            }
            if (isFill) {
                for (int _y = y - 1; _y > 0; _y--) {
                    for (int x = 0; x < getFieldWidth(); x++) {
                        bufferField[_y + 1][x] = bufferField[_y][x];
                    }
                }
                this.linecount++;
            }
            isFill = true;
        }
        addScore();
    }

    public void addScore() {
        int count = getcount();
        int intMax = 21_4748_3647;
        switch (count) {
            case 1:
                if (score - 40 > intMax - 40) {
                    score = intMax;
                } else {
                    score += 40;
                }
                break;
            case 2:
                if (score - 100 > intMax - 100) {
                    score = intMax;
                } else {
                    score += 100;
                }
                break;
            case 3:
                if (score - 300 > intMax - 300) {
                    score = intMax;
                } else {
                    score += 300;
                }
                break;
            case 4:
                if (score - 1200 > intMax - 1200) {
                    score = intMax;
                } else {
                    score += 1200;
                }
                break;
            default:
                score += 0;
                break;
        }
    }

    public void moveDown(Mino mino) {
        mino.addMinoY();
    }

    public void moveRight(Mino mino) {
        mino.addMinoX();
    }

    public void moveLeft(Mino mino) {
        mino.decMinoX();
    }

    public void rotation(Mino mino) {
        mino.setMinoAngle((mino.getMinoAngle() + 1) % mino.getMinoAngleSize());
    }

}
