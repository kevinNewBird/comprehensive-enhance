
package com.base.vavr;


import io.vavr.collection.Seq;
import io.vavr.control.Validation;

/**
 * @Description: java8函数式库vavr(引入外部依赖vavr)  -- 验证Validation
 * @Author:zhao.song
 * @Date:2019/12/14 16:09
 * @Version:1.0
 */
public class PersonValidatorTest {


    /**
     * @decription: Validation
     *      1.Vavr将函数式编程中Applicative Functor(函子)的概念引入java.vavr.control.Validation类能够将错误整合.
     *        通常情况下,程序遇到错误,并且未做处理就会终止.然而,Validation会继续处理,并将程序错误累积,最终作为一个整体处理.
     *      2.例如我们希望注册用户,用户具有用户名和密码.我们会接收一个输入,然后决定是否创建Person实例或返回一个错误.接着,
     *        创建一个PersonValidation类.每个变量都会有一个方法来验证.此外还有方法可以将所有的验证结果整合到一个Validation实例中.
     * @param args
     * @return:
     */
    public static void main(String[] args) {
        PersonValidator validator = new PersonValidator();
        Validation<Seq<String>, Person> validation = validator.validatePerson("kevin我", 13);
        if(validation.isValid()){
            Person person = validation.get();
            System.out.println(person);
        }else {
            Seq<String> error = validation.getError();
            System.out.println(error);
        }
    }
}
