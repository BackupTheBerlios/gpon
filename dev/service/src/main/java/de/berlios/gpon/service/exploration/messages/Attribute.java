package de.berlios.gpon.service.exploration.messages;

public class Attribute {
String name;
String value;
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getValue() {
	return value;
}
public void setValue(String value) {
	this.value = value;
}
public Attribute(String name, String value) {
	super();
	// TODO Auto-generated constructor stub
	this.name = name;
	this.value = value;
}
}
