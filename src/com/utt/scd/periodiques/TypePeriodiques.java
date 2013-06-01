package com.utt.scd.periodiques;

import java.util.LinkedList;

import com.parse.ParseObject;

public class TypePeriodiques 
{
	private LinkedList<ParseObject> list;
	private String nom;
	
	public TypePeriodiques(String nom)
	{
		this.nom = nom;
		this.list = new LinkedList<ParseObject>();
	}

	public LinkedList<ParseObject> getList() 
	{
		return list;
	}

	public void setList(LinkedList<ParseObject> list) 
	{
		this.list = list;
	}
	
	
	
	public String getNom() 
	{
		return nom;
	}

	public void setNom(String nom) 
	{
		this.nom = nom;
	}

	public void ajouterElement(ParseObject parseObject)
	{
		this.list.add(parseObject);
	}
	
	
}
