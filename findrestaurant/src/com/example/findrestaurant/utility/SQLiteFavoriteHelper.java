package com.example.findrestaurant.utility;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteFavoriteHelper extends SQLiteOpenHelper{
	private String newTable =
			"CREATE TABLE IF NOT EXISTS userfavorite("+
			"uf_key INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL,"+
			"uf_catagory TEXT NOT NULL,"+
			"uf_name TEXT NOT NULL,"+
			"uf_phone TEXT,"+
			"uf_address TEXT,"+
			"uf_city TEXT NOT NULL,"+
			"uf_note TEXT,"+
			"uf_latitude  REAL,"+
			"uf_longitude REAL,"+
			"uf_placeid TEXT)";

	public SQLiteFavoriteHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(newTable);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

	
}
