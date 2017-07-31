# Android开发框架（封装中）
### 封装一个基于 MVP + RxJava2 + Retrofit2 的Android开发框架 — AndroidBase
#### 1. 创建common模块，用于封装Retrofit2网络请求，通用utils，以及一些BaseActivity、BaseFragment等。
#### 2. 整理统一gradle依赖，创建config.grade配置通用依赖，维护相关版本号。
#### 3. 封装BaseActivity、BaseFragment以及Toolbar，方便以后页面快速统一编写。
#### 4. 封装Retrofit2网络访问，添加拦截器、请求header、日志开关。
#### 5. 网络请求基于RxJava2进行变更，并添加Rxlifecycle，防止内存泄露。
#### 6. 整体架构基于MVP模式进行构建，创建BaseView，BaseModel，BasePresenter等。
#### 7. 基础框架搭建完成之后，使用目前普洱的相关接口进行测试，并对相关细节进行优化。
#### 由于RxJava2和MVP知识比较薄弱，尚处于边学边实践的阶段，并且借鉴github相关开源项目，初步预估框架搭建时间为一个月。
