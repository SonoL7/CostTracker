# 萌宠小窝（每日成本）

[![Kotlin](https://img.shields.io/badge/Kotlin-2.1.10-%23A97BFF?logo=kotlin)](https://kotlinlang.org)
[![Compose](https://img.shields.io/badge/Jetpack%20Compose-BOM%202025.04-%234285F4?logo=android)](https://developer.android.com/compose)
[![Room](https://img.shields.io/badge/Room-2.7.0-%234285F4?logo=android)](https://developer.android.com/training/data-storage/room)
[![Hilt](https://img.shields.io/badge/Hilt-2.52-%234285F4?logo=android)](https://dagger.dev/hilt)
[![minSdk](https://img.shields.io/badge/minSdk-26%20(Android%208.0)-%2334A853?logo=android)](https://developer.android.com)
[![targetSdk](https://img.shields.io/badge/targetSdk-36%20(Android%2016)-%2334A853?logo=android)](https://developer.android.com)

一款面向青少年的 Q 版萌宠风格 Android 记账 APP。记录每笔物品购买，自动计算已过天数与日均成本。

<p align="center">
  <img src="app/src/main/res/mipmap-xxxhdpi/ic_launcher.png" width="120" alt="icon"/>
</p>

---

## 功能

- **物品录入** — 记录物品名称、分类、购买日期、价格
- **日均成本** — 自动计算「已过天数」与「日均 ¥XX/天」，卡片直接显示核心指标
- **分类系统** — 22 个预设 Q 版萌宠分类图标，支持新增/删除自定义分类
- **自定义图标** — 每件物品可单独设定图标，优先级高于分类图标
- **网格布局** — 根据物品数量自动调节列数（2 → 3 → 4 → 5 列）
- **背景切换** — 内置多套 Q 版场景背景
- **成功动画** — 添加物品后弹出随机萌宠表情
- **本地存储** — Room 数据库，离线可用，无需网络

## 技术栈

| 层级 | 技术 |
|------|------|
| 语言 | Kotlin |
| UI 框架 | Jetpack Compose + Material 3 |
| 架构 | MVVM + Clean Architecture |
| 数据库 | Room (SQLite) |
| 依赖注入 | Hilt |
| 导航 | Navigation Compose |
| 构建 | Gradle 8.9 + AGP 8.7.3 + KSP |
| 最低版本 | Android 8.0 (API 26) |
| 目标版本 | Android 16 (API 36) |

## 项目结构

```
app/src/main/java/com/example/costtracker/
├── CostTrackerApp.kt              # @HiltAndroidApp
├── MainActivity.kt                # Single Activity
├── data/local/
│   ├── entity/                    # ItemEntity, CategoryEntity
│   ├── dao/                       # ItemDao, CategoryDao
│   ├── database/                  # CostDatabase
│   └── repository/                # ItemRepositoryImpl
├── domain/
│   ├── model/                     # ItemDisplay
│   ├── repository/                # ItemRepository (interface)
│   └── usecase/                   # AddItem, UpdateItem, DeleteItem, etc.
├── ui/
│   ├── navigation/                # AppNavGraph
│   ├── screen/list/               # ItemListScreen + ViewModel
│   ├── screen/add/                # AddItemScreen + ViewModel
│   ├── screen/edit/               # EditItemScreen + ViewModel
│   ├── component/                 # ItemCard, ItemForm, CategoryGrid, etc.
│   └── theme/                     # Color, Type, Theme
└── di/                            # AppModule (Hilt)
```

## 构建与运行

### 环境要求

- JDK 17+
- Android SDK Platform 36
- Android Build-Tools 36.0.0
- Gradle 8.9（通过 wrapper 自动下载）

### 编译

```bash
# Debug
./gradlew assembleDebug

# Release
./gradlew assembleRelease
```

### 安装

```bash
adb install app/build/outputs/apk/debug/app-debug.apk
```

> **注意**：Release APK 未签名，需使用 `apksigner` 签名后方可安装：
> ```bash
> apksigner sign --ks your.keystore \
>   --v1-signing-enabled true \
>   --v2-signing-enabled true \
>   --v3-signing-enabled true \
>   --out app-signed.apk \
>   app/build/outputs/apk/release/app-release-unsigned.apk
> ```

## 数据计算规则

| 字段 | 计算公式 | 说明 |
|------|---------|------|
| 已过天数 | `max(1, today - purchaseDate)` | 当天购买计为 1 天 |
| 日均成本 | `price ÷ daysPassed` | 四舍五入保留 2 位小数 |

## License

MIT

---

<p align="center">by San. (2026.05)</p>
