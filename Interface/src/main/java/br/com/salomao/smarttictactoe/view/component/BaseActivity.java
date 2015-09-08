package br.com.salomao.smarttictactoe.view.component;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    public int getCompatColor(int colorResourceId) {
        return ContextCompat.getColor(this, colorResourceId);
    }
}
