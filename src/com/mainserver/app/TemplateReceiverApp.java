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

	public void prepareToExit() {
		// TODO this method should save all the data that are worked with
	}

	public void getProblemsFromDB() {
		// TODO http
		Problem problem1 = new Problem((new Date()).toString(), new User("dobran_ovi@yahoo.com", "a_password_123"),
				"pisica in copac", "45.749455", "21.231243", Status.NEW);
		Problem problem2 = new Problem((new Date()).toString(), new User("app_client_user@yahoo.com", "a_password_123"),
				"pisica in copac", "45.749455", "21.231243", Status.NEW);
		Problem problem3 = new Problem((new Date()).toString(), new User("app_client_user@yahoo.com", "a_password_123"),
				"pisica in copac", "45.749455", "21.231243", Status.NEW);
		Problem problem4 = new Problem((new Date()).toString(), new User("app_client_user@yahoo.com", "a_password_123"),
				"pisica in copac", "45.749455", "21.231243", Status.NEW);

		problems = new ArrayList<Problem>();
		problems.add(problem1);
		problems.add(problem2);
		problems.add(problem3);
		problems.add(problem4);
	}

	public void getReceiversFromDB() {
		// TODO http
		receivers = new ArrayList<Receiver>();
		receivers.add(new Receiver("Politie"));
		receivers.add(new Receiver("Primarie"));
		receivers.add(new Receiver("Pompieri"));
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
	}

}