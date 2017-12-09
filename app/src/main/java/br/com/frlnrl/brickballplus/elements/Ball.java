package br.com.frlnrl.brickballplus.elements;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import java.util.UUID;

import br.com.frlnrl.brickballplus.engine.Tela;

/**
 * Created by Fabio on 20/10/2017.
 */

public class Ball {

    public static final int DOWN = 999;
    public static final int UP = 111;
    private final UUID id;

    private final Tela tela;
    private final float alturaDaTela;
    private final float larguraDaTela;
    private final float originalY;
    private final int displacement;
    private Balls balls;
    private final Paint corDaBola;
    private float RAIO = 16;
    private float xCos;
    private float ySin;
    private int angle;
    private float X;
    private float Y;
    private float Xdestino;
    private double speedOfX = 25;
    private double speedOfY = -25;
    private boolean emMovimento;
    private int qtdBounce;

    private int hit;

    private boolean garagem;
    float getX() {
        return X;
    }

    public int getHit() {
        return hit;
    }

    private final String TAG = "Ball:";

    Ball(Tela tela, Balls balls) {
        this.id = UUID.randomUUID();
        this.tela = tela;
        this.X = tela.getLargura() / 2;
        this.displacement = (int) (tela.getAltura() * 0.08361204);
        this.originalY = this.Y = (tela.getAlturaTabuleiro() + displacement) - 32;
        this.alturaDaTela = tela.getAlturaTabuleiro() + displacement;
        this.larguraDaTela = tela.getLargura();
        this.balls = balls;
        this.corDaBola = Cores.getCorBola();
    }

    Ball(Tela tela, Balls balls, float X){
       this(tela, balls);
       this.X = X;
    }

    void desenhaNo(Canvas canvas) {
        canvas.drawCircle(X, Y, RAIO, corDaBola); //x e y sao o centro do circulo.
//        canvas.drawRect(getRect(), corDaBola);
//        canvas.drawText(String.valueOf("Ball X: " + X), 50, 170, Cores.getCorTexto());
//        canvas.drawText(String.valueOf("Ball Y: " + Y), 50, 230, Cores.getCorTexto());
//        canvas.drawText(String.valueOf("angulo: " + angle), 50, 290, Cores.getCorTexto());
//        canvas.drawText(String.valueOf("Xcos: " + xCos), 50, 350, Cores.getCorTexto());
//        canvas.drawText(String.valueOf("Ysin: " + ySin), 50, 410, Cores.getCorTexto());
    }

    void comecaMovimento(int angle) {
        Log.d(TAG, "comecaMovimento: id=" + id);
        this.angle = angle;
        if (!emMovimento) {
            emMovimento = true;
            qtdBounce = 0;
            calculaDirecaoXY(angle);
        }
    }

    private double[] calculaDirecaoXY(int angle) {
        double radians = Math.toRadians(angle);
        xCos = (float) (Math.cos(radians) * speedOfX);
        ySin = (float) (Math.sin(radians) * speedOfY);
        return new double[]{xCos, ySin};
    }

    private void paraMovimento() {
        emMovimento = false;
        Y = originalY;
        speedOfX = Math.abs(speedOfX);
        speedOfY *= -1;
        hit = 0;
        balls.chegouNoChao(this);
    }

    void bounce() {

        if (emMovimento) {

            if (bateuNaParede()) {
                registraHit();
                mudaSinalX();
            } else if (chegouNoTopo()) {
                registraHit();
                mudaSinalY();
            }

            if (chegouNoChao()) {
                paraMovimento();
            }else {
                Y = Y + ySin;
                X = X + xCos;
                qtdBounce++;

                if (X - RAIO < 0) X = RAIO; //Se passou da esquerda da tela
                if (X + RAIO > larguraDaTela) X = larguraDaTela - RAIO; //Se passou da direita
                if (Y - RAIO < displacement) Y = RAIO + displacement; //Se passou do topo
            }
        }else if (garagem){
            trataRetornoAGaragem();
        }

    }

    private void trataRetornoAGaragem() {
//        Log.d(TAG, "trataRetornoAGaragem: id=" + id + " - XDestino=" + Xdestino + " - X=" + X);
        if (Xdestino > X){
            if (Xdestino - X < Math.abs(speedOfX)){
                X = Xdestino;
                garagem = false;
                speedOfX = Math.abs(speedOfX);
            }else {
                speedOfX = Math.abs(speedOfX);
                X = (float) (X + speedOfX);
            }
        }else if (Xdestino < X){
            if (Xdestino - X > speedOfX){
                X = Xdestino;
                garagem = false;
                speedOfX = Math.abs(speedOfX);
            }else {
                if (speedOfX > 0) {
                    speedOfX = speedOfX * -1;
                }
                X = (float) (X + speedOfX);
            }
        }else {
            garagem = false;
            Brick.totalHits = 0;
        }
//        Log.d(TAG, "trataRetornoAGaragem: id=" + id + " - XDestino=" + Xdestino + " - X=" + X);
    }

    void startGaragem() {
        garagem = true;
    }

    public void mudaSinalY() {
        speedOfY *= -1;
        calculaDirecaoXY(angle);
    }

    public void mudaSinalY(int direcao) {
        if (direcao == DOWN){
            if (speedOfY < 0){
                speedOfY *= -1;
            }
        }else if(direcao == UP){
            if (speedOfY > 0){
                speedOfY *= -1;
            }
        }
        calculaDirecaoXY(angle);
    }

    public void mudaSinalX() {
        speedOfX *= -1;
        calculaDirecaoXY(angle);
    }

    private boolean bateuNaParede() {
        if (X - RAIO <= 0) {
            return true;
        } else if (X + RAIO >= larguraDaTela) {
            return true;
        }
        return false;
    }

    private boolean chegouNoTopo() {
        return (Y - RAIO <= displacement);
    }

    /*
    * Primeiro verifica se a bolinha já bateu em alguma coisa, senao, como ela teria voltado pro chao?
    * Só retorna verdadeiro quando a bolinha esta em emMovimento E o centro da bolinha + RAIO é maior que a altura da tela (o chao)
    * */
    private boolean chegouNoChao() {
        if (hit > 0) {
            return emMovimento && Y + RAIO > alturaDaTela;
        }
        return false;
    }

    public void registraHit() {
        hit++;
    }

    void desenhaMiraNo(Canvas canvas, int angle, boolean startLine) {
        if ((!emMovimento) && (startLine)) {
            double[] xy = calculaDirecaoXY(angle);
            float xFinal = X + ((float) xy[0] * 50);
            float yFinal = Y + ((float) xy[1] * 50);
            canvas.drawLine(X, Y, xFinal, yFinal, corDaBola);
        }
    }

    boolean jaPartiu() {
        return qtdBounce > 4;
    }

    public int getQtdBounce() {
        return qtdBounce;
    }

    void defineLocalGaragem(float xBallLider) {
        Y = originalY;
        Xdestino = xBallLider;
        //Log.d(TAG, "defineLocalGaragem: Xdestino=" + Xdestino);
    }

    void speedUp() {
        speedOfX *= 3;
    }

    public Rect getRect() {
        return new Rect((int) (X - RAIO * 2), (int) (Y - RAIO * 2), (int) (X + RAIO * 2), (int) (Y + RAIO * 2));
    }

    public UUID getId() {
        return id;
    }

    public void alteraAngulo() {
        Log.d(TAG, "alteraAngulo: angle=" + angle);
        if (angle > 45 && angle < 70){
            angle += 100;
        }else if (angle >= 70 && angle < 90) {
            angle += 30;
        } else if (angle >= 90 && angle < 110){
            angle -= 30;
        }else if (angle >=110 && angle < 135){
            angle -= 100;
        }
        Log.d(TAG, "alteraAngulo: angle2=" + angle);
        Log.d(TAG, "alteraAngulo: xCos=" + xCos);
        Log.d(TAG, "alteraAngulo: ySin=" + ySin);
        calculaDirecaoXY(angle);
        Log.d(TAG, "alteraAngulo: xCos=" + xCos);
        Log.d(TAG, "alteraAngulo: ySin=" + ySin);

    }
}
