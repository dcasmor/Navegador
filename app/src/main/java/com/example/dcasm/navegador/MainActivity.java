package com.example.dcasm.navegador;

import android.content.ClipData;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.method.TextKeyListener;
import android.text.style.BackgroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class MainActivity extends AppCompatActivity {

    private WebView webview;
    private AutoCompleteTextView autoText;
    private String aux, url;
    private ArrayAdapter<String> historial;
    private BrowserDB bdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Control AutoCompleteTextview
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, HISTORIAL);

        autoText = (AutoCompleteTextView) findViewById(R.id.autoText);
        autoText.setAdapter(adapter);

        /*autoText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                aux = autoText.getText().toString();
                if (hasFocus && aux.equals("Buscar o introducir página")) {
                    TextKeyListener.clear((autoText).getText());
                } else {
                    if (hasFocus && !(aux.equals("Buscar o introducir página"))) {
                        Spannable spanText = Spannable.Factory.getInstance().newSpannable(aux);
                        spanText.setSpan(new BackgroundColorSpan(0xFFFFFF00), 0, aux.length(),
                                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        autoText.setText(spanText);
                    } else {
                        if (!hasFocus && !type) {
                            autoText.setText(aux);
                        }
                    }
                }
            }
        });*/

        //Control WebView
        /*webview = (WebView) findViewById(R.id.webV);
        webview.loadUrl("https://www.google.com");
        webview.setWebViewClient(new WebViewClient());*/
    }

    private static final String[] HISTORIAL = new String[] {
            "Belgium", "France", "Italy", "Germany", "Spain", "Payaso", "Pantalla"
    };

    private void buscar() {
        int a = 1;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }
}
