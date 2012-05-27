import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.*;
import javax.servlet.http.*;




public class Main extends HttpServlet {


	public void doGet(HttpServletRequest request,
			HttpServletResponse response)
					throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		int nameLength = Integer.valueOf((String)request.getParameter("length"));

		String i[] = new String[nameLength];
		String options = "var options = [";
		String endCounter = "var endCounter = [";
		String length = "var nameLength = "+ nameLength;
		String counter = "var counter = [";
		for(int x = 0; x< nameLength; x++){
			i[x] = (String)request.getParameter(Integer.toString((x)));
			options += "'" + i[x] + "'";
			endCounter += numbOfOptions(i[x]);
			counter += "1";
			if(x != (nameLength-1)){
				options +=",";
				endCounter +=",";
				counter  +=",";
			}
		}
		options +="];";
		endCounter +="];";
		counter += "];";
		
		//merge top to one string

		out.println(
				"<HTML>\n" +
						"<HEAD><TITLE>DOMAINFINDER</TITLE>" +

			"</HEAD>\n" +
			"<BODY>\n" +

			"<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.js\"></script> \n"+
			"<script type=\"text/javascript\"> " + options +"\n "+ endCounter +"\n "+ length + "\n " + counter +"\n" +
			"</script>\n"+
			"<script type=\"text/javascript\" src=\"../mainSiteLogic.js\"></script>\n"+
			"<div class=\"searchbar\"></div>" +
			"The search has started<br>"+
			"<div id=\"container\">" + "</div><br>\n"+
			"<a href=\"#\" class=\"next\">NEXT</a>"+
			"</BODY></HTML>"
				);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException
			{
		doGet(request, response);
			}
	
	public static int numbOfOptions(String x){
		int options = 0;
		switch(x.charAt(0)){
		case 'L':
			options = 26;
			break;
		case 'N':
			options = 10;
			break;
		case 'C':
			options = 21;
			break;
		case 'V':
			options = 5;
			break;
		case 'B':
			options = 36;
			break;
		case 'D':
			options = 1;
			break;
		default:
			options = 1;
			break;
		}

		return options;
	}
}