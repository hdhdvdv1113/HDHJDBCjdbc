package day04.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ChattingServer {

	public static void main(String[] args) {
		// 네트워크 프로그래밍
		ServerSocket serverSocket = null;
		int port = 9999;
		InputStream is = null;
		OutputStream os = null;
		DataInputStream dis = null;
		DataOutputStream dos = null;
		Scanner sc = new Scanner(System.in);
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("클라이언트의 응답을기다리고있습니다....");
			Socket socket = serverSocket.accept(); // 여기서 기다리고있다
			System.out.println("클라이언트와 접속되었습니다.");
			// os = new FileOutputStream(""); 이거안됨 왜안되는진 녹음기
			is = socket.getInputStream();
			os = socket.getOutputStream(); // 소켓에서 해와야함
			dis = new DataInputStream(is);
			dos = new DataOutputStream(os); // dos 쓰기위한객체만들기
			while (true) {
				System.out.println("서버(나)");
				String sendMsg = sc.nextLine();
				if ("end".equalsIgnoreCase(sendMsg)) {
					dos.writeUTF("end");
					dos.flush();
					System.out.println("채팅을 종료하였습니다.");
					break;
				}
				dos.writeUTF(sendMsg); // 이렇게 보내주는게있어야client에서받을수잇음
				dos.flush(); // 이렇게 보내주는게있어야client에서받을수잇음
				String recvMsg = dis.readUTF(); // recvMsg는 담는곳임
				System.out.println("받은 메시지 " + recvMsg);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				dos.close();
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}