import os
import subprocess
import time

from kivy.properties import ObjectProperty, StringProperty
from kivy.uix.button import Button
from kivy.uix.popup import Popup
from kivymd.app import MDApp
from kivymd.uix.boxlayout import MDBoxLayout
from kivymd.uix.button import MDRectangleFlatButton
from kivymd.uix.gridlayout import MDGridLayout
from kivy.core.window import Window

from .output_converter import OutputConverter


class SelectButton(Button):
    value = StringProperty(None)


class SelectPopup(Popup):
    button = ObjectProperty()

    def select(self, value, *args):
        self.button.set_value(value)
        self.dismiss()


class SudokuCell(MDRectangleFlatButton):
    value = StringProperty(None)

    def __init__(self, **kwargs):
        super().__init__(**kwargs)
        self.bind(on_release=self.show_popup)
        self.popup = SelectPopup(button=self)

    def show_popup(self, *args):
        self.popup.open()

    def set_value(self, value):
        self.value = value


class SudokuBox(MDBoxLayout):
    pass


class SolvePopup(Popup):
    pass


class Sudoku(MDGridLayout):
    def __init__(self, **kwargs):
        super().__init__(**kwargs)
        self.popup = SolvePopup()

    def solve(self):

        # Reset i/o files
        # File output sebelumnya perlu dihapus biar ga baca yg sebelumnya
        # Unkomen dibawah kalo kode ngubah output minisat ke gui udh jadi
        # output_path = "io/gui_output.txt"
        # if os.path.exists(output_path):
        #     os.remove(output_path)
        self.write_input()
        self.read_output()

    def write_input(self):
        input_path = "io/gui_input.txt"
        with open(input_path, "w") as f:
            boxes = self.ids.board.children[::-1]
            for idx1, box in enumerate(boxes):
                cells = box.ids.box.children[::-1]
                for idx2, cell in enumerate(cells):
                    if cell.value is not None:
                        box_coords = (idx1 // 4, idx1 % 4)
                        cell_coords = (idx2 // 4, idx2 % 4)
                        print(
                            box_coords[0] * 4 + cell_coords[0] + 1,
                            box_coords[1] * 4 + cell_coords[1] + 1,
                            cell.value,
                            file=f,
                        )
        p = subprocess.Popen(["src/InputConverter"], cwd=os.getcwd())
        p.wait()

    def read_output(self):
        output_path = "io/gui_output.txt"

        op = OutputConverter()
        op.convert_output()

        while not os.path.exists(output_path):
            time.sleep(0.1)

        with open(output_path, "r") as f:
            line = f.readline()
            flag = line.split()[0]
            grid = [["-" for x in range(16)] for y in range(16)]
            if flag == "1":
                line = f.readline()
                while line:
                    row, col, val = line.split()
                    print(line)
                    grid[int(row) - 1][int(col) - 1] = val
                    line = f.readline()
                # print(grid)
                self.update(grid)
            else:
                self.popup.open()

    def update(self, grid):
        boxes = self.ids.board.children[::-1]
        for idx1, box in enumerate(boxes):
            cells = box.ids.box.children[::-1]
            for idx2, cell in enumerate(cells):
                box_coords = (idx1 // 4, idx1 % 4)
                cell_coords = (idx2 // 4, idx2 % 4)
                x = box_coords[0] * 4 + cell_coords[0]
                y = box_coords[1] * 4 + cell_coords[1]
                cell.set_value(grid[x][y])


class SudokuSolverApp(MDApp):
    def build(self):
        self.theme_cls.theme_style = "Dark"
        Window.size = (600, 600)
