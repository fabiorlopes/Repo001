package br.com.frlnrl.brickballplus;

import android.app.Activity;
import android.os.Bundle;
import android.widget.FrameLayout;

import br.com.frlnrl.brickballplus.engine.Game;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends Activity {

    @BindView(R.id.main_container)
    FrameLayout container;

    Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        game = new Game(this);

        container.addView(game);
    }

    @Override
    protected void onResume() {
        super.onResume();
        game.continuar();
        new Thread(game, "Game").start();
    }

    @Override
    protected void onPause() {
        game.pause();
        super.onPause();
    }
}
