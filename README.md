# Reloj Fix3

App para Wear OS (paquete `com.relojfix3`).
Todos los días a las 7:57 suma 10 min al tiempo de apagado de pantalla y a las 8:07 restaura el valor original.

## Compilar en GitHub
1. Sube este contenido a un repositorio.
2. Ve a la pestaña **Actions** > **Build APK** (se ejecuta solo al subir; o usa *Run workflow*).
3. Descarga el APK en **Artifacts** > `RelojFix3-debug-apk`.

## Instalar en el reloj
    adb connect IP_DEL_RELOJ:PUERTO
    adb install app-debug.apk

Abre la app y concede los permisos que pida. Desactiva la optimización de batería para la app.
