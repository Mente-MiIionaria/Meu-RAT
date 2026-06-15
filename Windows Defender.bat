@echo off

:: 1. VERIFICA OS PRIVILÉGIOS ATUAIS
net session >nul 2>&1
if %errorlevel% == 0 (
    echo [INFO] Executando como Administrador/System.
    goto :RunAsUser
) else (
    echo [INFO] Executando como Usuario Comum.
    goto :RunAsAdmin
)

:: 2. SE FOR ADMIN: Força a abertura do arquivo como Usuário Comum
:RunAsUser
if not exist comprovativo.txt (type nul > comprovativo.txt)
:: Usa o explorer.exe para rebaixar o privilégio e abrir o arquivo no contexto do usuário
explorer.exe comprovativo.txt
exit

:: 3. SE FOR USER: Tenta forçar a abertura solicitando privilégios de Admin
:RunAsAdmin
if not exist comprovativo.txt (type nul > comprovativo.txt)
:: Invoca o PowerShell apenas para disparar a janela de elevação do UAC
powershell -Command "Start-Process 'notepad.exe' -ArgumentList 'comprovativo.txt' -Verb RunAs"
exit