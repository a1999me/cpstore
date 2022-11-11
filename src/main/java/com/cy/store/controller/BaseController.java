package com.cy.store.controller;

import com.cy.store.controller.ex.*;
import com.cy.store.service.ex.*;
import com.cy.store.util.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpSession;
import javax.sql.rowset.serial.SerialException;

/**
 * 表示控制层类的基类
 * 主要是做异常的响应处理
 */
public class BaseController {
    //设置所有通过的都是200
    public static final int OK=200;

    /**
     * 操作不成功
     * 请求处理方法，这个方法的返回值就是需要传递给前端数据
     */
    //当项目中出现了异常，会被同意拦截到此方法中，这种方法充当的是请求处理方法，方法的返回值直接给到前端
    @ExceptionHandler({ServiceException.class,FileUploadException.class})//注解主要是用于统一处理抛出的异常
    public JsonResult<Void> handleException(Throwable e){
        JsonResult<Void>result=new JsonResult<>(e);
        //判断e的数据类型是否属于username
        if(e instanceof UsernameDuplicatedException){
            result.setState(4000);
            result.setMessage("用户名已经被占用");
        } else if (e instanceof UserNotFoundException){
            result.setState(4001);
            result.setMessage("用户数据不存在");
        }else if (e instanceof PasswordNotMatchException){
            result.setState(4002);
            result.setMessage("用户密码错误的异常");
        }else if (e instanceof AddressCountLimitException){
            result.setState(4003);
            result.setMessage("用户的收货地址超出上限");
        } else if (e instanceof AddressNotFoundException){
            result.setState(4004);
            result.setMessage("用户的收货地址数据不存在");
        }
        else if (e instanceof AccessDeniedException){
            result.setState(4005);
            result.setMessage("数据非法访问的异常");
        }else if (e instanceof ProductNotFoundException){
            result.setState(4006);
            result.setMessage("数据找不到了");
        }else if (e instanceof CartNotFoundException) {
            result.setState(4007);
            result.setMessage("购物车表不存在该商品的异常");
        }
        else if (e instanceof InsertException){
            result.setState(5000);
            result.setMessage("注册时产生未知的异常");
        } else if (e instanceof UserNotFoundException){
            result.setState(5001);
            result.setMessage("用户数据不存在");
        }else if (e instanceof PasswordNotMatchException){
            result.setState(5002);
            result.setMessage("用户密码错误的异常");
        }else if (e instanceof UpdateException){
            result.setState(5003);
            result.setMessage("更新数据时产生未知的异常");
        } else if (e instanceof FileEmptyException) {
            result.setState(6000);
        } else if (e instanceof FileSizeException) {
            result.setState(6001);
        } else if (e instanceof FileTypeException) {
            result.setState(6002);
        } else if (e instanceof FileStateException) {
            result.setState(6003);
        } else if (e instanceof FileUploadIOException) {
            result.setState(6004);
        }else if (e instanceof DeleteException){
            result.setState(6005);
            result.setMessage("删除数据时产生的异常");
        }
        return result;
    }

    /**
     * 获取当前登录用户的username
     * @param session
     * @return
     */
    protected  final  Integer getuidFromSession(HttpSession session){
        //session当中可以保存任何数据类型，但是cookie当中只能保存String数据类型
       //转换成integer类型
        return  Integer.valueOf(session.getAttribute("uid").toString());
    }

    /**
     * 获取当前登录用户的username
     * @param session
     * @return
     */
    protected  final String getUsernameFromSession(HttpSession session){
       return session.getAttribute("username").toString();
    }
}
