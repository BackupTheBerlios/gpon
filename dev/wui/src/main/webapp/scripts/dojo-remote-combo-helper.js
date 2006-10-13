	var myEntryId = 30;
	
	var temporaryObject = null;
	
	function showSelected(){
			var w=dojo.widget.byId("testTable");
			if(w){
				var o=w.getSelection();
				if(o){
					var s="({\n";
					var a=[];
					for(var p in o){
						a.push("\t\""+p+"\":\""+o[p]+"\"");
					}
					s+=a.join(",\n");
					s+="\n})";
					alert(s);
				}else{
					alert("No rows are selected.");
				}
			}
		}
		
		function cloneObject(what) 
		{
		  for (i in what) {
		  this[i] = what[i];
		  }
		}
		
		
		function fillIds(tableId,fieldId) 
		{
		  var table=dojo.widget.byId(tableId);
		  var fielddiv=document.getElementById(fieldId);
		  
		  var ids='';
		  
		  for(var i=0; i<table.data.length; i++)
		  {
		   var row = table.data[i];
		   
		   if (i!=0) {
		   	ids=ids+',';
		   }
		   ids=ids+row['Id'];
		  }
		  
		  var field=fielddiv.getElementsByTagName("input")[0];
		  
		  field.value=ids;
		}
		
		
		function addSelectionToTable(targetTable, srcComboBox, getItemUrl) {
		  var table=dojo.widget.byId(targetTable);
		  var combo=dojo.widget.byId(srcComboBox);
		  
		  var id = combo.comboBoxSelectionValue.value;
		  
		  // set combobox to null
		  combo.setValue(null);
		  
		  if (id) 
		  {
			// lookup
			var kw = {
				url: getItemUrl+id,
				mimetype: "text/json",
				load: function(type, json, http) {
			  	    for(var i=0; i<table.data.length; i++)
					{
					 var row = table.data[i];
					 if (row["Id"]==id) {
					   return;
					 } 
					}
					table.data.reverse();
					table.data.push(json);
					table.data.reverse();
					table.renderWoSort();
					fillIds(targetTable,targetTable+'result');
				}
			};
			
			dojo.io.bind(kw);
			
		  }
		  else
		  {
		  	alert ("No selected item to insert!")
		  }
		  
		}
		
		function removeSelectionFromTable(tableId) {
		  var table=dojo.widget.byId(tableId);
		  var o = table.getSelection();
		  
		  var newData = new Array();
		  
		  var id = o["Id"] + 0;
		  
		  for(var i=0; i<table.data.length; i++)
		  {
		    var row = table.data[i];
		    var rowid = row["Id"]+0;
		    if (rowid!=id) {
		      newData.push(row);
		    }
		    else {
		    //	
		    }
		  }
		  
		  table.parseData(newData);
		  table.renderWoSort();
		  fillIds(tableId,tableId+'result');
		}
		
		var assocTables = [];
		
		function initAssocTables() 
		{
	  		for (var i = 0; i < assocTables.length; i++) 
	  		{
	  			fillIds(assocTables[i],assocTables[i]+'result');
	  		}
	
		}
		
		