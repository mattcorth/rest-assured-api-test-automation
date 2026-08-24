package com.mattcorth.cucumbertests.resources;

public enum APIResource {
    addPlaceAPI("/maps/api/place/add/json"),
    getPlaceAPI("/maps/api/place/get/json"),
    deletePlaceAPI("/maps/api/place/delete/json");

    private final String endpoint;
    APIResource(String path){
        this.endpoint = path;
    }

    public String getEndpoint(){
        return this.endpoint;
    }
}
