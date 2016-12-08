package com.gmail.xiu43317.finger_guessing;

import com.gmail.xiu43317.finger_guessing.R;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	

	ImageView mImgBtnScissor,mImgBtnRock,mImgBtnPaper,end;
	TextView txt,score,ai,player;
	ImageView img1,img2;
	int i,j,k;
	MediaPlayer mMediaPlayer;
	
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			
	        int number = (int)(Math.random()*3);
	        if(number == 1) {
	        	txt.setText("你出石頭,電腦出剪刀,你贏了");
	        	i++;
	        	ai.setText("剪刀");
	        	player.setText("石頭");
	        	score.setText(i+"勝"+j+"負"+k+"和");
	        	img1.setImageResource(R.drawable.scissor);
	        	img2.setImageResource(R.drawable.rock);
	        }
	        else if(number == 2) {
	        	txt.setText("都出石頭,平手");
	        	k++;
	        	ai.setText("石頭");
	        	player.setText("石頭");
	        	score.setText(i+"勝"+j+"負"+k+"和");
	        	img1.setImageResource(R.drawable.rock);
	        	img2.setImageResource(R.drawable.rock);
	        }
	        else {
	        	txt.setText("你出石頭,電腦出布,你輸了");
	        	j++;
	        	ai.setText("布");
	        	player.setText("石頭");
	        	score.setText(i+"勝"+j+"負"+k+"和");
	        	img1.setImageResource(R.drawable.paper);
	        	img2.setImageResource(R.drawable.rock);
	        }
		}
		
	};
	
	private Handler handler2 = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			
	        int number = (int)(Math.random()*3);
	        if(number == 1) {
	        	txt.setText("都出剪刀,平手"); 
	        	k++;
	        	ai.setText("剪刀");
	        	player.setText("剪刀");
	        	score.setText(i+"勝"+j+"負"+k+"和");
	        	img1.setImageResource(R.drawable.scissor);
	        	img2.setImageResource(R.drawable.scissor);
	        }
	        else if(number == 2) {
	        	txt.setText("你出剪刀 ,電腦出石頭,你輸了");
	        	j++;
	        	ai.setText("石頭");
	        	player.setText("剪刀");
	        	score.setText(i+"勝"+j+"負"+k+"和");
	        	img1.setImageResource(R.drawable.rock);
	        	img2.setImageResource(R.drawable.scissor);
	        }
	        else {
	        	txt.setText("你出剪刀,電腦出布,你贏了");
	        	i++;
	        	ai.setText("布");
	        	player.setText("剪刀");
	        	score.setText(i+"勝"+j+"負"+k+"和");
	        	img1.setImageResource(R.drawable.paper);
	        	img2.setImageResource(R.drawable.scissor);
	        }
		}
		
	};
	
	private Handler handler3 = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			
	        int number = (int)(Math.random()*3);
	        if(number == 1) {
	        	txt.setText("你出布,電腦出剪刀,你輸了");
	        	j++;
	        	ai.setText("剪刀");
	        	player.setText("布");
	        	score.setText(i+"勝"+j+"負"+k+"和");
	        	img1.setImageResource(R.drawable.scissor);
	        	img2.setImageResource(R.drawable.paper);
	        }
	        else if(number == 2) {
	        	txt.setText("你出布 ,電腦出石頭,你贏了");
	        	i++;
	        	ai.setText("石頭");
	        	player.setText("布");
	        	score.setText(i+"勝"+j+"負"+k+"和");
	        	img1.setImageResource(R.drawable.rock);
	        	img2.setImageResource(R.drawable.paper);
	        }
	        else {
	        	txt.setText("都出布,平手");
	        	k++;
	        	ai.setText("布");
	        	player.setText("布");
	        	score.setText(i+"勝"+j+"負"+k+"和");
	        	img1.setImageResource(R.drawable.paper);
	        	img2.setImageResource(R.drawable.paper);
	        }
		}
		
	};
	
	protected void play(){
		
		mMediaPlayer = new MediaPlayer();
		Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.firealarm);
		try{
			mMediaPlayer.setDataSource(this,uri);
			mMediaPlayer.prepare();
			mMediaPlayer.start();
		}catch(Exception e){
			e.printStackTrace();
			Toast.makeText(MainActivity.this, "指定音樂錯誤", Toast.LENGTH_LONG).show();
		}
		mMediaPlayer.setOnCompletionListener(new OnCompletionListener() {
			
			@Override
			public void onCompletion(MediaPlayer mp) {
				// TODO Auto-generated method stub
				mp.release();
			}
		});
	
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		

		txt = (TextView) findViewById(R.id.textView1);
		score = (TextView) findViewById(R.id.textView2);
		ai = (TextView) findViewById(R.id.textView5);
		player = (TextView) findViewById(R.id.textView6);
		img1 = (ImageView) findViewById(R.id.imageView1);
		img2 = (ImageView) findViewById(R.id.imageView2);
		mImgBtnScissor = (ImageView) findViewById(R.id.imageView3);
		mImgBtnRock = (ImageView) findViewById(R.id.imageView4);
		mImgBtnPaper = (ImageView) findViewById(R.id.imageView5);
		end = (ImageView) findViewById(R.id.imageView6);
		
		

				
		mImgBtnScissor.setOnClickListener(btn1OnClick);
		mImgBtnRock.setOnClickListener(btn2OnClick);
		mImgBtnPaper.setOnClickListener(btn3OnClick);
		end.setOnClickListener(done);
		img1.setImageResource(R.drawable.picture1);
		img2.setImageResource(R.drawable.picture1);
	}
	
	private View.OnClickListener done = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			System.exit(0);
		}
	};
	
	private View.OnClickListener btn1OnClick = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
	        Resources res = getResources();
	        final AnimationDrawable animDraw = (AnimationDrawable) res.getDrawable(R.drawable.anim_drawble);
	        final AnimationDrawable animDraw2 = (AnimationDrawable) res.getDrawable(R.drawable.anim_drawble);
	        img1.setImageDrawable(animDraw);
	        img2.setImageDrawable(animDraw2);
	        animDraw.start();
	        animDraw2.start();
	        play();
	        
	       
	        
	        new Thread(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					try{						
						Thread.sleep(2000);
					}catch(Exception e){
						e.printStackTrace();
					}
					animDraw.stop();
					animDraw2.stop();				
					handler2.sendMessage(handler2.obtainMessage());
				}
	        	
	        }).start();        
		}
	};
	
	private View.OnClickListener btn2OnClick = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

	        Resources res = getResources();
	        final AnimationDrawable animDraw = (AnimationDrawable) res.getDrawable(R.drawable.anim_drawble);
	        final AnimationDrawable animDraw2 = (AnimationDrawable) res.getDrawable(R.drawable.anim_drawble);
	        img1.setImageDrawable(animDraw);
	        img2.setImageDrawable(animDraw2);
	        animDraw.start();
	        animDraw2.start();
	        play();
	        
	        new Thread(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					try{
						Thread.sleep(2000);
					}catch(Exception e){
						e.printStackTrace();
					}
					animDraw.stop();
					animDraw2.stop();
					handler.sendMessage(handler.obtainMessage());
				}
	        	
	        }).start();

		}
	};
	
	private View.OnClickListener btn3OnClick = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
	        Resources res = getResources();
	        final AnimationDrawable animDraw = (AnimationDrawable) res.getDrawable(R.drawable.anim_drawble);
	        final AnimationDrawable animDraw2 = (AnimationDrawable) res.getDrawable(R.drawable.anim_drawble);
	        img1.setImageDrawable(animDraw);
	        img2.setImageDrawable(animDraw2);
	        animDraw.start();
	        animDraw2.start();
	        play();
	        
	        new Thread(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					try{
						Thread.sleep(2000);
					}catch(Exception e){
						e.printStackTrace();
					}
					animDraw.stop();
					animDraw2.stop();				
					handler3.sendMessage(handler3.obtainMessage());
				}
	        	
	        }).start();			
		}
	};
}
