package com.example.audiovideoaudiomanager;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.os.Build;
import android.app.Activity;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.view.View.OnClickListener;

public class AudioVideoAudioManagerActivity extends Activity {
	private int mVolume =6,mVolumeMax = 10, mVolumeMin = 0;
	private SoundPool mSoundPool;
	private int mSoundId;
	private AudioManager mAudioManager;
	private boolean mCanPlayAudio;
	Button upButton,downButton,playButton;
	TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_video_audio_manager);
        mAudioManager = (AudioManager)getSystemService(AUDIO_SERVICE);
    	tv = (TextView) findViewById(R.id.textView1);
    	tv.setText(String.valueOf(mVolume));
    	upButton = (Button) findViewById(R.id.upbutton);
    	upButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mAudioManager.playSoundEffect(AudioManager.FX_KEY_CLICK);
				if(mVolume<mVolumeMax){
					mVolume +=2;
					tv.setText(String.valueOf(mVolume));
				}
			}
    		
    	});
    	downButton = (Button) findViewById(R.id.downbutton);
    	downButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mAudioManager.playSoundEffect(AudioManager.FX_KEY_CLICK);
				if(mVolume>mVolumeMin){
					mVolume -=2;
					tv.setText(String.valueOf(mVolume));
				}
			}
    		
    	});
    	playButton = (Button) findViewById(R.id.palybutton);
    	playButton.setEnabled(false);
    	mSoundPool = new SoundPool(1,AudioManager.STREAM_MUSIC,0);
    	mSoundId = mSoundPool.load(this,R.raw.slow_whoop_bubble_pop, 1);
    	mSoundPool.setOnLoadCompleteListener(new OnLoadCompleteListener(){

			@Override
			public void onLoadComplete(SoundPool soundPool, int sampleId,
					int status) {
				// TODO Auto-generated method stub
				if(0 == status){
					playButton.setEnabled(true);
				}
				
			}
    		
    	});
    	playButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(mCanPlayAudio){
					mSoundPool.play(mSoundId, (float)mVolume / mVolumeMax, (float) mVolume / mVolumeMax, 1, 0, 1.0f);
				}
			}
    		
    	});
    	int result = mAudioManager.requestAudioFocus(afChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
    	mCanPlayAudio = AudioManager.AUDIOFOCUS_REQUEST_GRANTED == result;
    }
    
    @Override
    protected void onResume(){
    	super.onResume();
    	mAudioManager.setSpeakerphoneOn(true);
    	mAudioManager.loadSoundEffects();
    }
    
	@Override
	protected void onPause() {
		if(null != mSoundPool){
			mSoundPool.unload(mSoundId);
			mSoundPool.release();
			mSoundPool = null;
		}
		mAudioManager.setSpeakerphoneOn(false);
		mAudioManager.unloadSoundEffects();
		super.onPause();
	}

	OnAudioFocusChangeListener afChangeListener = new OnAudioFocusChangeListener(){

		@Override
		public void onAudioFocusChange(int focusChange) {
			// TODO Auto-generated method stub
			if(focusChange == AudioManager.AUDIOFOCUS_LOSS){
				mAudioManager.abandonAudioFocus(afChangeListener);
				mCanPlayAudio = false;
			}
		}
		
	};
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.audio_video_audio_manager, menu);
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

}
