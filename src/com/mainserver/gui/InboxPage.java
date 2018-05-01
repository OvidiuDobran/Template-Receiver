package com.mainserver.gui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.mainserver.app.ApplicationSession;
import com.mainserver.app.Problem;

public class InboxPage extends MyComposite implements Behaviourable, Refreshable {

	protected Table table;
	private Problem selectedProblem;

	public InboxPage(Composite parent, int style) {
		super(parent, style);
		addBehaviours();
	}

	@Override
	public void createContent() {
		FormLayout formLayout = new FormLayout();
		setLayout(formLayout);
		table = new Table(this, SWT.BORDER | SWT.FULL_SELECTION | SWT.V_SCROLL | SWT.H_SCROLL);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);

		String[] titles = { "ID", "Time", "User", "Problem", "Status" };
		for (int i = 0; i < titles.length; i++) {
			TableColumn column = new TableColumn(table, SWT.CENTER);
			column.setText(titles[i]);
			column.setWidth(200);
		}

		refresh();

		FormData tableFormData = new FormData();
		tableFormData.top = new FormAttachment(0, 10);
		tableFormData.bottom = new FormAttachment(100, -30);
		tableFormData.left = new FormAttachment(0, 10);
		tableFormData.right = new FormAttachment(100, -30);
		table.setLayoutData(tableFormData);

	}

	@Override
	public void refresh() {
		//selectedProblem = null;
		populateTable();
	}

	private void populateTable() {
		table.removeAll();
		ApplicationSession.getInstance().getApp().getProblemsFromDB();
		List<Problem> problems = ApplicationSession.getInstance().getApp().problems;
		for (int i = 0; i < problems.size(); i++) {
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(0, problems.get(i).getId() + "");
			item.setText(1, problems.get(i).getDate());
			item.setText(2, problems.get(i).getUser().getEmail());
			item.setText(3, problems.get(i).getDescription());
			item.setText(4, problems.get(i).getStatus().toString());
		}
	}

	public boolean isItemSelected() {
		return table.getSelectionIndex() != -1;
	}

	@Override
	public void addBehaviours() {
		table.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				int index = table.getSelectionIndex();
				TableItem item = table.getItem(index);
				selectedProblem = ApplicationSession.getInstance().getApp().getProblemById((Integer.parseInt(item.getText(0))));
			}
		});
	}

	public Problem getSelectedProblem() {
		return selectedProblem;
	}

	public void setProblem(Problem problem) {
		this.selectedProblem = problem;
	}

}
