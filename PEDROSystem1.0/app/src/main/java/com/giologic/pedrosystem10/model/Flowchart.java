package com.giologic.pedrosystem10.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Flowchart")
public class Flowchart {
	@DatabaseField(id = true)
	private int id;
	@DatabaseField
	private String idNumber;
	@DatabaseField
	private String specialization;
	@DatabaseField
	private String filepath;
	
	public Flowchart(String idNumber, String specialization, String filepath) {
		super();
		this.idNumber = idNumber;
		this.specialization = specialization;
		this.filepath = filepath;
	}
	
	protected Flowchart(){}

	public int getId() {
		return id;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public String getSpecialization() {
		return specialization;
	}

	public String getFilepath() {
		return filepath.replace(" ", "%20");
	}
	
	
}
