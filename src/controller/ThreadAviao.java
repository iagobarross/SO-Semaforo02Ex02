/*2. Um aeroporto tem 2 pistas (norte e sul) e, em cada pista, apenas um avi�o pode fazer a
decolagem. O procedimento de decolagem tem 4 fases ( taxiar, decolagem e afastamento da �rea).
A fase de manobra pode durar de 300 a 700 milissegundos A fase de taxiar, de 500 a 1000
milissegundos. A fase de decolagem, de 600 a 800 milissegundos. A fase de afastamento, de 300 a
800 milissegundos. O aeroporto re�ne, por ciclo, 12 aeronaves que podem decolar pela pista norte
ou pela pista sul (decis�o aleat�ria) mas, apenas 2 avi�es podem circular pela �rea de decolagem
ao mesmo tempo. Fazer uma aplica��o em Java que resolva o problema.
*/

package controller;

import java.util.concurrent.Semaphore;

public class ThreadAviao extends Thread {

	private int idAviao;
	private static Semaphore semaforoAreaDecolagem = new Semaphore(2);
	private Semaphore semaforoNorte;
	private Semaphore semaforoSul;
	private double pistaRandom;
	
	

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
			semaforoAreaDecolagem.acquire();
			pistaRandom = Math.random();
			if(pistaRandom < 0.5) {
				try{
					semaforoNorte.acquire();
					decolar("norte");
				} catch (Exception e) {
					System.err.println(e.getMessage());
				} finally {
					semaforoNorte.release();
				}
			} else {
				try {
					semaforoSul.acquire();
					decolar("sul");
				} catch (Exception e) {
					System.err.println(e.getMessage());
				} finally {
					semaforoSul.release();
				}
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		} finally {
			semaforoAreaDecolagem.release();
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

	private void decolar(String pista) {
		int tempo = (int) ((Math.random() * 201) + 600);
		
		
		System.out.println("O avião " + idAviao + " decolou na pista " + pista + ", levando " + tempo + " ms. para decolar.");
				
		
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
