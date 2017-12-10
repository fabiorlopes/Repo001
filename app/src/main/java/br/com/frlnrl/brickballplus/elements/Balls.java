package br.com.frlnrl.brickballplus.elements;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

import br.com.frlnrl.brickballplus.engine.Controles;
import br.com.frlnrl.brickballplus.engine.Tela;

/**
 * Created by Fabio on 25/10/2017.
 */

public class Balls {
    private static final String TAG = "Balls";
    private final Tela tela;
    private final float yLabelQtdBolinhas;
    private float xLabelQtdBolinhas;
    private Bricks bricks;

    private int qtdDeBolasNoChao = 0;

    private int numeroDeBolas = 1;
    public List<Ball> balls = new ArrayList<>();
    private float xBallLider;
    private float yBallLider;
    private int shouldAddBall;
    private final Controles controles;
    private Paint corDaBolinha;

    public Balls(Tela tela, Bricks bricks, Controles controles) {
        this.tela = tela;
        this.bricks = bricks;
        this.controles = controles;
        for (int i = 0; i < numeroDeBolas; i++) {
            balls.add(new Ball(tela, this));
        }
        yLabelQtdBolinhas = balls.get(0).getOriginalY() - 32;
        xLabelQtdBolinhas = balls.get(0).getX();
        corDaBolinha = Cores.getCorBola();
    }

    public void desenhaNo(Canvas canvas) {
        canvas.drawText("x" + String.valueOf(balls.size()), xLabelQtdBolinhas, yLabelQtdBolinhas, corDaBolinha);
        for (Ball ball : balls) {
            ball.desenhaNo(canvas);
        }
    }

    public void startBounce(int angle) {
        for (Ball ball : balls) {
            ball.comecaMovimento(angle);
        }
    }

    public void comecaComXBolas(int qtd) {
        numeroDeBolas = qtd;
    }

    public void adicionaNovaBolaNaFila() {
        shouldAddBall++;
        //balls.add(new Ball(tela, this));
    }

    public void speedUp(){
        for (Ball ball : balls) {
            ball.speedUp();
        }
    }

    public void bounce() {

        balls.get(0).bounce();
        for (int i = 1; i < numeroDeBolas; i++) {
            if (balls.get(i - 1).jaPartiu()) {
                balls.get(i).bounce();
            }
        }
    }

    public void desenhaMiraNo(Canvas canvas, int angle, boolean startLine) {
        for (Ball ball : balls) {
            ball.desenhaMiraNo(canvas, angle, startLine);
        }
    }

    void chegouNoChao(Ball ball){
        qtdDeBolasNoChao++;
        Log.d(TAG, "chegouNoChao: qtd=" + qtdDeBolasNoChao);
        if (qtdDeBolasNoChao == 1){
            xBallLider=  ball.getX();
            xLabelQtdBolinhas = xBallLider;
        }
        if(qtdDeBolasNoChao >= 2){
            ball.defineLocalGaragem(xBallLider);
            ball.startGaragem();
        }
        if (qtdDeBolasNoChao == numeroDeBolas){
            bricks.trataNovaFase();
            controles.liberaDisparo();
            qtdDeBolasNoChao = 0;
        }
    }

    public void adicionaNovasBolasDefinitivo() {
        while (shouldAddBall > 0){
            balls.add(new Ball(tela, this, xBallLider));
            numeroDeBolas++;
            shouldAddBall--;
        }
    }
}
