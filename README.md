# DeepLAPI
[![Documentation](https://img.shields.io/github/downloads/entonoire/deeplapi/total)](https://github.com/entonoire/DeepLAPI/releases/download/1.0/DeepLAPI.jar)

A Java api for deepl (simple to use i think)
## Usage
```java
Deepl api = new Deepl("key");

String[] request = api.translate("text", Deepl.Lang.FR);
System.out.println(request[0]); // tranlated text
System.out.println(request[1]); // code --> 1: pass ; 0: fail ; 2: exception
```
### Result
```
texte
1
```
