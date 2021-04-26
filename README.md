# ToastyPlugin
Cordova test plugin

## usage

```
window.plugins.printingPlugin.print('PAYMENT RECEIPT','<h1><b>This is working now</b></h1><p>text string</p>', function() {
            console.log('Excelsior!');
          }, function(err) {
            console.log('Uh oh... ' + err);
          });
```
