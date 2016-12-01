package com.example.findrestaurant.utility;

import java.io.Serializable;

import com.example.findrestaurant.Place;

public class MyPlace extends Place implements Serializable{
	public String Note;
	public Double lat,lng;
}
