import java.io.*;
import java.util.*;

class TK_logkom {
    static final int UKURAN_AKAR = 3;
    static final int UKURAN = 9;
    public static void main(String[] args) {
        System.out.println(ThreeDtoint(1,2,3)); // Display the string.
        input_template();
    }

    public static int ThreeDtoint(int a, int b, int c) {
        // return ((a)*(100) + (b)*10 + c);
        return ((a-1)*(UKURAN*UKURAN) + (b-1)*UKURAN + c);
    }

    public static void input_template() {
        StringBuilder klausa_baris_1 = new StringBuilder("");
        StringBuilder klausa_baris_2 = new StringBuilder("");
        StringBuilder klausa_kolom_1 = new StringBuilder("");
        StringBuilder klausa_kolom_2 = new StringBuilder("");
        StringBuilder klausa_blok_1 = new StringBuilder("");
        StringBuilder klausa_blok_2 = new StringBuilder("");
        StringBuilder klausa_angka_1 = new StringBuilder("");
        StringBuilder klausa_angka_2 = new StringBuilder("");
        for (int baris = 1; baris <= UKURAN; baris++) {
            for (int kolom = 1; kolom <= UKURAN; kolom++) {
                for (int angka = 1; angka <= UKURAN; angka++) {
                    klausa_angka_1.append(ThreeDtoint(baris, kolom, angka) + " ");
                    
                    klausa_baris_1.append(ThreeDtoint(angka, baris, kolom) + " ");

                    klausa_kolom_1.append(ThreeDtoint(baris, angka, kolom) + " ");

                    int[] posisi_convert = convertBlok(baris, angka);
                    int baris_convert = posisi_convert[0];
                    int kolom_convert = posisi_convert[1];

                    klausa_blok_1.append(ThreeDtoint(baris_convert, kolom_convert, kolom) + " ");

                    for (int angka2 = angka + 1; angka2 <= UKURAN; angka2++) {
                        klausa_angka_2.append("-" + ThreeDtoint(baris, kolom, angka) + " ");
                        klausa_angka_2.append("-" + ThreeDtoint(baris, kolom, angka2) + " ");
                        klausa_angka_2.append("0\n");

                        klausa_baris_2.append("-" + ThreeDtoint(angka, baris, kolom) + " ");
                        klausa_baris_2.append("-" + ThreeDtoint(angka2, baris, kolom) + " ");
                        klausa_baris_2.append("0\n");
                        
                        klausa_kolom_2.append("-" + ThreeDtoint(baris, angka, kolom) + " ");
                        klausa_kolom_2.append("-" + ThreeDtoint(baris, angka2, kolom) + " ");
                        klausa_kolom_2.append("0\n");
                        
                        int[] posisi_convert2 = convertBlok(baris, angka2);
                        int baris_convert2 = posisi_convert2[0];
                        int kolom_convert2 = posisi_convert2[1];

                        klausa_blok_2.append("-" + ThreeDtoint(baris_convert, kolom_convert, kolom) + " ");
                        klausa_blok_2.append("-" + ThreeDtoint(baris_convert2, kolom_convert2, kolom) + " ");
                        klausa_blok_2.append("0\n");
                    }
                }
                klausa_angka_1.append("0\n");
                klausa_baris_1.append("0\n");
                klausa_kolom_1.append("0\n");
                klausa_blok_1.append("0\n");
            }
        }
        // System.out.println(klausa_angka_2);
        try {
            FileWriter myWriter = new FileWriter("input.txt");

            int jumlah_var = (int)Math.pow(UKURAN, 3);
            int input_klausa = 0;
            int jumlah_klausa = (int)(( (UKURAN/2)*Math.pow(UKURAN, 3) + (UKURAN * UKURAN) )*4) + input_klausa;

            // myWriter.write("p cnf 999 " + jumlah_klausa + "\n");
            myWriter.write("p cnf " + jumlah_var + " " + jumlah_klausa + "\n");

            myWriter.write(klausa_blok_1.toString());
            myWriter.write(klausa_blok_2.toString());

            myWriter.write(klausa_baris_1.toString());
            myWriter.write(klausa_baris_2.toString());

            myWriter.write(klausa_kolom_1.toString());
            myWriter.write(klausa_kolom_2.toString());
            
            myWriter.write(klausa_angka_1.toString());
            myWriter.write(klausa_angka_2.toString());
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
            System.out.println(Arrays.toString(convertBlok(1,5)));
            System.out.println(Arrays.toString(convertBlok(2,9)));
            System.out.println(Arrays.toString(convertBlok(2,6)));
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static int[] convertBlok(int baris, int kolom) {
        int baris_luar_blok = ((baris - 1) / UKURAN_AKAR) * UKURAN_AKAR;
        int kolom_luar_blok = ((baris - 1) % UKURAN_AKAR) * UKURAN_AKAR;
        int baris_dalam_blok = ((kolom - 1) / UKURAN_AKAR);
        int kolom_dalam_blok = ((kolom - 1) % UKURAN_AKAR);

        int baris_akhir = baris_luar_blok + baris_dalam_blok + 1;
        int kolom_akhir = kolom_luar_blok + kolom_dalam_blok + 1;

        return new int[]{baris_akhir, kolom_akhir};
    }
}