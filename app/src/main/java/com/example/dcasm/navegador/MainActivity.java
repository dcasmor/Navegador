package com.example.dcasm.navegador;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    BrowserDB bd;

    private WebView web;
    private AutoCompleteTextView direccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bd = new BrowserDB(this);

        direccion = (AutoCompleteTextView) findViewById(R.id.acDireccion);
        web = (WebView) findViewById(R.id.webV);
        final InputMethodManager input = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);

        //Listener de introducción de URL
        direccion.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    if (URLUtil.isValidUrl(direccion.getText().toString())) {
                        bd.nuevaUrl(direccion.getText().toString());
                        web.loadUrl(direccion.getText().toString());
                        input.toggleSoftInput(0, 0);
                        direccion.clearFocus();
                        return true;
                    }
                    else
                        Toast.makeText(MainActivity.this, "URL no válida", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

        web.requestFocus();
        WebSettings webSettings = web.getSettings();
        webSettings.setJavaScriptEnabled(true);


        //Cóodigo para cambiar el contenido de la barra de dirección
        web.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView webView, String url) {
                direccion.setText(url);
            }
        });


        //Inicialización del programa
        web.loadUrl("http://www.google.es");
        direccion.setText(web.getUrl().toString());
    }

    @Override
    public void onBackPressed() {
        if (web.canGoBack()) {
            web.goBack();
        }
        else
            finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.bHome) {
            web.loadUrl("http://www.google.es");
            direccion.setText(web.getUrl().toString());
        }
        else if (id == R.id.forward && web.canGoForward())
            web.goForward();
        else if (id == R.id.delete)
            bd.borrar();

        return super.onOptionsItemSelected(item);
    }
}
