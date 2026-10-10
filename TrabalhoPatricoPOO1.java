
import java.io.*;
import java.text.DecimalFormat;
public class TrabalhoPatricoPOO1
{
  public static void main (String [] args) throws IOException
  {
    menu();
  }

  //Menu principal do programa
  public static void menu () throws IOException
  {
    //vp - valor de cada operacao sem IVA, vt - valor total do cliente sem IVA, vf - valor com IVA
    float vf = 0,d = 0,iva = 0,vp = 0,vt = 0;
    char operadora,tpI,tPacote,equipm;

    //Contadores
    int contT = 0, contD = 0, contZ = 0,contBasico = 0,contFamilia = 0, contPremium = 0,contFacil = 0,contFamilia2 = 0,contPremium2 = 0,contMin = 0,contFamilia3 = 0,contMax = 0, quantTV = 0, totalEquip = 0;
    int numcliente, opcao, nTv,nMeses,quant = 0,numSmartCard,adicio = 0;

    //Acumuladores (acumG guarda o total recebido sem IVA)
    float acumT = 0 , acumD = 0 , acumZ = 0, acumG = 0, vTDesc = 0, vTIva = 0,vtBasico = 0,vtFamilia = 0,vtPremium = 0,vtFacil = 0,vtFamilia2 = 0,vtPremium2 = 0,vtMin = 0,vtFamilia3 = 0,vtMax = 0;


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
      System.out.println ("||11.DSTV, visualizar o pacote que menos rendeu                                                                    ||");
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

          //o valor total do cliente comeca sempre em zero
          vt = 0;

          switch(operadora)
          {
            //Operadora TVCabo
            case 't':
            case 'T':
              do
              {
                System.out.println("\nBem vindo a operadora TVCabo");

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

                    //nova instalacao conta como novo cliente
                    quantTV++;

                    vp = calcularVp1(tpI,nTv);
                    iva = calcularIva (vp);
                    vf = calcularValorComIva (vp,iva);
                    vt += vp;

                    visualizarTabelaTVCabo (numcliente,tpI,nTv,vp,iva,vf);
                    break;

                  case 2:
                    System.out.println("Introduza o tipo de pacote (B-Basico / F-Familia / P-Premium): ");
                    tPacote = validarChar1('b','f','p','B','F','P');

                    System.out.println("Introduza o numero de meses (1-12): ");
                    nMeses = validarInt(1,12);

                    vp = calcularVp2(tPacote, nMeses);

                    //desconto de 5% apenas para 6 ou mais meses
                    if (nMeses >= 6)
                      d = calcularDesconto(vp);
                    else
                      d = 0;

                    vTDesc += d;
                    vp = calcularFloat (vp,d);
                    iva = calcularIva (vp);
                    vf = calcularValorComIva (vp,iva);
                    vt += vp;

                    //contar o cliente e acumular o valor recebido em cada pacote
                    switch (tPacote)
                    {
                      case 'b':
                      case 'B':
                        contBasico++;
                        vtBasico += vf;break;

                      case 'f':
                      case 'F':
                        contFamilia++;
                        vtFamilia += vf;break;

                      case 'p':
                      case 'P':
                        contPremium++;
                        vtPremium += vf;break;
                    }

                    visualizarTabelaRenovacao (numcliente,operadora,tPacote,nMeses,d,vp,iva,vf);
                    break;

                  case 3:
                    System.out.println("Deseja equipamento adicional? (S-Sim / N-Nao)");
                    equipm = validarChar2('s','n','S','N');

                    if (equipm == 's' || equipm == 'S')
                    {
                      System.out.println("Introduza a quantidade de equipamentos (>0): ");
                      quant = validarValorInt();

                      totalEquip += quant;
                      adicio = calcularAdicionais (quant,operadora,equipm);
                      vt = calcularValorComAdicional (vt,adicio);

                      visualizarvp (adicio);
                    }
                    break;

                  case 4:
                    System.out.println("Voltaste ao menu principal");break;
                }
              }
              while (opcao != 4);

              //o cliente so e contado se fez alguma operacao
              if (vt > 0)
              {
                contT ++;
                iva = calcularIva (vt);
                vf = calcularValorComIva (vt,iva);
                acumT += vf;
                acumG += vt;
                vTIva += iva;
                visualizarvf (vf);
              }
              break;

            //Operadora DSTV
            case 'd':
            case 'D':
              do
              {
                System.out.println("\nBem vindo a operadora DSTV");

                System.out.println("1. Activar novo cliente");
                System.out.println("2. Renovacao de Pacote da DSTV");
                System.out.println("3. Adicionar segundo descodificador");
                System.out.println("4. Sair da operadora DSTV");

                System.out.println("Digite a opcao desejada (1-4): ");
                opcao = validarInt (1,4);

                switch(opcao)
                {
                  case 1:
                    System.out.println("Introduza o numero de SmartCard (111111111-999999999): ");
                    numSmartCard = validarInt (111111111,999999999);

                    System.out.println("Introduza o tipo de instalacao (N-Normal / C-Completa): ");
                    tpI = validarChar2 ('n','c','N','C');

                    //activacao conta como novo cliente
                    quantTV++;

                    vp = calcularVp3 (tpI);
                    iva = calcularIva (vp);
                    vf = calcularValorComIva (vp,iva);
                    vt += vp;

                    visualizarTabelaDSTV (numcliente,numSmartCard,tpI,vp,iva,vf);
                    break;

                  case 2:
                    System.out.println("Introduza o numero de SmartCard (111111111-999999999): ");
                    numSmartCard = validarInt (111111111,999999999);

                    System.out.println("Introduza o tipo de pacote (C-Facil / M-Familia / R-Premium): ");
                    tPacote = validarChar1 ('c','m','r','C','M','R');

                    System.out.println("Introduza o numero de meses (1-12): ");
                    nMeses = validarInt (1,12);

                    vp = calcularVp2 (tPacote,nMeses);
                    iva = calcularIva (vp);
                    vf = calcularValorComIva (vp,iva);
                    vt += vp;

                    //contar o cliente e acumular o valor recebido em cada pacote
                    switch (tPacote)
                    {
                      case 'c':
                      case 'C':
                        contFacil++;
                        vtFacil += vf;break;

                      case 'm':
                      case 'M':
                        contFamilia2++;
                        vtFamilia2 += vf;break;

                      case 'r':
                      case 'R':
                        contPremium2++;
                        vtPremium2 += vf;break;
                    }

                    visualizarTabelaRenovacaoDSTV (numcliente,numSmartCard,tPacote,nMeses,vp,iva,vf);
                    break;

                  case 3:
                    System.out.println("Deseja um segundo ou mais descodificadores? (S-Sim / N-Nao)");
                    equipm = validarChar2('s','n','S','N');

                    if (equipm == 's' || equipm == 'S')
                    {
                      System.out.println("Introduza a quantidade de descodificadores (>0): ");
                      quant = validarValorInt();

                      adicio = calcularAdicionais (quant,operadora,equipm);
                      vt = calcularValorComAdicional (vt,adicio);

                      visualizarvp (adicio);
                    }
                    break;

                  case 4:
                    System.out.println("Voltaste ao menu principal");break;
                }
              }
              while (opcao != 4);

              //o cliente so e contado se fez alguma operacao
              if (vt > 0)
              {
                contD ++;
                iva = calcularIva (vt);
                vf = calcularValorComIva (vt,iva);
                acumD += vf;
                acumG += vt;
                vTIva += iva;
                visualizarvf (vf);
              }
              break;

            //Operadora ZAP
            case 'z':
            case 'Z':
              do
              {
                System.out.println("\nBem vindo a operadora ZAP");

                System.out.println("1. Nova Instalacao da ZAP");
                System.out.println("2. Renovacao de Pacote da ZAP");
                System.out.println("3. Comprar descodificador");
                System.out.println("4. Sair da operadora ZAP");

                System.out.println("Digite a opcao desejada (1-4): ");
                opcao = validarInt (1,4);

                switch(opcao)
                {
                  case 1:
                    System.out.println("Introduza o local de instalacao (M-Maputo / T-Fora de Maputo): ");
                    tpI = validarChar2('m','t','M','T');

                    //nova instalacao conta como novo cliente
                    quantTV++;

                    vp = calcularVp3 (tpI);
                    iva = calcularIva (vp);
                    vf = calcularValorComIva (vp,iva);
                    vt += vp;

                    visualizarTabelaZAP (numcliente,tpI,vp,iva,vf);
                    break;

                  case 2:
                    System.out.println("Introduza o tipo de pacote (N-Mini / L-Familia / X-Max): ");
                    tPacote = validarChar1 ('n','l','x','N','L','X');

                    System.out.println("Introduza o numero de meses (1-12): ");
                    nMeses = validarInt (1,12);

                    vp = calcularVp2 (tPacote,nMeses);
                    d = calcularDesconto (vp,nMeses);

                    vTDesc += d;
                    vp = calcularFloat (vp,d);
                    iva = calcularIva (vp);
                    vf = calcularValorComIva (vp,iva);
                    vt += vp;

                    //contar o cliente e acumular o valor recebido em cada pacote
                    switch (tPacote)
                    {
                      case 'n':
                      case 'N':
                        contMin++;
                        vtMin += vf;break;

                      case 'l':
                      case 'L':
                        contFamilia3++;
                        vtFamilia3 += vf;break;

                      case 'x':
                      case 'X':
                        contMax++;
                        vtMax += vf;break;
                    }

                    visualizarTabelaRenovacao (numcliente,operadora,tPacote,nMeses,d,vp,iva,vf);
                    break;

                  case 3:
                    System.out.println("Deseja comprar descodificador? (S-Sim / N-Nao)");
                    equipm = validarChar2('s','n','S','N');

                    if (equipm == 's' || equipm == 'S')
                    {
                      System.out.println("Introduza a quantidade de descodificadores (>0): ");
                      quant = validarValorInt();

                      adicio = calcularAdicionais (quant,operadora,equipm);
                      vt = calcularValorComAdicional (vt,adicio);

                      visualizarvp (adicio);
                    }
                    break;

                  case 4:
                    System.out.println("Voltaste ao menu principal");break;
                }
              }
              while (opcao != 4);

              //o cliente so e contado se fez alguma operacao
              if (vt > 0)
              {
                contZ++;
                iva = calcularIva (vt);
                vf = calcularValorComIva (vt,iva);
                acumZ += vf;
                acumG += vt;
                vTIva += iva;
                visualizarvf (vf);
              }
              break;
          }
          break;

        case 2: visualizarcontadores (contT,contD,contZ);
          break;
        case 3: visualizaracumuladores (acumT,acumD,acumZ);
          break;
        case 4: visualizarvalorTotal (acumT + acumD + acumZ);
          break;
        case 5: visualizarTotalDesconto (vTDesc);
          break;
        case 6: visualizarTotalIva (vTIva);
          break;
        case 7: visualizarPacotesTVCABO(contBasico,contFamilia,contPremium,vtBasico,vtFamilia,vtPremium);
          break;
        case 8: visualizarSituacao(vtBasico,vtFamilia,vtPremium,opcao);
          break;
        case 9: visualizarQuantAdicional (totalEquip);
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



  //Visualizacoes
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

  //Tabela da nova instalacao da TVCabo
  public static void visualizarTabelaTVCabo (int numcliente, char tpI, int nTv, float vp, float iva, float vf)
  {
    System.out.println ("\nDados do cliente - TVCabo - Nova Instalacao (valores em MTS)");
    System.out.println ("============================================================================================");
    System.out.println ("| Nr Cliente | Tipo (R/E) |   Nr TVs   |  Valor s/IVA   |    IVA 16%     |  Valor c/IVA   |");
    System.out.println ("--------------------------------------------------------------------------------------------");
    System.out.printf  ("| %10d | %10c | %10d | %14.2f | %14.2f | %14.2f |\n",numcliente,tpI,nTv,vp,iva,vf);
    System.out.println ("============================================================================================");
  }

  //Tabela da activacao de novo cliente da DSTV
  public static void visualizarTabelaDSTV (int numcliente, int numSmartCard, char tpI, float vp, float iva, float vf)
  {
    System.out.println ("\nDados do cliente - DSTV - Activar Novo Cliente (valores em MTS)");
    System.out.println ("============================================================================================");
    System.out.println ("| Nr Cliente | SmartCard  | Tipo (N/C) |  Valor s/IVA   |    IVA 16%     |  Valor c/IVA   |");
    System.out.println ("--------------------------------------------------------------------------------------------");
    System.out.printf  ("| %10d | %10d | %10c | %14.2f | %14.2f | %14.2f |\n",numcliente,numSmartCard,tpI,vp,iva,vf);
    System.out.println ("============================================================================================");
  }

  //Tabela da nova instalacao da ZAP
  public static void visualizarTabelaZAP (int numcliente, char tpI, float vp, float iva, float vf)
  {
    System.out.println ("\nDados do cliente - ZAP - Nova Instalacao (valores em MTS)");
    System.out.println ("===============================================================================");
    System.out.println ("| Nr Cliente | Local(M/T) |  Valor s/IVA   |    IVA 16%     |  Valor c/IVA   |");
    System.out.println ("-------------------------------------------------------------------------------");
    System.out.printf  ("| %10d | %10c | %14.2f | %14.2f | %14.2f |\n",numcliente,tpI,vp,iva,vf);
    System.out.println ("===============================================================================");
  }

  //Tabela da renovacao de pacote da TVCabo e da ZAP
  public static void visualizarTabelaRenovacao (int numcliente, char operadora, char tPacote, int nMeses, float d, float vp, float iva, float vf)
  {
    System.out.println ("\nDados do cliente - Renovacao de Pacote (valores em MTS)");
    System.out.println ("=========================================================================================================================");
    System.out.println ("| Nr Cliente | Operadora  |   Pacote   |   Meses    |    Desconto    |  Valor s/IVA   |    IVA 16%     |  Valor c/IVA   |");
    System.out.println ("-------------------------------------------------------------------------------------------------------------------------");
    System.out.printf  ("| %10d | %10c | %10c | %10d | %14.2f | %14.2f | %14.2f | %14.2f |\n",numcliente,operadora,tPacote,nMeses,d,vp,iva,vf);
    System.out.println ("=========================================================================================================================");
  }

  //Tabela da renovacao de pacote da DSTV
  public static void visualizarTabelaRenovacaoDSTV (int numcliente, int numSmartCard, char tPacote, int nMeses, float vp, float iva, float vf)
  {
    System.out.println ("\nDados do cliente - DSTV - Renovacao de Pacote (valores em MTS)");
    System.out.println ("========================================================================================================");
    System.out.println ("| Nr Cliente | SmartCard  |   Pacote   |   Meses    |  Valor s/IVA   |    IVA 16%     |  Valor c/IVA   |");
    System.out.println ("--------------------------------------------------------------------------------------------------------");
    System.out.printf  ("| %10d | %10d | %10c | %10d | %14.2f | %14.2f | %14.2f |\n",numcliente,numSmartCard,tPacote,nMeses,vp,iva,vf);
    System.out.println ("========================================================================================================");
  }

  //Verifica se o valor recebido sem IVA chega para pagar a taxa do exterior
  public static void verificarLucro (float acumG)
  {
    final int TAXA = 1000000;
    DecimalFormat mt = new DecimalFormat ("###,###,###,###,##0.00 MTS");

    if (acumG >= TAXA)
      System.out.println ("O valor chega para pagar a taxa. Resta de lucro: "+mt.format(acumG - TAXA));
    else
      System.out.println ("O valor nao chega para pagar a taxa. Falta: " +mt.format(TAXA - acumG));
  }

  public static void visualizarTotalServicosTV (int quantTV)
  {
    System.out.println("A quantidade total de novos clientes que aderiram aos servicos de televisao e de: " +quantTV);
  }

  public static void visualizarQuantAdicional (int totalEquip)
  {
    System.out.println("A quantidade total de equipamento adicional vendido pela TVCabo e de: " +totalEquip);
  }

  //Mostra o pacote que mais rendeu (opcoes 8 e 13) ou o que menos rendeu (opcao 11)
  public static void visualizarSituacao (float a,float b,float c,int opcao)
  {
    DecimalFormat mt = new DecimalFormat ("###,###,###,##0.00 MTS");

    if (a == 0 && b == 0 && c == 0)
      System.out.println ("Ainda nao foi vendido nenhum pacote nesta operadora");
    else
      switch (opcao)
      {
        case 8:
          if (a >= b && a >= c)
            System.out.println ("O pacote Basico foi o que mais rendeu, com " +mt.format(a));
          else
            if (b >= a && b >= c)
              System.out.println ("O pacote Familia foi o que mais rendeu, com " +mt.format(b));
            else
              System.out.println ("O pacote Premium foi o que mais rendeu, com " +mt.format(c));
          break;

        case 11:
          if (a <= b && a <= c)
            System.out.println ("O pacote Facil foi o que menos rendeu, com " +mt.format(a));
          else
            if (b <= a && b <= c)
              System.out.println ("O pacote Familia foi o que menos rendeu, com " +mt.format(b));
            else
              System.out.println ("O pacote Premium foi o que menos rendeu, com " +mt.format(c));
          break;

        case 13:
          if (a >= b && a >= c)
            System.out.println ("O pacote Mini foi o que mais rendeu, com " +mt.format(a));
          else
            if (b >= a && b >= c)
              System.out.println ("O pacote Familia foi o que mais rendeu, com " +mt.format(b));
            else
              System.out.println ("O pacote Max foi o que mais rendeu, com " +mt.format(c));
          break;
      }
  }

  public static void visualizarPacotesZAP(int contMin,int contFamilia3,int contMax,float vtMin,float vtFamilia3,float vtMax)
  {
    DecimalFormat mt = new DecimalFormat ("###,###,###,##0.00 MTS");

    System.out.println("A Quantidade de clientes que aderiram ao pacote Mini e de: "+contMin);
    System.out.println("A Quantidade de clientes que aderiram ao pacote Familia e de: "+contFamilia3);
    System.out.println("A Quantidade de clientes que aderiram ao pacote Max e de: "+contMax);
    System.out.println("O Valor total recebido no pacote Mini e de: "+mt.format(vtMin));
    System.out.println("O Valor total recebido no pacote Familia e de: "+mt.format(vtFamilia3));
    System.out.println("O Valor total recebido no pacote Max e de: "+mt.format(vtMax));
  }

  public static void visualizarPacotesDSTV(int contFacil,int contFamilia2,int contPremium2,float vtFacil,float vtFamilia2,float vtPremium2)
  {
    DecimalFormat mt = new DecimalFormat ("###,###,###,##0.00 MTS");

    System.out.println("A Quantidade de clientes que aderiram ao pacote Facil e de: "+contFacil);
    System.out.println("A Quantidade de clientes que aderiram ao pacote Familia e de: "+contFamilia2);
    System.out.println("A Quantidade de clientes que aderiram ao pacote Premium e de: "+contPremium2);
    System.out.println("O Valor total recebido no pacote Facil e de: "+mt.format(vtFacil));
    System.out.println("O Valor total recebido no pacote Familia e de: "+mt.format(vtFamilia2));
    System.out.println("O Valor total recebido no pacote Premium e de: "+mt.format(vtPremium2));
  }

  public static void  visualizarPacotesTVCABO(int contBasico,int contFamilia, int contPremium,float vtBasico,float vtFamilia,float vtPremium)
  {
    DecimalFormat mt = new DecimalFormat ("###,###,###,##0.00 MTS");

    System.out.println("A Quantidade de clientes que aderiram ao pacote Basico e de: "+contBasico);
    System.out.println("A Quantidade de clientes que aderiram ao pacote Familia e de: "+contFamilia);
    System.out.println("A Quantidade de clientes que aderiram ao pacote Premium e de: "+contPremium);
    System.out.println("O Valor total recebido no pacote Basico e de: "+mt.format(vtBasico));
    System.out.println("O Valor total recebido no pacote Familia e de: "+mt.format(vtFamilia));
    System.out.println("O Valor total recebido no pacote Premium e de: "+mt.format(vtPremium));
  }

  public static void visualizarcontadores (int contT, int contD, int contZ)
  {
    System.out.println("A quantidade de clientes da TVCabo e de: " +contT);
    System.out.println("A quantidade de clientes da DSTV e de: " +contD);
    System.out.println("A quantidade de clientes da ZAP e de: " +contZ);
  }


  public static void visualizaracumuladores(float acumT, float acumD, float acumZ)
  {
    DecimalFormat mt = new DecimalFormat ("###,###,###,##0.00 MTS");

    System.out.println("O Valor total recebido pela TVCabo e de: " +mt.format(acumT));
    System.out.println("O Valor total recebido pela DSTV e de: " +mt.format(acumD));
    System.out.println("O Valor total recebido pela ZAP e de: " +mt.format(acumZ));
  }


  public static void visualizarvalorTotal(float total)
  {
    DecimalFormat mt = new DecimalFormat ("###,###,###,##0.00 MTS");
    System.out.println("O Valor total recebido e de: " +mt.format(total));
  }


  public static void visualizarTotalDesconto(float vTDesc)
  {
    DecimalFormat mt = new DecimalFormat ("###,###,###,##0.00 MTS");
    System.out.println("O Valor total do desconto e de: " +mt.format(vTDesc));
  }


  public static void visualizarTotalIva (float vTIva)
  {
    DecimalFormat mt = new DecimalFormat ("###,###,###,##0.00 MTS");
    System.out.println("O Valor total do IVA e de: " +mt.format(vTIva));
  }


  //Valor final do cliente ja com IVA
  public static void visualizarvf (float vf)
  {
    DecimalFormat mt = new DecimalFormat ("###,###,###,##0.00 MTS");
    System.out.println ("O valor final a pagar pelo cliente (com IVA 16%) e: " +mt.format (vf));
  }

  //Valor de uma operacao sem IVA
  public static void visualizarvp (float a)
  {
    DecimalFormat mt = new DecimalFormat ("###,###,###,##0.00 MTS");
    System.out.println ("O valor a pagar (sem IVA) e: " +mt.format (a));
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


  //Valida valores inteiros maiores que zero
  public static int validarValorInt () throws IOException
  {
    int v;
    BufferedReader br = new BufferedReader ( new InputStreamReader (System.in));

    do
    {
      v = Integer.parseInt(br.readLine ());

      if (v <= 0)
        System.out.println ("ERROR! Tente Novamente!");
    }
    while (v <= 0);

    return v;
  }


  //Calculos
  //Desconto da ZAP de acordo com o numero de meses
  public static float calcularDesconto (float vp, int b)
  {
    float d;
    final float D1 = 10/100f, D2 = 7/100f, D3 = 3/100f;

    if (b == 12)
      d = vp * D1;
    else
      if (b >= 6)
        d = vp * D2;
      else
        if (b >= 3)
          d = vp * D3;
        else
          d = 0;

    return d;
  }


  //Desconto de 5% da TVCabo
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


  //Valor dos equipamentos adicionais / descodificadores de cada operadora
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


  //Valor da instalacao da DSTV (N/C) e da ZAP (M/T)
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


  //Valor da nova instalacao da TVCabo
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

  //Valor dos pacotes das 3 operadoras (cada pacote tem uma letra diferente)
  //TVCabo: B, F, P   DSTV: C, M, R   ZAP: N, L, X
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
