<h1>TokenManager</h1> 

[![](https://jitpack.io/v/Realizedd/TokenManager.svg)](https://jitpack.io/#Realizedd/TokenManager)

A simple economy plugin for spigot. <a href="https://www.spigotmc.org/resources/tokenmanager.8610/">Spigot Project Page</a>

**This fork is made to be compatible with Minecraft 1.20 and up, versions 1.19 and below are not being supported!**

---

* **[Wiki](https://github.com/Realizedd/TokenManager/wiki)**
* **[Commands](https://github.com/Realizedd/TokenManager/wiki/commands)**
* **[Permissions](https://github.com/Realizedd/TokenManager/wiki/permissions)**
* **[config.yml](https://github.com/Realizedd/TokenManager/blob/master/src/main/resources/config.yml)**
* **[lang.yml](https://github.com/Realizedd/TokenManager/blob/master/src/main/resources/lang.yml)**
* **[shops.yml](https://github.com/Realizedd/TokenManager/blob/master/src/main/resources/shops.yml)**
* **[Support Discord](https://discord.gg/RNy45sg)**


### Getting the dependency

#### Repository
Gradle:
```groovy
maven {
    name 'jitpack-repo'
    url 'https://jitpack.io'
}
```

Maven:
```xml
<repository>
  <id>jitpack-repo</id>
  <url>https://jitpack.io</url>
</repository>
```

#### Dependency
Gradle:
```groovy
compile (group: 'com.github.OverCrave', name: 'TokenManager', version: '3.5.0') {
    transitive = false
}
```  

Maven:
```xml
<dependency>
    <groupId>com.github.OverCrave</groupId>
    <artifactId>TokenManager</artifactId>
    <version>3.5.0</version>
    <exclusions>
        <exclusion>
            <groupId>*</groupId>
            <artifactId>*</artifactId>
        </exclusion>
    </exclusions>
</dependency>
```

### plugin.yml
Add TokenManager as a soft-depend to ensure TokenManager is fully loaded before your plugin.
```yaml
soft-depend: [TokenManager]
```

### Getting the API instance

```java
@Override
public void onEnable() {
  TokenManager api = (TokenManager) Bukkit.getServer().getPluginManager().getPlugin("TokenManager");
}
```
