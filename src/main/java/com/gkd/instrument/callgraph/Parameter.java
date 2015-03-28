package com.gkd.instrument.callgraph;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "parameter")
public class Parameter {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "parameterId", unique = true, nullable = false)
	private Integer parameterId;

	@ManyToOne
	private JmpData jmpData;

	public String name;
	public String type;

	public Parameter() {
	}

	public Parameter(JmpData jmpData, String name, String type) {
		this.jmpData = jmpData;
		this.name = name;
		this.type = type;
	}

	public String toString() {
		return name + "(" + type + ")";
	}

}
