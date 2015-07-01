package com.project.projectioiocoptori;

import java.util.concurrent.TimeUnit;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.IBinder;
import ioio.lib.api.AnalogInput;
import ioio.lib.api.DigitalOutput;
import ioio.lib.api.DigitalOutput.Spec.Mode;
import ioio.lib.api.IOIO;
import ioio.lib.api.PwmOutput;
import ioio.lib.api.exception.ConnectionLostException;
import ioio.lib.util.BaseIOIOLooper;
import ioio.lib.util.IOIOLooper;
import ioio.lib.util.android.IOIOService;
import com.project.projectioiocoptori.R;

/**
 * An example IOIO service. While this service is alive, it will attempt to
 * connect to a IOIO and blink the LED. A notification will appear on the
 * notification bar, enabling the user to stop the service.
 */
public class IOIOServiceBackg extends IOIOService {
	@Override
	protected IOIOLooper createIOIOLooper() {
		return new BaseIOIOLooper() {
			private DigitalOutput led_;
			// Declare pulse width of PWM output instance is 2ms
			int[] pw = {2000,2000,2000,2000,2000,2000};
			int[] temp_pw = {1600,1500,1800,1700,2000,2000};
			// Assign PWM output with port on IOIO board (PWM pins in board is 34-40 and 45-48)
			
			int[] pin_pwm = {34,35,36,37,38,39};
			PwmOutput[] PWM = new PwmOutput[6];
			
			AnalogInput[] AIn = new AnalogInput[4];
			int[] pin_AIn = {43,44,45,46};
			// Declare variable startTime,EndTime
			long startTime;
	    	long EndTime;
	    	int freq = 50 ;

			@Override
			protected void setup() throws ConnectionLostException,
					InterruptedException {
				for(int i = 0;i<4;i++){
					// Assign analog input with port on IOIO board
					AIn[i] = ioio_.openAnalogInput(pin_AIn[i]);
					}
				
				led_ = ioio_.openDigitalOutput(IOIO.LED_PIN);
			/*	for(int i = 0;i<6;i++){
					Thread.sleep(602);
					PWM[i] = ioio_.openPwmOutput(pin_pwm[i], 50);
					//PWM[i].setPulseWidth(pw[i]);
				//	Thread.sleep(1, 1000000);
			
				}*/
				
				
				TimeUnit.MICROSECONDS.sleep(402000);
				PWM[0] = ioio_.openPwmOutput(pin_pwm[0], 50);
				TimeUnit.MICROSECONDS.sleep(402000);
				PWM[1] = ioio_.openPwmOutput(pin_pwm[1], 50);
				TimeUnit.MICROSECONDS.sleep(402000);
				PWM[2] = ioio_.openPwmOutput(pin_pwm[2], 50);
				TimeUnit.MICROSECONDS.sleep(402000);
				PWM[3] = ioio_.openPwmOutput(pin_pwm[3], 50);
				TimeUnit.MICROSECONDS.sleep(402000);
				PWM[4] = ioio_.openPwmOutput(pin_pwm[4], 50);
				TimeUnit.MICROSECONDS.sleep(402000);
				PWM[5] = ioio_.openPwmOutput(pin_pwm[5], 50);
				TimeUnit.MICROSECONDS.sleep(402000);
				
				
				
			/*	TimeUnit.MICROSECONDS.sleep(402000);
				PWM[0] = ioio_.openPwmOutput(new DigitalOutput.Spec(pin_pwm[0], Mode.OPEN_DRAIN), 50);
				TimeUnit.MICROSECONDS.sleep(402000);
				PWM[1] = ioio_.openPwmOutput(new DigitalOutput.Spec(pin_pwm[1], Mode.OPEN_DRAIN), 50);
				TimeUnit.MICROSECONDS.sleep(402000);
				PWM[2] = ioio_.openPwmOutput(new DigitalOutput.Spec(pin_pwm[2], Mode.OPEN_DRAIN), 50);
				TimeUnit.MICROSECONDS.sleep(402000);
				PWM[3] = ioio_.openPwmOutput(new DigitalOutput.Spec(pin_pwm[3], Mode.OPEN_DRAIN), 50);
				TimeUnit.MICROSECONDS.sleep(402000);
				PWM[4] = ioio_.openPwmOutput(new DigitalOutput.Spec(pin_pwm[4], Mode.OPEN_DRAIN), 50);
				TimeUnit.MICROSECONDS.sleep(402000);
				PWM[5] = ioio_.openPwmOutput(new DigitalOutput.Spec(pin_pwm[5], Mode.OPEN_DRAIN), 50);
				TimeUnit.MICROSECONDS.sleep(402000);*/
				
				
				
				
				
				
				
			
				
				led_.write(false);
				Thread.sleep(500);
				led_.write(true);
				Thread.sleep(500);
				
				
			//	Toast.makeText(getApplicationContext(),"Connected!", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void loop() throws ConnectionLostException,
					InterruptedException {
				
				
				startTime = System.nanoTime();
				
				for(int j = 0; j<6; j++){
					PWM[j].setPulseWidth(pw[j]);	
				//	Thread.sleep(0, 2000);		
					
				}
	/*		
				for(int i = 0; i<6;i++){
					for(int j = 0; j<6; j++){
						if(j==i){
							temp_pw[j] = pw[i];
						}else{
							temp_pw[j] = 0;
						}
					}
					
					for(int j = 0; j<6; j++){
						PWM[j].setPulseWidth(temp_pw[j]);	
						TimeUnit.MICROSECONDS.sleep(pw[i]);
						
					}
					
				}*/
				
				
			/*	for(int a = 0;a<4;a++){
					PWM[a].setDutyCycle(AIn[a].read());	
					}*/
				
				
				for(int j = 0; j<6; j++){
					PWM[j].setPulseWidth(temp_pw[j]);	
				//	Thread.sleep(0, 2000);		
					
				}
				
				
				EndTime = (System.nanoTime()-startTime)*1000;
				
		//		TimeUnit.MICROSECONDS.sleep((1000000/freq)-EndTime);
				
				
			}
		};
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		if (intent != null && intent.getAction() != null
				&& intent.getAction().equals("stop")) {
			// User clicked the notification. Need to stop the service.
			nm.cancel(0);
			stopSelf();
		} else {
			// Service starting. Create a notification.
			Notification notification = new Notification(
					R.drawable.ic_launcher, "IOIO service running",
					System.currentTimeMillis());
			notification
					.setLatestEventInfo(this, "IOIO Service", "Click to stop",
							PendingIntent.getService(this, 0, new Intent(
									"stop", null, this, this.getClass()), 0));
			notification.flags |= Notification.FLAG_ONGOING_EVENT;
			nm.notify(0, notification);
		}
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

}

