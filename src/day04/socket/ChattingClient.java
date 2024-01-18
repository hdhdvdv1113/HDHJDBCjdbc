package day04.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ChattingClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String address = "127.0.0.1";
		int port = 9999;
		InputStream is = null;
		DataInputStream dis = null;
		OutputStream os = null;
		DataOutputStream dos = null;
		Scanner sc = new Scanner(System.in);
		try {
			System.out.println("서버와 연결 중입니다.");
			Socket socket = new Socket(address, 9999);
			System.out.println("서버와 연결되었습니다.");
			is = socket.getInputStream(); // 소켓에서 해와야함
			os = socket.getOutputStream(); // 소켓에서 해와야함
			dis = new DataInputStream(is); // dis 쓰기위한객체만들기
			dos = new DataOutputStream(os); // dos 쓰기위한객체만들기
			while (true) {
				String recvMsg = dis.readUTF(); // recvMsg는 담는곳임
				if ("end".equalsIgnoreCase(recvMsg)) {
					System.out.println("서버에서 종료하였습니다.");
					break;
				}
				System.out.println("서버 (상대) :" + recvMsg);
				System.out.println("클라이언트 나 :");
				String sendMsg = sc.nextLine();
				dos.writeUTF(sendMsg);
				dos.flush();
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				is.close();
				dis.close();
				dos.close();
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
