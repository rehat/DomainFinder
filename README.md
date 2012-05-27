DomainFinder
============

Java servlet site that generates a combination of domain names and checks to see if the domain is available.

Running on my dell mini 9  www.alitaher.com:8080

The mainSiteLogic.js handles updating the select menus and sends the java servlet DomainFinder.java with the select menu options and a counter for the next domain to look up.  The Main.java is called at the beginning of a new search and sets up the mainSiteLogic.js variables:

options[] 		-contains the user's options for each character of the domain name

endCounter[] 	-contains the length value of the options for each character of the domain name

counter[] 		-each index set to 1

length  		-length of the domain name.


For example:

options[L,N,a,V,c]  		- L =letters, N = numbers 0-9, a = 'a', V = vowels, c = 'c'

endCounter[26,10,1,5,1] 	- length of each option in options[]

counter[1,1,1,1,1]			- starting counter

length = 5					- domain size


The mainSiteLogic.js then increments the counter sort of like a timmer and stops when the counter equals the endCounter then sends the counter and options array to the DomainFinder.java which generates the domain name to check and replies if its take or available.


Issues:  
Having a hard time finding a server service that allows executing shell commands since my code uses the whois command for each domain name.

ToDo:
- Need to design a simple site
- Add java script to the index.html to disable the dash options 
- Display only available domain names
- Add a wild/repeat option so option cWaWt will generate all domain names with repeats so:
  ccatt.com, caaat.com, ...

