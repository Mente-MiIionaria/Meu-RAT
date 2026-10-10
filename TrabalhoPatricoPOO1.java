
import java.io.*;
import java.text.DecimalFormat;
public class TrabalhoPatricoPOO1
{
   public static void main (String [] args) throws IOException 
   {
	   menu();
   }
   
   public static void menu () throws IOException 
   {
	   float vf,d,iva,vd = 0,vp = 0;
	   char operadora,tpI,tPacote, equipm;
	   int contT = 0, contD = 0, contZ = 0,contBasico = 0,contFamilia = 0, contPremium = 0,contFacil = 0,contFamilia2 = 0,contPremium2 = 0,contMin = 0,contFamilia3 = 0,contMax = 0, quantTV = 0;
	   int numcliente, opcao, nTv,nMeses,quant = 0,numSmartCard,adicio = 0;
	   int acumT = 0 , acumD = 0 , acumZ = 0, acumG = 0, vTDesc = 0, vTIva = 0,vtBasico = 0,vtFamilia = 0,vtPremium = 0,vtFacil = 0,vtFamilia2 = 0,vtPremium2 = 0,vtMin = 0,vtFamilia3 = 0,vtMax = 0;
	
	   
	   
	   System.out.println ("Bem vindo a aplicacao para gerir operacoes de servicos de televisao por subscricao !");
	   
	   
	   do 
	   {
		   System.out.println ("\n===================================================================================================================");
		   System.out.println ("||********************************************MENU*****************************************************************||");
		   System.out.println ("=====================================================================================================================");
		   System.out.println ("||1.Receber o dados do cliente e visualizar no ecra em forma de tabela( apenas nas opcoes 1 e 2 de cada operadora) ||");
		   System.out.println ("||2.Visualizar a quantidade de clientes de cada operadora                                                          ||");
		   System.out.println ("||3.Visualizar o valor total recebido por cada operadora                                                           ||");
		   System.out.println ("||4.Visualizar o valor total recebido                                                                              ||");
		   System.out.println ("||5.Visualizar o valor total do desconto                                                                           ||");
		   System.out.println ("||6.Visualizar o valor total do IVA                                                                                ||");
		   System.out.println ("||7.TVCABO, a quatidade de clientes de cada pacote e valor total recebido por cada pacote                          ||");
		   System.out.println ("||8.TVCABO, visualizar o pacote que mais rendeu                                                                    ||");
		   System.out.println ("||9.TVCABO, a quantidade total de equipamentos adicional que foi vendida                                           ||");
		   System.out.println ("||10.DSTV, a quantidade de clientes de cada pacote e o valor total recebido em cada pacote                         ||");
		   System.out.println ("||11.DSTV, visualizar o pacote que mais rendeu                                                                     ||");
		   System.out.println ("||12.ZAP, a quatidade de clientes de cada pacote e valor total recebido por cada pacote                            ||");
		   System.out.println ("||13.ZAP, visualizar o pacote que mais rendeu                                                                      ||");
		   System.out.println ("||14.Quantidade total de novos clientes que aderiram aos servicos de televisao                                     ||");
		   System.out.println ("||15.Verificar se o valor ira chegar ou nao, presentar quanto falta ou resta de lucro                              ||");
		   System.out.println ("||16.Dados dos programadores                                                                                       ||");
		   System.out.println ("||17.Sair do programa                                                                                              ||");
		   System.out.println ("=====================================================================================================================");
		   
		   System.out.println("\nDigite a opcao desejada (1-17): ");
		   System.out.print("->");opcao = validarInt(1,17);
		   
		   switch (opcao)
		   {
		   case 1:
			   System.out.println("Introduza o tipo de operadora (T-TVCabo / D-DSTV / Z-ZAP): ");
			   operadora = validarChar1 ('t','d','z','T','D','Z');
			   
			   System.out.println("Introduza o numero de cliente (111111111-999999999): ");
			   numcliente = validarInt (111111111,999999999);
					   
			  
			switch(operadora)
			   {
			     case 't':
			     case 'T':
			    	 do
			    	 {
			    		 System.out.println("Bem vindo a operadora TVCabo");
			    		 
			    		 
				         System.out.println("1. Nova Instalacao da TVCabo");
				         System.out.println("2. Renovacao de Pacote da TVCabo");
				         System.out.println("3. Equipamento adicional");
				         System.out.println("4. Sair da operadora TVCabo");
				         
                         System.out.println("Digite a opcao desejada (1-4): ");
				         opcao = validarInt(1,4);
			         
			         switch(opcao)
			         {
			           case 1:
			        	   System.out.println("Introduza o tipo de instalacao (R-Residencial / E-Empresarial): ");
			        	   tpI = validarChar2('r','e','R','E');
			        	   
			        	   System.out.println("Introduza o numero de TVs (>0): ");
			        	   nTv = validarValorInt();
			        	   
			        	   if (nTv == 'S' && nTv == 's')
			        		   quantTV++;
			        	  
			        	   vp = calcularVp1(tpI,nTv);
			        	   visualizarvp (vp);
			        	   break;
			        	   
			           case 2:
			        	   System.out.println("Introduza os tipos de pacotes (B-Basico / F-Familia / P-Premium): ");
			        	   tPacote = validarChar1('b','f','p','B','F','P');
			        	   
			        	   switch (tPacote)
			        	   {
				        	   case 'b':
				    		   case 'B':
				    			  contBasico++;break;
				    			   
				    		   case 'f':
				    		   case 'F':
				    			   contFamilia++;break;
				    			   
				    		   case 'p':
				    		   case 'P':
				    			   contPremium++;break;
			        	   }
			        	   
			        	   System.out.println("Introduza o numero de meses (1-12): ");
			        	   nMeses = validarInt(1,12);
			        	   
			        	   vp = calcularVp2(tPacote, nMeses);
			        	   d = calcularDesconto(vp);
			        	   vTDesc += d;
			        	   vp = calcularFloat (vp,d);   
			        	   visualizarvp (vp);
			        	   break;
			        	   
			           case 3:
			        	   System.out.println("Deseja equipamento? (S-Sim / N-Nao)");
			        	   equipm = validarChar2('s','n','S','N');
			        	   
			        	   System.out.println("Introduza a quantidade de equipamentos (>0): ");
			        	   quant = validarValorInt();
			        	   
			        	   adicio = calcularAdicionais (quant,operadora,equipm);
			        	   
			        	   vp = calcularValorComAdicional (vd,adicio);
			        	  
			        	   visualizarvp (vp);
			        	   break;
			        	   
			        	   
			           case 4:
			        	   System.out.println("Voltaste ao menu");break;
			        	  
			         }
				         
			        
			    	 }
			    	 while (opcao != 4);
			    	 
			    	 contT ++;
			    	 iva = calcularIva (vp);
			    	 vf = calcularValorComIva (vd,iva);
			    	 acumT+=vf;
			    	 vTIva +=iva;
			    	 visualizarvp (vf);
			     break;
			     
			     case 'd':
			     case 'D':
			    	 do
			    	 {
			    		 System.out.println("Bem vindo a operadora DSTV");
			    		 
				         System.out.println("1. Activar novo cliente");
				         System.out.println("2. Renovacao de Pacote da DSTV");
				         System.out.println("3. Adicionar segundo descodificador");
				         System.out.println("4. Sair da operadora DSTV");
				         
                         System.out.println("Digite a opcao desejada (1-4): ");
				         opcao = validarInt (1,4);
			         
				         
			         switch(opcao)
			         {
			           case 1:
			        	  
			        	   System.out.println("Introduza o numero de SmartCadr (111111111-999999999): ");
			        	   numSmartCard = validarInt (111111111,999999999);
			        	   
			        	   System.out.println("Introduza o tipo de instalacao (N-Normal / C-Completo): ");
			        	   tpI = validarChar2 ('n','c','N','C');
			        	   
			        	   vp = calcularVp3 (tpI);
			        	   visualizarvp (vp);
			        	   break;
			           case 2:
			        	   System.out.println("Introduza o numero de SmartCadr (111111111-999999999): ");
			        	   numSmartCard = validarInt (111111111,999999999);
			        	   
			        	   System.out.println("Introduza os tipos de pacotes (C-Facil / M-Familia / P-Premium): ");
			        	   tPacote = validarChar1 ('c','m','p','C','M','P');
			        	   
			        	   System.out.println("Introduza o numero de meses (1-12): ");
			        	   nMeses = validarInt (1,12);
			        	   
			        	   vp = calcularVp2 (tPacote,nMeses);
			        	   visualizarvp (vp);	   
			        	   break;
			        	   
			           case 3:
			        	   System.out.println("Deseja um segundo ou mais descodificadores? (S-Sim / N-Nao)");
			        	   equipm = validarChar2('s','n','S','N');
			        	   
			        	   System.out.println("Introduza a quantidade de descodificadores (>0): ");
			        	   quant = validarValorInt();
			        	   
			        	   adicio = calcularAdicionais (quant,operadora,equipm);
			        	   vp = calcularValorComAdicional (vp,adicio);
			        	   visualizarvp (vp);
			        	   break;
			        	 
			           case 4:
			        	   System.out.println("Voltaste ao menu");break;
			         }
			         
				         
			    	 }
			    	 while (opcao != 4);
			    	 contD ++;
			    	 iva = calcularIva (vp);
			    	 vf = calcularValorComIva (vp,iva);
			    	 acumD+=vf;
			    	 vTIva +=iva;
			    	 visualizarvp (vp);
			     break;
			     
			     case 'z':
			     case 'Z':
			    	 do
			    	 {
			    		 System.out.println("Bem vindo a operadora ZAP");
			    		 
				         System.out.println("1. Nova Instalacao da ZAP");
				         System.out.println("2. Renovacao de Pacote da ZAP");
				         System.out.println("3. Desesja descodificador");
				         System.out.println("4. Sair da operadora ZAP");
                         System.out.println("Digite a opcao desejada (1-4): ");
				         opcao = validarInt (1,4);
			         
			         switch(opcao)
			         {
			           case 1:
			        	   System.out.println("Introduza o tipo de instalacao (R-Residencial / E-Empresarial): ");
			        	   tpI = validarChar2('m','t','M','T');
			        	   
			        	   vp = calcularVp3 (tpI);
			        	   break;
			        	   
			           case 2:
			        	   System.out.println("Introduza os tipos de pacotes (L-Familia / N-Mini / M-Maxi): ");
			        	   tPacote = validarChar1 ('l','n','m','L','N','M');
			        	   
			        	   System.out.println("Introduza o numero de meses (1-12): ");
			        	   nMeses = validarInt (1,12);
			        	   
			        	   vp = calcularVp2 (tPacote,nMeses);
			        	   
			        	   d = calcularDesconto (vp,nMeses);
			        	   
			        	   vTDesc +=d;
			        	   
			        	   vp = calcularFloat (vp,d);
			        	   
			        	   
			        	   break;
			        	   
			           case 3:
			        	   System.out.println("Deseja um segundo ou mais descodificadores? (S-Sim / N-Nao)");
			        	   equipm = validarChar2('s','n','S','N');
			        	   
			        	   System.out.println("Introduza a quantidade de descodificadores (>0): ");
			        	   quant = validarValorInt();
			        	   
			        	   adicio = calcularAdicionais (quant,operadora,equipm);
			        	   vp = calcularValorComAdicional (vp,adicio);
			        	   visualizarvp (vp);
			        	   break;
			        	
			           case 4:
			        	   System.out.println("Voltaste ao menu");break;
			         }
			         
			    	 }
			    	 while (opcao != 4);
			    	 
			    	 contZ++;
			    	 iva = calcularIva (vp);
			    	 vf = calcularValorComIva (vp,iva);
			    	 acumZ +=vf;
			    	 vTIva +=iva;
			    	 visualizarvp (vf);
			     break;
			   }
			   
			   acumG +=vp;
			   
			   break;
			   
		   case 2: visualizarcontadores (contT,contD,contZ);
			   break;
		   case 3: visualizaracumuladores (acumT,acumD,acumZ);
			   break;
		   case 4: visualizarvalorTotal (acumG);
			   break;
		   case 5: visualizarTotalDesconto (vTDesc);
			   break;
		   case 6: visualizarToatalIva (vTIva);
			   break;
		   case 7: visualizarPacotesTVCABO(contBasico,contFamilia,contPremium,vtBasico,vtFamilia,vtPremium);
			   break;
		   case 8: visualizarSituacao(vtBasico,vtFamilia,vtPremium,opcao);
			   break;
		   case 9: visualizarQuantAdicional (quant);
			   break;
		   case 10:visualizarPacotesDSTV(contFacil,contFamilia2,contPremium2,vtFacil,vtFamilia2,vtPremium2);
			   break;
		   case 11: visualizarSituacao(vtFacil,vtFamilia2,vtPremium2,opcao);
			   break;
		   case 12: visualizarPacotesZAP(contMin,contFamilia3,contMax,vtMin,vtFamilia3,vtMax);
			   break;
		   case 13: visualizarSituacao(vtMin,vtFamilia3,vtMax,opcao);
			   break;
		   case 14: visualizarTotalServicosTV (quantTV);
			   break;
		   case 15: verificarLucro (acumG);
			   break;
		   case 16: dadosdosprogramadores();
			   break;
		   case 17: System.out.println ("Obrigado por utilizar os nossos servicos, Volte sempre!!");
			   break;
		   }
	   }
	   while (opcao !=17);
   }
   

   
  

   //Viualizacoes
    public static void dadosdosprogramadores()
    {
        System.out.println("=====================================================================");
        System.out.println("||    Dados    dos    Programadores    ||     Dados dos Docentes   ||");
        System.out.println("||=====================================||==========================||");
        System.out.println("||    Alaine General    -   20260665   ||     Eng. Darmite Meggi   ||");
        System.out.println("||    Wendy Mangaze     -   20260835   ||     Eng. Abel Ernesto    ||");
        System.out.println("||    Kleyton Abelardo  -   20250671   ||      -    -    -    -    ||");
        System.out.println("=====================================================================");
    }
   
   public static void verificarLucro (int acumG)
   {
	  
	   DecimalFormat mt = new DecimalFormat ("###,###,###,###,###.00");
	   if (acumG > 1000000 )	   
		   System.out.println ("A empresa tem lucro de: "+mt.format(acumG - 1000000));
	   else
		   System.out.println ("A empresa esta em prejuizo de: " +mt.format(1000000-acumG));	   
	   
   }
   
   public static void visualizarTotalServicosTV (int quantTV)
   {
	   System.out.println(); 
   }
   
   public static void visualizarQuantAdicional (int quant)
   {
	   System.out.println();
   }
   public static void  visualizarSituacao (int a,int b,int c,int opcao)
   {
	   boolean maiorA = false, maiorB = false,maiorC = false;
	   
	   switch (opcao)
	   {
	   
	   case 8:
	   if (a > b && a > c) 
		{
			System.out.println ("O pacote basico foi o que mais rendeu");
            maiorA = true;            			
		}
		else
			if (b > a && b > c) 
			{
				System.out.println ("O pacote familia foi o que mais rendeu");
				maiorB = true;
			}
		   else
		   {
			   System.out.println ("O pacote premium foi o que mais rendeu");
			   maiorC = true;
		   }
	   break;
	   
	   case 11:
		   if (a < b && a < c) 
			{
				System.out.println ("O pacote facil foi o que menos rendeu");
	            maiorA = true;            			
			}
			else
				if (b < a && b < c) 
				{
					System.out.println ("O pacote familia foi o que menos rendeu");
					maiorB = true;
				}
			   else
			   {
				   System.out.println ("O pacote premium foi o que menos rendeu");
				   maiorC = true;
			   }
		   break;
		   
	   case 13:
		   if (a > b && a > c) 
			{
				System.out.println ("O pacote mini foi o que mais rendeu");
	            maiorA = true;            			
			}
			else
				if (b < a && b < c) 
				{
					System.out.println ("O pacote familia foi o que mais rendeu");
					maiorB = true;
				}
			   else
			   {
				   System.out.println ("O pacote max foi o que mais rendeu");
				   maiorC = true;
			   }
		   break;
	   }
			   
   }
   public static void visualizarPacotesZAP(int contMin,int contFamilia3,int contMax,int vtMin,int vtFamilia3,int vtMax)
   {
	   System.out.println("A Quantidade de clientes que aderiram ao pacote Mini e de:"+contMin);
	   System.out.println("A Quantidade de clientes que aderiram ao pacote Familia e de:"+contFamilia3);
	   System.out.println("A Quantidade de clientes que aderiram ao pacote Max e de:"+contMax);
	   System.out.println("O Valor total de clientes que aderiram ao pacote Mini e de:"+vtMin);
	   System.out.println("O Valor total de clientes que aderiram ao pacote Familia e de:"+vtFamilia3);
	   System.out.println("O Valor totalde clientes que aderiram ao pacote Max e de:"+contMax);
   }
   public static void visualizarPacotesDSTV(int contFacil,int contFamilia2,int contPremium2,int vtFacil,int vtFamilia2,int vtPremium2)
   {
	   System.out.println("A Quantidade de clientes que aderiram ao pacote Facil e de:"+contFacil);
	   System.out.println("A Quantidade de clientes que aderiram ao pacote Familia e de:"+contFamilia2);
	   System.out.println("A Quantidade de clientes que aderiram ao pacote Premium e de:"+contPremium2);
	   System.out.println("O Valor total de clientes que aderiram ao pacote Facil e de:"+vtFacil);
	   System.out.println("O Valor total de clientes que aderiram ao pacote Familia e de:"+vtFamilia2);
	   System.out.println("O Valor totalde clientes que aderiram ao pacote Premium e de:"+contPremium2);
   }
   public static void  visualizarPacotesTVCABO(int contBasico,int contFamilia, int contPremium,int vtBasico,int vtFamilia,int vtPremium)
   {
	   System.out.println("A Quantidade de clientes que aderiram ao pacote Basico e de:"+contBasico);
	   System.out.println("A Quantidade de clientes que aderiram ao pacote Familia e de:"+contFamilia);
	   System.out.println("A Quantidade de clientes que aderiram ao pacote Premium e de:"+contPremium);
	   System.out.println("O Valor total de clientes que aderiram ao pacote Basico e de:"+vtBasico);
	   System.out.println("O Valor total de clientes que aderiram ao pacote Familia e de:"+vtFamilia);
	   System.out.println("O Valor totalde clientes que aderiram ao pacote Premium e de:"+contPremium);
   }
   public static void visualizarcontadores (int contT, int contD, int contZ)
   {
	   System.out.println("A quantidade de clientes da TVCabo e de:" +contT);
	   System.out.println("A quantidade de clientes da DSTV e de:" +contD);
	   System.out.println("A quantidade de clientes da ZAP e de:" +contZ);
   }
   
   
   public static void visualizaracumuladores(int acumT, int acumD, int acumZ)
   {
	   System.out.println("O Valor total recebido pela TVCabo e de:" +acumT);
	   System.out.println("O Valor total recebido pela DSTV e de:" +acumD);
	   System.out.println("O Valor total recebido pela ZAP e de:" +acumZ);
   }
   
   
   public static void visualizarvalorTotal(int acumG)
   {
	   System.out.println("O Valor total recebido e:" +acumG);
   }
   
   
   public static void visualizarTotalDesconto( int vTDesc)
   {
	   System.out.println("O Valor total do desconto e de:" +vTDesc);
   }
   
   
   public static void visualizarToatalIva (int vTIva)
   {
	   System.out.println("O Valor total do IVA e de:" +vTIva);
   }
   
   
   public static void visualizarvf (float vf)
   {
	   System.out.println ("O valor final a pagar e: " +vf);
   }
   
   public static void visualizarvp (float a)
   {
	   DecimalFormat mt = new DecimalFormat ("###,###,###,###.00 MTS");
	   System.out.println ("O valor final a pagar e: " +mt.format (a));
	   
   }
   
   
   //Validacoes
   public static int validarInt (int a,int b) throws IOException 
   {
	   int op;
	   BufferedReader br = new BufferedReader ( new InputStreamReader (System.in));
	   
	   do 
	   {
		   op = Integer.parseInt(br.readLine());
		   
		   if (op < a || op > b)
			   System.out.println ("ERROR! Tente Novamente!");
		   
	   }
	   while (op < a || op > b);
	   
	   return op;
   }
   
   public static char validarChar1 (char b1,char b2,char b3,char b4,char b5,char b6) throws IOException 
   {
	   char tp;
	   BufferedReader br = new BufferedReader ( new InputStreamReader (System.in));  
	   
	   do
	   {
		   tp = br.readLine().charAt(0);
		   
		   if (tp != b1 && tp != b2 && tp != b3 && tp != b4 && tp != b5 && tp != b6)
			   System.out.println ("ERROR! Tente Novamente!");
		   
	   }
	   while (tp != b1 && tp != b2 && tp != b3 && tp != b4 && tp != b5 && tp != b6);
	   
	   return tp;	   
	   
   }
   
   
   public static char validarChar2 (char a1,char a2,char a3,char a4) throws IOException 
   {
	   char tp;
	   BufferedReader br = new BufferedReader ( new InputStreamReader (System.in));  
	   
	   do
	   {
		   tp = br.readLine().charAt(0);
		   
		   if (tp != a1 && tp != a2 && tp != a3 && tp != a4)
			   System.out.println ("ERROR! Tente Novamente!");
		   
	   }
	   while (tp != a1 && tp != a2 && tp != a3 && tp != a4);
	   
	   return tp;	   
	   
   }
   
   
   public static int validarValorInt () throws IOException 
   {
	   int v;
	   BufferedReader br = new BufferedReader ( new InputStreamReader (System.in)); 
	   
	   do
	   {
		   v = Integer.parseInt(br.readLine ());
		   
		   if (v<0)
			   System.out.println ("ERROR! Tente Novamente!");
	   }
	   while (v<0);
	   
	   return v;
   }

   
   //Calculos
   public static float calcularDesconto (float vp, int b)
   {
	   float d;
	   final float D1 = 10/100f, D2 = 7/100f, D3 = 3/100f;
	   
	   if (b > 12)
		   d = vp * D1;
	   else 
		   if (b > 6)
			   d = vp * D2;
		   else
			   d = vp * 3;
	   
	   return 0;
		   
   }
   
   
   public static float calcularDesconto(float a)
   {
	   final float D = 5/100f;
	   
	   return a * D;
   }
   
   
   public static float calcularFloat (float a, float b)
   {
	   return a - b;
   }
 
   
   public static float calcularValorComAdicional (float a, int b)
   {
	   return a + b;
   }
   
   
   public static int calcularAdicionais (int a,char operadora, char equipm) 
   {
	  final short TVCABO = 2500, DSTV = 1800, ZAP = 2200; 
	  
	  if (equipm == 'n' || equipm == 'N') 
		  return 0;
	  
	  else 
	  {
		  
		  switch (operadora)
		  {
		  case 't':
		  case 'T':
			  return TVCABO * a;
			  
		  case 'd':
		  case 'D':
			  return DSTV * a;
			  
		  case 'z':
		  case 'Z':
			  return ZAP * a;
			  
		  }
	  }
	  
	  return 0;
		  
   }
      
   
   public static float calcularIva (float a) 
   {
	   final float IVA = 16/100f;
	   return a * IVA;
   }
   
   public static float calcularValorComIva (float a, float b)
   {
	   return a+b;
   }
   
   
   public static int calcularVp3 (char tpI)
   {
	 final short FORAM = 3500, MAPUTO = 2800, COMPLETA = 2500, NORMAL = 1500;
	 
	 switch (tpI)
	 {
	 case 'n':
	  case 'N':return NORMAL;
		  
	  case 'c':
	  case 'C': return COMPLETA;
		  
	  case 'm':
	  case 'M': return MAPUTO;
		  
	  case 't':
	  case 'T':return FORAM;
		  
	 }
	 
	 return 0;
   }
   
    
   public static int calcularVp1 (char tpI, int nTv)
   {
	  final short RESIDENCIAL = 3500, EMPRESARIAL = 6000, TVS = 750;
	  switch (tpI)
	  {
	  case 'r':
	  case 'R':return RESIDENCIAL + ((nTv - 1)*TVS);
		  
	  case 'e':
	  case 'E':return EMPRESARIAL + ((nTv - 1)*TVS);
	  
	  }
	  
	  return 0;
   }
   
   public static int calcularVp2 (char tPacote, int nMeses)
   {
	   final short BASICO = 1200, FAMILIA1 = 2000, PREMIUM1 = 3500, FAMILIA2 = 1500, PREMIUM2= 5500, FACIL = 650, FAMILIA3 = 1800, MINI = 750, MAX = 3200;
	   
	   switch(tPacote)
	   {
		   case 'b':
		   case 'B':
			   return BASICO * nMeses;
			   
		   case 'f':
		   case 'F':
			   return FAMILIA1 * nMeses;
			   
		   case 'p':
		   case 'P':
			   return PREMIUM1 * nMeses;
			   
		   case 'm':
		   case 'M':
			   return FAMILIA2* nMeses;
			   
		   case 'c':
		   case 'C':
			   return FACIL * nMeses;
			   
		   case 'r':
		   case 'R':
			   return PREMIUM2 * nMeses;
			   
		   case 'l':
		   case 'L':
			   return FAMILIA3 * nMeses;
			   
		   case 'n':
		   case 'N':
			   return MINI * nMeses;
			   
		   case 'x':
		   case 'X':
			   return MAX * nMeses;
			   
			   
	   }
	   
	   return 0;
   }
   
   
   
  
  
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
}
