package com.unisound.iot.controller.mongo;

import com.unisound.iot.common.mongo.Channel;
import com.unisound.iot.common.vo.ItemVo;
import com.unisound.iot.controller.base.BaseController;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Created by yingwuluohan on 2018/12/18.
 * @Company 北京云知声技术有限公司
 */
@RestController
@RequestMapping("v1/user/")
public class UserController extends BaseController {

//    @Autowired
//    private UserServiceImpl userService;
//    @Autowired
//    private ChannelServiceImpl channelService;


    /**v1/user/find_user */
    @SuppressWarnings("unchecked")
    @RequestMapping(value="find_user/{id}",method = {RequestMethod.GET, RequestMethod.POST})
    public String mongoOperate(HttpServletRequest request, @ModelAttribute ItemVo itemVo ,
                                      @PathVariable(name="id") String id){
        Channel channel = new Channel();
        channel.setCode( "testcode" );
        channel.setName( "terstname");
        channel.setId( "2837381");
//        channelService.addChannel( channel );
//        User list = userService.getUser( id );
        return "ok";
    }

    //v1/user/find_channel/testcode
    @SuppressWarnings("unchecked")
    @RequestMapping(value="find_channel/{code}",method = {RequestMethod.GET, RequestMethod.POST})
    public List mongoOperate2(HttpServletRequest request, @ModelAttribute ItemVo itemVo ,
                               @PathVariable(name="code") String code){
//        List< Channel > list = channelService.findChannel( code );
        return null;
    }


}
