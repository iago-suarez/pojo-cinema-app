# pojo-cinema-app
Aplicación Web para la gestión de una cadena de cines elaborada en la asignatura Programación Avanzada.

# Instalación previa

Se requiere tener correctamente instalados:

* java jdk 7
* maven
* mysql (correctamente configurado)

Para más información de como configurar mysql ver los ficheros
* [instalacion_previa_unix.txt](https://github.com/iago-suarez/pojo-cinema-app/blob/master/instalacion_previa_unix.txt)
* [instalacion_previa_windows.txt](https://github.com/iago-suarez/pojo-cinema-app/blob/master/instalacion_previa_windows.txt)

# Descarga

Para descargar el código fuente de la aplicación:

	git clone https://github.com/iago-suarez/pojo-cinema-app

# Despliegue en Jetty

Para compilarlo y arrancar el servidor de aplicaciones Jetty:

	cd pojo-cinema-app
	mvn install sql:execute
	mvn jetty:run

Y podremos acceder a la web desde nuestro navegador con la url:

	http://localhost:9090/pojo-cinema-app/