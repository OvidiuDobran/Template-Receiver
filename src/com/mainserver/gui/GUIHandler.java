package com.mainserver.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import com.mainserver.app.ApplicationSession;
import com.mainserver.app.Receiver;

public class GUIHandler {
	private Display display;
	private Shell shell;
	private int buttonWidth = 70;
	private int buttonHeight = 30;
	private WelcomePage welcomePage;
	private InboxPage inboxPage;
	private Composite contentPanel;
	private StackLayout layout;
	private DetailsPage detailsPage;
	private Button refresh;
	private Button next;
	private Button exit;
	private Button back;

	public GUIHandler() {
		setDisplay(new Display());
		setShell(new Shell(getDisplay(), SWT.ALT | SWT.MIN | SWT.CLOSE));
	}

	public void run() {
		shell.setText("Template Receiver");
		shell.setLayout(new FormLayout());

		contentPanel = new Composite(shell, SWT.BORDER);
		// contentPanel.setBounds(10, 10, 300, 300);
		layout = new StackLayout();
		contentPanel.setLayout(layout);

		welcomePage = new WelcomePage(contentPanel, SWT.NONE);
		inboxPage = new InboxPage(contentPanel, SWT.NONE);
		detailsPage = new DetailsPage(contentPanel, SWT.NONE);
		changeToPage(welcomePage);

		FormData dataContentPanel = new FormData();
		dataContentPanel.top = new FormAttachment(0, 0);
		dataContentPanel.right = new FormAttachment(100, 0);
		dataContentPanel.left = new FormAttachment(0, 0);
		dataContentPanel.bottom = new FormAttachment(100, -70);
		contentPanel.setLayoutData(dataContentPanel);

		exit = new Button(shell, SWT.NONE);
		exit.setText("Exit");
		FormData exitData = new FormData();
		exitData.bottom = new FormAttachment(100, -10);
		exitData.right = new FormAttachment(100, -10);
		exitData.width = buttonWidth;
		exitData.height = buttonHeight;
		exit.setLayoutData(exitData);

		next = new Button(shell, SWT.NONE);
		next.setText("Next");
		FormData nextData = new FormData();
		nextData.bottom = new FormAttachment(100, -10);
		nextData.right = new FormAttachment(exit, -20);
		nextData.width = buttonWidth;
		nextData.height = buttonHeight;
		next.setLayoutData(nextData);

		back = new Button(shell, SWT.NONE);
		back.setText("Back");
		FormData backData = new FormData();
		backData.bottom = new FormAttachment(100, -10);
		backData.right = new FormAttachment(next, -20);
		backData.width = buttonWidth;
		backData.height = buttonHeight;
		back.setLayoutData(backData);
		back.setEnabled(false);
		back.setVisible(false);

		refresh = new Button(shell, SWT.NONE);
		refresh.setText("Refresh");
		FormData refereshData = new FormData();
		refereshData.bottom = new FormAttachment(100, -10);
		refereshData.right = new FormAttachment(back, -20);
		refereshData.width = buttonWidth;
		refereshData.height = buttonHeight;
		refresh.setLayoutData(refereshData);
		refresh.setEnabled(false);
		refresh.setVisible(false);

		addBehaviours();
		welcomePage.refresh();

		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				// If no more entries in event queue
				display.sleep();
			}
		}
		display.dispose();
	}

	private void addBehaviours() {
		exit.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				shell.close();
			}
		});

		welcomePage.typeCombo.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				String receiverName = welcomePage.typeCombo.getItem(welcomePage.typeCombo.getSelectionIndex());
				Receiver receiverWithName = ApplicationSession.getInstance().getApp().getReceiverByName(receiverName);
				ApplicationSession.getInstance().getApp().receiver=receiverWithName;
				shell.setText(receiverWithName.getName());
			}
		});

		next.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event arg0) {

				if (layout.topControl == welcomePage) {
					if (welcomePage.isItemSelected()) {
						changeToPage(inboxPage);
						inboxPage.refresh();
						refresh.setEnabled(true);
						refresh.setVisible(true);
						back.setEnabled(true);
						back.setVisible(true);
					} else {
						MessageBox messageBox = new MessageBox(shell, SWT.ICON_WARNING);
						messageBox.setText("Select a receiver");
						messageBox.setMessage("You need to select a receiver to continue");
						messageBox.open();
					}

				} else if (layout.topControl == inboxPage) {
					if (inboxPage.isItemSelected()) {
						refresh.setEnabled(false);
						refresh.setVisible(false);
						changeToPage(detailsPage);
						detailsPage.updateProblemToDisplay(inboxPage.getSelectedProblem());
						detailsPage.refresh();
						next.setText("Solve");
					} else {
						MessageBox messageBox = new MessageBox(shell, SWT.ICON_WARNING);
						messageBox.setText("Select a record");
						messageBox.setMessage("You need to select a record to continue");
						messageBox.open();
					}
				} else if (layout.topControl == detailsPage) {
					ApplicationSession.getInstance().getApp().solveProblem(detailsPage.getProblem());
					MessageBox messageBox = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
					messageBox.setText("Problem solved");
					messageBox.setMessage("The problem was solved");

					int buttonId = messageBox.open();
					if (buttonId == SWT.OK) {
						changeToPage(inboxPage);
						next.setText("Next");
						inboxPage.refresh();
						refresh.setEnabled(true);
						refresh.setVisible(true);
						back.setEnabled(true);
						back.setVisible(true);
					}

				}
			}
		});

		back.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				if (layout.topControl == inboxPage) {
					changeToPage(welcomePage);
					back.setEnabled(false);
					back.setVisible(false);
					refresh.setEnabled(false);
					refresh.setVisible(false);
				} else if (layout.topControl == detailsPage) {
					changeToPage(inboxPage);
					inboxPage.refresh();
					refresh.setEnabled(true);
					refresh.setVisible(true);
				}
			}
		});

		refresh.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				inboxPage.refresh();
			}
		});
	}

	private void changeToPage(Composite page) {
		layout.topControl = page;
		contentPanel.layout();
	}

	public Display getDisplay() {
		return display;
	}

	public void setDisplay(Display display) {
		this.display = display;
	}

	public Shell getShell() {
		return shell;
	}

	public void setShell(Shell shell) {
		this.shell = shell;
	}

}
