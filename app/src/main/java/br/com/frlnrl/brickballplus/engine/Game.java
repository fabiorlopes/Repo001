package br.com.frlnrl.brickballplus.engine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import br.com.frlnrl.brickballplus.R;
import br.com.frlnrl.brickballplus.elements.Balls;
import br.com.frlnrl.brickballplus.elements.Bricks;
import br.com.frlnrl.brickballplus.elements.Cores;

/**
 * Created by Fabio on 20/10/2017.
 */

public class Game extends SurfaceView implements Runnable {


    private final Context context;
    private boolean isRunning;
    SurfaceHolder surfaceHolder;
    private Bitmap background;
    private Bitmap backgroundFull;
    private int angle = 0;

    private final String TAG = "Game:";

    private boolean startLine = false;
    private Balls balls;
    private Bricks bricks;
    private Canvas canvas;
    private CollisionControl cc;
    private Screen screen;
    private int topBack;
    private boolean gameOver;
    private Paint gameOverColor;
    private Controles controles;

    public Game(Context context) {
        super(context);

        this.context = context;

        surfaceHolder = getHolder();

        controles = new Controles(context);
        controles.setGame(this);
        setOnTouchListener(controles);

        inicializaElementos();
    }

    private void inicializaElementos() {
        this.screen = new Screen(context);
        this.bricks = new Bricks(screen, context, this, controles);
        this.balls = new Balls(screen, bricks, controles);
        this.bricks.setBalls(this.balls);
        this.cc = new CollisionControl(balls, bricks);
        this.background = Bitmap.createScaledBitmap(decodeBackground(), screen.getLargura(), screen.getAlturaTabuleiro(), false);
        this.backgroundFull = Bitmap.createScaledBitmap(decodeBackgroundGrey(), screen.getLargura(), screen.getAltura(), false);
        this.topBack = (int) (screen.getAltura() * 0.08361204);
        this.gameOverColor = Cores.getCorGameOver();
    }

    private Bitmap decodeBackground() {
        return BitmapFactory.decodeResource(getResources(), R.drawable.background);
    }

    private Bitmap decodeBackgroundGrey() {
        return BitmapFactory.decodeResource(getResources(), R.drawable.backgroundgray);
    }


    @Override
    public void run() {

        while (isRunning) {
            if (!surfaceHolder.getSurface().isValid()) continue;

            canvas = surfaceHolder.lockCanvas();

            //background full
            canvas.drawBitmap(backgroundFull, 0, 0, null);
            //background tabuleiro
            canvas.drawBitmap(background, 0, topBack, null);

            //bricks
            bricks.desenhaNoCanvas(canvas);


            //bolas
            balls.bounce();
            if (gameOver) {
                canvas.drawBitmap(backgroundFull, 0, 0, null);
                canvas.drawText("GameOver", screen.getLargura() / 2, screen.getAltura() / 2, gameOverColor);
                pause();
                continue;
            }
            balls.desenhaNo(canvas);
            balls.desenhaMiraNo(canvas, angle, startLine);

            //Controle de colisao:
            cc.controlCollision();

            //coloca o canvas na tela:
            surfaceHolder.unlockCanvasAndPost(canvas);
        }

    }

    public void continuar() {
        isRunning = true;
    }

    public void pause() {
        isRunning = false;
    }

    void startBallsBounce() {
        balls.startBounce(angle);
    }

    public void setStartLine(boolean startLine) {
        this.startLine = startLine;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public void gameOver() {
        Log.d(TAG, "gameOver!!");
        gameOver = true;
    }
}
