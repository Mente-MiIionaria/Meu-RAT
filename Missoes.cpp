#include <iostream>
#include <chrono>

int main() {
    auto start = std::chrono::high_resolution_clock::now();
    
    int i = 0;
    
    while (i < 1000000000) {
        i++;
    }
    
    if (i >= 1000000000) {
            std::cout << "Ja contei do zero a um milhao.\n";
        }

    auto end = std::chrono::high_resolution_clock::now();
    auto duration1 = std::chrono::duration_cast<std::chrono::nanoseconds>(end - start);
    auto duration2 = std::chrono::duration<double>(end - start);
    std::cout << "\nTempo de execucao: " << duration1.count() << " nanosegundos\n" << std::endl;
    std::cout << "Tempo de execucao: " << duration2.count() << " segundos\n" << std::endl;
    return 0;
}