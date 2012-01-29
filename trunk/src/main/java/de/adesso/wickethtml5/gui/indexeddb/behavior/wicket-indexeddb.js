/**
 * IndexedDB namespace.
 */
if (typeof(IndexedDB) == "undefined"){
	IndexedDB = { };
}

/**
 * This class provides functionality to extract data from a form and store it into the IndexedDB.
 * By calling IndexedDB.Form(formId, dbName, storeName, storeVersion, keyPath)
 * the submit function of the form is overriden so that the data will be redirected
 * to the IndexedDB.
 */
IndexedDB.Form = function(formId, dbName, storeName, storeVersion, keyPath, redirectUrl) {
	
	var logger = new IndexedDB.Logger();
	
	var indexedDB = new IndexedDB.DB(dbName, storeName, storeVersion, keyPath);

	var init = function() {
		logger.logInfo('overriding form submit'); 
		var form = $("#" + formId);
		form.submit(function(event) {
			event.preventDefault();

			var onsuccess = function(event) {
				redirect();
			};
			var onerror = function(event) {
				console.log(event);
			}
			storeForm(onsuccess, onerror);
		});
	};
	var storeForm = function(onsuccess, onerror) {
		var formData = getFormData();
		indexedDB.onstore = function(data){
			logger.logInfo("data successfully stored in IndexedDB: " + JSON.stringify(data));
			onsuccess();
		}
		indexedDB.oninitialized = function(db){
			indexedDB.store(db, formData);
		}
		indexedDB.onerror = onerror; 
		indexedDB.initialize();
	}
	var getFormData = function() {
		logger.logInfo("extracting form data...");
		var form = $("#" + formId);
		var data = {};
		form.find("input").each(function(index){
			if($(this).attr("type") != "submit" && $(this).attr("type") != "hidden"){ 
				var name = $(this).attr("name");
				var value = $(this).val();
				data[name] = value;
			}
		});
		logger.logInfo("form data extracted: " + JSON.stringify(data)); 
		return data;
	}
	var redirect = function() {
		window.location.href = redirectUrl;
	}
	/**
	 * Object initialization.
	 */
	init();
	return true;
}


/**
 * This class abstracts from the IndexedDB API to provide functionality for
 * IndexedDB.Form and IndexedDB.Table.
 */
IndexedDB.DB = function(dbName, storeName, storeVersion, keyPath) {
	
	var logger = new IndexedDB.Logger();
	
	var self = this;

	this.oninitialized = function(db){};
	
	this.onerror = function(e){};
	
	this.onstore = function(data){};
	
	this.initialize = function() {
		
		logger.logInfo("storing form data...");
		var indexedDB = window.indexedDB || window.webkitIndexedDB || window.mozIndexedDB;
		if ('webkitIndexedDB' in window) {
			window.IDBTransaction = window.webkitIDBTransaction;
			window.IDBKeyRange = window.webkitIDBKeyRange;
		}

		var request = indexedDB.open(dbName, "");
		var db;
		request.onsuccess = function(event) {
			db = event.target.result;
			logger.logInfo("existing database version: " + db.version + "; needed database version: " + storeVersion);
			if(db.version != storeVersion) {
				// user needs a fresh db
				logger.logInfo("new database version - (re)creating database (contents will be lost)");
				var versionRequest = db.setVersion(storeVersion);
				versionRequest.onerror = this.onerror;
				versionRequest.onsuccess = function(e) {
					if(db.objectStoreNames.contains(storeName)) {
						db.deleteObjectStore(storeName);
					}
					var store = db.createObjectStore(storeName, {
						keyPath : "lastName"
					});
					self.oninitialized(db);
				}
			} else {
				// user already has a db of the correct version
				logger.logInfo("database is up to date...");
				self.oninitialized(db);
			}
		}
		request.onerror = self.onerror;
	}
	
	this.store = function(db, data){
		logger.logInfo("storing in IndexedDB: " + JSON.stringify(data));
	
		var tx = db.transaction([storeName], IDBTransaction.READ_WRITE);
		var store = tx.objectStore(storeName);
		var request = store.put(data);
		
		request.onsuccess = function(e){
			self.onstore(data);
		}
		
		request.onerror = self.onerror;
	}
	
	this.each = function(db, oneach){
		var tx = db.transaction([storeName], IDBTransaction.READ_WRITE);
		var store = tx.objectStore(storeName);
		var keyRange = IDBKeyRange.lowerBound(0);
		var cursorRequest = store.openCursor(keyRange);
		cursorRequest.onsuccess = oneach;
		cursorRequest.onerror = self.onerror;
	}
}

IndexedDB.Table = function(tableId, dbName, storeName, storeVersion, keyPath) {

	var logger = new IndexedDB.Logger();
	
	var indexedDB = new IndexedDB.DB(dbName, storeName, storeVersion, keyPath);
	
	var init = function(){
		$(document).ready(function(){
			logger.logInfo("initializing dataTable..."); 
			var table = $("#" + tableId).dataTable();
			loadData();
		});
	}
	
	var loadData = function(){
		logger.logInfo("loading data into table...");
		
		indexedDB.oninitialized = function(db){
			indexedDB.each(db, function(e){
				var result = e.target.result;
				logger.logInfo("result of IndexedDB query: " + JSON.stringify(result));
				if(result == false || result == null){ 
					return;
				}
				var data = result.value;
				logger.logInfo("data loaded from IndexedDB: " + JSON.stringify(result));
				var tableData = [];
				for(var property in data){
					tableData.push(data[property]);
				}
				$("#" + tableId).dataTable().fnAddData(tableData);
				result.continue();
			});
		};
		
		indexedDB.onerror = function(e){
			logger.logError("error loading data from IndexedDB: " + JSON.stringify(e));
		}
		
		indexedDB.initialize();
		
	}
	
	/**
	 * Object initialization.
	 */
	init();
	return true;
}


/**
 * This class wraps Wicket's JavaScript logger. If Wicket's JavaScript
 * files are not available, it will simply do nothing.
 */
IndexedDB.Logger = function() {

	this.logEnabled = function() {
		if( typeof (WicketAjaxDebug) == "undefined") {
			return false;
		} else {
			return true;
		}
	}

	this.logInfo = function(msg) {
		if(this.logEnabled())
			WicketAjaxDebug.logInfo(msg);
	};

	this.logError = function(msg) {
		if(this.logEnabled())
			WicketAjaxDebug.logError(msg);
	};

	this.logDebug = function(msg) {
		if(this.logEnabled())
			WicketAjaxDebug.logDebug(msg);
	};
}