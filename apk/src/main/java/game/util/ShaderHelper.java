package game.util;

import android.util.Log;
import game.util.log.LoggerConfig;

import static android.opengl.GLES20.*;

/*
* Now that we have read in the shader source from our file, the next step is to compile exh shader. We'll create a new helper class
* that is going to create a new OpenGL shader object, compile our shader code, and return the shader object for that shader code.
* */

public class ShaderHelper {

    private static final String TAG = SecurityException.class.getName();

    public static int compileVertexShader(String shaderCode) {
        return compileShader(GL_VERTEX_SHADER, shaderCode);
    }

    public static int compileFragmentShader(String shaderCode) {
        return compileShader(GL_FRAGMENT_SHADER, shaderCode);
    }

    public static int compileShader(int type, String shaderCode) {

        /*
        * CREATING A NEW SHADER OBJECT
        * The first thing we should create a new shader object and check if the creation was successful.
        *
        * The type of shaderObjectId can be GL_VERTEX_SHADER for a vertex shader, or GL_FRAGMENT_SHADER for a fragment shader.
        *
        * Take note of how we create the obj and check if it's valid; this pattern is used everywhere in OpenGL:
        * 1. Create an obj using a call as glCreateShader(). The call will return an integer.
        * 2. This integer is the reference to our OpenGL object.Whenever we want to refer to this object in the future. we'll pass
        * the same integer back to OpenGL.
        * 3. A return value of 0 indicates that the object creation failed and is analogous to a return value of null in Java code.
        *
        * If the obj creation failed, we'll return 0 to the calling code. Why do we return 0 instead of throwing an exception?
        * Well, OpenGL doesn't actually throw ant exceptions internally. Instead, we'll get a return value of 0 or
        * OpenGL will inform us of the error through glFetError(), a method that lets us ask OpenGL if any of our
        * API calls have resulted in an error. We'll follow the same convention to stay consistent.
        * */

        final int shaderObjectId = glCreateShader(type); //create new shader obj with a call to glCreateShader() and store the ID of the obj

        if (shaderObjectId == 0) {
            if (LoggerConfig.ON) {
                Log.w(TAG, "Could not create new shader");
            }
            return 0;
        }

        /*
        * UPLOADING AND COMPILING THE SHADER SOURCE CODE
        * Once we have a valid shader object, we call glShaderSource(shaderObjectId, shaderCode) to upload the source code.
        * This call tells OpenGL to read in the source code defined in the String shaderCode and associate it with the shader
        * object referred to by shaderObjectId. We can then call glCompileShader(shaderObjectId) to compile the shader
        *
        * glCompileShader tells OpenGL to compile the source code that was previously uploaded to shaderObjectId
        * */

        glShaderSource(shaderObjectId, shaderCode);
        glCompileShader(shaderObjectId);

        /*
        * RETRIEVING THE COMPILATION STATUS
        * To check whether the compile failed or succeeded, we create a new int array with a length of 1 and call it compileStatus.
        * We then call glGetShaderiv(shaderObjectId, GL_COMPILE_STATUS, compileStatus, 0). This tells OpenGL to read the compile
        * status associated with shaderObjectId and write it to the 0-th element of compileStatus.
        *
        * We tell OpenGL to store the result in array's first element
        * */
        final int[] compileStatus = new int[1];
        glGetShaderiv(shaderObjectId, GL_COMPILE_STATUS, compileStatus, 0);

        /*
        * RETRIEVING THE SHADER INFO LOG
        * When we get the compile status. OpenGL will give us a simple yes or no answer.
        * It turns out that we can get a human-readable message by calling glGetShaderInfoLog(shaderObjectId). If OpenGL has anything
        * interesting to say about our shader, it will store message in shader's info log.
        *
        * We print this log to Android's log output, wrapping everything in an IF statement that checks the value of LoggerConfig.ON.
        * We can easily turn off these logs by flipping the constant to false.
        * */

        if (LoggerConfig.ON) {
            Log.v(TAG, "Results of compiling source:" + "\n" + shaderCode + "\n:" + glGetShaderInfoLog(shaderObjectId));
        }

        if (compileStatus[0] == 0) {
            glDeleteShader(shaderObjectId);

            if (LoggerConfig.ON) {
                Log.w(TAG, "Compilation of shader failed.");
            }

            return 0;
        }

        return shaderObjectId;
    }

    public static int linkProgram(int vertexShaderId, int fragmentShaderId) {
        final int programObjectId = glCreateProgram();

        if (programObjectId == 0) {
            if (LoggerConfig.ON) {
                Log.w(TAG, "Could not create new program");
            }
            return 0;
        }

        glAttachShader(programObjectId, vertexShaderId);
        glAttachShader(programObjectId, fragmentShaderId);
        glLinkProgram(programObjectId);

        final int[] linkStatus = new int[1];
        glGetProgramiv(programObjectId, GL_LINK_STATUS, linkStatus, 0);

        if (LoggerConfig.ON) {
            Log.v(TAG, "Results of linking program:\n"
                    + glGetProgramInfoLog(programObjectId));
        }

        if (linkStatus[0] == 0) {
            if (LoggerConfig.ON) {
                Log.v(TAG, "Linking of program failed");
            }

            return 0;
        }

        return programObjectId;
    }

    public static boolean validateProgram(int programObjectId) {
        glValidateProgram(programObjectId);

        final int[] validateStatus = new int[1];
        glGetProgramiv(programObjectId, GL_VALIDATE_STATUS, validateStatus, 0);
        Log.v(TAG, "Result of validating program: " + validateStatus[0] + "\nLog: " + glGetProgramInfoLog(programObjectId));

        return validateStatus[0] != 0;
    }

    public static int buildProgram(String vertexShaderSource, String fragmentShaderSource) {
        int program;

        int vertexShader = compileVertexShader(vertexShaderSource);
        int fragmentShader = compileFragmentShader(fragmentShaderSource);

        program = linkProgram(vertexShader, fragmentShader);

        if (LoggerConfig.ON) {
            validateProgram(program);
        }

        return program;
    }

}
