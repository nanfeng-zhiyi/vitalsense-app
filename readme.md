# VitalSense 健康监测应用

## 项目概述
VitalSense是一款基于Kotlin和Jetpack Compose开发的Android健康监测应用。该应用提供全面的健康数据监测和分析功能，采用现代化UI设计，为用户提供流畅直观的使用体验。

## 主要功能
- **健康数据监测**：心率、血压、血氧等生命体征实时监测
- **呼吸率检测**：监测用户的呼吸频率，评估呼吸系统健康
- **疾病筛查**：基于用户健康数据进行初步疾病风险评估
- **健康报告**：生成日报、周报、月报和年度健康报告，追踪健康状况变化
- **健康问答**：专业健康问题咨询和回答服务
- **健康专区**：提供分类健康文章、健康服务导航和健康知识
- **健康中心**：集中管理所有健康相关信息和数据
- **个人中心**：用户资料和设置管理

## 技术特点详解

### 现代化UI设计
- **Material 3设计语言**：采用Google最新的设计规范，提供现代感强烈的视觉体验
- **动态主题**：支持根据用户系统设置自动切换明/暗模式
- **响应式布局**：自适应不同尺寸的屏幕，从手机到平板电脑均可完美显示
- **流畅动画过渡**：页面切换和内容更新均采用平滑动画，提升用户体验
- **实时数据可视化**：采用交互式图表库，展示直观、美观的健康数据图表

### 高级导航系统
- **多级导航结构**：主导航(底部导航栏)与子导航(页面内标签)相结合，方便访问深层功能
- **页面过渡动画**：定制的页面切换动画，使用户在不同功能间切换时保持方向感
- **深度链接支持**：支持通过通知、小组件等直接跳转到应用特定页面
- **回退导航逻辑**：智能化的返回操作处理，确保用户不会迷失在多级页面中

### 性能优化
- **懒加载机制**：页面和重型组件采用懒加载策略，减少初始加载时间
- **高效列表渲染**：使用Compose的LazyColumn和LazyRow高效处理长列表数据
- **数据缓存策略**：减少网络请求，加快数据加载速度
- **后台处理优化**：耗时操作放在协程中执行，避免阻塞主线程
- **低功耗运行模式**：针对长期监测场景优化电池使用效率

### 数据安全与隐私
- **端到端加密**：敏感健康数据采用强加密算法保护
- **安全存储**：利用Android安全存储机制保护用户凭据
- **隐私控制中心**：用户可精确控制应用的数据访问权限
- **合规性设计**：符合健康数据相关法规和隐私保护要求

## 完整项目结构

```
app/src/main/
├── java/com/example/vitalsense/
│   ├── components/         # 可复用UI组件
│   │   ├── BottomNavigationBar.kt     # 底部导航栏组件
│   │   ├── HealthCard.kt              # 健康数据卡片组件
│   │   ├── LineChart.kt               # 折线图表组件
│   │   ├── MetricProgressBar.kt       # 健康指标进度条
│   │   ├── NotificationItem.kt        # 通知项组件
│   │   └── ProfileHeader.kt           # 个人资料头部组件
│   ├── data/               # 数据层
│   │   ├── models/                    # 数据模型
│   │   ├── repositories/             # 数据仓库
│   │   └── local/                    # 本地数据源
│   ├── di/                 # 依赖注入
│   ├── navigation/         # 导航配置
│   │   ├── AppNavigation.kt          # 主导航配置
│   │   └── AppScreen.kt              # 屏幕路由定义
│   ├── screens/            # 页面
│   │   ├── DashboardScreen.kt        # 仪表盘主页
│   │   ├── HealthScreen.kt           # 健康监测页
│   │   ├── HeartRateScreen.kt        # 心率监测
│   │   ├── BloodPressureScreen.kt    # 血压监测
│   │   ├── BloodOxygenScreen.kt      # 血氧监测
│   │   ├── DiseaseScreeningScreen.kt # 疾病筛查
│   │   ├── ReportsScreen.kt          # 健康报告
│   │   ├── QaScreen.kt               # AI问答
│   │   └── MineScreen.kt             # 个人中心
│   ├── ui/                 # UI定义
│   │   ├── theme/                    # 主题设置
│   │   │   ├── Color.kt              # 颜色定义
│   │   │   ├── Shape.kt              # 形状定义
│   │   │   └── Theme.kt              # 主题定义
│   │   └── composables/              # 通用组合函数
│   ├── utils/              # 工具类
│   │   ├── DateUtils.kt              # 日期处理工具
│   │   ├── HealthCalculator.kt       # 健康指标计算
│   │   └── PermissionUtils.kt        # 权限管理工具
│   ├── viewmodels/         # 视图模型
│   │   ├── DashboardViewModel.kt     # 仪表盘视图模型
│   │   ├── HealthViewModel.kt        # 健康监测视图模型
│   │   └── UserViewModel.kt          # 用户数据视图模型
│   ├── navigation/           # 应用导航组件
│   │   ├── AppNavigation.kt          # 应用导航实现
│   │   └── Screen.kt                 # 屏幕路由定义
│   ├── screens/              # 应用屏幕
│   │   ├── DashboardScreen.kt        # 仪表盘主屏幕
│   │   ├── HeartRateScreen.kt        # 心率监测屏幕
│   │   ├── BloodPressureScreen.kt    # 血压监测屏幕
│   │   ├── BloodOxygenScreen.kt      # 血氧监测屏幕
│   │   ├── RespiratoryRateScreen.kt  # 呼吸率监测屏幕
│   │   ├── DiseaseScreeningScreen.kt # 疾病筛查屏幕
│   │   ├── HealthStatsScreen.kt      # 健康统计屏幕
│   │   ├── QAScreen.kt               # 健康问答屏幕
│   │   ├── ReportsScreen.kt          # 健康报告屏幕
│   │   ├── HealthScreen.kt           # 健康专区屏幕
│   │   ├── MineScreen.kt             # 个人中心屏幕
│   │   └── SettingsScreen.kt         # 设置屏幕
│   └── MainActivity.kt     # 应用程序入口
├── res/                    # 资源文件
└── AndroidManifest.xml     # 应用配置
```

## 技术栈详情

### 开发语言与框架
- **Kotlin 1.7+**：现代、安全、简洁的JVM编程语言
- **Jetpack Compose**：Android声明式UI框架，简化UI开发
- **Coroutines & Flow**：处理异步操作和响应式数据流
- **Compose Navigation**：处理应用内导航和页面切换

### 架构组件
- **ViewModel**：管理UI相关数据，在配置更改时保存数据
- **Room**：简化SQLite数据库操作的持久性库
- **DataStore**：现代化的数据存储解决方案，替代SharedPreferences
- **WorkManager**：处理可延迟的后台任务

### 数据可视化
- **Compose Charts**：基于Compose的图表库，用于健康数据可视化
- **Animation API**：实现平滑的数据更新和过渡动画

### 依赖注入
- **Hilt**：简化Dagger依赖注入的使用

### 测试框架
- **JUnit**：单元测试
- **Compose Testing**：UI组件测试
- **Mockito**：模拟对象测试

## 注意事项
当前app并未接入模型，后续会考虑本地进一步优化相关性能考虑端侧部署相关预测算法模型
对于AI大模型应用考虑云端部署
