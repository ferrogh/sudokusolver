from kivy.properties import ObjectProperty, StringProperty
from kivy.uix.button import Button
from kivymd.app import MDApp
from kivymd.uix.boxlayout import MDBoxLayout
from kivymd.uix.button import MDRectangleFlatButton
from kivy.uix.popup import Popup


class SelectButton(Button):
    value = StringProperty(None)


class SelectPopup(Popup):
    button = ObjectProperty()

    def select(self, value, *args):
        self.button.set_value(value)
        self.dismiss()


class SudokuButton(MDRectangleFlatButton):
    value = StringProperty(None)

    def __init__(self, **kwargs):
        super().__init__(**kwargs)
        self.bind(on_release=self.show_popup)
        self.popup = SelectPopup(button=self)

    def show_popup(self, *args):
        self.popup.open()

    def set_value(self, value):
        self.value = value


class Sudoku(MDBoxLayout):
    def print(self):
        with open("io/gui_input.txt", "w") as f:
            boxes = self.ids.board.children[::-1]
            for idx, box in enumerate(boxes):
                if box.value is not None:
                    print((idx // 4) + 1, (idx % 4) + 1, box.value, file=f)


class SudokuSolverApp(MDApp):
    def build(self):
        self.theme_cls.theme_style = "Dark"
