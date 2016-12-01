package com.example.findrestaurant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Dialog;
import android.app.ExpandableListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SimpleExpandableListAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.findrestaurant.utility.SQLiteFavoriteHelper;

import favorite.FavoriteModifyActivity;

public class MainActivity extends ExpandableListActivity{
	private TextView tvMenu1,tvMenu2;			//indicator in head
	private ViewFlipper flipperMenu;
	private int flipperPages[] = {R.id.menuFrame1,R.id.menuFrame2};
	private ExpandableListView expListView;
	private Spinner spCatagory;

	private ArrayList<String> ListCatagory;
	private ArrayList<HashMap<String, String>> ListCity;
	private ArrayList<ArrayList<HashMap<String,String>>> ListRest;
	private String strCatagory;

	private ExpandableListAdapter expAdapter;
	private ArrayAdapter arrayAdapter;

	private SQLiteFavoriteHelper dbHelper;
	private SQLiteDatabase db;
	
	private Dialog dialogAction;
	private int iAction;
	private final int MODIFYCALL = 1051;
	private Map<String, String> mapChild;

	private MyHandler handler;
	private int groupPos,childPos;
	
	private GestureDetector gd;
	private int[] flipPages;
	private View[] flipViews;
	private int PageNo;
	
	private RelativeLayout mainPage,r1;
	private LinearLayout r2;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		handler = new MyHandler();

		gd = new GestureDetector(this,new MyGestureListener());

		r1 = (RelativeLayout)findViewById(R.id.menuFrame1);
		r2 = (LinearLayout)findViewById(R.id.menuFrame2);
		dbHelper = new SQLiteFavoriteHelper(this, "findrestaurant", null, 1);
		db = dbHelper.getWritableDatabase();	//use writable to avoid hanging there

		tvMenu1 = (TextView)findViewById(R.id.tvMenu1);
		tvMenu2 = (TextView)findViewById(R.id.tvMenu2);
		flipperMenu = (ViewFlipper)findViewById(R.id.flipperMenu);
		expListView = (ExpandableListView)findViewById(android.R.id.list);
		spCatagory = (Spinner)findViewById(R.id.menuListCatagory);

		
		flipPages = new int[]{R.id.menuFrame1,R.id.menuFrame2};
		flipViews = new View[flipPages.length];
		for(int i=0 ; i < flipPages.length ; i++){
			flipViews[i] = (View)findViewById(flipPages[i]);
		}
		PageNo = 0;			//Default Page 0;

//		mainPage.setOnTouchListener(new MyTouchListener());
		r1.setOnTouchListener(new MyTouchListener());
		r2.setOnTouchListener(new MyTouchListener());

		prepareCatagory();
		handler.sendEmptyMessage(0);	//set spinner
		spCatagory.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				strCatagory = ListCatagory.get(arg2).toString();
				prepareRest();
				handler.sendEmptyMessage(1);	//set expandable list view
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
		});

		expListView.setOnTouchListener(new MyTouchListener());
		expListView.setOnChildClickListener(new MyExpListChildClick());
		expListView.setOnItemLongClickListener(new MyExpListChildLongClick());
	}

	//for gd to determine next action
	@Override
	public boolean onTouchEvent(MotionEvent event) {
//		return super.onTouchEvent(event);
		return gd.onTouchEvent(event);
	}

	public void menu1(View v){
		Log.i("lucas", "page1/"+PageNo);
		if(PageNo % flipViews.length != 0){
			for(int i = PageNo ; i%flipViews.length != 0 ; i++){
				flipperMenu.showPrevious();
			}
			PageNo = 0;
		}
		
	}

	public void menu2(View v){
		Log.i("lucas", "page2/"+PageNo);
		if(PageNo % flipViews.length != 1){
			for(int i=PageNo; i % flipViews.length != 1 ; i++){
				flipperMenu.showNext();
			}
			PageNo = 1;
		}
	}

	public void addFavorite(View v){
		Intent it = new Intent(this,FavoriteModifyActivity.class);
		startActivity(it);
	}

	//Page1-search-Google Place API
	public void nearPlace(View v){
		Intent it = new Intent(this, PlaceActivity.class);
		startActivity(it);
	}

	//Page1-search-text and search
	public void location(View v){
		Intent it = new Intent(this,SearchOpionsActivity.class);
		startActivity(it);
	}

	private void prepareCatagory(){
		Cursor c = db.query(true, "userfavorite",new String[]{"uf_catagory"},
				            "",null, "", "", "uf_catagory", "");
		ListCatagory = new ArrayList<String>();
		ListCatagory.add(getString(R.string.AllCatagory));
		while(c.moveToNext()){
			Log.i("lucas","in cursor");
			ListCatagory.add(c.getString(0));
		}
		strCatagory = ListCatagory.get(0);	//pick first for default;
		prepareRest();
	}

	private void prepareRest(){
		Cursor c1;
		Cursor c2;
		String city,names,phones;
		HashMap<String,String> map;
		ArrayList<HashMap<String,String>> listMap;
		
		if(strCatagory.equals(getString(R.string.AllCatagory))){
			c1 = db.query(true, "userfavorite", new String[]{"uf_city"},
							"uf_catagory LIKE '%'",null, "", "", "", "");
		}else{
			c1 = db.query(true, "userfavorite",new String[]{"uf_city"},
							"uf_catagory = ?",new String[]{strCatagory}, "", "", "","");
		}

		ListCity = new ArrayList<HashMap<String,String>>();
		ListRest = new ArrayList<ArrayList<HashMap<String,String>>>();
		while(c1.moveToNext()){

			city = c1.getString(c1.getColumnIndex("uf_city"));
			if(strCatagory.equals(getString(R.string.AllCatagory))){
				c2 = db.query(true,"userfavorite", new String[]{"uf_key","uf_name","uf_phone"},
								"uf_catagory LIKE '%' AND uf_city = ?", new String[]{city}, "",
								"", "uf_name","");
			}else{
				c2 = db.query(true,"userfavorite", new String[]{"uf_key","uf_name","uf_phone"},
								"uf_catagory = ? AND uf_city = ?", new String[]{strCatagory,city},
								"", "", "uf_name","");
			}

			//first level of expListView
			map = new HashMap<String, String>();
			map.put("city", city);
			ListCity.add(map);

			listMap = new ArrayList<HashMap<String,String>>();
			while(c2.moveToNext()){
				map = new HashMap<String, String>();
				map.put("key"  ,c2.getString(c2.getColumnIndex("uf_key")));
				map.put("name" ,c2.getString(c2.getColumnIndex("uf_name")));
				map.put("phone",c2.getString(c2.getColumnIndex("uf_phone")));
				listMap.add(map);
			}
			ListRest.add(listMap);
		}
	}


	private void setSpCatagory(){
		arrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, ListCatagory);
		spCatagory.setAdapter(arrayAdapter);
	}

	private void setExpListView(){
		int[] parentTo,childTo;
		String[] parentFrom,childFrom;

		parentTo = new int[]{R.id.epxListParent1};
		childTo = new int[]{R.id.expListchild1,R.id.expListchild2};
		parentFrom = new String[]{"city"};
		childFrom = new String[]{"name","phone"};
//		new SimpleExpandableListAdapter(context, groupData, groupLayout, groupFrom, groupTo, childData, childLayout, childFrom, childTo)
		Log.i("lucas",ListCity.size() + ":" + ListRest.size());
		expAdapter = new SimpleExpandableListAdapter(this,
													 ListCity, R.layout.explistparent,
				                                     parentFrom, parentTo,
				                                     ListRest, R.layout.explistchild,
				                                     childFrom, childTo);
		setListAdapter(expAdapter);
	}

	private void actionDetail(Map<String,String> mapData){
		Intent it = new Intent(MainActivity.this,
								SinglePlaceActivitySql.class);
		Bundle extras = new Bundle();
		extras.putInt("key", Integer.parseInt(mapData.get("key")));
		it.putExtras(extras);
		startActivity(it);
		dialogAction.dismiss();
	}

	private void actionModify(Map<String,String> mapData){
		Intent it = new Intent(MainActivity.this,
				FavoriteModifyActivity.class);
		Bundle extras = new Bundle();
		extras.putInt("key", Integer.parseInt(mapData.get("key")));
		it.putExtras(extras);
		startActivityForResult(it, MODIFYCALL);
		dialogAction.dismiss();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
			case MODIFYCALL:
					prepareCatagory();
					handler.sendEmptyMessage(0);	//set spinner	
				break;
			default:
				break;
		}
	}

	private boolean actionDelete(Map<String,String> mapData){
		int rows = db.delete("userfavorite", "uf_key = ?", new String[]{mapData.get("key")});
		if(rows == 0){
			Toast.makeText(MainActivity.this, getString(R.string.DeleteFailed), Toast.LENGTH_SHORT).show();
			return false;
		}
		dialogAction.dismiss();
		return true;
	}

	//Long click/ 3 action:Show/Modify/Delete
	private void longCLickDialog(){
		dialogAction = new Dialog(this);
		dialogAction.setTitle(getString(R.string.ActSelection));
		dialogAction.setCancelable(true);
		dialogAction.setContentView(R.layout.dialogfavorite);
		TextView tvInfo	  = (TextView)dialogAction.findViewById(R.id.listDlgInfo);
		TextView tvUpdate = (TextView)dialogAction.findViewById(R.id.listDlgUpdate);
		TextView tvDelete = (TextView)dialogAction.findViewById(R.id.listDlgDelete);

		mapChild = ListRest.get(groupPos).get(childPos);

		tvInfo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				actionDetail(mapChild);
			}
		});

		tvUpdate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				actionModify(mapChild);
			}
		});

		tvDelete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(actionDelete(mapChild)){
					ListRest.get(groupPos).remove(childPos);
					handler.sendEmptyMessage(1);
				}
			}
		});
		dialogAction.show();
	}

	private class MyHandler extends Handler{
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch(msg.what){
				case 0:
					setSpCatagory();
					break;
				case 1:
					setExpListView();
					break;
			}
		}
	}

	private class MyGestureListener extends GestureDetector.SimpleOnGestureListener{


		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
//			Log.i("lucas", ""+velocityX);
			Log.i("lucas",e1.getX()+":"+e2.getX());
			if((e1.getX() - e2.getX()) > 150){
				Log.i("lucas","to previous");
				flipperMenu.showNext();
				Log.i("lucas",PageNo+">>>>>>>");
				PageNo = (PageNo+1)%flipViews.length;
				Log.i("lucas",PageNo+">>>"+flipperPages.length);
			}else if((e1.getX() - e2.getX()) < -150){
				Log.i("lucas","to next");
				flipperMenu.showPrevious();
				Log.i("lucas",PageNo+"<<<<<<<");
				PageNo = (PageNo-1)%flipViews.length;
				Log.i("lucas",PageNo+"<<<"+flipperPages.length);
			}
			return super.onFling(e1, e2, velocityX, velocityY);
		}
	}

	private class MyTouchListener implements View.OnTouchListener{

		@Override
		public boolean onTouch(View v, MotionEvent event) {
//			Log.i("lucas","my touch on "+v.toString());
//			return false;

			return gd.onTouchEvent(event);
		}
	}

	private class MyExpListChildClick implements ExpandableListView.OnChildClickListener{

		@Override
		public boolean onChildClick(ExpandableListView parent, View v,
				int groupPosition, int childPosition, long id) {
			mapChild = ListRest.get(groupPosition).get(childPosition);
			Intent it = new Intent(MainActivity.this,
									SinglePlaceActivitySql.class);
			Bundle extras = new Bundle();
			extras.putInt("key", Integer.parseInt(mapChild.get("key")));
			it.putExtras(extras);
			startActivity(it);
			return false;
		}
		
	}

	private class MyExpListChildLongClick implements OnItemLongClickListener{

		@Override
		public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
				int arg2, long arg3) {
			Log.i("lucas","in long click");
			groupPos = ExpandableListView.getPackedPositionGroup(arg3);
			childPos = ExpandableListView.getPackedPositionChild(arg3);
			//arg2:position of ? arg3 id if of the clicked item
			int longClickOn = ExpandableListView.getPackedPositionType(arg3);
			if(longClickOn == ExpandableListView.PACKED_POSITION_TYPE_CHILD){
				longCLickDialog();
				return true;
			}else if(longClickOn == ExpandableListView.PACKED_POSITION_TYPE_GROUP){
				//nothing to do right now
				return false;
			}

			return false;
		}
		
	}
}
