package com.siftscience;

import com.siftscience.model.DecisionStatusFieldSet;
import okhttp3.*;

import java.io.IOException;

import static com.siftscience.model.DecisionStatusFieldSet.ENTITY_CONTENT;
import static com.siftscience.model.DecisionStatusFieldSet.ENTITY_SESSIONS;

public class DecisionStatusRequest extends SiftRequest<DecisionStatusResponse> {
    DecisionStatusRequest(HttpUrl baseUrl, String accountId, OkHttpClient okClient, DecisionStatusFieldSet fields) {
        super(baseUrl, accountId, okClient, fields);
    }

    @Override
    protected HttpUrl path(HttpUrl baseUrl) {
        HttpUrl.Builder builder = baseUrl.newBuilder()
            .addPathSegment("v3")
            .addPathSegment("accounts")
            .addPathSegment(getAccountId());
        String entity = ((DecisionStatusFieldSet)fieldSet).getEntity();
        if (entity.equals(ENTITY_CONTENT) || entity.equals(ENTITY_SESSIONS)) {
            builder = builder
                .addPathSegment("users")
                .addPathSegment(((DecisionStatusFieldSet)fieldSet).getUserId());
        }
        return builder
            .addPathSegment(entity)
            .addPathSegment(((DecisionStatusFieldSet)fieldSet).getEntityId())
            .addPathSegment("decisions")
            .build();
    }

    @Override
    DecisionStatusResponse buildResponse(Response response, FieldSet requestFields)
            throws IOException {
        return new DecisionStatusResponse(response, requestFields);
    }

    @Override
    protected void modifyRequestBuilder(Request.Builder builder) {
        super.modifyRequestBuilder(builder);
        builder.header("Authorization", Credentials.basic(fieldSet.getApiKey(), "")).get();
    }
}
