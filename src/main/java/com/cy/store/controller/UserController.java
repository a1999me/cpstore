package com.cy.store.controller;

import com.cy.store.controller.ex.*;
import com.cy.store.entity.User;
import com.cy.store.service.IUserService;
import com.cy.store.service.ex.InsertException;
import com.cy.store.service.ex.UsernameDuplicatedException;
import com.cy.store.util.JsonResult;
import javafx.scene.media.VideoTrack;
import org.apache.ibatis.annotations.Insert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("users")
public class UserController extends  BaseController{
    //自动装配业务层

    @Autowired
    private IUserService userService;

    /*@RequestMapping("reg")
    //@ResponseBody//表示此方法以json的格式进行数据的响应给到前端
    public JsonResult<Void> reg(User user) {
        //创建响应结果对象
        JsonResult<Void> result = new JsonResult<>();
        //调用业务层的方法,将参数对象进行传递
        try {
            userService.reg(user);//调用此方法时可能会抛出异常
            //设置一个正确状态码
            result.setState(200);
            //设置描述信息
            result.setMessage("用户注册成功");
        } catch (UsernameDuplicatedException e) {//有可能是username被占用了
            //设置一个错误状态码
            result.setState(4000);
            //设置描述信息
            result.setMessage("用户名被占用");
        } catch (InsertException e) {
            //设置一个错误状态码
            result.setState(5000);
            //设置描述信息
            result.setMessage("注册时产生未知的异常");
        }
        return  result;


    }
    */
    @PostMapping("reg")
    @ResponseBody
    //@ResponseBody//表示此方法以json的格式进行数据的响应给到前端
    public JsonResult<Void> reg(User user) {
        //创建响应结果对象
        JsonResult<Void> result = new JsonResult<>();
        //调用业务层的方法,将参数对象进行传递
        userService.reg(user);
        return new JsonResult<>(OK);
    }
    @RequestMapping("login")
    public JsonResult<User> login(String username, String password, HttpSession session){
        //直接调用业务层的接口方法
        User data = userService.login(username, password);
        //向session对象中完成
        session.setAttribute("uid",data.getUid());
        session.setAttribute("username",data.getUsername());
        //获取session中绑定的数据
        System.out.println(getuidFromSession(session));
        System.out.println(getUsernameFromSession(session));
        return new JsonResult<User>(OK,data);
    }
    @RequestMapping("change_password")
    public JsonResult<Void> changePassword(String oldPassword,String newPassword,HttpSession session){
        Integer uid=getuidFromSession(session);
        String username = getUsernameFromSession(session);
        //调用业务层的change方法传递uid
        userService.changePassword(uid,username,oldPassword,newPassword);
        return new  JsonResult<>(OK);
    }
    @RequestMapping("get_by_uid")
    public JsonResult<User>getByUid(HttpSession session){
        User data = userService.getByUid(getuidFromSession(session));
        return  new JsonResult<>(OK,data);
    }
    @RequestMapping("change_info")
    public JsonResult<Void>changeInfo(User user,
                                      HttpSession session){
        //user对象中有四部分的数据：username、phone\email\ gender
        //uid 数据需要再次封装到user对象当中
        Integer uid=getuidFromSession(session);
        String username=getUsernameFromSession(session);
        userService.changeInfo(uid,username,user);
        return new JsonResult<>(OK);
    }
/// 给文件定义大小
    public static final int AVATAR_MAX_SIZE=10*1024*1024;
    //限制上传文件的类型
    public static  final List<String> AVATAR_TYPE=new ArrayList<>();
    static {
        AVATAR_TYPE.add("image/jpeg");
        AVATAR_TYPE.add("image/png");
        AVATAR_TYPE.add("image/bmp");
        AVATAR_TYPE.add("image/gif");
    }

    /**
     * MultipartFile 接口是由stringmvc提供的接口，这个接口为我们包装了获取文件类型的数据
     * 任何类型的file都可以，springboot整合了springmvc ，所以只需要在库里请求的方法参数列表上声明一个参数类型为multipartfilr
     * 然后springboot会自动的将文件数据赋值给这个参数
     * @RequeParam 表示请求中的参数，将请求中的参数注入请求处理方法的某个参数上，
     * 如果名称不一致则可以使用@RequestParam注解进行标记和映射
     * @param session
     * @param file
     * @return
     */
    @PostMapping("change_avatar")
    public JsonResult<String>changeAvatar(HttpSession session,
                                         @RequestParam("file") MultipartFile file){
        //判断文件是否为空
        if (file.isEmpty()){
            throw new FileEmptyException("文件为空");
        }if (file.getSize()>AVATAR_MAX_SIZE){
            throw new FileSizeException("文件过大");
        }
        //判断文件的类型是否是我们规定的后缀类型
        String contentType = file.getContentType();
        //如果集合包含某个元素则返回值true
        if (!AVATAR_TYPE.contains(contentType)){
            throw new FileTypeException("文件类型不支持");
        }
        //接下来是把文件放到相应的位置  上传的文件
        String parent = session.getServletContext().getRealPath("ppp");
        //File对象指向这个路径，File是否存在
        File dir = new File(parent);
        if (!dir.exists()){//检测目录是否存在
            dir.mkdir();//创建当前目录
        }
        //获取到这个文件名称，UUID工具将生成一个新的字符串作为文件名
        String originalFilename = file.getOriginalFilename();
        System.out.println("OriginalFilename="+originalFilename);
        int index = originalFilename.lastIndexOf(".");
        String suffix = originalFilename.substring(index);
        String filename = UUID.randomUUID().toString().toUpperCase()+suffix;
        File dest = new File(dir, filename);//是一个空文件
        //参数file中数据写入到空文件中
        try {//将file文件中数据写入到dest文件中
            file.transferTo(dest);
        } catch (IOException e) {
           throw new FileUploadIOException("文件读取异常");

        }catch (FileStateException e){
            throw new FileStateException("文件状态异常");
        }
        Integer uid=getuidFromSession(session);
        String username = getUsernameFromSession(session);
        String avatar="/ppp/"+filename;
        //返回头像的路径  /upload/test.png
        userService.changeAvatar(uid,avatar,username);
        //返回用户头像的路径给前端页面，将来用于头像展示使用
        return new JsonResult<>(OK,avatar);

    }
}

