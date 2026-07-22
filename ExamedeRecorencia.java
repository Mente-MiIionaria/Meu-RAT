import java.io.*;
import java.text.DecimalFormat;
public class Exame2026 
{
  public static void main (String [] args) throws IOException 
  {
	 BufferedReader br = new BufferedReader (new InputStreamReader (System.in));
	 DecimalFormat mt = new DecimalFormat ("###,###,##0.00 MTs");
	 DecimalFormat mts = new DecimalFormat ("###,##0.00 metros cubicos");
	 DecimalFormat mtc = new DecimalFormat ("###,##0.00 obras");
	 
	 int n,opcao,quant,valorpagar = 0,espessura,totalV = 0,totalF = 0, quantF = 0,quantV = 0, contH = 0, totalDesconto = 0;
	 final int HIDROFUGO = 3000, FUNDACAO = 8500, VIGA = 11000;
	 float acrescimo = 0,totalBetonMoz = 0,iva,desconto,valorfinal = 0;
	 final float IVA = 17/100f, DESCONTO = 20/100f, ACRESCIMO = 30/100f;
	 char tpB,tpC,querH;


		
		System.out.println ("\nBem vindo ao programa da empresa BetonMoz, Lda, especializada em betonagem de obras de construcao civil!");

		

		
		do
		{

            System.out.println ("\n=================================================================================");
            System.out.println ("||--------------------------------------MENU-----------------------------------||");
            System.out.println ("|| 1-Receber os dados e calcular o valor a pagar                               ||");
            System.out.println ("|| 2-Visualizar o valor total de cada tipo                                     ||");
            System.out.println ("|| 3-Visualizar a quantidade total de cada tipo                                ||");
            System.out.println ("|| 4-Visualizar o numero de obras que solicitaram aditivo hidrofugo            ||");
            System.out.println ("|| 5-Visualizar o valor total de descontos concebidos aos clientes particulares||");
            System.out.println ("|| 6-Visualizar o valor total da Empresa                                       ||");
            System.out.println ("|| 7-Sair do programa                                                          ||");
            System.out.println ("||=============================================================================||");
            System.out.println ("\nIntoduza a opcao desejada (1-7)");
            System.out.print ("opcao ->");opcao = Integer.parseInt(br.readLine () );

			switch (opcao) 
			{
			case 1:
										
				for (int w=0; w<2; w++)
                {

                    do
                    {
                        System.out.println ("\nIntroduza o tipo de betao (F-Betao de fundacao / V-Betao de Viga");
                        tpB = br.readLine().charAt(0);

                        if (tpB != 'F' && tpB != 'V')
                            System.out.println ("\nLetra Invalida, Tente novamente!");

                    }
                    while (tpB != 'F' && tpB != 'V');

                    do
                    {
                        System.out.println ("\nIntroduza a quantidade (>0)");
                        quant = Integer.parseInt (br.readLine ());

                        if (quant<0)
                            System.out.println ("\nValor Invalido, Tente Novamente!");

                    }
                    while (quant<0);

                    switch (tpB)
                    {
                        case 'V' :
                            valorpagar = FUNDACAO*quant;
                            totalV+=valorpagar;
                            quantV++;

                            do
                            {
                                System.out.println ("\nDigite a espessura desjada (10-60)");
                                espessura = Integer.parseInt (br.readLine ());

                                if (espessura< 10 || espessura >60)
                                    System.out.println ("\nValor Invalido, Tente novamente");
                            }
                            while (espessura< 10 || espessura >60);

                            if (espessura > 36)
                                acrescimo = valorpagar * ACRESCIMO;
                            valorpagar += acrescimo;

                            break;

                        case 'F' :
                            valorpagar = VIGA*quant;
                            totalF+=valorpagar;
                            quant++;

                            do
                            {
                                System.out.println ("\nDeseja adicionar aditivo hidrofugo? (S-Sim / N-Nao)");
                                querH = br.readLine().charAt(0);

                                if (querH != 'S' && querH != 'N')
                                    System.out.println ("\nLetra Invalida, Tente novamente!");

                            }
                            while (querH != 'S' && querH != 'N');

                            if (querH == 'S')
                                valorpagar = valorpagar + HIDROFUGO;
                            contH ++;
                            break;
                    }

                    do
                    {
                        System.out.println ("\nIntroduza o tipo de cliente (P-Particular / E-Empresa)");
                        tpC = br.readLine().charAt(0);

                        if (tpC != 'P' && tpC != 'E')
                            System.out.println ("\nLetra Invalida, Tente novamente!");

                    }
                    while (tpC != 'P' && tpC != 'E');

                    switch (tpC)
                    {
                        case 'P' :
                            desconto = valorpagar * DESCONTO;
                            valorfinal = valorpagar - desconto;
                            totalDesconto += desconto;
                            break;

                        case 'E' :
                            iva = valorpagar * IVA;
                            valorfinal = valorpagar + iva;
                            break;
                    }

                    totalBetonMoz += valorfinal;


                    System.out.println ("\nO valor final a pagar e " +mt.format(valorfinal));
                    System.out.println ("\nCliente " +(w+1));


                }

				break;
				
				
				
			case 2:
				System.out.println ("\nO valor total do Betao de Fundacao e " +mt.format(totalF));
				System.out.println ("\nO valor total do Betao de Viga " +mt.format(totalV));
				break;
				
			case 3:
				System.out.println ("\nA quantidade total do Betao de Fundacao e " +mts.format(quantF));
				System.out.println ("\nA quantidade total do Betao de de Viga e " +mts.format(quantV));
				break;
				
			case 4:
				System.out.println ("\nPara o aditivo hidrofugo foram solicitados " +mtc.format(contH));
				break;
				
			case 5:
				System.out.println ("\nO valor total do desconto e " +mt.format(totalDesconto));
				break;
				
			case 6:
				System.out.println ("\nO valor total recebido pela empresa e " +mt.format(totalBetonMoz));
				break;
				
			case 7:
				System.out.println ("\nObrigado por utilizar os nossos servicos,volte sempre!");
				break;
			}
			
		}
		while (opcao != 7);
		 
			 
		 
  }
}
