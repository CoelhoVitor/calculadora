package br.com.vod3r.calc.modelo;

import java.util.ArrayList;
import java.util.List;

public class Memoria {
	
	private enum TipoComando {
			ZERAR, NUMERO, DIV, MULT, SUB, SOMA, IGUAL, VIRGULA
	};

	private static final Memoria instancia = new Memoria();
	
	private final List<MemoriaObservador> observadores = new ArrayList<>();
	
	private String textoAtual = "";
	
	public Memoria() {
		
	}

	public static Memoria getInstancia() {
		return instancia;
	}
	
	public void adicionarObservador(MemoriaObservador observador) {
		observadores.add(observador);
	}

	public String getTextoAtual() {
		return textoAtual.isEmpty() ? "0" : textoAtual;
	}
	
	public void processarComando(String texto) {
		
		TipoComando tipoComando = detectarTipoComando(texto);
		
		textoAtual += texto;		
		observadores.forEach(o -> o.valorAlterado(getTextoAtual()));
	}

	private TipoComando detectarTipoComando(String texto) {
		
		if (textoAtual.isEmpty() && texto == "0") {
			return null;
		}
		
		try {
			Integer.parseInt(texto);
			return TipoComando.NUMERO;
		} catch (NumberFormatException e) {
			// Quando não for número
			if ("AC".equals(texto)) {
				return TipoComando.ZERAR;
			} 
			
			if ("/".equals(texto)) {
				return TipoComando.DIV;
			} 
			
			if ("*".equals(texto)) {
				return TipoComando.MULT;
			} 
			
			if ("+".equals(texto)) {
				return TipoComando.SOMA;
			} 
			
			if ("-".equals(texto)) {
				return TipoComando.SUB;
			} 
			
			if ("=".equals(texto)) {
				return TipoComando.IGUAL;
			} 
			
			if (",".equals(texto)) {
				return TipoComando.VIRGULA;
			} 
		}
		
		return null;
	}
	
}
