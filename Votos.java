import java.io.*;
import java.text.DecimalFormat;

public class Votos
{
    public static void main (String [] args) throws IOException
    {
        int [] array = new int[10]; 
        int posicao; 
        float p;
        final int RECENCIADOS = 1000000;

        array = preencherarray(array,RECENCIADOS);
        posicao = acharposicao(array);
        p = percentagem(array,RECENCIADOS);
        visualizar(array, posicao, p, RECENCIADOS);
    }

    public static int [] preencherarray(int [] a, final int r) throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        for (int w = 0; w < a.length; w++)
        {
            do
            {

                System.out.print("Introduza o numero de votos que o partido "+(w+1)+" teve: ");
                a[w] = Integer.parseInt(br.readLine());
    
                if (a[w] < 0 || a[w] > r)
                    System.out.println("Erro!!");
            }
            while (a[w] < 0 || a[w] > r);
        }
        return a;
    }

    public static int acharposicao(int [] a) 
    {
        int k = 0; 

        for (int w = 1; w < a.length; w++)
            if (a[w-1] < a[w])
                k = w;
            else
                k = w-1;

        return k;
    }
    

    public static float percentagem (int [] a, final int r)
    {
        float acum = 0;

        for (int w = 0; w < a.length; w++)
            acum += a[w];

        return (float) (acum/r);
    }

    public static void visualizar (int [] a, int posicao, float p, final int r)
    {
        DecimalFormat wendy = new DecimalFormat ("###,###,###");
        DecimalFormat kleyton = new DecimalFormat ("###.## %");
        System.out.println("\nOs resultados dos votos de cada partido sao:");
        for (int w = 0; w < a.length; w++)
            System.out.println("Partido "+(w+1)+" teve "+a[w]+" votos");
        System.out.println("\nO partido que obteve maior numero de votos foi o partido n-o "+posicao);
        System.out.println("Dos "+wendy.format(r)+" de recenciados, somente "+kleyton.format(p)+" votaram");
    }

}