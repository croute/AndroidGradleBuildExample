AndroidGradleBuildExample
=========================

This project is example for using muliple build type.

* Related post http://novafactory.net/archives/3143

## Generate Keystore ##

Using keytool, create a `keystore`.

```
$ keytool -genkey -v -keystore test.keystore -alias nova -keyalg RSA -keysize 2048 -validity 10000
```

## Signing configuration ##

Add `signingConfig` and `signingConfig.release` to android block.

```
android {
    ...
    signingConfigs {
        release {
            storeFile file("./keystore/test.keystore")
            storePassword "testtest"
            keyAlias "nova"
            keyPassword "testtest"
        }
    }
    ...
}
```

## Changing APK file name ##

Using `applicationVariants.all`, change output file name.

```
import java.text.SimpleDateFormat
 
apply plugin: 'com.android.application' 
... 
android {
    ...
    applicationVariants.all { variant ->
        def df = new SimpleDateFormat("yyMMdd")
        df.setTimeZone(TimeZone.getDefault())
        def file = variant.outputFile
        def newName = "${df.format(new Date())}_BuildTypesTestApp_{defaultConfig.versionName}(${defaultConfig.versionCode})"
        newName += "_${variant.buildType.name}.apk"
        variant.outputFile = new File(file.parent, newName);
    }
    ...
}
```

## Multiple build types ##

```java
package setting;
 
public enum DeployPhase {
    Debug(1),
    Stage(2),
    Release(3);
 
    int value;
 
    DeployPhase(int value) {
        this.value = value;
    }
 
    public static DeployPhase findByValue(int value) {
        switch (value) {
            case 1:
                return Debug;
            case 2:
                return Stage;
            case 3:
                return Release;
            default:
                return null;
        }
    }
}
```


```
buildConfigField "<type>", "<name>", "<value>"
```

Add debug, stage and release to `buildTypes` block. Then add `buildConfigField` code to each build types.


```
android {
    ...
    buildTypes {
        debug {
            buildConfigField "setting.DeployPhase", "DEPLOY_PHASE", "setting.DeployPhase.Debug"
        }
        stage {
            buildConfigField "setting.DeployPhase", "DEPLOY_PHASE", "setting.DeployPhase.Stage"
        }
        release {
            buildConfigField "setting.DeployPhase", "DEPLOY_PHASE", "setting.DeployPhase.Release"
            runProguard false
            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
            signingConfig signingConfigs.release
        }
    }
    ...
}
```

### Usage BuildConfig.DEPLOY_PHASE ###

```java
package net.novafactory.example.gradle.buildtypes;
 
/**
 * @author nova
 * @since 2014. 8. 5.
 */
public class Setting {
 
    public static final String SERVER_URL = initServerUrl();
 
    private static String initServerUrl() {
        switch (BuildConfig.DEPLOY_PHASE) {
            case Debug:
                return "http://debug-server-url";
            case Stage:
                return "http://stage-server-url";
            case Release:
                return "http://release-server-url";
            default:
        }
 
        return null;
    }
}
```

