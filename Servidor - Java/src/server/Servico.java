package server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class Servico {
	public static float newId; 
	
	
	public Servico() {
		File file = new File("src/util/nextId.txt");
		try {
			FileReader r = new FileReader(file);
			BufferedReader br = new BufferedReader(r);
			String nextId = br.readLine();
			Float nextIdNum = Float.parseFloat(nextId);
			this.newId = nextIdNum;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void save(String recebido) {	
		String[] novasNoticias = recebido.split("\\+");
		JSONObject json = new JSONObject();
		FileWriter filewriter = null;
		float novoId = newId;
		json.put("id", novoId);
		json.put("titulo", novasNoticias[0]);
		json.put("texto", novasNoticias[1]);	
		
		//incrementando ID
	    File file = new File("src/util/nextId.txt");
	    try {
			filewriter = new FileWriter(file.getPath(), false);
			float f = novoId+1;
			newId = f;
			String escrit = String.valueOf(f);
			filewriter.write(escrit);
			filewriter.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    
	    file = new File("src/json/noticias.json");
		try {
			filewriter = new FileWriter(file.getPath(), true);
			filewriter.write(json.toJSONString() + "\n");
			filewriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<JSONObject> readNews() throws FileNotFoundException {
		File file = new File("src/json/noticias.json");
		FileReader fileread = new FileReader(file);
		BufferedReader br = new BufferedReader(fileread);
		String result = new String("");
		String linha;
		JSONParser parser = new JSONParser();
		List<JSONObject> jsons = new ArrayList<JSONObject>();
		try {
			String prox = br.readLine();
			while(prox != null) {		
				jsons.add((JSONObject) parser.parse(prox));
				prox = br.readLine();
			}
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsons;
	}
}
