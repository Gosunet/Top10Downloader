package org.example.top10downloader;

import java.io.StringReader;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Log;

public class ParseApplications {
	private String data;
	private ArrayList<Application> applications;
	
	
	
	public ParseApplications(String xmlData){
		data = xmlData;
		applications = new ArrayList<Application>();
	}





	/**
	 * @return the applications
	 */
	public ArrayList<Application> getApplications() {
		return applications;
	}
	
	
	//variable que l'on va manipuler
	public boolean process(){
		
		boolean operationStatus = true; 
		
		Application currentRecord = null; // class appli pour stocker nos valeurs
		boolean inEntry = false; // dans un tag entry ou non
		String textValue = ""; // le text qui nous interesse
		
		
		try{
			// Methode pour parcourir un XML
			//Parsing code
			// Android
			
			
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			XmlPullParser xpp = factory.newPullParser();
			
			xpp.setInput(new StringReader(this.data)); // convertir en Strinf pour le Parsing
			int eventType = xpp.getEventType();
			
			while (eventType != XmlPullParser.END_DOCUMENT){ // fin du xml
				String tagName = xpp.getName();
			
				if(eventType == XmlPullParser.START_TAG){ // debut d'un tag
					
					
					if(tagName.equalsIgnoreCase("entry")){ // sommes nous dans entry ?
						inEntry = true;						// oui
						// Nous sommes dans un tag entry
						// On va rénitialiser notre application courante pour re stocker
						currentRecord = new Application();
					}
					
					
				} else if(eventType == XmlPullParser.TEXT){ // rencontre de text
					
					textValue = xpp.getText(); // STocker le text après le TAG dans le string text value
					
				} else if(eventType == XmlPullParser.END_TAG){ // fin du tag /title par exemple
					
					if(inEntry){
						if(tagName.equalsIgnoreCase("entry")){ // arrivé à la fin de l'entry
							applications.add(currentRecord); // ajouter aux vectuer d'application
							inEntry = false; // plus dans entry
						}
						
						if(tagName.equals("name")){ // fin d'un nom alors on l'ajoute à nae de app
							currentRecord.setName(textValue);
							
						} else if (tagName.equalsIgnoreCase("Artist")){ // egale aux tags mais on ignore le tag
							currentRecord.setArtist(textValue);
							
						} else if (tagName.equalsIgnoreCase("releaseDate")){
							currentRecord.setReleaseDate(textValue);
						}
						
						
					}
					
				}
				
				eventType = xpp.next(); // prochain tag
				
			}
			
			
			
			
			
			
		} catch(Exception e){
			e.printStackTrace();
			operationStatus = false; // si erreur operation est faux
		}
		
		
		
		//test for each LOG est un truc de test pour afficher sur le log (console)
		
		for(Application app : applications){
			Log.d("LOG","*************");
			Log.d("LOG",app.getName());
			Log.d("LOG",app.getArtist());
			Log.d("LOG",app.getReleaseDate());
		}
		
		
		
		
		return operationStatus; // sans soucis ou non
	}
	
	
	
	
	
}
