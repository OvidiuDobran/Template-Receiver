package com.mainserver.gui;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.internal.win32.GESTURECONFIG;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import com.mainserver.app.ApplicationSession;
import com.mainserver.app.Receiver;

public class WelcomePage extends MyComposite implements Refreshable {

	public Combo typeCombo;

	public WelcomePage(Composite parent, int style) {
		super(parent, style);
	}

	@Override
	public void createContent() {
		setLayout(new FormLayout());

		Label welcome = new Label(this, SWT.NONE);
		welcome.setText("Welcome!");
		FontData fontData = welcome.getFont().getFontData()[0];
		Font font = new Font(getDisplay(), new FontData(fontData.getName(), 18, SWT.BOLD));
		welcome.setFont(font);

		Label typeLabel = new Label(this, SWT.NONE);
		typeLabel.setText("Choose the type of this receiver:");

		typeCombo = new Combo(this, SWT.NONE);

		FormData welcomeData = new FormData();
		welcomeData.top = new FormAttachment(0, 20);
		welcomeData.left = new FormAttachment(0, 20);
		welcome.setLayoutData(welcomeData);

		FormData typeLabelData = new FormData();
		typeLabelData.top = new FormAttachment(welcome, 20);
		typeLabelData.left = new FormAttachment(0, 20);
		typeLabel.setLayoutData(typeLabelData);

		FormData typeComboData = new FormData();
		typeComboData.top = new FormAttachment(welcome, 20);
		typeComboData.left = new FormAttachment(typeLabel, 10);
		typeCombo.setLayoutData(typeComboData);
		
		
	}

	@Override
	public void refresh() {
		ApplicationSession.getInstance().getApp().getReceiversFromDB();
		List<Receiver> receivers = ApplicationSession.getInstance().getApp().receivers;
		for (Receiver receiver : receivers) {
			typeCombo.add(receiver.getName());
		}
	}
	
	public boolean isItemSelected() {
		return typeCombo.getSelectionIndex() != -1;
	}

	public Receiver getSelectedReceiver() {
		for (Receiver receiver : ApplicationSession.getInstance().getApp().receivers) {
			if (receiver.getName().equals(typeCombo.getItem(typeCombo.getSelectionIndex()))) {
				return receiver;
			}
		}
		return null;
	}

}
