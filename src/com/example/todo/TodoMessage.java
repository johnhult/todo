package com.example.todo;

public class TodoMessage {

	private int id;
	private String title;
	private String info;
	private int prio;
	
	public TodoMessage(String t, String i, int p) {
		this.title = t;
		this.info = i;
		this.prio = p;
	}
	
	public int getId() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getInfo() {
		return info;
	}
	
	
	public int getPrio() {
		return prio;
	}
	
	public void setId(int i) {
		this.id = i;
	}
		
	public void setTitle(String t) {
		this.title = t;
	}
	
	public void setInfo(String i) {
		this.info = i;
	}
	
	public void setPrio(int i) {
		prio = i;
	}
	
}
