# MinigameLib

This is a Java library to create Minecraft minigames in Bukkit

### Create a minigame
You need:
* In the plugin.yml:
```yaml
softdepends: Vault

commands:
  map:
    permission: minigamelib.map
  start:
    permission: minigamelib.start

permissions:
  minigamelib.*:
    children:
      minigamelib.map: true
      minigamelib.start: true
  minigamelib.map:
    default: true
  minigamelib.start:
    default: op
  minigame.*:
    default: op
    children:
      minigamelib.*: true
      yourminigame.perm1: true
```
* As Main class
```java
public class Main extends Minigame {

    @Override
    public void preInitialisation() {
        // This Code will execute at PreInitialisation
    }

    @Override
    public GameConfig initialisation() {
        // This Code will execute at Initialisation

        // You must return a GameConfig
        GameConfig config = new GameConfig(...);
        return config;
    }

    @Override
    public void postInitialisation() {
        // This Code will execute at PostInitialisation
    }

    @Override
    public void onStop() {
        // This Code will execute at Stop
    }
}
```

