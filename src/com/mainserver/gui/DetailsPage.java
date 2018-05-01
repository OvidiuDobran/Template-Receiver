package com.mainserver.gui;

import java.lang.reflect.MalformedParametersException;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.internal.win32.GESTURECONFIG;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;

import com.mainserver.app.ApplicationSession;
import com.mainserver.app.Problem;
import com.mainserver.app.Receiver;

public class DetailsPage extends MyComposite implements Behaviourable, Refreshable {

	private Text textTime;
	private Text textUser;
	private Text textDescription;
	private Text textLong;
	private Text textLat;
	private Problem problem;
	private Text textId;
	private Button mapsButton;

	public DetailsPage(Composite parent, int style) {
		super(parent, style);
		addBehaviours();
	}

	@Override
	public void createContent() {
		FormLayout formLayout = new FormLayout();
		setLayout(formLayout);

		int textWidth = 300;

		Label labelId = new Label(this, SWT.NONE);
		labelId.setText("ID");
		FormData labelIdData = new FormData();
		labelIdData.top = new FormAttachment(0, 20);
		labelIdData.left = new FormAttachment(0, 20);
		labelId.setLayoutData(labelIdData);

		textId = new Text(this, SWT.BORDER | SWT.READ_ONLY);
		FormData textIdData = new FormData();
		textIdData.top = new FormAttachment(0, 20);
		textIdData.left = new FormAttachment(labelId, 80);
		textIdData.width = textWidth;
		textId.setLayoutData(textIdData);

		Label labelTime = new Label(this, SWT.NONE);
		labelTime.setText("Time:");
		FormData labelTimeData = new FormData();
		labelTimeData.top = new FormAttachment(labelId, 20);
		labelTimeData.left = new FormAttachment(0, 20);
		labelTime.setLayoutData(labelTimeData);

		textTime = new Text(this, SWT.BORDER | SWT.READ_ONLY);
		FormData textTimeData = new FormData();
		textTimeData.top = new FormAttachment(labelId, 20);
		textTimeData.left = new FormAttachment(labelId, 80);
		textTimeData.width = textWidth;
		textTime.setLayoutData(textTimeData);

		Label labelUser = new Label(this, SWT.NONE);
		labelUser.setText("User:");
		FormData labelUserData = new FormData();
		labelUserData.top = new FormAttachment(labelTime, 20);
		labelUserData.left = new FormAttachment(0, 20);
		labelUser.setLayoutData(labelUserData);

		textUser = new Text(this, SWT.BORDER | SWT.READ_ONLY);
		FormData textUserData = new FormData();
		textUserData.top = new FormAttachment(labelTime, 20);
		textUserData.left = new FormAttachment(labelId, 80);
		textUserData.width = textWidth;
		textUser.setLayoutData(textUserData);

		Label labelDescription = new Label(this, SWT.NONE);
		labelDescription.setText("Description:");
		FormData labelDescriptionData = new FormData();
		labelDescriptionData.top = new FormAttachment(labelUser, 20);
		labelDescriptionData.left = new FormAttachment(0, 20);
		labelDescription.setLayoutData(labelDescriptionData);

		textDescription = new Text(this,
				SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.READ_ONLY);
		FormData textDescriptionData = new FormData();
		textDescriptionData.top = new FormAttachment(labelUser, 20);
		textDescriptionData.left = new FormAttachment(labelId, 80);
		textDescriptionData.width = 300;
		textDescriptionData.height = 100;
		textDescription.setLayoutData(textDescriptionData);

		Label labelLong = new Label(this, SWT.NONE);
		labelLong.setText("Longitude:");
		FormData labelLongData = new FormData();
		labelLongData.top = new FormAttachment(textDescription, 20);
		labelLongData.left = new FormAttachment(0, 20);
		labelLong.setLayoutData(labelLongData);

		textLong = new Text(this, SWT.BORDER | SWT.READ_ONLY);
		FormData textLongData = new FormData();
		textLongData.top = new FormAttachment(textDescription, 20);
		textLongData.left = new FormAttachment(labelId, 80);
		textLongData.width = textWidth;
		textLong.setLayoutData(textLongData);

		Label labelLat = new Label(this, SWT.NONE);
		labelLat.setText("Latitude:");
		FormData labelLatData = new FormData();
		labelLatData.top = new FormAttachment(labelLong, 20);
		labelLatData.left = new FormAttachment(0, 20);
		labelLat.setLayoutData(labelLatData);

		textLat = new Text(this, SWT.BORDER | SWT.READ_ONLY);
		FormData textLatData = new FormData();
		textLatData.top = new FormAttachment(labelLong, 20);
		textLatData.left = new FormAttachment(labelId, 80);
		textLatData.width = textWidth;
		textLat.setLayoutData(textLatData);

		mapsButton = new Button(this, SWT.NONE);
		mapsButton.setText("Show On Maps");
		FormData mapsButtonData = new FormData();
		mapsButtonData.top = new FormAttachment(textLat, 20);
		mapsButtonData.left = new FormAttachment(labelTime, 60);
		mapsButton.setLayoutData(mapsButtonData);

	}

	public void updateProblemToDisplay(Problem problem) {
		this.setProblem(problem);
	}

	@Override
	public void refresh() {
		if (getProblem() != null) {
			textId.setText(getProblem().getId() + "");
			textTime.setText(getProblem().getDate());
			textUser.setText(getProblem().getUser().getEmail());
			textDescription.setText(getProblem().getDescription());
			textLong.setText(getProblem().getLongitude());
			textLat.setText(getProblem().getLatitude());

		}
	}

	@Override
	public void addBehaviours() {
		mapsButton.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				ApplicationSession.getInstance().getApp().openInBrowser(textLong.getText(), textLat.getText());
			}
		});

	}

	public Problem getProblem() {
		return problem;
	}

	public void setProblem(Problem problem) {
		this.problem = problem;
	}

}
