

# HAGT MVC 框架

一个轻量级的 Java Web MVC 框架，基于 Servlet Filter 实现，类似于 Spring MVC 的简化版本。

## 简介

HAGT 是一个轻量级 MVC（Model-View-Controller）框架，它简化了 Java Web 应用的开发。通过注解驱动的方式，开发者可以快速构建 RESTful 风格的 Web 服务。

## 软件架构

### 核心组件

- **Hagt**: 框架入口类，负责初始化和启动
- **HagtFilter**: Servlet 过滤器，拦截所有请求并分发给框架处理
- **Handing**: 请求处理器抽象类，支持责任链模式扩展
- **Mapping**: 映射接口，用于管理 URL 与处理方法的映射关系
- **MvcConfigLoad**: 配置加载器，扫描包并初始化控制器和方法映射

### 注解系统

| 注解 | 作用 |
|------|------|
| `@Controller` | 标记控制器类 |
| `@MappingFunction` | 标记请求处理方法 |
| `@RequestParam` | 获取请求参数 |
| `@RequestBody` | 获取请求体中的 JSON 数据 |
| `@RequestFile` | 获取上传文件 |
| `@Scope` | 设置控制器作用域（单例/多例） |

### 请求处理流程

1. `HagtFilter` 拦截请求
2. `MvcConfigLoad` 扫描并加载控制器
3. `DefaultMapping` 根据 URL 找到对应的处理方法
4. `DefaultHanding` 执行方法调用并处理参数
5. 返回结果渲染到响应

## 安装教程

### 环境要求

- JDK 1.8+
- Maven 3.x

### Maven 依赖

```xml
<dependency>
    <groupId>com.hagt</groupId>
    <artifactId>hagt</artifactId>
    <version>1.0.0</version>
</dependency>
```

### 部署到本地仓库

```bash
mvn clean install
```

## 使用说明

### 1. 创建控制器

```java
@Controller(baseUrl = "api")
public class UserController {
    
    @MappingFunction(url = "/user/{id}")
    public User getUser(@RequestParam("id") String id) {
        return new User("张三", 25);
    }
    
    @MappingFunction(url = "/users", method = RequestType.POST)
    public Map<String, Object> createUser(@RequestBody("data") User user) {
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("data", user);
        return result;
    }
    
    @MappingFunction(url = "/upload", method = RequestType.POST)
    public String upload(@RequestFile("file") byte[] file) {
        // 处理文件上传
        return "上传成功";
    }
}
```

### 2. 配置 web.xml

```xml
<web-app>
    <filter>
        <filter-name>hagt</filter-name>
        <filter-class>com.hagt.core.HagtFilter</filter-class>
        <init-param>
            <param-name>scanPackage</param-name>
            <param-value>com.example.controller</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>hagt</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
</web-app>
```

### 3. 参数获取方式

| 注解 | 用途 | 示例 |
|------|------|------|
| `@RequestParam` | 获取 URL 参数或表单参数 | `@RequestParam("name") String name` |
| `@RequestBody` | 获取 JSON 请求体 | `@RequestBody("user.name") User user` |
| `@RequestFile` | 获取上传文件 | `@RequestFile("avatar") byte[] file` |
| `HttpServletRequest` | 原始请求对象 | 直接注入使用 |
| `HttpServletResponse` | 原始响应对象 | 直接注入使用 |

### 4. 响应结果

框架自动将返回对象转换为 JSON 响应：

```java
@MappingFunction(url = "/hello")
public Map<String, String> hello() {
    Map<String, String> map = new HashMap<>();
    map.put("message", "Hello, HAGT!");
    return map;
}
```

## 示例项目

项目包含一个测试控制器 `TestController`，演示了各种用法：

- 多值参数获取
- 文件上传
- JSON 数据绑定
- 自定义处理器扩展

## 参与贡献

1. Fork 本仓库
2. 新建 Feat_xxx 分支
3. 提交代码
4. 新建 Pull Request

## 扩展机制

### 自定义请求处理器

继承 `Handing` 类实现自定义请求处理逻辑：

```java
@Scope(ControllerScope.PROTOTYPE)
public class AuthHandler extends Handing {
    @Override
    public void handing(String requestPath, HttpServletRequest req, 
                       HttpServletResponse res, FilterChain chain) {
        // 权限验证逻辑
        if (nextHanding != null) {
            nextHanding.handing(requestPath, req, res, chain);
        }
    }
}
```

## 许可证

本项目采用 MIT 许可证。