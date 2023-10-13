package io.aexp.api.client.core;

import com.paytend.models.trans.req.BatchAdminRequest;
import org.junit.Test;

/**
 * @author Sunny
 * @create 2023/8/21 21:20
 */
public class BatchAdminRequestTest {


    int version = 10000000;
    String merId = "8127921740";
    int batchID = 100000;

    @Test
    public void testBatchAdminRequest() {

        BatchAdminRequest batchAdminRequest = BatchAdminRequest.builder()
                .Version(String.valueOf(version))
                .MerId(merId)
                .BatchID(String.valueOf(batchID))
                .BatchOperation(String.valueOf(batchID))
                .build();

    }
}
