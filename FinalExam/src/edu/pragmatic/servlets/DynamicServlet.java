package edu.pragmatic.servlets;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.pragmatic.interfaces.ISetCorrectFileName;
import edu.pragmatic.model.AnimalEntry;
import edu.pragmatic.model.Notification;

@WebServlet("/DynamicServlet")
public class DynamicServlet extends HttpServlet implements ISetCorrectFileName{
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.getWriter().append("Served at: ").append(request.getContextPath());		
			
		
		String getKey =request.getParameter("hiddenName");	
		String getActionName =request.getParameter("hiddenActionName");	
		Notification notification = new Notification();
		String insertFileName =request.getParameter("insertFileName");
		
		String outputFileName = "";
		boolean isNotNullFileName = (insertFileName != null && request.getSession().getAttribute("newFileName") != null);
		//read from last create output file
		if(isNotNullFileName && !request.getSession().getAttribute("newFileName").equals(insertFileName)){
			 outputFileName = insertFileName;
		}
		
		//get filename from session
		if(request.getSession().getAttribute("notificationAnimalList") != null && (insertFileName == null || insertFileName == "")){		
			insertFileName = (String) request.getSession().getAttribute("newFileName");
		}
		
		getActionName = (getActionName == "" || getActionName == null) ? "loadPage" : getActionName;

	
		
		if((getActionName != null && getActionName.equals("loadPage")) ){
			insertFileName = "load_file_start_page.csv";
			String directory = "readFiles";
			notification.startUp("animals.lostandfound.csv",insertFileName,directory);
			 request.getSession().setAttribute("newFileName",  insertFileName);
			request.getSession().setAttribute("notificationAnimalList",  notification.getAnimalEntries());
		}else if((getActionName != null && getActionName.equals("createFileRecords")) ){			   
			//if user no set extension format then set format csv to filename
		   insertFileName = setCorrectFileFormat(insertFileName);

			
			
			insertFileName = request.getSession().getAttribute("newFileName") != null ? (String)request.getSession().getAttribute("newFileName") : insertFileName; 
			///String readFileName, String outputFileName
			if(outputFileName != null && outputFileName != ""){
				//if user no set extension format then set format csv to filename
				outputFileName = setCorrectFileFormat(outputFileName);

			  String directory = "outputFiles";
			  notification.startUp((String)request.getSession().getAttribute("newFileName"),outputFileName,directory);
			}else if(request.getSession().getAttribute("newFileName") == null){
				String directory = "readFiles";
				notification.startUp("animals.lostandfound.csv",insertFileName,directory);
			}else if(insertFileName != null){
				String directory = "readFiles";
				notification.startUp(insertFileName,insertFileName,directory);
			}
		   request.getSession().setAttribute("notificationAnimalList",  notification.getAnimalEntries());
		   request.getSession().setAttribute("newFileName",  insertFileName);		   
		   request.getSession().setAttribute("showMessage",  "show");
		   request.getSession().setAttribute("messageText",  "You are created new file with edit information");
		}else if(getActionName != null && getActionName.equals("deleteNotificationAnimal")){			
			if(insertFileName == null){
				insertFileName = (String) request.getSession().getAttribute("newFileName");
			}
			notification.setAnimalEntries(notification.getAnimalEntries());	
			notification.removeAnimalNotification(getKey,insertFileName);
			request.getSession().setAttribute("notificationAnimalList",  notification.getAnimalEntries());
			request.getSession().setAttribute("showMessage",  "show");
			request.getSession().setAttribute("messageText",  "You are deleted the animal record successfully");
		}
	   
	    
		response.sendRedirect(request.getContextPath() +"/views/listNotificationAnimals.jsp");
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	@Override
	public String setCorrectFileFormat(String fileName) {
		  int res = fileName.lastIndexOf('.');
			if(res==-1){
				fileName += ".csv";
			}
		return fileName;
	}

}
