package com.example.findrestaurant;

import java.util.ArrayList;
import java.util.Map;

import android.app.Application;

public class MyApp extends Application{
	private static ArrayList<Map<String,String>> areaDatas;

	public void insertAreaDatas(ArrayList<Map<String,String>> datas){
		areaDatas = datas;
	}

	public ArrayList<Map<String,String>> getAreaDatas(){
		return areaDatas;
	}
}
