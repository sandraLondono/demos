const {defineConfig} = require('cypress')
const allureWriter = require('@shelex/cypress-allure-plugin/writer');

module.exports = defineConfig({
    video: true,
    e2e: {
        setupNodeEvents(on, config) {
            allureWriter(on, config);
            require('@cypress/grep/src/plugin')(config);
            return config;
        },
        baseUrl: 'https://www99.bancred.com.bo/yape.com.bo/'
    },
    env: {
        allure: true,
        allureAttachVideos: true,
        allureSkipAfterEach: false,
    },

})