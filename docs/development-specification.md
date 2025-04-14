# 景区智能分析系统开发规范

## 一、项目结构规范

### 1. 目录结构
```
src/main/
├── java/com/scenic/ai/
│   ├── config/         # 配置类
│   ├── controller/     # 控制器
│   ├── service/       # 服务层
│   │   └── impl/     # 服务实现
│   ├── dao/          # 数据访问层
│   ├── model/        # 实体类
│   └── exception/    # 异常处理
├── resources/
│   ├── mapper/       # MyBatis映射文件
│   ├── spring/       # Spring配置文件
│   └── jdbc.properties # 数据库配置
└── webapp/
    └── WEB-INF/
        ├── jsp/      # JSP视图
        │   ├── layout/   # 布局模板
        │   ├── crowd/    # 人群统计相关页面
        │   ├── alert/    # 告警相关页面
        │   └── common/   # 公共组件
        ├── static/   # 静态资源
        │   ├── css/     # 样式文件
        │   ├── js/      # JavaScript文件
        │   └── img/     # 图片资源
        └── web.xml   # Web配置文件
```

### 2. 命名规范
- 包名：全小写，例如：`com.scenic.ai.controller`
- 类名：大驼峰，例如：`CrowdStatisticsController`
- 方法名：小驼峰，例如：`getStatisticsByDevice`
- 变量名：小驼峰，例如：`deviceCode`
- 常量名：全大写下划线分隔，例如：`MAX_RETRY_COUNT`
- JSP文件：小写中划线分隔，例如：`crowd-statistics.jsp`
- JavaScript文件：小写中划线分隔，例如：`crowd-statistics.js`

## 二、前端开发规范

### 1. 页面框架
- 采用JSP + Bootstrap + ECharts技术栈
- 使用固定的页面布局结构：
  - 左侧固定菜单（200px宽）
  - 顶部固定标题栏（60px高）
  - 右侧自适应内容区

### 2. 页面组件
```html
<!-- 标准筛选条件组件 -->
<div class="filter-section">
    <div class="row">
        <div class="col-md-3">
            <div class="form-group">
                <label>标签名</label>
                <input type="text" class="form-control">
            </div>
        </div>
    </div>
</div>

<!-- 数据表格组件 -->
<div class="data-table">
    <div class="table-header">
        <h5>标题</h5>
        <div class="table-actions">
            <button class="btn btn-primary">操作按钮</button>
        </div>
    </div>
    <table class="table">
        <!-- 表格内容 -->
    </table>
</div>

<!-- 图表容器组件 -->
<div class="chart-container">
    <div class="chart-header">
        <h5>图表标题</h5>
    </div>
    <div id="chartId" style="height: 400px;"></div>
</div>
```

### 3. JavaScript规范
- 使用模块化组织代码
- 统一使用jQuery进行DOM操作
- ECharts图表配置遵循统一风格
- 使用AJAX进行数据交互

## 三、后端开发规范

### 1. Controller层规范
```java
@Controller
@RequestMapping("/api/v1/crowd-statistics")
public class CrowdStatisticsController {
    
    @GetMapping("/{deviceCode}")
    public ResponseEntity<?> getByDevice(@PathVariable String deviceCode) {
        // 实现代码
    }
}
```

### 2. Service层规范
```java
public interface CrowdStatisticsService {
    List<CrowdStatistics> getByDevice(String deviceCode);
}

@Service
public class CrowdStatisticsServiceImpl implements CrowdStatisticsService {
    @Override
    public List<CrowdStatistics> getByDevice(String deviceCode) {
        // 实现代码
    }
}
```

### 3. DAO层规范
```java
@Mapper
public interface CrowdStatisticsMapper {
    List<CrowdStatistics> selectByDevice(@Param("deviceCode") String deviceCode);
}
```

## 四、API接口规范

### 1. 响应格式
```json
{
    "code": 200,
    "message": "success",
    "data": {
        // 具体数据
    }
}
```

### 2. 分页参数
- pageNum: 页码，从1开始
- pageSize: 每页大小
- total: 总记录数
- pages: 总页数

### 3. 时间参数
- 入参格式：yyyy-MM-dd HH:mm:ss
- 返回格式：yyyy-MM-dd HH:mm:ss
- 使用UTC时间存储

## 五、数据库规范

### 1. 表命名
- 使用大写字母
- 下划线分隔单词
- 例如：CROWD_STATISTICS

### 2. 字段命名
- 使用大写字母
- 下划线分隔单词
- 例如：DEVICE_CODE

### 3. 必备字段
- ID: 主键
- CREATE_TIME: 创建时间
- UPDATE_TIME: 更新时间

## 六、异常处理规范

### 1. 异常分类
- BusinessException: 业务异常
- SystemException: 系统异常
- ValidationException: 参数验证异常

### 2. 异常处理方式
```java
@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBusinessException(BusinessException e) {
        // 处理业务异常
    }
}
```

## 七、日志规范

### 1. 日志级别
- ERROR: 系统错误
- WARN: 警告信息
- INFO: 一般信息
- DEBUG: 调试信息

### 2. 日志格式
```
时间戳 [线程名] 日志级别 类名 - 具体信息
```

## 八、注释规范

### 1. 类注释
```java
/**
 * 类的功能描述
 * 
 * @author 作者
 * @date 创建日期
 */
```

### 2. 方法注释
```java
/**
 * 方法的功能描述
 * 
 * @param 参数名 参数说明
 * @return 返回值说明
 * @throws 异常类型 异常说明
 */
```

## 九、版本控制规范

### 1. 分支管理
- master: 主分支
- develop: 开发分支
- feature/*: 功能分支
- hotfix/*: 紧急修复分支

### 2. 提交信息
```
类型(范围): 描述

- 类型: feat/fix/docs/style/refactor/test/chore
- 范围: 可选，表示修改的范围
- 描述: 简明扼要的修改说明
``` 