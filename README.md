# TELPO PRINT PLUGIN
Cordova TELPO print plugin

## installation
cordova plugin add path-to-the-repo


## usage

```
window.plugins.printingPlugin.print('RECEIPT TITLE','receipt content', function() {
            //console.log('WORKED FINE!');
          }, function(err) {
            //console.log('Uh oh... ' + err);
          });
```



