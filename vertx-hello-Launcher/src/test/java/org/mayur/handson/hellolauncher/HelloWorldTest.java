package org.mayur.handson.hellolauncher;

import io.vertx.core.Launcher;
import junit.framework.TestCase;
import org.junit.Test;

public class HelloWorldTest extends TestCase {

    @Test
    public void testUsingLauncher(){
        Launcher.executeCommand("run", HelloWorld.class.getName());
    }

}