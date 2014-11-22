package il.ac.shenkar.broadcastrecieverexample;

import java.util.Calendar;
import java.util.Date;

import il.ac.shenkar.broadcastrecieverexample.bl.MainController;

import com.example.broadcastrecieverexample.R;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

public class MainActivity extends Activity {

	private MainController controller;
	private EditText editText;
	private TimePicker timePicker;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		timePicker = (TimePicker) findViewById(R.id.timePicker1);
		editText = (EditText) findViewById(R.id.editTextMessage);
		controller = new MainController(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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

	public void remindMeClick(View v) {
		int hour = timePicker.getCurrentHour();
		int minute = timePicker.getCurrentMinute();
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.set(Calendar.HOUR_OF_DAY, hour);
		c.set(Calendar.MINUTE, minute);
		String message = editText.getText().toString();
		controller.CreateAlarm(message, c.getTime());
	}
}
