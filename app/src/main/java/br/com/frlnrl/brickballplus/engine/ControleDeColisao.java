package br.com.frlnrl.brickballplus.engine;

import android.graphics.Rect;
import android.util.Log;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import br.com.frlnrl.brickballplus.elements.Ball;
import br.com.frlnrl.brickballplus.elements.BallInfo;
import br.com.frlnrl.brickballplus.elements.Balls;
import br.com.frlnrl.brickballplus.elements.Brick;
import br.com.frlnrl.brickballplus.elements.BrickInfo;
import br.com.frlnrl.brickballplus.elements.Bricks;

import static java.lang.Math.abs;

/**
 * Created by Fabio on 22/10/2017.
 */

class ControleDeColisao {
    //Colecao de bolinhas
    private final Balls balls;
    //Colecao de bricks
    private final Bricks bricks;
    private final String TAG = "CC";

    //Colisoes jah detectadas para evitar colisao duplicada
    private ArrayList<AbstractMap.SimpleEntry<BallInfo, BrickInfo>> paresDeColisao = new ArrayList<>();

    ControleDeColisao(Balls balls, Bricks bricks) {
        this.balls = balls;
        this.bricks = bricks;
    }


    void controlaColisao() {
        //Para cada bolinha
        for (Ball ball : balls.balls) {
            //Para cada Linha
            for (List<Brick> colunaDeBricks : bricks.linhasDeBricks) {
                ListIterator<Brick> iterator = colunaDeBricks.listIterator();
                //Para cada coluna
                while (iterator.hasNext()) {
                    Brick brick = iterator.next();
                    //Se o brick estiver visível
                    if (!brick.isInvisible()) {
                        //Verifica se a bolinha bateu no brick
                        verificaInterseccao(ball, iterator, brick);
                    }else if (brick.isBolinha()){
                        verificaNovaBolinha(ball, iterator, brick);
                    }
                }
            }
        }
    }

    private void verificaNovaBolinha(Ball ball, ListIterator<Brick> iterator, Brick brick) {
        //Pede para bolinha o seu retangulo de colisao
        Rect ballRect = ball.getRect();
        //Pede para o brick seu retangulo menor (representa a bolinha extra)
        Rect brickSmall = brick.getSmallRect();
        if (ballRect.intersects(brickSmall.left, brickSmall.top, brickSmall.right, brickSmall.bottom)){
            Log.d(TAG, "verificaNovaBolinha: intersects...");
            brick.setHitPoints(0);
            brick.setBolinha(false);
            balls.adicionaNovaBolaNaFila();
            iterator.set(brick);
        }

    }

    private void verificaInterseccao(Ball ball, ListIterator<Brick> iterator, Brick brick) {
        //Pede para bolinha o seu retangulo de colisao
        Rect ballRect = ball.getRect();

        if (brick.getrBotCorner1().intersects(ballRect.left, ballRect.top, ballRect.right, ballRect.bottom)) {
            if (!jaExisteColisao(ball, brick)){
                Log.d(TAG, "bot Corner 1 hit");
                ball.registraHit();
                ball.mudaSinalY(Ball.DOWN);
                ball.mudaSinalX();
                ball.alteraAngulo();
                brick.hit();
                iterator.set(brick);
                paresDeColisao.add(new AbstractMap.SimpleEntry<>(new BallInfo(ball.getQtdBounce(), ball.getHit(), ball.getId()), new BrickInfo(brick.getId())));
            }
        } else if (brick.getrBotCorner2().intersects(ballRect.left, ballRect.top, ballRect.right, ballRect.bottom)) {
            if (!jaExisteColisao(ball, brick)) {
                Log.d(TAG, "bot Corner 2 hit");
                ball.registraHit();
                ball.mudaSinalY(Ball.DOWN);
                ball.mudaSinalX();
                ball.alteraAngulo();
                brick.hit();
                iterator.set(brick);
                paresDeColisao.add(new AbstractMap.SimpleEntry<>(new BallInfo(ball.getQtdBounce(), ball.getHit(), ball.getId()), new BrickInfo(brick.getId())));
            }
        } else if (brick.getrTopCorner1().intersects(ballRect.left, ballRect.top, ballRect.right, ballRect.bottom)) {
            if (!jaExisteColisao(ball, brick)) {
                Log.d(TAG, "top-corner-1 hit");
                ball.registraHit();
                ball.mudaSinalY(Ball.UP);
                ball.mudaSinalX();
                ball.alteraAngulo();
                brick.hit();
                iterator.set(brick);
                paresDeColisao.add(new AbstractMap.SimpleEntry<>(new BallInfo(ball.getQtdBounce(), ball.getHit(), ball.getId()), new BrickInfo(brick.getId())));
            }
        }else if (brick.getrTopCorner2().intersects(ballRect.left, ballRect.top, ballRect.right, ballRect.bottom)) {
            if (!jaExisteColisao(ball, brick)) {
                Log.d(TAG, "top-corner-2 hit");
                ball.registraHit();
                ball.mudaSinalY(Ball.UP);
                ball.mudaSinalX();
                ball.alteraAngulo();
                brick.hit();
                iterator.set(brick);
                paresDeColisao.add(new AbstractMap.SimpleEntry<>(new BallInfo(ball.getQtdBounce(), ball.getHit(), ball.getId()), new BrickInfo(brick.getId())));
            }
        }else if (brick.getRleft().intersects(ballRect.left, ballRect.top, ballRect.right, ballRect.bottom)) {
            if (!jaExisteColisao(ball, brick)) {
                Log.d(TAG, "left hit");
                ball.registraHit();
                ball.mudaSinalX();
                brick.hit();
                iterator.set(brick);
                paresDeColisao.add(new AbstractMap.SimpleEntry<>(new BallInfo(ball.getQtdBounce(), ball.getHit(), ball.getId()), new BrickInfo(brick.getId())));
            }
        } else if (brick.getRright().intersects(ballRect.left, ballRect.top, ballRect.right, ballRect.bottom)) {
            if (!jaExisteColisao(ball, brick)) {
                Log.d(TAG, "right hit");
                ball.registraHit();
                ball.mudaSinalX();
                brick.hit();
                iterator.set(brick);
                paresDeColisao.add(new AbstractMap.SimpleEntry<>(new BallInfo(ball.getQtdBounce(), ball.getHit(), ball.getId()), new BrickInfo(brick.getId())));
            }
        }   else if (brick.getRtop().intersects(ballRect.left, ballRect.top, ballRect.right, ballRect.bottom)) {
            if (!jaExisteColisao(ball, brick)) {
                Log.d(TAG, "top hit");
                ball.registraHit();
                ball.mudaSinalY();
                brick.hit();
                iterator.set(brick);
                paresDeColisao.add(new AbstractMap.SimpleEntry<>(new BallInfo(ball.getQtdBounce(), ball.getHit(), ball.getId()), new BrickInfo(brick.getId())));
            }
        } else if (brick.getRbot().intersects(ballRect.left, ballRect.top, ballRect.right, ballRect.bottom)) {
            if (!jaExisteColisao(ball, brick)) {
                Log.d(TAG, "bot hit");
                ball.registraHit();
                ball.mudaSinalY();
                brick.hit();
                iterator.set(brick);
                paresDeColisao.add(new AbstractMap.SimpleEntry<>(new BallInfo(ball.getQtdBounce(), ball.getHit(), ball.getId()), new BrickInfo(brick.getId())));
            }
        }
    }

    private boolean jaExisteColisao(Ball ball, Brick brick) {
        boolean existeColisao = false;
        ListIterator<AbstractMap.SimpleEntry<BallInfo, BrickInfo>> simpleEntryListIterator = paresDeColisao.listIterator();
        while (simpleEntryListIterator.hasNext()){
            AbstractMap.SimpleEntry<BallInfo, BrickInfo> par = simpleEntryListIterator.next();
            BallInfo bp = par.getKey();
            BrickInfo brp = par.getValue();
            if (bp.getId() == ball.getId() && brp.getId() == brick.getId()) {
                Log.d(TAG, "ja existe colisao para esta dupla");
                existeColisao = true;
                Log.d(TAG, "ball.getQtdBounce()=" + ball.getQtdBounce());
                Log.d(TAG, "bp.getQtdBounce()=" + bp.getQtdBounce());
                //verifica se a colisao eh antiga, e desfaz ela
//                if (ball.getQtdBounce() > bp.getQtdBounce() + 3) {
                if (ball.getHit() > bp.getQtdHit()) {
                    existeColisao = false;
                    simpleEntryListIterator.remove();
                }
            }
        }
        return existeColisao;
    }
}
