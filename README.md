Barça Catch-It: Training

## 📌 Descripción del proyecto

**Barça Catch-It: Training** es un videojuego arcade 2D tipo “catch-it” desarrollado como proyecto académico de desarrollo de videojuegos.

El jugador controla a un futbolista real del FC Barcelona (como Pedri) situado en la parte inferior de la pantalla. El objetivo es recoger objetos positivos que caen desde la parte superior mientras se esquivan objetos negativos.

El juego se basa en una mecánica simple pero adictiva, con dificultad progresiva y un sistema de puntuación que incentiva la mejora continua del jugador.

### 🎮 Características principales

* Jugabilidad arcade 2D rápida y sencilla
* Personajes basados en jugadores reales del FC Barcelona
* Sistema de puntuación en tiempo real
* Dificultad progresiva (la velocidad aumenta con la puntuación)
* Sistema de usuario (login y registro)
* Ranking con los 10 mejores resultados (Hall of Fame)
* Controles simples con teclado y ratón

---

## 🚀 Instrucciones para ejecutar el juego

El videojuego **Barça Catch-It: Training** está desarrollado en **Java (Swing)**, por lo que se ejecuta como una aplicación de escritorio.

---

### 📌 Requisitos previos

Antes de ejecutar el proyecto necesitas:

* Java JDK 8 o superior instalado
* Un IDE como:

  * IntelliJ IDEA
  * Eclipse
  * NetBeans
* MySQL instalado y en ejecución
* Base de datos creada

---

### 🗄️ Base de datos

El juego guarda las puntuaciones en MySQL.

Debes crear la base de datos:

```sql
CREATE DATABASE barca_catch_it;
```

Y dentro la tabla:

```sql
CREATE TABLE ranking (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50),
    puntuacion INT,
    tiempo_segundos INT,
    vidas_finales INT
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

---

### ⚙️ Configuración de conexión

En el código Java (`BBDD`), asegúrate de que estos datos coinciden con tu entorno:

```java
String url = "jdbc:mysql://localhost:3306/barca_catch_it";
String user = "root";
String pass = "1234";
```

---

### ▶️ Cómo ejecutar el juego

1. Abre el proyecto en tu IDE
2. Asegúrate de tener la estructura de carpetas correcta:

   * `src/images/` (con todas las imágenes del juego)
3. Ejecuta la clase principal:

```java
public static void main(String[] args)
```

o directamente la clase:

```
Training.java
```

---

### 🎮 Ejecución paso a paso

1. El programa pedirá un nombre de usuario
2. Se abrirá la ventana del juego
3. Usa los controles para jugar:

   * ⬅️ / ➡️ o A / D para moverte
   * ESPACIO o ↑ para saltar
4. Recoge objetos positivos y evita los negativos
5. Intenta llegar a la puntuación objetivo
6. Tu puntuación se guarda automáticamente en la base de datos

---

### 🧩 Posibles errores comunes

* ❌ Error de conexión MySQL → comprobar usuario/contraseña
* ❌ Imágenes no cargan → revisar carpeta `src/images`
* ❌ No se abre ventana → asegurar ejecución desde `main`

---

### 🏁 Resultado final

Si todo está correcto, el juego:

* Se ejecutará en ventana Java Swing
* Guardará puntuaciones en MySQL
* Mostrará ranking de jugadores

---

### ▶️ Ejecución del proyecto

1. Clona el repositorio:

```bash
git clone https://github.com/tu-usuario/barca-catch-it-training.git
```

2. Entra en la carpeta del proyecto:

```bash
cd barca-catch-it-training
```

3. Instala dependencias (si el proyecto lo requiere):

```bash
npm install
```

4. Ejecuta el juego:

```bash
npm start
```

---

## 🎮 Controles

* ⬅️ Flecha izquierda / tecla A → mover a la izquierda
* ➡️ Flecha derecha / tecla D → mover a la derecha
* Ratón → navegación por menús

---

## 🏆 Objetivo del juego

Consigue la máxima puntuación posible recogiendo objetos positivos y evitando obstáculos. Compite por entrar en el **Top 10 del Hall of Fame**.


ANTES DE EJECUTAR EL PROGRAMA

1 - DESCARGAR UN PROGRAMA PARA PODER EJECUTAR EL ARCHIVO .SQL ADJUNTO
2 . UNA VEZ INSTALADO EL PROGRAMA REQUERIDO, ABRE EL ARCHIVO Barca-Catch-it-Training.sql
3 - EJECUTA EL CODIGO DEL sql PARA QUE LOS DATOS DEL PROGRAMA SE GUARDEN EN LA BASE DE DATOS
4 - ASEGÚRATE DE TENER CONFIGURADO MYSQL CON:
- Usuario: root
- Contraseña: 1234
- Base de datos: barca_catch_it
5 - ¡Disfruta!

RECOMENDACIONES

- INSTALAR MYSQL WORKBENCH
https://dev.mysql.com/downloads/workbench/

USAR ESTE COMANDO PARA MODIFICAR EL USUARIO ROOT;

ALTER USER 'root'@'localhost' IDENTIFIED BY '1234';
FLUSH PRIVILEGES;
