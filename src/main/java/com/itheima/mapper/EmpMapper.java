package com.itheima.mapper;

import com.itheima.pojo.Emp;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.util.List;

@Mapper //标识出是mybaits的一个mapper接口，程序运行时，会自动生成该接口的实现类对象（代理对象），并且会将该对象交给IOC容器管理
public interface EmpMapper {
    //根据ID删除数据,mybatis提供的参数占位符#{}
    @Delete("delete from emp where id = #{id}")
    //实际有返回值，返回值代表此次操作影响的记录数
//    public int delete(Integer id);
    public void delete(Integer id);

    //新增员工
    //true，要获取返回的主键值，keyProperty返回的主键封装的位置
    @Options(useGeneratedKeys = true,keyProperty = "id")
    @Insert("insert into emp(username, name, gender, image, job, entrydate, dept_id, create_time, update_time) VALUES (#{username},#{name},#{gender},#{image},#{job},#{entrydate},#{deptId},#{createTime},#{updateTime})")
    public void insert(Emp emp);

    //更新员工

    @Update("update emp set username = #{username},name = #{name}, gender = #{gender}, image = #{image}," +
            "job = #{job},entrydate = #{entrydate},dept_id =#{deptId}, update_time = #{updateTime} where id = #{id}")
    public void update(Emp emp);

//    //根据ID查询员工 原始代码
//    @Select("select * from emp where id = #{id}")
//    public Emp getById(Integer id);

//    //根据ID查询员工
//    //方案1 给字段起别名，让别名与实体类属性一致
//    @Select("select id, username, password, name, gender, image, job, entrydate," +
//            "dept_id deptId, create_time createTime, update_time updateTime from emp where id = #{id}")
//    public Emp getById(Integer id);

//    //根据ID查询员工
//    //方案2 通过@Results,@Result注解手动映射封装
//    @Results({
//            @Result(column = "dept_id",property = "deptId"),
//            @Result(column = "create_time",property = "createTime"),
//            @Result(column = "update_time",property = "updateTime")
//    })
//    @Select("select * from emp where id = #{id}")
//    public Emp getById(Integer id);

        //根据ID查询员工
    //方案3 开启mybatis的驼峰命名自动映射开关，在application配置   自动将a_cloumn封装到aColumn属性名中
    @Select("select * from emp where id = #{id}")
    public Emp getById(Integer id);

    //条件查询员工
    //模糊查询中#{}不允许出现在引号中，所以name like '%#{name}%'不能出现需要换成${},
    //${}字符串拼接符号，不会生成预编译sql,但是有效率问题和安全问题
//    @Select("select * from emp where name like '%${name}%' and gender = #{gender} and " +
//            "entrydate between #{begin} and #{end} order by update_time desc ")
//    public List<Emp> list(String name, Short gender, LocalDate begin,LocalDate end);

    //通过字符串拼接函数concat实现拼接的同时避免安全性问题
    // name like concat('%',#{name},'%')
//    @Select("select * from emp where name like concat('%',#{name},'%') and gender = #{gender} and " +
//            "entrydate between #{begin} and #{end} order by update_time desc ")
//    public List<Emp> list(String name, Short gender, LocalDate begin,LocalDate end);


    //动态条件查询
    public List<Emp> list(String name, Short gender, LocalDate begin,LocalDate end);

    //动态更新员工
    public void update2(Emp emp);

    //批量删除员工
    public void deleteByIds(List<Integer> ids);
}
