package br.com.frlnrl.brickballplus.elements;

import java.util.UUID;

/**
 * Created by Fabio on 25/11/2017.
 */

public class BallInfo {
    private int qtdBounce;

    public int getQtdHit() {
        return qtdHit;
    }

    private int qtdHit;
    private UUID id;

    public BallInfo(int qtdBounce, int qtdHit, UUID id) {
        this.qtdBounce = qtdBounce;
        this.qtdHit = qtdHit;
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public int getQtdBounce() {
        return qtdBounce;
    }
}
