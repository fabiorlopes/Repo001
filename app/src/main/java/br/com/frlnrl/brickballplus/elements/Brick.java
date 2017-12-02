package br.com.frlnrl.brickballplus.elements;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import java.util.UUID;

/**
 * Created by Fabio on 22/10/2017.
 */

public class Brick {

    private final UUID id;

    private final Paint corTexto;
    private final Paint corRectBot;
    private final Paint corRectLeft;
    private final Paint corRectRight;
    private final Paint corRectTop;

    private final Paint corBolinha;
    private final Paint corAnimacaoBolinha;

    private static final String TAG = "Brick";

    private boolean bolinha;

    private Paint cor;
    private int x1;
    private int x2;
    private int y1;
    private int y2;
    private boolean invisible;
    private int hitPoints;
    private Rect rect;
    private Rect rtop;
    private Rect rTopCorner1;
    private Rect rTopCorner2;
    private Rect rbot;
    private Rect rBotCorner1;
    private Rect rBotCorner2;
    private Rect rleft;
    private Rect rright;
    private int countOfAnimation;
    private Rect smallRect;

    Brick(int coluna, int linha, boolean invisible, int hitpoints, boolean bolinha) {
        this.bolinha = bolinha;
        this.id = UUID.randomUUID();
        this.cor = Cores.getCorRect();
        this.countOfAnimation = 0;
        corRectTop = Cores.getCorRectTop();
        corRectBot = Cores.getCorRectBot();
        corRectLeft = Cores.getCorRectLeft();
        corRectRight = Cores.getCorRectRight();
        corBolinha = Cores.getCorBolinha();
        corAnimacaoBolinha = Cores.getCorAnimacaoBolinha();
        this.invisible = invisible;
        this.hitPoints = hitpoints;
        this.corTexto = Cores.getCorTextoRect();

        if (linha < 7) {
            this.x1 = Bricks.posicaoX[coluna * 2];
            this.x2 = Bricks.posicaoX[coluna * 2 + 1];

            this.y1 = Bricks.posicaoY[linha * 2];
            this.y2 = Bricks.posicaoY[linha * 2 + 1];

            rect = new Rect(x1, y1, x2, y2);

            rtop = new Rect(x1 + 5, y1, x2 - 5, y1 + 5);
            rTopCorner1 = new Rect(x1, y1, x1 + 5, y1 + 5);
            rTopCorner2 = new Rect(x2 - 5, y1, x2, y1 + 5);

            rbot = new Rect(x1 + 5, y2 - 5, x2 - 5, y2);
            rBotCorner1 = new Rect(x1, y2, x1 + 5, y2 - 5);
            rBotCorner2 = new Rect(x2, y2, x2 - 5, y2 - 5);


            rleft = new Rect(x1, y1 + 5, x1 + 5, y2 - 5);
            rright = new Rect(x2 - 5, y1 + 5, x2, y2 - 5);
        }

    }

    void desenhaNoCanvas(Canvas canvas) {

        if (!invisible) {
            canvas.drawRect(rect, cor);
            canvas.drawRect(rTopCorner1, corRectTop);
            canvas.drawRect(rTopCorner2, corRectTop);
            canvas.drawRect(rBotCorner1, corRectBot);
            canvas.drawRect(rBotCorner2, corRectBot);
            canvas.drawRect(rtop, corRectRight);
            canvas.drawRect(rleft, corRectLeft);
            canvas.drawRect(rbot, corRectRight);
            canvas.drawRect(rright, corRectLeft);
            String s = String.valueOf(hitPoints);
            canvas.drawText(s, x2 - 70, y2 - 35, corTexto);
        } else if (bolinha) {
            if (countOfAnimation > 30 && countOfAnimation <= 40) {
                canvas.drawCircle((((x2 - x1) / 2) + x1), (((y2 - y1) / 2) + y1), 28, corAnimacaoBolinha);
                if (countOfAnimation == 40) countOfAnimation = 0;
            } else if (countOfAnimation > 20 && countOfAnimation <= 30) {
                canvas.drawCircle((((x2 - x1) / 2) + x1), (((y2 - y1) / 2) + y1), 32, corAnimacaoBolinha);
            } else if (countOfAnimation > 10 && countOfAnimation <= 20) {
                canvas.drawCircle((((x2 - x1) / 2) + x1), (((y2 - y1) / 2) + y1), 28, corAnimacaoBolinha);
            } else {
                canvas.drawCircle((((x2 - x1) / 2) + x1), (((y2 - y1) / 2) + y1), 24, corAnimacaoBolinha);
            }
            countOfAnimation++;
            canvas.drawCircle((((x2 - x1) / 2) + x1), (((y2 - y1) / 2) + y1), 16, corBolinha);
            //canvas.drawRect(getSmallRect(), corRectTop);
        }
    }

    public void destroy() {
        cor = Cores.getCorBola();
    }

    public float getX1() {
        return x1;
    }

    public int getX2() {
        return x2;
    }

    public void hit() {

        hitPoints--;
        tryToKill();
    }

    public void tryToKill() {
        if (hitPoints == 0) {
            this.invisible = true;
        }
    }

    public boolean isInvisible() {
        return invisible;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    public int getY1() {
        return y1;
    }

    public int getY2() {
        return y2;
    }

    public Rect getRect() {
        return rect;
    }

    public Rect getRtop() {
        return rtop;
    }

    public Rect getrTopCorner1() {
        return rTopCorner1;
    }

    public Rect getrTopCorner2() {
        return rTopCorner2;
    }

    public Rect getRbot() {
        return rbot;
    }

    public Rect getrBotCorner1() {
        return rBotCorner1;
    }

    public Rect getrBotCorner2() {
        return rBotCorner2;
    }

    public Rect getRleft() {
        return rleft;
    }

    public Rect getRright() {
        return rright;
    }

    public UUID getId() {
        return id;
    }

    public boolean isBolinha() {
        return bolinha;
    }

    public void setBolinha(boolean bolinha) {
        this.bolinha = bolinha;
    }

    public Rect getSmallRect() {
        smallRect = new Rect(
                ((((x2 - x1) / 2) + x1) - 32),
                ((((y2 - y1) / 2) + y1) - 32),
                ((((x2 - x1) / 2) + x1) + 32),
                ((((y2 - y1) / 2) + y1) + 32)
        );
        return smallRect;
    }
}
