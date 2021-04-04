import os
import subprocess


class OutputConverter:
    def convert_output(self):
        p = subprocess.Popen(
            ["minisat", "io/input.txt", "io/output.txt"], cwd=os.getcwd()
        )
        p.wait()
        # print(os.path.exists("io/input.txt"))
        # os.system('cmd /c "minisat io/input.txt io/output.txt"')

        f_in = open("io/output.txt", "r")
        f_out = open("io/gui_output.txt", "w")
        status = f_in.readline().split()[0]
        if status == "SAT":
            f_out.write("1")
            sat = f_in.readline().split(" ")
            sat = [int(i) for i in sat if "-" not in i]
            for i in sat:
                baris = i // 256 + 1
                kolom = i // 16 % 16 + 1
                angka = i % 16

                f_out.write("\n%d %d %d" % (baris, kolom, angka))
        else:
            f_out.write("0")

        f_in.close()
        f_out.close()
