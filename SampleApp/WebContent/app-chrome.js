	  
		var recognition;
		var today = new Date();
		var dd = today.getDate();
		var mm = today.getMonth() + 1; //January is 0!
		var yyyy = today.getFullYear();

		if (dd < 10) {
		  dd = '0' + dd;
		}

		if (mm < 10) {
		  mm = '0' + mm;
		}

		todayDate =  dd + '-' +mm+'-'+ yyyy;
		console.log(todayDate);
		
	function startRecognitionChrome() {
			recognition = new webkitSpeechRecognition();
			recognition.onstart = function(event) {
				//updateRec();
			};
			recognition.onresult = function(event) {
				var text = "";
			    for (var i = event.resultIndex; i < event.results.length; ++i) {
			    	text += event.results[i][0].transcript;
			    }
			    setInput(text);
				stopRecognition();
			};
			recognition.onend = function() {
				stopRecognition();
			};
			var lan=$("#languageSelect").val();
			if(lan=='JA'){
				recognition.lang = "ja";
			}else{
				recognition.lang = "en-IN";
			}
			
			recognition.start();
		}
	
		function stopRecognition() {
			if (recognition) {
				recognition.stop();
				recognition = null;
			}
			//updateRec();
		}

		function setInput(text) {
			$("#typeSearch").val(text);
			var text = $("#typeSearch").val();
			typeSearchWit(text);
			console.log(text);
		}
	function typeSearchWit(text){
			//var text="金曜日にシドニーからモスクワへ旅行したいのですが。";		
			var xhr=new XMLHttpRequest();
			  xhr.onload=function(e) {
			      if(this.readyState === 4&&this.status == 200) {
			          console.log("Server returned: ",e.target.responseText);
			         var responses= e.target.responseText.split(',');
			          var from=responses[0].trim();
			          var dep=responses[1].trim();
			          var travelDate=responses[2].trim();
			          if(travelDate==""||travelDate==null||travelDate==undefined){
			        	  travelDate=todayDate;
			          }
			         
			        window.open("https://www.google.com/flights?"
			      	    		+"q="+from+"+to+"+dep+"+on+"+travelDate);
			        console.log("https://www.google.com/flights?"
			      	    		+"q="+from+"+to+"+dep+"+on+"+travelDate);
			        
			      }
			  };
			 var fd=new FormData();
			 var encoded=encodeURIComponent(text);
			  fd.append("searchString",text);
			  var lan=$("#languageSelect").val();
			  fd.append("languageofApp",lan);
			  xhr.open("POST","./record",true);
			  xhr.send(fd);
		}
