import serial
import random
ser = serial.Serial("COM5", timeout=1)

vars = [1, 0, 30, 22.1]

while True:
	vars[3] = random.randint(15, 30)
	msg = ser.readline()
	if(len(msg) == 0):
		continue
	print "MENSAJE", msg
	if(msg[0:2] == "00"):
		res = "{}/{}/{}/{}|00\n".format(vars[0], vars[1], vars[2], vars[3])
		ser.write(bytes(res))
	else:
		# ESCRIBIR
		if msg[1] == "1":
			index = int(msg[0])-1
			value = int(msg[2:])
			vars[index] = value
		elif msg[1] == "0":
			print "LEYENDO", msg
			index = int(msg[0])-1
			res = "{}|{}{}\n".format(vars[index], msg[0], msg[1])
			ser.write(bytes(res))