package classmanage.role.professor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.Scanner;

import org.json.JSONObject;

import classmanage.dto.MemberDto;

public class ChatProfessor {

	Scanner scan = new Scanner(System.in);

	public ChatProfessor(MemberDto user) {
		connect(user);
	}

	private void connect(MemberDto user) {
		String IP = "192.168.0.45";
		int PORT = 9000;
		Socket socket = null;
		PrintWriter pw = null;
		BufferedReader br = null;

		try {
			// 서버를 주소를 찾아서 연결
			socket = new Socket(IP, PORT);
			pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			System.out.println("------------채팅 서버 요청-------------");
			ReceiveThread rt = new ReceiveThread(br);
			rt.start();
			setUser(user, pw);
			Thread.sleep(300);

			boolean isRun = true;
			while (isRun) {
				System.out.println("1. 전체채팅");
				System.out.println("2. 나가기");
				System.out.print("번호입력 >> ");
				String sel = scan.next();
				switch (sel) {
				case "1":
					allChat(user, pw, scan);
					break;
				case "2":
					System.out.println("채팅 서비스 종료");
					isRun = false;
					break;
				default:
					break;
				}
			}

		} catch (

		Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if (socket != null)
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

	public static void setUser(MemberDto user, PrintWriter pw) {
		JSONObject idObj = new JSONObject();
		String dto = "";
		dto = user.getUserId() + "/" + user.getName();
		idObj.put("cmd", "ENTER");
		idObj.put("user", dto);

		String packet = idObj.toString();
		pw.println(packet);
		pw.flush();
	}

	public static void allChat(MemberDto user, PrintWriter pw, Scanner sc) {
		while (true) {
			JSONObject idObj = new JSONObject();
			String dto = "";
			System.out.print("메시지 입력 >> ");
			String msg = sc.next();
			dto = user.getUserId() + "/" + user.getName();
			idObj.put("cmd", "ALLCHAT");
			idObj.put("user", dto);
			idObj.put("msg", msg);
			String packet = idObj.toString();
			pw.println(packet);
			pw.flush();
			if (msg.equals("quit")) {
				System.out.println("채팅서비스 종료");
				break;
			}
		}
	}

}

class ReceiveThread extends Thread {
	private BufferedReader br = null;
	private MemberDto user;

	public void setUser(MemberDto user) {
		this.user = user;
	}

	public ReceiveThread(BufferedReader br) {
		this.br = br;
	}

	@Override
	public void run() {
		try {
			while (true) {
				String packet = br.readLine();
				if (packet == null)
					break;

				JSONObject packetObj = new JSONObject(packet);
				processPacket(packetObj);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void processPacket(JSONObject packetObj) throws UnsupportedEncodingException {
		String preCmd = packetObj.getString("cmd");
		String cmd = new String(preCmd.getBytes("utf-8"), "utf-8");
		// 서버의 id처리에 대한 응답
		
		if(preCmd.equals("BROADCHAT")) {
			String user = packetObj.getString("user");
			
			String msg = packetObj.getString("msg");
			System.out.println("\n" + user + ": " + msg);
		}

	}

}
