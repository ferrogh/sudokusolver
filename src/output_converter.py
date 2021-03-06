import os
import subprocess


class OutputConverter:
    def convert_output(self):
        minisat_out = open("io/minisat_output.txt", "w")
        subprocess.call(
            "minisat io/input.txt io/output.txt",
            cwd=os.getcwd(),
            stdout=minisat_out,
            stderr=minisat_out,
        )

        f_in = open("io/output.txt", "r")
        f_out = open("io/gui_output.txt", "w")
        f_solution = open("io/solution.txt", "a")
        status = f_in.readline().split()[0]
        if status == "SAT":
            f_out.write("1")
            res = f_in.readline()
            res = res.split(" ")[:-1]
            sat = [int(i) for i in res if "-" not in i and i != ""]
            sol = [i for i in res if "-" not in i and i != ""]
            f_solution.write(" ".join(sol))
            f_solution.write("\n")
            for i in sat:
                baris = (i - 1) // 256 + 1
                kolom = (i - 1) // 16 % 16 + 1
                angka = i % 16
                if angka == 0:
                    angka = 16

                f_out.write("\n%d %d %d" % (baris, kolom, angka))
        else:
            f_out.write("0")

        f_in.close()
        f_out.close()
        f_solution.close()
