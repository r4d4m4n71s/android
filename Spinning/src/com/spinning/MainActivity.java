package com.spinning;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.accelerometer.AccelerometerManager;
import com.accelerometer.IAccelerometerListener;

public class MainActivity extends Activity implements IAccelerometerListener {

	private static final String KEY_STATE = "state";
	private static final int TRANSITIONS = 5;
	private static int stateValue;
	private TextView text;
	private RelativeLayout rl;        
	private Typeface styleFont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (TextView)findViewById(R.id.text);        
    	rl = (RelativeLayout)findViewById(R.id.layout);
    	styleFont = Typeface.createFromAsset(getAssets(), "fonts/Action_Jackson.ttf");
    	text.setTypeface(styleFont);
    	
    	if(null != savedInstanceState){
    		//restore state random from screen changes
        	this.selection(savedInstanceState.getInt(KEY_STATE));	
    	}    	
    }
    
    public void onAccelerationChanged(float x, float y, float z) {
        // TODO Auto-generated method stub
         
    }
 
    public void onShake(float force) {
    	this.selection(this.getRandomNumber());         
    }    
    
    @Override
	protected void onSaveInstanceState(Bundle outState) {
    	outState.putInt(KEY_STATE,stateValue);
		super.onSaveInstanceState(outState);
	}

	@Override
    public void onResume() {
            super.onResume();
            /*Toast.makeText(getBaseContext(), "onResume Accelerometer Started", 
                    Toast.LENGTH_SHORT).show();*/
             
            //Check device supported Accelerometer senssor or not
            if (AccelerometerManager.isSupported(this)) {
                 
                //Start Accelerometer Listening
                AccelerometerManager.startListening(this);
            }
    }
     
    @Override
    public void onStop() {
            super.onStop();
             
            //Check device supported Accelerometer senssor or not
            if (AccelerometerManager.isListening()) {
                 
                //Start Accelerometer Listening
                AccelerometerManager.stopListening();
                 
                /*Toast.makeText(getBaseContext(), "onStop Accelerometer Stoped", 
                         Toast.LENGTH_SHORT).show();*/
            }
            
    }
     
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("Sensor", "Service  distroy");
         
        //Check device supported Accelerometer senssor or not
        if (AccelerometerManager.isListening()) {
             
            //Start Accelerometer Listening
            AccelerometerManager.stopListening();
             
            Toast.makeText(getBaseContext(), "onDestroy Accelerometer Stoped", 
                   Toast.LENGTH_SHORT).show();
        }
             
    }
    
    private void selection(int op){
    	
    	text.setTextColor(Color.BLACK);
    	switch (op) {	case 6:	text.setText(getString(R.string.drink)+"!");
    							text.setTextColor(Color.WHITE);
    							rl.setBackgroundColor(Color.BLUE);    							
    							break;
				    	case 2:	text.setText(getString(R.string.drink_double)+"!");
				    			text.setTextColor(Color.WHITE);
				    			rl.setBackgroundColor(Color.RED);
								break;
				    	case 3:	text.setText(getString(R.string.dright_no)+"!");
				    			rl.setBackgroundColor(Color.GREEN);
				    			break;
				    	case 1:	text.setText(getString(R.string.drink_right)+"!");
				    			rl.setBackgroundColor(Color.YELLOW);
				    			break;				    	
				    	case 4:	text.setText(getString(R.string.drink_left)+"!");				    			
				    			rl.setBackgroundColor(Color.MAGENTA);
				    			break;
				    	case 5:	text.setText(getString(R.string.drink_all)+"!");				    					
				    			rl.setBackgroundColor(Color.CYAN);
		    					break;

				    	default:text.setText(getString(R.string.shake_your_phone)+"!");
    							rl.setBackgroundColor(Color.WHITE);
    							break;
		}
    	stateValue = op;
    }
    
    private int getRandomNumber() {
    	return (int)Math.round(Math.random()*TRANSITIONS + 1);    	        
    }
}
