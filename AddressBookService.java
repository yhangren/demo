package com.fiberhome.ms.cict.app.union.service;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.fiberhome.ms.cict.app.system.entity.User;
import com.fiberhome.ms.cict.app.system.service.UserActionService;
import com.fiberhome.ms.cict.app.union.entity.PersonDetail;
import com.fiberhome.ms.cict.app.union.entity.UnionOrg;
import com.fiberhome.ms.cict.app.union.entity.UnionUser;
import com.fiberhome.ms.cict.app.union.feignresource.AddressBookUnionFeignResource;
import com.fiberhome.ms.cict.app.union.feignresource.AddressBookUserFeignResource;
import com.fiberhome.ms.cict.app.util.AppConstant;
import com.fiberhome.ms.cict.app.util.CommonFunc;
import com.fiberhome.ms.cict.app.util.UserTokenUtils;
import com.fiberhome.ms.common.protocol.res.ObjectResp;
import com.fiberhome.ms.common.protocol.res.ObjectResult;
import com.fiberhome.ms.common.util.Constant;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: AddressBookService
 * @projectName service
 * @description: TODO
 * @author xhuang100
 * @date 2019/8/1415:49
 */

@Service
public class AddressBookService {

    @Autowired
    private AddressBookUnionFeignResource addressBookUnionFeignResource;

    @Autowired
    private AddressBookUserFeignResource addressBookUserFeignResource;

    @Autowired
    private UserActionService userActionService;

    @Autowired
    private HttpServletRequest request;
    
    /**
     　　* @description: 工会通讯录
     　　* @author xhuang100
     　　* @date  2019/8/14 
     　　*/
    public ObjectResult list(String orgId,String tenantId) {
        //将tenantId 以及 parentId 传给基础层
        ObjectResp resp = addressBookUnionFeignResource.getUnionOrgByOrgId(orgId,tenantId);
        Object object = CommonFunc.getObject(resp);
        UnionOrg unionOrg = JSON.parseObject(object.toString(), new TypeReference<UnionOrg>() {});
        Map<String,Object> map = new HashMap<>();
        map.put("tenantId", tenantId);
        map.put("unionId", unionOrg.getId());
        ObjectResp resp2 = addressBookUnionFeignResource.getList(map);
        Object object2 = CommonFunc.getObject(resp2);
        List<UnionUser> unionUsers = (List<UnionUser>) object2;
        unionOrg.setUnionUsers(unionUsers);
        ObjectResult result = new ObjectResult();
        result.addObject(unionOrg);
        return result;
    }


    /**
     　　* @description: 工会通讯录中的搜索
     　　* @author xhuang100
     　　* @date  2019/8/19
     　　*/
    public ObjectResult search(Map<String,Object> map){
        String userId = UserTokenUtils.getUserId(request);
        //根据用户id获取用户信息
        User user = userActionService.getUserInfo(userId);
        map.put("tenantId", user.getTenantId());
        ObjectResp resp = addressBookUnionFeignResource.getList(map);
        return CommonFunc.getResult(resp);
    }

    /**
     　　* @description: 工会用户详情
     　　* @author xhuang100
     　　* @date  2019/8/19
     　　*/
    public ObjectResult personDetail(String id,String userId){
        //查询工会会员id
        ObjectResp resp = addressBookUnionFeignResource.getById(id);
        Object object = CommonFunc.getObject(resp);
        UnionUser unionUser = (UnionUser) JSONObject.toBean(JSONObject.fromObject(object.toString()), UnionUser.class);
        //查询用户信息
        ObjectResp resp1 = addressBookUserFeignResource.getById(userId);
        Object object1 = CommonFunc.getObject(resp1);
        com.fiberhome.ms.cict.app.union.entity.User user = (com.fiberhome.ms.cict.app.union.entity.User) JSONObject.toBean(JSONObject.fromObject(object1.toString()), com.fiberhome.ms.cict.app.union.entity.User.class);
        //人员详情
        PersonDetail personDetail = new PersonDetail();
        personDetail.setUnionUserId(unionUser.getId());
        personDetail.setUserId(user.getId());
        personDetail.setUserName(user.getName());
        personDetail.setUnionUserName(unionUser.getName());
        ObjectResult result = new ObjectResult();
        result.add(AppConstant.KEYNAME, personDetail);
        return result;
    }

}
