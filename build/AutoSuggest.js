
window.onload = function () {
  var oTextbox = new AutoSuggestControl(document.getElementById("querybox")); 
}

// send Google suggest request based on the user input
function sendAjaxRequest(input,callback)
{
	var xmlHttp = new XMLHttpRequest(); // works only for Firefox, Safari, ...
  var request = "http://localhost:1448/eBay/suggest?q="+encodeURI(input);

  xmlHttp.open("GET", request); 
  xmlHttp.onreadystatechange = function(){
    if (xmlHttp.readyState == 4 && xmlHttp.status==200) {
     var suggestions = [];
     response = xmlHttp.responseText;
     var xml = (new DOMParser()).parseFromString(response, 'text/xml');
     var resultList = xml.getElementsByTagName("suggestion");
     for (var i=0; i < resultList.length; i++) {
      suggestions.push(resultList[i].attributes[0].nodeValue);
    }
    callback(suggestions);

  }

};
xmlHttp.send(null);
}

function AutoSuggestControl(oTextbox) {

  this.textbox = oTextbox;

  this.init();

}

/**
 * Autosuggests one or more suggestions for what the user has typed.
 * If no suggestions are passed in, then no autosuggest occurs.
 * @scope private
 * @param aSuggestions An array of suggestion strings.
 */
 AutoSuggestControl.prototype.autosuggest = function (aSuggestions /*:Array*/) {

    //make sure there's at least one suggestion
    if (aSuggestions.length > 0) {
    	console.log(aSuggestions[0]);
      this.typeAhead(aSuggestions[0]);
    }
  };


/**
 * Handles keyup events.
 * @scope private
 * @param oEvent The event object for the keyup event.
 */
 AutoSuggestControl.prototype.handleKeyUp = function (oEvent /*:Event*/) {

  var iKeyCode = oEvent.keyCode;

    //make sure not to interfere with non-character keys
    if (iKeyCode < 32 || (iKeyCode >= 33 && iKeyCode <= 46) || (iKeyCode >= 112 && iKeyCode <= 123)) {
        //ignore
      } else {
        var oThis = this;
        sendAjaxRequest(this.textbox.value, function(result){ 
          oThis.autosuggest(result)
        });  
      }
    };

/**
 * Initializes the textbox with event handlers for
 * auto suggest functionality.
 * @scope private
 */
 AutoSuggestControl.prototype.init = function () {
    //save a reference to this object
    var oThis = this;
    this.textbox.onkeyup = function (oEvent) {
      if (!oEvent) {
        oEvent = window.event;
      }    
      oThis.handleKeyUp(oEvent);
    };
    
  };

/**
 * Selects a range of text in the textbox.
 * @scope public
 * @param iStart The start index (base 0) of the selection.
 * @param iLength The number of characters to select.
 */
 AutoSuggestControl.prototype.selectRange = function (iStart, iLength) {

    //use text ranges for Internet Explorer
    if (this.textbox.createTextRange) {
      var oRange = this.textbox.createTextRange(); 
      oRange.moveStart("character", iStart); 
      oRange.moveEnd("character", iLength - this.textbox.value.length);      
      oRange.select();

    //use setSelectionRange() for Mozilla
  } else if (this.textbox.setSelectionRange) {
    this.textbox.setSelectionRange(iStart, iLength);
  }     
    //set focus back to the textbox
    this.textbox.focus();      
  }; 

/**
 * Inserts a suggestion into the textbox, highlighting the 
 * suggested part of the text.
 * @scope private
 * @param sSuggestion The suggestion for the textbox.
 */
 AutoSuggestControl.prototype.typeAhead = function (sSuggestion /*:String*/) {

    //check for support of typeahead functionality
    if (this.textbox.createTextRange || this.textbox.setSelectionRange){
      var iLen = this.textbox.value.length; 
      this.textbox.value = sSuggestion; 
      this.selectRange(iLen, sSuggestion.length);
    }
  };

