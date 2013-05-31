package com.utt.scd.model;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.Build;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class Connection 
{

	public Gson gson;
	
	private static Connection instance;
	
	private boolean isInitialized = false;	
	
	
	private Connection() 
	{
		super();
	}
	
	public final static Connection getInstance() 
	{
		if (Connection.instance == null) 
		{
			synchronized (Connection.class) 
			{
				if (Connection.instance == null) 
				{
					Connection.instance = new Connection();
				}
			}
		}

		return Connection.instance;
	}
	
	public Connection initialize()
	{
		
		GsonBuilder gsonBuilder = new GsonBuilder();

		// TODO : Handle timezones properly
		this.gson = gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
		
		
		this.isInitialized = true;
		

		return (Connection.getInstance());
		
		
	}
	
	
	
	//******************************* RECHERCHE ***************************************//
	public List<ParseObject> rechercheSimple(String chaine) throws ConnectionNotInitializedException, ParseException
	{
		ParseQuery query = new ParseQuery("Livre");
		query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
		
		query.whereMatches("Titre", regexRecherche(chaine));
		
		if (!this.isInitialized) 
		{
			throw new ConnectionNotInitializedException("Connection has not been initialized");
		}
		else
		{
			return query.find();
		}
	}
	
	public List<ParseObject> rechercheAvancee(String titre, String auteur, String support, String langue, String uv, String domaine) throws ConnectionNotInitializedException, ParseException
	{
		
		ParseQuery query = new ParseQuery("Livre");
		query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
		
		System.out.println(titre + auteur + support + langue + uv + domaine);
		
		if (!titre.equals(""))
		{
			query.whereMatches("Titre", regexRecherche(titre));
			System.out.println(regexRecherche(titre));
		}
		if (!auteur.equals(""))
		{
			query.whereMatches("Auteur", regexRecherche(auteur));
		}
		if (!support.equals(""))
		{
			if (!support.equals("Tous"))
			{
				ParseQuery subQuery = new ParseQuery("Exemplaire");
				subQuery.whereMatches("support", regexRecherche(support));

				query.whereMatchesQuery("has", subQuery);
			}
		}
		if (!langue.equals(""))
		{
			if (!langue.equals("Toutes"))
			{
				System.out.println(regexRecherche(langue));
				query.whereMatches("Langue", regexRecherche(langue));
			}
			
		}
		if (!uv.equals(""))
		{
			query.whereMatches("UV", regexRecherche(uv));
		}
		if (!domaine.equals(""))
		{
			query.whereMatches("Domaine", regexRecherche(domaine));
		}
		
		
		if (!this.isInitialized) 
		{
			throw new ConnectionNotInitializedException("Connection has not been initialized");
		}
		else
		{
			return query.find();
		}
	}
	
	
	public String regexRecherche(String chaine)
	{
		String[] mots = chaine.split(" ");
		String regex="";
		
		regex += ".*";
		for (int i = 0 ; i < mots.length ; i++)
		{
			
			for (int j = 0 ; j < mots[i].length() ; j++)
			{
				regex += "[";
				if (Character.isUpperCase(mots[i].charAt(j)))
				{
					regex += Character.toString(mots[i].charAt(j)) + Character.toString(Character.toLowerCase(mots[i].charAt(j)));
				}
				else if (Character.isLowerCase(mots[i].charAt(j)))
				{
					regex += Character.toString(mots[i].charAt(j)) + Character.toString(Character.toUpperCase(mots[i].charAt(j)));
				}
				else
				{
					regex += Character.toString(mots[i].charAt(j));
				}
				regex += "]";
			}
			if(i != mots.length)
			{
				regex += ".*";
			}
			
		}

		return regex;
	}
	
	//******************************* RECUPERER LES EXEMPLAIRES D'UN LIVRE ***************************************//
	
	public List<ParseObject> recupererExemplaire(ParseObject livre) throws ConnectionNotInitializedException, ParseException
	{
		ParseQuery query = new ParseQuery("Exemplaire");
		query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
		
		query.whereEqualTo("exemplaire_de", livre);

		if (!this.isInitialized) 
		{
			throw new ConnectionNotInitializedException("Connection has not been initialized");
		}
		else
		{
			return query.find();
		}
	}
	
	public List<ParseObject> recupererExemplaire(String id_livre) throws ConnectionNotInitializedException, ParseException
	{
		ParseQuery query = new ParseQuery("Exemplaire");
		query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
		
		ParseQuery subQuery = new ParseQuery("Livre");
		subQuery.whereMatches("objectId", id_livre);

		query.whereMatchesQuery("exemplaire_de", subQuery);
		
		
		if (!this.isInitialized) 
		{
			throw new ConnectionNotInitializedException("Connection has not been initialized");
		}
		else
		{
			return query.find();
		}
	}
	
	
	
	
	//******************************* RECUPERER LES PERIODIQUES ***************************************//
	
	public List<ParseObject> recupererPeriodique() throws ConnectionNotInitializedException, ParseException
	{
		ParseQuery query = new ParseQuery("Periodique");
		query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);

		if (!this.isInitialized) 
		{
			throw new ConnectionNotInitializedException("Connection has not been initialized");
		}
		else
		{
			return query.find();
		}
	}
	
	
	//******************************* RECUPERER LES EVENEMENTS/ACTIVITES ***************************************//
	
		public List<ParseObject> recupererEvenement() throws ConnectionNotInitializedException, ParseException
		{
			ParseQuery query = new ParseQuery("Evenement");
			query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);

			if (!this.isInitialized) 
			{
				throw new ConnectionNotInitializedException("Connection has not been initialized");
			}
			else
			{
				return query.find();
			}
		}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//******************************* WEB SERVICES ***************************************//
	
	public String doRequest(String httpVerb, String path, String dataToBeSent) throws IOException, ConnectionNotInitializedException 
	{

		if (!isInitialized) 
		{
			throw new ConnectionNotInitializedException("Connection has not been initialized");
		}
		else if ( (dataToBeSent == null || dataToBeSent.equals("")) && httpVerb.equals(HttpVerb.PUT) )
		{
			throw new IllegalArgumentException("Cannot sent a request PUT with empty body");
		}
		else
		{
			if (Build.VERSION.SDK_INT == Build.VERSION_CODES.FROYO) 
			{
				
		        System.setProperty("http.keepAlive", "false");
		        System.out.println("Android version <= 2.2");
		        
		        if (httpVerb.equals(HttpVerb.GET))
		        {
		        	HttpClient http_client = new DefaultHttpClient();
		        	
			        HttpGet http_get = new HttpGet(path + "?" + dataToBeSent);
			        
			        http_get.setHeader("X-Parse-Application-Id", API.X_Parse_Application_Id);
			        http_get.setHeader("X-Parse-REST-API-Key", API.X_Parse_REST_API_Key);
			        http_get.setHeader("X-Parse-Master-Key", API.X_Parse_Master_Key);
			        http_get.setHeader("Accept", "application/json");


			        
			        HttpResponse response = http_client.execute(http_get);
			        //System.out.println(response);
					InputStream content = response.getEntity().getContent();
					
					BufferedReader responseReader = new BufferedReader(new InputStreamReader(content));
					
					StringBuilder responseBuilder = new StringBuilder();
					String line = null;
					
					while ((line = responseReader.readLine()) != null) 
					{
						responseBuilder.append(line);
					}
					//System.out.println("Return get : " + responseBuilder.toString());
					
					return (responseBuilder.toString());
					
		        }
		        else if (httpVerb.equals(HttpVerb.PUT))
		        {

		        	HttpClient http_client = new DefaultHttpClient();
		        	
			        HttpPut http_put = new HttpPut(path);
			        
			        http_put.setHeader("X-Parse-Application-Id", API.X_Parse_Application_Id);
			        http_put.setHeader("X-Parse-REST-API-Key", API.X_Parse_REST_API_Key);
			        http_put.setHeader("X-Parse-Master-Key", API.X_Parse_Master_Key);
			        http_put.setHeader("Accept", "application/json");

					StringEntity s = new StringEntity(dataToBeSent);
					s.setContentType("application/json");
					http_put.setEntity(s);


					
					HttpResponse response = http_client.execute(http_put);
					InputStream content = response.getEntity().getContent();
					
					BufferedReader responseReader = new BufferedReader(new InputStreamReader(content));
					
					StringBuilder responseBuilder = new StringBuilder();
					String line = null;
					
					while ((line = responseReader.readLine()) != null) 
					{
						responseBuilder.append(line);
					}
					//System.out.println("Return post: " + responseBuilder.toString());
				
					return (responseBuilder.toString());
		        }
		        

		        return null;
		    }
			
			else
			{
				URL requestURL ;
				
				if (httpVerb == HttpVerb.GET)
				{
					requestURL = new URL(path + "?" + dataToBeSent);
					System.out.println("hehe : " + path + "?" + dataToBeSent);
				}
				else
				{
					requestURL = new URL(path);
				}

				HttpURLConnection backendConnection = (HttpURLConnection) requestURL.openConnection();

				backendConnection.setRequestProperty("X-Parse-Application-Id", API.X_Parse_Application_Id);
				backendConnection.setRequestProperty("X-Parse-REST-API-Key", API.X_Parse_REST_API_Key);
				backendConnection.setRequestProperty("X-Parse-Master-Key", API.X_Parse_Master_Key);
				backendConnection.setRequestProperty("Accept", "application/json");
				backendConnection.setRequestMethod(httpVerb);

				if (httpVerb == HttpVerb.PUT) 
				{

					backendConnection.setDoOutput(true);
					backendConnection.setRequestProperty("Content-Type", "application/json");

					// Send request
					DataOutputStream dataOutputStream = new DataOutputStream(backendConnection.getOutputStream());
					dataOutputStream.writeBytes(dataToBeSent);
					dataOutputStream.flush();
					dataOutputStream.close();

				}

				
				if (backendConnection.getResponseCode() > 400)
				{
					InputStream errorStream = backendConnection.getErrorStream();
					BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorStream));
					
					StringBuilder errorBuilder = new StringBuilder();
					String line = null;
					
					while ((line = errorReader.readLine()) != null) 
					{
						errorBuilder.append(line);
					}

					//System.out.println("Code d'erreur : " + backendConnection.getResponseCode());
					//System.out.println("Message d'erreur : " + errorBuilder.toString());
					//System.out.println("Return : " + String.valueOf(backendConnection.getResponseCode()) + " " + errorBuilder.toString());
					
					return ("Resulat : " + String.valueOf(backendConnection.getResponseCode()) + " " + errorBuilder.toString());

				}
				
				else
				{
					backendConnection.connect();
					InputStream responseStream = (InputStream) backendConnection.getInputStream();
					BufferedReader responseReader = new BufferedReader(new InputStreamReader(responseStream));

					StringBuilder responseBuilder = new StringBuilder();
					String line = null;
					
					while ((line = responseReader.readLine()) != null) 
					{
						responseBuilder.append(line);
					}
					backendConnection.disconnect();	
					
					//System.out.println("Code de succes :"+backendConnection.getResponseCode());
					//System.out.println("Message de succes :"+responseBuilder.toString());
			
					//System.out.println("Return " + responseBuilder.toString());
					
					return (responseBuilder.toString());
				}
			}
		}
		
		
		
	}

}