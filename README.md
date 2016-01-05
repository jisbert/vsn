# Cliente de la matriz de conmutación elpro SDZ16

Cliente que se conecta a la matriz de conmutación elpro SDZ16 y envía un comando por el puerto serie.

Este cliente se ha desarrollado en el marco de la prueba del proceso de selección para VSN.

## Requisitos

Para ejecutar la aplicación

```bash
$ java -version
java version "1.8.0_66"
Java(TM) SE Runtime Environment (build 1.8.0_66-b17)
Java HotSpot(TM) 64-Bit Server VM (build 25.66-b17, mixed mode)
```

Para compilar la aplicación se requiere además el [JDK de Oracle (jdk1.8.0_66)](http://www.oracle.com/technetwork/java/javase/downloads/index.html) y haber instalado el [Java Communications API](http://www.oracle.com/technetwork/java/javasebusiness/downloads/java-archive-downloads-misc-419423.html) en el directorio `lib` del proyecto.

## Instalación

Únicamente es necesario instalar el proyecto para ejecutar los casos de prueba y generar la distribución. Existen dos modos alternativos de hacerlo:

1. Clonar el repositorio con git

  ```bash
  $ git clone -b master https://github.com/jisbert/vsn-sdz16-cli.git
  ```

2. Descargar y extraer la última revisión

  ```bash
  $ curl -LO https://github.com/jisbert/vsn-sdz16-cli/archive/master.zip && 7z x master.zip
  ```

Para utilizar la aplicación basta con extraer el contenido de la distribución en un directorio y ejecutar la aplicación desde la línea de comandos.

```bash
$ 7z x vsn-sdz16-cli.zip
$ vsn-sdz16-cli/bin/vsn-sdz16-cli
```

## Generar la distribución

El siguiente comando compila el código fuente, ejecuta las pruebas unitarias y genera la distribución, debe ejecutarse en el directorio de instalación del proyecto

```bash
$ ./gradlew build
```

La primera vez que se ejecuta el comando el programa descarga la distribución de [gradle](http://gradle.org/) utilizada para desarrollar el proyecto, por lo que tarda más tiempo en ejecutarse, las siguientes veces tarda considerablemente menos tiempo.

Una vez generada la distribución el informe de las pruebas unitarias puede consultarse en la siguiente ubicación

```bash
$ firefox build/reports/tests/index.html
```

## Utilizar la librería Java Communications API

Para utilizar esta aplicación no es necesario utilizar Java Communications API, puesto que no se establece una conexión real con la matriz de conmutación sino que se emplea una implementación que suplanta a la conexión real. Por otro lado, tanto el API como el tipo de conexión son anticuados, lo que puede impedir utilizar el API incluso de forma simulada, produciéndose errores al tratar de utilizar la clase `javax.comm.CommPortIdentifier`. No obstante, la aplicación trata de obtener un puerto serie utilizando la librería y se ha preparado el API de modo que resulte posible inyectar un puerto serie para poder establecer una conexión serie real con la matriz (véase la documentación del API para más información).

Este apartado describe como configurar el entorno para utilizar la librería Java Communications API en un sistema Linux de 32 bits (no existe soporte oficial para sistemas Linux de 64 bit, Windows o MacOS). Esta información se ha obtenido de la documentación de la librería. Desde el directorio raíz del proyecto se crea un enlace al fichero `javax.comm.properties` en el directorio que contiene `comm.jar` y se configura la variable `LD_LIBRARY_PATH` de modo que apunte al directorio que contiene las librerías nativas.

```bash
$ ln -rs lib/commapi/docs/javax.comm.properties lib/commapi/jar/
$ export LD_LIBRARY_PATH=`pwd`/lib/commapi/lib/${LD_LIBRARY_PATH+:}$LD_LIBRARY_PATH
```  

## Autor

Jose Gisbert
