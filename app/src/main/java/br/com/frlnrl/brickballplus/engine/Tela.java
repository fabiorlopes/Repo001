package br.com.frlnrl.brickballplus.engine;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by Fabio on 20/10/2017.
 */

public class Tela {
    private DisplayMetrics metrics;

    private static final String TAG = "Tela";

    Tela(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = null;
        if (wm != null) {
            display = wm.getDefaultDisplay();
        }
        metrics = new DisplayMetrics();
        if (display != null) {
            display.getMetrics(metrics);
        }

    }

    public int getAlturaTabuleiro() {
        int bottom = (int) (metrics.heightPixels * 0.22296544);
        return (int) metrics.heightPixels - bottom;
    }

    public int getAltura(){
        //Log.d(TAG, "getAltura: " + metrics.heightPixels);
        return metrics.heightPixels;
    }

    public int getLargura() {
        return metrics.widthPixels;
    }
}
