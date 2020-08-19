package de.awacademy.currency_conversion_persistence;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class Wallet {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String pocket;
	
	@OneToMany
	@JoinColumn(name = "wallet_id")
	private List<Money> moneys = new ArrayList<>();

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPocket() {
		return pocket;
	}

	public void setPocket(String pocket) {
		this.pocket = pocket;
	}

	public List<Money> getMoneys() {
		return moneys;
	}

	public void setMoneys(List<Money> moneys) {
		this.moneys = moneys;
	}

}
