package server;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;

public class Despachante{
	private Servente serv;
	
	public Despachante() {
		this.serv = new Servente();
	}
	
	public List<JSONObject> exe(Long methodId, String recebido) throws FileNotFoundException {
		if(methodId == 0.0) {
			return serv.readNews();
		}
		if(methodId == 1.0) {
			
			serv.writeNews(recebido);
		}
		JSONObject resposta = new JSONObject();
		resposta.put("arguments", "Nova Notícia Salva com Sucesso");
		List<JSONObject> jsons = new ArrayList<JSONObject>();
		jsons.add(resposta);
		return jsons;
	}
}


