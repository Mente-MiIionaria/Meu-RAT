import java.io.*;
import java.text.DecimalFormat;
public class ExameNormal 
{
	
	public static void main (String [] args) throws IOException 
	{
		
		BufferedReader br = new BufferedReader (new InputStreamReader (System.in));
		DecimalFormat mt = new DecimalFormat ("###,###,##0.00 MTs");
		DecimalFormat usd = new DecimalFormat ("###,###,##0.00 USD");
		
		
		
		int quantA = 0, quantL = 0, quantE = 0, contm = 0, vTL = 0, vTA = 0, vTE = 0, cont0 = 0, cont2 = 0, cacau, quant, opcao, nivelacucar =0;
		final int LEITE = 300, AMARGO = 400, ESCURO = 500, MOLHOC = 100, CAMBIO = 64, RENDA = 5000;
		
		float valorpagar =0, desconto, iva, valorfinal, vTempresa = 0, valor, des = 0, vTd = 0, valorsemiva = 0, valoremfalta;
		final float IVA = 13/100f, DESCONTO1 = 43/100f, DESCONTO2 = 33/100f, ADD1 = 1/4f, ADD2 = 1/2f;
		
		char tpC,molhoC = 0;
		
		do {
			
			System.out.println ("\nBem vindo a Lindor!");
			
			System.out.println ("\n=================================================================================");
			System.out.println ("||--------------------------------------MENU-----------------------------------||");
			System.out.println ("|| 1-Receber os dados e calcular o valor a pagar                               ||");
			System.out.println ("|| 2-Visualizar o valor total de cada tipo                                     ||");
			System.out.println ("|| 3-Visualizar a quantidade total de cada tipo                                ||");
			System.out.println ("|| 4-Visualizar a quantidade de clientes que pediu caramelo                    ||");
			System.out.println ("|| 6-Visualizar o valor total de descontos                                     ||");
			System.out.println ("|| 5-Visualizar o valor total recebido pela empresa com iva                    ||");
			System.out.println ("|| 7-Verificar qual dos chocolates amargos e mais vendida                      ||");
			System.out.println ("|| 8-Verificar se a empresa tera valor suficiente para pagar a renda           ||");
			System.out.println ("|| 9-Sair do programa                                                          ||");
			System.out.println ("||=============================================================================||");
			System.out.println ("\nIntoduza a opcao desejada (1-7)");
			System.out.print ("opcao ->");opcao = Integer.parseInt(br.readLine () );
			
			switch (opcao) 
			{
				case 1:
					
					for (int contador = 0; contador < 2; contador ++) 
					{
						
					
						do 
						{
							
							System.out.println ("Introduza o tipo de chocolate (L-Leite / A-Amargo / E-Escuro)");
							tpC = br.readLine ().charAt(0);
							
							if (tpC != 'L' && tpC != 'A' && tpC != 'E')
								System.out.println ("ERROR, tente novamente!");
							
						}
						while (tpC != 'L' && tpC != 'A' && tpC != 'E');
						
						do 
						{
						   
							System.out.println ("Introduza a quantidade de clientes (>0)");
							quant = Integer.parseInt (br.readLine ());
							
							if (quant < 0)
								System.out.println ("ERROR, tente novamente!");
				
						}
						while (quant<0);
						
						
						switch (tpC) 
						{
								case 'L' :
									valorpagar = LEITE * quant;
									quantL += quant;
									
									do 
									{
										
										System.out.println ("Deseja molho de caramelo? (S-Sim / N-Nao)");
										molhoC = br.readLine ().charAt(0);
										
										if (molhoC != 'S' && molhoC != 'N')
											System.out.println ("ERROR, tente novamente!");
										
									}
									while (molhoC != 'S' && molhoC != 'N');
									
									if (molhoC == 'S')
										valorpagar = valorpagar + MOLHOC;
									    contm++;
									    vTL += valorpagar;
										
								break;
								
								case 'A' :
									valorpagar = AMARGO * quant;
									quantA += quant;
									
									do 
									{
										
										System.out.println ("Qual e o nivel de acucar que deseja (0 / 2)");
										nivelacucar = Integer.parseInt(br.readLine ());
										
										if (nivelacucar != 0 && nivelacucar != 2)
											System.out.println ("ERROR, tente novamente!");
										
									}
									while (nivelacucar != 0 && nivelacucar != 2);
									
									
									if (nivelacucar == 0) 
									{
										
										valor = valorpagar * ADD1;
										cont0 ++;
										
									}
									
									else 
									{
										valor = valorpagar * ADD2;
										cont2 ++;	
									}
									
									valorpagar += valor;
									vTA += vTA;
								break;
								
								case 'E' :
									valorpagar = ESCURO * quant;
									quantE += quant;
									
									do 
									{
										
										System.out.println ("Introduza a percentagem de cacau (30-90%)");
										cacau = Integer.parseInt(br.readLine ());
										
										if (cacau < 30 || cacau > 90)
											System.out.println ("ERROR, tente novamente!");
										
									}
									while (cacau < 30 || cacau > 90);
									
									if (cacau > 75)
										des = valorpagar * DESCONTO1;
									else 
										if (cacau > 50)
											des = valorpagar * DESCONTO2;
								    
									valorpagar -= des;
									vTE += valorpagar;
									vTd += des;
									
								break;
								
						}
						valorsemiva += valorpagar/CAMBIO;
						iva = valorpagar * IVA;
						valorfinal = valorpagar + iva;
						vTempresa += valorfinal;
						
						System.out.println ("O valor final a pagar e " +valorfinal);
					
					}
					
		
				break;
				
				case 2: System.out.println ("Valores totais de cada tipo:");
				        System.out.println ("Chocolate leite " +mt.format(vTL));
				        System.out.println ("Chocolate amargo " +mt.format(vTA));
				        System.out.println ("Chocolate escuro " +mt.format(vTE));
				break;
				
				case 3: System.out.println ("Quantidade total vendida de cada tipo");
						System.out.println ("Chocolate leite " +quantL);
				        System.out.println ("Chocolate amargo " +quantA);
				        System.out.println ("Chocolate escuro " +quantE);
				break;
				
				case 4: System.out.println ("A quantidade de cliente que pediu caramelo e " +contm);
			    break;
			    
				case 5: System.out.println ("O valor total de desconto e " +mt.format(vTd));
				break;
				
				case 6: System.out.println ("O valor total recebido da empresa com iva e " +mt.format(vTempresa));
				break;
				
				case 7: 
					if (cont0 > cont2)	
					   System.out.println ("O nivel de acucar 0 e o mais vendido");
					else
						if (cont2 > cont0)
							System.out.println ("O nivel de acucar 2 e o mais vendido");
					    else 
					    	System.out.println ("O nivel de acucar 0 e 2 sao os mais vendidos");
							
				break;
				
				case 8: 
					if (valorsemiva > RENDA)
					   System.out.println ("O valor da empresa e suficiente para pagar a renda");
					else
						System.out.println ("O valor da empresa nao e suficiente para pagar a renda");
					
					    valoremfalta = RENDA - valorsemiva;
					    System.out.println ("O valor em falta para cubrir a renda e " +usd.format(valoremfalta));
				break;
				
				case 9: System.out.println ("Muito obrigado por usar os nossos servicos");
				break;
				
				
				
			
				
			}
			
		}
		while (opcao !=9);
		
		
		
	}
}