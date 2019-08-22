package com.fiberhome.ms.cict.app.union.feignresource.fallback;

import com.fiberhome.ms.cict.app.union.feignresource.AddressBookUnionFeignResource;
import com.fiberhome.ms.cict.app.util.CommonFunc;
import com.fiberhome.ms.common.protocol.res.ObjectResp;
import com.fiberhome.ms.common.util.ErrConstant;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @title: AddressBookUnionFeignResourceFallback
 * @projectName service
 * @description: TODO
 * @author xhuang100
 * @date 2019/8/1415:56
 */


@Component
public class AddressBookUnionFeignResourceFallback implements AddressBookUnionFeignResource {

    @Override
    public ObjectResp getUnionOrgByOrgId(String orgId, String tenantId) {
        return CommonFunc.getServiceFailed(ErrConstant.C05X00010);
    }

    @Override
    public ObjectResp getList(Map<String, Object> map) {
        return CommonFunc.getServiceFailed(ErrConstant.C05X00010);
    }

    @Override
    public ObjectResp getById(String id) {
        return CommonFunc.getServiceFailed(ErrConstant.C05X00010);
    }

}
