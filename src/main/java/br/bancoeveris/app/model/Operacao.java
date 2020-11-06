package br.bancoeveris.app.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Operacao {
	
	@Id
	private Long Id;
	private String Tipo;
	private double Valor;
	
	
	@ManyToOne
    @JoinColumn (name ="IdContaOrigem")
    private Conta ContaO;
	
	@ManyToOne
    @JoinColumn (name ="IdContaDestino")
    private Conta ContaD;

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getTipo() {
		return Tipo;
	}

	public void setTipo(String tipo) {
		Tipo = tipo;
	}

	public double getValor() {
		return Valor;
	}

	public void setValor(double valor) {
		Valor = valor;
	}

	public Conta getContaO() {
		return ContaO;
	}

	public void setContaO(Conta contaO) {
		ContaO = contaO;
	}

	public Conta getContaD() {
		return ContaD;
	}

	public void setContaD(Conta contaD) {
		ContaD = contaD;
	}
	
}
