## Project Structure
**Multi Modules Single Project**
```
root (com.example)
├── build.gradle
└── settings.gradle
└── buildSrc
│   ├── build.gradle
│   └── src
│       └── main
│           └── kotlin
│               └── Dependencies.kt
│               └── Plugins.kt
│               └── Versions.kt
└── module-api
│   ├── build.gradle
│   └── src
│       └── main
│           └── java
│               └── com
│                   └── example
│                       └── moduleapi
│                           └── ApiApplication.kt (Spring Boot Application)
└── module-common
│   ├── build.gradle
│   └── src
│       └── main
│           └── java
│               └── com
│                   └── example
│                       └── modulecommon
└── module-domain
    ├── build.gradle
    └── src
        └── main
            └── java
                └── com
                    └── example
                        └── moduledomain
```

<br>

## Code Formatting and Linting
```bash
./gradlew ktlintCheck  // 로 틀린부분을 체크받고
./gradlew ktlintFormat  // 로 자동수정을 할 수 있습니다.
```

<br>

ktlintcheck를 수동으로 수행하는 것이 귀찮다면 다음 명령으로 커밋할때마다 `ktlintcheck`가 실행되게 할 수 있습니다.
```bash
./gradlew addKtlintCheckGitPreCommitHook
```

<br>

## Build the Project
```bash
./gradlew :module-api:build
```

<br>

## Modules
- **module-api**: This moudle is reponsible for the API Server.
- **module-domain**: This module is responsible for the domain logic
- **module-common**: This moudle is responsible for common utilities and libraries.

<br>

## Dependency Flow
```lua
+----------------+
|  module-api    |
|                |
+----------------+
        ^
        |
+-------------------+
|  module-domain   |
|                  |
+-------------------+
        ^
        |
+----------------+
| module-common  |
|                |
+----------------+
```
