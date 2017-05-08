
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.internal.$Gson$Types;
import java.lang.reflect.Type;

public class MainDirectory {
	private ArrayList<Racer> list = new ArrayList<Racer>();

	public void add(Collection<Racer> e) {
		this.list.addAll(e);
	}

	public void print() {
		if (list.isEmpty()) {
			System.out.println("<empty directory>");
		} else {
			for (Racer e : list) {
				System.out.printf("%s, %s %s %s\n", e.place, e.bib, e.name, e.time);
			}
		}
	}
	
	public String toString(){
		String out = "";
		if (list.isEmpty()) {
			out = "<empty directory>\n";
		} else {
			for (Racer e : list) {
				out += String.format("%s, %s %s %s\n", e.place, e.bib, e.name, e.time);
			}
		}
		return out;
	}
	
	public String toTableRow(){
		String out = "";
		Collections.sort(list);
		for (Racer e : list) {
			out += "<tr><td>"+ e.place + "</td><td>"+ e.bib + "</td><td>" + e.name + "</td><td>" + e.time + "</td></tr>" ;
		}
		
		return out;
	}

	public void clear() {
		list.clear();
	}

	@SuppressWarnings("unchecked")
	public void receive(String json) {
		Gson gson = new Gson();
		Type collectionType = new TypeToken<Collection<Racer>>() {
		}.getType();
		this.add((Collection<Racer>) gson.fromJson(json, collectionType));

		// Not sure if this (and the sort method in general goes here or
		// if the list is sorted before it is given to us.
		sort();
	}

	// See employee class for comparator
	public void sort() {
		Collections.sort(list);

	}

}
