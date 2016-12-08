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
	        	txt.setText("�A�X���Y,�q���X�ŤM,�AĹ�F");
	        	i++;
	        	ai.setText("�ŤM");
	        	player.setText("���Y");
	        	score.setText(i+"��"+j+"�t"+k+"�M");
	        	img1.setImageResource(R.drawable.scissor);
	        	img2.setImageResource(R.drawable.rock);
	        }
	        else if(number == 2) {
	        	txt.setText("���X���Y,����");
	        	k++;
	        	ai.setText("���Y");
	        	player.setText("���Y");
	        	score.setText(i+"��"+j+"�t"+k+"�M");
	        	img1.setImageResource(R.drawable.rock);
	        	img2.setImageResource(R.drawable.rock);
	        }
	        else {
	        	txt.setText("�A�X���Y,�q���X��,�A��F");
	        	j++;
	        	ai.setText("��");
	        	player.setText("���Y");
	        	score.setText(i+"��"+j+"�t"+k+"�M");
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
	        	txt.setText("���X�ŤM,����"); 
	        	k++;
	        	ai.setText("�ŤM");
	        	player.setText("�ŤM");
	        	score.setText(i+"��"+j+"�t"+k+"�M");
	        	img1.setImageResource(R.drawable.scissor);
	        	img2.setImageResource(R.drawable.scissor);
	        }
	        else if(number == 2) {
	        	txt.setText("�A�X�ŤM ,�q���X���Y,�A��F");
	        	j++;
	        	ai.setText("���Y");
	        	player.setText("�ŤM");
	        	score.setText(i+"��"+j+"�t"+k+"�M");
	        	img1.setImageResource(R.drawable.rock);
	        	img2.setImageResource(R.drawable.scissor);
	        }
	        else {
	        	txt.setText("�A�X�ŤM,�q���X��,�AĹ�F");
	        	i++;
	        	ai.setText("��");
	        	player.setText("�ŤM");
	        	score.setText(i+"��"+j+"�t"+k+"�M");
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
	        	txt.setText("�A�X��,�q���X�ŤM,�A��F");
	        	j++;
	        	ai.setText("�ŤM");
	        	player.setText("��");
	        	score.setText(i+"��"+j+"�t"+k+"�M");
	        	img1.setImageResource(R.drawable.scissor);
	        	img2.setImageResource(R.drawable.paper);
	        }
	        else if(number == 2) {
	        	txt.setText("�A�X�� ,�q���X���Y,�AĹ�F");
	        	i++;
	        	ai.setText("���Y");
	        	player.setText("��");
	        	score.setText(i+"��"+j+"�t"+k+"�M");
	        	img1.setImageResource(R.drawable.rock);
	        	img2.setImageResource(R.drawable.paper);
	        }
	        else {
	        	txt.setText("���X��,����");
	        	k++;
	        	ai.setText("��");
	        	player.setText("��");
	        	score.setText(i+"��"+j+"�t"+k+"�M");
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
			Toast.makeText(MainActivity.this, "���w���ֿ��~", Toast.LENGTH_LONG).show();
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
