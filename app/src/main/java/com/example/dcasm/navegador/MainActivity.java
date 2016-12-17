package com.example.dcasm.navegador;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;

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

        direccion.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    web.loadUrl(direccion.getText().toString());
                    return true;
                }
                return false;
            }
        });

        web.requestFocus();

        web.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView webView, String url) {
                direccion.setText(url);
            }
        });
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

        return super.onOptionsItemSelected(item);
    }
}
