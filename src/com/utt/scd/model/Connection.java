package com.utt.scd.model;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
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
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

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
	
	// Par son objectId
	public ParseObject rechercheLivre(String objectId) throws ConnectionNotInitializedException, ParseException
	{
		ParseQuery query = new ParseQuery("Livre");
		query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
		
		if (!this.isInitialized) 
		{
			throw new ConnectionNotInitializedException("Connection has not been initialized");
		}
		else
		{
			return query.get(objectId);
		}
	}
	
	
	// Recherche avancée
	public List<ParseObject> rechercheAvancee(String titre, String auteur, String support, String langue, String uv, String domaine) throws ConnectionNotInitializedException, ParseException
	{
		
		ParseQuery query = new ParseQuery("Livre");
		query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
		
		
		//System.out.println(titre + auteur + support + langue + uv + domaine);
		
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
				//System.out.println(regexRecherche(langue));
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
		
		query.addDescendingOrder("Titre");
		
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
	
	
	
	//******************************* RECUPERER LIVRE D'UN EXEMPLAIRE *************************************//
	
	// Par exemplaire objet lui-même
	public List<ParseObject> recupererLivreParSonExemplaire(ParseObject exemplaire) throws ConnectionNotInitializedException, ParseException
	{
		ParseRelation relation = exemplaire.getRelation("exemplaire_de");

		if (!this.isInitialized) 
		{
			throw new ConnectionNotInitializedException("Connection has not been initialized");
		}
		else
		{
			ParseQuery query = relation.getQuery();
			query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
			
			return query.find();
		}
		
		
		// Autre façon
		/*ParseQuery query = new ParseQuery("Livre");
		query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
		
		query.whereEqualTo("has", exemplaire);

		if (!this.isInitialized) 
		{
			throw new ConnectionNotInitializedException("Connection has not been initialized");
		}
		else
		{
			//return query.find();
		}*/

	}
	
	
	// Par son objectId
	public List<ParseObject> recupererLivreParSonExemplaire(String id_exemplaire) throws ConnectionNotInitializedException, ParseException
	{
		
		ParseQuery query = new ParseQuery("Livre");
		query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
		
		ParseQuery subQuery = new ParseQuery("Exemplaire");
		subQuery.whereMatches("objectId", id_exemplaire);

		query.whereMatchesQuery("has", subQuery);
		
		
		if (!this.isInitialized) 
		{
			throw new ConnectionNotInitializedException("Connection has not been initialized");
		}
		else
		{
			return query.find();
		}
		
		
		// Autre façon
		
		/*ParseQuery query = new ParseQuery("Exemplaire");
		query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
		
		ParseObject exemplaire = query.get(id_exemplaire);
		
		return recupererLivre(exemplaire);*/

	}
	
	
	
	//******************************* RECUPERER LES EXEMPLAIRES D'UN LIVRE ***************************************//
	
	// Par Objet Livre lui-même
	public List<ParseObject> recupererExemplaireParSonLivre(ParseObject livre) throws ConnectionNotInitializedException, ParseException
	{
		
		ParseRelation relation = livre.getRelation("has");

		if (!this.isInitialized) 
		{
			throw new ConnectionNotInitializedException("Connection has not been initialized");
		}
		else
		{
			ParseQuery query = relation.getQuery();
			query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
			
			return query.find();
		}
		
		
		// Autre façon
		/*ParseQuery query = new ParseQuery("Exemplaire");
		query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
		
		query.whereEqualTo("exemplaire_de", livre);

		if (!this.isInitialized) 
		{
			throw new ConnectionNotInitializedException("Connection has not been initialized");
		}
		else
		{
			return query.find();
		}*/
	}
	
	
	// Par son objectId
	public List<ParseObject> recupererExemplaireParSonLivre(String id_livre) throws ConnectionNotInitializedException, ParseException
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
		
		// Autre façon
		
		/*ParseQuery query = new ParseQuery("Livre");
		query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
				
		ParseObject livre = query.get(id_livre);
				
		return recupererExemplaireParSonLivre(livre);*/
	}
	
	
	
	
	
	//******************************* RECUPERER LES PERIODIQUES ***************************************//
	
	
	// Toutes périodiques
	public List<ParseObject> recupererToutesPeriodiques() throws ConnectionNotInitializedException, ParseException
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
	
	
	// Une périodique quelconque par son objectId
	public ParseObject recupererPeriodique(String objectId) throws ConnectionNotInitializedException, ParseException
	{
		ParseQuery query = new ParseQuery("Periodique");
		query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
		
		if (!this.isInitialized) 
		{
			throw new ConnectionNotInitializedException("Connection has not been initialized");
		}
		else
		{
			return query.get(objectId);
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
	
	
	
	
	
	
	//******************************* UTILISATEUR ***************************************//
	
	
	// Login
	public ParseUser login(String username, String password) throws ParseException, ConnectionNotInitializedException
	{	
		if (!this.isInitialized) 
		{
			throw new ConnectionNotInitializedException("Connection has not been initialized");
		}
		else
		{
			ParseUser user = ParseUser.logIn(username, password);
			
			user.put("is_logging",1); // L'indice qui indique si l'utilisateur est connecté actuellement ou pas
			user.put("installationId", ParseInstallation.getCurrentInstallation().getInstallationId()); // L'indice qui indique sur quel appareil l'utilisateur est connecté (pour envoyer des notifications push)
			
			user.save();
			
			return user;
		}
		
	}
	
	
	// Logout
	public void logout() throws ParseException, ConnectionNotInitializedException
	{	
		if (!this.isInitialized) 
		{
			throw new ConnectionNotInitializedException("Connection has not been initialized");
		}
		else
		{
			ParseUser user = ParseUser.getCurrentUser();
			
			user.put("is_logging",0);
			user.remove("installationId");
		
			user.save();
			
			ParseUser.logOut();
		}
		
	}
	
	
	
	// Ajouter un livre à sa collection, le paramètre contient un array des objectID des livres à ajouter
	
	public void ajouterLivreCollection(ArrayList<String> livresPanier) throws ParseException, ConnectionNotInitializedException
	{
		ParseQuery query = new ParseQuery("Livre");
		query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
		
		if (!this.isInitialized) 
		{
			throw new ConnectionNotInitializedException("Connection has not been initialized");
		}
		else
		{
			for (String objectId : livresPanier)
			{
				ParseObject livre =  query.get(objectId); // parse exeption s'est produit ici, mais normalement on retourne bien un livre
				
				ParseUser user = ParseUser.getCurrentUser();
				
				if (user.getObjectId() != null)
				{
					livre.put("collecte_par", user);
					livre.save(); // parse exception s'est produit ici
				}
			}

		}
	}
	
	
	
	// Récupérer tous les livres dans sa collection
	public List<ParseObject> recupererLivresCollection() throws ConnectionNotInitializedException, ParseException
	{
		ParseUser user = ParseUser.getCurrentUser();

		ParseQuery query = new ParseQuery("Livre");
		query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
		query.whereEqualTo("collecte_par", user);	
		
		if (!this.isInitialized) 
		{
			throw new ConnectionNotInitializedException("Connection has not been initialized");
		}
		else
		{
			return query.find();
		}
	}
	
	
	
	
	
	
	
	
	
	// Etre alerté par l'apparition d'exemplaire d'un livre; le premier paramètre est objectId du livre,  option = 1 pour un exemplaire dispo court, 2 pour dispo long, 0 pour dispo (court + long)
	
	public void notifieExemplaire(String objectId, String option) throws ParseException, ConnectionNotInitializedException
	{
		ParseQuery query = new ParseQuery("Livre");
		query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
		
		if (!this.isInitialized) 
		{
			throw new ConnectionNotInitializedException("Connection has not been initialized");
		}
		else
		{
			ParseObject livre =  query.get(objectId);
			
			ParseUser user = ParseUser.getCurrentUser();
			
			if (user.getObjectId() != null)
			{
				if (option.equals("1"))
				{
					livre.put("notifier_court", user);
				}
				else if (option.equals("2"))
				{
					livre.put("notifier_long", user);
				}
				else
				{
					livre.put("notifier_court", user);
					livre.put("notifier_long", user);
				}
				livre.save();
			}
			
		}
	}
	
	
	// Récupérer toutes les alertes
	
	public List<ParseObject> recupererAlertes() throws ConnectionNotInitializedException, ParseException
	{
		ParseUser user = ParseUser.getCurrentUser();
		
		ParseQuery query_court = new ParseQuery("Livre");
		query_court.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
		query_court.whereEqualTo("notifie_court", user);

		ParseQuery query_long = new ParseQuery("Livre");
		query_long.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
		query_long.whereEqualTo("notifie_long", user);
		
		List<ParseQuery> queries = new ArrayList<ParseQuery>();
		queries.add(query_court);
		queries.add(query_long);
		 
		ParseQuery mainQuery = ParseQuery.or(queries);
		
		if (!this.isInitialized) 
		{
			throw new ConnectionNotInitializedException("Connection has not been initialized");
		}
		else
		{
			return mainQuery.find();
		}
	}
	
	
	
	
	
	
	
	
	
	
	// Get livre emprunter par currentUser
	
	public List<ParseObject> recupererLivresEmprunter() throws ConnectionNotInitializedException, ParseException
	{
		ParseUser user = ParseUser.getCurrentUser();
		
		ParseQuery query = new ParseQuery("Livre");
		query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
		
		ParseQuery subQuery = new ParseQuery("Exemplaire");
		subQuery.whereEqualTo("emprunte_par", user);

		query.whereMatchesQuery("has", subQuery);
		
		
		if (!this.isInitialized) 
		{
			throw new ConnectionNotInitializedException("Connection has not been initialized");
		}
		else
		{
			return query.find();
		}
	}
	
	
	
	// Get Exemplaire emprunter (ObjectId est id du livre)
	
	public List<ParseObject> recupererExemplaireEmprunter(String objectId) throws ConnectionNotInitializedException, ParseException
	{

		if (!this.isInitialized) 
		{
			throw new ConnectionNotInitializedException("Connection has not been initialized");
		}
		else
		{
			ParseUser user = ParseUser.getCurrentUser();
			
			
			ParseQuery subQuery = new ParseQuery("Livre");
			ParseObject livre = subQuery.get(objectId);
			
			//System.out.println(livre.getString("Titre") + "livvvvvvv");
			
			ParseQuery query = new ParseQuery("Exemplaire");
			query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
			
			query.whereEqualTo("emprunte_par", user); // liste d'exemplaires empruntes
			query.whereEqualTo("exemplaire_de", livre); // exemplaire qui correspond bien au livre
			//query.whereMatchesQuery("exemplaire_de", subQuery); // son livre correspond*/
			
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
