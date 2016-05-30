package com.giologic.pedrosystem10.model;

import com.giologic.pedrosystem10.util.ImageUtils;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@DatabaseTable(tableName = "Organization")
public class Organization {
	@DatabaseField(id=true)
	private int id;
	@DatabaseField
	private String name;
	@DatabaseField
	private String description;
	@DatabaseField
	private String email;
	@DatabaseField
	private String location;
    @ForeignCollectionField(eager = true)
	private Collection<Post> posts;
	@DatabaseField
	private String logo;
	@DatabaseField
	private String color;
	
	protected Organization(){}

	public Organization(String name) {
		super();
		this.name = name;
		posts = new ArrayList<Post>();
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getEmail() {
		return email;
	}

	public String getLocation() {
		return location;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return "Organization [id=" + id + ", name=" + name + ", description="
				+ description + ", email=" + email + ", location=" + location
				+ "]";
	}
	
	public Collection<Post> getPosts(){
		return new ArrayList<Post>(posts);
	}
	
	public void addPost(Post p){
		posts.add(p);
		p.setOrg(this);
	}
	
	public String getLogo(){
		return logo;
	}
	
	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	
	
}
