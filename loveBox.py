from tkinter import *
from socket import *
import threading


p = Tk()
p.geometry("500x500")
p.config(bg = "PaleVioletRed1")
p.title("LoveBox")

host = "177.220.18.110"
port = 3000
addr = (host, port)

s = socket(AF_INET, SOCK_STREAM)
s.bind((addr))
s.listen(5)
print("aguardando conexao")
(conexao, cliente) = s.accept()

v = StringVar(p)
lb = Label(p, textvariable = v, wraplength = 250, width = 45, height = 35, bg = "PaleVioletRed1", fg = "white", font = "Times 24", anchor = CENTER)
lb.pack(pady = 10)

def receber():
    while True:
        data = conexao.recv(1024)
        mensagem = data
        v.set(mensagem)

print("conectado")
th = threading.Thread(target = receber)
th.start()
p.mainloop()



