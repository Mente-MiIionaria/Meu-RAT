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
			
			System.out.println ("\n*** Bem-vindo a Lindor! ***");
			
			System.out.println ("\n===== MENU PRINCIPAL =====");
			System.out.println ("1. Fazer pedido");
			System.out.println ("2. Ver valor total por tipo");
			System.out.println ("3. Ver quantidade vendida por tipo");
			System.out.println ("4. Ver clientes com caramelo");
			System.out.println ("5. Ver descontos totais");
			System.out.println ("6. Ver receita total (com IVA)");
			System.out.println ("7. Tipo amargo mais vendido");
			System.out.println ("8. Verificar renda");
			System.out.println ("9. Sair");
			System.out.println ("===========================");
			System.out.print ("Escolha uma opcao (1-9): ");
			opcao = Integer.parseInt(br.readLine () );
			
			switch (opcao) 
			{
				case 1:
					
					for (int contador = 0; contador < 2; contador ++) 
					{
						
					
						do 
						{
							
							System.out.print ("Tipo de chocolate (L/A/E): ");
							tpC = br.readLine ().charAt(0);
							
							if (tpC != 'L' && tpC != 'A' && tpC != 'E')
								System.out.println ("Invalido! Digite L, A ou E");
							
						}
						while (tpC != 'L' && tpC != 'A' && tpC != 'E');
						
						do 
						{
						   
							System.out.print ("Quantidade (>0): ");
							quant = Integer.parseInt (br.readLine ());
							
							if (quant < 0)
								System.out.println ("Quantidade deve ser positiva!");
				
						}
						while (quant<0);
						
						
						switch (tpC) 
						{
								case 'L' :
									valorpagar = LEITE * quant;
									quantL += quant;
									
									do 
									{
										
										System.out.print ("Quer caramelo? (S/N): ");
										molhoC = br.readLine ().charAt(0);
										
										if (molhoC != 'S' && molhoC != 'N')
											System.out.println ("Digite S ou N");
										
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
										
										System.out.print ("Nivel de acucar (0/2): ");
										nivelacucar = Integer.parseInt(br.readLine ());
										
										if (nivelacucar != 0 && nivelacucar != 2)
											System.out.println ("Digite 0 ou 2");
										
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
										
										System.out.print ("Percentagem de cacau (30-90): ");
										cacau = Integer.parseInt(br.readLine ());
										
										if (cacau < 30 || cacau > 90)
											System.out.println ("Digite entre 30 e 90");
										
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
						
						System.out.println (">> Valor a pagar: " +valorfinal+ " MTs");
					
					}
					
		
				break;
				
				case 2: System.out.println ("\n--- Valores Totais ---");
				        System.out.println ("Leite: " +mt.format(vTL));
				        System.out.println ("Amargo: " +mt.format(vTA));
				        System.out.println ("Escuro: " +mt.format(vTE));
				break;
				
				case 3: System.out.println ("\n--- Quantidade Vendida ---");
						System.out.println ("Leite: " +quantL);
				        System.out.println ("Amargo: " +quantA);
				        System.out.println ("Escuro: " +quantE);
				break;
				
				case 4: System.out.println ("\nClientes com caramelo: " +contm);
			    break;
			    
				case 5: System.out.println ("\nDescontos totais: " +mt.format(vTd));
				break;
				
				case 6: System.out.println ("\nReceita com IVA: " +mt.format(vTempresa));
				break;
				
				case 7: 
					if (cont0 > cont2)	
					   System.out.println ("\nNivel 0 mais vendido");
					else
						if (cont2 > cont0)
							System.out.println ("\nNivel 2 mais vendido");
					    else 
					    	System.out.println ("\nNiveis 0 e 2 igual");
							
				break;
				
				case 8: 
					if (valorsemiva > RENDA)
					   System.out.println ("\nRenda OK");
					else
						System.out.println ("\nRenda insuficiente");
					
					    valoremfalta = RENDA - valorsemiva;
					    System.out.println ("Falta: " +usd.format(valoremfalta));
				break;
				
				case 9: System.out.println ("\nAte logo!");
				break;
				
				
				
			
				
			}
			
		}
		while (opcao !=9);
		
		
		
	}
}
