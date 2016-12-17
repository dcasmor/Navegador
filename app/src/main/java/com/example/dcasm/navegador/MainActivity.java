package com.example.dcasm.navegador;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    BrowserDB bd;

    private WebView web;
    private AutoCompleteTextView direccion;
    private InputMethodManager input;
    private Vector<String> urls = new Vector<String>();
    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bd = new BrowserDB(this);

        direccion = (AutoCompleteTextView) findViewById(R.id.acDireccion);
        web = (WebView) findViewById(R.id.webV);
        pb = (ProgressBar) findViewById(R.id.pbar);
        input = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);

        pb.setMax(100);

        WebSettings webSettings = web.getSettings();
        webSettings.setJavaScriptEnabled(true);

        //Listener de introducción de URL
        direccion.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    cargaWeb();
                    return true;
                }
                return false;
            }
        });

        direccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoComp();
            }
        });

        web.setWebViewClient(new WebViewClient() {
            public void onPageStarted(WebView webView, String url, Bitmap favicon) {
                direccion.setText(url);
                bd.nuevaUrl(url);
            }

        });

        web.setWebChromeClient(new WebChromeClient() {
            private int progress;

            public void setProgress(int progress) {
                this.progress = progress;
            }

            @Override
            public void onProgressChanged(WebView view, int progress) {
                pb.setProgress(0);
                pb.setVisibility(View.VISIBLE);
                this.setProgress(progress * 1000);
                pb.incrementProgressBy(progress);
                if (progress == 100)
                    pb.setVisibility(View.INVISIBLE);
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, urls);
        direccion.setAdapter(adapter);

        //Inicialización del programa
        web.loadUrl("http://www.google.es");
        direccion.setText(web.getUrl().toString());
        web.requestFocus();
        autoComp();
    }

    public void autoComp() {
        urls.clear();
        Cursor c = bd.getUrls();
        if (c != null) {
            while (c.moveToNext()) {
                urls.add(c.getString(0));
                if (c.getString(0).substring(10) == "https://www.")
                    urls.add(c.getString(0).substring(11, c.getString(0).length()-1));
                if (c.getString(0).substring(9) == "http://www.")
                urls.add(c.getString(0).substring(10, c.getString(0).length()-1));
            }
        }

    }

    public void cargaWeb() {
        if (URLUtil.isValidUrl(direccion.getText().toString())) {
            web.loadUrl(direccion.getText().toString());
            input.toggleSoftInput(0, 0);
            web.requestFocus();
            autoComp();
        }
        else {
            String url = "https://www.google.com/search?q=";
            String texto = direccion.getText().toString().trim();
            String busca = url + texto;
            web.loadUrl(busca);
            input.toggleSoftInput(0, 0);
            web.requestFocus();
            autoComp();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle estado) {
        super.onSaveInstanceState(estado);
        estado.putString("url", direccion.getText().toString());

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        direccion.setText(savedInstanceState.getString("url").toString());
        cargaWeb();
    }

    @Override
    public void onBackPressed() {
        if (web.canGoBack())
            web.goBack();
        else
            super.onBackPressed();
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
