package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import model.Noticia;
public class TCPServer {
	public static void main (String args[]) {
		try{
			int serverPort = 7896; // the server port
			ServerSocket listenSocket = new ServerSocket(serverPort);
			while(true) {
				Socket clientSocket = listenSocket.accept();
				Connection c = new Connection(clientSocket);
			}
		} catch(IOException e) {System.out.println("Listen socket:"+e.getMessage());}
	}
}

class Connection extends Thread {
	DataInputStream in;
	DataOutputStream out;
	Socket clientSocket;
	public Connection (Socket aClientSocket) {
		try {
			clientSocket = aClientSocket;
			in = new DataInputStream( clientSocket.getInputStream());
			out =new DataOutputStream( clientSocket.getOutputStream());
			this.start();
		} catch(IOException e) {System.out.println("Connection:"+e.getMessage());}
	}
	public void run(){
			Despachante desp = new Despachante();
			String recebido;
			List<JSONObject> jsonsR = null;
			
			//Inicializando variaveis da mensagem recebida
			String requestId = null;
			Long messageType = null;
			Long methodId = null;
			String arguments = null;
			
			while(true) {
				recebido = getRequest();
				JSONParser parser = new JSONParser();
				try {
					JSONObject json = (JSONObject) parser.parse(recebido);
					
					if((Long) json.get("messageType") == 0) {
						requestId = (String) json.get("requestId");
						methodId = (Long) json.get("methodId");
						JSONObject subjson = (JSONObject) json.get("arguments");
						arguments = (String) subjson.get("titulo") + "+" + (String) subjson.get("texto");
						String objRef = (String) json.get("ObjectReference");
						if(objRef.contains("SaveNewsService")){
							jsonsR = doOperation(desp, methodId, arguments);
						}
					}
				} catch (ParseException | FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				JSONObject resposta = new JSONObject();
				resposta.put("requestId", requestId);
				resposta.put("messageType", 1);
				resposta.put("methodId", methodId);
				resposta.put("arguments", jsonsR);
				
				sendReply(resposta);
			}
	}
	
	public String getRequest() {
		try {
//			System.out.println("Ate aqui foi 0.1");
//			byte[] recebido = in.readAllBytes();
//			String retorno = new String(recebido);
//			System.out.println("foi aqui" + retorno + "fechou aqui");
//			return retorno;	
			
			String recebido = in.readLine();
			System.out.println(recebido);
			return recebido;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public void sendReply(JSONObject resposta) {
		try {
			out.write(resposta.toJSONString().getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<JSONObject> doOperation(Despachante RemoteObject, Long methodId, String arguments) throws FileNotFoundException{
		
		return RemoteObject.exe(methodId, arguments);	
	}
}