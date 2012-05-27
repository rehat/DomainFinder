import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.*;
import javax.servlet.http.*;


public class DomainFind extends HttpServlet {
	//might need to initialize in doGet for 5+ character domain search
	public static ArrayList[] optionsList;  //set of items to choose from 


	public void doGet(HttpServletRequest request,
			HttpServletResponse response)
					throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		String curNameCheck = "";
		int nameLength = Integer.valueOf((String)request.getParameter("length"));
		optionsList = new ArrayList[nameLength];

		String index[] = new String[nameLength];
		int counter[] = new int[nameLength]; 

		//Generates list of character options based on the current domain length
		//and then constructs the domain name to do the search for
		for(int x = 0; x< nameLength; x++){
			index[x] = (String)request.getParameter(Integer.toString((x)));
			genOptions(x, index[x]);
			counter[x] = Integer.valueOf((String)request.getParameter("c"+x));
			curNameCheck += (String)optionsList[x].get((counter[x] -1));
		}
		curNameCheck += ".com";

		System.out.println(curNameCheck);

		out.println(runCheck(curNameCheck));

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException
			{
		doGet(request, response);
			}


	public static void genOptions(int x, String option){
		String letters[] = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
		String numbers[] = {"1","2","3","4","5","6","7","8","9","0"};
		String vowels[] = {"a","e","i","o","u"};
		String consonant[] = {"b","c","d","f","g","h","j","k","l","m","n","p","q","r","s","t","v","w","x","y","z"};
		String letterNumbers[] = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","1","2","3","4","5","6","7","8","9","0"};
		switch(option.charAt(0)){
		case 'L': // Letters
			optionsList[x] = new ArrayList<String>(Arrays.asList(letters));
			break;
		case 'N': // Numbers
			optionsList[x] = new ArrayList<String>(Arrays.asList(numbers));
			break;
		case 'V': // Vowels
			optionsList[x] = new ArrayList<String>(Arrays.asList(vowels));
			break;
		case 'C': // Consonant
			optionsList[x] = new ArrayList<String>(Arrays.asList(consonant));
			break;
		case 'B': // Letters & Numbers
			optionsList[x] = new ArrayList<String>(Arrays.asList(letterNumbers));
			break;
		case 'D': // Dash
			optionsList[x] = new ArrayList<String>();
			optionsList[x].add("-");
			break;
		default:
			optionsList[x] = new ArrayList<String>();
			optionsList[x].add(option);
			break;
		}
	}



	public static String runCheck(String x) throws IOException{
		String cmd = "whois "+ x;
		Runtime run = Runtime.getRuntime();
		String result;
		String output ="";
		Process pr = null;

		try{
			pr = run.exec(cmd);
		}catch(IOException e){	
			e.printStackTrace();
		}
		try{
			pr.waitFor();
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		BufferedReader buf = new BufferedReader(new InputStreamReader(pr.getInputStream()));
		while((result = buf.readLine()) != null){
			int index = result.indexOf("No match for");
			if(index != -1){
				output = x + " is Available";
				return output;
			}
		}

		output = x + " is Taken";

		return output; 
	}
}




