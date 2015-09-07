package br.com.salomao.smarttictactoe.view.component;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import br.com.salomao.smarttictactoe.view.SmartTicTacToeApplication;

public class BaseActivity extends AppCompatActivity {
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Perform injection so that when this call returns all dependencies will be available for use.
        ((SmartTicTacToeApplication) getApplication()).component().inject(this);
    }
}
