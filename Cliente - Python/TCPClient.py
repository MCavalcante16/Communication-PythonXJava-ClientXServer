from socket import *
from random import *
import json

def gera_requestId(tamanho):
        caracters = '0123456789abcdefghijlmnopqrstuwvxz'
        id = ''
        for char in xrange(tamanho):
                id += choice(caracters)
        return  id


serverName = 'localhost'
serverPort = 7896
clientSocket = socket(AF_INET, SOCK_STREAM)
messageType = "0"
#three-way handshaking
clientSocket.connect((serverName, serverPort))
while(True):
	methodIdCollect = input('Voce deseja ler[0] ou escrever[1] noticias?\n')
	if methodIdCollect == 1:
		firstArgument = raw_input('Digite o titulo da noticia:\n')
		print ("\n\n")
		secondArgument = raw_input('Digite o texto da noticia:\n')
	elif methodIdCollect == 0:
		firstArgument = "n"
		secondArgument = "n"
	requestId = gera_requestId(10)


	noticia = {"id":0, "titulo": firstArgument, "texto": secondArgument}

	request = {
		"requestId": requestId,
		"messageType": 0,
		"ObjectReference": "SaveNewsService",
		"methodId": methodIdCollect,
		"arguments": noticia
	}
	
	clientSocket.send(bytes(json.dumps(request)+"\r\n"))
	
	receivedJson = bytes(clientSocket.recv(4096)).decode("utf-8")
	result = json.loads(receivedJson)

	if(result["requestId"] == requestId): 
		if(result["methodId"] == 0):
			for item in result["arguments"]:
				print ("\n\n\nTitulo:" + item["titulo"])
				print ("Texto: " + item["texto"]+"\n\n\n\n")
		else:
			for item in result["arguments"]:
				print ("\n\n\n" + item["arguments"] + "\n\n\n")

clientSocket.close()


