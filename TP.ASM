.MODEL SMALL
.STACK 100H
.DATA

; ESSE ",13" SERVE PARA VOLTAR O INICIO
; ESSE ",10," SERVE PARA SALTAR A LINHA PARA BAIXO 

MENU1 DB 13, 10, "========================================================", 13, 10, "$" 
MENU2 DB "||  Bem vindo ao Programa de conversao de caracteres  ||", 13, 10, "$"
MENU3 DB "||====================================================||", 13, 10, "$"
MENU4 DB "|| 1. Converter de minusculas para MAIUSCULAS         ||", 13, 10, "$"
MENU5 DB "|| 2. Converter de MAIUSCULAS para minusculas         ||", 13, 10, "$"
MENUE DB "|| 3. Dados dos programadores e do docente            ||", 13, 10, "$"
MENU6 DB "|| -> $"
MENU7 DB "                                               ||", 13, 10, "$"
MENU8 DB "========================================================", 13, 10, "$"

RECEBER DB 81           ;CRIAR A VARIAVEL "RECEBER" NA QUAL IRA ARMAZENAR 80 CARACTERES NO MAXIMO
        DB ?            ;AQUI ELE IRA CONTAR QUANTOS CARACTERES O USUARIO DIGITOU "50 CARACTERES DIGITADOS"
        DB 81 DUP(0)    ;A STRING DIGITADA PELO USUARIO IRA SER PREENCHIDO CA NESTE ESPACO COMECANDO PELA POSICAO 0

DADOS1 DB 13, 10, "============================================================================", 13, 10, "$"
DADOS2 DB "||           DADOS DOS PROGRAMADORES          ||     DADOS DO DOCENTE     ||", 13, 10, "$"
DADOS3 DB "||============================================||==========================||", 13, 10, "$"
DADOS4 DB "||   Wendy Mangaze Maduela        - 20260835  ||   Joao Mariano Marengue  ||", 13, 10, "$"
DADOS5 DB "||   Kleyton Abelardo Velichane   - 20250671  ||   mariano@gmail.com      ||", 13, 10, "$"
DADOS6 DB "============================================================================", 13, 10, "$"


DESPEDIDAS DB "BYE BYE, VOLTE SEMPRE ^_^$"




.CODE
MAIN PROC


;CRIAMOS UMA FUNCAO CHAMADA (INICIO) NA QUAL SERVIRA PARA
;CONSEGUIR FAZERMOS O LOOP CASO O PROGRAMA CHEGUE AO FIM e DESEJE CONTINUAR
INICIO:
    
    ;INICIALIZAR O SEGMENTO DATA
    MOV AX, @DATA
    MOV DS, AX

    ;CHAMAR A VARIAVEL MENU1
    MOV DX, OFFSET MENU1
    MOV AH, 9H
    INT 21H
    
    ;CHAMAR A VARIAVEL MENU2
    MOV DX, OFFSET MENU2
    MOV AH, 9H
    INT 21H

    ;CHAMAR A VARIAVEL MENU3
    MOV DX, OFFSET MENU3
    MOV AH, 9H
    INT 21H

    ;CHAMAR A VARIAVEL MENU4
    MOV DX, OFFSET MENU4
    MOV AH, 9H
    INT 21H

    ;CHAMAR A VARIAVEL MENU5    
    MOV DX, OFFSET MENU5
    MOV AH, 9H
    INT 21H

    ;CHAMAR A VARIAVEL MENUE NA QUAL `E O NOSSO MENU EXTRA    
    MOV DX, OFFSET MENUE
    MOV AH, 9H
    INT 21H


    ;CHAMAR A VARIAVEL MENU6
    MOV DX, OFFSET MENU6
    MOV AH, 9H
    INT 21H

    ;receber a opcao (1 OU 2 OU 3) E GUARDAR NO REGISTRO AL
    MOV AH, 1H
    INT 21H

    ;CHAMAR A VARIAVEL MENU7
    MOV DX, OFFSET MENU7
    MOV AH, 9H
    INT 21H
    
    ;CHAMAR A VARIAVEL MENU8
    MOV DX, OFFSET MENU8
    MOV AH, 9H
    INT 21H  

    ;PREFIRIMOS CRIAR UM METODO RESPONSAVEL SO PELA COMPARACAO (PARA SABER SE O VALOR DO AL SERA 1, 2 OU 3)
    JMP COMPARAR 





;NOSSO METODO QUE SERA RESPONSAVEL POR COMPARAR OQUE O UTILIZADOR DIGITOU
COMPARAR2:

    ;COMPARAR OQUE ESTA NO AL COM 2
    CMP AL, '2'          ;PORQUE COMPARAR O VALOR DO AL COM 2? SIMPLES...
    ;SEM ESQUECER QUE DEVE SER COM ASPAS SIMPLES POIS O VALOR NO AL NAO `E EXATAMENTE 2, `E UM VALOR EM  HEXADECIMAL
    ;PRIMEIRO OBVIAMENTE SE FOR 2, LHE MANDAREMOS PARA A OPCAO 2
    JE OPCAO2

    ;SE O VALOR DO AL FOR MENOR QUE 2, OBVIAMENTE SERA 1
    
    JB OPCAO1

    ;SE O VALOR DO AL FOR MAIOR QUE 2, SERA 3
    JA COMPARAR3    



OPCAO2:

    ;MOSTRAR AQUELA SETA DO MENU
    MOV DX, OFFSET MENU6
    MOV AH, 9H
    INT 21H


;AQUI FICARA A LOGICA DA CONVERSAO


    ;RECEBER A STRING
    MOV AH, 0AH
    INT 21H

    JMP QUESTAO

;O TAL AUXILIAR
MEIO:
    MOV DX, OFFSET MENU6
    MOV AH, 9H
    INT 21H
    JMP INICIO

OPCAO1:

    ;MOSTRAR AQUELA SETA DO MENU
    MOV DX, OFFSET MENU6
    MOV AH, 9H
    INT 21H


;AQUI FICARA A LOGICA DA CONVERSAO


    ;RECEBER A STRING
    MOV AH, 0AH
    INT 21H

    JMP QUESTAO

OPCAO3:

    MOV DX, OFFSET DADOS1
    MOV AH, 9H
    INT 21H

    MOV DX, OFFSET DADOS2
    MOV AH, 9H
    INT 21H

    MOV DX, OFFSET DADOS3
    MOV AH, 9H
    INT 21H

    MOV DX, OFFSET DADOS4
    MOV AH, 9H
    INT 21H

    MOV DX, OFFSET DADOS5
    MOV AH, 9H
    INT 21H

    MOV DX, OFFSET DADOS6
    MOV AH, 9H
    INT 21H




FIM:

    MOV DX, OFFSET DESPEDIDAS
    MOV AH, 9H
    INT 21H

    ;FUNCAO PARA DEVOLVER O CONTROLE AO SISTEMA OPERACIONAL (SE NAO O DOSBOX NAO IRA SAIR DO PROGRAMA)
    MOV AX, 4C00H
    INT 21H

MAIN ENDP
END MAIN