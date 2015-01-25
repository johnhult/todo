package com.sunrise.todo;

import java.util.Comparator;

public class TodoPrioComparator implements Comparator<TodoMessage> {

	@Override
	public int compare(TodoMessage lhs, TodoMessage rhs) {
		return lhs.getPrio() - rhs.getPrio();
	}
		
}
