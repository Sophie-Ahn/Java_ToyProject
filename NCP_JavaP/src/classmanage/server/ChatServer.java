package classmanage.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import classmanage.dto.MemberDto;

public class ChatServer {
	
	public static void main(String[] args) {
		final int PORT = 9000;
		Hashtable<String, Socket> clientHt = new Hashtable<>(); // 접속자를 관리하는 테이블

		try {
			ServerSocket serverSocket = new ServerSocket(PORT);
			String mainThreadName = Thread.currentThread().getName();
			/* main thread는 계속 accept()처리를 담당한다 */
			while (true) {
				System.out.printf("[서버-%s] Client접속을 기다립니다...\n", mainThreadName);
				Socket socket = serverSocket.accept();
				WorkerThread wt = new WorkerThread(socket, clientHt);
				wt.start();
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}

class WorkerThread extends Thread {
	private Socket socket;
	private Hashtable<String, Socket> ht;

	public WorkerThread(Socket socket, Hashtable<String, Socket> clientHt) {
		this.socket = socket;
		this.ht = clientHt;
	}

	@Override
	public void run() {
		try {
			MemberDto user = new MemberDto();
			InetAddress inetAddr = socket.getInetAddress();
			System.out.printf("<서버-%s>%s로부터 접속했습니다.\n", getName(), inetAddr.getHostAddress());
			OutputStream out = socket.getOutputStream();
			InputStream in = socket.getInputStream();
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			while (true) {
				String line = br.readLine();
				if (line == null)
					break;
				JSONObject packetObj = new JSONObject(line);
				processPacket(packetObj);
			}
		} catch (Exception e) {
			System.out.printf("<서버-%s>%s\n", getName(), e.getMessage());
		}

	}
	
	private void processPacket(JSONObject packetObj) throws IOException {
		
		JSONObject ackObj = new JSONObject();
		String cmd = packetObj.getString("cmd");
		
		// id 입장
		if(cmd.equals("ENTER")) {
			String user = packetObj.getString("user");
			System.out.println(user + " 님이 입장하셨습니다.");
		
			ackObj.put("cmd", "ENTER");
			ackObj.put("user", user);
			ackObj.put("ack", "ok");
			String ack = ackObj.toString();
			OutputStream out = this.socket.getOutputStream();
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));
			ht.put(user, this.socket);
			pw.println(ack);
			pw.flush();
			
		}else if(cmd.equals("ALLCHAT")){
			String user = packetObj.getString("user");
			String msg = packetObj.getString("msg");
			System.out.println(user + "님: " + msg);
			
			ackObj.put("cmd", "ALLCHAT");
			ackObj.put("ack", "ok");
			String ack = ackObj.toString();
			OutputStream out = this.socket.getOutputStream();
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));
			pw.println(ack);
			pw.flush();
			
			JSONObject broadObj = new JSONObject();
			broadObj.put("cmd", "BROADCHAT");
			broadObj.put("user", user);
			broadObj.put("msg", msg);
			String strBroad = broadObj.toString();
			// 전체 전송
			broadcast(strBroad);
		}
	
	}

	private void broadcast(String packet) throws IOException {
		Set<String> idSet = ht.keySet();
		Iterator<String> idIter = idSet.iterator();
		while(idIter.hasNext()) {
			String id = idIter.next();
			Socket sock = (Socket) ht.get(id);
			
			// 메시지를 보내온 클라이언트한테는 보낼 필요가 없으므로
			if(sock==this.socket)
				continue;
			
			OutputStream out = sock.getOutputStream();
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));
			pw.println(packet);
			pw.flush();
		}
	}

}
