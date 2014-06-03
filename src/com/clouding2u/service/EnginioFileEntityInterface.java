package com.clouding2u.service;


import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.mime.MultipartTypedOutput;
import retrofit.mime.TypedByteArray;

import com.google.gson.JsonObject;

public interface EnginioFileEntityInterface {
@POST("/files")
JsonObject uploadFile(
		           @Body MultipartTypedOutput output
		          );
 @GET("/files/{id}")
 JsonObject getFileInfo(@Path("id") String fileId);
 @GET("/files/{id}/download_url")
 JsonObject getURL(@Path("id") String fileId);
 @POST("/files")
 JsonObject insertChunkedFileProperty(@Body JsonObject uploadFileProperty);
 @PUT("/files/{id}/chunk")
 JsonObject chunkedUpload(@Header("Content-Range") String contentRange, @Path("id") String id,@Body TypedByteArray uploadData);

}
