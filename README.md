# MyProject
This is Description
专业词汇在线翻译系统(javaEE项目)

开发工具 itelij 
环境 jdk8, mysql5.4
简单MVC设计

金山翻译, 百度翻译, 有道翻译, 以及专业词汇翻译(数据库)

用户: 

    管理员:
        1.添加新的专业
        2.管理对应用户所属专业的待审核单词
        
    超级管理员:
        1.专业管理
        2.用户/管理员 管理
        3.词汇管理
        
    普通用户:
        添加单词，添加单词超过一定数量(可在Const接口中设置), 可称为该用户所属专业的管理员
        
    访客:
        查询单词
        
        
数据库:
    
    默认超级管理员
    
        id:1111
        name:admin
        password:admin123456
        
    默认单词:计算机专业: 1756个
