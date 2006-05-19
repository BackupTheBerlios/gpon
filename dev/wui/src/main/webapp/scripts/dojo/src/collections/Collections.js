dojo.provide("dojo.collections.Collections");

dojo.collections = {Collections:true};
dojo.collections.DictionaryEntry = function(k,v){
	this.key = k;
	this.value = v;
	this.valueOf = function(){ return this.value; };
	this.toString = function(){ return this.value; };
}

dojo.collections.Iterator = function(a){
	var obj = a;
	var position = 0;
	this.atEnd = (position>=obj.length-1);
	this.current = obj[position];
	this.moveNext = function(){
		if(++position>=obj.length){
			this.atEnd = true;
		}
		if(this.atEnd){
			return false;
		}
		this.current=obj[position];
		return true;
	}
	this.reset = function(){
		position = 0;
		this.atEnd = false;
		this.current = obj[position];
	}
}

dojo.collections.DictionaryIterator = function(obj){
	var arr = [] ;	//	Create an indexing array
	for (var p in obj) {
		arr.push(obj[p]);	//	fill it up
	}
	var position = 0 ;
	this.atEnd = (position>=arr.length-1);
	this.current = arr[position]||null ;
	this.entry = this.current||null ;
	this.key = (this.entry)?this.entry.key:null ;
	this.value = (this.entry)?this.entry.value:null ;
	this.moveNext = function() { 
		if (++position>=arr.length) {
			this.atEnd = true ;
		}
		if(this.atEnd){
			return false;
		}
		this.entry = this.current = arr[position] ;
		if (this.entry) {
			this.key = this.entry.key ;
			this.value = this.entry.value ;
		}
		return true;
	} ;
	this.reset = function() { 
		position = 0 ; 
		this.atEnd = false ;
		this.current = arr[position]||null ;
		this.entry = this.current||null ;
		this.key = (this.entry)?this.entry.key:null ;
		this.value = (this.entry)?this.entry.value:null ;
	} ;
};
