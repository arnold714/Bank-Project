package MiniProject;

import java.util.Scanner;

import MiniProject.Members.MembersMenu;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		MembersMenu m = new MembersMenu();
		m.run(sc);
	}
}
