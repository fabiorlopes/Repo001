package br.com.frlnrl.brickballplus.elements;

import java.util.UUID;

/**
 * Created by Fabio on 25/11/2017.
 */

public class BallInfo {
    private int qtdBounce;
    private UUID id;

    public BallInfo(int qtdBounce, UUID id) {
        this.qtdBounce = qtdBounce;
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public int getQtdBounce() {
        return qtdBounce;
    }
}
