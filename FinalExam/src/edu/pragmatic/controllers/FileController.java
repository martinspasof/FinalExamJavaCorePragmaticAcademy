package edu.pragmatic.controllers;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

import edu.pragmatic.model.AnimalEntry;


public class FileController {	
	
	private static final String SEPARATOR = ",";
	private String workingDirectory;
	private String fileNameReadOfNotification;
	private String fileNameWriteOfNotification;
	private String directory;
	
	public FileController(String fileNameReadOfNotification,String fileNameWriteOfNotification) {
		this.setFileNameReadOfNotification (fileNameReadOfNotification);
		this.setFileNameWriteOfNotification(fileNameWriteOfNotification);
	}
	
	public FileController(String fileNameWriteOfNotification) {		
		this.setFileNameWriteOfNotification(fileNameWriteOfNotification);
	}	
	
	
	public String getFileNameReadOfNotification() {
		return fileNameReadOfNotification;
	}

	public void setFileNameReadOfNotification(String fileNameReadOfNotification) {
		this.fileNameReadOfNotification = fileNameReadOfNotification;
	}

	public String getFileNameWriteOfNotification() {
		return fileNameWriteOfNotification;
	}

	public void setFileNameWriteOfNotification(String fileNameWriteOfNotification) {
		this.fileNameWriteOfNotification = fileNameWriteOfNotification;
	}
	
	 
	
	public Collection<AnimalEntry> readAnimalData() throws FileNotFoundException, IOException{
		 List<AnimalEntry> animalEntries = new ArrayList<>();
		 
		 String pathFile = constructFilePath(getFileNameReadOfNotification(),getDirectory());

		try(BufferedInputStream in = new BufferedInputStream(new FileInputStream(pathFile));
				Scanner scanner = new Scanner(in)) {			
				
				while(scanner.hasNextLine()) {
					String line = scanner.nextLine();
					
					AnimalEntry entry = createAnimalEntry(line);
					if(entry != null) {						
						animalEntries.add(entry);
					}
					
				}	
			}
			
			return animalEntries;
		
	}
	
   private static String createString(AnimalEntry entry) {
		
		StringBuilder builder = new StringBuilder();
		builder.append(entry.getDate());
		builder.append(SEPARATOR);
		builder.append(entry.getColor());
		builder.append(SEPARATOR);
		builder.append(entry.getBreed());		
		builder.append(SEPARATOR);		
		builder.append(entry.getSex());		
		builder.append(SEPARATOR);
		builder.append(entry.getState());		
		builder.append(SEPARATOR);
		builder.append(entry.getName());		
		builder.append(SEPARATOR);
		builder.append(entry.getDateCreated());
		
		return builder.toString();		
	}
   
   
   public String constructFilePath(String filename, String directory){		   
		String fileSep = File.separator;
		this.workingDirectory = System.getProperty("user.dir")+fileSep+directory+fileSep+filename;
	
		return workingDirectory;
	}
	
    public void writeInNewFileAnimalData(Collection<AnimalEntry> animalEntries) throws IOException {		
		 String directory = "outputFiles";
		 String pathFile = constructFilePath(getFileNameWriteOfNotification(),directory);
		try(PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(pathFile)))) {
			
			for(AnimalEntry entry : animalEntries) {				
				writer.println(createString(entry));				
			}
		}
	}
	
	private static AnimalEntry createAnimalEntry(String line) {
		String[] elements = line.split(SEPARATOR);
		if(elements.length != 7) {
			return null;
		}
		
		return new AnimalEntry(elements[0], elements[1], elements[2],elements[3],elements[4],elements[5],elements[6]);
	}

	public String getDirectory() {
		return directory;
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}
	
	
	

	
	
	
}
