Cypress.Commands.add('captureConsoleErrors', () => {
    const consoleErrors = [];

    cy.on('window:before:load', (win) => {
        const originalError = win.console.error;
        const originalWarn = win.console.warn;

        win.console.error = function (...args) {
            consoleErrors.push(`[ERROR] ${args.join(' ')}`);
            originalError.apply(win.console, args);
        };

        win.console.warn = function (...args) {
            consoleErrors.push(`[WARN] ${args.join(' ')}`);
            originalWarn.apply(win.console, args);
        };
    });

    cy.intercept('GET', '**', (req) => {
        req.continue((res) => {
            if (res.statusCode >= 400) {
                consoleErrors.push(`[ASSET FAIL] ${res.statusCode} - ${req.url}`);
            }
        });
    });

    Cypress.on('home.css_selector.json:after:run', () => {
        if (consoleErrors.length > 0) {
            const logText = consoleErrors.join('\n');
            cy.allure().attachment('Errores de consola del navegador', logText, 'text/plain');
        }
    });
});
