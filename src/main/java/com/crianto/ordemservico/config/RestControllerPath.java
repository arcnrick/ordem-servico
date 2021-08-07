package com.crianto.ordemservico.config;

public final class RestControllerPath {

    ///////////////////////////////////////////////////////////////
    // ROOT PATH
    ///////////////////////////////////////////////////////////////
    public static final String ALL = "/**";
    public static final String ROOT_PATH = "/api";
    public static final String PRIVATE_ROOT_PATH = ROOT_PATH + "/private";

    ///////////////////////////////////////////////////////////////
    // PRIVATE PATHS
    ///////////////////////////////////////////////////////////////
    public static final String MARCA_PATH = PRIVATE_ROOT_PATH + "/marca";
    public static final String EQUIPAMENTO_PATH = PRIVATE_ROOT_PATH + "/equipamento";
    public static final String CLIENTE_PATH = PRIVATE_ROOT_PATH + "/cliente";
    public static final String OS_PATH = PRIVATE_ROOT_PATH + "/os";
}
		
	
