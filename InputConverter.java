import java.io.*;
import java.util.*;

class InputConverter {
    static final int UKURAN_AKAR = 4;
    static final int UKURAN = UKURAN_AKAR*UKURAN_AKAR;
    static int jumlah_input = 0;
    public static void main(String[] args) {
        try {
            FileWriter file_template = new FileWriter("input.txt");
            File file_user = new File("gui_input.txt");
            Scanner reader = new Scanner(file_user);
            String klausa_input = input_user(reader, file_template);
            input_template(file_template, klausa_input);
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static int ThreeDtoint(int a, int b, int c) {
        // return ((a)*(100) + (b)*10 + c);
        return ((a-1)*(UKURAN*UKURAN) + (b-1)*UKURAN + c);
    }

    public static String input_user(Scanner reader, FileWriter file_template) {
        StringBuilder klausa_input = new StringBuilder("");
        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            String[] splitted = line.split(" ");
            if (splitted.length >= 3) {
                int baris = Integer.parseInt(splitted[0]);
                int kolom = Integer.parseInt(splitted[1]);
                int angka = Integer.parseInt(splitted[2]);
                klausa_input.append(ThreeDtoint(baris, kolom, angka) + " 0\n");
                jumlah_input++;
            }
        }
        return klausa_input.toString();
    }

    public static void input_template(FileWriter file_template, String klausa_input) {
        StringBuilder klausa_baris_1 = new StringBuilder("");
        StringBuilder klausa_baris_2 = new StringBuilder("");
        StringBuilder klausa_kolom_1 = new StringBuilder("");
        StringBuilder klausa_kolom_2 = new StringBuilder("");
        StringBuilder klausa_blok_1 = new StringBuilder("");
        StringBuilder klausa_blok_2 = new StringBuilder("");
        StringBuilder klausa_angka_1 = new StringBuilder("");
        StringBuilder klausa_angka_2 = new StringBuilder("");
        for (int x = 1; x <= UKURAN; x++) {
            for (int y = 1; y <= UKURAN; y++) {
                for (int z = 1; z <= UKURAN; z++) {
                    klausa_angka_1.append(ThreeDtoint(x, y, z) + " ");
                    
                    klausa_baris_1.append(ThreeDtoint(z, x, y) + " ");

                    klausa_kolom_1.append(ThreeDtoint(x, z, y) + " ");

                    int[] posisi_convert = convertBlok(x, z);
                    int baris_convert = posisi_convert[0];
                    int kolom_convert = posisi_convert[1];

                    klausa_blok_1.append(ThreeDtoint(baris_convert, kolom_convert, y) + " ");

                    for (int z2 = z + 1; z2 <= UKURAN; z2++) {
                        klausa_angka_2.append("-" + ThreeDtoint(x, y, z) + " ");
                        klausa_angka_2.append("-" + ThreeDtoint(x, y, z2) + " ");
                        klausa_angka_2.append("0\n");

                        klausa_baris_2.append("-" + ThreeDtoint(z, x, y) + " ");
                        klausa_baris_2.append("-" + ThreeDtoint(z2, x, y) + " ");
                        klausa_baris_2.append("0\n");
                        
                        klausa_kolom_2.append("-" + ThreeDtoint(x, z, y) + " ");
                        klausa_kolom_2.append("-" + ThreeDtoint(x, z2, y) + " ");
                        klausa_kolom_2.append("0\n");
                        
                        int[] posisi_convert2 = convertBlok(x, z2);
                        int baris_convert2 = posisi_convert2[0];
                        int kolom_convert2 = posisi_convert2[1];

                        klausa_blok_2.append("-" + ThreeDtoint(baris_convert, kolom_convert, y) + " ");
                        klausa_blok_2.append("-" + ThreeDtoint(baris_convert2, kolom_convert2, y) + " ");
                        klausa_blok_2.append("0\n");
                    }
                }
                klausa_angka_1.append("0\n");
                klausa_baris_1.append("0\n");
                klausa_kolom_1.append("0\n");
                klausa_blok_1.append("0\n");
            }
        }
        try {
            int jumlah_var = (int)Math.pow(UKURAN, 3);
            int jumlah_klausa = (int)(( ((UKURAN-1.0)/2)*Math.pow(UKURAN, 3) + (UKURAN * UKURAN) )*4) + jumlah_input;

            // file_template.write("p cnf 999 " + jumlah_klausa + "\n");
            file_template.write("p cnf " + jumlah_var + " " + jumlah_klausa + "\n");

            file_template.write(klausa_input);

            file_template.write(klausa_blok_1.toString());
            file_template.write(klausa_blok_2.toString());

            file_template.write(klausa_baris_1.toString());
            file_template.write(klausa_baris_2.toString());

            file_template.write(klausa_kolom_1.toString());
            file_template.write(klausa_kolom_2.toString());
            
            file_template.write(klausa_angka_1.toString());
            file_template.write(klausa_angka_2.toString());
            file_template.close();
            System.out.println("Successfully wrote to the file.");
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