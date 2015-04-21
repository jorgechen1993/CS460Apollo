package com.example.cs460apollopubcrawl;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.Toast;
import android.content.Intent;


public class SelectLine extends Activity implements OnItemSelectedListener {

	private ImageView subImage;
	public String menuItemSelected;
	public int menuItemNum;
	private Button buttonGo;
	private ArrayList<String> items = new ArrayList();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_line);
		
		subImage = (ImageView)findViewById(R.id.image);
		buttonGo = (Button)findViewById(R.id.buttonGo);
		
		Spinner dropMenu = (Spinner)findViewById(R.id.dropMenu);
		dropMenu.setOnItemSelectedListener(this);   //set listener
		
		//add items to the array that will be used in the drop down menu
		items.add("Green");
		items.add("Orange");
		items.add("Red");
		items.add("Blue");
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
		//set resource to use for drop down view, Android supplied format
		adapter.setDropDownViewResource(
		   android.R.layout.simple_spinner_dropdown_item);  
		dropMenu.setAdapter(adapter);
		

        buttonGo.setOnClickListener(  new OnClickListener() {
   
                     public void onClick(View v) {
                        Intent i = new Intent(getBaseContext(),SelectStop.class);
                        if (menuItemNum > 0) {
                        i.putExtra("line_color", menuItemSelected);
                        i.putExtra("line_color_num", menuItemNum);
                        startActivity(i);
                        } else {
                        	Toast.makeText(getBaseContext(), "Line was not selected", Toast.LENGTH_LONG).show();
                        }
                     }    
        } );        		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.select_line, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	//listener methods for callbacks 
	public void onItemSelected(AdapterView<?> parent, View v, int position,
			long id) {
		
		if (position == 0) {
			subImage.setImageResource(R.drawable.green_line);
		} else if (position == 1) {
			subImage.setImageResource(R.drawable.orange_line);
		} else if (position == 2) {
			subImage.setImageResource(R.drawable.red_line);
		} else if (position == 3) {
			subImage.setImageResource(R.drawable.blue_line);
		} else {
			subImage.setImageResource(R.drawable.subway_map);
		}
		menuItemSelected = items.get(position);
		menuItemNum = position;
	}

	public void onNothingSelected(AdapterView<?> parent) {
		subImage.setImageResource(R.drawable.subway_map);
	}
}
