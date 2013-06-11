package com.utt.scd;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.actionbarsherlock.widget.SearchView;
import com.actionbarsherlock.widget.SearchView.OnQueryTextListener;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.PushService;
import com.slidinglayer.SlidingLayer;
import com.utt.scd.apropos.Apropos;
import com.utt.scd.dialog.AlertingDialogOneButton;
import com.utt.scd.model.Connection;
import com.utt.scd.model.ConnectionNotInitializedException;
import com.utt.scd.parametres.Parametre;
import com.utt.scd.periodiques.Periodiques;
import com.utt.scd.resultats.RechercheAvancee;
import com.utt.scd.resultats.Resultats;
import com.utt.scd.user.CompteLecteur;

public class SCD extends SherlockFragmentActivity implements OnQueryTextListener, OnItemClickListener, OnClickListener 
{

	public static int THEME = R.style.Theme_Dark_purple;
	
    private SlidingLayer slidingLayer;
    private TextView titre, detail;
    private Button close;
    
    private GridView gridView;
    private SCDAdapter adapter;
    
    private LinearLayout parent;
    private List<ParseObject> listeEvenements;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		setTheme(THEME);
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS); 
		
		setContentView(R.layout.activity_scd);
		
		getSupportActionBar().setHomeButtonEnabled(true);
		
		setSupportProgressBarIndeterminateVisibility(false); 
		
		PushService.setDefaultPushCallback(this, SCD.class);
		PushService.subscribe(this,  "", SCD.class);
		ParseInstallation.getCurrentInstallation().saveInBackground();
		
		this.gridView = (GridView) findViewById(R.id.gridview);
		
		ArrayList<FonctionSCD> items = new ArrayList<FonctionSCD>();
		items.add(new FonctionSCD("Recherche Avancée", R.drawable.search));
		items.add(new FonctionSCD("Compte Lecteur", R.drawable.user));
		items.add(new FonctionSCD("Périodiques", R.drawable.book));
		items.add(new FonctionSCD("Paramètres", R.drawable.setting));
		
		this.adapter = new SCDAdapter(this, items);
		
		this.gridView.setAdapter(adapter);
		this.gridView.setOnItemClickListener(this);
		
		
		this.parent = (LinearLayout) findViewById(R.id.parent);
		

		this.listeEvenements = new ArrayList<ParseObject>();
		
		new recupereEvenements().execute();
		
		
		this.slidingLayer = (SlidingLayer) findViewById(R.id.slidingLayer1);

		this.slidingLayer.setShadowWidthRes(R.dimen.shadow_width);
		this.slidingLayer.setShadowDrawable(R.drawable.sidebar_shadow);
		this.slidingLayer.setStickTo(SlidingLayer.STICK_TO_AUTO);
		this.slidingLayer.setCloseOnTapEnabled(true);
        
        this.titre = (TextView) findViewById(R.id.titre);
        this.detail = (TextView) findViewById(R.id.detail);
        
        this.close = (Button) findViewById(R.id.Button1);
        this.close.setOnClickListener(this);

	}
	
	
	
	@SuppressWarnings("deprecation")
	public void populateView()
	{
		if (this.listeEvenements.size() > 0)
		{
			WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
			Display display = wm.getDefaultDisplay();
			
			for (int i = 0 ; i < this.listeEvenements.size() ; i++)
			{
				ParseObject eve = this.listeEvenements.get(i);
				
				View view = LayoutInflater.from(this).inflate(R.layout.evenement_item, null);
				
				TextView date = (TextView) view.findViewById(R.id.date);
				Format formatter  = new SimpleDateFormat("dd/MM/yyyy");
				date.setText(formatter.format(eve.getDate("Date")));
				date.setBackgroundColor(SCD.color());
				
				
				TextView titre = (TextView) view.findViewById(R.id.textView1);
				titre.setText(eve.getString("Titre"));
				
				TextView detail = (TextView) view.findViewById(R.id.textView2);
				
				String dt = eve.getString("Detail");
				
				if (dt.length() > 35)
				{
					detail.setText(dt.substring(0, 34) + "...");
				}
				else
				{
					detail.setText(dt);
				}
				
				
				view.setLayoutParams(new LayoutParams(display.getWidth(), LayoutParams.WRAP_CONTENT));
				
				this.parent.addView(view);
				
				LinearLayout lay = (LinearLayout) view.findViewById(R.id.lay);
				
				lay.setTag(i);
				
				lay.setOnClickListener(this);
				
				
			}
			
		}
	}
	
	
	
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) 
	{
        switch (keyCode) 
        {
	        case KeyEvent.KEYCODE_BACK:
	            if (slidingLayer.isOpened()) 
	            {
	            	slidingLayer.closeLayer(true);
	                return true;
	            }
	
	        default:
	            return super.onKeyDown(keyCode, event);
        }
    }
	
	
	private AlertDialog choice ;
	private LayoutInflater inflater;
	

	
	public class login extends AsyncTask<String, Integer, String>
	{
		private AlertingDialogOneButton alertingDialogOneButton;
		
		private Connection connection;
		

		public login()
		{
			connection = Connection.getInstance();
			connection.initialize();
		}

		@Override
		protected void onPreExecute() 
		{
			super.onPreExecute();
			setSupportProgressBarIndeterminateVisibility(true); 
		}
		
		@Override
		protected String doInBackground(String... arg0) 
		{
			try 
			{
				System.out.println(arg0[0] + arg0[1]);
				this.connection.login(arg0[0], arg0[1]);
			} 
			catch (ParseException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "fail";
			} 
			catch (ConnectionNotInitializedException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "no internet";
			}
			
			return "successful";
    	    
		}
		
		@Override
		protected void onPostExecute(String result) 
		{
			super.onPostExecute(result);

			setSupportProgressBarIndeterminateVisibility(false); 
			
			if(result.equals("fail"))
			{
				
				alertingDialogOneButton = AlertingDialogOneButton.newInstance("Erreur", 
																			result,																			
																			R.drawable.action_about);
				alertingDialogOneButton.show(getSupportFragmentManager(), "error 1 alerting dialog");
			}
			else if(result.equals("no internet"))
			{
				alertingDialogOneButton = AlertingDialogOneButton.newInstance("Erreur", 
																			result,																			
																			R.drawable.action_search);
				alertingDialogOneButton.show(getSupportFragmentManager(), "error 1 alerting dialog");
				
			}
			else if (result.equals("successful"))
			{	
				System.out.println("Identification successful");
				
				Intent intent = new Intent(getBaseContext(), CompteLecteur.class);
				startActivity(intent);
			}
			
		}

		
		
	}
	
	

	public class recupereEvenements extends AsyncTask<String, Integer, String>
	{
		private AlertingDialogOneButton alertingDialogOneButton;
		
		public Connection connection;
		public List<ParseObject> resultats;

		public recupereEvenements()
		{
			connection = Connection.getInstance();
			connection.initialize();
			
			this.resultats = new LinkedList<ParseObject>();
		}

		@Override
		protected void onPreExecute() 
		{
			super.onPreExecute();
			setSupportProgressBarIndeterminateVisibility(true); 
		}
		
		@Override
		protected String doInBackground(String... arg0) 
		{
			try 
			{
				this.resultats = connection.recupererEvenement();
			} 
			catch (ConnectionNotInitializedException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "no internet";
			} 
			catch (ParseException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "fail";
			}
			
			return "successful";
    	    
		}
		
		@Override
		protected void onPostExecute(String result) 
		{
			super.onPostExecute(result);

			setSupportProgressBarIndeterminateVisibility(false); 
			
			if(result.equals("fail"))
			{
				
				alertingDialogOneButton = AlertingDialogOneButton.newInstance("Erreur", 
																			result,																			
																			R.drawable.action_about);
				alertingDialogOneButton.show(getSupportFragmentManager(), "error 1 alerting dialog");
			}
			else if(result.equals("no internet"))
			{
				alertingDialogOneButton = AlertingDialogOneButton.newInstance("Erreur", 
																			result,																			
																			R.drawable.action_search);
				alertingDialogOneButton.show(getSupportFragmentManager(), "error 1 alerting dialog");
				
			}
			else if (result.equals("successful"))
			{	
				listeEvenements = resultats;

				populateView();
				
			}
			
		}

		
		
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		
		//Create the search view
        SearchView searchView = new SearchView(getSupportActionBar().getThemedContext());
        searchView.setQueryHint("Recherche simple");
        searchView.setOnQueryTextListener(this);
        
       
        MenuItem recherche_simple = menu.add(0,0,0,"Recherche simple");
        {
        	recherche_simple.setIcon(R.drawable.action_search);
        	recherche_simple.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);              
        	recherche_simple.setActionView(searchView);
        }
        
        MenuItem apropos = menu.add(0,1,1,"A propos");
        {
            apropos.setIcon(R.drawable.action_about);
            apropos.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);           
        }
        
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		switch (item.getItemId()) 
		{
			case 0:
	
				
				return true;
	
			case 1:
				
				Intent intent = new Intent(this,Apropos.class);
				startActivity(intent);
				
				return true;
	
			case android.R.id.home:
	
				this.finish();
	
				return true;
	
		};

		return false;
	}


	@Override
	public boolean onQueryTextSubmit(String query) 
	{
		Intent intent = new Intent(this, Resultats.class);
		intent.putExtra("Titre", query);
		startActivity(intent);
		
		return false;
	}


	@Override
	public boolean onQueryTextChange(String newText) 
	{
		return false;
	}

	@Override
	public void onItemClick(AdapterView<?> adapter, View view, int position, long id) 
	{
		if (position == 0)
		{
			Intent intent = new Intent(this, RechercheAvancee.class);
			startActivity(intent);
		}
		else if (position == 1)
		{
			if (ParseUser.getCurrentUser().getObjectId() == null)
			{
				final AlertDialog.Builder builder = new AlertDialog.Builder(this);
	            builder.setTitle("Veuillez vous identifier");
	            
	            inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            View login = inflater.inflate(R.layout.login, null);
	            builder.setView(login);
	            
	            final EditText edt_pseudo = (EditText) login.findViewById(R.id.editText1);
	            final EditText edt_mot_de_passe = (EditText) login.findViewById(R.id.editText2);
	            
	            builder.setPositiveButton("S'identifier", new DialogInterface.OnClickListener() {
	    			public void onClick(DialogInterface dialog, int whichButton) 
	    			{
	    				
	    			}
	    		});
	            builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
	    			public void onClick(DialogInterface dialog, int whichButton) 
	    			{
	    				dialog.dismiss();
	    			}
	    		});
	            
	            choice = builder.create();
	            choice.setCancelable(true);
	            choice.setOnShowListener(new DialogInterface.OnShowListener() {

	                @Override
	                public void onShow(final DialogInterface dialog) 
	                {

	                    Button b = choice.getButton(AlertDialog.BUTTON_POSITIVE);
	                    b.setOnClickListener(new View.OnClickListener() {

	                        @Override
	                        public void onClick(View view) 
	                        {
	                        	if (edt_pseudo.getText() == null || edt_pseudo.getText().toString().length() == 0)
	    	    				{
	    	    					edt_pseudo.setError("Veuillez remplir ce champ");
	    	    				}
	    	    				else if (edt_mot_de_passe.getText() == null || edt_mot_de_passe.getText().toString().length() == 0)
	    	    				{
	    	    					edt_mot_de_passe.setError("Veuillez remplir ce champ");
	    	    				}
	    	    				else
	    	    				{
	    	    					String[] data = {edt_pseudo.getText().toString(),edt_mot_de_passe.getText().toString()};
	    	    					new login().execute(data);
	    	    					choice.dismiss();
	    	    				}
	                        }
	                    });
	                }
	            });
	            
	            
	            choice.show();

			}
			else
			{
				Intent intent = new Intent(this, CompteLecteur.class);
				startActivity(intent);
			}
		}
		else if (position == 2)
		{
			System.out.println("phai chay buoc nay");
			Intent intent = new Intent(this, Periodiques.class);
			startActivity(intent);
		}
		else if (position == 3)
		{
			Intent intent = new Intent(this, Parametre.class);
			startActivity(intent);
			
			/*if (!this.slidingLayer.isOpened()) 
			{
				this.slidingLayer.openLayer(true);
            }
			else if (this.slidingLayer.isOpened())
			{
				this.slidingLayer.closeLayer(true);
            }*/
		}
		
	}



	@Override
	public void onClick(View v) 
	{
		if (v.equals(close))
		{
			if (!this.slidingLayer.isOpened()) 
			{
				this.slidingLayer.openLayer(true);
            }
			else if (this.slidingLayer.isOpened())
			{
				this.slidingLayer.closeLayer(true);
            }
		}
		else
		{
			int postion = (Integer) v.getTag();
			
			ParseObject eve = this.listeEvenements.get(postion);
			
			titre.setText(eve.getString("Titre"));
			
			String dt = eve.getString("Detail");
			
			detail.setText(dt);
			
			if (!slidingLayer.isOpened()) 
			{
				slidingLayer.openLayer(true);
            }
		}
		
	}
	
	
	
	
	
	
	
	public static int color()
	{
		if (THEME == R.style.Theme_Dark_blue)
		{
			return Color.BLUE;
		}
		else if (THEME == R.style.Theme_Dark_green)
		{
			return Color.parseColor("#488214");
		}
		else if (THEME == R.style.Theme_Dark_purple)
		{
			return Color.parseColor("#8A2BE2");
		}
		else if (THEME == R.style.Theme_Dark_red)
		{
			return Color.RED;
		}
		else
		{
			return Color.parseColor("#FFB00F");
		}
	}

	

}
