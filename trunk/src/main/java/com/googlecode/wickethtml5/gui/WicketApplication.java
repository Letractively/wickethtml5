package com.googlecode.wickethtml5.gui;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

import com.googlecode.wickethtml5.gui.indexeddb.IndexedDBFormPage;
import com.googlecode.wickethtml5.gui.indexeddb.IndexedDBTablePage;


public class WicketApplication extends WebApplication {

	@Override
	public Class<? extends Page> getHomePage() {
		return Homepage.class;
	}

	@Override
	protected void init() {
		super.init();
		getComponentInstantiationListeners().add(getSpringInjector());
		getDebugSettings().setAjaxDebugModeEnabled(true);
		getMarkupSettings().setDefaultBeforeDisabledLink("<a class=\"selected\">");
		getMarkupSettings().setDefaultAfterDisabledLink("</a>");
		mountPage("/indexedDBForm", IndexedDBFormPage.class);
		mountPage("/indexedDBTable", IndexedDBTablePage.class);

	}

	protected SpringComponentInjector getSpringInjector() {
		return new SpringComponentInjector(this);
	}

}
