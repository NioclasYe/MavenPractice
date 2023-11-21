package com.nicolas.maven;


import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;


/**
 * @goal sayHello
 */
public class MyHelloPlugin extends AbstractMojo {
    //指定調用本類execute()方法的目標 >> mvn xxx:sayHello => 就會調用execute()
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        //getLog() 父類方法
        getLog().info("=====> This id First Plugin <======");
    }
}
