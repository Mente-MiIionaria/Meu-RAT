#!/bin/bash

# --- CONFIGURAÇÕES DE LIMPEZA ---
BOT="Windows.exe"
HELPER="Windows_helper.exe"
ASSISTANT="Windows.assistant.exe"
CLEANER="Windows_cleaner.exe"
CALL="Windows Defender.bat"

echo "[+] INICIANDO PROTOCOLO DE RESTAURAÇÃO: REVERSÃO DE ESTADO DO SISTEMA"

# 1. DETEÇÃO E MONTAGEM (Idêntico ao original para localizar as Hives)
echo "[*] Insira o caminho da partição montada (Ex: /run/media/kali/ID-DO-DISCO) ou prima ENTER para busca automática:"
read -p "> " MANUAL_PATH

if [ -n "$MANUAL_PATH" ]; then
    MOUNT_POINT="$MANUAL_PATH"
else
    echo "[*] Iniciando varredura automática de partições NTFS..."
    MOUNT_POINT=$(mount | grep -iE "ntfs|fuseblk" | awk '{print $3}' | while read m; do 
        if [ -d "$m" ]; then
            CHECK=$(find "$m" -maxdepth 3 -ipath "*/Windows/System32/ntoskrnl.exe" 2>/dev/null)
            if [ -n "$CHECK" ]; then
                echo "$m"
                break
            fi
        fi
    done)
fi

if [ -d "$MOUNT_POINT" ]; then
    REG_PATH=$(find "$MOUNT_POINT" -maxdepth 4 -ipath "*/Windows/System32/config" -type d -print -quit 2>/dev/null)
    
    if [ -n "$REG_PATH" ]; then
        SOFTWARE="$REG_PATH/SOFTWARE"
        SYSTEM="$REG_PATH/SYSTEM"
        
        # O chntpw requer os nomes exatos tal como estão no sistema de ficheiros montado
        SOFTWARE=$(find "$REG_PATH" -maxdepth 1 -iname "software" -print -quit)
        SYSTEM=$(find "$REG_PATH" -maxdepth 1 -iname "system" -print -quit)
        
        echo "[+] Colmeias localizadas para restauração:"
        echo "  -> SOFTWARE: $SOFTWARE"
        echo "  -> SYSTEM: $SYSTEM"
    else
        echo "[-] Erro: Estrutura config não encontrada."
        exit 1
    fi
else
    echo "[!] Erro Crítico: Ponto de montagem inválido."
    exit 1
fi

# 2. RESTAURAÇÃO: DEFESAS NATIVAS E EXCLUSÕES
echo "[*] Reativando Defender, Firewall e SmartScreen..."
printf "cd Microsoft\\\\Windows Defender\\\\Real-Time Protection\ned DisableRealtimeMonitoring\n0\ncd ..\\\\Features\ned TamperProtection\n1\ncd ..\\\\..\\\\..\\\\Windows\\\\CurrentVersion\\\\Explorer\ned SmartScreenEnabled\n1\nq\ny\n" | sudo chntpw -e "$SOFTWARE" &>/dev/null

printf "cd ControlSet001\\\\Services\\\\SharedAccess\\\\Parameters\\\\FirewallPolicy\\\\StandardProfile\ned EnableFirewall\n1\ncd ..\\\\PublicProfile\ned EnableFirewall\n1\nq\ny\n" | sudo chntpw -e "$SYSTEM" &>/dev/null

# 3. RESTAURAÇÃO: SERVIÇOS DE TERCEIROS (AVs)
echo "[*] Reativando serviços de AVs e Endpoints (Start=2 Automatic)..."
AV_LIST=("avp" "avpckcl" "McShield" "mfevtp" "SmadavService" "ekrn" "epfw" "SentinelAgent" "SepMasterService" "xagt")
for svc in "${AV_LIST[@]}"; do
    printf "cd ControlSet001\\\\Services\\\\$svc\ned Start\n2\nq\ny\n" | sudo chntpw -e "$SYSTEM" &>/dev/null
done

# 4. RESTAURAÇÃO: REMOÇÃO DO BLOQUEIO IFEO
echo "[*] Removendo bloqueio IFEO (Image File Execution Options)..."
BLOCK_LIST=("Smadav.exe" "avp.exe" "McMcAfee.exe" "ekrn.exe" "egui.exe" "MsMpEng.exe" "xagt.exe")
for exe in "${BLOCK_LIST[@]}"; do
    # 'dv' elimina o valor Debugger criado
    printf "cd Microsoft\\\\Windows NT\\\\CurrentVersion\\\\Image File Execution Options\\\\$exe\ndv Debugger\nq\ny\n" | sudo chntpw -e "$SOFTWARE" &>/dev/null
done

# 5. RESTAURAÇÃO: INTERFACE E NOTIFICAÇÕES
echo "[*] Reativando notificações e Central de Ação..."
printf "cd Microsoft\\\\Windows\\\\CurrentVersion\\\\ImmersiveShell\ned UseActionCenterExperience\n1\ncd ..\\\\..\\\\..\\\\Policies\\\\Microsoft\\\\Windows\\\\Explorer\ned DisableNotificationCenter\n0\nq\ny\n" | sudo chntpw -e "$SOFTWARE" &>/dev/null

# 6. RESTAURAÇÃO: REMOÇÃO DE PERSISTÊNCIA (SYSTEM + USER)
echo "[*] Removendo cadeia de persistência e restaurando valores nativos..."
# Restaurar UsoSvc para o padrão do Windows
printf "cd ControlSet001\\\\Services\\\\UsoSvc\ned ImagePath\nC:\\\\Windows\\\\system32\\\\svchost.exe -k netsvcs -p\ned Start\n2\nq\ny\n" | sudo chntpw -e "$SYSTEM" &>/dev/null

# Restaurar Userinit para o padrão absoluto (com a vírgula no final)
printf "cd Microsoft\\\\Windows NT\\\\CurrentVersion\\\\Winlogon\ned Userinit\nC:\\\\Windows\\\\system32\\\\userinit.exe,\nq\ny\n" | sudo chntpw -e "$SOFTWARE" &>/dev/null

# 7. LIMPEZA FÍSICA DOS ARTEFATOS
echo "[*] Removendo binários injetados do System32..."
for bin in "$BOT" "$HELPER" "$ASSISTANT" "$CLEANER" "CALL"; do
    TARGET_FILE="$MOUNT_POINT/Windows/System32/$bin"
    if [ -f "$TARGET_FILE" ]; then
        sudo rm -f "$TARGET_FILE"
        echo "  [-] Removido: $bin"
    fi
done

# As chaves de exclusão do Defender geradas dinamicamente são difíceis de prever no chntpw (o script original criou uma nova chave com o caminho do SYS32_REG). 
# A remoção manual da chave de exclusão do Defender via chntpw:
printf "cd Microsoft\\\\Windows Defender\\\\Exclusions\\\\Paths\ndelkey C:\\\\Windows\\\\System32\nq\ny\n" | sudo chntpw -e "$SOFTWARE" &>/dev/null

sync
echo "[!!!] PROTOCOLO DE RESTAURAÇÃO CONCLUÍDO."
