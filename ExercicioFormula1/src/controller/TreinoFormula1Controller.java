package controller;

import java.util.Arrays;
import java.util.concurrent.Semaphore;

public class TreinoFormula1Controller extends Thread
{
	private int idCar;
	private Semaphore semaforo;
	private Semaphore semaforo2;
	private static int posicao = 0;
	private static String[] tempoVolta = new String[14];

	public TreinoFormula1Controller(int idCar, Semaphore semaforo,Semaphore semaforo2 ) 
	{
		this.idCar = idCar;
		this.semaforo = semaforo;
		this.semaforo2 = semaforo2;
	}

	@Override
	public void run() 
	{
		try 
		{
			semaforo.acquire();
			corrida();
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		} 
		finally 
		{
			semaforo.release();			
		}
		
		if (posicao == 14) 
		{
			ranking();
		}
	}

	private void corrida()
	{		
		double tempoT;
		double maior = Integer.MAX_VALUE;
		for (int i = 0; i < 3; i++)
		{
			double tempo = ((Math.random() * 5) + 2);
			
			try 
			{
				sleep((long) tempo);
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
			
			tempoT = tempo;
			System.out.println("Volta " + (i + 1) + " do carro " + idCar + " tempo " + tempoT);
			
			if (maior > tempoT) 
			{
				maior = tempoT;
			}
		}
		
		try 
		{
			semaforo2.acquire(); 
			tempoVolta[posicao] = ("Com o tempo de " + maior + " o Carro " + idCar+ "\n");
			System.out.println(posicao+ " " + idCar);
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		finally 
		{
			semaforo2.release();
		}		
		posicao++;
	}
	
	private void ranking() 
	{
		System.out.println("==================================");
		System.out.println("Posição do Grid para a largada da corrida !");
		System.out.println("==================================");
		
		Arrays.sort(tempoVolta);
		
		for (int i = 0; i < tempoVolta.length; i++) 
		{
			System.out.println("Na " + (i + 1) + "° Posição ");
			System.out.println(tempoVolta[i]);
		}
	}
}
