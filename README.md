# GymPlanner

GymPlanner es una aplicación web para crear y organizar rutinas de entrenamiento de forma visual, pensada principalmente para entrenadores personales y usuarios que trabajan con programas estructurados.

El foco del proyecto está en la planificación clara y rápida de rutinas, permitiendo construir programas completos a partir de una biblioteca de ejercicios reutilizable.

## Demo

La versión demo está desplegada online y contiene datos precargados para mostrar el flujo principal de la aplicación.

La demo incluye:

- Biblioteca de ejercicios precargada
- Programas de entrenamiento de ejemplo
- Planner visual funcional
- Organización de ejercicios por categorías
- Modo demo con algunas acciones deshabilitadas intencionadamente

## Funcionalidades principales

- Creación y organización de programas de entrenamiento
- Gestión de días dentro de un programa
- Asignación visual de ejercicios a cada día
- Definición de series, repeticiones, descansos, tempo e intensidad
- Reutilización de ejercicios en distintos programas
- Categorización de ejercicios por grupo muscular, equipamiento y tipo
- Filtros y paginación en listados
- Carga inicial de datos para entorno demo

## Stack técnico

### Backend

- Java
- Spring Boot
- Spring Data JPA
- PostgreSQL
- OpenAPI
- JUnit
- Maven
- Perfiles de configuración para `dev`, `test` y `prod`

### Frontend

- React
- JavaScript
- Interfaz visual tipo planner
- Drag & drop para organización de rutinas

### Infraestructura / despliegue

- Backend desplegado en entorno cloud
- Frontend desplegado en Vercel
- Base de datos PostgreSQL
- Configuración separada por entorno

## Arquitectura backend

El backend está estructurado siguiendo una arquitectura orientada a dominio y separación de responsabilidades.

Incluye:

- Interfaces OpenAPI para definición y documentación de endpoints
- Controladores REST
- DTOs específicos para requests y responses
- Servicios de aplicación
- Entidades JPA
- Mappers entre entidades y DTOs
- Enums de dominio
- Repositorios con Spring Data JPA
- Configuración personalizada de Jackson
- Manejo centralizado y personalizado de excepciones
- Configuraciones separadas para entornos `dev`, `test` y `prod`
- Carga inicial de datos mediante `data.sql`

## Testing

El proyecto incluye pruebas automatizadas orientadas a validar la lógica principal del backend.

Tipos de pruebas incluidas:

- Tests unitarios de servicios y lógica de negocio
- Tests de integración sobre endpoints y persistencia
- Configuración específica para entorno de test

## Documentación API

La API está definida mediante OpenAPI, lo que permite documentar los contratos de los endpoints y mantener una estructura clara entre frontend y backend.

La documentación facilita:

- Revisión de endpoints disponibles
- Consulta de modelos de request y response
- Validación del contrato de la API
- Separación entre definición de API e implementación

## Estado del proyecto

Proyecto en fase demo, desarrollado como aplicación full stack de portfolio.

El objetivo principal es demostrar capacidad para diseñar, implementar y desplegar una aplicación real usando Java, Spring Boot, PostgreSQL y React, aplicando buenas prácticas de backend, separación de responsabilidades, documentación de API y testing.

## Próximas mejoras técnicas

- Dockerización completa del entorno local
- Pipeline CI/CD con ejecución automática de tests
- Mejora de cobertura de pruebas
- Observabilidad básica con health checks y logs estructurados
- Generación/exportación avanzada de rutinas
- Posible procesamiento asíncrono para tareas secundarias
