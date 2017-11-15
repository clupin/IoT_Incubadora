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


Estructura de mensajes
-------------

Para poder enviar los **mensajes** a Arduino, estos deben poseer la siguiente estructura:

- El primer dígito indica el sensor que se desea utilizar:

> - 0: todos los sensores
> - 1: lámpara de calor (indicado con led rojo)
> - 2: ventilador (indicado con led azul)
> - 3: servomotor
> - 4: sensor de temperatura
> - ...

El segundo dígito indica la acción que se desea realizar con el sensor indicado, este valor es válido para todos los valores del primer dígito excepto 0 (ya que este lee todos los sensores):

> - 0: leer
> - 1: escribir

Los dígitos siguientes tienen relación con el valor que se desea enviar, este puede variar entre 0 y 255.
 
> **Nota:**

> - Para el caso de escribir en el sensor de temperatura, este debe enviar un mensaje a Java de cual es la temperatura máxima y no "escribir" en el sensor, ya que esto no se realiza en ningún caso.

#### <i class="icon-file"></i> Informe

El documento del informe se encuentra en el Drive del grupo, disponible también en el siguiente link <i class="icon-provider-gdrive"></i> [Ver documento](https://docs.google.com/document/d/1kLUyE4AekxI3gem5dZ4AYuAD81TaHYN3kU59PaPLxao/edit). 

----------



### Conexion

![Conexion de grove shield](https://raw.githubusercontent.com/clupin/IoT_Incubadora/master/Arduino/%C3%ADndice.png?token=AIbrix3JjXYh6LtlqLvEn8PYWA-CV6WGks5aFOxrwA%3D%3D "Conexion de grove shield")