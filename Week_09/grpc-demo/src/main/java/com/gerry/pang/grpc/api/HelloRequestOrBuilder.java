// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: HelloWorld.proto

package com.gerry.pang.grpc.api;

public interface HelloRequestOrBuilder extends
    // @@protoc_insertion_point(interface_extends:helloworld.HelloRequest)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * 参数默认都为可选，如果没有赋值，那么接口中不会出现该参数，不会默认为null之类的
   * </pre>
   *
   * <code>string serviceName = 1;</code>
   * @return The serviceName.
   */
  java.lang.String getServiceName();
  /**
   * <pre>
   * 参数默认都为可选，如果没有赋值，那么接口中不会出现该参数，不会默认为null之类的
   * </pre>
   *
   * <code>string serviceName = 1;</code>
   * @return The bytes for serviceName.
   */
  com.google.protobuf.ByteString
      getServiceNameBytes();

  /**
   * <code>string methodName = 2;</code>
   * @return The methodName.
   */
  java.lang.String getMethodName();
  /**
   * <code>string methodName = 2;</code>
   * @return The bytes for methodName.
   */
  com.google.protobuf.ByteString
      getMethodNameBytes();

  /**
   * <code>string messageData = 3;</code>
   * @return The messageData.
   */
  java.lang.String getMessageData();
  /**
   * <code>string messageData = 3;</code>
   * @return The bytes for messageData.
   */
  com.google.protobuf.ByteString
      getMessageDataBytes();

  /**
   * <code>string request_id = 4;</code>
   * @return The requestId.
   */
  java.lang.String getRequestId();
  /**
   * <code>string request_id = 4;</code>
   * @return The bytes for requestId.
   */
  com.google.protobuf.ByteString
      getRequestIdBytes();
}
