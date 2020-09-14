package com.minio.es.common.status;

/**
 * @author chenlj
 */
public enum ResponseStatus {
    COMMON_OK(0, "成功"),
    COMMON_FAIL(1, "失败"),
    COMMON_ARGS_WRONG(2, "缺失关键参数"),
    COMMON_NAME_WRONG(3, "名称重复不可用"),
    COMMON_NAME_JUDGE(4, "名称已存在，请尝试其他名称"),
    COMMON_IO(5, "系统IO操作异常"),
    COMMON_DB_ERR(6, "数据库异常");
    private final int value;
    private final String reasonPhrase;

    private ResponseStatus(int value, String reasonPhrase) {
        this.value = value;
        this.reasonPhrase = reasonPhrase;
    }

    public int value() {
        return this.value;
    }

    public String getReasonPhrase() {
        return this.reasonPhrase;
    }

    public enum Development {

        DEVELOPMENT_TASK_WAITING(5001, "该任务处于待审核状态"),
        DEVELOPMENT_HDFS_ERROR(5002, "执行hdfs错误"),
        DEVELOPMENT_FUNCTION_ERROR(5003, "添加函数中间表维护失败"),
        DEVELOPMENT_FUNCTION_DROP_ERROR(5004, "HIVE删除函数失败"),
        DEVELOPMENT_TASK_CATE_ERROR(5005, "任务目录非空无法删除"),
        DEVELOPMENT_TASK_FIND_ERROR(5006, "未找到相关工作流"),
        DEVELOPMENT_TASK_XML_ERROR(5007, "XML解析错误"),
        DEVELOPMENT_TASK_RUNTIME_ERROR(5008, "请确认任务是否已删除"),
        DEVELOPMENT_TASK_RUNTIME_IO_ERROR(5009, "任务测试运行时生成日志IO操作异常"),
        DEVELOPMENT_WORKFLOW_PUB_WRONG(5010, "请确认已勾选需发布的工作流"),
        DEVELOPMENT_WORKFLOW_PUB_NOTFULL(5011, "所勾选的待发布流程不完整"),
        DEVELOPMENT_WORKFLOW_PUB_TASKERROR(5012, "勾选任务发布状态更新失败"),
        DEVELOPMENT_TASK_RUN_THREADERROR(5013, "任务被杀死"),
        DEVELOPMENT_HIVE_TABLE_MANAGER_ERROR(5014, "执行hive操作失败"),
        DEVELOPMENT_LOG_WEB_SOCKET_ERROR(5015, "websocket异常"),
        DEVELOPMENT_RESOURCE_DEP_LOSE(5016, "资源依赖丢失"),
        DEVELOPMENT_TASK_PUBED(5017, "任务已发布，不能修改名称"),
        DEVELOPMENT_DATASOURCE_DRIVER(5018, "加载表数据时驱动丢失"),
        DEVELOPMENT_FUNC_CATE_ERROR(5020, "函数目录非空无法删除"),
        DEVELOPMENT_RES_CATE_ERROR(5021, "资源目录非空无法删除"),
        DEVELOPMENT_WORKFLOW_PUBD(5022, "工作流已发布无法操作"),
        DEVELOPMENT_WORKFLOW_PUB_EMPTY(5023, "发布包下无工作流，请确认参数"),
        DEVELOPMENT_WORKFLOW_PD_EMPTY(5024, "发布包下发布流程图为空，请确认参数"),
        DEVELOPMENT_TASK_PUB_CKD(5025, "当前任务有处于待审核状态的任务无法重复提交"),
        DEVELOPMENT_TASK_PUB_CKD2(5034, "当前任务处于待发布状态或审核状态不能删除"),
        DEVELOPMENT_TASK_PUB_CKD3(5036, "当前任务存在于流程图中无法操作"),
        DEVELOPMENT_WORKFLOW_PUB_WRONGGRAM(5026, "发布工作流流程图错误"),
        DEVELOPMENT_FUN_HIVE_ERROR(5027, "创建函数失败，存在语法错误"),
        DEVELOPMENT_TABLE_MANAGER_SQL_ERROR(5028, "sql语句语法错误"),
        DEVELOPMENT_SHELL_EXE_ERROR(5029, "远程shell执行错误"),
        DEVELOPMENT_YARN_ERROR(5030, "连接yarn报错"),
        DEVELOPMENT_TABLE_MANAGER_ERROR(5031, "表管理异常"),
        DEVELOPMENT_TASK_EXE_ERROR(5032, "运行任务超时退出"),
        DEVELOPMENT_WORKFLOW_PUBDERROR(5033, "工作流未发布无法操作"),
        DEVELOPMENT_WORKFLOW_CREATED_ERR(5035, "非空白工作流根目录下无法创建任务"),
        DEVELOPMENT_TASK_CATE_ERR(5037, "非空白工作流根目录下无法创建任务目录"),
        DEVELOPMENT_FUN_RES_ERR(5038, "函数必须选择jar类资源进行依赖"),
        DEVELOPMENT_HIVE_NOPART_ERROR(5039, "该表不是分区表，无法展示分区"),
        DEVELOPMENT_DTX_ERR(5040, "数据同步任务不完整请检查数据源"),
        DEVELOPMENT_TB_QATTR(5041, "缺失参数配置"),
        DEVELOPMENT_TASK_RES_ERR(5042, "维护资源任务中间表错误"),
        DEVELOPMENT_REMOTE_PRO_ERR(5043, "请求远程数据项目id错误"),
        DEVELOPMENT_REMOTE_TASK_ERR(5044, "请求远程数据任务名称错误"),
        DEVELOPMENT_WORKFLOW_FUNCTION_SOURCE_WRONG(5045, "请确认勾选发布内容"),
        DEVELOPMENT_WORKFLOW_PUD_RES_ERR(5046, "发布任务维护资源中间关系失败"),
        //DEVELOPMENT_TASK_PUB_CKD4(5047, "当前任务存在于流程图中无法重命名"),
        DEVELOPMENT_TABLE_MANAGE_PATRERR(5048, "非分区表获取表分区错误"),
        DEVELOPMENT_RES_LOS(5049, "资源丢失，请联系管理员"),
        DEVELOPMENT_YARN_ERR(5050, "YARN服务器出错"),
        DEVELOPMENT_TASK_RUN_ERR(5051, "任务尚未进入资源管理器"),
        DEVELOPMENT_TASK_RUN_ERRO(5052, "任务未保存或提交无法运行"),
        DEVELOPMENT_TASK_MONITOR_ERR(5053, "维护监控任务中间表错误"),
        DEVELOPMENT_TASK_JSON_ERR(5054, "JSON格式化错误"),
        DEVELOPMENT_TASK_AI_ERR(5055, "远程调用失败"),
        DEVELOPMENT_TASK_ES_ERR(5056, "ES数据源调用失败");


        private final int value;
        private final String reasonPhrase;


        private Development(int value, String reasonPhrase) {
            this.value = value;
            this.reasonPhrase = reasonPhrase;
        }

        public int getValue() {
            return value;
        }

        public String getReasonPhrase() {
            return reasonPhrase;
        }
    }

    public enum DataSource {
        DATASOURCE_CONNOT_OPYION(4001, "该数据源不可操作"),
        DATASOURCE_CONNOT_FAIL(4002, "数据源连接失败"),
        DATASOURCE_PASSWORD_ERROR(4003, "密码转换异常"),
        DATASOURCE_UNMATCH_ERR(4004, "输入/输出源配置过时，请重新配置"),
        DATASOURCE_SEARCE_FAIL(4005, "数据查询失败"),
        DATASOUREC_SEARCH_BIG(4006, "数据量超过1000条,请使用分页查询"),
        DATASOUREC_SEARCH_HIVE(4007, "请访问即席查询api");

        private final int value;
        private final String reasonPhrase;

        private DataSource(int value, String reasonPhrase) {
            this.value = value;
            this.reasonPhrase = reasonPhrase;
        }

        public int getValue() {
            return value;
        }

        public String getReasonPhrase() {
            return reasonPhrase;
        }
    }

    public enum Usermanage {
        USER_HAVENOT_AUTH(2001, "权限不足"),
        USER_CANNOT_HANDLE(2002, "该数据不可操作"),
        PARAM_CANNOT_EMPTY(2003, "该数据不可操作");

        private final int value;
        private final String reasonPhrase;

        Usermanage(int value, String reasonPhrase) {
            this.value = value;
            this.reasonPhrase = reasonPhrase;
        }

        public int getValue() {
            return value;
        }

        public String getReasonPhrase() {
            return reasonPhrase;
        }
    }

    public enum Publisher {


        PUBLISHER_FILEERROR(60001, "文件创建失败，未找到发布的临时文件路径"),
        PUBLISHER_PROJECTNOFOUND(6002, "发布流程的项目未找到"),
        PUBLISHER_PACKAGEERROR(6003, "发布包数据存在异常，更新失败"),
        PUBLISHER_AZKABANLOGINERROR(6004, "azkaban登陆异常"),
        PUBLISHER_AZKABANERROR(6005, "azkaban接口异常"),
        PUBLISHER_AZKABANUNZIPERROR(6006, "azkaban上传失败"),
        PUBLISHER_AZKABANCONFIGERROR(6007, "读取配置文件的azkaban内容异常"),
        PUBLISHER_AZKABANPROJECTCERROR(6008, "azkaban创建项目失败"),
        PUBLISHER_AZKABANPROJECTDERROR(6009, "azkaban删除项目失败"),
        PUBLISHER_AZKABANSCHEDULEERROR(6010, "azkaban调度失败"),
        PUBLISHER_AZKABANSCHEDULCRONEERROR(6011, "调度数据异常"),
        PUBLISHER_AZKABANCRONERROR(6012, "cron表达式错误，调度日期内容异常"),
        PUBLISHER_AZKABANTASKDATAERROR(6013, "azkaba与平台任务数据不匹配"),
        PUBLISHER_AZKABANSQLERROR(6014, "azkaban查询失败"),
        PUBLISHER_AZKABANCONERROR(6015, "azkaban连接数据库失败"),
        PUBLISHER_PROJECTERROR(6016, "发布包中流程属于不同的项目，不能发布"),
        PUBLISHER_PROJECTPRIVLIEGEERROR(6017, "发布包没有权限上传azkaban，请通过审核上传"),
        PUBLISHER_CRAETEEERROR(6018, "发布包创建失败"),
        PUBLISHER_UPDATETASKERROR(6019, "发布包创建时更新任务失败"),
        PUBLISHER_WORKFLIWSCHEDULEUPDATEKERROR(6020, "发布包创建时更新流程调度表失败"),
        PUBLISHER_WORKFLIWSCHEDULECREATEERROR(6020, "发布包创建时创建流程调度表数据失败"),
        PUBLISHER_CRAETERELEASEWORKARTIEERROR(6021, "生成发布包关联流程表数据失败"),
        PUBLISHER_WORKFLOWTUERROR(6022, "流程图未创建"),
        PUBLISHER_TARGETTABLENOTFOUNDERROR(6023, "目的表不存在主键无法更新"),
        PUBLISHER_TARGETTABLEFOUNDERROR(6024, "目的表存在主键请选择更新操作"),
        PUBLISHER_COPYERROR(6025, "覆盖操作失败"),
        PUBLISHER_DIRELEASEPACKAGEITEMERROR(6026, "发布包关联表新增失败"),
        PUBLISHER_FLOWKILLERROR(6027, "azkaban流程已经结束"),
        PUBLISHER_FLOWLOSEERROR(6028, "azkaban流程已经过期"),
        PUBLISHER_SQLERROR(6029, "SQL异常"),
        PUBLISHER_RESOURCEERROR(6030, "当没有勾选任务时，资源不能单独新增，删除发布"),
        PUBLISHER_FUNCTIONERROR(6031, "当没有勾选任务时，函数不能单独新增，删除发布"),
        PUBLISHER_FUNCTIONWITHRESOURCE(6032, "当前项目找不到函数关联的资源"),
        PUBLISHER_RESOURCEJUDGE(6033, "发布前任务资源函数校验不通过"),
        PUBLISHER_CROSSPROJECTFUNCTIONWITHRESOURCE(6034, "跨项目找不到函数关联的资源"),
        PUBLISHER_NOTFINDHDFS(6035, "HDFS路径不存在或者没有权限操作");


        private final int value;
        private final String reasonPhrase;

        private Publisher(int value, String reasonPhrase) {
            this.value = value;
            this.reasonPhrase = reasonPhrase;
        }

        public int getValue() {
            return value;
        }

        public String getReasonPhrase() {
            return reasonPhrase;
        }
    }

    public enum Monitor {
        MONITOR_TASK_PARENT(7001, "当前节点无父节点"),
        MONITOR_TASK_PARENTALL(7002, "当前节点无其他父节点"),
        MONITOR_TASK_CHILDREN(7003, "当前节点无子节点"),
        MONITOR_TASK_CHILDRENALL(7002, "当前节点无其它子节点");
        private final int value;
        private final String reasonPhrase;

        Monitor(int value, String reasonPhrase) {
            this.value = value;
            this.reasonPhrase = reasonPhrase;
        }

        public int getValue() {
            return value;
        }

        public String getReasonPhrase() {
            return reasonPhrase;
        }
    }

    public enum Project {
        NO_RELEASE_TARGET(3001, "发布目标项目不存在"),
        PROJECT_CREATE_FAIL(3002, "项目创建失败"),
        MISMATCH_RELEASE_TARGET(3003, "选定的发布目标已绑定项目，请重新选择");

        private final int value;
        private final String reasonPhrase;


        private Project(int value, String reasonPhrase) {
            this.value = value;
            this.reasonPhrase = reasonPhrase;
        }

        public int getValue() {
            return value;
        }

        public String getReasonPhrase() {
            return reasonPhrase;
        }
    }

    public enum MetaDataQuatity {
        MDQ_PRO_ERR(8001, "目标项目丢失"),
        MDQ_DBTYPE_ERR(8002, "数据库类型错误"),
        MDQ_RULE_ERR(8003, "新建规则失败请重试"),
        MDQ_RULE_TASK_ERR(8004, "当前规则已和任务绑定无法删除，请先解除绑定关系"),
        MDQ_RULE_REF_ERR(8005, "反射创建对象异常"),
        MDQ_RULE_WD_ERR(8006, "计算工作日错误");
        private final int value;
        private final String reasonPhrase;


        private MetaDataQuatity(int value, String reasonPhrase) {
            this.value = value;
            this.reasonPhrase = reasonPhrase;
        }

        public int getValue() {
            return value;
        }

        public String getReasonPhrase() {
            return reasonPhrase;
        }
    }

    public enum RealTimeTask {

        RRT_CATE_NOT_NULL(9001, "目录非空无法删除"),
        RRT_TASK_ERR(9002, "当前任务已有实例处于待审核状态无法提交"),
        RRT_COM_ERR(9003, "类型转换错误"),
        RRT_CLS_ERR(9004, "找不到指定类型"),
        RRT_IO_ERR(9005, "IO异常"),
        RRT_UNSUPORT_FUN(9006, "不支持的函数类型"),
        RRT_LOCKED(9007, "任务状态被锁定无法启动"),
        RRT_LOG_ERR(9008, "获取实时任务日志失败"),
        RRT_DEL_ERR_COM(9009, "该实时任务处于提交状态，不允许删除"),
        RRT_DEL_ERR_PAG(9010, "该实时任务处于待审核状态，不允许删除"),
        RRT_DEL_ERR_RUN(9011, "该实时任务运行中，不允许删除");
        private final int value;
        private final String reasonPhrase;


        private RealTimeTask(int value, String reasonPhrase) {
            this.value = value;
            this.reasonPhrase = reasonPhrase;
        }

        public int getValue() {
            return value;
        }

        public String getReasonPhrase() {
            return reasonPhrase;
        }
    }

}
