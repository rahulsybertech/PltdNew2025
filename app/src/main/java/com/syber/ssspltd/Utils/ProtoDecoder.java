package com.syber.ssspltd.Utils;

import androidx.annotation.Nullable;

import com.chuckerteam.chucker.api.BodyDecoder;

import java.nio.charset.StandardCharsets;

import okhttp3.Request;
import okhttp3.Response;
import okio.ByteString;

public class ProtoDecoder implements BodyDecoder {

    @Nullable
    @Override
    public String decodeRequest(Request request, ByteString body) {
        if (isExpectedProtoRequest(request)) {
            return decodeProtoBody(body);
        }
        return null;
    }

    @Nullable
    @Override
    public String decodeResponse(Response response, ByteString body) {
        if (isExpectedProtoResponse(response.request())) {
            return decodeProtoBody(body);
        }
        return null;
    }

    private boolean isExpectedProtoRequest(Request request) {
        return "application/x-protobuf".equalsIgnoreCase(request.header("Content-Type"));
    }

    private boolean isExpectedProtoResponse(Request request) {
        return "application/x-protobuf".equalsIgnoreCase(request.header("Accept"));
    }

    private String decodeProtoBody(ByteString body) {
        // This is just a placeholder — you can use real protobuf decoding here
        return "Proto Decoded (hex): " + body.hex();
    }
}
