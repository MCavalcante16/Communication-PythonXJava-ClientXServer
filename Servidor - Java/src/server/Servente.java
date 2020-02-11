package server;

import java.io.FileNotFoundException;
import java.util.List;

import org.json.simple.JSONObject;

public class Servente{
	private Servico servico; 
	
	public Servente() {
		this.servico = new Servico();
	}
	
	public List<JSONObject> readNews() throws FileNotFoundException {
		return servico.readNews();
	}

	public void writeNews(String recebido) {
		servico.save(recebido);
	}
	
	
}
