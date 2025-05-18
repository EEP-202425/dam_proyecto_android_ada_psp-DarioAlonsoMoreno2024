[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/O1oNnYGo)


1.- VIDEO =>

https://eepmad-my.sharepoint.com/:v:/g/personal/dario-alonso1_eep-igroup_com/ETzDSt8BGmpOrU1hxsuyMikBGoAdEkidpbz1ksHpG6BjBw?e=ajQlJ2&nav=eyJyZWZlcnJhbEluZm8iOnsicmVmZXJyYWxBcHAiOiJTdHJlYW1XZWJBcHAiLCJyZWZlcnJhbFZpZXciOiJTaGFyZURpYWxvZy1MaW5rIiwicmVmZXJyYWxBcHBQbGF0Zm9ybSI6IldlYiIsInJlZmVycmFsTW9kZSI6InZpZXcifX0%3D



2.Requisitos Funcionales

2.1. Aplicación Android

  RF-AN-01 => Implementado
  RF-AN-02 => Implementado
  RF-AN-03 => Implementado
  RF-AN-04 => Implementado
  RF-AN-05 => Implementado
  RF-AN-06 => No implementado
  RF-AN-07 => Implementado
  RF-AN-08 => Implementado
  RF-AN-09 => Implementado
  RF-AN-10 => Implementado
  RF-AN-11 => No implementado.
  
2.2. API REST

   RF-API-01 => Implementado
   RF-API-02 => Implementado
   RF-API-03 => Implementado
   RF-API-04 => Implementado
   RF-API-05 => Implementado
   RF-API-06 => No implementado
   RF-API-07 => Implementado
   RF-API-08 => Implementado
   RF-API-09 => Implementado
   RF-API-10 => Implementado
   RF-API-11 => No implementado
   
2.3. Acceso a Datos

  RF-BD-01 => Implementado
  RF-BD-02 => Implementado
  RF-BD-03 => Implementado
  RF-BD-04 => Implementado
  RF-BD-05 => Implementado
  RF-BD-06 => Implementado
  RF-BD-07 => Implementado
  RF-BD-08 => Implementado
  
3.Requisitos Técnicos

3.1. Arquitectura y Desarrollo

  RT-ARQ-01 => Implementado
  RT-ARQ-02 => Implementado
  RT-ARQ-03 => Implementado
  RT-ARQ-04 => Implementado
  RT-ARQ-05 => Implementado
  RT-ARQ-06 => Implementado
  RT-ARQ-07 => Implementado
  RT-ARQ-08 => Implementado
  RT-ARQ-09 => Implementado
  RT-ARQ-10. => Implementado
  
3.2. Seguridad

  RT-SEG-01 => Implementado 
  RT-SEG-02 => Implementado
  RT-SEG-03 => No implementado
  RT-SEG-04 => Implementado
  RT-SEG-05 => Implementado
  
3.3. Pruebas

  RT-PR-01 => Implementado
  RT-PR-02 => No implementado
  RT-PR-03 => No implementado
  RT-PR-04 => No implementado
4.Funcionalidades Adicionales

      FA-01 => Implementado
      FA-02 => No implementado
      FA-03 => No implementado
5.Entrega

  RE-01 => Implementado
  RE-02 => Implementado
    RE-02.1 => Implementado 
    RE-02.2 => Implementado
    RE-02.3 => Implementado

  RE-03 => Implementado
    RE-03.1 => Implementado 
    RE-03.2 => Implementado. 
    RE-03.3 => Implementado 
    RE-03.4 => Implementado 
    RE-03.5 => Implementado

Descripcion:

RepuestosAlonso es una aplicación cliente-servidor para la gestión de repuestos de coches. El backend está desarrollado con Spring Boot y Spring Data JPA (MySQL) y ofrece una API REST documentada con Swagger UI. El frontend es una app Android escrita en Kotlin con Jetpack Compose, Retrofit y StateFlow, que permite listar, crear, editar, borrar y pedir repuestos de manera interactiva.

En RepuestosAlonso el usuario puede autenticarse con un login seguro mediante token JWT. Una vez dentro, accede a un listado de repuestos donde se muestra el modelo, el precio y el año de cada pieza. Desde esa pantalla puede añadir nuevos repuestos rellenando un formulario sencillo, editar cualquier repuesto existente mediante un diálogo emergente y eliminarlo con un botón de papelera. También puede generar un pedido de un repuesto, que se registra en la base de datos.

La arquitectura se basa en tres capas: la app Android consume la API REST de Spring Boot a través de Retrofit, la cual expone endpoints para obtener todos los repuestos, consultar uno por ID, crear, actualizar y eliminar. El backend persiste los datos en MySQL usando Hibernate y ofrece en “/swagger-ui/index.html” una interfaz interactiva donde se describen todos los endpoints y se pueden probar directamente.

En la aplicación, tras el login, la barra superior ofrece un botón de recarga para refrescar la lista. En cada tarjeta de repuesto aparecen iconos de editar (tinte azul) y borrar (tinte rojo), un botón “Pedir” y un botón flotante ➕ para añadir. Se muestran mensajes tipo Snackbar para confirmar cada acción.

Como siguientes pasos está previsto reforzar la validación de datos en el backend con JSR-380, añadir paginación, ordenación y filtrado de resultados, implementar el registro de usuarios desde la app, y cubrir todo el proyecto con pruebas unitarias en la API, la persistencia y el ViewModel de Android.




    
