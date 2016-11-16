package com.shuyun.crypt;

import helloworld.BatchUpdateBean;
import helloworld.EncryptGrpc;
import io.grpc.stub.StreamObserver;

/**
 * @author hewei
 * @version 5.0
 * @date 16/11/16  17:57
 * @desc
 */
public class EncryptImpl extends EncryptGrpc.EncryptImplBase{

    @Override
    public void toEncrypt(BatchUpdateBean.EncryptRequest request, StreamObserver<BatchUpdateBean.EncryptReply> responseObserver) {
        BatchUpdateBean.EncryptReply reply = BatchUpdateBean.EncryptReply.newBuilder()
                .setPlatform(request.getPlatform()+ "<-")
                .setTenantId(request.getTenantId()+ "<-")
                .setValue(request.getValue()+ "<-")
                .setPropertyId(request.getPropertyId()+ "<-")
                .build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
