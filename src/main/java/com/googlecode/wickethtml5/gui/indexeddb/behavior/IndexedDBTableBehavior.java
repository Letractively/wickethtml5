package com.googlecode.wickethtml5.gui.indexeddb.behavior;

import java.text.MessageFormat;

import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.WicketAjaxReference;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WicketEventReference;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

import com.googlecode.wickethtml5.gui.util.JQueryDataTablesReference;
import com.googlecode.wickethtml5.gui.util.JQueryReference;
import com.googlecode.wickethtml5.gui.util.WicketAjaxDebugReference;


public class IndexedDBTableBehavior extends Behavior {

	private String dbName;
	private String storeName;
	private String storeVersion;
	private String keyPath;

	public IndexedDBTableBehavior(String dbName, String storeName, String storeVersion, String keyPath) {
		this.dbName = dbName;
		this.storeName = storeName;
		this.storeVersion = storeVersion;
		this.keyPath = keyPath;
	}

	@Override
	public void renderHead(Component component, IHeaderResponse response) {

		response.renderJavaScriptReference(JQueryReference.INSTANCE);
		response.renderJavaScriptReference(JQueryDataTablesReference.INSTANCE);

		response.renderJavaScriptReference(new JavaScriptResourceReference(IndexedDBFormBehavior.class,
		    "wicket-indexeddb.js"));

		String tableMarkupId = component.getMarkupId(true);
		response.renderOnDomReadyJavaScript(MessageFormat.format(
		    "var myIndexedDBTable = new IndexedDB.Table(''{0}'',''{1}'',''{2}'',''{3}'',''{4}'');", tableMarkupId, dbName,
		    storeName, storeVersion, keyPath));

		Application application = WebApplication.get();
		if (application.getDebugSettings().isAjaxDebugModeEnabled()) {
			response.renderJavaScriptReference(WicketEventReference.INSTANCE);
			response.renderJavaScriptReference(WicketAjaxReference.INSTANCE);
			response.renderJavaScriptReference(WicketAjaxDebugReference.INSTANCE);
			response.renderJavaScript("wicketAjaxDebugEnable=true;", "wicket-ajax-debug-enable");
		}

	}

	@Override
	public void bind(Component component) {
		// the component needs an ID for the javascript to hook in
		component.setOutputMarkupId(true);
	}

}
