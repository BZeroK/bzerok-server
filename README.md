# Liquor Review System API Server (bzerok-server)

## Structure

```
bzerok-server
  ├─ gradle
  ├─ src
  │   ├─ main
  │   │   ├─ java
  │   │   │   └─ com.bzerok.server
  │   │   │       ├─ config
  │   │   │       │   ├─ security
  │   │   │       │   │   ├─ [jwt](https://www.notion.so/JWT-e7ca0119d139497e8aed3beef243ca40)
  │   │   │       │   │   ├─ [oauth2](https://www.notion.so/OAuth-2-0-d092623e5dd4449f9ef3506a56ad17b4)
  │   │   │       │   │   └─ [SecurityConfig.java](https://www.notion.so/SecurityConfig-java-a601bfcbe62f4128adb6b80064a5d62c)
  │   │   │       │   └─ [WebConfig.java](https://www.notion.so/WebConfig-java-5d3edcc031ee401e93c65c594f4c7bac)
  │   │   │       ├─ domain
  │   │   │       │   ├─ liquor
  │   │   │       │   └─ user
  │   │   │       ├─ service
  │   │   │       │   ├─ luquor
  │   │   │       │   └─ user
  │   │   │       └─ web
  │   │   │           ├─ dto
  │   │   │           └─ [LiquorPostController.java](https://www.notion.so/LiquorPostController-6fd744069c464f149d419fba25f3bbaf)
  │   │   └─ resources
  │   │       ├─ [application.yml](https://www.notion.so/application-yml-baf8a0359ba847df976bf5378255419b)
  │   │       └─ log4j2.yml
  │   └─ [test](https://www.notion.so/Test-76fcf321712c4a86a58c5d1f89cb00a5)
  │       ├─ java
  │       │   └─ com.bzerok.server
  │       │       ├─ domain
  │       │       │   ├─ liqour
  │       │       │   └─ user
  │       │       ├─ security
  │       │       │   └─ SecurityTest.java
  │       │       └─ web
  │       │           └─ LiquorPostControllerTest.java
  │       └─ resources
  ├─ .gitignore
  ├─ build.gradle
  ├─ gradlew
  ├─ gradlew.bat
  └─ settings.gradle
```

## Project Specification

### Spring Boot

[Spring Boot](https://www.notion.so/Spring-Boot-f1fcad0aec3c4757bc4766b605a0c272)

### Database

[Database](https://www.notion.so/Database-24d9b622b69f4371b67594f32f3711ee)

### Config

[SecurityConfig.java](https://www.notion.so/SecurityConfig-java-a601bfcbe62f4128adb6b80064a5d62c)

[WebConfig.java](https://www.notion.so/WebConfig-java-5d3edcc031ee401e93c65c594f4c7bac)

### Resources

[application.yml](https://www.notion.so/application-yml-baf8a0359ba847df976bf5378255419b)

### Test

[Test](https://www.notion.so/Test-76fcf321712c4a86a58c5d1f89cb00a5)

## Deployment

[Deployment](https://www.notion.so/Deployment-235a3c7d28db428c81dc343251e3db4c)

## Feature Development

### APIs

[LiquorPostController](https://www.notion.so/LiquorPostController-6fd744069c464f149d419fba25f3bbaf)

### Login

[OAuth 2.0](https://www.notion.so/OAuth-2-0-d092623e5dd4449f9ef3506a56ad17b4)

[JWT](https://www.notion.so/JWT-e7ca0119d139497e8aed3beef243ca40)

## 더 자세한 내용은 아래 주소를 참조해주세요.
https://husky-soda-a2f.notion.site/BZeroK-Project-604b6ea15f4c49e9ba7b0833f8fb2870
