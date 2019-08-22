package com.fiberhome.ms.cict.app.union.feignresource;

import com.fiberhome.ms.cict.app.union.feignresource.fallback.AddressBookUnionFeignResourceFallback;
import com.fiberhome.ms.common.protocol.res.ObjectResp;
import com.fiberhome.ms.common.util.Constant;
import com.fiberhome.smartms.feign.FeignClientsConfigurationCustom;
import org.springframework.cloud.netflix.feign.FeignClient;

import javax.ws.rs.*;
import java.util.Map;

/**
 * @title: AddressBookUnionFeignResource
 * @projectName service
 * @description: TODO
 * @author xhuang100
 * @date 2019/8/1415:52
 */

@FeignClient(name = "union", path = Constant.BASE_UNION, configuration = {FeignClientsConfigurationCustom.class}, fallback = AddressBookUnionFeignResourceFallback.class)
@Produces("application/json")
@Consumes("application/json")
public interface AddressBookUnionFeignResource {

    /**
     * 工会组织树(展示两级往下延申)
     *
     * @param orgId:组织id,
     * @param tenantId:企业id
     * @return
     */
    @GET
    @Path("/org/getUnionOrgByOrgId/{orgId}/{tenantId}")
    ObjectResp getUnionOrgByOrgId(@PathParam("orgId") String orgId,@PathParam("tenantId")String tenantId);

    /**
     * @description: 查询会员信息
     * @param map tenantId   企业id
     *            name       会员名
     *            userNo     员工号
     *            unionId    组织id
     *            id         会员id
     * @return
     */
    @POST
    @Path("/user/getList")
    ObjectResp getList(Map<String, Object> map);


    /**
     * 根据id查询工会会员详情
     *
     * @param id  工会会员id
     * @return
     */
    @POST
    @Path("/user/getById/{id}")
    ObjectResp getById(@PathParam("id") String id);
}
