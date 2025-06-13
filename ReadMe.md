
# 🧪 Cypress Demo Project

Este es un proyecto base de automatización de pruebas end-to-end utilizando [Cypress](https://www.cypress.io/), diseñado para ser modular, escalable y fácilmente integrable con herramientas de reportería como Allure.

## 📁 Estructura del Proyecto

```bash
cypress_demo/
├── .github/               # Workflows o configuraciones de CI/CD
├── allure-report/         # Reportes HTML generados por Allure
├── allure-results/        # Resultados brutos generados por el plugin Allure
├── cypress/
│   ├── downloads/         # Archivos descargados por Cypress
│   ├── e2e/               # Pruebas automatizadas organizadas por feature
│   ├── fixtures/          # Datos estáticos en formato JSON para pruebas
│   ├── plugins/           # Plugins y configuración para Cypress
│   ├── screenshots/       # Evidencias visuales capturadas por Cypress
│   ├── selectors/         # Objetos de página o mapeo de elementos
│   ├── support/           # Comandos personalizados y configuraciones globales
│   ├── utils/             # Utilidades y funciones de apoyo
│   └── videos/            # Grabaciones de pruebas (si están habilitadas)
├── node_modules/          # Dependencias del proyecto
├── .gitignore             # Archivos ignorados por Git
├── cypress.config.js      # Configuración global de Cypress
├── package.json           # Dependencias y scripts del proyecto
├── package-lock.json      # Lockfile de npm
```

## 🚀 Scripts disponibles

Estos scripts están definidos en `package.json`:

| Script                 | Descripción |
|------------------------|-------------|
| `npm run test`         | Ejecuta todas las pruebas en modo headless con Allure activado. |
| `npm run test:chrome`  | Ejecuta las pruebas en el navegador Chrome. |
| `npm run test:smoke`   | Ejecuta solo los escenarios etiquetados con `@smoke`. |
| `npm run test:regression` | Ejecuta los escenarios con tag `@regression`. |
| `npm run test:critical` | Ejecuta los escenarios con tag `@critical`. |
| `npm run test:i`       | Abre la interfaz de Cypress para ejecución interactiva. |
| `npm run allure:report`| Genera el reporte HTML de Allure desde los resultados. |

## 🧩 Plugins y Dependencias

- [`@cypress/grep`](https://github.com/cypress-io/cypress-grep): Permite filtrar tests por tags (`@smoke`, `@regression`, etc.).
- [`@shelex/cypress-allure-plugin`](https://github.com/Shelex/cypress-allure-plugin): Generación de reportes Allure desde Cypress.
- [`cypress-real-events`](https://github.com/dmtrKovalenko/cypress-real-events): Simula eventos reales como `hover`, `scroll`, etc.

## 📚 Conceptos Clave de Cypress Usados

### 🔹 Estructura basada en buenas prácticas

- **Fixtures**: Separación de datos de prueba para mantenerlos reutilizables y limpios.
- **Support & Commands**: Abstracción de comandos personalizados para reuso.
- **Selectors**: Centralización de selectores para mayor mantenibilidad.
- **Utils**: Funciones auxiliares (e.g. formateo de fechas, mocks).
- **Tags (`@cypress/grep`)**: Permite ejecutar subconjuntos de pruebas desde la CLI.

### 🔹 Reporter de Allure

La integración de Allure genera reportes visuales detallados, con evidencias como:

- Screenshots automáticos de pruebas fallidas
- Videos (si se configuran)
- Anotaciones de pasos, fallas y tiempos de ejecución

Para visualizar el reporte, corre:

```bash
npm run allure:report
npx allure open allure-report
npx http-server path/allure/report/
```

## 🧪 Cómo agregar nuevos tests

1. Crea un archivo en `cypress/e2e/` con una convención clara (`login.cy.js`, `search.cy.js`, etc.).
2. Usa la siguiente estructura:

```js
/// <reference types="cypress" />
describe('Login Tests', () => {
  it('should log in successfully', {tags: ['@critical', '@regression']}, () => {
    cy.visit('/login');
    cy.get('#user').type('admin');
    cy.get('#pass').type('123456');
    cy.get('form').submit();
    cy.contains('Welcome').should('exist');
  });
});
```

## 🧠 Buenas prácticas implementadas

- Separación de lógica de pruebas, datos y elementos de UI
- Generación automática de evidencias
- Ejecución por entorno o tipo de pruebas (tags)
- Uso de comandos personalizados para DRY (Don't Repeat Yourself)

## 🏁 Requisitos Previos

- Node.js ≥ v18
- `npm install` (para instalar dependencias)
- [Allure Commandline](https://docs.qameta.io/allure/#_installing_a_commandline) instalado globalmente (por ejemplo: `npm install -g allure-commandline`)
