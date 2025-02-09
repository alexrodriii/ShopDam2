package model;

import java.text.DecimalFormat;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlValue;


@Embeddable
public class Amount {
	@Column(name = "value", insertable = false, updatable = false)
	private double value;
	@Column(name = "currency", insertable = false, updatable = false)
	private String currency = "€";

	private static final DecimalFormat df = new DecimalFormat("0.00");

	public Amount(double value) {
		super();
		this.value = value;
	}

	public Amount() {

	}

	   @Column(insertable = false, updatable = false) 
	@XmlAttribute(name = "currency")
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@XmlValue
	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return df.format(value) + currency;
	}

}
