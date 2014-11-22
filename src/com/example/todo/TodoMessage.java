package com.example.todo;

public class TodoMessage {

	int nrOfMsg;
	String title;
	String info;
	int prio = 0;
	
	public TodoMessage(String t, String i) {
		this.title = t;
		this.info = i;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setPrio(int i) {
		prio = i;
	}
	
	public int getPrio() {
		return prio;
	}
}
