package com.mainserver.app;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ovi on 5/9/2018.
 */

public class HTTPPostHandler {

	/*
	 * public void main(String[] args) { try { String body =
	 * post("http://192.168.43.233:8081/user/getUserName", "UserId=2");
	 * body=body.substring(body.indexOf("[")+3, body.indexOf("]")-2);
	 * body=body.replace("\"",""); System.out.println(body);
	 * 
	 * } catch(IOException ioe) { ioe.printStackTrace(); } }
	 */

	public String post(String method, String data) {
		try {
			String postUrl = "http://192.168.43.233:8081/user/" + method;
			URL url = new URL(postUrl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");

			con.setDoOutput(true);

			this.sendData(con, data);

			return this.read(con.getInputStream());
		} catch (IOException e) {
			return null;
		}
	}

	protected void sendData(HttpURLConnection con, String data) throws IOException {
		DataOutputStream wr = null;
		try {
			wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(data);
			wr.flush();
			wr.close();
		} catch (IOException exception) {
			throw exception;
		} finally {
			this.closeQuietly(wr);
		}
	}

	private String read(InputStream is) throws IOException {
		BufferedReader in = null;
		String inputLine;
		StringBuilder body;
		try {
			in = new BufferedReader(new InputStreamReader(is));

			body = new StringBuilder();

			while ((inputLine = in.readLine()) != null) {
				body.append(inputLine);
			}
			in.close();

			return body.toString();
		} catch (IOException ioe) {
			throw ioe;
		} finally {
			this.closeQuietly(in);
		}
	}

	protected void closeQuietly(Closeable closeable) {
		try {
			if (closeable != null) {
				closeable.close();
			}
		} catch (IOException ex) {

		}
	}

	public List<Problem> getAllProblemsFromDB(Receiver receiver) {

		List<Problem> problems = new ArrayList<Problem>();
		String result = post("getAllProblemsForReceiver", "Data=Name=" + receiver.//
				getName());
		System.err.println(result);
		if (result.contains("[")) {
			result = result.substring(result.indexOf("["));
			String results[] = result.split("},");
			for (int i = 0; i < results.length; i++) {
				System.out.println(results[i]);
				String recs[] = results[i].split(",");
				/*
				 * for (String s:recs) { System.out.println(s); }
				 */
				for (int j = 0; j < recs.length; j++) {
					recs[j] = recs[j].substring(recs[j].indexOf(":") + 1);
					if (recs[j].contains("\"")) {
						recs[j] = recs[j].substring(recs[j].indexOf("\"") + 1, recs[j].length() - 1);
					}
					if (recs[j].contains("}")) {
						recs[j] = recs[j].substring(0, recs[j].indexOf("}"));
					}
					System.out.println(recs[j]);
				}

				User user = getUserById(Integer.parseInt(recs[1]));

				Problem problem = new Problem(Integer.parseInt(recs[0]), recs[4], user, recs[3], recs[5], recs[6],
						Status.NEW);
				problems.add(problem);

			}
		}
		return problems;
	}

	private User getUserById(int userId) {
		String result = post("getUserById", "Data=UserId=" + userId);
		System.out.println(result);
		String[] results = result.substring(result.indexOf("{")).split(",");
		System.out.println(results[0]);
		int id = Integer.parseInt(results[0].substring(results[0].indexOf("Id") + 4).trim());
		String email = results[1].substring(results[1].indexOf(":") + 1);
		email = email.substring(email.indexOf("\"") + 1, email.length() - 1);
		String password = results[2].substring(results[2].indexOf(":") + 1);
		password = password.substring(password.indexOf("\"") + 1, password.length() - 1);
		return new User(id, email, password);
	}

	public void solveProblem(Problem problem) {
		post("solveProblem", "Data=ProblemId=" + problem.getId());
	}

	public List<Receiver> getReceiversFromDB() {
		List<Receiver> receivers = new ArrayList<Receiver>();
		String result = post("getReceivers", "");
		System.out.println(result);

		result = result.//
				substring(result.indexOf("["));
		String results[] = result.split("},");
		for (int i = 0; i < results.length; i++) {
			System.out.println(results[i]);
			String recs[] = results[i].split(",");
			/*
			 * for (String s:recs) { System.out.println(s); }
			 */
			for (int j = 0; j < recs.length; j++) {
				recs[j] = recs[j].substring(recs[j].indexOf(":") + 1);
				if (recs[j].contains("\"")) {
					recs[j] = recs[j].substring(recs[j].indexOf("\"") + 1, recs[j].length() - 1);
				}
				if (recs[j].contains("}")) {
					recs[j] = recs[j].substring(0, recs[j].indexOf("}"));
				}
				System.out.println(recs[j]);
			}

			Receiver receiver = new Receiver(Integer.parseInt(recs[0]), recs[1]);
			receivers.add(receiver);

		}
		return receivers;
	}
}
