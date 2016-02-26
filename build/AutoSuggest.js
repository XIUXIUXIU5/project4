
window.onload = function () {
  var oTextbox = new AutoSuggestControl(document.getElementById("querybox")); 
}

// send Google suggest request based on the user input
function sendAjaxRequest(input,callback)
{
	var xmlHttp = new XMLHttpRequest(); // works only for Firefox, Safari, ...
  if (xmlHttp == null) {
      alert("Can't create XMLHttpRequest");
      callback([]);
      return;
   }
  var request = "/eBay/suggest?q="+encodeURI(input);

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
  this.cur = -1;
  this.layer = null;
  this.textbox = oTextbox;
  this.init();
}

AutoSuggestControl.prototype.hideSuggestions = function () {
    this.layer.style.visibility = "hidden";
};


AutoSuggestControl.prototype.highlightSuggestion = function (oSuggestionNode) {

    for (var i=0; i < this.layer.childNodes.length; i++) {
        var oNode = this.layer.childNodes[i];
        if (oNode == oSuggestionNode) {
            oNode.className = "current"
        } else if (oNode.className == "current") {
            oNode.className = "";
        }
    }
};


AutoSuggestControl.prototype.createDropDown = function () {

    this.layer = document.createElement("div");
    this.layer.className = "suggestions";
    this.layer.style.visibility = "hidden";
    this.layer.style.width = this.textbox.offsetWidth;
    document.body.appendChild(this.layer);

    var oThis = this;

    this.layer.onmousedown = this.layer.onmouseup = 
    this.layer.onmouseover = function (oEvent) {
        oEvent = oEvent || window.event;
        oTarget = oEvent.target || oEvent.srcElement;

        if (oEvent.type == "mousedown") {
            oThis.textbox.value = oTarget.firstChild.nodeValue;
            oThis.hideSuggestions();
        } else if (oEvent.type == "mouseover") {
            oThis.highlightSuggestion(oTarget);
        } else {
            oThis.textbox.focus();
        }
    };

};

AutoSuggestControl.prototype.getLeft = function () {

    var oNode = this.textbox;
    var iLeft = 0;

    while(oNode.tagName != "BODY") {
        iLeft += oNode.offsetLeft;
        oNode = oNode.offsetParent; 
    }

    return iLeft;
};


AutoSuggestControl.prototype.getTop = function () {

    var oNode = this.textbox;
    var iTop = 0;

    while(oNode.tagName != "BODY") {
        iTop += oNode.offsetTop;
        oNode = oNode.offsetParent; 
    }

    return iTop;
};

AutoSuggestControl.prototype.showSuggestions = function (aSuggestions) {

    var oDiv = null;
    this.layer.innerHTML = "";

    for (var i=0; i < aSuggestions.length; i++) {
        oDiv = document.createElement("div");
        oDiv.appendChild(document.createTextNode(aSuggestions[i]));
        this.layer.appendChild(oDiv);
    }

    this.layer.style.left = this.getLeft() + "px";
    this.layer.style.top = (this.getTop()+this.textbox.offsetHeight) + "px";
    this.layer.style.visibility = "visible";
};

AutoSuggestControl.prototype.previousSuggestion = function () {
    var cSuggestionNodes = this.layer.childNodes;

    if (cSuggestionNodes.length > 0 && this.cur > 0) {
        var oNode = cSuggestionNodes[--this.cur];
        this.highlightSuggestion(oNode);
        this.textbox.value = oNode.firstChild.nodeValue; 
    }
};

AutoSuggestControl.prototype.nextSuggestion = function () {
    var cSuggestionNodes = this.layer.childNodes;

    if (cSuggestionNodes.length > 0 && this.cur < cSuggestionNodes.length-1) {
        var oNode = cSuggestionNodes[++this.cur];
        this.highlightSuggestion(oNode);
        this.textbox.value = oNode.firstChild.nodeValue; 
    }
};

AutoSuggestControl.prototype.handleKeyDown = function (oEvent) {
    switch(oEvent.keyCode) {
        case 38: //up arrow
            this.previousSuggestion();
            break;
        case 40: //down arrow 
            this.nextSuggestion();
            break;
        case 13: //enter
            this.hideSuggestions();
            break;
    }
};
/**
 * Autosuggests one or more suggestions for what the user has typed.
 * If no suggestions are passed in, then no autosuggest occurs.
 * @scope private
 * @param aSuggestions An array of suggestion strings.
 */
 AutoSuggestControl.prototype.autosuggest = function (aSuggestions) {

    if (aSuggestions.length > 0) {
        this.showSuggestions(aSuggestions);
    } else {
        this.hideSuggestions();
    }
};

/**
 * Handles keyup events.
 * @scope private
 * @param oEvent The event object for the keyup event.
 */
 AutoSuggestControl.prototype.handleKeyUp = function (oEvent /*:Event*/) {

  var iKeyCode = oEvent.keyCode;
  var oThis = this;

    if (iKeyCode == 8 || iKeyCode == 46) {
        sendAjaxRequest(this.textbox.value, function(result){ 
          oThis.autosuggest(result);
        });
    } else if (iKeyCode < 32 || (iKeyCode >= 33 && iKeyCode <= 46) || (iKeyCode >= 112 && iKeyCode <= 123)) {
        //ignore
      } else {
        sendAjaxRequest(this.textbox.value, function(result){ 
          oThis.autosuggest(result);
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
    
    this.textbox.onkeydown = function (oEvent) {

        if (!oEvent) {
            oEvent = window.event;
        } 

        oThis.handleKeyDown(oEvent);
    };

    this.textbox.onblur = function () {
        oThis.hideSuggestions();
    };

    this.createDropDown();
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

