# base-ong-client-android
Repositorio base para Caso ONG de Android!

## Requerimientos
Para poder utilizar el login de facebook correctamente cada desarrollador se debe generar una clave Hash para guardarla en la consola de Facebook, sino no les va a funcionar el inicio de sesion con Facebook desde la aplicación.  
A continuación les digo como generarla con **Win10**:
1. Descargue [OpenSSl](https://code.google.com/archive/p/openssl-for-windows/downloads) ya sea el tercero o el cuarto (con **e** funcionará mejor) según su sistema de 32 bits o 64 bits.
2. Extraiga el zip descargado y muévalo dentro del directorio C.
3. Abra la carpeta hasta el contenedor y copie la ruta (agregar **\openssl** al final), debería ser algo como:
~~~
C:\openssl-0.9.8k_X64\bin\openssl
~~~
4. ahora necesitamos la ubicación del almacén de claves de depuración, para eso abra 
C -> Users -> YourUserName -> .android
debería haber un nombre de archivo debug.keystore, ahora copie la ubicación de la ruta, debería ser algo como: 
~~~
C:\Users\Bren\.android\debug.keystore
~~~
5. ahora abra el símbolo del sistema y escriba el comando:
~~~
cd C:\Users\{yourUser}\.android 
~~~
en mi caso: 
~~~
cd C:\Users\Bren\.android
~~~
6. ahora construye el siguiente comando (en un block de notas para poder modificarlo con tus rutas): 
~~~
keytool -exportcert -alias androiddebugkey -keystore {RUTA_PUNTO_4} | {RUTA_PUNTO_3} sha1 -binary | {RUTA_PUNTO_3} base64
~~~
en mi caso, el comando se verá así: 
~~~
keytool -exportcert -alias androiddebugkey -keystore C:\Users\Bren\.android\debug.keystore | C:\openssl-0.9.8e_X64\bin\openssl sha1 -binary | C:\openssl-0.9.8e_X64\bin\openssl base64
~~~
7. ahora ingrese este comando en el símbolo del sistema, si hizo lo correcto, se le pedirá una contraseña (la contraseña es android) 
~~~
Enter keystore password:  android
~~~
8. eso es todo, se te dará el Key Hash para poner en la [consola de facebook](https://developers.facebook.com/apps/)


