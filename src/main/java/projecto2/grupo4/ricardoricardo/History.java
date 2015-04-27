package projecto2.grupo4.ricardoricardo;

import java.util.ArrayList;

public class History {
	private ArrayList<String> history;

	public ArrayList<String> getHistory() {
		return history;
	}

	public void setHistory(ArrayList<String> history) {
		this.history = history;
	}
	
	public void addToHistory(String e) {
		history.add(e);
	}

}
