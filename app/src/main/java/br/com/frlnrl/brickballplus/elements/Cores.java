package br.com.frlnrl.brickballplus.elements;

import android.graphics.Color;
import android.graphics.Paint;

import static android.graphics.Paint.Style.STROKE;

/**
 * Created by Fabio on 20/10/2017.
 */

public class Cores {

    private static final int VERMELHO = 0xFFFF0000;
    private static final int VERDE = 0xFF00FF00;
    private static final int VERDELEVE = 0xFF34CB8E;
    private static final int VERMELHOLEVE = 0xFFDE2142;
    private static final int BRANCO = 0xFFFFFFFF;
    private static final int PRETO = 0xFF000000;
    private static Paint paint;

    static Paint getCorBola() {
        paint = new Paint();
        paint.setTextSize(38);
        paint.setColor(BRANCO);
        return paint;
    }
    static Paint getCorBolinha(){
        paint = new Paint();
        paint.setColor(BRANCO);
        return paint;
    }
    static Paint getCorAnimacaoBolinha(){
        paint = new Paint();
        paint.setColor(BRANCO);
        paint.setStyle(STROKE);
        paint.setStrokeWidth(7f);
        return paint;
    }

    static Paint getCorRect(){
        paint = new Paint();
        paint.setColor(VERDELEVE);
        return paint;
    }

    public static Paint getCorRectTop() {
        Paint paint = new Paint();
        paint.setColor(VERMELHO);
        return paint;
    }
    public static Paint getCorRectBot() {
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        return paint;
    }

    public static Paint getCorRectLeft() {
        Paint paint = new Paint();
        paint.setColor(Color.YELLOW);
        return paint;
    }

    public static Paint getCorRectRight() {
        Paint paint = new Paint();
        paint.setColor(BRANCO);
        return paint;
    }

    public static Paint getCorTexto() {
        Paint paint = new Paint();
        paint.setColor(BRANCO);
        paint.setTextSize(60);
        paint.setShadowLayer(3, 5, 5, PRETO);
        return paint;
    }

    public static Paint getCorTextoRect() {
        Paint paint = new Paint();
        paint.setColor(PRETO);
        paint.setTextSize(55);
        return paint;
    }

    public static Paint getCorGameOver() {
        Paint paint = new Paint();
        paint.setColor(VERMELHOLEVE);
        paint.setTextSize(55);
        return paint;
    }
}
