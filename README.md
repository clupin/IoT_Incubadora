Incubadora IoT
===================


Hola, este es el proyecto de Incubadora para el ramo Internet de las Cosas (IoT) de la Universidad de Santiago de Chile (USACH).

Los alumnos a quienes pertenece este proyecto son:

- Cristian Espinoza
- Claudia Pérez
- Luis Riquelme

El proyecto consta de 2 partes, por un lado esta el codigo de Arduino, versionado de forma manual de acuerdo a los avances realizados, para lograr tener un mayor control de lo realizado y poder volver de forma cómoda y clara a los grandes saltos dados en pasos anteriores.
Por otro lado esta el código en Java, el cual permite realizar la comunicación del **Arduino** con el **Servidor** y este mismo con el chatbot de **Telegram**.

----------


Estructura de mensajes Arduino
-------------

Para poder enviar los **mensajes** a Arduino (actualmente "proyecto_v4.ino"), estos deben poseer la siguiente estructura:

- El primer dígito indica el sensor que se desea utilizar:

> - 0: todos los sensores
> - 1: lámpara de calor (indicado con led rojo)
> - 2: ventilador (indicado con led azul)
> - 3: servomotor
> - 4: sensor de temperatura

El segundo dígito indica la acción que se desea realizar con el sensor indicado, este valor es válido para todos los valores del primer dígito excepto 0 (ya que este lee todos los sensores):

> - 0: leer
> - 1: escribir

Los dígitos siguientes tienen relación con el valor que se desea enviar, este puede variar entre 0 y 255.
 
> **Nota:**

> - Para el caso del sensor de temperatura, la opción escribir tiene 2 opciones más, las cuales son:
>  - 0: escribir temperatura mínima
>  - 1: escribir temperatura máxima

> - Leer todos los sensores se devuelve en formato:
> > lampara/ventilador/servomotor/temperatura

----------


Estructura de mensajes API
-------------

Existe una **API** que entrega la información guardada de los estados en que se encuentra el arduino, se guarda esta información cada vez que se realiza un "readAll()" en Arduino, ya sea por acción de schedule o petición a través de Telegram.

Las rutas posibles para esta API son:

- Obtener lista de todos los datos
<p>@GET</p>
<p>/dato</p>

```json

[
	{
		"id":1,
		"heat_lamp":8,
		"fan_state":true,
		"servo_angle":93,
		"temperature":50.0,
		"date":1511725556231
	},{
		"id":2,
		"heat_lamp":9,
		"fan_state":true,
		"servo_angle":93,
		"temperature":64.26,
		"date":1511725562996
	}
]

```

- Obtener lista de todos los valores de Heat Lamp
<p>@GET</p>
<p>/dato/heatLamp</p>
```json

[
	{
		8
	},{
		9
	}
] 

```

- Obtener lista de todos los valores de Ventilador
<p>@GET</p>
<p>/dato/fanState</p>
```json

[
	{
		true
	},{
		true
	}
]
    
```

- Obtener lista de todos los valores de Bandeja
<p>@GET</p>
<p>/dato/servoAngle</p>
```json

[
	{
		93
	},{
		93
	}
] 

```

- Obtener lista de todos los valores de Temperatura
<p>@GET</p>
<p>/dato/temperature</p>
```json

[
	{
		50.0
	},{
		64.26
	}
] 

```





----------

#### <i class="icon-file"></i> Informe

El documento del informe se encuentra en el Drive del grupo, disponible también en el siguiente link <i class="icon-provider-gdrive"></i> [Ver documento](https://docs.google.com/document/d/1wwkghwGqaChSvA9dx7WjvqPfPHqH9tGVwRQVi_l2ag4/edit). 

----------



### Conexion

![Conexion de grove shield](https://raw.githubusercontent.com/clupin/IoT_Incubadora/master/Arduino/%C3%ADndice.png?token=AIbrix3JjXYh6LtlqLvEn8PYWA-CV6WGks5aFOxrwA%3D%3D "Conexion de grove shield")