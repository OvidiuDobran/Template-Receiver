package com.mainserver.gui;

import org.eclipse.swt.widgets.Composite;

import com.mainserver.app.ApplicationSession;

public abstract class MyComposite extends Composite{
	
	protected GUIHandler guiHandler=ApplicationSession.getInstance().getGuiHandler();
	
	public MyComposite(Composite parent, int style) {
		super(parent, style);
		createContent();
	}
	
	public abstract void createContent();
	

}
