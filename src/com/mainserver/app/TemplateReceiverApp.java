package com.mainserver.app;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class TemplateReceiverApp {

	public List<Problem> problems;
	public List<Receiver> receivers;
	public Receiver receiver;

	public TemplateReceiverApp() {
		problems = new ArrayList<Problem>();
		receivers = new ArrayList<Receiver>();
	}

	public static void main(String[] args) {
		TemplateReceiverApp app = ApplicationSession.getInstance().getApp();
		app.run();
	}

	private void run() {
		ApplicationSession.getInstance().getGuiHandler().run();
	}

	public void openInBrowser(String longitude, String latitude) {
		if (Desktop.isDesktopSupported()) {
			try {
				Desktop.getDesktop().browse(new URI("https://www.google.com/maps/?q=" + longitude + "," + latitude));
			} catch (IOException | URISyntaxException e) {
				e.printStackTrace();
			}
		}
	}

	public void getProblemsFromDB() {
		if (receiver != null) {
			problems = ApplicationSession.getInstance().getPostHandler().getAllProblemsFromDB(receiver);
		}
	}

	public void getReceiversFromDB() {
		// TODO http
		/*
		 * receivers = new ArrayList<Receiver>(); receivers.add(new
		 * Receiver("Politie")); receivers.add(new Receiver("Primarie"));
		 * receivers.add(new Receiver("Pompieri"));
		 */
		receivers = ApplicationSession.getInstance().getPostHandler().getReceiversFromDB();
	}

	public Problem getProblemById(int id) {
		for (Problem problem : problems) {
			if (problem.getId() == id) {
				return problem;
			}
		}
		return null;
	}

	public Receiver getReceiverByName(String name) {
		for (Receiver receiver : receivers) {
			if (receiver.getName().equals(name)) {
				return receiver;
			}
		}
		return null;
	}

	public void solveProblem(Problem problem) {
		// TODO in the DB, change the status of the given problem from AT_RECEIVER to
		// HANDLED
		ApplicationSession.getInstance().getPostHandler().solveProblem(problem);
	}

}