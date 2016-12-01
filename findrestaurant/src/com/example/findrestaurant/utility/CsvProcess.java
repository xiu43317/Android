package com.example.findrestaurant.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.example.findrestaurant.R;

public class CsvProcess {
	private LinkedHashSet<Map<String,String>> areaCodeSets;
	private ArrayList<Map<String,String>> areaCodeLists;
	private Resources res;
	private Context context;

	public CsvProcess(Activity activity) {
		this.context = activity;
	}

	public ArrayList<Map<String,String>> csvToArrayList(){
		String line;
		String[] lineDatas;
		Map<String, String> areaData;

		areaCodeSets = new LinkedHashSet<Map<String,String>>();
		Log.i("lucas","get application-s");
		res = context.getResources();				//right
		Log.i("lucas","get application-E");

		BufferedReader br =
				new BufferedReader(new InputStreamReader(res.openRawResource(R.raw.post)));
		Log.i("lucas","start reading csv");
		try {
			line = br.readLine();
			while((line = br.readLine())!=null){
				areaData = new HashMap<String,String>();
				String[] lineData = line.split(",");
				for(int i = 0;i<lineData.length;i++){
					switch(i){
//						case 0:
//							areaData.put("areacode", lineData[i].trim().substring(0, 3));
//							break;
						case 1:
//							Log.i("lucas",lineData[i]);
							areaData.put("city", lineData[i].trim());
							break;
						case 2:
							areaData.put("dist", lineData[i].trim());
							break;
						case 3:
							areaData.put("road", lineData[i].trim());
							break;
					}
				}
				areaCodeSets.add(areaData);
			}
		} catch (IOException e) {
		}
		areaCodeLists = new ArrayList<Map<String,String>>(areaCodeSets);

		return areaCodeLists;
	}
}
