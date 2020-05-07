package com.base.vavr;

import io.vavr.API;
import io.vavr.control.Either;
import io.vavr.control.Option;
import io.vavr.control.Try;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.vavr.API.$;
import static io.vavr.API.Case;

/**
 * @Description:
 * @Author:zhao.song
 * @Date:2019/12/14 18:39
 * @Version:1.0
 */
public class EitherTest {
    private static Logger logger = LoggerFactory.getLogger(EitherTest.class);

    public static void main(String[] args) {
        Either<String, Boolean> either = validate("kevin1");
        if (either.isRight()) {
            System.out.println(either.get());
        } else {
            System.out.println(either.getLeft());
        }

        Either<Throwable, String> either2 = register(1);
        System.out.println(either2);

        logger.error("从[{}]下载视频到本地[{}]时失败,错误信息[{}]", "www.baidu.com", "/TRS/WCMData/webpic", "error001");
    }


    public static Either<String, Boolean> validate(String name) {
        if (name.equalsIgnoreCase("kevin")) {
            return Either.right(true);
        } else {
            return Either.left("不被期待的用户");
        }
    }

    public static Either<Throwable, String> register(int code) {
        Try<Either> t = Try.of(()->{
            Either either = null;
            if (code == 1) {
                either =  Either.left(new RuntimeException("状态码为1异常!"));
            }
            if (code == 2) {
                either = Either.right("状态码为2正常!");
            }
            return either;

        });
        if (t.isSuccess()) {
            return t.get();
        }else {
            return Either.left(t.failed().get());
        }
    }

}
