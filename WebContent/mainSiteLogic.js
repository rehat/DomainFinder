
$(document).ready(function(){

	var done = 0;
	var found = 0;


	//Loads and changes external search bar html to match current search values
	$.get('../selectFormTemplate.html').success(function(data){
		rememberSearch(data);
	});
	function rememberSearch(data){

		$('div.searchbar').html("<FORM ACTION=\"/DomainFinder/servlet/Main\" METHOD=POST>");
		for(var i = 0; i< nameLength; i++){	
			$('form').append(data);
			updateSelected(i);
		}
		$('form').append("<INPUT TYPE=Hidden name=length VALUE="+nameLength+">");
		$('form').append("<input type=button name=add value=+>");
		$('form').append("<input type=button name=remove value=->");
		$('form').append("<INPUT TYPE=SUBMIT VALUE=CHECK></FORM>");



		function updateSelected(i){
			$('select[name="empty"]').prop('name', i);
			$('select[name='+ i +']').children('option[value='+options[i]+']').attr('selected','yes');
		}
		
		sizeLogic();
		dashLogic();
		
		//used to disable the dash option at the beginning or end of the name and to prevent consecutive dashes  
		function dashLogic(){
			for(var i = 1; i< nameLength; i++){
				if($('select[name='+ i +']').find(":selected").attr("value") == "D"){
					$('select[name='+ (i -1) +']').children('option[value="D"]').prop('disabled', true);
					$('select[name='+ (i +1) +']').children('option[value="D"]').prop('disabled', true);
					i++;
				}
				else
					$('select[name='+ i +']').children('option[value="D"]').prop('disabled', false);
			}

			//Disable first and last Dash option
			$('select[name="0"]').children('option[value="D"]').prop('disabled', true);
			$('select[name='+ (nameLength -1) +']').children('option[value="D"]').prop('disabled', true);
		}
		
		//hide/show plus or minus buttons depending on the current domain name length 5 min 8 max
		function sizeLogic(){
			(nameLength < 8) ? $('input[name="add"]').show(): $('input[name="add"]').hide();	
			(nameLength > 5) ? $('input[name="remove"]').show():$('input[name="remove"]').hide();
			$('input[name=length]').attr('value', nameLength);
		}
		
		//make sure all dash options have been corrected whenever 
		$('select').change(function(){
			dashLogic();		
		});
		
		//plus button to increase the domain name by one character and add another drop down menu
		$('input[name="add"]').click(function(){
			options.push("L");
			counter.push(1);
			$('select[name='+ (nameLength-1) +']').after(data);
			nameLength++;
			$('select[name="empty"]').prop('name', (nameLength-1));
			dashLogic();
			sizeLogic();
		});

		//minus button to remove a drop down menu
		$('input[name="remove"]').click(function(){
			options.pop();
			counter.pop();
			$('select[name='+ (nameLength-1) +']').remove();
			nameLength--;
			dashLogic();
			sizeLogic();

		});
	}
	
	//ajax check to servlet to see if domain name is available
	function check(url, id){
		//target = $("<div>");
		//target.attr('name', id);
		//$("#container").append(target);
		$.ajax({
			async:true,
			type:'GET',
			url:url,	
			success: function(data){
				//$('div[name='+ id +']').html(data);
				//target.html(data);
				$('div[id="container"]').append(data + "<br>");
				if(!done && found < 50){
					runCheck();
				}
			},
		});


	}


	function runCheck(){
		//Generates URL for the next search item
		var servlet = "/DomainFinder/servlet/DomainFind?";
		for(var i = 0; i < nameLength ; i++){ 
			if(i>0){
				servlet += "&"+ i + "=" + options[i];
			}else
				servlet += i + "=" + options[i];

			servlet += "&c"+i+"="+ counter[i]; 
		}
		servlet += "&length="+ nameLength;


		check(servlet, found);

		//increments counter
		for(var y = (nameLength-1); y >= 0 ; y--){  
			if( y == 0 && counter[y] >= endCounter[y]){   
				done = 1;    
			}else{   
				if(counter[y] >= endCounter[y]){    
					counter[y] = 1;   
				}else{    
					counter[y]++;   
					break;   
				}  
			}
		}
		found++;
	}


	$('a.next').click(function(event){
		event.preventDefault();
		$('div[id="container"]').html(" ");
		found = 0;
		runCheck();
	});

	runCheck();


});