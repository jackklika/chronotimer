
import java.util.*;
import java.util.stream.Stream;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.internal.$Gson$Types;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class MainDirectory {
	private ArrayList<Racer> list = new ArrayList<Racer>();

	public void add(Collection<Racer> e) {
		this.list.addAll(e);
	}
	
	public String toTableRow(){
		String out = "";
		Collections.sort(list);
		int place = 1;
		for (Racer e : list) {
			try {
				out += "<tr><td>"+ place++ + "</td><td>"+ e.bib + "</td><td>" + nameByBib(e.bib) + "</td><td>" + e.prettyTime + "</td></tr>" ;
			} catch (IOException e1) {
				Main.dbg.printDebug(0, "Problems with I/O");
				e1.printStackTrace();
			}
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
	
	// Returns a string if the bib is found, else empty string.
	public String nameByBib(int bib) throws IOException {
		try {
			BufferedReader br = new BufferedReader(new FileReader("racers.txt"));
			String line;
			while ((line = br.readLine()) != null) {
				String[] temp = line.split(",");
				if (Integer.parseInt(temp[0]) == bib) {
					br.close();
					return temp[1];
				}
			}
			br.close();
			return "";
		} catch (Exception ex){
			return "";
		}
	}

}
