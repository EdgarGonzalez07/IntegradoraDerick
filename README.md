# [Peru Warehouse]

> **Proyecto Integrador – Desarrollo de Aplicaciones Móviles**
>
> **Cuatrimestre:** [4 D]  
> **Fecha de entrega:** 11 de diciembre

---

## 👥 Equipo de Desarrollo

| Nombre Completo | Rol / Tareas Principales | Usuario GitHub |
| :--- | :--- | :--- |
| [Emiliano Aviles Sanchez] | UI Design, Repositorio | @emicss |
| [Edgar Adrian Gonzales Guadarrama] | Backend, Retrofit | @EdgarGonzalez07 |
| [Yoed Soriano Ocampo] | Sensores, Lógica | @YoedSoka |

---

## 📱 Descripción del Proyecto

### ¿Qué hace la aplicación?
La aplicación es un sistema móvil de gestión de ventas para almacenes que implementa un CRUD completo (Crear, Leer, Actualizar y Eliminar) de productos y registros de venta. Está dirigida a pequeños y medianos negocios que requieren un control eficiente de su inventario y de las operaciones de venta desde un dispositivo Android.

El sistema permite registrar productos, consultar el inventario disponible, actualizar información como precios o cantidades, y eliminar registros cuando es necesario. Además, integra el uso de un sensor del dispositivo para ejecutar acciones específicas dentro de la aplicación, como confirmar operaciones, agilizar procesos de venta o activar funcionalidades sin interacción manual directa, mejorando la experiencia de usuario y la eficiencia operativa.

### Objetivo
Demostrar la implementación de una arquitectura robusta en Android utilizando servicios web y hardware del dispositivo.

---

## 🛠️ Stack Tecnológico y Características

Este proyecto ha sido desarrollado siguiendo estrictamente los lineamientos de la materia:

- **Lenguaje:** Kotlin 90% Python 10%
- **Interfaz de Usuario:** Jetpack Compose
- **Arquitectura:** MVVM (Model–View–ViewModel)
- **Conectividad (API REST):** Retrofit  
  - **GET:** Se obtienen los usuarios que se han registrado en la app.  
  - **POST:** Se envian los datos de los usuarios: 'username', 'email' y 'password'.
  - **UPDATE:** Se actualizan los datos del usuarios: 'username', 'email' y 'password'.  
  - **DELETE:** Se eliminan los datos del usuario: 'username', 'email' y 'password'.  
- **Sensores Integrado:** Acelerómetro y Giroscopio  
  - **Uso:** [El sensor del dispositivo se utiliza para activar acciones específicas dentro de la aplicación, como la confirmación de operaciones de venta, la ejecución de comandos rápidos o la navegación entre funciones del sistema, permitiendo una interacción más ágil y reduciendo la necesidad de intervención manual.]

---

## 🖼️ Capturas de Pantalla

> Agrega al menos 3 capturas.  
> Las imágenes deben estar dentro del repositorio o usar URLs válidas.

| Pantalla de Inicio | Operación CRUD | Uso del Sensor |
| :---: | :---: | :---: |
| ![Inicio](url_imagen) | ![CRUD](url_imagen) | ![Sensor](url_imagen) |

---

## 📦 Instalación y Releases

El ejecutable firmado (**.apk**) se encuentra disponible en la sección de **Releases** de este repositorio.

### Pasos de instalación

1. Ve a la sección **Releases** o haz clic [aquí](link_a_tus_releases).
2. Descarga el archivo `.apk` de la última versión.
3. Instálalo en tu dispositivo Android  
   *(asegúrate de permitir la instalación desde orígenes desconocidos)*.
