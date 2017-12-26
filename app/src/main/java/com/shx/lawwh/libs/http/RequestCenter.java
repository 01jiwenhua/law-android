package com.shx.lawwh.libs.http;

import com.shx.lawwh.entity.request.ChemicalsRequest;
import com.shx.lawwh.entity.request.LawRequest;

/**
 * Created by 邵鸿轩 on 2016/12/6.
 */

public class RequestCenter {
    public static final String GET_LAWLIST="/law//list";
    public static final String GET_KNOWNLIST="/chemicals/getKnownList";
    public static final String GET_CHEMICALSDETAILS="/chemicals/getChemicalsDetails";
    public static final String GET_UNKNOWPARAMS="/chemicals/getUnknowParams";
    /**
     * 获取法律法规列表
     * @param lawRequest
     * @param callBack
     */
    public static void getLawList(LawRequest lawRequest,HttpCallBack callBack){
        ZCRequest request=new ZCRequest();
        request.setUrl(GET_LAWLIST);

        request.putParams("name",lawRequest.getName());
        request.putParams("level",lawRequest.getLevel());
        request.putParams("typeName",lawRequest.getTypeName());
        request.putParams("typeCode",lawRequest.getTypeCode());
        request.putParams("issoNo",lawRequest.getIssue_no());
        request.putParams("page",lawRequest.getPage());
        request.putParams("pageSize",lawRequest.getPageSize());
        request.putParams("description",lawRequest.getDescription());
        HttpManager.getInstance().doPost(request,callBack);
    }

    /**
     * 获取已知物质
     * @param chemicalsRequest
     * @param callBack
     */
    public static void getKnownlist(ChemicalsRequest chemicalsRequest,HttpCallBack callBack){
        ZCRequest request=new ZCRequest();
        request.setUrl(GET_KNOWNLIST);
        request.putParams("name",chemicalsRequest.getName());
        request.putParams("page",chemicalsRequest.getPage());
        request.putParams("pageSize",chemicalsRequest.getPageSize());
        HttpManager.getInstance().doPost(request,callBack);

    }
    public static void getChemicalsdetails(String id,HttpCallBack callBack){
        ZCRequest request=new ZCRequest();
        request.setUrl(GET_CHEMICALSDETAILS);
        request.putParams("id",id);
        HttpManager.getInstance().doPost(request,callBack);

    }
    public static void getUnknowparams(HttpCallBack callBack){
        ZCRequest request=new ZCRequest();
        request.setUrl(GET_UNKNOWPARAMS);
        HttpManager.getInstance().doPost(request,callBack);
    }

}
