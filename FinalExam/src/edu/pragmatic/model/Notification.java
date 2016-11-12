package edu.pragmatic.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;

import edu.pragmatic.controllers.FileController;



public class Notification {
	private FileController fileController; 
	private static Map<String, AnimalEntry> animalEntries = new HashMap<String,AnimalEntry>();
	private Collection<AnimalEntry> collectionAnimalEntries;



	public void startUp(String readFileName, String outputFileName, String directory) throws FileNotFoundException, IOException{	
		if(readFileName == null || outputFileName == null){
			return;
		}
		fileController = new FileController(readFileName, outputFileName);
		fileController.setDirectory(directory);
		collectionAnimalEntries = fileController.readAnimalData();
		
		collectionAnimalEntries.forEach((animal) -> {
			String key = animal.getName()+animal.getColor()+animal.getBreed();
			animalEntries.put(key, animal);    
			
        });

		
		fileController.writeInNewFileAnimalData(collectionAnimalEntries);		 
	}
	
	public Map<String, AnimalEntry> getAnimalEntries(){
			return this.animalEntries;
	}
	
	
	public void setAnimalEntries(Map<String, AnimalEntry> animalEntries) {
		this.animalEntries = animalEntries;
	}
	
	
	
	public void removeAnimalNotification(String removeKey,String outputFileName) throws IOException{
		fileController = new FileController(outputFileName);
		AnimalEntry entry = getAnimalEntries().remove(removeKey);
		fileController.writeInNewFileAnimalData(this.animalEntries.values());

	}
	
	


	
	

}
