// Empty constructor
function PrintingPlugin() {}

// The function that passes work along to native shells
// Message is a string, duration may be 'long' or 'short'
PrintingPlugin.prototype.print = function(message,successCallback, errorCallback) {
  var options = {};
  options.message = message;
  cordova.exec(successCallback, errorCallback, 'PrintingPlugin', 'print', [options]);
}

// Installation constructor that binds ToastyPlugin to window
PrintingPlugin.install = function() {
  if (!window.plugins) {
    window.plugins = {};
  }
  window.plugins.printingPlugin = new PrintingPlugin();
  return window.plugins.printingPlugin;
};
cordova.addConstructor(PrintingPlugin.install);