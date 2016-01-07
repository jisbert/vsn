# Cliente de la matriz de conmutación elpro SDZ16

Cliente que se conecta a la matriz de conmutación elpro SDZ16 y envía un comando por el puerto serie.

Este cliente se ha desarrollado en el marco de la prueba del proceso de selección para VSN.

## Requisitos

Para ejecutar la aplicación:

    $ java -version
    java version "1.8.0_66"
    Java(TM) SE Runtime Environment (build 1.8.0_66-b17)
    Java HotSpot(TM) Client VM (build 25.66-b17, mixed mode)

Para utilizar las funciones de [Java Communications API](http://www.oracle.com/technetwork/java/javasebusiness/downloads/java-archive-downloads-misc-419423.html) y obtener descriptores efectivos de puertos serie del sistema es necesario configurar el sistema según se indica [a continuación](#javax-comm).

Para compilar la aplicación se requiere además el [JDK de Oracle (jdk1.8.0_66)](http://www.oracle.com/technetwork/java/javase/downloads/index.html) y haber instalado el Java Communications API en el directorio `lib` del proyecto.

## Instalación

Únicamente es necesario instalar el proyecto para ejecutar los casos de prueba y generar la distribución. Existen dos modos alternativos de hacerlo:

1. Clonar el repositorio con git

        $ git clone -b master https://github.com/jisbert/vsn-sdz16-cli.git $PROJECT_ROOT

2. Descargar y extraer la última revisión

        $ curl -LO https://github.com/jisbert/vsn-sdz16-cli/archive/master.zip && 7z x master.zip && mv vsn-sdz16-cli-master $PROJECT_ROOT

## Ejecutar la aplicación

Para utilizar la aplicación basta con extraer el contenido de la distribución en un directorio y ejecutar la aplicación desde la línea de comandos.

    $ 7z x vsn-sdz16-cli-2.2.zip && mv vsn-sdz16-cli-2.2 $DIST_ROOT
    $ $DIST_ROOT/bin/vsn-sdz16-cli

De forma equivalente.

    $ java -cp '$DIST_ROOT/lib/comm.jar:$DIST_ROOT/lib/commons-cli-1.3.1.jar:'\
    > '$DIST_ROOT/lib/logback-classic-1.1.3.jar:$DIST_ROOT/lib/logback-core-1.1.3.jar:'\
    > '$DIST_ROOT/lib/slf4j-api-1.7.7.jar:$DIST_ROOT/lib/vsn-sdz16-cli-2.2.jar' com.vsn.elpro.sdz16.Main

## Generar la distribución

El programa `gradlew` compila el código fuente, ejecuta las pruebas unitarias y genera la distribución, debe ejecutarse en el directorio de instalación del proyecto. Recordar de nuevo que para que funcione es necesario instalar Java Communications API en el directorio `lib` del proyecto.

    $ cd $PROJECT_ROOT
    $ 7z x $DOWNLOADS/comm-3.0_linux.zip -olib
    $ ./gradlew build

La primera vez que se ejecuta el comando el programa descarga la distribución de [gradle](http://gradle.org/) utilizada para desarrollar el proyecto, por lo que tarda más tiempo en ejecutarse, las siguientes veces tarda considerablemente menos tiempo.

Una vez generada la distribución el informe de las pruebas unitarias puede consultarse en la siguiente ubicación:

    $ firefox $PROJECT_ROOT/build/reports/tests/index.html

Las distribuciones (un archivo `tar` y un archivo `zip`) se generan en el directorio `$PROJECT_ROOT/build/distributions`.

## Utilizar la librería Java Communications API<a name="javax-comm"></a>

A continuación se describe como configurar el entorno para utilizar la librería Java Communications API en un sistema Linux de 32 bits (no existe soporte oficial para sistemas Linux de 64 bit, Windows o MacOS), esta información se ha obtenido de la documentación de la propia librería. Únicamente es necesario configurar la variable `LD_LIBRARY_PATH` de modo que apunte al directorio que contiene las librerías nativas, suponiendo que se ha instalado Java Communications API en el directorio `$PROJECT_ROOT/lib`:

    $ export LD_LIBRARY_PATH=$PROJECT_ROOT/lib/commapi/lib/${LD_LIBRARY_PATH+:}$LD_LIBRARY_PATH

## Autor

Jose Gisbert
