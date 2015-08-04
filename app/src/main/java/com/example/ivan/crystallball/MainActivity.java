package com.example.ivan.crystallball;

import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String Tag = MainActivity.class.getSimpleName();
    private CrystallBall mCrystallBall = new CrystallBall();
    private TextView mAnswerLabel;
    private Button mGetAnswerButton;
    private ImageView mCrystallBallImage;
    private SensorManager mSensorManager;
    private Sensor mAccelorometer;
    private ShakeDetector mShakeDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAnswerLabel = (TextView) findViewById(R.id.textView);
        mGetAnswerButton = (Button) findViewById((R.id.button));

        mGetAnswerButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                handleNewAnswer();
            }
        });

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelorometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector(new ShakeDetector.OnShakeListener() {
            @Override
            public void onShake() {
                handleNewAnswer();
            }
        });

        Log.d(Tag, "We're logging from the OnCreate() method");
        //Toast.makeText(this, "Yay! Our activity was created!", Toast.LENGTH_LONG).show();
        Toast welcomeToast = Toast.makeText(this, "Look at me up here", Toast.LENGTH_LONG);
        welcomeToast.setGravity(Gravity.TOP, 0, 0);
        welcomeToast.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        mSensorManager.registerListener(mShakeDetector,mAccelorometer,SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(mShakeDetector);
    }

    private void handleNewAnswer() {
        String answer = mCrystallBall.getAnAnswer();

        //Update the label with dynamic answer
        mAnswerLabel.setText(answer);

        animateCrystalBall();
        animateAnswer();
        playSound();
    }

    private void animateCrystalBall() {
        mCrystallBallImage = (ImageView) findViewById(R.id.imageView);
        mCrystallBallImage.setImageResource(R.drawable.ball_animation);
        AnimationDrawable ballanimation = (AnimationDrawable) mCrystallBallImage.getDrawable();
        ballanimation.start();
    }

    private void animateAnswer() {
        AlphaAnimation fadeAnimetion = new AlphaAnimation(0, 1);
        fadeAnimetion.setDuration(1500);
        fadeAnimetion.setFillAfter(true);

        mAnswerLabel.setAnimation(fadeAnimetion);
    }

    private void playSound() {
        MediaPlayer player = MediaPlayer.create(this, R.raw.crystal_ball);
        player.start();
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
            }
        });
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
}
