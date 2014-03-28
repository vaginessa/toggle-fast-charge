package chris.conley.togglefastcharge;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;

public class FastCharge extends Activity {

	ToggleButton fcToggle;
	File ffc;

	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_layout);

		fcToggle = (ToggleButton) findViewById(R.id.fcToggle);
		final TextView tv = (TextView) findViewById(R.id.tv1);

		ffc = new File("/sys/kernel/fast_charge/force_fast_charge");

		StringBuilder text = new StringBuilder();

		try {
			BufferedReader br = new BufferedReader(new FileReader(ffc));
			String line;

			while ((line = br.readLine()) != null) {
				text.append(line);
				text.append('\n');
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		tv.setText(text);
		update(tv.getText().toString());

		fcToggle.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					FileOutputStream writer = new FileOutputStream(ffc);
					if (tv.getText().toString().contains("1"))
						try {
							writer.write("0".getBytes());
							writer.close();
							StringBuilder text = new StringBuilder();

							try {
								BufferedReader br = new BufferedReader(
										new FileReader(ffc));
								String line;

								while ((line = br.readLine()) != null) {
									text.append(line);
									text.append('\n');
								}
								br.close();
							} catch (IOException e) {
								e.printStackTrace();
							}

							tv.setText(text);
							update(tv.getText().toString());
						} catch (IOException e) {
							e.printStackTrace();
						}
					if (tv.getText().toString().contains("0"))
						try {
							writer.write("1".getBytes());
							writer.close();
							StringBuilder text = new StringBuilder();

							try {
								BufferedReader br = new BufferedReader(
										new FileReader(ffc));
								String line;

								while ((line = br.readLine()) != null) {
									text.append(line);
									text.append('\n');
								}
								br.close();
							} catch (IOException e) {
								e.printStackTrace();
							}

							tv.setText(text);
							update(tv.getText().toString());
						} catch (IOException e) {
							e.printStackTrace();
						}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void update(String str) {
		if (str.contains("1")) {
			fcToggle.setChecked(true);
		}
		if (str.contains("0")) {
			fcToggle.setChecked(false);
		}
	}
}