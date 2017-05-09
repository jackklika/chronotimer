
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
			if (e.dnf == true) e.prettyTime = "DNF";
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

		Collections.sort(list); // See Racer class for comparator
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
