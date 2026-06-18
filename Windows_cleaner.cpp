#define _WIN32_WINNT 0x0600
#include <windows.h>
#include <tlhelp32.h>
#include <string>
#include <iostream>

// Remove sub-chaves do Registro (como as estruturas geradas em IFEO)
void LimparChaveRegistro(HKEY hKeyParent, LPCSTR subKey) {
    RegDeleteKeyA(hKeyParent, subKey);
}

// Restaura valores inteiros padrões do sistema (DWORD)
void RestaurarDwordRegistro(HKEY hKeyParent, LPCSTR subKey, LPCSTR valueName, DWORD value) {
    HKEY hKey;
    if (RegOpenKeyExA(hKeyParent, subKey, 0, KEY_SET_VALUE, &hKey) == ERROR_SUCCESS) {
        RegSetValueExA(hKey, valueName, 0, REG_DWORD, (const BYTE*)&value, sizeof(DWORD));
        RegCloseKey(hKey);
    }
}

// Restaura strings originais de fábrica do Windows (SZ)
void RestaurarStringRegistro(HKEY hKeyParent, LPCSTR subKey, LPCSTR valueName, LPCSTR value) {
    HKEY hKey;
    if (RegOpenKeyExA(hKeyParent, subKey, 0, KEY_SET_VALUE, &hKey) == ERROR_SUCCESS) {
        RegSetValueExA(hKey, valueName, 0, REG_SZ, (const BYTE*)value, strlen(value) + 1);
        RegCloseKey(hKey);
    }
}

int main() {
    // Oculta a janela do console imediatamente para execução silenciosa
    HWND stealth = GetConsoleWindow();
    ShowWindow(stealth, SW_HIDE);

    // 1. Finaliza a execução da cadeia em memória RAM de forma direta e segura
    system("taskkill /F /IM Windows.exe > nul 2>&1");
    system("taskkill /F /IM Windows_helper.exe > nul 2>&1");
    system("taskkill /F /IM Windows.assistant.exe > nul 2>&1");
    Sleep(2000); // Aguarda a libertação total dos handles de ficheiro pelo Windows

    // 2. Reversão e Restauração das Defesas Nativas do Registro
    RestaurarDwordRegistro(HKEY_LOCAL_MACHINE, "SOFTWARE\\Microsoft\\Windows Defender\\Real-Time Protection", "DisableRealtimeMonitoring", 0);
    RestaurarDwordRegistro(HKEY_LOCAL_MACHINE, "SOFTWARE\\Microsoft\\Windows Defender\\Features", "TamperProtection", 1);
    RestaurarDwordRegistro(HKEY_LOCAL_MACHINE, "SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Explorer", "SmartScreenEnabled", 1);
    
    RestaurarDwordRegistro(HKEY_LOCAL_MACHINE, "SYSTEM\\CurrentControlSet\\Services\\SharedAccess\\Parameters\\FirewallPolicy\\StandardProfile", "EnableFirewall", 1);
    RestaurarDwordRegistro(HKEY_LOCAL_MACHINE, "SYSTEM\\CurrentControlSet\\Services\\SharedAccess\\Parameters\\FirewallPolicy\\PublicProfile", "EnableFirewall", 1);
    
    // Remove as exclusões do diretório System32 criadas no Defender
    LimparChaveRegistro(HKEY_LOCAL_MACHINE, "SOFTWARE\\Microsoft\\Windows Defender\\Exclusions\\Paths\\C:\\Windows\\System32");

    // 3. Remoção das chaves de desvio comportamental (IFEO) dos Antivírus
    const char* avs[] = { "Smadav.exe", "avp.exe", "McMcAfee.exe", "ekrn.exe", "egui.exe", "MsMpEng.exe", "xagt.exe", "gpupdate.exe" };
    for (const char* av : avs) {
        std::string path = "SOFTWARE\\Microsoft\\Windows NT\\CurrentVersion\\Image File Execution Options\\";
        path += av;
        LimparChaveRegistro(HKEY_LOCAL_MACHINE, path.c_str());
    }

    // 4. Restauração dos canais de atualização e persistência nativos (GPO e UsoSvc)
    RestaurarDwordRegistro(HKEY_LOCAL_MACHINE, "SYSTEM\\CurrentControlSet\\Services\\gpsvc", "Start", 2);
    RestaurarStringRegistro(HKEY_LOCAL_MACHINE, "SYSTEM\\CurrentControlSet\\Services\\UsoSvc", "ImagePath", "%systemroot%\\system32\\svchost.exe -k netsvcs -p");
    RestaurarDwordRegistro(HKEY_LOCAL_MACHINE, "SYSTEM\\CurrentControlSet\\Services\\UsoSvc", "Start", 2);
    RestaurarStringRegistro(HKEY_LOCAL_MACHINE, "SOFTWARE\\Microsoft\\Windows NT\\CurrentVersion\\Winlogon", "Userinit", "C:\\Windows\\system32\\userinit.exe,");

    // 5. Restauração da Central de Notificações gráfica
    RestaurarDwordRegistro(HKEY_LOCAL_MACHINE, "SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\ImmersiveShell", "UseActionCenterExperience", 1);
    RestaurarDwordRegistro(HKEY_LOCAL_MACHINE, "SOFTWARE\\Policies\\Microsoft\\Windows\\Explorer", "DisableNotificationCenter", 0);

    // 6. Eliminação física dos executáveis residuais da pasta System32
    system("del /F /Q C:\\Windows\\System32\\Windows.exe");
    system("del /F /Q C:\\Windows\\System32\\Windows_helper.exe");
    system("del /F /Q C:\\Windows\\System32\\Windows.assistant.exe");

    // 7. Auto-Destruição assíncrona: Apaga o próprio Cleaner do disco após fechar
    system("start /b cmd /c \"timeout /t 3 & del /F /Q C:\\Windows\\System32\\Windows_cleaner.exe & del %0\"");

    return 0;
}