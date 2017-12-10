package br.com.frlnrl.brickballplus.elements;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.com.frlnrl.brickballplus.engine.Game;
import br.com.frlnrl.brickballplus.engine.Tela;

/**
 * Created by Fabio on 01/11/2017.
 */

public class Bricks {
    private static final String TAG = "Bricks";
    private final Tela tela;
    private final Context context;
    private Game game;
    private final Paint corTexto;
    private final int displacement;
    private double espacoTotalEmBranco;
    private double espacoTotalPreenchido;
    private int espacoEmBranco;
    private int espacoPreenchido;
    public static final int[] posicaoX = new int[14];
    public static final int[] posicaoY = new int[14];
    private int linhaAtual = -1;
    private int numeroDeLinhas = 0;
    private int numeroDeColunas = 7;
    private Random random;
    public List<List<Brick>> linhasDeBricks = new ArrayList<>();
    private int fase;

    public void setBalls(Balls balls) {
        this.balls = balls;
    }

    private Balls balls;

    public Bricks(Tela tela, Context context, Game game) {
        this.tela = tela;
        this.context = context;
        this.game = game;
        this.corTexto = Cores.getCorTexto();
        this.displacement = (int) (tela.getAltura() * 0.08361204);

        calculaPosicoesDeXeY();

        this.fase = 1;

        adicionaLinhaVazia();

        random = new Random();

        populaLinhas();
    }


    private void calculaPosicoesDeXeY() {
        this.espacoTotalEmBranco = tela.getLargura() * 0.15;
//        Log.d(TAG, "Bricks: espacoTotalEmBranco=" + espacoTotalEmBranco);
        this.espacoEmBranco = (int) (espacoTotalEmBranco / 8);
//        Log.d(TAG, "Bricks: espacoEmBranco=" + espacoEmBranco);
        espacoTotalPreenchido = tela.getLargura() - espacoTotalEmBranco;
//        Log.d(TAG, "Bricks: espacoTotalPreenchido=" + espacoTotalPreenchido);
        espacoPreenchido = (int) (espacoTotalPreenchido / 7);
//        Log.d(TAG, "Bricks: espacoPreenchido=" + espacoPreenchido);

        int base = 0;
        for (int x = 0; x < 14; x++){
            base = base + espacoEmBranco;
            posicaoX[x] = base;
//            Log.d(TAG, "Bricks: posicaoX=" + base);
            x++;
            base = base + espacoPreenchido;
//            Log.d(TAG, "Bricks: posicaoX=" + base);
            posicaoX[x] = base;
        }
        base = displacement + espacoPreenchido + espacoEmBranco;
        for (int y = 0; y < 14; y++){
            base = base + espacoEmBranco;
            posicaoY[y] = base;
//            Log.d(TAG, "Bricks: posicaoX=" + base);
            y++;
            base = base + espacoPreenchido;
//            Log.d(TAG, "Bricks: posicaoX=" + base);
            posicaoY[y] = base;
        }
    }

    private void populaLinhas() {

        //define o maximo de bricksInvisiveis a serem criados (0 a 6)
        int max = random.nextInt(numeroDeColunas);
        if (max < 2){ max = 2; }
        //todos bricks começam invisiveis
        boolean[] bricksInvisiveis = { true, true, true, true, true, true, true };
        //define quais sao visíveis
        for (int x = 0; x < max; x++){
            bricksInvisiveis[random.nextInt(numeroDeColunas)] = false;
        }
//        for (int x = 0; x < 7; x++) {
//            Log.d(TAG, "populaLinhas: bricksInvisiveis[" + x + "]" + bricksInvisiveis[x]);
//        }
        //'count' guarda a qtd de invisiveis para criar a bolinha extra
        //'pi[]' guarda as posicoes invisiveis
        int count = 0;
        int pi[] = new int[7];
        for (int x = 0; x < 7; x++){
            if (bricksInvisiveis[x]){
                pi[count] = x;
                count++;
            }
        }
//        for (int x = 0; x < count; x++) {
//            Log.d(TAG, "populaLinhas: pi[" + x + "]" + pi[x]);
//        }
        //Log.d(TAG, "populaLinhas: count=" + count);
        int bolinha = random.nextInt(count);
        //Log.d(TAG, "populaLinhas: bolinha=" + bolinha);
        for (int i = 0; i < 7; i++) {
            if (i == pi[bolinha]){
                linhasDeBricks.get(0).add(new Brick(i, 0, bricksInvisiveis[i], fase, true));
            }else {
                linhasDeBricks.get(0).add(new Brick(i, 0, bricksInvisiveis[i], fase, false));
            }
        }
    }

    private void transfereLinhas() {

        for (int i = linhaAtual; i > 0; i--) {
            int linhaAnterior = i - 1;
            if (!(linhaAnterior < 0)) {
                List<Brick> colunaDeBricks = linhasDeBricks.get(i);
                for (int j = 0; j < 7; j++) {
                    Brick b = linhasDeBricks.get(linhaAnterior).get(j);
//                    Log.d(TAG, "transfereLinha=" + linhaAnterior + ", para=" + i + ", b[" + j + "].isInvisible=" + b.isInvisible());
                    colunaDeBricks.add(new Brick(j, i, b.isInvisible(), b.getHitPoints(), b.isBolinha()));
                }
                linhasDeBricks.get(linhaAnterior).clear();
            }
        }
        testForGameOver();

    }

    private void testForGameOver() {
        if (linhaAtual >= 7){
            for (int j = 0; j < 7; j++) {
                Brick b = linhasDeBricks.get(7).get(j);
                Log.d(TAG, "testForGameOver: b[" + j + "].invisible=" + b.isInvisible());
                if (!b.isInvisible()){ //se todos os bricks nao estiverem invisiveis, gameover...
                    gameOver();
                    //break;
                }
            }

        }
    }

    private void gameOver() {
        Log.d(TAG, "gameOver!");
        game.gameOver();
    }

    private void adicionaLinhaVazia() {
        linhaAtual++;
        numeroDeLinhas++;
        linhasDeBricks.add(new ArrayList<Brick>(numeroDeColunas));
        Log.d(TAG, "adicionaLinhaVazia: linhaAtual=" + linhaAtual);
    }

    public void desenhaNoCanvas(Canvas canvas) {
        canvas.drawText(String.valueOf(fase), 50, 100, corTexto);
        canvas.drawText(String.valueOf(Brick.totalHits), 500, 100, corTexto);
        for (List<Brick> colunaDeBricks : linhasDeBricks) {
            for (Brick b : colunaDeBricks) {
                b.desenhaNoCanvas(canvas);
            }
        }
    }

    void trataNovaFase() {
        fase++;
        adicionaLinhaVazia();
        transfereLinhas();
        populaLinhas();
        balls.adicionaNovasBolasDefinitivo();
    }
}
