# 최면 어플 (hypnosis)

촌스러운 무지개 메인 화면과 울렁이는 나선 화면으로 만든 장난용 Android 앱입니다.
"최면 실행" 버튼을 누르면 회전하며 커졌다 작아지는 나선이 전체 화면에 나오고, 화면을 터치하면 메인 화면으로 돌아옵니다.
실제 최면이나 치료 효과를 주는 앱이 아닙니다.

## 기능

- **메인 화면** (`MainActivity`): 쨍한 원색 무지개 대각선 그라데이션 배경, 테두리 글씨 제목(`OutlineTextView`), 계속 흔들리고 커졌다 작아지는 제목과 "최면 실행" 버튼.
- **최면 화면** (`HypnosisActivity`): 나선 이미지를 6초에 한 바퀴 회전시키면서 1.8초 주기로 1.0~1.25배 확대·축소합니다. 전체 화면(몰입 모드)이며 화면이 꺼지지 않습니다. 화면 어디든 터치해서 손을 떼면 종료합니다.
- 화면이 계속 회전·확대되므로 어지러움을 느끼거나 불편하면 바로 터치해서 종료하세요.
- 메인 화면 하단에 광고용 빈 영역(`adContainer`)만 마련되어 있고, **광고 SDK는 아직 연동되어 있지 않습니다**. 최면 화면에는 실수 클릭 방지를 위해 광고를 넣지 않는다는 메모가 레이아웃에 있습니다.

## 기술 스택

- Kotlin 1.9.24, Android Gradle Plugin 8.5.2, Gradle 8.9 (wrapper 포함), JDK 17
- compileSdk / targetSdk 34, minSdk 24
- AndroidX·Compose 없이 `android.app.Activity` + XML 레이아웃 + `ObjectAnimator`만 사용 (외부 라이브러리 의존성 없음)
- 패키지명(`applicationId`): `com.example.hypnosis` (임시 값)

## 빌드 방법

```bash
./gradlew assembleDebug
# 결과물: app/build/outputs/apk/debug/app-debug.apk
```

Android Studio에서 열어 실행해도 됩니다. Android SDK 경로는 `local.properties`(커밋 대상 아님) 또는 `ANDROID_HOME`로 지정합니다.
GitHub Actions(`.github/workflows/build.yml`)가 push마다 디버그 APK를 빌드해 `hypnosis-debug-apk` 아티팩트로 올립니다.

## 설정·비밀값

서명 키스토어, API 키, 광고 ID 등 비밀값은 사용하지 않으며 저장소에도 없습니다. 릴리스 서명 설정은 아직 없습니다(디버그 빌드만 구성).

## 프로젝트 구조

```
app/src/main/java/com/example/hypnosis/
  MainActivity.kt       메인 화면 (그라데이션 배경, 흔들리는 제목/버튼)
  HypnosisActivity.kt   나선 회전·확대 화면, 터치 시 종료
  OutlineTextView.kt    테두리 글씨 TextView
app/src/main/res/       레이아웃, 나선·아이콘 이미지, 문자열
.github/workflows/      디버그 APK 빌드 CI
```

## 상태와 한계

- 초기 프로토타입 수준입니다. 테스트 코드는 없습니다.
- `applicationId`가 `com.example.*` 임시 값이고 릴리스 서명·광고 연동이 없어 스토어 배포 준비는 되어 있지 않습니다.
