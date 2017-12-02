package br.com.frlnrl.brickballplus.elements;

import java.util.UUID;

/**
 * Created by Fabio on 25/11/2017.
 */

public class BrickInfo {
    private UUID id;

    public BrickInfo(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}
