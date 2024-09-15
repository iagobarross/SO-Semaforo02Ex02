package view;

import java.util.concurrent.Semaphore;

import controller.ThreadAviao;

public class Main {

	public static void main(String[] args) {
		int permissoes = 1;
		Semaphore semaforoNorte = new Semaphore(permissoes);
		Semaphore semaforoSul = new Semaphore(permissoes);
		
		for(int i = 0; i < 12; i++) {
			Thread t = new ThreadAviao(i+1, semaforoNorte, semaforoSul);
			t.start();
		}
	}

}
