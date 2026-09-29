.MODEL SMALL
.STACK 100H

;o jumps avisa o tasm para ajustar sozinho os saltos condicionais (je, jne, jb, ja) que ficam longe demais
;sem ele, com a logica da conversao o codigo cresce e o tasm da o erro "relative jump out of range"
JUMPS

.DATA

; esse ",13" serve para voltar o inicio
; esse ",10," serve para saltar a linha para baixo

MENU1 DB 13, 10, "Bem vindo ao Programa de conversao de caracteres", 13, 10, "$"
MENU2 DB "1. Converter de minusculas para MAIUSCULAS", 13, 10, "$"
MENU3 DB "2. Converter de MAIUSCULAS para minusculas", 13, 10, "$"
MENU4 DB "-> $"

RECEBER DB 81           ;criar a variavel "receber" na qual ira armazenar 80 caracteres no maximo
        DB ?            ;aqui ele ira contar quantos caracteres o usuario digitou "50 caracteres digitados"
        DB 81 DUP(0)    ;a string digitada pelo usuario ira ser preenchido ca neste espaco comecando pela posicao 0

QUESTIONAR DB 13, 10, "Deseja continuar? (s/S ou n/N): $"
VAZIO1     DB "Nao podes introduzir uma mensagem vazia, tente novamente: ", 13, 10, "$"
DESPEDIDAS DB "BYE BYE, VOLTE SEMPRE ^_^$"
LINHA DB 13, 10, "$"

;mensagens que usamos na logica da conversao
PEDIR      DB "Introduza a frase (max 80 caracteres): $"
RESULTADO  DB "Resultado da conversao:", 13, 10, "$"
CONTAGEM   DB 13, 10, 13, 10, "Numero de caracteres processados: $"



.CODE
MAIN PROC


;criamos uma funcao chamada (inicio) na qual servira para
;conseguir fazermos o loop caso o programa chegue ao fim e deseje continuar
INICIO:

    ;inicializar o segmento data
    MOV AX, @DATA
    MOV DS, AX

    ;chamar a variavel menu1
    MOV DX, OFFSET MENU1
    MOV AH, 9H
    INT 21H

    ;chamar a variavel menu2
    MOV DX, OFFSET MENU2
    MOV AH, 9H
    INT 21H

    ;chamar a variavel menu3
    MOV DX, OFFSET MENU3
    MOV AH, 9H
    INT 21H

    ;chamar a variavel menu4
    MOV DX, OFFSET MENU4
    MOV AH, 9H
    INT 21H

    ;receber a opcao (1 ou 2) e guardar no registro al
    MOV AH, 1H
    INT 21H

    ;saltar para a linha de baixo, para a opcao e o resto ficarem em linhas diferentes
    MOV DX, OFFSET LINHA
    MOV AH, 9H
    INT 21H

    ;nao validamos a opcao: comparar oque esta no al com '2'
    ;deve ser com aspas simples pois o valor no al nao `e exatamente 2, `e o codigo ascii do caracter
    CMP AL, '2'

    ;se for maior ou igual a '2' (2, 3, letras, simbolos...) entra na opcao 2
    JAE OPCAO2

    ;se for menor que '2' (1, enter, ou qualquer outra coisa) entra na opcao 1, pois esta logo abaixo
    JMP OPCAO1


OPCAO2:

    ;aqui ficara a logica da conversao

    ;mostrar a mensagem que pede a frase ao utilizador (o 0ah aceita no maximo 80 caracteres)
    MOV DX, OFFSET PEDIR
    MOV AH, 9H
    INT 21H

    ;receber a string
    ;o 0ah precisa que o dx aponte para o inicio da variavel "receber", se nao ele guarda a string no sitio errado
    MOV DX, OFFSET RECEBER
    MOV AH, 0AH
    INT 21H

    ;saltar para a linha de baixo, para a entrada e a saida ficarem em linhas diferentes
    MOV DX, OFFSET LINHA
    MOV AH, 9H
    INT 21H

    ;o 0ah guarda na posicao 1 da variavel "receber" a quantidade de caracteres que o utilizador digitou
    ;(a posicao 0 tem o maximo e a string comeca na posicao 2)
    ;comparamos esse valor com 0 para saber se a frase esta vazia (so digitou enter)
    MOV AL, RECEBER+1
    CMP AL, 0

    ;se nao estiver vazia, salta para a conversao
    JNE CONVERTER2

    ;se estiver vazia, mostrar a mensagem de frase vazia
    MOV DX, OFFSET VAZIO1
    MOV AH, 9H
    INT 21H

    ;e voltar a pedir a frase (volta para o inicio desta opcao)
    JMP OPCAO2

CONVERTER2:

    ;mostrar a mensagem que antecede o resultado da conversao
    MOV DX, OFFSET RESULTADO
    MOV AH, 9H
    INT 21H

    ;o si vai apontar para o primeiro caracter da frase (posicao 2 da variavel "receber")
    MOV SI, OFFSET RECEBER + 2

    ;o cx vai ser o contador do ciclo, com a quantidade de caracteres que o utilizador digitou (posicao 1)
    MOV CH, 0
    MOV CL, RECEBER+1

;ciclo que percorre a frase caracter a caracter
CICLO2:

    ;colocar no dl o caracter atual (o dl `e o registro que o 02h usa para mostrar o caracter)
    MOV DL, [SI]

    ;comparar o caracter com 'A'
    CMP DL, 'A'

    ;se for menor que 'A' nao `e maiuscula, entao fica igual
    JB SEGUIR2

    ;comparar o caracter com 'Z'
    CMP DL, 'Z'

    ;se for maior que 'Z' nao `e maiuscula, entao fica igual
    JA SEGUIR2

    ;se chegou aqui, esta entre 'A' (41h) e 'Z' (5ah), entao `e maiuscula
    ;a diferenca entre a minuscula e a maiuscula `e sempre 20h (32), entao somamos 20h
    ADD DL, 20H

    ;guardar o caracter convertido no mesmo lugar da string
    MOV [SI], DL

SEGUIR2:

    ;mostrar o caracter (convertido ou igual), pois as letras foram convertidas e o resto (numeros, espacos, simbolos) ficou intacto
    ;usamos o 02h e nao o 09h, porque se a frase tiver o simbolo '$' o 09h parava nele
    MOV AH, 2H
    INT 21H

    ;avancar para o proximo caracter
    INC SI

    ;o loop subtrai 1 ao cx e, enquanto o cx nao chegar a 0, volta para o ciclo
    LOOP CICLO2

    ;mostrar a mensagem da quantidade de caracteres processados
    MOV DX, OFFSET CONTAGEM
    MOV AH, 9H
    INT 21H

    ;a quantidade vai de 1 a 80 e o 02h so mostra um caracter de cada vez, entao separamos em dezenas e unidades
    ;dividimos por 10: o quociente (al) sao as dezenas e o resto (ah) sao as unidades
    MOV AL, RECEBER+1
    MOV AH, 0
    MOV BL, 10
    DIV BL

    ;guardar as unidades no bh, pois o ah vai ser usado para chamar o 02h
    MOV BH, AH

    ;se as dezenas forem 0, nao mostrar o zero a esquerda (ex: 5 em vez de 05)
    CMP AL, 0
    JE UNIDADES2

    ;mostrar as dezenas, somando 48 ('0') para transformar o numero no caracter ascii
    MOV DL, AL
    ADD DL, '0'
    MOV AH, 2H
    INT 21H

UNIDADES2:

    ;mostrar as unidades, tambem somando 48 ('0')
    MOV DL, BH
    ADD DL, '0'
    MOV AH, 2H
    INT 21H

    JMP QUESTAO


OPCAO1:

    ;aqui ficara a logica da conversao

    ;mostrar a mensagem que pede a frase ao utilizador (o 0ah aceita no maximo 80 caracteres)
    MOV DX, OFFSET PEDIR
    MOV AH, 9H
    INT 21H

    ;receber a string
    ;o 0ah precisa que o dx aponte para o inicio da variavel "receber", se nao ele guarda a string no sitio errado
    MOV DX, OFFSET RECEBER
    MOV AH, 0AH
    INT 21H

    ;saltar para a linha de baixo, para a entrada e a saida ficarem em linhas diferentes
    MOV DX, OFFSET LINHA
    MOV AH, 9H
    INT 21H

    ;o 0ah guarda na posicao 1 da variavel "receber" a quantidade de caracteres que o utilizador digitou
    ;(a posicao 0 tem o maximo e a string comeca na posicao 2)
    ;comparamos esse valor com 0 para saber se a frase esta vazia (so digitou enter)
    MOV AL, RECEBER+1
    CMP AL, 0

    ;se nao estiver vazia, salta para a conversao
    JNE CONVERTER1

    ;se estiver vazia, mostrar a mensagem de frase vazia
    MOV DX, OFFSET VAZIO1
    MOV AH, 9H
    INT 21H

    ;e voltar a pedir a frase (volta para o inicio desta opcao)
    JMP OPCAO1

CONVERTER1:

    ;mostrar a mensagem que antecede o resultado da conversao
    MOV DX, OFFSET RESULTADO
    MOV AH, 9H
    INT 21H

    ;o si vai apontar para o primeiro caracter da frase (posicao 2 da variavel "receber")
    MOV SI, OFFSET RECEBER + 2

    ;o cx vai ser o contador do ciclo, com a quantidade de caracteres que o utilizador digitou (posicao 1)
    MOV CH, 0
    MOV CL, RECEBER+1

;ciclo que percorre a frase caracter a caracter
CICLO1:

    ;colocar no dl o caracter atual (o dl `e o registro que o 02h usa para mostrar o caracter)
    MOV DL, [SI]

    ;comparar o caracter com 'a'
    CMP DL, 'a'

    ;se for menor que 'a' nao `e minuscula, entao fica igual
    JB SEGUIR1

    ;comparar o caracter com 'z'
    CMP DL, 'z'

    ;se for maior que 'z' nao `e minuscula, entao fica igual
    JA SEGUIR1

    ;se chegou aqui, esta entre 'a' (61h) e 'z' (7ah), entao `e minuscula
    ;a diferenca entre a minuscula e a maiuscula `e sempre 20h (32), entao subtraimos 20h
    SUB DL, 20H

    ;guardar o caracter convertido no mesmo lugar da string
    MOV [SI], DL

SEGUIR1:

    ;mostrar o caracter (convertido ou igual), pois as letras foram convertidas e o resto (numeros, espacos, simbolos) ficou intacto
    ;usamos o 02h e nao o 09h, porque se a frase tiver o simbolo '$' o 09h parava nele
    MOV AH, 2H
    INT 21H

    ;avancar para o proximo caracter
    INC SI

    ;o loop subtrai 1 ao cx e, enquanto o cx nao chegar a 0, volta para o ciclo
    LOOP CICLO1

    ;mostrar a mensagem da quantidade de caracteres processados
    MOV DX, OFFSET CONTAGEM
    MOV AH, 9H
    INT 21H

    ;a quantidade vai de 1 a 80 e o 02h so mostra um caracter de cada vez, entao separamos em dezenas e unidades
    ;dividimos por 10: o quociente (al) sao as dezenas e o resto (ah) sao as unidades
    MOV AL, RECEBER+1
    MOV AH, 0
    MOV BL, 10
    DIV BL

    ;guardar as unidades no bh, pois o ah vai ser usado para chamar o 02h
    MOV BH, AH

    ;se as dezenas forem 0, nao mostrar o zero a esquerda (ex: 5 em vez de 05)
    CMP AL, 0
    JE UNIDADES1

    ;mostrar as dezenas, somando 48 ('0') para transformar o numero no caracter ascii
    MOV DL, AL
    ADD DL, '0'
    MOV AH, 2H
    INT 21H

UNIDADES1:

    ;mostrar as unidades, tambem somando 48 ('0')
    MOV DL, BH
    ADD DL, '0'
    MOV AH, 2H
    INT 21H


QUESTAO:

    ;questiona se pretende continuar ou nao
    MOV DX, OFFSET QUESTIONAR
    MOV AH, 9H
    INT 21H

    ;receber o caracter que ele digitou
    MOV AH, 1H
    INT 21H

    MOV DX, OFFSET LINHA
    MOV AH, 9H
    INT 21H

    ;so validamos o 's': comparar para ver se oque ele digitou `e 's' em minusculo
    CMP AL, 's'

    ;se for 's' volta para o inicio (o jumps ja resolve o problema de o inicio ficar longe demais)
    JE INICIO

    ;se nao for, verificar se `e 'S' em maiusculo
    CMP AL, 'S'

    ;se for, volta para o inicio
    JE INICIO

    ;se nao for nem 's' nem 'S', consideramos que ele quer sair, entao segue para o fim


FIM:

    MOV DX, OFFSET DESPEDIDAS
    MOV AH, 9H
    INT 21H

    ;funcao para devolver o controle ao sistema operacional (se nao o dosbox nao ira sair do programa)
    MOV AX, 4C00H
    INT 21H

MAIN ENDP
END MAIN
