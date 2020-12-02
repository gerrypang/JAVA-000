package com.gerry.pang.service;

public interface CommonService {
    
    void initEnvironment();
    
    void cleanEnvironment();
    
    void processSuccess();
    
    void processFailure();
    
    void printData();
    
}
