package model;

import java.io.Serializable;

public class Noticia implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id; 
	
	private String titulo;
	private String texto;
	
	
	public Noticia() {}
																																	
	public Noticia(String id, String titulo, String texto) {
		this.id = id;
		this.titulo = titulo;
		this.texto = texto;
	}
	
	public Noticia(String titulo, String texto) {
		this.titulo = titulo;
		this.texto = texto;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String string) {
		this.id = string;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}

	@Override
	public String toString() {
		return "\n" + titulo + "\n" + texto + "\n\n\n";
	} 
	
	

}
