import org.junit.Test;

import java.lang.reflect.Constructor;

/**
 * description:TODO
 *
 * @author caiqiang.wu
 * @create 2020/03/10
 **/
public class ReflectTest {


    @Test
    public void test()throws Exception{
        //加载Class对象
        //会报出不存在该类的异常
        Class c=Class.forName("User");

        //获取所有公用构造方法
        System.out.println("================获取所有公共的构造方法=================");
        Constructor[] constructors=c.getConstructors();
        for (Constructor constructor:constructors) {
            System.out.println("公共的构造方法:"+constructor+","+constructor.getDeclaringClass());
        }
        //获取所有构造方法
        System.out.println("================获取所有的构造方法=================");
        Constructor[] declaredconstructors=c.getDeclaredConstructors();
        for (Constructor constructor:declaredconstructors) {
            System.out.println("所有构造方法:"+constructor);
        }
    }
}
