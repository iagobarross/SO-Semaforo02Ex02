/*2. Um aeroporto tem 2 pistas (norte e sul) e, em cada pista, apenas um avião pode fazer a
decolagem. O procedimento de decolagem tem 4 fases ( taxiar, decolagem e afastamento da área).
A fase de manobra pode durar de 300 a 700 milissegundos A fase de taxiar, de 500 a 1000
milissegundos. A fase de decolagem, de 600 a 800 milissegundos. A fase de afastamento, de 300 a
800 milissegundos. O aeroporto reúne, por ciclo, 12 aeronaves que podem decolar pela pista norte
ou pela pista sul (decisão aleatória) mas, apenas 2 aviões podem circular pela área de decolagem
ao mesmo tempo. Fazer uma aplicação em Java que resolva o problema.
*/

//in progress
package controller;

import java.util.concurrent.Semaphore;

public class ThreadAviao extends Thread {

	private int idAviao;
	private Semaphore semaforoNorte;
	private Semaphore semaforoSul;

	public ThreadAviao(int idAviao, Semaphore semaforoNorte, Semaphore semaforoSul) {
		this.idAviao = idAviao;
		this.semaforoNorte = semaforoNorte;
		this.semaforoSul = semaforoSul;
	}

	@Override
	public void run() {
		manobrar();
		taxiar();
		try {
			semaforoNorte.acquire();
			decolar();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		} finally {
			semaforoNorte.release();
		}
		afastar();
	}

	private void manobrar() {
		int tempo = (int) ((Math.random() * 401) + 300);
		
		System.out.println("O avião " + idAviao + " manobrou por " + tempo + " ms.");
		
		try {
			sleep(tempo);
		} catch (InterruptedException e) {
			System.err.println(e.getMessage());
		}
	}

	private void taxiar() {
		int tempo = (int) ((Math.random() * 501) + 500);
		
		System.out.println("O avião " + idAviao + " taxiou por " + tempo + " ms.");

		try {
			sleep(tempo);
		} catch (InterruptedException e) {
			System.err.println(e.getMessage());
		}
	}

	private void decolar() {
		int tempo = (int) ((Math.random() * 201) + 600);
		boolean decolagem = false;
		
		try {
			if(!decolagem) {
				semaforoNorte.acquire();
				System.out.println("O avião " + idAviao + " decolou na pista norte, levando " + tempo + " ms. para decolar.");
				decolagem = true;
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		} finally {
			semaforoNorte.release();
		}
		
		try {
			if(!decolagem) {
				semaforoSul.acquire();
				System.out.println("O avião " + idAviao + " decolou na pista sul, levando " + tempo + " ms. para decolar.");
				decolagem = true;
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		} finally {
			semaforoSul.release();
		}
		
		try {
			sleep(tempo);
		} catch (InterruptedException e) {
			System.err.println(e.getMessage());
		}
	}

	private void afastar() {
		int tempo = (int) ((Math.random() * 501) + 300);
		
		System.out.println("O avião " + idAviao + " se afastou da área por " + tempo + " ms.");
		
		try {
			sleep(tempo);
		} catch (InterruptedException e) {
			System.err.println(e.getMessage());
		}
	}

}
