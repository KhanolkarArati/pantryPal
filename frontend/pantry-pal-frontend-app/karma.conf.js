module.exports = function (config) {
  config.set({
    frameworks: ['jasmine', '@angular-devkit/build-angular'],
    browsers: ['ChromeHeadless'],
    reporters: ['progress', 'coverage'],
    coverageReporter: {
      dir: require('path').join(__dirname, './coverage'),
      subdir: '.',
      reporters: [
        { type: 'json', file: 'coverage.json' },
        { type: 'lcov' },
        { type: 'text-summary' }
      ]
    },
    angularCli: {
      environment: 'prod'
    },
    // Ensures that Karma runs headless in CI environments
    customLaunchers: {
      ChromeHeadlessCI: {
        base: 'ChromeHeadless',
        flags: ['--no-sandbox', '--disable-gpu', '--remote-debugging-port=9222']
      }
    },
    // Adjust this to control browser timeouts in CI
    browserNoActivityTimeout: 100000, // adjust as necessary for your test duration
    captureTimeout: 120000,
    singleRun: true, // ensures tests run once and exit (useful for CI)
    autoWatch: false, // prevents watching in CI (for faster execution)
    restartOnFileChange: false // avoid unnecessary restarts on file changes
  });
};
