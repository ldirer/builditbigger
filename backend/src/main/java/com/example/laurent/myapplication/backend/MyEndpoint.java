/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.example.laurent.myapplication.backend;

import com.example.JokeProvider;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import javax.inject.Named;

/** An endpoint class we are exposing */
@Api(
  name = "myApi",
  version = "v1",
  namespace = @ApiNamespace(
    ownerDomain = "backend.myapplication.laurent.example.com",
    ownerName = "backend.myapplication.laurent.example.com",
    packagePath=""
  )
)
public class MyEndpoint {

    public JokeProvider mJokeProvider;

    public MyEndpoint() {
        super();
        mJokeProvider = new JokeProvider();
    }

    @ApiMethod(name="getJoke", httpMethod = "GET") //, path = "joke")
    public MyBean getJoke(){
        MyBean response = new MyBean();
//        response.setData("So, this is the story of a penguin that breathes...");
        response.setData(mJokeProvider.getJoke());
        return response;
    }

    /** A simple endpoint method that takes a name and says Hi back */
    @ApiMethod(name = "sayHi")
    public MyBean sayHi(@Named("name") String name) {
        MyBean response = new MyBean();
        response.setData("Hi, " + name);

        return response;
    }


}
