package com.fiberhome.ms.cict.app.union.ui;

import com.fiberhome.ms.cict.app.union.service.AddressBookService;
import com.fiberhome.ms.cict.app.util.ApiQueryParamAndToken;
import com.fiberhome.ms.common.api.base.BaseUI;
import com.fiberhome.ms.common.protocol.res.ObjectResult;
import com.fiberhome.ms.common.util.Constant;
import com.fiberhome.ms.common.util.ErrConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;

/**
 * @title: AddressBookController
 * @projectName service
 * @description: TODO
 * @author xhuang100
 * @date 2019/8/1415:39
 */

@Path(Constant.BASE_APP + "/addressBook")
@Api(tags = "app工会通讯录接口")
@Produces("application/json")
@Consumes("application/json")
public class AddressBookController extends BaseUI {
    Logger logger = LoggerFactory.getLogger(ApplyController.class);

    @Autowired
    private AddressBookService addressBookService;


    /**
     　　* @description: 工会通讯录树
     　　* @author xhuang100
     　　* @date  2019/8/14
     　　*/
    @GET
    @Path(value = "/list/{orgId}/{tenantId}")
    @ApiOperation(notes = "参数:orgId,-1则表示最高根节点，其它则表示组织id,tenantId:企业id", value = "通讯录树")
    @ApiQueryParamAndToken
    public ObjectResult list(
            @PathParam("orgId") String orgId,
            @PathParam("tenantId") String tenantId){
        ObjectResult result = new ObjectResult();
        try{
            result = addressBookService.list(orgId,tenantId);
        }catch (Exception e){
            logger.error("工会通讯录列表", e);
            result.setErrorCode(ErrConstant.A01X00010);
        }
        return result;
    }

    /**
     　　* @description: 工会通讯录搜索
     　　* @author xhuang100
     　　* @date  2019/8/14
     　　*/
    @GET
    @Path(value = "/search")
    @ApiOperation(notes = "参数:tenantId(企业id),name(会员名),userNo(员工号),unionId(组织id),id(会员id)", value = "工会通讯录搜索")
    @ApiQueryParamAndToken
    public ObjectResult search(){
        ObjectResult result = new ObjectResult();
        try{
            result = addressBookService.search(this.getQueryParam());
        }catch (Exception e){
            logger.error("工会通讯录搜索", e);
            result.setErrorCode(ErrConstant.A01X00010);
        }
        return result;
    }

    /**
     　　* @description: 查询当前组织下的人员的详细信息
     　　* @author xhuang100
     　　* @date  2019/8/14
     　　*/
    @GET
    @Path(value = "/getPersonDetail/{id}/{userId}")
    @ApiOperation(notes = "id(工会会员id),userId(用户id)", value = "工会通讯录人员信息详情")
    @ApiQueryParamAndToken
    public ObjectResult getPersonDetail(@PathParam("id") String id,@PathParam("userId") String userId){
        ObjectResult result = new ObjectResult();
        try{
            result = addressBookService.personDetail(id,userId);
        }catch (Exception e){
            logger.error("工会通讯录人员信息详情", e);
            result.setErrorCode(ErrConstant.A01X00010);
        }
        return result;
    }
}
