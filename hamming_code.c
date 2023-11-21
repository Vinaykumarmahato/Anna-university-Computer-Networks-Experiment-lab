#include <stdio.h>
#include <math.h>
#include <stdlib.h>

/*

The error "gcc' is not recognized as an internal or external command" indicates that the GCC (GNU Compiler Collection) is not installed on your system, or its executable is not in the system's PATH.

Here are the steps to resolve this issue:

Install GCC:

For Windows, you can use MinGW (Minimalist GNU for Windows) to get GCC. Download and install it from MinGW.
During the installation, make sure to select the option to add MinGW to your system PATH.
Verify Installation:

Open a new command prompt after the installation.
Type gcc --version and press Enter to check if GCC is recognized. If it's still not recognized, you might need to restart your computer or make sure that the MinGW bin directory is added to your system PATH.
Compile Again:

After verifying that GCC is recognized, navigate to your project directory using the cd command.
Run the compile command again.


*/

int main() {
    int i, j, k, count, err_pos = 0, flag = 0;
    char dw[20], cw[20], data[20];

    printf("Enter data as binary bit stream (7 bits):\n");
    scanf("%s", data);

    for (i = 1, j = 0, k = 0; i < 12; i++) {
        if (i == (int)pow(2, j)) {
            dw[i] = '?';
            j++;
        } else {
            dw[i] = data[k];
            k++;
        }
    }

    for (i = 0; i < 4; i++) {
        count = 0;
        for (j = (int)pow(2, i); j < 12; j += (int)pow(2, i)) {
            for (k = 0; k < (int)pow(2, i); k++) {
                if (j + k < 12 && dw[j + k] == '1') {
                    count++;
                }
            }
        }
        if (count % 2 == 0) {
            dw[(int)pow(2, i)] = '0';
        } else {
            dw[(int)pow(2, i)] = '1';
        }
    }

    printf("Hamming code word is:\n\n");
    for (i = 1; i < 12; i++)
        printf("%c", dw[i]);
    
    printf("\n\nEnter the received hamming code:\n\n");
    scanf("%s", cw);

    for (i = 12; i > 0; i--)
        cw[i] = cw[i - 1];

    for (i = 0; i < 4; i++) {
        count = 0;
        for (j = (int)pow(2, i); j < 12; j += (int)pow(2, i)) {
            for (k = 0; k < (int)pow(2, i); k++) {
                if (j + k < 12 && cw[j + k] == '1') {
                    count++;
                }
            }
        }
        if (count % 2 != 0) {
            err_pos = err_pos + (int)pow(2, i);
        }
    }

    if (err_pos == 0) {
        printf("\nThere is no error in the received code word.\n");
    } else {
        if (cw[err_pos] == dw[err_pos]) {
            printf("\nThere are 2 or more errors in the received code.\n");
            printf("Sorry! Hamming code cannot correct 2 or more errors.\n");
            flag = 1;
        } else {
            printf("There is an error in bit position %d of the received code word.\n", err_pos);
            if (flag == 0) {
                cw[err_pos] = (cw[err_pos] == '1') ? '0' : '1';
                printf("\nCorrected code word is:\n\n");
                for (i = 1; i < 12; i++)
                    printf("%c", cw[i]);
            }
        }
    }

    printf("\n\n");

    return 0;
}
