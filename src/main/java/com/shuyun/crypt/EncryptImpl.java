package com.shuyun.crypt;

import com.shuyun.crypt.exception.EncryptException;
import com.shuyun.crypt.taobaosdk.com.taobao.api.security.SecurityClient;
import helloworld.BatchUpdateBean;
import helloworld.EncryptGrpc;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hewei
 * @version 5.0
 * @date 16/11/16  17:57
 * @desc
 */
@Slf4j
public class EncryptImpl extends EncryptGrpc.EncryptImplBase{

    @Override
    public void toEncrypt(BatchUpdateBean.EncryptRequest request, StreamObserver<BatchUpdateBean.EncryptReply> responseObserver) {
        try{
            List<String> encryptCustomerList=new ArrayList<>(request.getCustomersCount());
            for(String customer : request.getCustomersList()) {
                try {
                    encryptCustomerList.add(CryptTools.encrypt(customer, SecurityClient.NICK));
                } catch(EncryptException e) {
                    log.error("({})加密时出错", customer, e);
                    throw e;
                }
            }
            BatchUpdateBean.EncryptReply reply = BatchUpdateBean.EncryptReply.newBuilder()
                    .addAllCustomers(encryptCustomerList)
                    .setPlatform(request.getPlatform())
                    .setTenantId(request.getTenantId())
                    .setValue(request.getValue())
                    .setPropertyId(request.getPropertyId())
                    .build();
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        }catch(Throwable t){
            responseObserver.onError(Status.ABORTED.withDescription(t.toString()).withCause(t).asException());
        }
    }
}
