package edu.olemiss.jlgabbar.p1jlgabbar;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import android.widget.TextView.OnEditorActionListener;



public class MainActivity extends ActionBarActivity implements SeekBar.OnSeekBarChangeListener {
    TextView tiptv;
    SeekBar mySeekBar;
    SeekBar splitSeek;
    TextView tiptotal;
    int num;
    EditText billInput;
    float tipAmount;
    NumberFormat nf = new DecimalFormat("#.00");
    float totalAmount;
    TextView totalView;
    Switch switcher;
    TextView splittv;
    TextView tipSlidertv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tiptv = (TextView)findViewById(R.id.tip);
        tiptv.setText("Tip (15%):");
        tipSlidertv = (TextView)findViewById(R.id.tipSliderView);
        tipSlidertv.setText("Tip (15%)");
        mySeekBar = (SeekBar)findViewById(R.id.seekBar1);
        mySeekBar.setOnSeekBarChangeListener(this);
        splitSeek = (SeekBar)findViewById(R.id.splitSeek1);
        splitSeek.setOnSeekBarChangeListener(this);
        totalView = (TextView)findViewById(R.id.total_id);
        tiptotal = (TextView)findViewById(R.id.tip_total);
        billInput = (EditText) findViewById(R.id.bill_input);
        switcher = (Switch)findViewById(R.id.switch1);
        tipAmount = 0;
        billInput.setText("0");
        splittv = (TextView)findViewById(R.id.splitView);
        splittv.setText("1 Check");
        billInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_SEND){
                    tipAmount = Float.parseFloat(billInput.getText().toString())*15/100.00f;
                    tiptotal.setText("" + nf.format(tipAmount));
                    totalAmount = tipAmount + Float.parseFloat(billInput.getText().toString());
                    totalView.setText("$"+nf.format(totalAmount));
                    return true;
                }
                return false;
            }
        });
//        nf.setMaximumFractionDigits(2);
//        nf.setMinimumFractionDigits(2);


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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onToggleClicked(View view) {
        // Is the toggle on?
        boolean on = ((Switch)view).isChecked();
        totalAmount = tipAmount + Float.parseFloat(billInput.getText().toString());
        int b;
        float f;
        if (on) {
            if(splitSeek.getProgress()+1==1) {
                b = Math.round((int) totalAmount);
                totalView.setText("$" + nf.format(b + 1));
            }
            else{
                b = Math.round((int) totalAmount/(splitSeek.getProgress()+1));
                totalView.setText("$" + nf.format(b + 1));
            }

        } else {
            if(splitSeek.getProgress()+1==1) {
                f = totalAmount;
                totalView.setText("$" + nf.format(f));
            }
            else {
                f = totalAmount/(splitSeek.getProgress()+1);
                totalView.setText("$" + nf.format(f));
            }

        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switcher.setChecked(false);
        if(seekBar ==mySeekBar){
            tiptv.setText(null);
            tipSlidertv.setText(null);
            num = progress;
            tiptv.setText("Tip (" + num + "%):");
            tipAmount = Float.parseFloat(billInput.getText().toString())*num/100.00f;
            tiptotal.setText("" + nf.format(tipAmount));
            totalAmount = tipAmount + Float.parseFloat(billInput.getText().toString());
            totalView.setText("$"+nf.format(totalAmount/(splitSeek.getProgress()+1)));
            tipSlidertv.setText("Tip (" + num + "%)");

        }
        else{

            num = progress +1;
            splittv.setText(null);
            if(num == 1) {
                splittv.setText(num + " Check");
            }
            else splittv.setText(num + " Checks");
            //totalAmount = (totalAmount/num);
            totalView.setText("$"+nf.format(totalAmount/num));

        }
//        tiptv.setText(null);
//        num = progress;
//        tiptv.setText("Tip (" + num + "%):");
//        tipAmount = Float.parseFloat(billInput.getText().toString())*num/100.00f;
//        tiptotal.setText("" + nf.format(tipAmount));
//        totalAmount = tipAmount + Float.parseFloat(billInput.getText().toString());
//        totalView.setText("$"+nf.format(totalAmount));
    }


    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        Toast.makeText(getApplicationContext(), "start", Toast.LENGTH_LONG);
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        Toast.makeText(getApplicationContext(), "stop",Toast.LENGTH_LONG);

    }
}
